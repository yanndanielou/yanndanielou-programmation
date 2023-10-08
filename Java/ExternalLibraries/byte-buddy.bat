powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy-agent/1.14.6/byte-buddy-agent-1.14.6.jar', 'byte-buddy-agent-1.14.6.jar')
powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/net/bytebuddy/byte-buddy/1.14.6/byte-buddy-1.14.6.jar', 'byte-buddy-1.14.6.jar')


timeout /t 30


