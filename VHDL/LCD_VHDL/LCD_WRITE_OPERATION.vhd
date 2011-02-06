-- Squelette pour l'exercice Compteur
library IEEE;
use IEEE.Std_logic_1164.all;
use IEEE.Numeric_std.all;

entity Lcd_Write_Operation is
  port (Clock, Reset: in Std_logic;
        Data:   inout std_logic_vector(7 downto 0);
		RS: out std_logic;
		RW: out std_logic;
		EN: out std_logic;
		RS_Value: in std_logic;
		Data_Value: in std_logic_vector(7 downto 0);
		Done: out std_logic;		
		LED: OUT std_logic_vector(8 DOWNTO 0);
		IsWrite: in std_logic
		);
end entity Lcd_Write_Operation;


architecture archi_Lcd_Write_Operation of Lcd_Write_Operation is

TYPE STATE_TYPE IS (
		Idle, 
		Waiting_40ns,
		Write_Data,
		Waiting_230ns,
		Disable,
		Waiting_10ns,
	--	Waiting_4_1ms_Before_End_Write,
		End_Write 
   );
 
   -- Declare current and next state signals
   SIGNAL current_state : STATE_TYPE;
   SIGNAL next_state : STATE_TYPE;

   signal counter_10ns_finish: std_logic;
   signal counter_10ns_reset: std_logic;

   signal counter_40ns_finish: std_logic;
   signal counter_40ns_reset: std_logic;

   signal counter_230ns_finish: std_logic;
   signal counter_230ns_reset: std_logic;

   signal counter_4_1ms_finish: std_logic;
   signal counter_4_1ms_reset: std_logic;




BEGIN

	clocked_proc :process(clock, reset)--, isWrite)
	begin

		if  reset = '1' then
			counter_10ns_reset 	 <= '1';
			counter_40ns_reset	 <= '1';
			counter_230ns_reset	 <= '1';
			counter_4_1ms_reset	 <= '1';
			current_state 		 <= Idle;	
			RS 	 <= not RS_Value;
			RW 	 <= '1';	
			EN 	 <= '0';	
			Done <= '0';
			LED <= "100000000";
			
		elsif Rising_edge(clock) then
			current_state <= next_state;
			
			case current_state is

				when Idle =>
					RS 	 <= RS_Value;					
					if isWrite = '1' then				
						RW 	 <= '0';						
						Data <= "00000000";
					else		
						RW 	 <= '1';				
					end if;
					EN 	 <= '0';	
					counter_10ns_reset 	 <= '1';
					counter_40ns_reset	 <= '0';
					counter_230ns_reset	 <= '1';
					counter_4_1ms_reset	 <= '1';
					Done <= '0';
					LED <= "000000001";
								
					
				when Waiting_40ns =>			
					RS 	 <= RS_Value;				
					if isWrite = '1' then				
						RW 	 <= '0';						
						Data <= "00000000";
					else		
						RW 	 <= '1';				
					end if;
					EN 	 <= '0';		
					counter_10ns_reset 	 <= '1';
					counter_40ns_reset	 <= '0';
					counter_230ns_reset	 <= '1';
					counter_4_1ms_reset	 <= '1';
					Done <= '0';
					LED <= "000000010";
					
				when Write_Data =>				
					if isWrite = '1' then				
						RW 	 <= '0';						
						Data <= Data_Value;
					else		
						RW 	 <= '1';				
					end if;
					RS 	 <= RS_Value;
					EN 	 <= '1';
					counter_10ns_reset 	 <= '1';
					counter_40ns_reset	 <= '1';
					counter_230ns_reset	 <= '0';
					counter_4_1ms_reset	 <= '1';
					Done <= '0';
					LED <= "000000100";
					
				when Waiting_230ns =>		
					if isWrite = '1' then				
						RW 	 <= '0';						
						Data <= Data_Value;
					else		
						RW 	 <= '1';				
					end if;
					RS 	 <= RS_Value;
					EN 	 <= '1';		
					counter_10ns_reset 	 <= '1';
					counter_40ns_reset	 <= '1';
					counter_230ns_reset	 <= '0';
					counter_4_1ms_reset	 <= '1';
					Done <= '0';
					LED <= "000001000";
					
				when Disable =>		
					if isWrite = '1' then				
						RW 	 <= '0';						
						Data <= Data_Value;
					else		
						RW 	 <= '1';				
					end if;
					RS 	 <= RS_Value;
					EN 	 <= '0';		
					counter_10ns_reset 	 <= '0';
					counter_40ns_reset	 <= '1';
					counter_230ns_reset	 <= '1';
					counter_4_1ms_reset	 <= '1';
					Done <= '0';
					LED <= "000010000";
					
				when Waiting_10ns =>		
					if isWrite = '1' then				
						RW 	 <= '0';						
						Data <= Data_Value;
					else		
						RW 	 <= '1';				
					end if;
					RS 	 <= RS_Value;
					EN 	 <= '0';		
					counter_10ns_reset 	 <= '0';
					counter_40ns_reset	 <= '1';
					counter_230ns_reset	 <= '1';
					counter_4_1ms_reset	 <= '1';
					Done <= '0';
					LED <= "000100000";
