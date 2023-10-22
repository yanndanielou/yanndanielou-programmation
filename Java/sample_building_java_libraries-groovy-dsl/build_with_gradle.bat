SET GRADLE_PATH="C:\Program Files\java\Gradle\gradle-8.4-all\gradle-8.4\bin"
SET GRADLE_VERSION=8.4
SET JDK_HOME="C:\Program Files\java\jdk-21"

ECHO %DATE% %TIME% >> gradle_logs.log

SET JAVA_HOME=%JDK_HOME%
call gradlew build >> gradle_logs.log  2>&1

timeout /t 15