/*********************************************************************
 *
 *             Microchip USB C18 Firmware -  USB-CDC Version 1.0
 *             Adapte pour la carte microcontrôleur Gamel_Trophy v3
 *
 * Voici les quelques fonctions a utiliser
 *
 * void usb_init()
 *       Cette fonction permet d'initialiser le peripherique usb.
 *
 * byte getsUSBUSART(char *buffer,byte len)
 *       Cette fonction recupere une chaine de caractere de longueur len 
 *       et la place a l'endroit pointee par buffer.
 *       len doit etre inferieur strictement a 64.
 *       La fonction renvoie le nombre de caracteres recus
 *
 * mCDCUsartRxIsBusy()
 *       Cette macro permet de verifier si l'usb a recu quelque chose
 * 
 * void putsUSBUSART(char *data)
 *       Cette fonction envoie la chaine de caractere pointee par data.
 *       La chaine envoyee doit etre inferieur a 255 caracteres et se
 *       en memoire donnees.
 *       Il faut verifier que le port est libre avant d'envoyer une telle
 *       chaine a l'aide de mUSBUSARTIsTxTrfReady()
 * 
 * void putrsUSBUSART(const rom char *data)
 *       Meme chose que ci-dessus mais pour une chaine de caracteres situee
 *       en memoire programme
 *
 * mUSBUSARTIsTxTrfReady()
 *       Cette macro permet de verifier si le port USB est disponible pour
 *       que l'on puisse transmettre. Il ne faut pas que cette macro soit
 *       bloquante.
 * 
 * 
 *
 *********************************************************************
 
/** I N C L U D E S **********************************************************/
#include <p18cxxx.h>
#include "usb.h"

#ifdef USB_USE_CDC

/** V A R I A B L E S ********************************************************/
#pragma udata
byte cdc_rx_len;            // total rx length

byte cdc_trf_state;         // States are defined cdc.h
POINTER pCDCSrc;            // Dedicated source pointer
POINTER pCDCDst;            // Dedicated destination pointer
byte cdc_tx_len;            // total tx length
byte cdc_mem_type;          // _ROM, _RAM

LINE_CODING line_coding;    // Buffer to store line coding information
CONTROL_SIGNAL_BITMAP control_signal_bitmap;

/*
 * SEND_ENCAPSULATED_COMMAND and GET_ENCAPSULATED_RESPONSE are required
 * requests according to the CDC specification.
 * However, it is not really being used here, therefore a dummy buffer is
 * used for conformance.
 */
#define dummy_length    0x08
byte dummy_encapsulated_cmd_response[dummy_length];

/** P R I V A T E  P R O T O T Y P E S ***************************************/

/** D E C L A R A T I O N S **************************************************/
#pragma code

/** C L A S S  S P E C I F I C  R E Q ****************************************/
/******************************************************************************
 * Function:        void USBCheckCDCRequest(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine checks the setup data packet to see if it
 *                  knows how to handle it
 *
 * Note:            None
 *****************************************************************************/
void USBCheckCDCRequest(void)
{
    /*
     * If request recipient is not an interface then return
     */
    if(SetupPkt.Recipient != RCPT_INTF) return;

    /*
     * If request type is not class-specific then return
     */
    if(SetupPkt.RequestType != CLASS) return;

    /*
     * Interface ID must match interface numbers associated with
     * CDC class, else return
     */
    if((SetupPkt.bIntfID != CDC_COMM_INTF_ID)&&
       (SetupPkt.bIntfID != CDC_DATA_INTF_ID)) return;
    
    switch(SetupPkt.bRequest)
    {
        case SEND_ENCAPSULATED_COMMAND:
            ctrl_trf_session_owner = MUID_CDC;
            pSrc.bRam = (byte*)&dummy_encapsulated_cmd_response;
            usb_stat.ctrl_trf_mem = _RAM;
            LSB(wCount) = dummy_length;
            break;
        case GET_ENCAPSULATED_RESPONSE:
            ctrl_trf_session_owner = MUID_CDC;
            // Populate dummy_encapsulated_cmd_response first.
            pDst.bRam = (byte*)&dummy_encapsulated_cmd_response;
            break;
        case SET_COMM_FEATURE:                  // Optional
            break;
        case GET_COMM_FEATURE:                  // Optional
            break;
        case CLEAR_COMM_FEATURE:                // Optional
            break;
        case SET_LINE_CODING:
            ctrl_trf_session_owner = MUID_CDC;
            pDst.bRam = (byte*)&line_coding;    // Set destination
            break;
        case GET_LINE_CODING:
            ctrl_trf_session_owner = MUID_CDC;
            pSrc.bRam = (byte*)&line_coding;    // Set source
            usb_stat.ctrl_trf_mem = _RAM;       // Set memory type
            LSB(wCount) = LINE_CODING_LENGTH;   // Set data count
            break;
        case SET_CONTROL_LINE_STATE:
            ctrl_trf_session_owner = MUID_CDC;
            control_signal_bitmap._byte = LSB(SetupPkt.W_Value);
            break;
        case SEND_BREAK:                        // Optional
            break;
        default:
            break;
    }//end switch(SetupPkt.bRequest)

}//end USBCheckCDCRequest

/** U S E R  A P I ***********************************************************/

/******************************************************************************
 * Function:       void usb_init(void)
 *
 *****************************************************************************/
void usb_init(void)
{
  tris_usb_bus_sense = 1;
  mInitializeUSBDriver(); 
}



/******************************************************************************
 * Function:        void CDCInitEP(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        CDCInitEP initializes CDC endpoints, buffer descriptors,
 *                  internal state-machine, and variables.
 *                  It should be called after the USB host has sent out a
 *                  SET_CONFIGURATION request.
 *                  See USBStdSetCfgHandler() in usb9.c for examples.
 *
 * Note:            None
 *****************************************************************************/
void CDCInitEP(void)
{
    //Abstract line coding information
    line_coding.dwDTERate._dword = 115200;      // baud rate
    line_coding.bCharFormat = 0x00;             // 1 stop bit
    line_coding.bParityType = 0x00;             // None
    line_coding.bDataBits = 0x08;               // 5,6,7,8, or 16

    cdc_trf_state = CDC_TX_READY;
    cdc_rx_len = 0;
    
    CDC_COMM_UEP = EP_IN|HSHK_EN;               // Enable 1 Comm pipe
    CDC_DATA_UEP = EP_OUT_IN|HSHK_EN;           // Enable 2 data pipes

    /*
     * Do not have to init Cnt of IN pipes here.
     * Reason:  Number of bytes to send to the host
     *          varies from one transaction to
     *          another. Cnt should equal the exact
     *          number of bytes to transmit for
     *          a given IN transaction.
     *          This number of bytes will only
     *          be known right before the data is
     *          sent.
     */
    CDC_INT_BD_IN.ADR = (byte*)&cdc_notice;     // Set buffer address
    CDC_INT_BD_IN.Stat._byte = _UCPU|_DAT1;     // Set status

    CDC_BULK_BD_OUT.Cnt = sizeof(cdc_data_rx);  // Set buffer size
    CDC_BULK_BD_OUT.ADR = (byte*)&cdc_data_rx;  // Set buffer address
    CDC_BULK_BD_OUT.Stat._byte = _USIE|_DAT0|_DTSEN;// Set status

    CDC_BULK_BD_IN.ADR = (byte*)&cdc_data_tx;   // Set buffer size
    CDC_BULK_BD_IN.Stat._byte = _UCPU|_DAT1;    // Set buffer address

}//end CDCInitEP

/******************************************************************************
 * Function:        byte getsUSBUSART(char *buffer,
 *                                    byte len)
 *
 * PreCondition:    Value of input argument 'len' should be smaller than the
 *                  maximum endpoint size responsible for receiving bulk
 *                  data from USB host for CDC class.
 *                  Input argument 'buffer' should point to a buffer area that
 *                  is bigger or equal to the size specified by 'len'.
 *
 * Input:           buffer  : Pointer to where received bytes are to be stored
 *                  len     : The number of bytes expected.
 *
 * Output:          The number of bytes copied to buffer.
 *
 * Side Effects:    Publicly accessible variable cdc_rx_len is updated with
 *                  the number of bytes copied to buffer.
 *                  Once getsUSBUSART is called, subsequent retrieval of
 *                  cdc_rx_len can be done by calling macro mCDCGetRxLength().
 *
 * Overview:        getsUSBUSART copies a string of bytes received through
 *                  USB CDC Bulk OUT endpoint to a user's specified location. 
 *                  It is a non-blocking function. It does not wait
 *                  for data if there is no data available. Instead it returns
 *                  '0' to notify the caller that there is no data available.
 *
 * Note:            If the actual number of bytes received is larger than the
 *                  number of bytes expected (len), only the expected number
 *                  of bytes specified will be copied to buffer.
 *                  If the actual number of bytes received is smaller than the
 *                  number of bytes expected (len), only the actual number
 *                  of bytes received will be copied to buffer.
 *****************************************************************************/
