SET GRADLE_PATH="C:\Program Files\java\Gradle\gradle-8.4-all\gradle-8.4\bin"
SET GRADLE_VERSION=8.4

call %GRADLE_PATH%\gradle.bat" wrapper --gradle-version %GRADLE_VERSION%
call gradlew build



