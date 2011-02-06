-- Squelette pour l'exercice Compteur
library IEEE;
use IEEE.Std_logic_1164.all;
use IEEE.Numeric_std.all;

entity Counter_40ns is
  port (Clock, Reset: in Std_logic;
        finish:   out std_logic);
end entity Counter_40ns;

architecture archi_Counter_40ns of Counter_40ns is
  signal count : integer range 0 to + 100;

begin
  process(reset, Clock)
  begin
    if reset = '1' then
      count <= 0;
	  finish <= '0';
    elsif rising_edge(Clock) then
	  count <= count + 1;
	  if count = 3 then
		finish <= '1';
	--  else		
	--	finish <= '0';
	  end if;
    end if;
  end process;
end architecture archi_Counter_40ns;

