/*********************************************************************
 *
 *                Microchip USB C18 Firmware Version 1.0
 *
 *********************************************************************
 * FileName:        usb.h
 * Dependencies:    See INCLUDES section below
 * Processor:       PIC18
 * Compiler:        C18 2.30.01+
 * Company:         Microchip Technology, Inc.
 *
 * Software License Agreement
 *
 * The software supplied herewith by Microchip Technology Incorporated
 * (the “Company”) for its PICmicro® Microcontroller is intended and
 * supplied to you, the Company’s customer, for use solely and
 * exclusively on Microchip PICmicro Microcontroller products. The
 * software is owned by the Company and/or its supplier, and is
 * protected under applicable copyright laws. All rights are reserved.
 * Any use in violation of the foregoing restrictions may subject the
 * user to criminal sanctions under applicable laws, as well as to
 * civil liability for the breach of the terms and conditions of this
 * license.
 *
 * THIS SOFTWARE IS PROVIDED IN AN “AS IS” CONDITION. NO WARRANTIES,
 * WHETHER EXPRESS, IMPLIED OR STATUTORY, INCLUDING, BUT NOT LIMITED
 * TO, IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE APPLY TO THIS SOFTWARE. THE COMPANY SHALL NOT,
 * IN ANY CIRCUMSTANCES, BE LIABLE FOR SPECIAL, INCIDENTAL OR
 * CONSEQUENTIAL DAMAGES, FOR ANY REASON WHATSOEVER.
 *
 * Author               Date        Comment
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Rawin Rojvanit       11/19/04    Original.
 ********************************************************************/
#ifndef USB_H
#define USB_H

//definition des broches pour l'entree Vusb
#define tris_usb_bus_sense  TRISDbits.TRISD3    // Input
#define usb_bus_sense       PORTDbits.RD3

/*
 * usb.h provides a centralize way to include all files
 * required by Microchip USB Firmware.
 *
 * The order of inclusion is important.
 * Dependency conflicts are resolved by the correct ordering.
 */

#ifndef TYPEDEFS_H
#define TYPEDEFS_H

typedef unsigned char   byte;           // 8-bit
typedef unsigned int    word;           // 16-bit
typedef unsigned long   dword;          // 32-bit

typedef union _BYTE
{
    byte _byte;
    struct
    {
        unsigned b0:1;
        unsigned b1:1;
        unsigned b2:1;
        unsigned b3:1;
        unsigned b4:1;
        unsigned b5:1;
        unsigned b6:1;
        unsigned b7:1;
    };
} BYTE;

typedef union _WORD
{
    word _word;
    struct
    {
        byte byte0;
        byte byte1;
    };
    struct
    {
        BYTE Byte0;
        BYTE Byte1;
    };
    struct
    {
        BYTE LowB;
        BYTE HighB;
    };
    struct
    {
        byte v[2];
    };
} WORD;
#define LSB(a)      ((a).v[0])
#define MSB(a)      ((a).v[1])

typedef union _DWORD
{
    dword _dword;
    struct
    {
        byte byte0;
        byte byte1;
        byte byte2;
        byte byte3;
    };
    struct
    {
        word word0;
        word word1;
    };
    struct
    {
        BYTE Byte0;
        BYTE Byte1;
        BYTE Byte2;
        BYTE Byte3;
    };
    struct
    {
        WORD Word0;
        WORD Word1;
    };
    struct
    {
        byte v[4];
    };
} DWORD;
#define LOWER_LSB(a)    ((a).v[0])
#define LOWER_MSB(a)    ((a).v[1])
#define UPPER_LSB(a)    ((a).v[2])
#define UPPER_MSB(a)    ((a).v[3])

typedef void(*pFunc)(void);

typedef union _POINTER
{
    struct
    {
        byte bLow;
        byte bHigh;
        //byte bUpper;
    };
    word _word;                         // bLow & bHigh
    
    //pFunc _pFunc;                       // Usage: ptr.pFunc(); Init: ptr.pFunc = &<Function>;

    byte* bRam;                         // Ram byte pointer: 2 bytes pointer pointing
                                        // to 1 byte of data
    word* wRam;                         // Ram word poitner: 2 bytes poitner pointing
                                        // to 2 bytes of data

    rom byte* bRom;                     // Size depends on compiler setting
    rom word* wRom;
    //rom near byte* nbRom;               // Near = 2 bytes pointer
    //rom near word* nwRom;
    //rom far byte* fbRom;                // Far = 3 bytes pointer
    //rom far word* fwRom;
} POINTER;

typedef enum _BOOL { FALSE = 0, TRUE } BOOL;

#define OK      TRUE
#define FAIL    FALSE

#endif //TYPEDEFS_H



#ifndef USBCFG_H
#define USBCFG_H

/** D E F I N I T I O N S *******************************************/
#define EP0_BUFF_SIZE           8   // 8, 16, 32, or 64
#define MAX_NUM_INT             1   // For tracking Alternate Setting

/* Parameter definitions are defined in usbdrv.h */
#define MODE_PP                 _PPBM0
#define UCFG_VAL                _PUEN|_TRINT|_FS|MODE_PP

#define USE_SELF_POWER_SENSE_IO
#define USE_USB_BUS_SENSE_IO

/** D E V I C E  C L A S S  U S A G E *******************************/
#define USB_USE_CDC

