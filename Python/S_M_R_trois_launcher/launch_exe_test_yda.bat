call CurrentDate.bat 0

echo dd %dd%
echo mm %mm%
echo yyyy %yyyy%

set current_time=%TIME%
rem ex: 10:25:54,58
rem ECHO current_time %current_time%
set current_time=%current_time::=_%
rem ECHO current_time %current_time%
set current_time=%current_time:,=_%
set current_time=%current_time: =0%
ECHO current_time:%current_time%

cd Exe
Smt3_ATSPlus_V5_2_13_2.exe "D:\Pour_Yann_Melnotte\SMT3_Package\Fichiers .m extraits" -spdef smt_server.base_uri.port=8090 -Dlog4j.configurationFile=./log4j2-smtserver2.xml -Dvicos.cbtc.logger.folder.path=./logs/  1>logs/app_8090_%yyyy%_%mm%_%dd%_%current_time%.log

timeout /t 30
