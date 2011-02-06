VERSION 5.00
Object = "{648A5603-2C6E-101B-82B6-000000000014}#1.1#0"; "MSCOMM32.OCX"
Begin VB.Form Form1 
   Caption         =   "Form1"
   ClientHeight    =   6315
   ClientLeft      =   60
   ClientTop       =   450
   ClientWidth     =   7650
   LinkTopic       =   "Form1"
   ScaleHeight     =   6315
   ScaleWidth      =   7650
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton Commande_afficher_texte_entre 
      Caption         =   "envoyer le texte au LCD"
      Enabled         =   0   'False
      Height          =   735
      Left            =   4200
      TabIndex        =   10
      Top             =   3240
      Width           =   1815
   End
   Begin VB.TextBox Txt_bas_lcd 
      Height          =   285
      Left            =   3960
      MaxLength       =   15
      TabIndex        =   7
      Text            =   "textebas"
      Top             =   2640
      Width           =   2535
   End
   Begin VB.TextBox Txt_haut_lcd 
      Height          =   285
      Left            =   3960
      MaxLength       =   15
      TabIndex        =   6
      Text            =   "textehaut"
      Top             =   1920
      Width           =   2535
   End
   Begin VB.CommandButton Commande_recevoir_données 
      Caption         =   "recevoir données"
      Enabled         =   0   'False
      Height          =   495
      Left            =   240
      TabIndex        =   5
      Top             =   3600
      Width           =   1695
   End
   Begin VB.CheckBox Check_checksum_faux 
      Caption         =   "envoyer un faux checksum"
      Height          =   495
      Left            =   360
      TabIndex        =   4
      Top             =   960
      Width           =   3135
   End
   Begin VB.CommandButton Commande_bonjour_lcd 
      Caption         =   "afficher Bonjour"
      Enabled         =   0   'False
      Height          =   495
      Left            =   240
      TabIndex        =   3
      Top             =   2880
      Width           =   1695
   End
   Begin VB.CommandButton Commande_relais 
      Caption         =   "commander le relais"
      Enabled         =   0   'False
      Height          =   495
      Left            =   240
      TabIndex        =   2
      Top             =   1560
      Width           =   1695
   End
   Begin VB.CommandButton Commande_led 
      Caption         =   "allumer la led"
      Enabled         =   0   'False
      Height          =   495
      Left            =   240
      TabIndex        =   1
      Top             =   2160
      Width           =   1695
   End
   Begin VB.CommandButton Commande_connecter 
      Caption         =   "Connexion"
      Height          =   495
      Left            =   1200
      TabIndex        =   0
      Top             =   240
      Width           =   3975
   End
   Begin MSCommLib.MSComm MSComm 
      Left            =   240
      Top             =   5040
      _ExtentX        =   1005
      _ExtentY        =   1005
      _Version        =   393216
      DTREnable       =   -1  'True
   End
   Begin VB.Label Label2 
      Caption         =   "texte de la ligne du bas du LCD"
      Height          =   255
      Left            =   3960
      TabIndex        =   9
      Top             =   2400
      Width           =   2415
   End
   Begin VB.Label Label1 
      Caption         =   "texte de la ligne du haut du LCD"
      Height          =   255
      Left            =   3960
      TabIndex        =   8
      Top             =   1680
      Width           =   2535
   End
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Declare Sub Sleep Lib "kernel32" (ByVal dwMilliseconds As Long)




Private Function envoyer_commande(octet_commande As Integer, octet_nbre_parametres As Integer)
Dim checksum As Integer
Dim i As Integer
Dim recu As String

checksum = octet_commande + octet_nbre_parametres

If Check_checksum_faux.Value = 1 Then checksum = checksum + 1


MSComm.Output = Chr$(254) & Chr$(octet_commande) & Chr$(octet_nbre_parametres) & Chr$(checksum) & Chr$(255)
Sleep 500
recu = MSComm.Input

If recu = "ERRCHEC" Then
 MsgBox "La commande envoyée à la platine n'a pas été validée par la platine" & vbCrLf, vbInformation, "Projet liaison série"
 Exit Function
End If




End Function


Private Function envoyer_parametre(parametre As String)
Dim recu As String

Dim i As Integer
Dim longueur_parametre As Integer
Dim checksum1 As Integer
Dim checksum2 As Integer
Dim checksum3 As Integer
Dim somme As Integer


checksum1 = 0
checksum2 = 0
checksum3 = 0

longueur_parametre = Len(parametre)


somme = 0

For i = 1 To longueur_parametre
 somme = somme + Asc(Mid(parametre, i, 1))
Next i


For i = 0 To 100
    If somme >= 255 Then
        checksum1 = checksum1 + 1
        somme = somme - 255
        
    Else
        checksum2 = somme
    End If
Next i


If checksum1 = 253 Then
  checksum1 = checksum1 - 1
  checksum3 = 1
End If

If checksum2 = 253 Then
  checksum2 = checksum1 - 2
  checksum3 = 2
End If


If Check_checksum_faux.Value = 1 Then checksum1 = checksum1 + 1


MSComm.Output = Chr$(253) & Chr$(longueur_parametre) & parametre & Chr$(checksum1) & Chr$(checksum2) & Chr$(checksum3) & Chr$(255)
Sleep 500
recu = MSComm.Input

Sleep 500


End Function



Private Sub Commande_afficher_texte_entre_Click()
Dim chaine As String

Call envoyer_commande(3, 2)
chaine = "1" & Txt_haut_lcd.Text
Call envoyer_parametre(chaine)
Sleep 500
chaine = "2" & Txt_bas_lcd.Text
Call envoyer_parametre(chaine)

End Sub

Private Sub Commande_led_Click()

Call envoyer_commande(2, 0)

End Sub

Private Sub Commande_bonjour_lcd_Click()

Call envoyer_commande(3, 2)

Call envoyer_parametre("1BONJOUR BONJOUR")
Sleep 500
Call envoyer_parametre("2BONJOUR BONJOUR")


End Sub

Private Sub Commande_connecter_Click()
If MSComm.PortOpen = True Then MSComm.PortOpen = False
MSComm.Settings = "9600,N,8,1"
MSComm.PortOpen = True
Sleep 100

Commande_led.Enabled = MSComm.PortOpen
Commande_bonjour_lcd.Enabled = MSComm.PortOpen
Commande_relais.Enabled = MSComm.PortOpen
Commande_recevoir_données.Enabled = MSComm.PortOpen
Commande_afficher_texte_entre.Enabled = MSComm.PortOpen



End Sub

Private Sub Commande_recevoir_données_Click()
Dim recu As String
Sleep 500
recu = MSComm.Input

End Sub

Private Sub Commande_relais_Click()

Call envoyer_commande(1, 0)

End Sub

