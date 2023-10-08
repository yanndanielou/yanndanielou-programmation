rem @ECHO DOWNLOAD_FILE_FROM_LINK 

@SET FULL_LINK=%1
@for /f "delims=" %%i in ("%FULL_LINK%") do set "fileName=%%~nxi"

@echo Download file %fileName% from 


powershell -Command "(New-Object Net.WebClient).DownloadFile('%FULL_LINK%', '%fileName%')
