@ECHO %date% %time%
@SET output_format=1080p30
@Echo output_format %output_format%

echo arg1 %1
echo arg2 %2
echo arg3 %3
echo arg4 %4
echo arg5 %5

@echo ON
@SET subject=%*
@for %%a in (%*) do @set input_video_full_path=%%a


@SET script_full_path=%0
@set script_full_path=%script_full_path:"=%
@echo script_full_path %script_full_path%

@for %%A IN (%script_full_path%) DO (@SET script_folder_path="%%~dpA")
@set script_folder_path=%script_folder_path:"=%
@echo script_folder_path %script_folder_path%


call %script_folder_path%\compress_video_handbrake_generic.bat %input_video_full_path% %output_format% "Fast %output_format%"


timeout /t 5