byte getsUSBUSART(char *buffer, byte len)
{
    cdc_rx_len = 0;
    
    if(!mCDCUsartRxIsBusy())
    {
        /*
         * Adjust the expected number of bytes to equal
         * the actual number of bytes received.
         */
        if(len > CDC_BULK_BD_OUT.Cnt)
            len = CDC_BULK_BD_OUT.Cnt;
        
        /*
         * Copy data from dual-ram buffer to user's buffer
         */
        for(cdc_rx_len = 0; cdc_rx_len < len; cdc_rx_len++)
            buffer[cdc_rx_len] = cdc_data_rx[cdc_rx_len];

        /*
         * Prepare dual-ram buffer for next OUT transaction
         */
        CDC_BULK_BD_OUT.Cnt = sizeof(cdc_data_rx);
        mUSBBufferReady(CDC_BULK_BD_OUT);
    }//end if
    
    return cdc_rx_len;
    
}//end getsUSBUSART

/******************************************************************************
 * Function:        void putsUSBUSART(char *data)
 *
 * PreCondition:    cdc_trf_state must be in the CDC_TX_READY state.
 *                  
 *                  The string of characters pointed to by 'data' must equal
 *                  to or smaller than 255 bytes.
 *
 * Input:           data    : Pointer to a null-terminated string of data.
 *                            If a null character is not found, 255 bytes
 *                            of data will be transferred to the host.
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        putsUSBUSART writes a string of data to the USB including
 *                  the null character. Use this version, 'puts', to transfer
 *                  data located in data memory.
 *
 * Note:            The transfer mechanism for device-to-host(put) is more
 *                  flexible than host-to-device(get). It can handle
 *                  a string of data larger than the maximum size of bulk IN
 *                  endpoint. A state machine is used to transfer a long
 *                  string of data over multiple USB transactions.
 *                  See CDCTxService() for more details.
 *****************************************************************************/
void putsUSBUSART(char *data)
{
    byte len;

    /*
     * User should have checked that cdc_trf_state is in CDC_TX_READY state
     * before calling this function.
     * As a safety precaution, this fuction checks the state one more time
     * to make sure it does not override any pending transactions.
     *
     * Currently it just quits the routine without reporting any errors back
     * to the user.
     *
     * Bottomline: User MUST make sure that mUSBUSARTIsTxTrfReady()==1
     *             before calling this function!
     * Example:
     * if(mUSBUSARTIsTxTrfReady())
     *     putsUSBUSART(pData);
     *
     * IMPORTANT: Never use the following blocking while loop to wait:
     * while(!mUSBUSARTIsTxTrfReady())
     *     putsUSBUSART(pData);
     *
     * The whole firmware framework is written based on cooperative
     * multi-tasking and a blocking code is not acceptable.
     * Use a state machine instead.
     */
    if(cdc_trf_state != CDC_TX_READY) return;
    
    /*
     * While loop counts the number of bytes to send including the
     * null character.
     */
    len = 0;
    do
    {
        len++;
        if(len == 255) break;       // Break loop once max len is reached.
    }while(*data++);
    
    /*
     * Re-adjust pointer to its initial location
     */
    data-=len;
    
    /*
     * Second piece of information (length of data to send) is ready.
     * Call mUSBUSARTTxRam to setup the transfer.
     * The actual transfer process will be handled by CDCTxService(),
     * which should be called once per Main Program loop.
     */
    mUSBUSARTTxRam((byte*)data,len);     // See cdc.h
}//end putsUSBUSART

/******************************************************************************
 * Function:        void putrsUSBUSART(const rom char *data)
 *
 * PreCondition:    cdc_trf_state must be in the CDC_TX_READY state.
 *                  
 *                  The string of characters pointed to by 'data' must equal
 *                  to or smaller than 255 bytes.
 *
 * Input:           data    : Pointer to a null-terminated string of data.
 *                            If a null character is not found, 255 bytes
 *                            of data will be transferred to the host.
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        putrsUSBUSART writes a string of data to the USB including
 *                  the null character. Use this version, 'putrs', to transfer
 *                  data literals and data located in program memory.
 *
 * Note:            The transfer mechanism for device-to-host(put) is more
 *                  flexible than host-to-device(get). It can handle
 *                  a string of data larger than the maximum size of bulk IN
 *                  endpoint. A state machine is used to transfer a long
 *                  string of data over multiple USB transactions.
 *                  See CDCTxService() for more details.
 *****************************************************************************/
void putrsUSBUSART(const rom char *data)
{
    byte len;

    /*
     * User should have checked that cdc_trf_state is in CDC_TX_READY state
     * before calling this function.
     * As a safety precaution, this fuction checks the state one more time
     * to make sure it does not override any pending transactions.
     *
     * Currently it just quits the routine without reporting any errors back
     * to the user.
     *
     * Bottomline: User MUST make sure that mUSBUSARTIsTxTrfReady()
     *             before calling this function!
     * Example:
     * if(mUSBUSARTIsTxTrfReady())
     *     putsUSBUSART(pData);
     *
     * IMPORTANT: Never use the following blocking while loop to wait:
     * while(cdc_trf_state != CDC_TX_READY)
     *     putsUSBUSART(pData);
     *
     * The whole firmware framework is written based on cooperative
     * multi-tasking and a blocking code is not acceptable.
     * Use a state machine instead.
     */
    if(cdc_trf_state != CDC_TX_READY) return;
    
    /*
     * While loop counts the number of bytes to send including the
     * null character.
     */
    len = 0;
    do
    {
        len++;
        if(len == 255) break;       // Break loop once max len is reached.
    }while(*data++);
    
    /*
     * Re-adjust pointer to its initial location
     */
    data-=len;
    
    /*
     * Second piece of information (length of data to send) is ready.
     * Call mUSBUSARTTxRom to setup the transfer.
     * The actual transfer process will be handled by CDCTxService(),
     * which should be called once per Main Program loop.
     */
    mUSBUSARTTxRom((rom byte*)data,len); // See cdc.h

}//end putrsUSBUSART

/******************************************************************************
 * Function:        void CDCTxService(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        CDCTxService handles device-to-host transaction(s).
 *                  This function should be called once per Main Program loop.
 *
 * Note:            None
 *****************************************************************************/
void CDCTxService(void)
{
    byte byte_to_send;
    
    if(mCDCUsartTxIsBusy()) return;
    /*
     * Completing stage is necessary while [ mCDCUSartTxIsBusy()==1 ].
     * By having this stage, user can always check cdc_trf_state,
     * and not having to call mCDCUsartTxIsBusy() directly.
     */
    if(cdc_trf_state == CDC_TX_COMPLETING)
        cdc_trf_state = CDC_TX_READY;
    
    /*
     * If CDC_TX_READY state, nothing to do, just return.
     */
    if(cdc_trf_state == CDC_TX_READY) return;
    
    /*
     * If CDC_TX_BUSY_ZLP state, send zero length packet
     */
    if(cdc_trf_state == CDC_TX_BUSY_ZLP)
    {
        CDC_BULK_BD_IN.Cnt = 0;
        cdc_trf_state = CDC_TX_COMPLETING;
    }
    else if(cdc_trf_state == CDC_TX_BUSY)
    {
        /*
         * First, have to figure out how many byte of data to send.
         */
    	if(cdc_tx_len > sizeof(cdc_data_tx))
    	    byte_to_send = sizeof(cdc_data_tx);
    	else
    	    byte_to_send = cdc_tx_len;

        /*
         * Next, load the number of bytes to send to Cnt in buffer descriptor
         */
        CDC_BULK_BD_IN.Cnt = byte_to_send;

        /*
         * Subtract the number of bytes just about to be sent from the total.
         */
    	cdc_tx_len = cdc_tx_len - byte_to_send;
    	        
        pCDCDst.bRam = (byte*)&cdc_data_tx; // Set destination pointer
        
        if(cdc_mem_type == _ROM)            // Determine type of memory source
        {
            while(byte_to_send)
            {
                *pCDCDst.bRam = *pCDCSrc.bRom;
                pCDCDst.bRam++;
                pCDCSrc.bRom++;
                byte_to_send--;
            }//end while(byte_to_send)
        }
        else // _RAM
        {
            while(byte_to_send)
            {
                *pCDCDst.bRam = *pCDCSrc.bRam;
                pCDCDst.bRam++;
                pCDCSrc.bRam++;
                byte_to_send--;
            }//end while(byte_to_send._word)
        }//end if(cdc_mem_type...)
        
        /*
         * Lastly, determine if a zero length packet state is necessary.
         * See explanation in USB Specification 2.0: Section 5.8.3
         */
        if(cdc_tx_len == 0)
        {
            if(CDC_BULK_BD_IN.Cnt == sizeof(cdc_data_tx))
                cdc_trf_state = CDC_TX_BUSY_ZLP;
            else
                cdc_trf_state = CDC_TX_COMPLETING;
        }//end if(cdc_tx_len...)
            
    }//end if(cdc_tx_sate == CDC_TX_BUSY)
    
    /*
     * Both CDC_TX_BUSY and CDC_TX_BUSY_ZLP states use the following macro
     */
    mUSBBufferReady(CDC_BULK_BD_IN);

}//end CDCTxService

