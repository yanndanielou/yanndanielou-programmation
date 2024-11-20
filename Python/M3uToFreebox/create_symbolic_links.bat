@IF NOT EXIST Dependencies MD Dependencies

@MKLINK /D C:\D_Drive\GitHub\yanndanielou-programmation\Python\Logger C:\D_Drive\GitHub\yanndanielou-programmation\Python\M3uToFreebox\Logger

@MKLINK /D ..\Logger Dependencies\Logger

timeout /t 10
pause