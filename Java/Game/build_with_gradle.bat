call clean_with_gradle.bat

SET GRADLE_PATH="C:\Program Files\java\Gradle\gradle-8.4-all\gradle-8.4\bin"
SET GRADLE_VERSION=8.4
SET JDK_HOME="C:\Program Files\java\jdk-21"

ECHO %DATE% %TIME% >> gradle_logs.log

SET JAVA_HOME=%JDK_HOME%

call %GRADLE_PATH%\gradle.bat wrapper --gradle-version %GRADLE_VERSION%  >> gradle_logs.log  2>&1
call gradlew build >> gradle_logs.log  2>&1

ECHO %DATE% %TIME% >> gradle_logs.log

xcopy lib\build\libs\*.jar ..\InternalLibraries\

timeout /t 15