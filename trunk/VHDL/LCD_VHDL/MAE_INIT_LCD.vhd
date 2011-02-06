-- Squelette pour l'exercice Compteur
library IEEE;
use IEEE.Std_logic_1164.all;
use IEEE.Numeric_std.all;

entity Mae_Init_Lcd is
  port (Clock, Reset: in Std_logic;
        Data:   inout std_logic_vector(7 downto 0);
		RS: out std_logic;
		RW: out std_logic;
		EN: out std_logic;
		LED: OUT std_logic_vector(17 DOWNTO 0);
		CONTROLE: in std_logic_vector(1 DOWNTO 0);
		LEDG: OUT std_logic_vector(8 DOWNTO 0));
end entity Mae_Init_Lcd;


architecture archi_Mae_Init_Lcd of Mae_Init_Lcd is

TYPE STATE_TYPE IS (
      Idle,
      Waiting_15ms,
      First_Init_Instruction,
      Waiting_4_1ms,
      Second_Init_Instruction,
      Waiting_100us,
      Third_Init_Instruction,
	  Wait_BF_before_Set_Lines_Font,
	  Check_BF_before_Set_Lines_Font,
	  Set_Lines_Font,
	  Wait_BF_before_Display_Off,
	  Check_BF_before_Display_Off,
	  Display_Off,
	  Wait_BF_before_Display_Clear,
	  Check_BF_before_Display_Clear,
	  Display_Clear,
	  Wait_BF_before_Entry_Mode_Set,
	  Check_BF_before_Entry_Mode_Set,
	  Entry_mode_set,
	  Wait_before_Display_character,
	  display_character,
      End_initialization
   );
 
   -- Declare current and next state signals
   SIGNAL current_state : STATE_TYPE;
   SIGNAL next_state : STATE_TYPE;

   signal RS_int: std_logic;
   signal RW_int: std_logic;
   signal EN_int: std_logic;

   signal counter_15ms_finish: std_logic;
   signal counter_15ms_reset: std_logic;

   signal counter_4_1ms_finish: std_logic;
   signal counter_4_1ms_reset: std_logic;

   signal counter_100us_finish: std_logic;
   signal counter_100us_reset: std_logic;

   signal data_Value: std_logic_vector(7 DOWNTO 0);
   signal rs_Value: std_logic;
   signal Read_Write_done: std_logic;
   signal write_operation_reset: std_logic;

   signal data_Read: std_logic_vector(7 DOWNTO 0);
   signal read_operation_reset: std_logic;

	signal IsWrite: std_logic;

BEGIN

	clocked_proc :process(clock, reset, CONTROLE)
	begin

		if  reset = '1' then
			counter_15ms_reset 	 <= '1';
			counter_4_1ms_reset <= '1';
			counter_100us_reset <= '1';
			
			write_operation_reset <= '1';
			rs_Value <= '0';
			data_Value <= "00000000";
			
			current_state <= Idle;
			LED <= "111000000000000000";
			IsWrite <= '1';
			
		elsif Rising_edge(clock) then
			current_state <= next_state;
			
			case current_state is

				when Idle =>
					counter_15ms_reset 	<= '0';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';
					
					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00000000";
					
					LED <= "000000000000000011";
					IsWrite <= '1';
					
				when Waiting_15ms =>		
					counter_15ms_reset 	<= '0';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';
					
					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00000000";
					
					LED <= "000000000000000110";
					IsWrite <= '1';
					
				when First_Init_Instruction =>	
				
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';
					
					
					write_operation_reset <= '0';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
			
					LED <= "000000000000001100";
					IsWrite <= '1';
					
					
				WHEN Waiting_4_1ms =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '0';
					counter_100us_reset <= '1';

					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00000000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000000011000";
					IsWrite <= '1';


				when Second_Init_Instruction =>

					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000000110000";
					IsWrite <= '1';
				
				when Waiting_100us =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '0';

					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00000000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000001100000";
					IsWrite <= '1';

					
				when Third_Init_Instruction =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '1';
				
