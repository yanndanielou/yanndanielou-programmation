
:INFINITE_LOOP
ECHO %DATE% %TIME% Start ArreterSimulationSmtTroisRienNeBouge.py >> logs\launch_ArreterSimulationSmtTroisRienNeBouge.log
Title Launch ArreterSimulationSmtTroisRienNeBouge %DATE% %TIME%
call C:\Users\Yann\AppData\Local\Programs\Python\Python39\python.exe ArreterSimulationSmtTroisRienNeBouge.py >> logs\launch_ArreterSimulationSmtTroisRienNeBouge.log 2>launch_ArreterSimulationSmtTroisRienNeBouge.error_log
timeout /t 30
GOTO :INFINITE_LOOP