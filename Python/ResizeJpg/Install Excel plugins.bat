CALL ..\SET_PYTHON_HOME.bat

call %PYTHON_HOME%\python.exe -m pip install Pillow

timeout /t 30
