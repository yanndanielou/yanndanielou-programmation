#from pywinauto.application import Application
import pyautogui

#For logs
import LoggerConfig
import logging
import sys
import random


import time

def window_SMT3_blocked_detected(window_SMT3_blocked):

    window_SMT3_blocked.show()
    time.sleep(1)
    window_SMT3_blocked.activate()
    pyautogui.hotkey('tab') 
    time.sleep(1)
    pyautogui.hotkey('tab')
    time.sleep(5) 
    pyautogui.hotkey('enter') 
                
def main(argv):
    
    log_file_name = 'ArreterSimulationSmtTroisRienNeBouge' + "." +  str(random.randrange(100)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    
    title_fenetre_blocante_smt3 = "Rien ne bouge.."
    list_of_windows_SMT3_blocked=pyautogui.getWindowsWithTitle(title_fenetre_blocante_smt3)

    logging.debug("There are " + str(len(list_of_windows_SMT3_blocked)) + " windows with title " + title_fenetre_blocante_smt3)
    if len(list_of_windows_SMT3_blocked) > 0:
        LoggerConfig.printAndLogInfo("There are " + str(len(list_of_windows_SMT3_blocked)) + " windows with title " + title_fenetre_blocante_smt3)
        for window_SMT3_blocked in list_of_windows_SMT3_blocked:
            window_SMT3_blocked_detected(window_SMT3_blocked)


if __name__ == "__main__":
    main(sys.argv[1:])
