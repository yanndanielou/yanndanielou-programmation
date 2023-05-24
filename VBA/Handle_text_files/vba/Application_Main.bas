Attribute VB_Name = "Application_Main"
'Option Explicit


        


Sub Main_macro()
	Call create_text_file()
End Sub

Private Sub create_text_file()

	Dim output_text_file_1 As Integer

    Open "\output\output_text_file_1.txt" For Output Access Write As #output_text_file_1
		
		Print #Fic, "Current time:"
		Print #Fic, Format(DateTime.Now, "yyyy-MM-dd hh:mm:ss")	

	Close #output_text_file_1 


End Sub
