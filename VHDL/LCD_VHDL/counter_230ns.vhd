-- Squelette pour l'exercice Compteur
library IEEE;
use IEEE.Std_logic_1164.all;
use IEEE.Numeric_std.all;

entity Counter_230ns is
  port (Clock, Reset: in Std_logic;
        finish:   out std_logic);
end entity Counter_230ns;

architecture archi_Counter_230ns of Counter_230ns is
  signal count : integer range 0 to + 150;

begin
  process(reset, Clock)
  begin
    if reset = '1' then
      count <= 0;
	  finish <= '0';
    elsif rising_edge(Clock) then
	  count <= count + 1;
	  if count = 12 then
		finish <= '1';
	--  else		
	--	finish <= '0';
	  end if;
    end if;
  end process;
end architecture archi_Counter_230ns;

