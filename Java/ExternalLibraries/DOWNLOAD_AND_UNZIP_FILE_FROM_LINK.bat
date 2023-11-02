@SET FULL_LINK=%1
@for /f "delims=" %%i in ("%FULL_LINK%") do @set "fileName=%%~nxi"
set fileNameWithoutExtension = %fileName:~0,-4%

echo fileName %fileName% 
echo FULL_LINK %FULL_LINK% 
echo fileNameWithoutExtension %fileNameWithoutExtension% 

pause

CALL DOWNLOAD_FILE_FROM_LINK %1
powershell -command "Expand-Archive -Force '%fileName%' '%fileNameWithoutExtension%'"

pause