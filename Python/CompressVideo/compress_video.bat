ECHO %date%
ECHO %time%


@echo ON
SET subject=%*
for %%a in (%*) do set subject=%%a
C:\Users\fr232487\AppData\Local\Programs\Python\Python312\python.exe D:\\GitHub\\yanndanielou-programmation\\Python\\CompressVideo\\compress_video.py %subject%

rem call ffmpeg\\ffmpeg-2024-08-07-git-94165d1b79-full_build\\bin\\ffmpeg.exe 
timeout /t 30