#endif //USB_USE_CDC



/** EOF cdc.c ****************************************************************/

/******************************************************************************
 * -usbmmap.c-
 * USB Memory Map
 * This file is the USB memory manager; it serves as a compile-time memory
 * allocator for the USB endpoints. It uses the compile time options passed
 * from usbcfg.h to instantiate endpoints and endpoint buffer.
 *
 * Each endpoint requires to have a set of Buffer Descriptor registers(BDT).
 * A BDT is 4-byte long and has a specific RAM location for each endpoint.
 * The BDT for endpoint 0 out is located at address 0x400 to 0x403.
 * The BDT for endpoint 0 in is located at address 0x404 to 0x407.
 * The BDT for endpoint 1 out is located at address 0x408 to 0x40B.
 * and so on... The above allocation assumes the Ping-Pong Buffer Mode 0 is
 * used. These locations are already hard-wired in the silicon. The point
 * of doing instantiation, i.e. volatile far BDT ep0Bo;, is to provide the
 * C compiler a way to address each variable directly. This is very important
 * because when a register can be accessed directly, it saves execution time
 * and reduces program size.
 * 
 * Endpoints are defined using the endpoint number and the direction
 * of transfer. For simplicity, usbmmap.c only uses the endpoint number
 * in the BDT register allocation scheme. This means if the usbcfg.h states
 * that the MAX_EP_NUMBER is number 1, then four BDTs will be
 * instantiated: one each for endpoint0 in and endpoint0 out, which must
 * always be instantiated for control transfer by default, and one each sets
 * for endpoint1 in and endpoint1 out. The naming convention for instantiating
 * BDT is
 * 
 * ep<#>B<d>
 *
 * where # is the endpoint number, and d is the direction of
 * transfer, which could be either <i> or <o>.
 *
 * The USB memory manager uses MAX_EP_NUMBER, as defined in usbcfg.h, to define
 * the endpoints to be instantiated. This represents the highest endpoint
 * number to be allocated, not how many endpoints are used. Since the BDTs for
 * endpoints have hardware-assigned addresses in Bank 4, setting this value too
 * high may lead to inefficient use of data RAM. For example, if an application
 * uses only endpoints EP0 and EP4, then the MAX_EP_NUMBER is 4, and not 2.
 * The in-between endpoint BDTs in this example (EP1, EP2, and EP3) go unused,
 * and the 24 bytes of memory associated with them are wasted. It does not make
 * much sense to skip endpoints, but the final decision lies with the user.
 *
 * The next step is to assign the instantiated BDTs to different
 * USB functions. The firmware framework fundamentally assumes that every USB
 * function should know which endpoint it is using, i.e., the default control
 * transfer should know that it is using endpoint 0 in and endpoint 0 out.
 * A HID class can choose which endpoint it wants to use, but once chosen, it
 * should always know the number of the endpoint.
 *
 * The assignment of endpoints to USB functions is managed centrally
 * in usbcfg.h. This helps prevent the mistake of having more
 * than one USB function using the same endpoint. The "Endpoint Allocation"
 * section in usbcfg.h provides examples for how to map USB endpoints to USB
 * functions.
 * Quite a few things can be mapped in that section. There is no
 * one correct way to do the mapping and the user has the choice to
 * choose a method that is most suitable to the application.
 *
 * Typically, however, a user will want to map the following for a given
 * USB interface function:
 * 1. The USB interface ID
 * 2. The endpoint control registers (UEPn)
 * 3. The BDT registers (ep<#>B<d>)
 * 4. The endpoint size
 *
 * Example: Assume a USB device class "foo", which uses one out endpoint
 *          of size 64-byte and one in endpoint of size 64-byte, then:
 *
 * #define FOO_INTF_ID          0x00
 * #define FOO_UEP              UEP1
 * #define FOO_BD_OUT           ep1Bo
 * #define FOO_BD_IN            ep1Bi
 * #define FOO_EP_SIZE          64
 *
 * The mapping above has chosen class "foo" to use endpoint 1.
 * The names are arbitrary and can be anything other than FOO_??????.
 * For abstraction, the code for class "foo" should use the abstract
 * definitions of FOO_BD_OUT,FOO_BD_IN, and not ep1Bo or ep1Bi.
 *
 * Note that the endpoint size defined in the usbcfg.h file is again
 * used in the usbmmap.c file. This shows that the relationship between
 * the two files are tightly related.
 * 
 * The endpoint buffer for each USB function must be located in the
 * dual-port RAM area and has to come after all the BDTs have been
 * instantiated. An example declaration is:
 * volatile far unsigned char[FOO_EP_SIZE] data;
 *
 * The 'volatile' keyword tells the compiler not to perform any code
 * optimization on this variable because its content could be modified
 * by the hardware. The 'far' keyword tells the compiler that this variable
 * is not located in the Access RAM area (0x000 - 0x05F).
 *
 * For the variable to be globally accessible by other files, it should be
 * declared in the header file usbmmap.h as an extern definition, such as
 * extern volatile far unsigned char[FOO_EP_SIZE] data;
 *
 * Conclusion:
 * In a short summary, the dependencies between usbcfg and usbmmap can
 * be shown as:
 *
 * usbcfg[MAX_EP_NUMBER] -> usbmmap
 * usbmmap[ep<#>B<d>] -> usbcfg
 * usbcfg[EP size] -> usbmmap
 * usbcfg[abstract ep definitions] -> usb9/hid/cdc/etc class code
 * usbmmap[endpoint buffer variable] -> usb9/hid/cdc/etc class code
 *
 * Data mapping provides a means for direct addressing of BDT and endpoint
 * buffer. This means less usage of pointers, which equates to a faster and
 * smaller program code.
 *
 *****************************************************************************/


/** U S B  G L O B A L  V A R I A B L E S ************************************/
#pragma udata
byte usb_device_state;          // Device States: DETACHED, ATTACHED, ...
USB_DEVICE_STATUS usb_stat;     // Global USB flags
byte usb_active_cfg;            // Value of current configuration
byte usb_alt_intf[MAX_NUM_INT]; // Array to keep track of the current alternate
                                // setting for each interface ID

/** U S B  F I X E D  L O C A T I O N  V A R I A B L E S *********************/
#pragma udata usbram4=0x400     //See Linker Script,usb4:0x400-0x4FF(256-byte)

/******************************************************************************
 * Section A: Buffer Descriptor Table
 * - 0x400 - 0x4FF(max)
 * - MAX_EP_NUMBER is defined in usbcfg.h
 * - BDT data type is defined in usbmmap.h
 *****************************************************************************/

#if(0 <= MAX_EP_NUMBER)
volatile far BDT ep0Bo;         //Endpoint #0 BD Out
volatile far BDT ep0Bi;         //Endpoint #0 BD In
#endif

#if(1 <= MAX_EP_NUMBER)
volatile far BDT ep1Bo;         //Endpoint #1 BD Out
volatile far BDT ep1Bi;         //Endpoint #1 BD In
#endif

#if(2 <= MAX_EP_NUMBER)
volatile far BDT ep2Bo;         //Endpoint #2 BD Out
volatile far BDT ep2Bi;         //Endpoint #2 BD In
#endif

#if(3 <= MAX_EP_NUMBER)
volatile far BDT ep3Bo;         //Endpoint #3 BD Out
volatile far BDT ep3Bi;         //Endpoint #3 BD In
#endif

#if(4 <= MAX_EP_NUMBER)
volatile far BDT ep4Bo;         //Endpoint #4 BD Out
volatile far BDT ep4Bi;         //Endpoint #4 BD In
#endif

#if(5 <= MAX_EP_NUMBER)
volatile far BDT ep5Bo;         //Endpoint #5 BD Out
volatile far BDT ep5Bi;         //Endpoint #5 BD In
#endif

#if(6 <= MAX_EP_NUMBER)
volatile far BDT ep6Bo;         //Endpoint #6 BD Out
volatile far BDT ep6Bi;         //Endpoint #6 BD In
#endif

#if(7 <= MAX_EP_NUMBER)
volatile far BDT ep7Bo;         //Endpoint #7 BD Out
volatile far BDT ep7Bi;         //Endpoint #7 BD In
#endif

#if(8 <= MAX_EP_NUMBER)
volatile far BDT ep8Bo;         //Endpoint #8 BD Out
volatile far BDT ep8Bi;         //Endpoint #8 BD In
#endif

#if(9 <= MAX_EP_NUMBER)
volatile far BDT ep9Bo;         //Endpoint #9 BD Out
volatile far BDT ep9Bi;         //Endpoint #9 BD In
#endif

#if(10 <= MAX_EP_NUMBER)
volatile far BDT ep10Bo;        //Endpoint #10 BD Out
volatile far BDT ep10Bi;        //Endpoint #10 BD In
#endif

#if(11 <= MAX_EP_NUMBER)
volatile far BDT ep11Bo;        //Endpoint #11 BD Out
volatile far BDT ep11Bi;        //Endpoint #11 BD In
#endif

