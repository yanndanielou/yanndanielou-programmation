@SETLOCAL
rem @ECHO OFF

ECHO ** Perflogs : Setup 

REM PARAMETER_FILE
REM Le fichier PerfLogsParameter est nommé par défaut
REM Il peut être préfixé par le nom du projet passé en argument dans la commande d'installation
SET NEW_PARAMETER_FILE=%2%
IF DEFINED NEW_PARAMETER_FILE SET PARAMETER_FILE="%~dp0%NEW_PARAMETER_FILE%PerfLogsParameters.bat"

IF NOT DEFINED PARAMETER_FILE SET PARAMETER_FILE="%~dp0PerfLogsParameters.bat"
IF NOT EXIST %PARAMETER_FILE% GOTO ERROR_PARAMETER_FILE

@CALL %PARAMETER_FILE%

REM CFG_FILENAME
REM Le fichier CFG_FILENAME est défini par défaut dans PerfLogsParameter.bat
REM Il peut être précisé dans la commande d'installation
SET NEW_CFG_FILENAME=%1%
IF DEFINED NEW_CFG_FILENAME SET CFG_FILENAME=%NEW_CFG_FILENAME%

REM Create Directories
IF NOT EXIST %LOGGER_FOLDER% MKDIR %LOGGER_FOLDER%
IF NOT EXIST %ZIPPED_PERFLOGS_DIRECTORY% MKDIR %ZIPPED_PERFLOGS_DIRECTORY%

ECHO ** Stop counter "%APPLICATION_NAME%". Please Wait...
logman stop "%APPLICATION_NAME%" > NUL 2>&1
timeout /t 2 /nobreak >NUL

ECHO ** Delete counter "%APPLICATION_NAME%". Please Wait...
logman delete "%APPLICATION_NAME%" > NUL 2>&1
timeout /t 2 /nobreak >NUL

:CREATE
ECHO ** Create counter "%APPLICATION_NAME%"
logman create counter "%APPLICATION_NAME%" -si %LOG_INTERVAL% -f bin -max %LOG_MAX_FILE_SIZE% -cnf %CHANGE_FILE_TIME% -o "%LOGGER_FOLDER%\%LOG_FILENAME%.blg" -cf "%~dp0%CFG_FILENAME%.cfg"
IF NOT %ERRORLEVEL%==0 GOTO ERROR_CREATE
GOTO :EOF

rem :START
rem ECHO ** Start "%APPLICATION_NAME%"
rem logman start "%APPLICATION_NAME%" >nul
rem IF NOT %ERRORLEVEL%==0 GOTO ERROR_START
rem GOTO :EOF

:ERROR_PARAMETER_FILE
ECHO.
ECHO ********** ERROR ***********
ECHO ** Parameter file not found
ECHO ** You need a parameter file: %PARAMETER_FILE%
pause
GOTO :EOF

:ERROR_CREATE
ECHO.
ECHO ********** ERROR ***********
ECHO ** Invalid command
ECHO ** Counter "%APPLICATION_NAME%" not created
pause
GOTO :CREATE

:ERROR_START
ECHO.
ECHO ********** ERROR ***********
ECHO ** Invalid command
ECHO ** "%APPLICATION_NAME%" Counter not started
pause
GOTO :EOF

:ERROR_STOP
ECHO.
ECHO ********** ERROR ***********
ECHO ** Invalid command
ECHO ** "%APPLICATION_NAME%" Counter not stopped
pause
GOTO :EOF

:ERROR_DELETE
ECHO.
ECHO ********** ERROR ***********
ECHO ** Invalid command
ECHO ** "%APPLICATION_NAME%" Counter not deleted
pause

:EOF
IF %ERRORLEVEL%==0 (
	ECHO ** Perf log Setup Done
	ECHO.
)
@ENDLOCAL

