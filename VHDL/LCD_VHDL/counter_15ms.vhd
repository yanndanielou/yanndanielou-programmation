-- Squelette pour l'exercice Compteur
library IEEE;
use IEEE.Std_logic_1164.all;
use IEEE.Numeric_std.all;

entity Counter_15ms is
  port (Clock, Reset: in Std_logic;
        finish:   out std_logic);
end entity Counter_15ms;

architecture archi_Counter_15ms of Counter_15ms is
  signal count : integer range 0 to + 750001;

begin
  process(reset, Clock)
  begin
    if reset = '1' then
      count <= 0;
	  finish <= '0';
    elsif rising_edge(Clock) then
	  count <= count + 1;
	  if count = 750000 then
		finish <= '1';
	--  else		
	--	finish <= '0';
	  end if;
    end if;
  end process;
end architecture archi_Counter_15ms;