#if(12 <= MAX_EP_NUMBER)
volatile far BDT ep12Bo;        //Endpoint #12 BD Out
volatile far BDT ep12Bi;        //Endpoint #12 BD In
#endif

#if(13 <= MAX_EP_NUMBER)
volatile far BDT ep13Bo;        //Endpoint #13 BD Out
volatile far BDT ep13Bi;        //Endpoint #13 BD In
#endif

#if(14 <= MAX_EP_NUMBER)
volatile far BDT ep14Bo;        //Endpoint #14 BD Out
volatile far BDT ep14Bi;        //Endpoint #14 BD In
#endif

#if(15 <= MAX_EP_NUMBER)
volatile far BDT ep15Bo;        //Endpoint #15 BD Out
volatile far BDT ep15Bi;        //Endpoint #15 BD In
#endif

/******************************************************************************
 * Section B: EP0 Buffer Space
 ******************************************************************************
 * - Two buffer areas are defined:
 *
 *   A. CTRL_TRF_SETUP
 *      - Size = EP0_BUFF_SIZE as defined in usbcfg.h
 *      - Detailed data structure allows direct adddressing of bits and bytes.
 *
 *   B. CTRL_TRF_DATA
 *      - Size = EP0_BUFF_SIZE as defined in usbcfg.h
 *      - Data structure allows direct adddressing of the first 8 bytes.
 *
 * - Both data types are defined in usbdefs_ep0_buff.h
 *****************************************************************************/
volatile far CTRL_TRF_SETUP SetupPkt;
volatile far CTRL_TRF_DATA CtrlTrfData;

/******************************************************************************
 * Section C: CDC Buffer
 ******************************************************************************
 *
 *****************************************************************************/
#pragma udata usbram5a=0x500    //See Linker Script,usb5:0x500-...
#if defined(USB_USE_CDC)
volatile far unsigned char cdc_notice[CDC_INT_EP_SIZE];
volatile far unsigned char cdc_data_rx[CDC_BULK_OUT_EP_SIZE];
volatile far unsigned char cdc_data_tx[CDC_BULK_IN_EP_SIZE];
#endif
#pragma udata

/** EOF usbmmap.c ************************************************************/


////////////////////////////////usb9.c/////////////////////////////////////////
/** V A R I A B L E S ********************************************************/
#pragma udata

/** P R I V A T E  P R O T O T Y P E S ***************************************/
void USBStdGetDscHandler(void);
void USBStdSetCfgHandler(void);
void USBStdGetStatusHandler(void);
void USBStdFeatureReqHandler(void);

/** D E C L A R A T I O N S **************************************************/
#pragma code
/******************************************************************************
 * Function:        void USBCheckStdRequest(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine checks the setup data packet to see if it
 *                  knows how to handle it
 *
 * Note:            None
 *****************************************************************************/
void USBCheckStdRequest(void)
{   
    if(SetupPkt.RequestType != STANDARD) return;
    
    switch(SetupPkt.bRequest)
    {
        case SET_ADR:
            ctrl_trf_session_owner = MUID_USB9;
            usb_device_state = ADR_PENDING_STATE;       // Update state only
            /* See USBCtrlTrfInHandler() in usbctrltrf.c for the next step */
            break;
        case GET_DSC:
            USBStdGetDscHandler();
            break;
        case SET_CFG:
            USBStdSetCfgHandler();
            break;
        case GET_CFG:
            ctrl_trf_session_owner = MUID_USB9;
            pSrc.bRam = (byte*)&usb_active_cfg;         // Set Source
            usb_stat.ctrl_trf_mem = _RAM;               // Set memory type
            LSB(wCount) = 1;                            // Set data count
            break;
        case GET_STATUS:
            USBStdGetStatusHandler();
            break;
        case CLR_FEATURE:
        case SET_FEATURE:
            USBStdFeatureReqHandler();
            break;
        case GET_INTF:
            ctrl_trf_session_owner = MUID_USB9;
            pSrc.bRam = (byte*)&usb_alt_intf+SetupPkt.bIntfID;  // Set source
            usb_stat.ctrl_trf_mem = _RAM;               // Set memory type
            LSB(wCount) = 1;                            // Set data count
            break;
        case SET_INTF:
            ctrl_trf_session_owner = MUID_USB9;
            usb_alt_intf[SetupPkt.bIntfID] = SetupPkt.bAltID;
            break;
        case SET_DSC:
        case SYNCH_FRAME:
        default:
            break;
    }//end switch
    
}//end USBCheckStdRequest

/******************************************************************************
 * Function:        void USBStdGetDscHandler(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine handles the standard GET_DESCRIPTOR request.
 *                  It utilizes tables dynamically looks up descriptor size.
 *                  This routine should never have to be modified if the tables
 *                  in usbdsc.c are declared correctly.
 *
 * Note:            None
 *****************************************************************************/
void USBStdGetDscHandler(void)
{
    if(SetupPkt.bmRequestType == 0x80)
    {
        switch(SetupPkt.bDscType)
        {
            case DSC_DEV:
                ctrl_trf_session_owner = MUID_USB9;
                pSrc.bRom = (rom byte*)&device_dsc;
                wCount._word = sizeof(device_dsc);          // Set data count
                break;
            case DSC_CFG:
                ctrl_trf_session_owner = MUID_USB9;
                pSrc.bRom = *(USB_CD_Ptr+SetupPkt.bDscIndex);
                wCount._word = *(pSrc.wRom+1);              // Set data count
                break;
            case DSC_STR:
                ctrl_trf_session_owner = MUID_USB9;
                pSrc.bRom = *(USB_SD_Ptr+SetupPkt.bDscIndex);
                wCount._word = *pSrc.bRom;                  // Set data count
                break;
        }//end switch
        
        usb_stat.ctrl_trf_mem = _ROM;                       // Set memory type
    }//end if
}//end USBStdGetDscHandler

/******************************************************************************
 * Function:        void USBStdSetCfgHandler(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine first disables all endpoints by clearing
 *                  UEP registers. It then configures (initializes) endpoints
 *                  specified in the modifiable section.
 *
 * Note:            None
 *****************************************************************************/
void USBStdSetCfgHandler(void)
{
    ctrl_trf_session_owner = MUID_USB9;
    mDisableEP1to15();                          // See usbdrv.h
    ClearArray((byte*)&usb_alt_intf,MAX_NUM_INT);
    usb_active_cfg = SetupPkt.bCfgValue;
    if(SetupPkt.bCfgValue == 0)
        usb_device_state = ADDRESS_STATE;
    else
    {
        usb_device_state = CONFIGURED_STATE;

        /* Modifiable Section */

        #if defined(USB_USE_CDC)                // See autofiles\usbcfg.h
        CDCInitEP();
        #endif

        /* End modifiable section */

    }//end if(SetupPkt.bcfgValue == 0)
}//end USBStdSetCfgHandler

/******************************************************************************
 * Function:        void USBStdGetStatusHandler(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine handles the standard GET_STATUS request
 *
 * Note:            None
 *****************************************************************************/
void USBStdGetStatusHandler(void)
{
    CtrlTrfData._byte0 = 0;                         // Initialize content
    CtrlTrfData._byte1 = 0;
        
    switch(SetupPkt.Recipient)
    {
        case RCPT_DEV:
            ctrl_trf_session_owner = MUID_USB9;
            /*
             * _byte0: bit0: Self-Powered Status [0] Bus-Powered [1] Self-Powered
             *         bit1: RemoteWakeup        [0] Disabled    [1] Enabled
             */
//            if(self_power == 1)                     // self_power defined in io_cfg.h
//                CtrlTrfData._byte0|=0b000000001;    // Set bit0
            
            if(usb_stat.RemoteWakeup == 1)          // usb_stat defined in usbmmap.c
                CtrlTrfData._byte0|=0b00000010;     // Set bit1
            break;
        case RCPT_INTF:
            ctrl_trf_session_owner = MUID_USB9;     // No data to update
            break;
        case RCPT_EP:
            ctrl_trf_session_owner = MUID_USB9;
            /*
             * _byte0: bit0: Halt Status [0] Not Halted [1] Halted
             */
            pDst.bRam = (byte*)&ep0Bo+(SetupPkt.EPNum*8)+(SetupPkt.EPDir*4);
            if(*pDst.bRam & _BSTALL)    // Use _BSTALL as a bit mask
                CtrlTrfData._byte0=0x01;// Set bit0
            break;
    }//end switch
    
    if(ctrl_trf_session_owner == MUID_USB9)
    {
        pSrc.bRam = (byte*)&CtrlTrfData;            // Set Source
        usb_stat.ctrl_trf_mem = _RAM;               // Set memory type
        LSB(wCount) = 2;                            // Set data count
    }//end if(...)
}//end USBStdGetStatusHandler

/******************************************************************************
 * Function:        void USBStdFeatureReqHandler(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine handles the standard SET & CLEAR FEATURES
 *                  requests
 *
 * Note:            None
 *****************************************************************************/
