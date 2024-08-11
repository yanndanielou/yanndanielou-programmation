@SET LOCALAPPDATA_PROGRAMS_PYTHON=%LOCALAPPDATA%\Programs\Python
@ECHO LOCALAPPDATA_PROGRAMS_PYTHON:%LOCALAPPDATA_PROGRAMS_PYTHON%

@IF NOT DEFINED PYTHON_HOME @CALL :CHECK_IF_EXISTS_AND_SET_PYTHON_HOME C:\Users\fr232487\AppData\Local\Programs\Python\Python312
@IF NOT DEFINED PYTHON_HOME @CALL :CHECK_IF_EXISTS_AND_SET_PYTHON_HOME %LOCALAPPDATA_PROGRAMS_PYTHON%\Python39

@ECHO PYTHON_HOME %PYTHON_HOME%

timeout /t 1
GOTO :END_OF_FILE


:CHECK_IF_EXISTS_AND_SET_PYTHON_HOME
@Echo Check python at %1

@if exist %1 (
@SET PYTHON_HOME=%1
@ECHO Python Home found! %1
) else (
@ECHO Python not found there

)

:END_OF_FILE