/*
 * MUID = Microchip USB Class ID
 * Used to identify which of the USB classes owns the current
 * session of control transfer over EP0
 */
#define MUID_NULL               0
#define MUID_USB9               1
#define MUID_HID                2
#define MUID_CDC                3
#define MUID_MSD                4

/** E N D P O I N T S  A L L O C A T I O N **************************/
/*
 * See usbmmap.c for an explanation of how the endpoint allocation works
 */

/* CDC */
#define CDC_COMM_INTF_ID        0x00
#define CDC_COMM_UEP            UEP2
#define CDC_INT_BD_IN           ep2Bi
#define CDC_INT_EP_SIZE         8

#define CDC_DATA_INTF_ID        0x01
#define CDC_DATA_UEP            UEP3
#define CDC_BULK_BD_OUT         ep3Bo
#define CDC_BULK_OUT_EP_SIZE    64
#define CDC_BULK_BD_IN          ep3Bi
#define CDC_BULK_IN_EP_SIZE     64

#define MAX_EP_NUMBER           3           // UEP3

#endif //USBCFG_H

#ifndef USBDEFS_STD_DSC_H
#define USBDEFS_STD_DSC_H

/** D E F I N I T I O N S ****************************************************/

/* Descriptor Types */
#define DSC_DEV     0x01
#define DSC_CFG     0x02
#define DSC_STR     0x03
#define DSC_INTF    0x04
#define DSC_EP      0x05

/******************************************************************************
 * USB Endpoint Definitions
 * USB Standard EP Address Format: DIR:X:X:X:EP3:EP2:EP1:EP0
 * This is used in the descriptors. See autofiles\usbdsc.c
 * 
 * NOTE: Do not use these values for checking against USTAT.
 * To check against USTAT, use values defined in "system\usb\usbdrv\usbdrv.h"
 *****************************************************************************/
#define _EP01_OUT   0x01
#define _EP01_IN    0x81
#define _EP02_OUT   0x02
#define _EP02_IN    0x82
#define _EP03_OUT   0x03
#define _EP03_IN    0x83
#define _EP04_OUT   0x04
#define _EP04_IN    0x84
#define _EP05_OUT   0x05
#define _EP05_IN    0x85
#define _EP06_OUT   0x06
#define _EP06_IN    0x86
#define _EP07_OUT   0x07
#define _EP07_IN    0x87
#define _EP08_OUT   0x08
#define _EP08_IN    0x88
#define _EP09_OUT   0x09
#define _EP09_IN    0x89
#define _EP10_OUT   0x0A
#define _EP10_IN    0x8A
#define _EP11_OUT   0x0B
#define _EP11_IN    0x8B
#define _EP12_OUT   0x0C
#define _EP12_IN    0x8C
#define _EP13_OUT   0x0D
#define _EP13_IN    0x8D
#define _EP14_OUT   0x0E
#define _EP14_IN    0x8E
#define _EP15_OUT   0x0F
#define _EP15_IN    0x8F

/* Configuration Attributes */
#define _DEFAULT    0x01<<7         //Default Value (Bit 7 is set)
#define _SELF       0x01<<6         //Self-powered (Supports if set)
#define _RWU        0x01<<5         //Remote Wakeup (Supports if set)

/* Endpoint Transfer Type */
#define _CTRL       0x00            //Control Transfer
#define _ISO        0x01            //Isochronous Transfer
#define _BULK       0x02            //Bulk Transfer
#define _INT        0x03            //Interrupt Transfer

/* Isochronous Endpoint Synchronization Type */
#define _NS         0x00<<2         //No Synchronization
#define _AS         0x01<<2         //Asynchronous
#define _AD         0x02<<2         //Adaptive
#define _SY         0x03<<2         //Synchronous

/* Isochronous Endpoint Usage Type */
#define _DE         0x00<<4         //Data endpoint
#define _FE         0x01<<4         //Feedback endpoint
#define _IE         0x02<<4         //Implicit feedback Data endpoint


/** S T R U C T U R E ********************************************************/

/******************************************************************************
 * USB Device Descriptor Structure
 *****************************************************************************/
typedef struct _USB_DEV_DSC
{
    byte bLength;       byte bDscType;      word bcdUSB;
    byte bDevCls;       byte bDevSubCls;    byte bDevProtocol;
    byte bMaxPktSize0;  word idVendor;      word idProduct;
    word bcdDevice;     byte iMFR;          byte iProduct;
    byte iSerialNum;    byte bNumCfg;
} USB_DEV_DSC;

/******************************************************************************
 * USB Configuration Descriptor Structure
 *****************************************************************************/
typedef struct _USB_CFG_DSC
{
    byte bLength;       byte bDscType;      word wTotalLength;
    byte bNumIntf;      byte bCfgValue;     byte iCfg;
    byte bmAttributes;  byte bMaxPower;
} USB_CFG_DSC;

/******************************************************************************
 * USB Interface Descriptor Structure
 *****************************************************************************/
typedef struct _USB_INTF_DSC
{
    byte bLength;       byte bDscType;      byte bIntfNum;
    byte bAltSetting;   byte bNumEPs;       byte bIntfCls;
    byte bIntfSubCls;   byte bIntfProtocol; byte iIntf;
} USB_INTF_DSC;

/******************************************************************************
 * USB Endpoint Descriptor Structure
 *****************************************************************************/