void USBStdFeatureReqHandler(void)
{
    if((SetupPkt.bFeature == DEVICE_REMOTE_WAKEUP)&&
       (SetupPkt.Recipient == RCPT_DEV))
    {
        ctrl_trf_session_owner = MUID_USB9;
        if(SetupPkt.bRequest == SET_FEATURE)
            usb_stat.RemoteWakeup = 1;
        else
            usb_stat.RemoteWakeup = 0;
    }//end if
    
    if((SetupPkt.bFeature == ENDPOINT_HALT)&&
       (SetupPkt.Recipient == RCPT_EP)&&
       (SetupPkt.EPNum != 0))
    {
        ctrl_trf_session_owner = MUID_USB9;
        /* Must do address calculation here */
        pDst.bRam = (byte*)&ep0Bo+(SetupPkt.EPNum*8)+(SetupPkt.EPDir*4);
        
        if(SetupPkt.bRequest == SET_FEATURE)
            *pDst.bRam = _USIE|_BSTALL;
        else
        {
            if(SetupPkt.EPDir == 1) // IN
                *pDst.bRam = _UCPU;
            else
                *pDst.bRam = _USIE|_DAT0|_DTSEN;
        }//end if
    }//end if
}//end USBStdFeatureReqHandler

/** EOF usb9.c ***************************************************************/

//////////////////////////////////usbctrltrf.c/////////////////////////////////

/** V A R I A B L E S ********************************************************/
#pragma udata
byte ctrl_trf_state;                // Control Transfer State
byte ctrl_trf_session_owner;        // Current transfer session owner

POINTER pSrc;                       // Data source pointer
POINTER pDst;                       // Data destination pointer
WORD wCount;                        // Data counter

/** P R I V A T E  P R O T O T Y P E S ***************************************/
void USBCtrlTrfSetupHandler(void);
void USBCtrlTrfOutHandler(void);
void USBCtrlTrfInHandler(void);

/** D E C L A R A T I O N S **************************************************/
#pragma code
/******************************************************************************
 * Function:        void USBCtrlEPService(void)
 *
 * PreCondition:    USTAT is loaded with a valid endpoint address.
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        USBCtrlEPService checks for three transaction types that
 *                  it knows how to service and services them:
 *                  1. EP0 SETUP
 *                  2. EP0 OUT
 *                  3. EP0 IN
 *                  It ignores all other types (i.e. EP1, EP2, etc.)
 *
 * Note:            None
 *****************************************************************************/
void USBCtrlEPService(void)
{   
    if(USTAT == EP00_OUT)
    {
        if(ep0Bo.Stat.PID == SETUP_TOKEN)           // EP0 SETUP
            USBCtrlTrfSetupHandler();
        else                                        // EP0 OUT
            USBCtrlTrfOutHandler();
    }
    else if(USTAT == EP00_IN)                       // EP0 IN
        USBCtrlTrfInHandler();
    
}//end USBCtrlEPService

/******************************************************************************
 * Function:        void USBCtrlTrfSetupHandler(void)
 *
 * PreCondition:    SetupPkt buffer is loaded with valid USB Setup Data
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine is a task dispatcher and has 3 stages.
 *                  1. It initializes the control transfer state machine.
 *                  2. It calls on each of the module that may know how to
 *                     service the Setup Request from the host.
 *                     Module Example: USB9, HID, CDC, MSD, ...
 *                     As new classes are added, ClassReqHandler table in
 *                     usbdsc.c should be updated to call all available
 *                     class handlers.
 *                  3. Once each of the modules has had a chance to check if
 *                     it is responsible for servicing the request, stage 3
 *                     then checks direction of the transfer to determine how
 *                     to prepare EP0 for the control transfer.
 *                     Refer to USBCtrlEPServiceComplete() for more details.
 *
 * Note:            Microchip USB Firmware has three different states for
 *                  the control transfer state machine:
 *                  1. WAIT_SETUP
 *                  2. CTRL_TRF_TX
 *                  3. CTRL_TRF_RX
 *                  Refer to firmware manual to find out how one state
 *                  is transitioned to another.
 *
 *                  A Control Transfer is composed of many USB transactions.
 *                  When transferring data over multiple transactions,
 *                  it is important to keep track of data source, data
 *                  destination, and data count. These three parameters are
 *                  stored in pSrc,pDst, and wCount. A flag is used to
 *                  note if the data source is from ROM or RAM.
 *
 *****************************************************************************/
void USBCtrlTrfSetupHandler(void)
{
    byte i;
    
    /* Stage 1 */
    ctrl_trf_state = WAIT_SETUP;
    ctrl_trf_session_owner = MUID_NULL;     // Set owner to NULL
    wCount._word = 0;
    
    /* Stage 2 */
    USBCheckStdRequest();                   // See system\usb9\usb9.c
    
    for(i=0;i < (sizeof(ClassReqHandler)/sizeof(pFunc));i++)
    {
        if(ctrl_trf_session_owner != MUID_NULL)break;
        ClassReqHandler[i]();               // See autofiles\usbdsc.c
    }//end while
        
    /* Stage 3 */
    USBCtrlEPServiceComplete();
    
}//end USBCtrlTrfSetupHandler

/******************************************************************************
 * Function:        void USBCtrlTrfOutHandler(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine handles an OUT transaction according to
 *                  which control transfer state is currently active.
 *
 * Note:            Note that if the the control transfer was from
 *                  host to device, the session owner should be notified
 *                  at the end of each OUT transaction to service the
 *                  received data.
 *
 *****************************************************************************/
void USBCtrlTrfOutHandler(void)
{
    if(ctrl_trf_state == CTRL_TRF_RX)
    {
        USBCtrlTrfRxService();
        
        /*
         * Don't have to worry about overwriting _KEEP bit
         * because if _KEEP was set, TRNIF would not have been
         * generated in the first place.
         */
        if(ep0Bo.Stat.DTS == 0)
            ep0Bo.Stat._byte = _USIE|_DAT1|_DTSEN;
        else
            ep0Bo.Stat._byte = _USIE|_DAT0|_DTSEN;
    }
    else    // CTRL_TRF_TX
        USBPrepareForNextSetupTrf();
    
}//end USBCtrlTrfOutHandler

/******************************************************************************
 * Function:        void USBCtrlTrfInHandler(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine handles an IN transaction according to
 *                  which control transfer state is currently active.
 *
 *
 * Note:            A Set Address Request must not change the acutal address
 *                  of the device until the completion of the control
 *                  transfer. The end of the control transfer for Set Address
 *                  Request is an IN transaction. Therefore it is necessary
 *                  to service this unique situation when the condition is
 *                  right. Macro mUSBCheckAdrPendingState is defined in
 *                  usb9.h and its function is to specifically service this
 *                  event.
 *****************************************************************************/
void USBCtrlTrfInHandler(void)
{
    mUSBCheckAdrPendingState();         // Must check if in ADR_PENDING_STATE
    
    if(ctrl_trf_state == CTRL_TRF_TX)
    {
        USBCtrlTrfTxService();
        
        if(ep0Bi.Stat.DTS == 0)
            ep0Bi.Stat._byte = _USIE|_DAT1|_DTSEN;
        else
            ep0Bi.Stat._byte = _USIE|_DAT0|_DTSEN;
    }
    else // CTRL_TRF_RX
        USBPrepareForNextSetupTrf();

}//end USBCtrlTrfInHandler

/******************************************************************************
 * Function:        void USBCtrlTrfTxService(void)
 *
 * PreCondition:    pSrc, wCount, and usb_stat.ctrl_trf_mem are setup properly.
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine should be called from only two places.
 *                  One from USBCtrlEPServiceComplete() and one from
 *                  USBCtrlTrfInHandler(). It takes care of managing a
 *                  transfer over multiple USB transactions.
 *
 * Note:            This routine works with isochronous endpoint larger than
 *                  256 bytes and is shown here as an example of how to deal
 *                  with BC9 and BC8. In reality, a control endpoint can never
 *                  be larger than 64 bytes.
 *****************************************************************************/
void USBCtrlTrfTxService(void)
{    
    WORD byte_to_send;
    
    /*
     * First, have to figure out how many byte of data to send.
     */
    if(wCount._word < EP0_BUFF_SIZE)
        byte_to_send._word = wCount._word;
    else
        byte_to_send._word = EP0_BUFF_SIZE;
    
    /*
     * Next, load the number of bytes to send to BC9..0 in buffer descriptor
     */
    ep0Bi.Stat.BC9 = 0;
    ep0Bi.Stat.BC8 = 0;
    ep0Bi.Stat._byte |= MSB(byte_to_send);
    ep0Bi.Cnt = LSB(byte_to_send);
    
    /*
     * Subtract the number of bytes just about to be sent from the total.
     */
    wCount._word = wCount._word - byte_to_send._word;
    
    pDst.bRam = (byte*)&CtrlTrfData;        // Set destination pointer

    if(usb_stat.ctrl_trf_mem == _ROM)       // Determine type of memory source
    {
        while(byte_to_send._word)
        {
            *pDst.bRam = *pSrc.bRom;
            pDst.bRam++;
            pSrc.bRom++;
            byte_to_send._word--;
        }//end while(byte_to_send._word)
    }
    else // RAM
    {
        while(byte_to_send._word)
        {
            *pDst.bRam = *pSrc.bRam;
            pDst.bRam++;
            pSrc.bRam++;
            byte_to_send._word--;
        }//end while(byte_to_send._word)
    }//end if(usb_stat.ctrl_trf_mem == _ROM)
    
}//end USBCtrlTrfTxService

