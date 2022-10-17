rem @ECHO OFF
FOR %%i in (*.blg) DO relog %%i -f CSV -o %%i.csv
