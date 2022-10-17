rem @ECHO OFF

REM -------------------------------------
REM Configuration générale
REM -------------------------------------
set PROJET_PREFIX=%COMPUTERNAME%
set APPLICATION_NAME=%PROJET_PREFIX% Performance Logs

REM -------------------------------------
REM Configuration des logs
REM -------------------------------------
REM CFG_FILENAME
REM Fichier contenant la liste des éléments à surveiller par le compteur de performance
IF NOT DEFINED CFG_FILENAME set CFG_FILENAME=PerfLogsSetup

REM LOGGER_FOLDER
REM Répertoire de sauvegarde des fichiers logs (format blg)
set LOGGER_FOLDER=%TMP%\PerfLogs

REM LOG_FILENAME
REM Radical du nom de fichier du log
set LOG_FILENAME=%PROJET_PREFIX%PerfsLogs_%COMPUTERNAME%

REM LOG_INTERVAL
REM Format <[[hh:]mm:]ss>. Intervalle d'échantillonnage pour les ensembles de compteurs de performances.
REM Exemple : Si vaut 30, les compteurs sont enregistrés toutes les 30 secondes, 
REM           Si vaut 01:30:00, ils sont enregistrés toutes les heures et demie.
set LOG_INTERVAL=1

REM LOG_MAX_FILE_SIZE
REM Taille maximale du fichier en Mo
set LOG_MAX_FILE_SIZE=50

REM -------------------------------------
REM Configuration des backup des logs
REM -------------------------------------
REM SEVEN_ZIP_PATH
REM Répertoire ou est stocké l'utilitaire "7-zip"
SET SEVEN_ZIP_PATH=C:\Produits\7-Zip
IF NOT EXIST "%SEVEN_ZIP_PATH%" SET SEVEN_ZIP_PATH=%PROGRAMFILES%\7-Zip
IF NOT EXIST "%SEVEN_ZIP_PATH%" SET SEVEN_ZIP_PATH=%PROGRAMFILES(X86)%\7-Zip

REM Use 7z which is more performant than zip
SET FILE_COMPRESSION_EXTENSION=7z

SET ZIPPED_PERFLOGS_DIRECTORY=D:\%PROJET_PREFIX%_Configuration\Backup_Activity

REM BACKUP_AND_ZIP_BAT
REM Nom du fichier BAT qui sauvegarde les logs
set BACKUP_AND_ZIP_BAT=PerfLogsBackup.bat

REM BACKUP_FILENAME
REM RRadical du nom du fichier de backup des logs
set BACKUP_FILENAME=%PROJET_PREFIX%PerfLogsBackup_%COMPUTERNAME%

REM BACKUP_KEEP_PERIOD  
REM Nombre de jours de conservation des backups.
set BACKUP_KEEP_PERIOD=150

REM BACKUP_TIME  
REM Spécifie l'heure choisie pour exécuter la tâche de backup. Le format d'heure est HH:MM:SS (sur 24 heures).
set BACKUP_TIME=02:00:00

REM USER_ADMIN
REM Specidie le nom de l'utilisateur lancant les perflogs. Il s'agit le plus souvent de l'administrateur
REM L'etoile signifie que le mot de passe de l'admin sera demande lors de la creation du compteur.
set USER_ADMIN=%USERNAME%

REM CHANGE_FILE_TIME
REM Spécifie le délai avant changement de fichier d'enregistrement HH:MM:SS
set CHANGE_FILE_TIME=72:00:00
