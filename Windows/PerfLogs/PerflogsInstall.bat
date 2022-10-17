rem rem @ECHO OFF
ECHO Check admin rights
net session >nul 2>&1
net session >nul 2>&1
if %errorLevel% == 0 (
	echo Success: Administrative permissions confirmed.
) else (
	echo Failure: Current permissions inadequate.
	pause
	exit
)



echo Admin rights granted

echo current dir CD : %CD%
echo current dir dp0 : %~dp0

CD /D %~dp0

SET PARAMETER_FILE=PerfLogsParameters.bat
IF NOT DEFINED ALWAYS_REINSTALL SET ALWAYS_REINSTALL=FALSE

IF NOT EXIST %PARAMETER_FILE% (
ECHO ********** ERROR ***********
ECHO Parameter file not found
ECHO You need a parameter file: %PARAMETER_FILE%
pause
GOTO :EOF
)

ECHO.
ECHO ****************************
ECHO ** Perflogs installation
ECHO ****************************

CALL %PARAMETER_FILE%

REM Check if a systematic reinstall wanted
IF %ALWAYS_REINSTALL% == TRUE (
	GOTO INSTALL
)

REM Installation verification
LOGMAN | FIND /I "%APPLICATION_NAME%"
IF %ERRORLEVEL%==0 (
	GOTO FIND
)


:INSTALL
REM Installation of perflogs
CALL PerfLogsKill.bat %2
IF EXIST %LOGGER_FOLDER% (
	ECHO Backing up existing perf logs...
	CALL PerfLogsBackup.bat %2 > NUL 2>&1
)
CALL PerfLogsSetup.bat %1 %2
CALL PerfLogsSchedule.bat %2
CALL PerfLogsBackupSchedule.bat %2
CALL PerfLogsStart.bat %2
GOTO :EOF

:FIND

CHOICE /C YN /M "Perflog is already installed. The script will reinstall it. Do you want to skip this step" /T 15 /D N
IF %ERRORLEVEL%==2 GOTO INSTALL
GOTO :EOF

:EOF
