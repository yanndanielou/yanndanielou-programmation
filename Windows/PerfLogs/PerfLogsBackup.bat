@SETLOCAL
rem @ECHO OFF

REM PARAMETER_FILE
REM Le fichier PerfLogsParameter est nommé par défaut
REM Il peut être préfixé par le nom du projet passé en argument dans la commande d'installation
SET NEW_PARAMETER_FILE=%1%
IF DEFINED NEW_PARAMETER_FILE SET PARAMETER_FILE="%~dp0%NEW_PARAMETER_FILE%PerfLogsParameters.bat"

IF NOT DEFINED PARAMETER_FILE SET PARAMETER_FILE="%~dp0PerfLogsParameters.bat"
IF NOT EXIST %PARAMETER_FILE% (
ECHO ********** ERROR ***********
ECHO Parameter file not found
ECHO You need a parameter file: %PARAMETER_FILE%
pause
GOTO :EOF
)

@CALL %PARAMETER_FILE%

@IF NOT DEFINED LOG_FOLDER SET LOG_FOLDER=D:\%PROJECT_PREFIX%Logger\DailyArchiveBackupLogs
@IF NOT DEFINED FILE_COMPRESSION_EXTENSION SET FILE_COMPRESSION_EXTENSION=zip

REM year, month, day of today to compute zip file name
CALL "%~dp0CurrentDate.bat" 1
@SET LOG_FILE=%LOG_FOLDER%\DailyPerflogBackup_%yyyy%_%mm%_%dd%.log
@IF NOT EXIST %LOG_FOLDER% MD %LOG_FOLDER%


@ECHO START AT %DATE% %TIME% > %LOG_FILE%

REM remove files older than the BACKUP_KEEP_PERIOD
CALL "%~dp0CurrentDate.bat" %BACKUP_KEEP_PERIOD%
PUSHD %ZIPPED_PERFLOGS_DIRECTORY%
SET FILE_TO_EXCLUDE=~~~FILE_TO_EXCLUDE.TXT~~
for /f "tokens=*" %%a IN ('xcopy %BACKUP_FILENAME%_*.zip  /D:%mm%-%dd%-%yyyy% /L /I null') do if exist %%~nxa echo %%~nxa >> %FILE_TO_EXCLUDE%
for /f "tokens=*" %%a IN ('xcopy %BACKUP_FILENAME%_*.zip  /EXCLUDE:%FILE_TO_EXCLUDE% /L /I null') do if exist %%~nxa DEL %%~nxa
for /f "tokens=*" %%a IN ('xcopy %BACKUP_FILENAME%_*.%FILE_COMPRESSION_EXTENSION%  /D:%mm%-%dd%-%yyyy% /L /I null') do if exist %%~nxa echo %%~nxa >> %FILE_TO_EXCLUDE%
for /f "tokens=*" %%a IN ('xcopy %BACKUP_FILENAME%_*.%FILE_COMPRESSION_EXTENSION%  /EXCLUDE:%FILE_TO_EXCLUDE% /L /I null') do if exist %%~nxa DEL %%~nxa
REM forfiles /M %BACKUP_FILENAME%_*.zip /C "cmd /c del @path" /D -%BACKUP_KEEP_PERIOD% 2> NUL

@ECHO %DATE% %TIME% FILE_TO_EXCLUDE >> %LOG_FILE%
type %FILE_TO_EXCLUDE% >> %LOG_FILE%

DEL %FILE_TO_EXCLUDE%
POPD %ZIPPED_PERFLOGS_DIRECTORY%

REM year, month, day of today to compute zip file name
CALL "%~dp0CurrentDate.bat" 1
SET FILE_NAME=%BACKUP_FILENAME%_%yyyy%_%mm%_%dd%

@ECHO %DATE% %TIME% Beginning zip >> %LOG_FILE%
"%SEVEN_ZIP_PATH%\7z.exe" a -y "%ZIPPED_PERFLOGS_DIRECTORY%\%FILE_NAME%.%FILE_COMPRESSION_EXTENSION%" %LOGGER_FOLDER%\*.blg
@ECHO %DATE% %TIME% End of zip >> %LOG_FILE%

SET ERROR_ZIP=%errorlevel%
REM No Error
if %ERROR_ZIP% == 0 GOTO :SUCCESS 
REM Warning
if %ERROR_ZIP% == 1 GOTO :SUCCESS

:FAILED
  @ECHO %DATE% %TIME% Failed >> %LOG_FILE%
  eventcreate /T ERROR /ID 2 /L APPLICATION /SO "%APPLICATION_NAME% Backup" /D "%APPLICATION_NAME% Backup failed"
  GOTO :END

:SUCCESS
  @ECHO %DATE% %TIME% Success >> %LOG_FILE%
  eventcreate /T INFORMATION /ID 1 /L APPLICATION /SO "%APPLICATION_NAME% Backup" /D "%APPLICATION_NAME% Backup has been done"
  GOTO :END

:END
  @ECHO %DATE% %TIME% remove all *.blg files deleted except the current opened one>> %LOG_FILE%
  DEL /A:A %LOGGER_FOLDER%\*.blg > NUL 2>&1

@ECHO END AT %DATE% %TIME% >> %LOG_FILE%

