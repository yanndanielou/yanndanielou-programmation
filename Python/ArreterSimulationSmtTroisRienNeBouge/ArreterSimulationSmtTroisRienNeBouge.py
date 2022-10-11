#from pywinauto.application import Application
import pyautogui

#For logs
import LoggerConfig
import logging
import sys
import random


import time

def window_SMT3_blocked_detected(window_SMT3_blocked):
    LoggerConfig.printAndLogInfo("Show popup")
    window_SMT3_blocked.show()
    time.sleep(1)
    LoggerConfig.printAndLogInfo("Activate popup")
    window_SMT3_blocked.activate()
    time.sleep(1)
    #LoggerConfig.printAndLogInfo("Give focus to 'Continue' button")
    pyautogui.hotkey('tab') 
    time.sleep(1)
    #LoggerConfig.printAndLogInfo("Give focus to 'Arreter tout' button")
    pyautogui.hotkey('tab')
    time.sleep(5) 
    LoggerConfig.printAndLogInfo("Press enter to close window")
    pyautogui.hotkey('enter') 

def infinite_loop_detect_SMT3_blocked_popup():
    title_fenetre_blocante_smt3 = "Rien ne bouge.."
    list_of_windows_SMT3_blocked=pyautogui.getWindowsWithTitle(title_fenetre_blocante_smt3)

    while True:
        logging.debug("There are " + str(len(list_of_windows_SMT3_blocked)) + " windows with title " + title_fenetre_blocante_smt3)
        if len(list_of_windows_SMT3_blocked) > 0:
            LoggerConfig.printAndLogInfo("There are " + str(len(list_of_windows_SMT3_blocked)) + " windows with title " + title_fenetre_blocante_smt3)
            for window_SMT3_blocked in list_of_windows_SMT3_blocked:
                window_SMT3_blocked_detected(window_SMT3_blocked)

        logging.debug("Begin to wait")
        
        time.sleep(30)

def main(argv):
    
    log_file_name = 'ArreterSimulationSmtTroisRienNeBouge' + "." +  str(random.randrange(100)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.printAndLogInfo("Application start")

    infinite_loop_detect_SMT3_blocked_popup()

    LoggerConfig.printAndLogInfo("Application ended")

if __name__ == "__main__":
    main(sys.argv[1:])
