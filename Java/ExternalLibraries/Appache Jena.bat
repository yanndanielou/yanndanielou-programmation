
call DOWNLOAD_FILE_FROM_LINK.bat "https://dlcdn.apache.org/jena/binaries/apache-jena-fuseki-4.10.0.zip"
call DOWNLOAD_FILE_FROM_LINK.bat "https://dlcdn.apache.org/jena/source/jena-4.10.0-source-release.zip"
call DOWNLOAD_FILE_FROM_LINK.bat "https://dlcdn.apache.org/jena/binaries/apache-jena-4.10.0.zip

powershell -command "Expand-Archive -Force 'vmockito-5.5.0.zip' 'mockito-5.5.0'"


timeout /t 15
