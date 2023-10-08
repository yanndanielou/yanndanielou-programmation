call DOWNLOAD_FILE_FROM_LINK https://repo1.maven.org/maven2/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4-javadoc.jar
Pause

echo https://repo1.maven.org/maven2/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4-javadoc.jar
echo https://repo1.maven.org/maven2/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4-sources.jar
echo https://repo1.maven.org/maven2/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4.jar
echo https://repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.13.0/commons-lang3-3.13.0-sources.jar
echo https://repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.13.0/commons-lang3-3.13.0.jarv
echo https://repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.13.0/commons-lang3-3.13.0-javadoc.jar
echo https://repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.13.0/commons-lang3-3.13.0-test-sources.jarv
echo https://repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.13.0/commons-lang3-3.13.0-tests.jar





echo powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo1.maven.org/maven2/org/apache/commons/commons-collections4/4.4/commons-collections4-4.4-javadoc.jar', 'commons-collections4-4.4-javadoc.jar')

timeout /t 10