typedef struct _USB_EP_DSC
{
    byte bLength;       byte bDscType;      byte bEPAdr;
    byte bmAttributes;  word wMaxPktSize;   byte bInterval;
} USB_EP_DSC;

#endif //USBDEFS_STD_DSC_H

/******************************************************************************
 * USB Definitions: Endpoint 0 Buffer
 *****************************************************************************/
#ifndef USBDEFS_EP0_BUFF_H
#define USBDEFS_EP0_BUFF_H

/******************************************************************************
 * CTRL_TRF_SETUP:
 *
 * Every setup packet has 8 bytes.
 * However, the buffer size has to equal the EP0_BUFF_SIZE value specified
 * in autofiles\usbcfg.h
 * The value of EP0_BUFF_SIZE can be 8, 16, 32, or 64.
 *
 * First 8 bytes are defined to be directly addressable to improve speed
 * and reduce code size.
 * Bytes beyond the 8th byte have to be accessed using indirect addressing.
 *****************************************************************************/
typedef union _CTRL_TRF_SETUP
{
    /** Array for indirect addressing ****************************************/
    struct
    {
        byte _byte[EP0_BUFF_SIZE];
    };
    
    /** Standard Device Requests *********************************************/
    struct
    {
        byte bmRequestType;
        byte bRequest;    
        word wValue;
        word wIndex;
        word wLength;
    };
    struct
    {
        unsigned :8;
        unsigned :8;
        WORD W_Value;
        WORD W_Index;
        WORD W_Length;
    };
    struct
    {
        unsigned Recipient:5;           //Device,Interface,Endpoint,Other
        unsigned RequestType:2;         //Standard,Class,Vendor,Reserved
        unsigned DataDir:1;             //Host-to-device,Device-to-host
        unsigned :8;
        byte bFeature;                  //DEVICE_REMOTE_WAKEUP,ENDPOINT_HALT
        unsigned :8;
        unsigned :8;
        unsigned :8;
        unsigned :8;
        unsigned :8;
    };
    struct
    {
        unsigned :8;
        unsigned :8;
        byte bDscIndex;                 //For Configuration and String DSC Only
        byte bDscType;                  //Device,Configuration,String
        word wLangID;                   //Language ID
        unsigned :8;
        unsigned :8;
    };
    struct
    {
        unsigned :8;
        unsigned :8;
        BYTE bDevADR;                   //Device Address 0-127
        byte bDevADRH;                  //Must equal zero
        unsigned :8;
        unsigned :8;
        unsigned :8;
        unsigned :8;
    };
    struct
    {
        unsigned :8;
        unsigned :8;
        byte bCfgValue;                 //Configuration Value 0-255
        byte bCfgRSD;                   //Must equal zero (Reserved)
        unsigned :8;
        unsigned :8;
        unsigned :8;
        unsigned :8;
    };
    struct
    {
        unsigned :8;
        unsigned :8;
        byte bAltID;                    //Alternate Setting Value 0-255
        byte bAltID_H;                  //Must equal zero
        byte bIntfID;                   //Interface Number Value 0-255
        byte bIntfID_H;                 //Must equal zero
        unsigned :8;
        unsigned :8;
    };
    struct
    {
        unsigned :8;
        unsigned :8;
        unsigned :8;
        unsigned :8;
        byte bEPID;                     //Endpoint ID (Number & Direction)
        byte bEPID_H;                   //Must equal zero
        unsigned :8;
        unsigned :8;
    };
    struct
    {
        unsigned :8;
        unsigned :8;
        unsigned :8;
        unsigned :8;
        unsigned EPNum:4;               //Endpoint Number 0-15
        unsigned :3;
        unsigned EPDir:1;               //Endpoint Direction: 0-OUT, 1-IN
        unsigned :8;
        unsigned :8;
        unsigned :8;
    };
    /** End: Standard Device Requests ****************************************/
    
} CTRL_TRF_SETUP;

/******************************************************************************
 * CTRL_TRF_DATA:
 *
 * Buffer size has to equal the EP0_BUFF_SIZE value specified
 * in autofiles\usbcfg.h
 * The value of EP0_BUFF_SIZE can be 8, 16, 32, or 64.
 *
 * First 8 bytes are defined to be directly addressable to improve speed
 * and reduce code size.
 * Bytes beyond the 8th byte have to be accessed using indirect addressing.
 *****************************************************************************/
typedef union _CTRL_TRF_DATA
{
    /** Array for indirect addressing ****************************************/
    struct
    {
        byte _byte[EP0_BUFF_SIZE];
    };
    
    /** First 8-byte direct addressing ***************************************/
    struct
    {
        byte _byte0;
        byte _byte1;
        byte _byte2;
        byte _byte3;
        byte _byte4;
        byte _byte5;
        byte _byte6;
        byte _byte7;
    };
    struct
    {
        word _word0;
        word _word1;
        word _word2;
        word _word3;
    };

} CTRL_TRF_DATA;

#endif //USBDEFS_EP0_BUFF_H
//////////////////USBMAP
#ifndef USBMMAP_H
#define USBMMAP_H

