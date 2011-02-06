# MPLAB IDE generated this makefile for use with GNU make.
# Project: liaison_serie.mcp
# Date: Fri Jan 11 14:12:29 2008

AS = mpasmwin.exe
CC = mcc18.exe
LD = mplink.exe
AR = mplib.exe
RM = rm

liaison_serie.cof : main.o lcd.o usart.o
	$(LD) /l"C:\MCC18\lib" "18f6722i.lkr" "main.o" "lcd.o" "usart.o" /z__MPLAB_BUILD=1 /o"liaison_serie.cof" /M"liaison_serie.map" /W

main.o : main.c ../../../MCC18/h/delays.h ../../../MCC18/h/stdlib.h ../../../MCC18/h/string.h lcd.h usart.h main.c ../../../MCC18/h/p18f6722.h ../../../MCC18/h/p18cxxx.h ../../../MCC18/h/stddef.h
	$(CC) -p=18F6722 "main.c" -fo="main.o" -Ou- -Ot- -Ob- -Op- -Or- -Od- -Opa-

lcd.o : lcd.c ../../../MCC18/h/delays.h ../../../MCC18/h/stdlib.h ../../../MCC18/h/string.h lcd.h lcd.c ../../../MCC18/h/p18f6722.h ../../../MCC18/h/p18cxxx.h ../../../MCC18/h/stddef.h
	$(CC) -p=18F6722 "lcd.c" -fo="lcd.o" -Ou- -Ot- -Ob- -Op- -Or- -Od- -Opa-

usart.o : usart.c ../../../MCC18/h/delays.h ../../../MCC18/h/stdlib.h ../../../MCC18/h/string.h lcd.h usart.h usart.c ../../../MCC18/h/p18f6722.h ../../../MCC18/h/p18cxxx.h ../../../MCC18/h/stddef.h
	$(CC) -p=18F6722 "usart.c" -fo="usart.o" -Ou- -Ot- -Ob- -Op- -Or- -Od- -Opa-

clean : 
	$(RM) "main.o" "lcd.o" "usart.o" "liaison_serie.cof" "liaison_serie.hex"

