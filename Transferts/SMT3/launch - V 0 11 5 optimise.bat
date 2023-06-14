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

CD Exe

SET SMT3_VERSION=D5_2_14_6

set dossiers_fichiers_m=D:\Pour_Yann_Melnotte\SMT3_Package\data_m_files\2023-05-25_23h01_4788_ME

set port=8080
set log_file_with_path=logs/SMT3_%SMT3_VERSION%_port_%port%_%yyyy%_%mm%_%dd%_%current_time%.log
echo dossiers_fichiers_m %dossiers_fichiers_m% > %log_file_with_path%
Smt3_ATSPlus_%SMT3_VERSION%.exe "%dossiers_fichiers_m%" -spdef smt_server.base_uri.port=%port% -Dlog4j.configurationFile=./log4j2-smtserver2.xml -Dvicos.cbtc.logger.folder.path=./logs/  1>>%log_file_with_path%

timeout /t 1

timeout /t 30