/* Buffer Descriptor Status Register Initialization Parameters */
#define _BSTALL     0x04                //Buffer Stall enable
#define _DTSEN      0x08                //Data Toggle Synch enable
#define _INCDIS     0x10                //Address increment disable
#define _KEN        0x20                //SIE keeps buff descriptors enable
#define _DAT0       0x00                //DATA0 packet expected next
#define _DAT1       0x40                //DATA1 packet expected next
#define _DTSMASK    0x40                //DTS Mask
#define _USIE       0x80                //SIE owns buffer
#define _UCPU       0x00                //CPU owns buffer

/* USB Device States - To be used with [byte usb_device_state] */
#define DETACHED_STATE          0
#define ATTACHED_STATE          1
#define POWERED_STATE           2
#define DEFAULT_STATE           3
#define ADR_PENDING_STATE       4
#define ADDRESS_STATE           5
#define CONFIGURED_STATE        6

/* Memory Types for Control Transfer - used in USB_DEVICE_STATUS */
#define _RAM 0
#define _ROM 1

/** T Y P E S ****************************************************************/
typedef union _USB_DEVICE_STATUS
{
    byte _byte;
    struct
    {
        unsigned RemoteWakeup:1;// [0]Disabled [1]Enabled: See usbdrv.c,usb9.c
        unsigned ctrl_trf_mem:1;// [0]RAM      [1]ROM
    };
} USB_DEVICE_STATUS;

typedef union _BD_STAT
{
    byte _byte;
    struct{
        unsigned BC8:1;
        unsigned BC9:1;
        unsigned BSTALL:1;              //Buffer Stall Enable
        unsigned DTSEN:1;               //Data Toggle Synch Enable
        unsigned INCDIS:1;              //Address Increment Disable
        unsigned KEN:1;                 //BD Keep Enable
        unsigned DTS:1;                 //Data Toggle Synch Value
        unsigned UOWN:1;                //USB Ownership
    };
    struct{
        unsigned BC8:1;
        unsigned BC9:1;
        unsigned PID0:1;
        unsigned PID1:1;
        unsigned PID2:1;
        unsigned PID3:1;
        unsigned :1;
        unsigned UOWN:1;
    };
    struct{
        unsigned :2;
        unsigned PID:4;                 //Packet Identifier
        unsigned :2;
    };
} BD_STAT;                              //Buffer Descriptor Status Register

typedef union _BDT
{
    struct
    {
        BD_STAT Stat;
        byte Cnt;
        byte ADRL;                      //Buffer Address Low
        byte ADRH;                      //Buffer Address High
    };
    struct
    {
        unsigned :8;
        unsigned :8;
        byte* ADR;                      //Buffer Address
    };
} BDT;                                  //Buffer Descriptor Table

/** E X T E R N S ************************************************************/
extern byte usb_device_state;
extern USB_DEVICE_STATUS usb_stat;
extern byte usb_active_cfg;
extern byte usb_alt_intf[MAX_NUM_INT];

extern volatile far BDT ep0Bo;          //Endpoint #0 BD Out
extern volatile far BDT ep0Bi;          //Endpoint #0 BD In
extern volatile far BDT ep1Bo;          //Endpoint #1 BD Out
extern volatile far BDT ep1Bi;          //Endpoint #1 BD In
extern volatile far BDT ep2Bo;          //Endpoint #2 BD Out
extern volatile far BDT ep2Bi;          //Endpoint #2 BD In
extern volatile far BDT ep3Bo;          //Endpoint #3 BD Out
extern volatile far BDT ep3Bi;          //Endpoint #3 BD In
extern volatile far BDT ep4Bo;          //Endpoint #4 BD Out
extern volatile far BDT ep4Bi;          //Endpoint #4 BD In
extern volatile far BDT ep5Bo;          //Endpoint #5 BD Out
extern volatile far BDT ep5Bi;          //Endpoint #5 BD In
extern volatile far BDT ep6Bo;          //Endpoint #6 BD Out
extern volatile far BDT ep6Bi;          //Endpoint #6 BD In
extern volatile far BDT ep7Bo;          //Endpoint #7 BD Out
extern volatile far BDT ep7Bi;          //Endpoint #7 BD In
extern volatile far BDT ep8Bo;          //Endpoint #8 BD Out
extern volatile far BDT ep8Bi;          //Endpoint #8 BD In
extern volatile far BDT ep9Bo;          //Endpoint #9 BD Out
extern volatile far BDT ep9Bi;          //Endpoint #9 BD In
extern volatile far BDT ep10Bo;         //Endpoint #10 BD Out
extern volatile far BDT ep10Bi;         //Endpoint #10 BD In
extern volatile far BDT ep11Bo;         //Endpoint #11 BD Out
extern volatile far BDT ep11Bi;         //Endpoint #11 BD In
extern volatile far BDT ep12Bo;         //Endpoint #12 BD Out
extern volatile far BDT ep12Bi;         //Endpoint #12 BD In
extern volatile far BDT ep13Bo;         //Endpoint #13 BD Out
extern volatile far BDT ep13Bi;         //Endpoint #13 BD In
extern volatile far BDT ep14Bo;         //Endpoint #14 BD Out
extern volatile far BDT ep14Bi;         //Endpoint #14 BD In
extern volatile far BDT ep15Bo;         //Endpoint #15 BD Out
extern volatile far BDT ep15Bi;         //Endpoint #15 BD In

extern volatile far CTRL_TRF_SETUP SetupPkt;
extern volatile far CTRL_TRF_DATA CtrlTrfData;

