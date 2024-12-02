#from pywinauto.application import Application
import pyautogui

#For logs
import LoggerConfig
import logging
import sys
import random


import time

def window_SMT3_blocked_detected(window_SMT3_blocked):


    if not window_SMT3_blocked.isActive:
        LoggerConfig.print_and_log_info("SMT3 windows is hidden, show it")
        time.sleep(0.1)
        window_SMT3_blocked.minimize()
        time.sleep(0.1)
        window_SMT3_blocked.restore()

    time.sleep(0.1)
    logging.debug("Give focus to 'Continue' button")
    pyautogui.hotkey('tab') 
    time.sleep(0.1)
    logging.debug("Give focus to 'Arreter tout' button")
    pyautogui.hotkey('tab')
    time.sleep(1) 
    LoggerConfig.print_and_log_info("Press enter to close window")
    pyautogui.hotkey('enter') 

def infinite_loop_detect_SMT3_blocked_popup():
    title_fenetre_blocante_smt3 = "Rien ne bouge.."

    while True:    
        list_of_windows_SMT3_blocked=pyautogui.getWindowsWithTitle(title_fenetre_blocante_smt3)
        logging.debug("There are " + str(len(list_of_windows_SMT3_blocked)) + " windows with title " + title_fenetre_blocante_smt3)
        if len(list_of_windows_SMT3_blocked) > 0:
            LoggerConfig.print_and_log_info("There are " + str(len(list_of_windows_SMT3_blocked)) + " windows with title " + title_fenetre_blocante_smt3)
            for window_SMT3_blocked in list_of_windows_SMT3_blocked:
                window_SMT3_blocked_detected(window_SMT3_blocked)

        logging.debug("Begin to wait")
        
        time.sleep(1)

def main(argv):
    
    log_file_name = 'ArreterSimulationSmtTroisRienNeBouge' + "." +  str(random.randrange(100)) + ".log"
    LoggerConfig.configure_logger(log_file_name)    
    LoggerConfig.print_and_log_info("Application start")

    infinite_loop_detect_SMT3_blocked_popup()

    LoggerConfig.print_and_log_info("Application ended")

if __name__ == "__main__":
    main(sys.argv[1:])