--			 when Wait_before_Set_Lines_Font =>
--			 
--					counter_15ms_reset 	<= '0';
--					counter_4_1ms_reset <= '1';
--					counter_100us_reset <= '1';
--
--					write_operation_reset <= '1';
--					rs_Value <= '0';
--					data_Value <= "00000000"; -- only 4 first digits count (4 lasts aren't read)
--					LED <= "000000000001100000";
--					IsWrite <= '1';
					
				when	Wait_BF_before_Set_Lines_Font =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '0';
					
				when Check_BF_before_Set_Lines_Font =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '1';
												
				 when Set_Lines_Font =>				
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_value <= "00111100"; -- DB4:interface data length  (1= 8-bit, 0 = 4-bit)
											  -- DB3: line display (1 = 2lines, 0 = 1line)
											  -- DB2: display font (1 = 5*11 dots, 0 = 5*8 do
											  
					LED <= "000000001100000000";
					IsWrite <= '1';
					
				when	Wait_BF_before_Display_Off =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '0';
					
				when Check_BF_before_Display_Off =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '1';
					
					
			

	
				 when Display_Off =>				
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_value <= "00001111"; -- DB4:interface data length  (1= 8-bit, 0 = 4-bit)
											  -- DB3: line display (1 = 2lines, 0 = 1line)
											  -- DB2: display font (1 = 5*11 dots, 0 = 5*8 dots)
					LED <= "000001100000000000";
					IsWrite <= '1';
					
	
					
				when	Wait_BF_before_Display_Clear =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '0';
					
				when Check_BF_before_Display_Clear =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '1';
				
										
				 when Display_Clear =>				
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_value <= "00000001"; -- DB4:interface data length  (1= 8-bit, 0 = 4-bit)
											  -- DB3: line display (1 = 2lines, 0 = 1line)
											  -- DB2: display font (1 = 5*11 dots, 0 = 5*8 dots)
					LED <= "001100000000000000";
					IsWrite <= '1';
						
					
				
				when	Wait_BF_before_Entry_Mode_Set =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '0';
					
				when Check_BF_before_Entry_Mode_Set =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '1';
				
					
				 when Entry_Mode_Set =>				
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_value <= "00000010"; -- DB4:interface data length  (1= 8-bit, 0 = 4-bit)
											  -- DB3: line display (1 = 2lines, 0 = 1line)
											  -- DB2: display font (1 = 5*11 dots, 0 = 5*8 dots)
					LED <= "011000000000000110";
					IsWrite <= '1';
					
				when Wait_before_Display_character =>
					counter_15ms_reset 	<= '0';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00000000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000011000000000";
					IsWrite <= '1';
							
				when display_character =>
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '1';
					data_value <= "01010011"; -- DB4:interface data length  (1= 8-bit, 0 = 4-bit)
											  -- DB3: line display (1 = 2lines, 0 = 1line)
											  -- DB2: display font (1 = 5*11 dots, 0 = 5*8 dots)
					LED <= "011000000000000110";
					IsWrite <= '1';
					
				when End_initialization =>
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					
					
					LED <= "111111111111111111";
					IsWrite <= '1';
				
	
				when Wait_BF_before_Operation =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '0';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '0';
					
				when Check_BF_before_Operation =>
					
					counter_15ms_reset 	<= '1';
					counter_4_1ms_reset <= '1';
					counter_100us_reset <= '1';

					write_operation_reset <= '1';
					rs_Value <= '0';
					data_Value <= "00110000"; -- only 4 first digits count (4 lasts aren't read)
					LED <= "000000000011000000";
					IsWrite <= '1';

				when Operation =>
					if CONTROLE = "00" then			-- display character
				
					elsif CONTROLE = "01" then		-- clear display
				
					elsif CONTROLE = "10" then		-- Return Home
				
					else 							-- Go to second line
				
					end if;
				
				
--				
				end case;
		end if;
	end process clocked_proc;


	-----------------------------------------------------------------
	nextstate_proc : PROCESS ( 
	  current_state,
	  counter_15ms_finish,
	  counter_4_1ms_finish,
	  counter_100us_finish,
	  Read_Write_done
	)
	-----------------------------------------------------------------

	 BEGIN
		CASE current_state IS
			
			WHEN Idle =>
				next_state <= Waiting_15ms;

				
			WHEN Waiting_15ms =>
				if counter_15ms_finish = '1' then
					next_state <= First_Init_Instruction;
				else				
					next_state <= Waiting_15ms;
				end if;
				
				
			WHEN First_Init_Instruction =>
				if Read_Write_done ='1' then
					next_state <= Waiting_4_1ms;
				else
					next_state <= First_Init_Instruction;
				end if;
				 
				 
			WHEN Waiting_4_1ms =>
				if counter_4_1ms_finish = '1' then
					next_state <= Second_Init_Instruction;
				else				
					next_state <= Waiting_4_1ms;
				end if;
				
				
			WHEN Second_Init_Instruction =>
				if Read_Write_done ='1' then
					next_state <= Waiting_100us;
				else
					next_state <= Second_Init_Instruction;
				end if;
				 
				 
			WHEN Waiting_100us =>
				if counter_100us_finish = '1' then
					next_state <= Third_Init_Instruction;
				else				
					next_state <= Waiting_100us;
				end if;
				
			WHEN Third_Init_Instruction =>
				if Read_Write_done ='1' then
					next_state <= Wait_BF_before_Set_Lines_Font;
				else
					next_state <= Third_Init_Instruction;
				end if;
												
			when Wait_BF_before_Set_Lines_Font =>
				if Read_Write_done ='1' then
					next_state <= Check_BF_before_Set_Lines_Font;
				else				
					next_state <= Wait_BF_before_Set_Lines_Font;
				end if;
												
			when Check_BF_before_Set_Lines_Font =>
				if Data(7) ='0' then
					next_state <= Set_Lines_Font;
				else				
					next_state <= Wait_BF_before_Set_Lines_Font;
				end if;
						
--									
--			when Wait_before_Set_Lines_Font =>
--				if counter_15ms_finish = '1' then
--					next_state <= Set_Lines_Font;
--				else				
--					next_state <= Wait_before_Set_Lines_Font;
--				end if;
				
			WHEN Set_Lines_Font =>
				if Read_Write_done ='1' then
					next_state <= Wait_BF_before_Display_Off;
				else
					next_state <= Set_Lines_Font;
				end if;			
			
												
			when Wait_BF_before_Display_Off =>
				if Read_Write_done ='1' then
					next_state <= Check_BF_before_Display_Off;
				else				
					next_state <= Wait_BF_before_Display_Off;
				end if;
												
			when Check_BF_before_Display_Off =>
				if Data(7) ='0' then
					next_state <= Display_Off;
				else				
					next_state <= Wait_BF_before_Display_Off;
				end if;
						

			WHEN Display_Off =>
				if Read_Write_done ='1' then
					next_state <= Wait_BF_before_Display_Clear;
				else
					next_state <= Display_Off;
				end if;	
			
												
			when Wait_BF_before_Display_Clear =>
				if Read_Write_done ='1' then
					next_state <= Check_BF_before_Display_Clear;
				else				
					next_state <= Wait_BF_before_Display_Clear;
				end if;
												
			when Check_BF_before_Display_Clear =>
				if Data(7) ='0' then
					next_state <= Display_Clear;
				else				
					next_state <= Wait_BF_before_Display_Clear;
				end if;

				
			WHEN Display_Clear =>
				if Read_Write_done ='1' then
					next_state <= Wait_BF_before_Entry_Mode_Set;
				else
					next_state <= Display_Clear;
				end if;
				
																
			when Wait_BF_before_Entry_Mode_Set =>
				if Read_Write_done ='1' then
					next_state <= Check_BF_before_Entry_Mode_Set;
				else				
					next_state <= Wait_BF_before_Entry_Mode_Set;
				end if;
												
			when Check_BF_before_Entry_Mode_Set =>
				if Data(7) ='0' then
					next_state <= Entry_Mode_Set;
				else				
					next_state <= Wait_BF_before_Entry_Mode_Set;
				end if;
						
				
			WHEN Entry_Mode_Set =>
				if Read_Write_done ='1' then
					next_state <= Wait_before_Display_character;
				else
					next_state <= Entry_Mode_Set;
				end if;
			
			WHEN Wait_before_Display_character => 
				if counter_15ms_finish = '1' then
					next_state <= display_character;
				else				
					next_state <= Wait_before_Display_character;
				end if;
				
			WHEN display_character =>
				if Read_Write_done ='1' then
					next_state <= Wait_before_Display_character;
				else
					next_state <= display_character;
				end if;
							
		
			WHEN End_initialization =>
					next_state <= End_initialization;
			
			
		--	WHEN others =>
			
		END CASE;

	END PROCESS nextstate_proc;
	
		

	
	WRITE_OPERATION:  entity WORK.Lcd_Write_Operation port map(	Clock  => Clock, 
																Reset  => write_operation_reset,
																Done => Read_Write_done,
																RS_Value => rs_Value,
																Data_Value => data_Value,
																RS  => RS,
																RW => RW,
																EN => EN,
																LED => LEDG,
																DATA => DATA,
																IsWrite => IsWrite
																);
	
--	
--	READ_OPERATION:  entity WORK.Lcd_Read_Operation port map(	Clock  => Clock, 
--																Reset  => read_operation_reset,
--																Done => Read_done,
--																RS_Value => rs_Value,
--																Data_Read => Data_Read,
--																RS  => RS_INT,
--																RW => RW_INT,
--																EN => EN_INT,
--																DATA => DATA);
	
	COUNTER_15_ms: entity WORK.counter_15ms port map(	Clock  => Clock, 
														Reset  => counter_15ms_reset,
														finish => counter_15ms_finish);
 

	COUNTER_100_us: entity WORK.counter_100us port map(	Clock  => Clock, 
														Reset  => counter_100us_reset,
														finish => counter_100us_finish);
																	
	COUNTER_4_1_ms: entity WORK.counter_4_1ms port map(	Clock  => Clock, 
														Reset  => counter_4_1ms_reset,
														finish => counter_4_1ms_finish);
	
	
end architecture archi_Mae_Init_Lcd;