#if defined(USB_USE_CDC)
extern volatile far unsigned char cdc_notice[CDC_INT_EP_SIZE];
extern volatile far unsigned char cdc_data_rx[CDC_BULK_OUT_EP_SIZE];
extern volatile far unsigned char cdc_data_tx[CDC_BULK_IN_EP_SIZE];
#endif

#endif //USBMMAP_H



#ifndef USBDRV_H
#define USBDRV_H

/** D E F I N I T I O N S ****************************************************/

/* UCFG Initialization Parameters */
#define _PPBM0      0x00            // Pingpong Buffer Mode 0
#define _PPBM1      0x01            // Pingpong Buffer Mode 1
#define _PPBM2      0x02            // Pingpong Buffer Mode 2
#define _LS         0x00            // Use Low-Speed USB Mode
#define _FS         0x04            // Use Full-Speed USB Mode
#define _TRINT      0x00            // Use internal transceiver
#define _TREXT      0x08            // Use external transceiver
#define _PUEN       0x10            // Use internal pull-up resistor
#define _OEMON      0x40            // Use SIE output indicator
#define _UTEYE      0x80            // Use Eye-Pattern test

/* UEPn Initialization Parameters */
#define EP_CTRL     0x06            // Cfg Control pipe for this ep
#define EP_OUT      0x0C            // Cfg OUT only pipe for this ep
#define EP_IN       0x0A            // Cfg IN only pipe for this ep
#define EP_OUT_IN   0x0E            // Cfg both OUT & IN pipes for this ep
#define HSHK_EN     0x10            // Enable handshake packet
                                    // Handshake should be disable for isoch

/******************************************************************************
 * USB - PICmicro Endpoint Definitions
 * PICmicro EP Address Format: X:EP3:EP2:EP1:EP0:DIR:PPBI:X
 * This is used when checking the value read from USTAT
 *
 * NOTE: These definitions are not used in the descriptors.
 * EP addresses used in the descriptors have different format and
 * are defined in: "usbdefs_std_dsc.h"
 *****************************************************************************/
#define OUT         0
#define IN          1

#define PIC_EP_NUM_MASK 0b01111000
#define PIC_EP_DIR_MASK 0b00000100

#define EP00_OUT    ((0x00<<3)|(OUT<<2))
#define EP00_IN     ((0x00<<3)|(IN<<2))
#define EP01_OUT    ((0x01<<3)|(OUT<<2))
#define EP01_IN     ((0x01<<3)|(IN<<2))
#define EP02_OUT    ((0x02<<3)|(OUT<<2))
#define EP02_IN     ((0x02<<3)|(IN<<2))
#define EP03_OUT    ((0x03<<3)|(OUT<<2))
#define EP03_IN     ((0x03<<3)|(IN<<2))
#define EP04_OUT    ((0x04<<3)|(OUT<<2))
#define EP04_IN     ((0x04<<3)|(IN<<2))
#define EP05_OUT    ((0x05<<3)|(OUT<<2))
#define EP05_IN     ((0x05<<3)|(IN<<2))
#define EP06_OUT    ((0x06<<3)|(OUT<<2))
#define EP06_IN     ((0x06<<3)|(IN<<2))
#define EP07_OUT    ((0x07<<3)|(OUT<<2))
#define EP07_IN     ((0x07<<3)|(IN<<2))
#define EP08_OUT    ((0x08<<3)|(OUT<<2))
#define EP08_IN     ((0x08<<3)|(IN<<2))
#define EP09_OUT    ((0x09<<3)|(OUT<<2))
#define EP09_IN     ((0x09<<3)|(IN<<2))
#define EP10_OUT    ((0x0A<<3)|(OUT<<2))
#define EP10_IN     ((0x0A<<3)|(IN<<2))
#define EP11_OUT    ((0x0B<<3)|(OUT<<2))
#define EP11_IN     ((0x0B<<3)|(IN<<2))
#define EP12_OUT    ((0x0C<<3)|(OUT<<2))
#define EP12_IN     ((0x0C<<3)|(IN<<2))
#define EP13_OUT    ((0x0D<<3)|(OUT<<2))
#define EP13_IN     ((0x0D<<3)|(IN<<2))
#define EP14_OUT    ((0x0E<<3)|(OUT<<2))
#define EP14_IN     ((0x0E<<3)|(IN<<2))
#define EP15_OUT    ((0x0F<<3)|(OUT<<2))
#define EP15_IN     ((0x0F<<3)|(IN<<2))

/******************************************************************************
 * Macro:           void mInitializeUSBDriver(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        Configures the USB module, definition of UCFG_VAL can be
 *                  found in usbcfg.h
 *
 *                  This register determines: USB Speed, On-chip pull-up
 *                  resistor selection, On-chip tranceiver selection, bus
 *                  eye pattern generation mode, Ping-pong buffering mode
 *                  selection.
 *
 * Note:            None
 *****************************************************************************/
#define mInitializeUSBDriver()      {UCFG = UCFG_VAL;                       \
                                     usb_device_state = DETACHED_STATE;     \
                                     usb_stat._byte = 0x00;                 \
                                     usb_active_cfg = 0x00;}

/******************************************************************************
 * Macro:           void mDisableEP1to15(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This macro disables all endpoints except EP0.
 *                  This macro should be called when the host sends a RESET
 *                  signal or a SET_CONFIGURATION request.
 *
 * Note:            None
 *****************************************************************************/
