library IEEE;
use IEEE.std_logic_1164.all;

ENTITY DE2_TOP IS
   PORT (
      --//////////////////////	Clock Input	 	////////////////////////

      CLOCK_27                : IN std_logic;   -- 	27 MHz
      CLOCK_50                : IN std_logic;   -- 	50 MHz
      EXT_CLOCK               : IN std_logic;   -- 	External Clock
      --//////////////////////	Push Button		////////////////////////

      KEY                     : IN std_logic_vector(3 DOWNTO 0);   -- 	Pushbutton[3:0]
      --//////////////////////	DPDT Switch		////////////////////////

      SW                      : IN std_logic_vector(17 DOWNTO 0);   -- 	Toggle Switch[17:0]
      --//////////////////////	7-SEG Dispaly	////////////////////////

      HEX0                    : OUT std_logic_vector(6 DOWNTO 0);   -- 	Seven Segment Digit 0
      HEX1                    : OUT std_logic_vector(6 DOWNTO 0);   -- 	Seven Segment Digit 1
      HEX2                    : OUT std_logic_vector(6 DOWNTO 0);   -- 	Seven Segment Digit 2
      HEX3                    : OUT std_logic_vector(6 DOWNTO 0);   -- 	Seven Segment Digit 3
      HEX4                    : OUT std_logic_vector(6 DOWNTO 0);   -- 	Seven Segment Digit 4
      HEX5                    : OUT std_logic_vector(6 DOWNTO 0);   -- 	Seven Segment Digit 5
      HEX6                    : OUT std_logic_vector(6 DOWNTO 0);   -- 	Seven Segment Digit 6
      HEX7                    : OUT std_logic_vector(6 DOWNTO 0);   -- 	Seven Segment Digit 7
      --//////////////////////////	LED		////////////////////////////

      LEDG                    : OUT std_logic_vector(8 DOWNTO 0);   -- 	LED Green[8:0]
      LEDR                    : OUT std_logic_vector(17 DOWNTO 0);   -- 	LED Red[17:0]
      --//////////////////////////	UART	////////////////////////////

      UART_TXD                : OUT std_logic;   -- 	UART Transmitter
      UART_RXD                : IN std_logic;   -- 	UART Receiver
      --//////////////////////////	IRDA	////////////////////////////

      IRDA_TXD                : OUT std_logic;   -- 	IRDA Transmitter
      IRDA_RXD                : IN std_logic;   -- 	IRDA Receiver
      --/////////////////////		SDRAM Interface	////////////////////////

      DRAM_DQ                 : INOUT std_logic_vector(15 DOWNTO 0);   -- 	SDRAM Data bus 16 Bits
      DRAM_ADDR               : OUT std_logic_vector(11 DOWNTO 0);   -- 	SDRAM Address bus 12 Bits
      DRAM_LDQM               : OUT std_logic;   -- 	SDRAM Low-byte Data Mask 
      DRAM_UDQM               : OUT std_logic;   -- 	SDRAM High-byte Data Mask
      DRAM_WE_N               : OUT std_logic;   -- 	SDRAM Write Enable
      DRAM_CAS_N              : OUT std_logic;   -- 	SDRAM Column Address Strobe
      DRAM_RAS_N              : OUT std_logic;   -- 	SDRAM Row Address Strobe
      DRAM_CS_N               : OUT std_logic;   -- 	SDRAM Chip Select
      DRAM_BA_0               : OUT std_logic;   -- 	SDRAM Bank Address 0
      DRAM_BA_1               : OUT std_logic;   -- 	SDRAM Bank Address 0
      DRAM_CLK                : OUT std_logic;   -- 	SDRAM Clock
      DRAM_CKE                : OUT std_logic;   -- 	SDRAM Clock Enable
      --//////////////////////	Flash Interface	////////////////////////

      FL_DQ                   : INOUT std_logic_vector(7 DOWNTO 0);   -- 	FLASH Data bus 8 Bits
      FL_ADDR                 : OUT std_logic_vector(21 DOWNTO 0);   -- 	FLASH Address bus 22 Bits
      FL_WE_N                 : OUT std_logic;   -- 	FLASH Write Enable
      FL_RST_N                : OUT std_logic;   -- 	FLASH Reset
      FL_OE_N                 : OUT std_logic;   -- 	FLASH Output Enable
      FL_CE_N                 : OUT std_logic;   -- 	FLASH Chip Enable
      --//////////////////////	SRAM Interface	////////////////////////

      SRAM_DQ                 : INOUT std_logic_vector(15 DOWNTO 0);   -- 	SRAM Data bus 16 Bits
      SRAM_ADDR               : OUT std_logic_vector(17 DOWNTO 0);   -- 	SRAM Address bus 18 Bits
      SRAM_UB_N               : OUT std_logic;   -- 	SRAM High-byte Data Mask 
      SRAM_LB_N               : OUT std_logic;   -- 	SRAM Low-byte Data Mask 
      SRAM_WE_N               : OUT std_logic;   -- 	SRAM Write Enable
      SRAM_CE_N               : OUT std_logic;   -- 	SRAM Chip Enable
      SRAM_OE_N               : OUT std_logic;   -- 	SRAM Output Enable
      --//////////////////	ISP1362 Interface	////////////////////////

      OTG_DATA                : INOUT std_logic_vector(15 DOWNTO 0);   -- 	ISP1362 Data bus 16 Bits
      OTG_ADDR                : OUT std_logic_vector(1 DOWNTO 0);   -- 	ISP1362 Address 2 Bits
      OTG_CS_N                : OUT std_logic;   -- 	ISP1362 Chip Select
      OTG_RD_N                : OUT std_logic;   -- 	ISP1362 Write
      OTG_WR_N                : OUT std_logic;   -- 	ISP1362 Read
      OTG_RST_N               : OUT std_logic;   -- 	ISP1362 Reset
      OTG_FSPEED              : OUT std_logic;   -- 	USB Full Speed,	0 = Enable, Z = Disable
      OTG_LSPEED              : OUT std_logic;   -- 	USB Low Speed, 	0 = Enable, Z = Disable
      OTG_INT0                : IN std_logic;   -- 	ISP1362 Interrupt 0
      OTG_INT1                : IN std_logic;   -- 	ISP1362 Interrupt 1
      OTG_DREQ0               : IN std_logic;   -- 	ISP1362 DMA Request 0
      OTG_DREQ1               : IN std_logic;   -- 	ISP1362 DMA Request 1
      OTG_DACK0_N             : OUT std_logic;   -- 	ISP1362 DMA Acknowledge 0
      OTG_DACK1_N             : OUT std_logic;   -- 	ISP1362 DMA Acknowledge 1
      LCD_ON                  : OUT std_logic;   -- 	LCD Power ON/OFF
      LCD_BLON                : OUT std_logic;   -- 	LCD Back Light ON/OFF
      LCD_RW                  : OUT std_logic;   -- 	LCD Read/Write Select, 0 = Write, 1 = Read
      LCD_EN                  : OUT std_logic;   -- 	LCD Enable
      LCD_RS                  : OUT std_logic;   -- 	LCD Command/Data Select, 0 = Command, 1 = Data
      --//////////////////	LCD Module 16X2	////////////////////////////

      LCD_DATA                : INOUT std_logic_vector(7 DOWNTO 0);   -- 	LCD Data bus 8 bits
      --//////////////////	SD Card Interface	////////////////////////

      SD_DAT                  : INOUT std_logic;   -- 	SD Card Data
      SD_DAT3                 : INOUT std_logic;   -- 	SD Card Data 3
      SD_CMD                  : INOUT std_logic;   -- 	SD Card Command Signal
      SD_CLK                  : OUT std_logic;   -- 	SD Card Clock
      --//////////////////	USB JTAG link	////////////////////////////

      TDI                     : IN std_logic;   --  CPLD -> FPGA (data in)
      TCK                     : IN std_logic;   --  CPLD -> FPGA (clk)
      TCS                     : IN std_logic;   --  CPLD -> FPGA (CS)
      TDO                     : OUT std_logic;   --  FPGA -> CPLD (data out)
      --//////////////////////	I2C		////////////////////////////////

      I2C_SDAT                : INOUT std_logic;   -- 	I2C Data
      I2C_SCLK                : OUT std_logic;   -- 	I2C Clock
      --//////////////////////	PS2		////////////////////////////////

      PS2_DAT                 : IN std_logic;   -- 	PS2 Data
      PS2_CLK                 : IN std_logic;   -- 	PS2 Clock
      --//////////////////////	VGA			////////////////////////////

      VGA_CLK                 : OUT std_logic;   -- 	VGA Clock
      VGA_HS                  : OUT std_logic;   -- 	VGA H_SYNC
      VGA_VS                  : OUT std_logic;   -- 	VGA V_SYNC
      VGA_BLANK               : OUT std_logic;   -- 	VGA BLANK
      VGA_SYNC                : OUT std_logic;   -- 	VGA SYNC
      VGA_R                   : OUT std_logic_vector(9 DOWNTO 0);   -- 	VGA Red[9:0]
      VGA_G                   : OUT std_logic_vector(9 DOWNTO 0);   -- 	VGA Green[9:0]
      VGA_B                   : OUT std_logic_vector(9 DOWNTO 0);   -- 	VGA Blue[9:0]
      --//////////////	Ethernet Interface	////////////////////////////

      ENET_DATA               : INOUT std_logic_vector(15 DOWNTO 0);   -- 	DM9000A DATA bus 16Bits
      ENET_CMD                : OUT std_logic;   -- 	DM9000A Command/Data Select, 0 = Command, 1 = Data
      ENET_CS_N               : OUT std_logic;   -- 	DM9000A Chip Select
      ENET_WR_N               : OUT std_logic;   -- 	DM9000A Write
      ENET_RD_N               : OUT std_logic;   -- 	DM9000A Read
      ENET_RST_N              : OUT std_logic;   -- 	DM9000A Reset
      ENET_INT                : IN std_logic;   -- 	DM9000A Interrupt
      ENET_CLK                : OUT std_logic;   -- 	DM9000A Clock 25 MHz
      --//////////////////	Audio CODEC		////////////////////////////

      AUD_ADCLRCK             : INOUT std_logic;   -- 	Audio CODEC ADC LR Clock
      AUD_ADCDAT              : IN std_logic;   -- 	Audio CODEC ADC Data
      AUD_DACLRCK             : INOUT std_logic;   -- 	Audio CODEC DAC LR Clock
      AUD_DACDAT              : OUT std_logic;   -- 	Audio CODEC DAC Data
      AUD_BCLK                : INOUT std_logic;   -- 	Audio CODEC Bit-Stream Clock
      AUD_XCK                 : OUT std_logic;   -- 	Audio CODEC Chip Clock
      --//////////////////	TV Devoder		////////////////////////////

      TD_DATA                 : IN std_logic_vector(7 DOWNTO 0);   -- 	TV Decoder Data bus 8 bits
      TD_HS                   : IN std_logic;   -- 	TV Decoder H_SYNC
      TD_VS                   : IN std_logic;   -- 	TV Decoder V_SYNC
      TD_RESET                : OUT std_logic;   -- 	TV Decoder Reset
      --//////////////////////	GPIO	////////////////////////////////

      GPIO_0                  : INOUT std_logic_vector(35 DOWNTO 0);   -- 	GPIO Connection 0
      GPIO_1                  : INOUT std_logic_vector(35 DOWNTO 0));   -- 	GPIO Connection 1
