@SET PORT_SMT3=%1
@ECHO PORT_SMT3 %PORT_SMT3%

@IF NOT DEFINED PORT_SMT3 ECHO Argument invalide & pause & exit

@Title SMT3 on port %PORT_SMT3% started at %DATE% %TIME% 

@netstat -o -n -a | findstr %PORT_SMT3%

if %ERRORLEVEL% equ 0 goto :PORT_FOUND

@echo port %PORT_SMT3% is currently not used

@goto :END_PORT_DETECTION

:PORT_FOUND

@echo Error!! port %PORT_SMT3% is currently already used
@pause

:END_PORT_DETECTION



@REM @netstat /o /a | find /i "listening" | find ":%PORT_SMT3%" >nul 2>nul && (
@REM    @echo Port %PORT_SMT3% is open
@REM ) || (
@REM   @echo %PORT_SMT3% is Not open
@REM )

@cd "D:\SMT3_generation\SMT3\SMT3-ATS\XML et Exe\Exe" 
Smt3_ATSPlus_D5_2_11.exe "D:\SMT3_generation\SMT3\SMT3-ATS\XML et Exe\Fichiers .m extraits" -spdef smt_server.base_uri.port=%PORT_SMT3% -Dlog4j.configurationFile=./log4j2-smtserver2.xml -Dvicos.cbtc.logger.folder.path=./logs/ > logs/app_%PORT_SMT3%.log
pause