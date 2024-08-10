SETLOCAL EnableDelayedExpansion

@ECHO %date% %time%

@Echo arguments:
@set input_video_full_path=%1
@SET output_format=%2
@SET preset=%3
@set move_input_file_after_process=%4%

@echo input_video_full_path:%input_video_full_path%
@echo output_format:%output_format%

@Title Compress video %input_video_full_path% format %output_format% preset %preset%

@timeout /t 1

if %move_input_file_after_process% == "true" (
	@if not exist original @MD original
)

@SET script_full_path=%0
@set script_full_path=%script_full_path:"=%
@echo script_full_path:%script_full_path%

@for %%A IN (%script_full_path%) DO (@SET script_folder_path="%%~dpA")
@set script_folder_path=%script_folder_path:"=%
@echo script_folder_path:%script_folder_path%

@Echo input_video_full_path %input_video_full_path%

@for %%F in (%input_video_full_path%) do SET input_video_file_name_with_extension=%%~nxF
@echo input_video_file_name_with_extension:%input_video_file_name_with_extension%


@for %%A IN (%input_video_file_name_with_extension%) DO (@SET input_video_file_extension="%%~xA")
@echo input_video_file_extension:%input_video_file_extension%

rem @SET input_video_file_name_without_extension=%input_video_file_name_with_extension:%input_video_file_extension% = % 
rem @for %%A IN (%input_video_file_name_with_extension%) DO (
rem @ECHO "%%~nA"
rem SET input_video_file_name_without_extension=!input_video_file_name_without_extension!"%%~nA"
rem ECHO input_video_file_name_without_extension !input_video_file_name_without_extension!
rem )
rem pause

@for %%A IN (%input_video_file_name_with_extension%) DO (@SET input_video_file_name_without_extension=!input_video_file_name_without_extension!" ""%%~nA")
@set input_video_file_name_without_extension=%input_video_file_name_without_extension:"=%
@for /f "tokens=* delims= " %%a in ("%input_video_file_name_without_extension%") do set input_video_file_name_without_extension=%%a
@echo input_video_file_name_without_extension:%input_video_file_name_without_extension%

rem pause

@for %%A IN (%input_video_full_path%) DO (@SET input_video_folder_path="%%~dpA")
@echo input_video_folder_path %input_video_folder_path%

@SET output_video_file_name_with_extension=%input_video_file_name_without_extension%_%output_format%.mp4
@echo output_video_file_name_with_extension %output_video_file_name_with_extension%
rem pause

@SET output_video_full_path=%input_video_folder_path%%output_video_file_name_with_extension%
@set output_video_full_path=%output_video_full_path:"=%

@echo output_video_full_path %output_video_full_path%

@timeout /t 1
rem HandBrakeCLI.exe HandBrakeCLI -Z "Fast 1080p30" -i 20240809_112811.mp4 -o out.mp4

rem echo call "%script_folder_path%\Handbrake\HandBrakeCLI-1.8.2-win-x86_64\HandBrakeCLI.exe" HandBrakeCLI -Z %preset% -i %input_video_full_path% -o "%output_video_full_path%"
call %script_folder_path%\Handbrake\HandBrakeCLI-1.8.2-win-x86_64\HandBrakeCLI.exe HandBrakeCLI -Z %preset% -i %input_video_full_path% -o "%output_video_full_path%"



if %move_input_file_after_process% == "true" (
	move %input_video_full_path% original
)

rem pause
@timeout /t 30