/******************************************************************************
 * Function:        void USBCtrlTrfRxService(void)
 *
 * PreCondition:    pDst and wCount are setup properly.
 *                  pSrc is always &CtrlTrfData
 *                  usb_stat.ctrl_trf_mem is always _RAM.
 *                  wCount should be set to 0 at the start of each control
 *                  transfer.
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        *** This routine is only partially complete. Check for
 *                  new version of the firmware.
 *
 * Note:            None
 *****************************************************************************/
void USBCtrlTrfRxService(void)
{
    WORD byte_to_read;

    MSB(byte_to_read) = 0x03 & ep0Bo.Stat._byte;    // Filter out last 2 bits
    LSB(byte_to_read) = ep0Bo.Cnt;
    
    /*
     * Accumulate total number of bytes read
     */
    wCount._word = wCount._word + byte_to_read._word;
    
    pSrc.bRam = (byte*)&CtrlTrfData;

    while(byte_to_read._word)
    {
        *pDst.bRam = *pSrc.bRam;
        pDst.bRam++;
        pSrc.bRam++;
        byte_to_read._word--;
    }//end while(byte_to_read._word)    
    
}//end USBCtrlTrfRxService

/******************************************************************************
 * Function:        void USBCtrlEPServiceComplete(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine wrap up the ramaining tasks in servicing
 *                  a Setup Request. Its main task is to set the endpoint
 *                  controls appropriately for a given situation. See code
 *                  below.
 *                  There are three main scenarios:
 *                  a) There was no handler for the Request, in this case
 *                     a STALL should be sent out.
 *                  b) The host has requested a read control transfer,
 *                     endpoints are required to be setup in a specific way.
 *                  c) The host has requested a write control transfer, or
 *                     a control data stage is not required, endpoints are
 *                     required to be setup in a specific way.
 *
 *                  Packet processing is resumed by clearing PKTDIS bit.
 *
 * Note:            None
 *****************************************************************************/
void USBCtrlEPServiceComplete(void)
{
    if(ctrl_trf_session_owner == MUID_NULL)
    {
        /*
         * If no one knows how to service this request then stall.
         * Must also prepare EP0 to receive the next SETUP transaction.
         */
        ep0Bo.Cnt = EP0_BUFF_SIZE;
        ep0Bo.ADR = (byte*)&SetupPkt;
        
        ep0Bo.Stat._byte = _USIE|_BSTALL;
        ep0Bi.Stat._byte = _USIE|_BSTALL;
    }
    else    // A module has claimed ownership of the control transfer session.
    {
        if(SetupPkt.DataDir == DEV_TO_HOST)
        {
            if(SetupPkt.wLength < wCount._word)
                wCount._word = SetupPkt.wLength;
            USBCtrlTrfTxService();
            ctrl_trf_state = CTRL_TRF_TX;
            /*
             * Control Read:
             * <SETUP[0]><IN[1]><IN[0]>...<OUT[1]> | <SETUP[0]>
             * 1. Prepare OUT EP to respond to early termination
             *
             * NOTE:
             * If something went wrong during the control transfer,
             * the last status stage may not be sent by the host.
             * When this happens, two different things could happen
             * depending on the host.
             * a) The host could send out a RESET.
             * b) The host could send out a new SETUP transaction
             *    without sending a RESET first.
             * To properly handle case (b), the OUT EP must be setup
             * to receive either a zero length OUT transaction, or a
             * new SETUP transaction.
             *
             * Since the SETUP transaction requires the DTS bit to be
             * DAT0 while the zero length OUT status requires the DTS
             * bit to be DAT1, the DTS bit check by the hardware should
             * be disabled. This way the SIE could accept either of
             * the two transactions.
             *
             * Furthermore, the Cnt byte should be set to prepare for
             * the SETUP data (8-byte or more), and the buffer address
             * should be pointed to SetupPkt.
             */
            ep0Bo.Cnt = EP0_BUFF_SIZE;
            ep0Bo.ADR = (byte*)&SetupPkt;            
            ep0Bo.Stat._byte = _USIE;           // Note: DTSEN is 0!
    
            /*
             * 2. Prepare IN EP to transfer data, Cnt should have
             *    been initialized by responsible request owner.
             */
            ep0Bi.ADR = (byte*)&CtrlTrfData;
            ep0Bi.Stat._byte = _USIE|_DAT1|_DTSEN;
        }
        else    // (SetupPkt.DataDir == HOST_TO_DEV)
        {
            ctrl_trf_state = CTRL_TRF_RX;
            /*
             * Control Write:
             * <SETUP[0]><OUT[1]><OUT[0]>...<IN[1]> | <SETUP[0]>
             *
             * 1. Prepare IN EP to respond to early termination
             *
             *    This is the same as a Zero Length Packet Response
             *    for control transfer without a data stage
             */
            ep0Bi.Cnt = 0;
            ep0Bi.Stat._byte = _USIE|_DAT1|_DTSEN;

            /*
             * 2. Prepare OUT EP to receive data.
             */
            ep0Bo.Cnt = EP0_BUFF_SIZE;
            ep0Bo.ADR = (byte*)&CtrlTrfData;
            ep0Bo.Stat._byte = _USIE|_DAT1|_DTSEN;
        }//end if(SetupPkt.DataDir == DEV_TO_HOST)
    }//end if(ctrl_trf_session_owner == MUID_NULL)
    
    /*
     * PKTDIS bit is set when a Setup Transaction is received.
     * Clear to resume packet processing.
     */
    UCONbits.PKTDIS = 0;

}//end USBCtrlEPServiceComplete

/******************************************************************************
 * Function:        void USBPrepareForNextSetupTrf(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        The routine forces EP0 OUT to be ready for a new Setup
 *                  transaction, and forces EP0 IN to be owned by CPU.
 *
 * Note:            None
 *****************************************************************************/
void USBPrepareForNextSetupTrf(void)
{
    ctrl_trf_state = WAIT_SETUP;            // See usbctrltrf.h
    ep0Bo.Cnt = EP0_BUFF_SIZE;              // Defined in usbcfg.h
    ep0Bo.ADR = (byte*)&SetupPkt;
    ep0Bo.Stat._byte = _USIE|_DAT0|_DTSEN;  // EP0 buff dsc init, see usbmmap.h
    ep0Bi.Stat._byte = _UCPU;               // EP0 IN buffer initialization
}//end USBPrepareForNextSetupTrf

/** EOF usbctrltrf.c *********************************************************/


////////////////////////////////usbdrv.c

/** V A R I A B L E S ********************************************************/
#pragma udata

/** P R I V A T E  P R O T O T Y P E S ***************************************/
void USBModuleEnable(void);
void USBModuleDisable(void);

void USBSuspend(void);
void USBWakeFromSuspend(void);

void USBProtocolResetHandler(void);
void USB_SOF_Handler(void);
void USBStallHandler(void);
void USBErrorHandler(void);

/** D E C L A R A T I O N S **************************************************/
#pragma code
/******************************************************************************
 * Function:        void USBCheckBusStatus(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine enables/disables the USB module by monitoring
 *                  the USB power signal.
 *
 * Note:            None
 *****************************************************************************/
void USBCheckBusStatus(void)
{
    /**************************************************************************
     * Bus Attachment & Detachment Detection
     * usb_bus_sense is an i/o pin defined in io_cfg.h
     *************************************************************************/
    #define USB_BUS_ATTACHED    1
    #define USB_BUS_DETACHED    0

    if(usb_bus_sense == USB_BUS_ATTACHED)       // Is USB bus attached?
    {
        if(UCONbits.USBEN == 0)                 // Is the module off?
            USBModuleEnable();                  // Is off, enable it
    }
    else
    {
        if(UCONbits.USBEN == 1)                 // Is the module on?
            USBModuleDisable();                 // Is on, disable it
    }//end if(usb_bus_sense...)
    
    /*
     * After enabling the USB module, it takes some time for the voltage
     * on the D+ or D- line to rise high enough to get out of the SE0 condition.
     * The USB Reset interrupt should not be unmasked until the SE0 condition is
     * cleared. This helps preventing the firmware from misinterpreting this
     * unique event as a USB bus reset from the USB host.
     */
    if(usb_device_state == ATTACHED_STATE)
    {
        if(!UCONbits.SE0)
        {
            UIR = 0;                        // Clear all USB interrupts
            UIE = 0;                        // Mask all USB interrupts
            UIEbits.URSTIE = 1;             // Unmask RESET interrupt
            UIEbits.IDLEIE = 1;             // Unmask IDLE interrupt
            usb_device_state = POWERED_STATE;
        }//end if                           // else wait until SE0 is cleared
    }//end if(usb_device_state == ATTACHED_STATE)

}//end USBCheckBusStatus

