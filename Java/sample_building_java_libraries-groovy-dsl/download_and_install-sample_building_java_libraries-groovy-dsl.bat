


call ..\ExternalLibraries\DOWNLOAD_FILE_FROM_LINK.bat "https://docs.gradle.org/current/samples/zips/sample_building_java_libraries-groovy-dsl.zip"
powershell -command "Expand-Archive -Force 'sample_building_java_libraries-groovy-dsl.zip' '.'"


timeout /t 10



