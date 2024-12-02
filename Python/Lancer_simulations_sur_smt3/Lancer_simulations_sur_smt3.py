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

from GenerateMEDataModel import GrapheSingleton
from GenerateMEDataModel import SimulationResultsSingleton
from GenerateMEDataModel import Graphe
from GenerateMEDataModel import PT2ADir
from GenerateMEDataModel import remove_proxies

from os import system

    

def Lancer_simulations_sur_smt3(smt3_port, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter):

    remove_proxies()

    __graphe = GrapheSingleton.Load(GrapheSingleton,"inputs\\Graphe-NG_ReadyForSimu.gme")

    LoggerConfig.print_and_log_info("Nombre de missions élémentaires de régulation : " + str(len(__graphe.missionsElementairesRegulation)))
    LoggerConfig.print_and_log_info("Nombre de missions élémentaires : " + str(len(__graphe.missionsElementaires)))

    LoggerConfig.print_and_log_info("Load empty Simulation.sme") 
    simuResults = SimulationResultsSingleton.Load(SimulationResultsSingleton, "inputs\\Simulation_empty.sme")



    now_as_datetime = datetime.datetime.now()
    now_as_string_for_file_suffix = now_as_datetime.strftime("%Y_%m_%d %H_%M_%S %f")

#      simuResults = SimulationResultsSingleton.Load(SimulationResultsSingleton, "D:\\SMT3_generation\\SMT3\\Simulation.sme")

    ignoredMER = ['']
    LoggerConfig.print_and_log_info("ignoredMER : " + str(ignoredMER)) 

    LoggerConfig.print_and_log_info("ProduireSimplesRuns") 
    pas_sauvegarde = 10
    __graphe.ProduireSimplesRuns("http://127.0.0.1:" + str(smt3_port), 0.4, 30.0,pas_sauvegarde,1.1,ignoredMER,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter, now_as_string_for_file_suffix)
  
    LoggerConfig.print_and_log_info("End of application") 



def main(argv):
    
    log_file_name = 'Lancer_simulations_sur_smt3-' +  str(random.randrange(100000)) + ".log"
    LoggerConfig.configure_logger(log_file_name)    
    LoggerConfig.print_and_log_info('Start application. Log file name: ' + log_file_name)


    list_arguments_names = ["numero_premiere_mission_elementaire_a_traiter=","numero_derniere_mission_elementaire_a_traiter=","port_smt3="]
    
    numero_premiere_mission_elementaire_a_traiter = None
    numero_derniere_mission_elementaire_a_traiter = None
    port_smt3 = None

    try:
        opts, args = getopt.getopt(argv,"hi:o:", list_arguments_names)
    except getopt.GetoptError as err:
        errorMessage = "Unsupported arguments list." + str(err) + " Allowed arguments:" + str(list_arguments_names) + ". Application stopped"
        LoggerConfig.print_and_log_critical_and_kill(errorMessage)
    for opt, arg in opts:
        if opt in ("-h", "--help"):
            LoggerConfig.print_and_log_info("Allowed arguments:" + str(list_arguments_names) + ". Application stopped")
            sys.exit()
        elif opt == "--numero_premiere_mission_elementaire_a_traiter":
            numero_premiere_mission_elementaire_a_traiter = int(arg)
        elif opt == "--numero_derniere_mission_elementaire_a_traiter":
            numero_derniere_mission_elementaire_a_traiter= int(arg)
        elif opt == "--port_smt3":
            port_smt3 = arg
        else:
            LoggerConfig.print_and_log_critical_and_kill (" Option:" + opt + " unknown with value:" + opt + ". Allowed arguments:" + str(list_arguments_names) + ". Application stopped")

    system("title " + " Lancer_simulations_sur_smt3 " + str(numero_premiere_mission_elementaire_a_traiter) + " "  + str(numero_derniere_mission_elementaire_a_traiter) + " "  + str(port_smt3) + " " )
    Lancer_simulations_sur_smt3(port_smt3,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)

    LoggerConfig.print_and_log_info('End application')

    
def launch(smt3_port, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter):

    
    log_file_name = 'Lancer_simulations_sur_smt3-' + str(smt3_port) + '' + "." +  str(random.randrange(100)) + ".log"
    LoggerConfig.configure_logger(log_file_name)    
    LoggerConfig.print_and_log_info('Start application. Log file name: ' + log_file_name)
    Lancer_simulations_sur_smt3(smt3_port,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)
    LoggerConfig.print_and_log_info('End application')
    
if __name__ == "__main__":
    main(sys.argv[1:])
