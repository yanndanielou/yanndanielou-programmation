SET GRADLE_PATH="C:\Program Files\java\Gradle\gradle-8.4-all\gradle-8.4\bin"
SET GRADLE_VERSION=8.4
SET JDK_HOME="C:\Program Files\java\jdk-21"

CALL clean_with_gradle.bat

ECHO %DATE% %TIME% > gradle_logs.log

SET JAVA_HOME=%JDK_HOME%
call %GRADLE_PATH%\gradle.bat wrapper --gradle-version %GRADLE_VERSION%  >> gradle_logs.log  2>&1
call gradlew build >> gradle_logs.log  2>&1
rem call gradlew shadowJar >> gradle_logs.log  2>&1


rem call gradlew jar >> gradle_logs.log  2>&1


ECHO %DATE% %TIME% >> gradle_logs.log



run_application_jar.bat

ECHO %DATE% %TIME% >> gradle_logs.log

timeout /t 15