/******************************************************************************
 * Function:        void USBModuleEnable(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine enables the USB module.
 *                  An end designer should never have to call this routine
 *                  manually. This routine should only be called from
 *                  USBCheckBusStatus().
 *
 * Note:            See USBCheckBusStatus() for more information.
 *****************************************************************************/
void USBModuleEnable(void)
{
    UCON = 0;
    UIE = 0;                                // Mask all USB interrupts
    UCONbits.USBEN = 1;                     // Enable module & attach to bus
    usb_device_state = ATTACHED_STATE;      // Defined in usbmmap.c & .h
}//end USBModuleEnable

/******************************************************************************
 * Function:        void USBModuleDisable(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine disables the USB module.
 *                  An end designer should never have to call this routine
 *                  manually. This routine should only be called from
 *                  USBCheckBusStatus().
 *
 * Note:            See USBCheckBusStatus() for more information.
 *****************************************************************************/
void USBModuleDisable(void)
{
    UCON = 0;                               // Disable module & detach from bus
    UIE = 0;                                // Mask all USB interrupts
    usb_device_state = DETACHED_STATE;      // Defined in usbmmap.c & .h
}//end USBModuleDisable

/******************************************************************************
 * Function:        void USBSoftDetach(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    The device will have to be re-enumerated to function again.
 *
 * Overview:        USBSoftDetach electrically disconnects the device from
 *                  the bus. This is done by stop supplying Vusb voltage to
 *                  pull-up resistor. The pull-down resistors on the host
 *                  side will pull both differential signal lines low and
 *                  the host registers the event as a disconnect.
 *
 *                  Since the USB cable is not physically disconnected, the
 *                  power supply through the cable can still be sensed by
 *                  the device. The next time USBCheckBusStatus() function
 *                  is called, it will reconnect the device back to the bus.
 *
 * Note:            None
 *****************************************************************************/
void USBSoftDetach(void)
{
    USBModuleDisable();
}//end USBSoftDetach

/******************************************************************************
 * Function:        void USBDriverService(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This routine is the heart of this firmware. It manages
 *                  all USB interrupts.
 *
 * Note:            Device state transitions through the following stages:
 *                  DETACHED -> ATTACHED -> POWERED -> DEFAULT ->
 *                  ADDRESS_PENDING -> ADDRESSED -> CONFIGURED -> READY
 *****************************************************************************/
void USBDriverService(void)
{   
    /*
     * Pointless to continue servicing if USB cable is not even attached.
     */
    if(usb_device_state == DETACHED_STATE) return;
    
    /*
     * Task A: Service USB Activity Interrupt
     */

    if(UIRbits.ACTVIF && UIEbits.ACTVIE)    USBWakeFromSuspend();

    /*
     * Pointless to continue servicing if the device is in suspend mode.
     */    
    if(UCONbits.SUSPND==1) return;
            
    /*
     * Task B: Service USB Bus Reset Interrupt.
     * When bus reset is received during suspend, ACTVIF will be set first,
     * once the UCONbits.SUSPND is clear, then the URSTIF bit will be asserted.
     * This is why URSTIF is checked after ACTVIF.
     */
    if(UIRbits.URSTIF && UIEbits.URSTIE)    USBProtocolResetHandler();
    
    /*
     * Task C: Service other USB interrupts
     */
    if(UIRbits.IDLEIF && UIEbits.IDLEIE)    USBSuspend();
    if(UIRbits.SOFIF && UIEbits.SOFIE)      USB_SOF_Handler();
    if(UIRbits.STALLIF && UIEbits.STALLIE)  USBStallHandler();
    if(UIRbits.UERRIF && UIEbits.UERRIE)    USBErrorHandler();

    /*
     * Pointless to continue servicing if the host has not sent a bus reset.
     * Once bus reset is received, the device transitions into the DEFAULT
     * state and is ready for communication.
     */
    if(usb_device_state < DEFAULT_STATE) return;

    /*
     * Task D: Servicing USB Transaction Complete Interrupt
     */
    if(UIRbits.TRNIF && UIEbits.TRNIE)
    {
        /*
         * USBCtrlEPService only services transactions over EP0.
         * It ignores all other EP transactions.
         */
        USBCtrlEPService();
        
        /*
         * Other EP can be serviced later by responsible device class firmware.
         * Each device driver knows when an OUT or IN transaction is ready by
         * checking the buffer ownership bit.
         * An OUT EP should always be owned by SIE until the data is ready.
         * An IN EP should always be owned by CPU until the data is ready.
         *
         * Because of this logic, it is not necessary to save the USTAT value
         * of non-EP0 transactions.
         */
        UIRbits.TRNIF = 0;
    }//end if(UIRbits.TRNIF && UIEbits.TRNIE)
    
}//end USBDriverService

/******************************************************************************
 * Function:        void USBSuspend(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        
 *
 * Note:            None
 *****************************************************************************/
void USBSuspend(void)
{
    /*
     * NOTE: Do not clear UIRbits.ACTVIF here!
     * Reason:
     * ACTVIF is only generated once an IDLEIF has been generated.
     * This is a 1:1 ratio interrupt generation.
     * For every IDLEIF, there will be only one ACTVIF regardless of
     * the number of subsequent bus transitions.
     *
     * If the ACTIF is cleared here, a problem could occur when:
     * [       IDLE       ][bus activity ->
     * <--- 3 ms ----->     ^
     *                ^     ACTVIF=1
     *                IDLEIF=1
     *  #           #           #           #   (#=Program polling flags)
     *                          ^
     *                          This polling loop will see both
     *                          IDLEIF=1 and ACTVIF=1.
     *                          However, the program services IDLEIF first
     *                          because ACTIVIE=0.
     *                          If this routine clears the only ACTIVIF,
     *                          then it can never get out of the suspend
     *                          mode.             
     */
    UIEbits.ACTVIE = 1;                     // Enable bus activity interrupt
    UIRbits.IDLEIF = 0;
    UCONbits.SUSPND = 1;                    // Put USB module in power conserve
                                            // mode, SIE clock inactive
    /*
     * At this point the PIC can go into sleep,idle, or
     * switch to a slower clock, etc.
     */
    
    /* Modifiable Section */
    PIR2bits.USBIF = 0;
    PIE2bits.USBIE = 1;                     // Set USB wakeup source
    Sleep();                                // Goto sleep
    PIE2bits.USBIE = 0;
    /* End Modifiable Section */

}//end USBSuspend

/******************************************************************************
 * Function:        void USBWakeFromSuspend(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        
 *
 * Note:            None
 *****************************************************************************/
void USBWakeFromSuspend(void)
{
    /* 
     * If using clock switching, this is the place to restore the
     * original clock frequency.
     */
    UCONbits.SUSPND = 0;
    UIEbits.ACTVIE = 0;
    UIRbits.ACTVIF = 0;
}//end USBWakeFromSuspend

/******************************************************************************
 * Function:        void USBRemoteWakeup(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This function should be called by user when the device
 *                  is waken up by an external stimulus other than ACTIVIF.
 *                  Please read the note below to understand the limitations.
 *
 * Note:            The modifiable section in this routine should be changed
 *                  to meet the application needs. Current implementation
 *                  temporary blocks other functions from executing for a
 *                  period of 1-13 ms depending on the core frequency.
 *
 *                  According to USB 2.0 specification section 7.1.7.7,
 *                  "The remote wakeup device must hold the resume signaling
 *                  for at lest 1 ms but for no more than 15 ms."
 *                  The idea here is to use a delay counter loop, using a
 *                  common value that would work over a wide range of core
 *                  frequencies.
 *                  That value selected is 1800. See table below:
 *                  ==========================================================
 *                  Core Freq(MHz)      MIP         RESUME Signal Period (ms)
 *                  ==========================================================
 *                      48              12          1.05
 *                       4              1           12.6
 *                  ==========================================================
 *                  * These timing could be incorrect when using code
 *                    optimization or extended instruction mode,
 *                    or when having other interrupts enabled.
 *                    Make sure to verify using the MPLAB SIM's Stopwatch
 *****************************************************************************/
void USBRemoteWakeup(void)
{
    static word delay_count;
    
    if(usb_stat.RemoteWakeup == 1)          // Check if RemoteWakeup function
    {                                       // has been enabled by the host.
        USBWakeFromSuspend();               // Unsuspend USB modue
        UCONbits.RESUME = 1;                // Start RESUME signaling

        /* Modifiable Section */
        
        delay_count = 1800U;                // Set RESUME line for 1-13 ms
        do
        {
            delay_count--;
        }while(delay_count);        
        
        /* End Modifiable Section */
        
        UCONbits.RESUME = 0;
    }//endif 
}//end USBRemoteWakeup

/******************************************************************************
 * Function:        void USB_SOF_Handler(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        The USB host sends out a SOF packet to full-speed devices
 *                  every 1 ms. This interrupt may be useful for isochronous
 *                  pipes. End designers should implement callback routine
 *                  as necessary.
 *
 * Note:            None
 *****************************************************************************/
