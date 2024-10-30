CALL SET_PYTHON_HOME.bat

call %PYTHON_HOME%\python.exe -m pip install mypy
call %PYTHON_HOME%\python.exe -m pip install m3uspiff

timeout /t 100
