@SETLOCAL
rem @ECHO OFF
@ECHO ** Perflogs : Create %APPLICATION_NAME% scheduled task

REM PARAMETER_FILE
REM Le fichier PerfLogsParameter est nommé par défaut
REM Il peut être préfixé par le nom du projet passé en argument dans la commande d'installation
SET NEW_PARAMETER_FILE=%1%
IF DEFINED NEW_PARAMETER_FILE SET PARAMETER_FILE="%~dp0%NEW_PARAMETER_FILE%PerfLogsParameters.bat"

IF NOT DEFINED PARAMETER_FILE SET PARAMETER_FILE="%~dp0PerfLogsParameters.bat"
IF NOT EXIST %PARAMETER_FILE% (
ECHO.
ECHO ********** ERROR ***********
ECHO ** Parameter file not found
ECHO ** You need a parameter file: %PARAMETER_FIL
pause
GOTO :EOF
)

@CALL %PARAMETER_FILE%

schtasks /delete /tn "%APPLICATION_NAME% Startup" /f > NUL 2>&1
schtasks /create /tn "%APPLICATION_NAME% Startup" /tr "logman start \"%APPLICATION_NAME%\"" /ru "System" /sc ONSTART > NUL

IF %ERRORLEVEL%==0 (
	ECHO ** Done.
	ECHO.
)