END ENTITY;

ARCHITECTURE RTL OF DE2_TOP IS

TYPE STATE_TYPE IS (
      Idle,
      Waiting_15ms,
      End_initialization
   );
 
   -- Declare current and next state signals
   SIGNAL current_state : STATE_TYPE;
   SIGNAL next_state : STATE_TYPE;
--
   signal counter_15ms_finish: std_logic;
   signal counter_15ms_reset: std_logic;

begin


 
   --	Turn on all display
   HEX0 <= "0000000" ;
   HEX1 <= "1111111" ;
   HEX2 <= "1111111" ;
   HEX3 <= "1111111" ;
   HEX4 <= "1111111" ;
   HEX5 <= "1111111" ;
   HEX6 <= "1111111" ;
   HEX7 <= "0000100" ;
 --  LEDG <= "111111111" ;
 --  LEDR <= "111111111111111111" ;


   --	All inout port turn to tri-state
   DRAM_DQ <= "ZZZZZZZZZZZZZZZZ" ;
   FL_DQ <= "ZZZZZZZZ" ;
   SRAM_DQ <= "ZZZZZZZZZZZZZZZZ" ;
   OTG_DATA <= "ZZZZZZZZZZZZZZZZ" ;
  -- LCD_DATA <= "ZZZZZZZZ" ;
   SD_DAT <= 'Z' ;
   I2C_SDAT <= 'Z' ;
   ENET_DATA <= "ZZZZZZZZZZZZZZZZ" ;
   AUD_ADCLRCK <= 'Z' ;
   AUD_DACLRCK <= 'Z' ;
   AUD_BCLK <= 'Z' ;
   GPIO_0 <= "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ" ;
   GPIO_1 <= "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ" ;

   LCD_ON 	<= '1' ;   -- 	LCD Power ON/OFF
   LCD_BLON <= '0' ;   -- 	LCD Back Light ON/OFF
   --LCD_EN 	<= '1' ; 
