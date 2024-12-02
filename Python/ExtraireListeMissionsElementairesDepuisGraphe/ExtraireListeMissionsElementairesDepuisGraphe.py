#For logs
import param
#For logs
import LoggerConfig
import logging
import sys
import random

import getopt

import datetime


import os
import sys
sys.path.append("..")

import GenerateMEDataModel
from GenerateMEDataModel import GrapheSingleton
from GenerateMEDataModel import SimulationResultsSingleton
from GenerateMEDataModel import Graphe
from GenerateMEDataModel import PT2ADir
from GenerateMEDataModel import remove_proxies

from os import system

    

def Lancer_simulations_sur_smt3():

    remove_proxies()

    __graphe = GrapheSingleton.Load(GrapheSingleton,"inputs\\Graphe-NG_ReadyForSimu.gme")

    LoggerConfig.print_and_log_info("Nombre de missions élémentaires de régulation : " + str(len(__graphe.missionsElementairesRegulation)))
    LoggerConfig.print_and_log_info("Nombre de missions élémentaires : " + str(len(__graphe.missionsElementaires)))

    LoggerConfig.print_and_log_info("Load empty Simulation.sme") 
    simuResults = SimulationResultsSingleton.Load(SimulationResultsSingleton, "inputs\\Simulation_empty.sme")



    now_as_datetime = datetime.datetime.now()
    now_as_string_for_file_suffix = now_as_datetime.strftime("%Y_%m_%d %H_%M_%S %f")

    LoggerConfig.print_and_log_info("ProduireSimplesRuns") 
    __graphe.ProduireSimplesRuns( now_as_string_for_file_suffix)
  
    LoggerConfig.print_and_log_info("End of application") 



def main(argv):
    
    log_file_name = 'ExtraireListeMissionsElementairesDepuisGraphe-' +  str(random.randrange(100000)) + ".log"
    LoggerConfig.configure_logger(log_file_name)    
    LoggerConfig.print_and_log_info('Start application. Log file name: ' + log_file_name)

    system("title " + " ExtraireListeMissionsElementairesDepuisGraphe ")
    Lancer_simulations_sur_smt3()

    LoggerConfig.print_and_log_info('End application')


if __name__ == "__main__":
    main(sys.argv[1:])
