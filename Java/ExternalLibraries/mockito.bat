powershell -Command "(New-Object Net.WebClient).DownloadFile('https://github.com/mockito/mockito/archive/refs/tags/v5.5.0.zip', 'mockito-5.5.0.zip')

powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/mockito/mockito-core/5.5.0/mockito-core-5.5.0.jar', 'mockito-core-5.5.0.jar')
powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/mockito/mockito-junit-jupiter/5.5.0/mockito-junit-jupiter-5.5.0.jar', 'mockito-junit-jupiter-5.5.0.jar')

powershell -command "Expand-Archive -Force 'mockito-5.5.0.zip' 'mockito-5.5.0'"


timeout /t 30