--
----process(CLOCK_50)
----begin
----	if SW(0) = '1' then
----		LEDG <= "111111111";
----	else
----		LEDG <= "000000000";
----	end if;
----end process;
--
--
--
--
----BEGIN
--
--	clocked_proc :process(clock_50, SW(0))
--	begin
--		if  SW(0) = '1' then
--			counter_15ms_reset 	 <= '1';
--			
--			LEDG <= "000000000";
--			LEDR <= "111000000000000000";
--			current_state <= Idle ;
--			
--		elsif Rising_edge(clock_50) then
--			current_state <= next_state;
--			
--			case current_state is
--
--				when Idle =>
--					counter_15ms_reset 	<= '0';					
--					LEDG <= "000000000";
--					LEDR <= "000000000000000011";
--					
--				when Waiting_15ms =>		
--					counter_15ms_reset 	<= '0';				
--					LEDG <= "000000000";
--					LEDR <= "000000000000000011";
--				
--				when End_initialization =>
--					counter_15ms_reset 	<= '1';				
--					LEDG <= "000000000";
--					LEDR <= "000011110000011000";
--				
--				end case;
--		end if;
--	end process clocked_proc;
--
--
--	-----------------------------------------------------------------
--	nextstate_proc : PROCESS ( 
--	  current_state,
--	  counter_15ms_finish
--	)
--	-----------------------------------------------------------------
--
--	 BEGIN
--		CASE current_state IS
--			
--			WHEN Idle =>
--				next_state <= Waiting_15ms;
--
--				
--			WHEN Waiting_15ms =>
--				if counter_15ms_finish = '1' then
--					next_state <= End_initialization;
--				else				
--					next_state <= Waiting_15ms;
--				end if;
--		
--			WHEN End_initialization =>
--					next_state <= End_initialization;
--			
--			
--		--	WHEN others =>
--			
--		END CASE;
--
--	END PROCESS nextstate_proc;
--	
--	


	MAE_INIT_LCD: entity WORK.Mae_Init_Lcd port map(	Clock  => CLOCK_50, 
														Reset  => SW(0),
														RS 	   => LCD_RS,	
														RW 	   => LCD_RW,
														EN     => LCD_EN,
														DATA   => LCD_DATA,
														LED    => LEDR,
														LEDG   =>  LEDG);	
--														
--															
--	COUNTER_15_ms: entity WORK.counter_15ms port map(	Clock  => CLOCK_50, 
--														Reset  => SW(0),
--														Start  => counter_15ms_start,
--														--RAZ  => counter_15ms_raz,
--														finish => counter_15ms_finish);

	COUNTER_15_ms: entity WORK.counter_15ms port map(	Clock  => CLOCK_50, 
														Reset  => counter_15ms_reset,
														--Start  => counter_15ms_reset,
														--RAZ  => counter_15ms_raz,
														finish => counter_15ms_finish);

END ARCHITECTURE;
