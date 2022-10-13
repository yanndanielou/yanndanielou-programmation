@SET PORT_SMT3=%1
@ECHO PORT_SMT3 %PORT_SMT3%

@IF NOT DEFINED PORT_SMT3 ECHO Argument invalide & pause & exit

cd "D:\SMT3_generation\SMT3\SMT3-ATS\XML et Exe\Exe" 
Smt3_ATSPlus_D5_2_11.exe "D:\SMT3_generation\SMT3\SMT3-ATS\XML et Exe\Fichiers .m extraits" -spdef smt_server.base_uri.port=%PORT_SMT3% -Dlog4j.configurationFile=./log4j2-smtserver2.xml -Dvicos.cbtc.logger.folder.path=./logs/ > logs/app_%PORT_SMT3%.log
pause