#define mDisableEP1to15()       ClearArray((byte*)&UEP1,15);
/*
#define mDisableEP1to15()       UEP1=0x00;UEP2=0x00;UEP3=0x00;\
                                UEP4=0x00;UEP5=0x00;UEP6=0x00;UEP7=0x00;\
                                UEP8=0x00;UEP9=0x00;UEP10=0x00;UEP11=0x00;\
                                UEP12=0x00;UEP13=0x00;UEP14=0x00;UEP15=0x00;
*/

/******************************************************************************
 * Macro:           void mUSBBufferReady(buffer_dsc)
 *
 * PreCondition:    IN Endpoint: Buffer is loaded and ready to be sent.
 *                  OUT Endpoint: Buffer is free to be written to by SIE.
 *
 * Input:           byte buffer_dsc: Root name of the buffer descriptor group.
 *                  i.e. ep0Bo, ep1Bi, ... Declared in usbmmap.c
 *                  Names can be remapped for readability, see examples in
 *                  usbcfg.h (#define HID_BD_OUT      ep1Bo)
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This macro should be called each time after:
 *                  1. A non-EP0 IN endpoint buffer is populated with data.
 *                  2. A non-EP0 OUT endpoint buffer is read.
 *                  This macro turns the buffer ownership to SIE for servicing.
 *                  It also toggles the DTS bit for synchronization.
 *
 * Note:            None
 *****************************************************************************/
#define mUSBBufferReady(buffer_dsc)                                         \
{                                                                           \
    buffer_dsc.Stat._byte &= _DTSMASK;          /* Save only DTS bit */     \
    buffer_dsc.Stat.DTS = !buffer_dsc.Stat.DTS; /* Toggle DTS bit    */     \
    buffer_dsc.Stat._byte |= _USIE|_DTSEN;      /* Turn ownership to SIE */ \
}

/** T Y P E S ****************************************************************/

/** E X T E R N S ************************************************************/

/** P U B L I C  P R O T O T Y P E S *****************************************/
void USBCheckBusStatus(void);
void USBDriverService(void);
void USBRemoteWakeup(void);
void USBSoftDetach(void); 

void ClearArray(byte* startAdr,byte count);
#endif //USBDRV_H

#ifndef USBCTRLTRF_H
#define USBCTRLTRF_H

/** D E F I N I T I O N S ****************************************************/

/* Control Transfer States */
#define WAIT_SETUP          0
#define CTRL_TRF_TX         1
#define CTRL_TRF_RX         2

/* USB PID: Token Types - See chapter 8 in the USB specification */
#define SETUP_TOKEN         0b00001101
#define OUT_TOKEN           0b00000001
#define IN_TOKEN            0b00001001

/* bmRequestType Definitions */
#define HOST_TO_DEV         0
#define DEV_TO_HOST         1

#define STANDARD            0x00
#define CLASS               0x01
#define VENDOR              0x02

#define RCPT_DEV            0
#define RCPT_INTF           1
#define RCPT_EP             2
#define RCPT_OTH            3

/** E X T E R N S ************************************************************/
extern byte ctrl_trf_session_owner;

extern POINTER pSrc;
extern POINTER pDst;
extern WORD wCount;

/** P U B L I C  P R O T O T Y P E S *****************************************/
void USBCtrlEPService(void);
void USBCtrlTrfTxService(void);
void USBCtrlTrfRxService(void);
void USBCtrlEPServiceComplete(void);
void USBPrepareForNextSetupTrf(void);


#endif //USBCTRLTRF_H

#ifndef USB9_H
#define USB9_H

/** D E F I N I T I O N S ****************************************************/

/******************************************************************************
 * Standard Request Codes
 * USB 2.0 Spec Ref Table 9-4
 *****************************************************************************/
#define GET_STATUS  0
#define CLR_FEATURE 1
#define SET_FEATURE 3
#define SET_ADR     5
#define GET_DSC     6
#define SET_DSC     7
#define GET_CFG     8
#define SET_CFG     9
#define GET_INTF    10
#define SET_INTF    11
#define SYNCH_FRAME 12

/* Standard Feature Selectors */
#define DEVICE_REMOTE_WAKEUP    0x01
#define ENDPOINT_HALT           0x00

/******************************************************************************
 * Macro:           void mUSBCheckAdrPendingState(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        Specialized checking routine, it checks if the device
 *                  is in the ADDRESS PENDING STATE and services it if it is.
 *
 * Note:            None
 *****************************************************************************/
#define mUSBCheckAdrPendingState()  if(usb_device_state==ADR_PENDING_STATE) \
                                    {                                       \
                                        UADDR = SetupPkt.bDevADR._byte;     \
                                        if(UADDR > 0)                       \
                                            usb_device_state=ADDRESS_STATE; \
                                        else                                \
                                            usb_device_state=DEFAULT_STATE; \
                                    }//end if

/** E X T E R N S ************************************************************/

/** P U B L I C  P R O T O T Y P E S *****************************************/
void USBCheckStdRequest(void);

#endif //USB9_H


#if defined(USB_USE_CDC)
#ifndef CDC_H
#define CDC_H

/** D E F I N I T I O N S ****************************************************/

