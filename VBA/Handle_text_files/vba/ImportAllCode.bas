Attribute VB_Name = "ImportAllCode"
'Option Explicit


'this subroutine load the file given in parameter in the vba subfolder of the current document
' you dont have to specify the vba\ part
Sub SafeScriptImport(fileName As String)
    Dim i As Integer
    Dim nom As String
    Dim nomModule As String
    Dim Path As String
    
    Path = ThisWorkbook.Path() & "\vba\"
    On Error Resume Next
    nomModule = LCase(Left(fileName, InStr(fileName, ".") - 1))
    
    For Each VBComp In ThisWorkbook.VBProject.VBComponents
        nom = LCase(VBComp.name)
        If (nom <> "ImportAllCode" And nomModule = nom) Then
            ThisWorkbook.VBProject.VBComponents.Remove (VBComp)
        End If
     Next VBComp
     Application.VBE.ActiveVBProject.VBComponents.Import (Path & fileName)
End Sub

        


Sub Load_all_code_Files()

    SafeScriptImport ("Application_Main.bas")
    
End Sub