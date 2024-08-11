@ECHO %date% %time%

CALL ..\SET_PYTHON_HOME.bat
IF NOT DEFINED PYTHON_HOME @Echo Python not found & @pause & @exit

@rem https://dev.to/m4cs/compressing-videos-easily-on-windows-w-ffmpeg-and-registry-files-5fin

@echo ON

@Echo print args from .reg file
@for %%a in (%*) do @ECHO Arg: %%a

@set input_image_full_path=%1
@SET size_ratio_to_apply=%2
@set move_input_file_after_process=%3%

if %move_input_file_after_process% == "true" (
	@if not exist original @MD original
)

@Echo input_image_full_path:%input_image_full_path%
@call %PYTHON_HOME%\python.exe D:\\GitHub\\yanndanielou-programmation\\Python\\ResizeJpg\\resize_jpg_reduce_by_ratio.py %input_image_full_path% %size_ratio_to_apply% %move_input_file_after_process%

timeout /t 3