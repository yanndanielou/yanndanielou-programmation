@SET FULL_LINK=%1
@for /f "delims=" %%i in ("%FULL_LINK%") do @set "fileName=%%~nxi"

@SET script_full_path=%0
@set script_full_path=%script_full_path:"=%
@echo script_full_path %script_full_path%

@for %%A IN (%script_full_path%) DO (@SET script_folder_path="%%~dpA")
@set script_folder_path=%script_folder_path:"=%
@echo script_folder_path %script_folder_path%

@echo FULL_LINK [%FULL_LINK%] 


@echo fileName [%fileName%] 

@set fileNameLast3Digits=%fileName:~-3%
@set fileNameLast4Digits=%fileName:~-4%

@echo fileNameLast3Digits [%fileNameLast3Digits%] 
@echo fileNameLast4Digits [%fileNameLast4Digits%] 

@Echo fileNameLast3Digits : %fileNameLast3Digits%
@Echo fileNameLast4Digits : %fileNameLast4Digits%

@set fileNameWithoutExtensionStep1=%fileName:~0,-10%
@echo fileNameWithoutExtension step 1 [%fileNameWithoutExtensionStep1%] 

@IF .7z == %fileNameLast3Digits% @set fileNameWithoutExtension=%fileName:~0,-3%& echo Extension found: .7z
@IF .zip == %fileNameLast4Digits% @set fileNameWithoutExtension=%fileName:~0,-4%& echo .zip

@echo fileNameWithoutExtension [%fileNameWithoutExtension%]

@timeout /t 30

@CALL %script_folder_path%\DOWNLOAD_FILE_FROM_LINK %1
@timeout /t 30

@ECHO Will Expand-Archive -Force '%fileName%' '%fileNameWithoutExtension%'
@powershell -command "Expand-Archive -Force '%fileName%' '%fileNameWithoutExtension%'"

