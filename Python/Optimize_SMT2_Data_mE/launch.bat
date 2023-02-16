SET python_full_path="%LOCALAPPDATA%\Programs\Python\Python310\python.exe"
IF NOT EXIST %python_full_path% SET python_full_path="%LOCALAPPDATA%\Programs\Python\Python39\python.exe"

echo python_full_path : %python_full_path%

%python_full_path% Optimize_SMT2_Data_mE.py

timeout /t 30