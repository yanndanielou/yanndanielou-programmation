$Path = "D:/GitHub/yanndanielou-programmation/Python/Sandbox/Intro-To-TKinter-Youtube-Course/master/Intro-To-TKinter-Youtube-Course-master"
$OldText = "c:/gui/"
$NewText = "D:/GitHub/yanndanielou-programmation/Python/Sandbox/Intro-To-TKinter-Youtube-Course/master/Intro-To-TKinter-Youtube-Course-master/"
 
Get-ChildItem $Path -Filter *.py | ForEach-Object {
    (Get-Content $_.FullName) -replace $OldText, $NewText | Set-Content $_.FullName
}


#Read more: https://www.sharepointdiary.com/2021/04/string-replacement-in-powershell.html#ixzz8sfeBdUHQ
