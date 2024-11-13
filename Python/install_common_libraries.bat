CALL SET_PYTHON_HOME.bat

call %PYTHON_HOME%\python.exe -m pip install mypy
call %PYTHON_HOME%\python.exe -m pip install m3uspiff
call %PYTHON_HOME%\python.exe -m pip install ffmpeg
call %PYTHON_HOME%\python.exe -m pip install ffprobe 
call %PYTHON_HOME%\python.exe -m pip install customtkinter


timeout /t 100
