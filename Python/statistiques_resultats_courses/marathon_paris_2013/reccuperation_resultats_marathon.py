# -*-coding:Latin-1 -*
import win32api
import win32gui
import win32con
import time
import os # On importe le module os
from ctypes import *
user32 = windll.user32

#ouverture de notepad pour stocker les resultats au format texte(pas notepad++ car celui-ci est utilisé pour le dev)
os.system("start notepad")

#ouvrir le navigateur internet avec le lien du site des résultats
os.system("start http://www.aso.fr/massevents/resultats/index.php?langue=fr^&course=mar13^&version=2^&width=935#")

#attendre que la page soit entièrement chargée
time.sleep(4.0) 

#pointeur vers la fenêtre du notepad
hw_notepad = win32gui.FindWindow('Notepad', None)
hw_chrome = win32gui.FindWindowEx(0,0,0, 'Semi-Marathon Paris 2011 - Google Chrome')

current_page = 0
nombre_page_results = 773

while current_page < nombre_page_results:
	current_page+=1	
	#enregistrer les données de la page courante
	
	
	# #selection du panneau
	user32.SetCursorPos(10, 450)
	user32.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
	time.sleep(0.1)
	user32.SetCursorPos(100, 450)
	time.sleep(0.1)
	user32.SetCursorPos(950, 450)
	user32.mouse_event(win32con.MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
	
	
	# #copie de la selection
	user32.keybd_event(win32con.VK_CONTROL,0,0,0) #is the code for KEYDOWN
	time.sleep(0.05)
	user32.keybd_event(0x43,0,0,0) #is the code for KEYDOWN
	time.sleep(0.1)
	user32.keybd_event(win32con.VK_CONTROL,0,win32con.KEYEVENTF_KEYUP,0) #is the code for KEYDOWN
	time.sleep(0.05)
	user32.keybd_event(0x43,0,win32con.KEYEVENTF_KEYUP,0) #is the code for KEYDOWN
	
	#focus du notepad
	win32gui.SetForegroundWindow(hw_notepad)
	time.sleep(1.0)	
	
	#collage dans notepad
	#key up
	user32.keybd_event(win32con.VK_CONTROL,0,4,0) #is the code for KEYDOWN
	time.sleep(0.05)
	user32.keybd_event(0x56,0,4,0) #is the code for KEYDOWN
	time.sleep(0.1)
	user32.keybd_event(win32con.VK_CONTROL,0,win32con.KEYEVENTF_KEYUP,0) #is the code for KEYDOWN
	time.sleep(0.05)
	user32.keybd_event(0x56,0,win32con.KEYEVENTF_KEYUP,0) #is the code for KEYDOWN
	
	
	#reprise du focus dans chrome
	win32gui.SetForegroundWindow(hw_chrome)
	
	
	#descendre en bas de la page
	user32.SetCursorPos(1910, 1150)
	user32.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN + win32con.MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
	user32.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN + win32con.MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
	user32.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN + win32con.MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
	user32.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN + win32con.MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
	user32.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN + win32con.MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
	
	
	
	#clic dans une zone vide pour déselectionner la selection (permet de ne pas avoir le titre du tableau pour les prochains résultats)
	time.sleep(0.1)
	user32.SetCursorPos(1800, 300)
	user32.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN + win32con.MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
	
	#le bouton de navigation vers la page suivante n'est pas toujours au même endroit (x diffère, y identique)
	#en fonction du numéro de page, on adapte donc l'emplacement de la souris
	x_bouton_next_page = 0
	if current_page < 6:
		x_bouton_next_page = 827
	elif current_page == 6:
		x_bouton_next_page = 821
	elif current_page == 7:
		x_bouton_next_page = 815
	elif current_page == 8:
		x_bouton_next_page = 809
	elif current_page == 9:
		x_bouton_next_page = 806
	elif current_page < 96:
		x_bouton_next_page = 804
	elif current_page == 96:
		x_bouton_next_page = 802
	elif current_page == 97:
		x_bouton_next_page = 796
	elif current_page == 98:
		x_bouton_next_page = 791
	elif current_page == 99:
		x_bouton_next_page = 788
	else :
		x_bouton_next_page = 787
	
	#passer à la page suivante
	user32.SetCursorPos(x_bouton_next_page, 1130)
	time.sleep(0.5)
	user32.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN + win32con.MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
	
	#delai pour que la page suivante ait le temps de se charger
	time.sleep(1)
	