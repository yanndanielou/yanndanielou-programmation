MD mockito-5.5.0

powershell -Command "(New-Object Net.WebClient).DownloadFile('https://github.com/mockito/mockito/archive/refs/tags/v5.5.0.zip', 'mockito-5.5.0/mockito-5.5.0.zip')

powershell -command "Expand-Archive -Force 'mockito-5.5.0/mockito-5.5.0.zip' 'mockito-5.5.0'"



timeout /t 30


