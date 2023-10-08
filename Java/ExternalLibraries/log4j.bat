


powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/apache/logging/log4j/log4j-api/2.20.0/log4j-api-2.20.0.jar', 'log4j-api-2.20.0.jar')
powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/apache/logging/log4j/log4j-core/2.20.0/log4j-core-2.20.0.jar', 'log4j-core-2.20.0.jar')


timeout /t 30