void USB_SOF_Handler(void)
{
    /* Callback routine here */
    
    UIRbits.SOFIF = 0;
}//end USB_SOF_Handler

/******************************************************************************
 * Function:        void USBStallHandler(void)
 *
 * PreCondition:    A STALL packet is sent to the host by the SIE.
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        The STALLIF is set anytime the SIE sends out a STALL
 *                  packet regardless of which endpoint causes it.
 *                  A Setup transaction overrides the STALL function. A stalled
 *                  endpoint stops stalling once it receives a setup packet.
 *                  In this case, the SIE will accepts the Setup packet and
 *                  set the TRNIF flag to notify the firmware. STALL function
 *                  for that particular endpoint pipe will be automatically
 *                  disabled (direction specific).
 *
 *                  There are a few reasons for an endpoint to be stalled.
 *                  1. When a non-supported USB request is received.
 *                     Example: GET_DESCRIPTOR(DEVICE_QUALIFIER)
 *                  2. When an endpoint is currently halted.
 *                  3. When the device class specifies that an endpoint must
 *                     stall in response to a specific event.
 *                     Example: Mass Storage Device Class
 *                              If the CBW is not valid, the device shall
 *                              STALL the Bulk-In pipe.
 *                              See USB Mass Storage Class Bulk-only Transport
 *                              Specification for more details.
 *
 * Note:            UEPn.EPSTALL can be scanned to see which endpoint causes
 *                  the stall event.
 *                  If
 *****************************************************************************/
void USBStallHandler(void)
{
    /*
     * Does not really have to do anything here,
     * even for the control endpoint.
     * All BDs of Endpoint 0 are owned by SIE right now,
     * but once a Setup Transaction is received, the ownership
     * for EP0_OUT will be returned to CPU.
     * When the Setup Transaction is serviced, the ownership
     * for EP0_IN will then be forced back to CPU by firmware.
     */
    if(UEP0bits.EPSTALL == 1)
    {
        USBPrepareForNextSetupTrf();        // Firmware work-around
        UEP0bits.EPSTALL = 0;
    }
    UIRbits.STALLIF = 0;
}//end USBStallHandler

/******************************************************************************
 * Function:        void USBErrorHandler(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        The purpose of this interrupt is mainly for debugging
 *                  during development. Check UEIR to see which error causes
 *                  the interrupt.
 *
 * Note:            None
 *****************************************************************************/
void USBErrorHandler(void)
{
    UIRbits.UERRIF = 0;
}//end USBErrorHandler

/******************************************************************************
 * Function:        void USBProtocolResetHandler(void)
 *
 * PreCondition:    A USB bus reset is received from the host.
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    Currently, this routine flushes any pending USB
 *                  transactions. It empties out the USTAT FIFO. This action
 *                  might not be desirable in some applications.
 *
 * Overview:        Once a USB bus reset is received from the host, this
 *                  routine should be called. It resets the device address to
 *                  zero, disables all non-EP0 endpoints, initializes EP0 to
 *                  be ready for default communication, clears all USB
 *                  interrupt flags, unmasks applicable USB interrupts, and
 *                  reinitializes internal state-machine variables.
 *
 * Note:            None
 *****************************************************************************/
void USBProtocolResetHandler(void)
{
    UEIR = 0;                       // Clear all USB error flags
    UIR = 0;                        // Clears all USB interrupts
    UEIE = 0b10011111;              // Unmask all USB error interrupts
    UIE = 0b01111011;               // Enable all interrupts except ACTVIE
    
    UADDR = 0x00;                   // Reset to default address
    mDisableEP1to15();              // Reset all non-EP0 UEPn registers
    UEP0 = EP_CTRL|HSHK_EN;         // Init EP0 as a Ctrl EP, see usbdrv.h

    while(UIRbits.TRNIF == 1)       // Flush any pending transactions
        UIRbits.TRNIF = 0;

    UCONbits.PKTDIS = 0;            // Make sure packet processing is enabled
    USBPrepareForNextSetupTrf();    // Declared in usbctrltrf.c
    
    usb_stat.RemoteWakeup = 0;      // Default status flag to disable
    usb_active_cfg = 0;             // Clear active configuration
    usb_device_state = DEFAULT_STATE;
}//end USBProtocolResetHandler


/* Auxiliary Function */
void ClearArray(byte* startAdr,byte count)
{
    *startAdr;
    while(count)
    {
        _asm
        clrf POSTINC0,0
        _endasm
        count--;
    }//end while
}//end ClearArray

/** EOF usbdrv.c *************************************************************/

/////////////////////////////usbdsc.c
/** C O N S T A N T S ************************************************/
#pragma romdata

/* Device Descriptor */
rom USB_DEV_DSC device_dsc=
{    
    sizeof(USB_DEV_DSC),    // Size of this descriptor in bytes
    DSC_DEV,                // DEVICE descriptor type
    0x0200,                 // USB Spec Release Number in BCD format
    CDC_DEVICE,             // Class Code
    0x00,                   // Subclass code
    0x00,                   // Protocol code
    EP0_BUFF_SIZE,          // Max packet size for EP0, see usbcfg.h
    0x04D8,                 // Vendor ID
    0x000A,                 // Product ID: CDC RS-232 Emulation Demo
    0x0000,                 // Device release number in BCD format
    0x01,                   // Manufacturer string index
    0x02,                   // Product string index
    0x00,                   // Device serial number string index
    0x01                    // Number of possible configurations
};

/* Configuration 1 Descriptor */
CFG01=
{
    /* Configuration Descriptor */
    sizeof(USB_CFG_DSC),    // Size of this descriptor in bytes
    DSC_CFG,                // CONFIGURATION descriptor type
    sizeof(cfg01),          // Total length of data for this cfg
    2,                      // Number of interfaces in this cfg
    1,                      // Index value of this configuration
    0,                      // Configuration string index
    _DEFAULT,               // Attributes, see usbdefs_std_dsc.h
    50,                     // Max power consumption (2X mA)
    
    /* Interface Descriptor */
    sizeof(USB_INTF_DSC),   // Size of this descriptor in bytes
    DSC_INTF,               // INTERFACE descriptor type
    0,                      // Interface Number
    0,                      // Alternate Setting Number
    1,                      // Number of endpoints in this intf
    COMM_INTF,              // Class code
    ABSTRACT_CONTROL_MODEL, // Subclass code
    V25TER,                 // Protocol code
    0,                      // Interface string index

    /* CDC Class-Specific Descriptors */
    sizeof(USB_CDC_HEADER_FN_DSC),CS_INTERFACE,DSC_FN_HEADER,0x0110,
    sizeof(USB_CDC_ACM_FN_DSC),CS_INTERFACE,DSC_FN_ACM,0x02,
    sizeof(USB_CDC_UNION_FN_DSC),CS_INTERFACE,DSC_FN_UNION,CDC_COMM_INTF_ID,CDC_DATA_INTF_ID,
    sizeof(USB_CDC_CALL_MGT_FN_DSC),CS_INTERFACE,DSC_FN_CALL_MGT,0x00,CDC_DATA_INTF_ID,

    /* Endpoint Descriptor */
    sizeof(USB_EP_DSC),DSC_EP,_EP02_IN,_INT,CDC_INT_EP_SIZE,0x02,
    
    /* Interface Descriptor */
    sizeof(USB_INTF_DSC),   // Size of this descriptor in bytes
    DSC_INTF,               // INTERFACE descriptor type
    1,                      // Interface Number
    0,                      // Alternate Setting Number
    2,                      // Number of endpoints in this intf
    DATA_INTF,              // Class code
    0,                      // Subclass code
    NO_PROTOCOL,            // Protocol code
    0,                      // Interface string index
    
    /* Endpoint Descriptors */
    sizeof(USB_EP_DSC),DSC_EP,_EP03_OUT,_BULK,CDC_BULK_OUT_EP_SIZE,0x00,
    sizeof(USB_EP_DSC),DSC_EP,_EP03_IN,_BULK,CDC_BULK_IN_EP_SIZE,0x00
};

rom struct{byte bLength;byte bDscType;word string[1];}sd000={
sizeof(sd000),DSC_STR,0x0409};

rom struct{byte bLength;byte bDscType;word string[25];}sd001={
sizeof(sd001),DSC_STR,
'M','i','c','r','o','c','h','i','p',' ',
'T','e','c','h','n','o','l','o','g','y',' ','I','n','c','.'};

rom struct{byte bLength;byte bDscType;word string[25];}sd002={
sizeof(sd002),DSC_STR,
'C','D','C',' ','R','S','-','2','3','2',' ',
'E','m','u','l','a','t','i','o','n',' ','D','e','m','o'};

rom const unsigned char *rom USB_CD_Ptr[]={&cfg01,&cfg01};
rom const unsigned char *rom USB_SD_Ptr[]={&sd000,&sd001,&sd002};

rom pFunc ClassReqHandler[1]=
{
    &USBCheckCDCRequest
};

#pragma code

/** EOF usbdsc.c ****************************************************/

