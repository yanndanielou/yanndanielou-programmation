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

        


Sub Load_History_Analyser_Files()

    SafeScriptImport ("Application_Main.bas")
    
End Sub


Sub Clear_all_code_Files()
Dim i As Integer
Dim nom As String
Dim typeObj As String
    For Each VBComp In ThisWorkbook.VBProject.VBComponents
        nom = VBComp.name
        
        typeObj = VBComp.Type()
        ' on épargne les type feuille et formulaire
        'If (nom <> "ImportAll" And typeObj <> 100 And typeObj <> 3) Then
        If (nom <> "ImportAllCode" And typeObj <> 100) Then
            ThisWorkbook.VBProject.VBComponents.Remove (VBComp)
        End If
     Next VBComp

End Sub

Sub CloseWorkbooks()
    Dim Wbk As Excel.Workbook
    For Each Wbk In Excel.Workbooks
        Wbk.Close
    Next
End Sub
