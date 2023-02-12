@ECHO off

@SET /A mm=0
@SET /A dd=0
@SET /A yyyy=0

REM ----------------------------------------------------------------------------
@ECHO La date complete du jour est %DATE%
@FOR /F "tokens=1,2" %%i IN ("%DATE%") DO SET JOURNAME=%%i&& SET DATE1=%%j
@IF "%DATE1%"=="" SET DATE1=%JOURNAME%
@FOR /F "TOKENS=3" %%A IN ('REG QUERY "HKEY_CURRENT_USER\Control Panel\International" ^| FIND /I "iDate"') DO (
@ECHO La date filtree du jour est %DATE1%
rem MJA  MM/JJ/AAAA
@IF %%A EQU 0 SET TODAY_DATE=%DATE1:~3,2%_%DATE1:~0,2%_%DATE1:~6,4%
rem JMA  JJ/MM/AAAA
@IF %%A EQU 1 SET TODAY_DATE=%DATE1%
rem AMJ AAAA/MM/JJ
@IF %%A EQU 2 SET TODAY_DATE=%DATE1:~8,2%_%DATE1:~5,2%_%DATE1:~0,4%)

@ECHO La date re-organisee en format JJ/MM/AAAA du jour est %TODAY_DATE%

rem [POUR TEST EN LOCAL]  
rem SET TODAY_DATE=seg 12/04/2011
rem Format de la date JJ/MM/AAAA et d‚termination du jour pr‚c‚dent - D pour date du jour - 
@SET mm=%TODAY_DATE:~3,2%
@SET dd=%TODAY_DATE:~0,2%
@SET /A yyyy=%TODAY_DATE:~6,4%
REM ----------------------------------------------------------------------------
rem Correction du bug des jours et mois en 08 et 09 (0? signifie octal -> 8, 9 n'existent pas)
if %dd%==08 (
set /A dd=8 ) else (
if %dd%==09 (
set /A dd=9 ) else ( 
SET /A dd=%dd%
) ) 

if %mm%==08 (
set /A mm=8 ) else (
if %mm%==09 (
set /A mm=9 ) else ( 
SET /A mm=%mm%) )

if NOT "%1"=="" set /A dd=%dd% - %1

set /A mm=%mm% + 0
:DAYCALC
if /I %dd% GTR 0 goto :END
set /A mm=%mm% - 1
if /I %mm% GTR 0 goto ADJUSTDAY
set /A mm=12
set /A yyyy=%yyyy% - 1

:ADJUSTDAY
if %mm%==1 goto SET31
if %mm%==2 goto LEAPCHK
if %mm%==3 goto SET31
if %mm%==4 goto SET30
if %mm%==5 goto SET31
if %mm%==6 goto SET30
if %mm%==7 goto SET31
if %mm%==8 goto SET31
if %mm%==9 goto SET30
if %mm%==10 goto SET31
if %mm%==11 goto SET30
if %mm%==12 goto SET31
goto :EOF

:SET31
set /A dd=31 + %dd%
goto :DAYCALC

:SET30
set /A dd=30 + %dd%
goto :DAYCALC

:LEAPCHK
set /A tt=%yyyy% %% 4
if not %tt%==0 goto SET28
set /A tt=%yyyy% %% 100
if not %tt%==0 goto SET29
set /A tt=%yyyy% %% 400
if %tt%==0 goto SET29

:SET28
set /A dd=28 + %dd%
goto :DAYCALC

:SET29
set /A dd=29 + %dd%
GOTO :DAYCALC
REM ----------------------------------------------------------------------------
:END
IF %dd% LSS 10 SET dd=0%dd%
IF %mm% LSS 10 SET mm=0%mm%