@ECHO %date% %time%

@rem https://dev.to/m4cs/compressing-videos-easily-on-windows-w-ffmpeg-and-registry-files-5fin

@echo ON
@SET input_image_full_path=%*
@for %%a in (%*) do @set input_image_full_path=%%a

SET size_ratio_to_apply=0.1

@Echo input_image_full_path:%input_image_full_path%
@call C:\Users\fr232487\AppData\Local\Programs\Python\Python312\python.exe D:\\GitHub\\yanndanielou-programmation\\Python\\ResizeJpg\\resize_jpg_reduce_by_ratio.py %input_image_full_path% %size_ratio_to_apply%

timeout /t 3
