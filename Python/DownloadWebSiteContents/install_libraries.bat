CALL ..\SET_PYTHON_HOME.bat

call %PYTHON_HOME%\python.exe -m pip install stubs
call %PYTHON_HOME%\python.exe -m pip install types-requests
call %PYTHON_HOME%\python.exe -m pip install requests
call %PYTHON_HOME%\python.exe -m pip install beautifulsoup4
call %PYTHON_HOME%\python.exe -m pip install types-beautifulsoup4
call %PYTHON_HOME%\python.exe -m pip install lxml

timeout /t 5