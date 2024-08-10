@ECHO %date% %time%
@SET output_format=480p30
@Echo output_format %output_format%

@echo ON
@SET subject=%*
@for %%a in (%*) do @set input_video_full_path=%%a

@SET script_full_path=%0
@set script_full_path=%script_full_path:"=%
@echo script_full_path %script_full_path%

@for %%A IN (%script_full_path%) DO (@SET script_folder_path="%%~dpA")
@echo script_folder_path %script_folder_path%

@Echo input_video_full_path %input_video_full_path%

@for %%F in (%input_video_full_path%) do SET input_video_file_name_with_extension=%%~nxF
@echo input_video_file_name_with_extension %input_video_file_name_with_extension%

@for %%A IN (%input_video_file_name_with_extension%) DO (@SET input_video_file_name_without_extension="%%~nA")
@echo input_video_file_name_without_extension %input_video_file_name_without_extension%

@for %%A IN (%input_video_file_name_with_extension%) DO (@SET input_video_file_extension="%%~xA")
@echo input_video_file_extension %input_video_file_extension%

@for %%A IN (%input_video_full_path%) DO (@SET input_video_folder_path="%%~dpA")
@echo input_video_folder_path %input_video_folder_path%

@SET output_video_file_name_with_extension=%input_video_file_name_without_extension%_%output_format%.mp4
@echo output_video_file_name_with_extension %output_video_file_name_with_extension%


@SET output_video_full_path=%input_video_folder_path%\\%output_video_file_name_with_extension%
@set output_video_full_path=%output_video_full_path:"=%

@echo output_video_full_path %output_video_full_path%

@timeout /t 100
rem HandBrakeCLI.exe HandBrakeCLI -Z "Fast 1080p30" -i 20240809_112811.mp4 -o out.mp4

call %script_folder_path%\Handbrake\HandBrakeCLI-1.8.2-win-x86_64\HandBrakeCLI.exe HandBrakeCLI -Z "Fast %output_format%" -i %input_video_full_path% -o "%output_video_full_path%"

timeout /t 30