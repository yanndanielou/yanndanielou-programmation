
:DOWNLOAD_FILE_FROM_LINK

ECHO DOWNLOAD_FILE_FROM_LINK 
for %%a in (%1:/= %) do set fileName=%%a
echo fileName is %fileName%


