#from pywinauto.application import Application
import pyautogui

#For logs
import LoggerConfig
import logging
import sys
import random


import time
from datetime import timedelta


def infinite_loop_detect_mise_en_veille_par_absence_activite():
    duree_attente_theorique_en_secondes = 1
    time_before_wait = time.time()

    while True:    
        logging.debug("Begin to wait")
        time.sleep(duree_attente_theorique_en_secondes)
        time_after_wait = time.time()
        duree_wait_realisee = time_after_wait - time_before_wait
        time_before_wait = time.time() # repris juste apres la precedente mesure pour detecter une veille n'importe où dans le programme
        if duree_wait_realisee > 2*duree_attente_theorique_en_secondes:
            #LoggerConfig.printAndLogInfo("Mise en veille du PC detectee, aux environs de :" + time.localtime(time_before_wait).strftime("%m/%d/%Y, %H:%M:%S") + " avec reprise aux environs de : " + time.localtime(time_before_wait).strftime("%m/%d/%Y, %H:%M:%S") + " . Duree de la veille estimee: " + str(timedelta(seconds=duree_wait_realisee)))
            LoggerConfig.printAndLogInfo("Mise en veille du PC detectee, aux environs de :" + time.strftime('%Y-%m-%dT%H:%M:%SZ', time.localtime(time_before_wait)) + " avec reprise aux environs de : " + time.strftime('%Y-%m-%dT%H:%M:%SZ', time.localtime(time_after_wait)) + " . Duree de la veille estimee: " + str(timedelta(seconds=duree_wait_realisee)))
        #else:
        #    logging.debug("Pas de mise en veille du PC detectee, time_before_wait: " + time.localtime(time_before_wait).strftime("%m/%d/%Y, %H:%M:%S") + " time_before_wait: " + time.localtime(time_before_wait).strftime("%m/%d/%Y, %H:%M:%S") + " . Duree de la non veille estimee: " + str(timedelta(seconds=duree_wait_realisee)))
 

def main(argv):
    
    log_file_name = 'DetecterMiseEnVeilleParAbsenceActiviteProgramme' + "." +  str(random.randrange(100)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.printAndLogInfo("Application start")

    infinite_loop_detect_mise_en_veille_par_absence_activite()

    LoggerConfig.printAndLogInfo("Application ended")

if __name__ == "__main__":
    main(sys.argv[1:])
