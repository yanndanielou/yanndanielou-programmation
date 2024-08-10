ECHO %date%
ECHO %time%


@echo ON
@SET subject=%*
@for %%a in (%*) do @set input_video_full_path=%%a

@Echo input_video_full_path %input_video_full_path%

@for %%F in (%input_video_full_path%) do SET input_video_file_name_with_extension=%%~nxF
@echo input_video_file_name_with_extension %input_video_file_name_with_extension%

@for %%A IN (%input_video_file_name_with_extension%) DO (@SET input_video_file_name_without_extension="%%~nA")
@echo input_video_file_name_without_extension %input_video_file_name_without_extension%

@for %%A IN (%input_video_file_name_with_extension%) DO (@SET input_video_file_extension="%%~xA")
@echo input_video_file_extension %input_video_file_extension%


@for %%A IN (%input_video_full_path%) DO (@SET input_video_folder_path="%%~dpA")
@echo input_video_folder_path %input_video_folder_path%


pause
exit

rem HandBrakeCLI.exe HandBrakeCLI -Z "Fast 1080p30" -i 20240809_112811.mp4 -o out.mp4


call Handbrake\HandBrakeCLI-1.8.2-win-x86_64\HandBrakeCLI.exe HandBrakeCLI -Z "Fast 1080p30" -i 20240809_112811.mp4 -o out.mp4

timeout /t 30