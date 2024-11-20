@CALL SET_PYTHON_HOME.bat

@call :INSTALL_PYTHON_LIB mysql
@call :INSTALL_PYTHON_LIB mysql-connector
@call :INSTALL_PYTHON_LIB pycairo
@call :INSTALL_PYTHON_LIB PyGObject
@call :INSTALL_PYTHON_LIB mypy
@call :INSTALL_PYTHON_LIB m3uspiff
@call :INSTALL_PYTHON_LIB ffmpeg
@call :INSTALL_PYTHON_LIB ffprobe 
@call :INSTALL_PYTHON_LIB customtkinter
@call :INSTALL_PYTHON_LIB matplotlib
@call :INSTALL_PYTHON_LIB ipywidgets


@GOTO :END_OF_FILE


:INSTALL_PYTHON_LIB
@Echo install %1
@call %PYTHON_HOME%\python.exe -m pip install %1
@EXIT /B 0


:END_OF_FILE

@timeout /t 100
