#For logs
import param
#For logs
import LoggerConfig
import logging
import sys
import random

import getopt


import os
import sys
sys.path.append("..")

from GenerateMEDataModel import GrapheSingleton
from GenerateMEDataModel import SimulationResultsSingleton
from GenerateMEDataModel import Graphe
from GenerateMEDataModel import PT2ADir
from GenerateMEDataModel import remove_proxies

from os import system

    

def ProduireSimplesRunsAndExporterSimplesRunsSimulations(smt3_port, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter):
    #pointsOptimisationsCBTC = ['HAUSSMANN_SL_V31', 'HAUSSMANN_SL_V32', 'HAUSSMANN_SL_V33', 'HAUSSMANN_SL_V34', 'MAGENTA_V51', 'MAGENTA_V52', 'MAGENTA_V53', 'MAGENTA_V54', 'PTA_SDP_T3', 'ROSA_PARKS_V1', 'ROSA_PARKS_V2', 'PANTIN_V1EOLE', 'PANTIN_V2EOLE']
    #gareTerminus = []

    remove_proxies()

    __graphe = GrapheSingleton.Load(GrapheSingleton,"D:\\SMT3_generation\\SMT3\\Graphe-NG_ReadyForSimu.gme")

    LoggerConfig.print_and_log_info("Nombre de missions élémentaires de régulation : " + str(len(__graphe.missionsElementairesRegulation)))
    LoggerConfig.print_and_log_info("Nombre de missions élémentaires : " + str(len(__graphe.missionsElementaires)))

    LoggerConfig.print_and_log_info("Load empty Simulation.sme") 
    simuResults = SimulationResultsSingleton.Load(SimulationResultsSingleton, "D:\\SMT3_generation\\SMT3\\Simulation_empty.sme")


#      simuResults = SimulationResultsSingleton.Load(SimulationResultsSingleton, "D:\\SMT3_generation\\SMT3\\Simulation.sme")

    ignoredMER = ['TRAMTRAIN_P27_1TER_INOUT|NOISY_K_P27_1RN_INOUT']
    LoggerConfig.print_and_log_info("ignoredMER : " + str(ignoredMER)) 

    LoggerConfig.print_and_log_info("ProduireSimplesRuns") 
    pas_sauvegarde = 10
    __graphe.ProduireSimplesRuns("http://127.0.0.1:" + str(smt3_port), 0.4, 30.0, "D:\\SMT3_generation\\SMT3\\Simulation_output-" + str(smt3_port) + ".sme",pas_sauvegarde,1.1,ignoredMER,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)
  
    LoggerConfig.print_and_log_info("ExporterSimplesRunsSimulations") 
    simuResults.ExporterSimplesRunsSimulations("D:\\SMT3_generation\\SMT3\\SimplesRunsSimulationsResults-" + str(smt3_port) + ".csv")
 
    #__graphe.EstimerNombreDeSimulation()

    LoggerConfig.print_and_log_info("Liste des points de contrôle")
    for i in sorted (__graphe.pointsDeControle.keys()) :
        LoggerConfig.print_and_log_info(i)

    LoggerConfig.print_and_log_info("Liste des transitions")
    for i in __graphe.transitions.values():
        i.print()


def main(argv):
    
    log_file_name = 'ProduireSimplesRunsAndExporterSimplesRunsSimulations-' +  str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.print_and_log_info('Start application. Log file name: ' + log_file_name)


    list_arguments_names = ["numero_premiere_mission_elementaire_a_traiter=","numero_derniere_mission_elementaire_a_traiter=","port_smt3="]
    
    numero_premiere_mission_elementaire_a_traiter = None
    numero_derniere_mission_elementaire_a_traiter = None
    port_smt3 = None

    try:
        opts, args = getopt.getopt(argv,"hi:o:", list_arguments_names)
    except getopt.GetoptError as err:
        errorMessage = "Unsupported arguments list." + str(err) + " Allowed arguments:" + str(list_arguments_names) + ". Application stopped"
        LoggerConfig.printAndLogCriticalAndKill(errorMessage)
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
            LoggerConfig.printAndLogCriticalAndKill (" Option:" + opt + " unknown with value:" + opt + ". Allowed arguments:" + str(list_arguments_names) + ". Application stopped")

    system("title " + " ProduireSimplesRunsAndExporterSimplesRunsSimulations " + str(numero_premiere_mission_elementaire_a_traiter) + " "  + str(numero_derniere_mission_elementaire_a_traiter) + " "  + str(port_smt3) + " " )
    ProduireSimplesRunsAndExporterSimplesRunsSimulations(port_smt3,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)

    LoggerConfig.print_and_log_info('End application')

    
def launch(smt3_port, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter):

    
    log_file_name = 'GenerateME-LoadGraphe-SimuSMT-' + str(smt3_port) + '' + "." +  str(random.randrange(100)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.print_and_log_info('Start application. Log file name: ' + log_file_name)
    ProduireSimplesRunsAndExporterSimplesRunsSimulations(smt3_port,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)
    LoggerConfig.print_and_log_info('End application')
    
if __name__ == "__main__":
    main(sys.argv[1:])