/* Class-Specific Requests */
#define SEND_ENCAPSULATED_COMMAND   0x00
#define GET_ENCAPSULATED_RESPONSE   0x01
#define SET_COMM_FEATURE            0x02
#define GET_COMM_FEATURE            0x03
#define CLEAR_COMM_FEATURE          0x04
#define SET_LINE_CODING             0x20
#define GET_LINE_CODING             0x21
#define SET_CONTROL_LINE_STATE      0x22
#define SEND_BREAK                  0x23

/* Notifications *
 * Note: Notifications are polled over
 * Communication Interface (Interrupt Endpoint)
 */
#define NETWORK_CONNECTION          0x00
#define RESPONSE_AVAILABLE          0x01
#define SERIAL_STATE                0x20


/* Device Class Code */
#define CDC_DEVICE                  0x02

/* Communication Interface Class Code */
#define COMM_INTF                   0x02

/* Communication Interface Class SubClass Codes */
#define ABSTRACT_CONTROL_MODEL      0x02

/* Communication Interface Class Control Protocol Codes */
#define V25TER                      0x01    // Common AT commands ("Hayes(TM)")


/* Data Interface Class Codes */
#define DATA_INTF                   0x0A

/* Data Interface Class Protocol Codes */
#define NO_PROTOCOL                 0x00    // No class specific protocol required


/* Communication Feature Selector Codes */
#define ABSTRACT_STATE              0x01
#define COUNTRY_SETTING             0x02

/* Functional Descriptors */
/* Type Values for the bDscType Field */
#define CS_INTERFACE                0x24
#define CS_ENDPOINT                 0x25

/* bDscSubType in Functional Descriptors */
#define DSC_FN_HEADER               0x00
#define DSC_FN_CALL_MGT             0x01
#define DSC_FN_ACM                  0x02    // ACM - Abstract Control Management
#define DSC_FN_DLM                  0x03    // DLM - Direct Line Managment
#define DSC_FN_TELEPHONE_RINGER     0x04
#define DSC_FN_RPT_CAPABILITIES     0x05
#define DSC_FN_UNION                0x06
#define DSC_FN_COUNTRY_SELECTION    0x07
#define DSC_FN_TEL_OP_MODES         0x08
#define DSC_FN_USB_TERMINAL         0x09
/* more.... see Table 25 in USB CDC Specification 1.1 */

/* CDC Bulk IN transfer states */
#define CDC_TX_READY                0
#define CDC_TX_BUSY                 1
#define CDC_TX_BUSY_ZLP             2       // ZLP: Zero Length Packet
#define CDC_TX_COMPLETING           3

/******************************************************************************
 * Macro:           BOOL mUSBUSARTIsTxTrfReady(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This macro is used to check if the CDC class is ready
 *                  to send more data.
 *                  Typical Usage: if(mUSBUSARTIsTxTrfReady())
 *
 * Note:            None
 *****************************************************************************/
#define mUSBUSARTIsTxTrfReady()     (cdc_trf_state == CDC_TX_READY)

/******************************************************************************
 * Macro:           (bit) mCDCUsartRxIsBusy(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This macro is used to check if CDC bulk OUT endpoint is
 *                  busy (owned by SIE) or not.
 *                  Typical Usage: if(mCDCUsartRxIsBusy())
 *
 * Note:            None
 *****************************************************************************/
#define mCDCUsartRxIsBusy()         CDC_BULK_BD_OUT.Stat.UOWN

/******************************************************************************
 * Macro:           (bit) mCDCUsartTxIsBusy(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        This macro is used to check if CDC bulk IN endpoint is
 *                  busy (owned by SIE) or not.
 *                  Typical Usage: if(mCDCUsartTxIsBusy())
 *
 * Note:            None
 *****************************************************************************/
#define mCDCUsartTxIsBusy()         CDC_BULK_BD_IN.Stat.UOWN

/******************************************************************************
 * Macro:           byte mCDCGetRxLength(void)
 *
 * PreCondition:    None
 *
 * Input:           None
 *
 * Output:          mCDCGetRxLength returns cdc_rx_len
 *
 * Side Effects:    None
 *
 * Overview:        mCDCGetRxLength is used to retrieve the number of bytes
 *                  copied to user's buffer by the most recent call to
 *                  getsUSBUSART function.
 *
 * Note:            None
 *****************************************************************************/
#define mCDCGetRxLength()           cdc_rx_len

/******************************************************************************
 * Macro:           void mUSBUSARTTxRam(byte *pData, byte len)
 *
 * PreCondition:    cdc_trf_state must be in the CDC_TX_READY state.
 *                  
 *                  Value of 'len' must be equal to or smaller than 255 bytes.
 *
 * Input:           pDdata  : Pointer to the starting location of data bytes
 *                  len     : Number of bytes to be transferred
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        Use this macro to transfer data located in data memory.
 *                  Use this macro when:
 *                  1. Data stream is not null-terminated
 *                  2. Transfer length is known
 *
 *                  Remember: cdc_trf_state must == CDC_TX_READY
 *                  Unlike putsUSBUSART, there is not code double checking
 *                  the transfer state. Unexpected behavior will occur if
 *                  this function is called when cdc_trf_state != CDC_TX_READY
 *
 * Note:            This macro only handles the setup of the transfer. The
 *                  actual transfer is handled by CDCTxService().
 *****************************************************************************/
