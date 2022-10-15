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
@echo Application will end now
@pause

:END_PORT_DETECTION


@SET SEVEN_z_folder=C:\Program Files\7-Zip
@SET SEVEN_z_full_exe_path=%SEVEN_z_folder%\7z.exe

@SET SMT3_Package_folder=Output\SMT3_Package_%PORT_SMT3%

@IF EXIST %SMT3_Package_folder% @echo Remove existing directory  %SMT3_Package_folder% & @RD /S /Q %SMT3_Package_folder%
@IF EXIST %SMT3_Package_folder% @echo could not use %SMT3_Package_folder% because already exists & pause & exit

@call "%SEVEN_z_full_exe_path%" x SMT3_Package.7z -o%SMT3_Package_folder%

@REM @netstat /o /a | find /i "listening" | find ":%PORT_SMT3%" >nul 2>nul && (
@REM    @echo Port %PORT_SMT3% is open
@REM ) || (
@REM   @echo %PORT_SMT3% is Not open
@REM )

SET CURRENT_DIRECTORY=%CD%
@cd "%SMT3_Package_folder%\SMT3_Package\Exe" 
Smt3_ATSPlus_D5_2_11.exe "%CURRENT_DIRECTORY%\%SMT3_Package_folder%\SMT3_Package\Fichiers .m extraits" -spdef smt_server.base_uri.port=%PORT_SMT3% -Dlog4j.configurationFile=./log4j2-smtserver2.xml -Dvicos.cbtc.logger.folder.path=./logs/ > logs/app_%PORT_SMT3%.log

@RD /S /Q %SMT3_Package_folder%

pause