for /f %%a in ('powershell.exe get-date -Format yyyy') do set "date_year_yyyy=%%a"
for /f %%a in ('powershell.exe get-date -Format MM') do set "date_month_mm=%%a"
for /f %%a in ('powershell.exe get-date -Format dd') do set "date_day_dd=%%a"

set current_time=%TIME%
rem ex: 10:25:54,58
rem ECHO current_time %current_time%
set current_time=%current_time::=_%
rem ECHO current_time %current_time%
set current_time=%current_time:,=_%
set current_time=%current_time: =0%
ECHO current_time:%current_time%

md output
Test_load_SMT2_Data_mE_contents.exe > output\Test_load_SMT2_Data_mE_contents__%date_year_yyyy%_%date_month_mm%_%date_day_dd%_%current_time%.log
