rem @ECHO OFF

REM PARAMETER_FILE
REM Le fichier PerfLogsParameter est nommé par défaut
REM Il peut être préfixé par le nom du projet passé en argument dans la commande d'installation
SET NEW_PARAMETER_FILE=%1%
IF DEFINED NEW_PARAMETER_FILE SET PARAMETER_FILE="%~dp0%NEW_PARAMETER_FILE%PerfLogsParameters.bat"

IF NOT DEFINED PARAMETER_FILE SET PARAMETER_FILE="%~dp0PerfLogsParameters.bat"
IF NOT EXIST %PARAMETER_FILE% (
ECHO ********** ERROR ***********
ECHO ** Parameter file not found
ECHO ** You need a parameter file: %PARAMETER_FILE%
pause
GOTO :EOF
)

@CALL %PARAMETER_FILE%

logman start "%APPLICATION_NAME%" > NUL 2>&1
PAUSE
