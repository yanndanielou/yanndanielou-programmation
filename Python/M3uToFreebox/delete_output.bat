
move output output_tmp
RD /S /Q  output_tmp
del /Q output_tmp\*

del /Q output\*

timeout /t 30