--			
--				when Waiting_4_1ms_Before_End_Write =>
--					if isWrite = '1' then				
--						RW 	 <= '0';						
--						Data <= Data_Value;
--					else		
--						RW 	 <= '1';				
--					end if;
--					RS 	 <= RS_Value;
--					EN 	 <= '0';		
--					counter_10ns_reset 	 <= '1';
--					counter_40ns_reset	 <= '1';
--					counter_230ns_reset	 <= '1';
--					counter_4_1ms_reset	 <= '0';
--					Done <= '0';
--					LED <= "000100000";
										
				when End_Write =>		
					if isWrite = '1' then				
						RW 	 <= '0';						
						Data <= Data_Value;
					else		
						RW 	 <= '0';				
					end if;
					RS 	 <= RS_Value;
					EN 	 <= '0';		
					Data <= "00000000";
					counter_10ns_reset 	 <= '1';
					counter_40ns_reset	 <= '1';
					counter_230ns_reset	 <= '1';
					counter_4_1ms_reset	 <= '1';
					Done <= '1';
					LED <= "001000000";
				
	
				end case;
		end if;
	end process clocked_proc;


	-----------------------------------------------------------------
	nextstate_proc : PROCESS ( 
	  current_state,
	  counter_10ns_finish,
	  counter_40ns_finish,
	  counter_230ns_finish,
	  counter_4_1ms_finish
	)
	-----------------------------------------------------------------

	 BEGIN
		CASE current_state IS
			
			WHEN Idle =>
				next_state <= Waiting_40ns;


			WHEN Waiting_40ns =>	
				if counter_40ns_finish = '1' then
					next_state <= Write_Data;				
				else
					next_state <= Waiting_40ns;
				end if;
				
			WHEN Write_Data =>				
				next_state <= Waiting_230ns;				
			
			WHEN Waiting_230ns =>
				if counter_230ns_finish = '1' then
					next_state <= Disable;				
				else
					next_state <= Waiting_230ns;
				end if;
				
			WHEN Disable =>															
				next_state <= Waiting_10ns;			
			
			WHEN Waiting_10ns =>
				if counter_10ns_finish = '1' then
					next_state <= End_Write;				
				else
					next_state <= Waiting_10ns;
				end if;
			
--			WHEN Waiting_4_1ms_Before_End_Write =>
--				if counter_4_1ms_finish = '1' then
--					next_state <= End_Write;				
--				else
--					next_state <= Waiting_4_1ms_Before_End_Write;
--				end if;	
				
			WHEN End_Write =>
					next_state <= End_Write;	
	
		END CASE;

	END PROCESS nextstate_proc;
	
	

	COUNTER_10_ns: entity WORK.counter_10ns port map(	Clock  => Clock, 
														Reset  => counter_10ns_reset,
														finish => counter_10ns_finish);
														
	COUNTER_40_ns: entity WORK.counter_40ns port map(	Clock  => Clock, 
														Reset  => counter_40ns_reset,
														finish => counter_40ns_finish);
														
	COUNTER_230_ns: entity WORK.counter_230ns port map(	Clock  => Clock, 
														Reset  => counter_230ns_reset,
														finish => counter_230ns_finish);
														
	COUNTER_4_1_ms: entity WORK.counter_4_1ms port map(	Clock  => Clock, 
														Reset  => counter_4_1ms_reset,
														finish => counter_4_1ms_finish);
 
 
	
	
end architecture archi_Lcd_Write_Operation;
