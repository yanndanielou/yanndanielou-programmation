-- Squelette pour l'exercice Compteur
library IEEE;
use IEEE.Std_logic_1164.all;
use IEEE.Numeric_std.all;

entity Counter_10ns is
  port (Clock, Reset: in Std_logic;
        finish:   out std_logic);
end entity Counter_10ns;

architecture archi_Counter_10ns of Counter_10ns is
  signal count : integer range 0 to + 3050;

begin
  process(reset, Clock)
  begin
    if reset = '1' then
      count <= 0;
	  finish <= '0';
    elsif rising_edge(Clock) then
	  count <= count + 1;
	  if count = 2050 then
		finish <= '1';
	--  else		
	--	finish <= '0';
	  end if;
    end if;
  end process;
end architecture archi_Counter_10ns;

