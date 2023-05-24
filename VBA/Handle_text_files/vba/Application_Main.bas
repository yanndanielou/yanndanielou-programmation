Attribute VB_Name = "Application_Main"
'Option Explicit


        


Sub Main_macro()
	Call create_text_file()
End Sub

Private Sub create_text_file()

	Dim output_text_file_1 As Integer
    output_text_file_1 = FreeFile

    Open ThisWorkbook.Path() & "\output\output_text_file_1.txt" For Output Access Write As #output_text_file_1
		
		Print #output_text_file_1, "Current time:"
		Print #output_text_file_1, Format(DateTime.Now, "yyyy-MM-dd hh:mm:ss")	

	Close #output_text_file_1 


End Sub