#define mUSBUSARTTxRam(pData,len)   \
{                                   \
    pCDCSrc.bRam = pData;           \
    cdc_tx_len = len;               \
    cdc_mem_type = _RAM;            \
    cdc_trf_state = CDC_TX_BUSY;    \
}

/******************************************************************************
 * Macro:           void mUSBUSARTTxRom(rom byte *pData, byte len)
 *
 * PreCondition:    cdc_trf_state must be in the CDC_TX_READY state.
 *                  
 *                  Value of 'len' must be equal to or smaller than 255 bytes.
 *
 * Input:           pDdata  : Pointer to the starting location of data bytes
 *                  len     : Number of bytes to be transferred
 *
 * Output:          None
 *
 * Side Effects:    None
 *
 * Overview:        Use this macro to transfer data located in program memory.
 *                  Use this macro when:
 *                  1. Data stream is not null-terminated
 *                  2. Transfer length is known
 *
 *                  Remember: cdc_trf_state must == CDC_TX_READY
 *                  Unlike putrsUSBUSART, there is not code double checking
 *                  the transfer state. Unexpected behavior will occur if
 *                  this function is called when cdc_trf_state != CDC_TX_READY
 *
 * Note:            This macro only handles the setup of the transfer. The
 *                  actual transfer is handled by CDCTxService().
 *****************************************************************************/
#define mUSBUSARTTxRom(pData,len)   \
{                                   \
    pCDCSrc.bRom = pData;           \
    cdc_tx_len = len;               \
    cdc_mem_type = _ROM;            \
    cdc_trf_state = CDC_TX_BUSY;    \
}

/** S T R U C T U R E S ******************************************************/

/* Line Coding Structure */
#define LINE_CODING_LENGTH          0x07

typedef union _LINE_CODING
{
    struct
    {
        byte _byte[LINE_CODING_LENGTH];
    };
    struct
    {
        DWORD   dwDTERate;          // Complex data structure
        byte    bCharFormat;
        byte    bParityType;
        byte    bDataBits;
    };
} LINE_CODING;

typedef union _CONTROL_SIGNAL_BITMAP
{
    byte _byte;
    struct
    {
        unsigned DTE_PRESENT;       // [0] Not Present  [1] Present
        unsigned CARRIER_CONTROL;   // [0] Deactivate   [1] Activate
    };
} CONTROL_SIGNAL_BITMAP;


/* Functional Descriptor Structure - See CDC Specification 1.1 for details */

/* Header Functional Descriptor */
typedef struct _USB_CDC_HEADER_FN_DSC
{
    byte bFNLength;
    byte bDscType;
    byte bDscSubType;
    word bcdCDC;
} USB_CDC_HEADER_FN_DSC;

/* Abstract Control Management Functional Descriptor */
typedef struct _USB_CDC_ACM_FN_DSC
{
    byte bFNLength;
    byte bDscType;
    byte bDscSubType;
    byte bmCapabilities;
} USB_CDC_ACM_FN_DSC;

/* Union Functional Descriptor */
typedef struct _USB_CDC_UNION_FN_DSC
{
    byte bFNLength;
    byte bDscType;
    byte bDscSubType;
    byte bMasterIntf;
    byte bSaveIntf0;
} USB_CDC_UNION_FN_DSC;

/* Call Management Functional Descriptor */
typedef struct _USB_CDC_CALL_MGT_FN_DSC
{
    byte bFNLength;
    byte bDscType;
    byte bDscSubType;
    byte bmCapabilities;
    byte bDataInterface;
} USB_CDC_CALL_MGT_FN_DSC;

/** E X T E R N S ************************************************************/
extern byte cdc_rx_len;

extern byte cdc_trf_state;
extern POINTER pCDCSrc;
extern byte cdc_tx_len;
extern byte cdc_mem_type;

/** P U B L I C  P R O T O T Y P E S *****************************************/
void USBCheckCDCRequest(void);
void CDCInitEP(void);
byte getsUSBUSART(char *buffer, byte len);
void putrsUSBUSART(const rom char *data);
void putsUSBUSART(char *data);
void CDCTxService(void);
void usb_init(void);

#endif //CDC_H

#ifndef USBDSC_H
#define USBDSC_H

/** D E F I N I T I O N S *******************************************/

#define CFG01 rom struct                            \
{   USB_CFG_DSC             cd01;                   \
    USB_INTF_DSC            i01a00;                 \
    USB_CDC_HEADER_FN_DSC   cdc_header_fn_i01a00;   \
    USB_CDC_ACM_FN_DSC      cdc_acm_fn_i01a00;      \
    USB_CDC_UNION_FN_DSC    cdc_union_fn_i01a00;    \
    USB_CDC_CALL_MGT_FN_DSC cdc_call_mgt_fn_i01a00; \
    USB_EP_DSC              ep02i_i01a00;           \
    USB_INTF_DSC            i02a00;                 \
    USB_EP_DSC              ep03o_i02a00;           \
    USB_EP_DSC              ep03i_i02a00;           \
} cfg01

/** E X T E R N S ***************************************************/
extern rom USB_DEV_DSC device_dsc;
extern CFG01;
extern rom const unsigned char *rom USB_CD_Ptr[];
extern rom const unsigned char *rom USB_SD_Ptr[];

extern rom pFunc ClassReqHandler[1];

#endif //USBDSC_H






#endif


#endif //USB_H
