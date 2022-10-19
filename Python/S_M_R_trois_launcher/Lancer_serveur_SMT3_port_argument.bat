@CALL Parameters.bat

@SET PORT_SMT3=%1
@ECHO PORT_SMT3 %PORT_SMT3%

@IF NOT DEFINED PORT_SMT3 ECHO Argument invalide & pause & exit

@Title Will start SMT3 on port %PORT_SMT3% (%DATE% %TIME%) 

@netstat -o -n -a | findstr %PORT_SMT3%

@if %ERRORLEVEL% equ 0 goto :PORT_FOUND

@echo port %PORT_SMT3% is currently not used

@goto :END_PORT_DETECTION

:PORT_FOUND

@echo %DATE% %TIME% !!Error!! port %PORT_SMT3% is currently already used
@echo %DATE% %TIME% Application will end now
@pause

:END_PORT_DETECTION

SET CURRENT_DIRECTORY=%CD%


@SET SEVEN_z_folder=C:\Program Files\7-Zip
@SET SEVEN_z_full_exe_path=%SEVEN_z_folder%\7z.exe

@SET SMT3_Package_folder=Output\SMT3_Package_%PORT_SMT3%

@IF EXIST %SMT3_Package_folder% @echo Remove existing directory  %SMT3_Package_folder% & @RD /S /Q %SMT3_Package_folder%
@IF EXIST %SMT3_Package_folder% @echo could not use %SMT3_Package_folder% because already exists & pause & exit

@echo %DATE% %TIME% Extracting SMT3 package to %SMT3_Package_folder%
@call "%SEVEN_z_full_exe_path%" x SMT3_Package.7z -o%SMT3_Package_folder%


REM Ecraser temp pour que chaque lancement ait son propre environnement
@SET TMP=%CD%\%SMT3_Package_folder%\SMT3_Package\TMP
@SET TEMP=%CD%\%SMT3_Package_folder%\SMT3_Package\TEMP

@MD %TMP%
@MD %TEMP%

@Title SMT3 on port %PORT_SMT3% started at %DATE% %TIME% 

@cd "%SMT3_Package_folder%\SMT3_Package\Exe" 
copy %SMT3_EWE_NAME_WITHOUT_EXTENSION%.exe %SMT3_EWE_NAME_WITHOUT_EXTENSION%_%PORT_SMT3%.exe
%SMT3_EWE_NAME_WITHOUT_EXTENSION%_%PORT_SMT3%.exe "%CURRENT_DIRECTORY%\%SMT3_Package_folder%\SMT3_Package\Fichiers .m extraits" -spdef smt_server.base_uri.port=%PORT_SMT3% -Dlog4j.configurationFile=./log4j2-smtserver2.xml -Dvicos.cbtc.logger.folder.path=./logs/ > logs/app_%PORT_SMT3%.log

@echo %DATE% %TIME% SMT3 has exited
@Title SMT3 on port %PORT_SMT3% ended at %DATE% %TIME% 

timeout /t 999

@echo %DATE% %TIME% Remove SMT3 package %SMT3_Package_folder%
@RD /S /Q %SMT3_Package_folder%

timeout /t 99999