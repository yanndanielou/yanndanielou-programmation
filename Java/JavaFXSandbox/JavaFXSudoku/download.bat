call ..\..\ExternalLibraries\DOWNLOAD_FILE_FROM_LINK.bat https://github.com/BenJeau/JavaFX-Sudoku/archive/refs/heads/master.zip

@set fileNameWithoutExtension=%fileName:~0,-4%

@echo fileName %fileName% 
@echo FULL_LINK %FULL_LINK% 
@echo fileNameWithoutExtension %fileNameWithoutExtension% 

powershell -command "Expand-Archive -Force '%fileName%' '%fileNameWithoutExtension%'"


timeout /t 30


