SET GRADLE_PATH="C:\Program Files\java\Gradle\gradle-8.4-all\gradle-8.4\bin"
SET GRADLE_VERSION=8.4
SET JDK_HOME="C:\Program Files\java\jdk-21"

SET JAVA_HOME=%JDK_HOME%
call %GRADLE_PATH%\gradle.bat wrapper --gradle-version %GRADLE_VERSION%
call gradlew build


timeout /t 15