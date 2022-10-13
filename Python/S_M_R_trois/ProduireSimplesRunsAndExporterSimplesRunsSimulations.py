#For logs
import param
#For logs
import LoggerConfig
import logging
import sys
import random

import _thread


import os
import sys
sys.path.append("..")

from GenerateMEDataModel import GrapheSingleton
from GenerateMEDataModel import SimulationResultsSingleton
from GenerateMEDataModel import Graphe
from GenerateMEDataModel import PT2ADir
from GenerateMEDataModel import remove_proxies

    

def ProduireSimplesRunsAndExporterSimplesRunsSimulations(smt3_port, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter):
    #pointsOptimisationsCBTC = ['HAUSSMANN_SL_V31', 'HAUSSMANN_SL_V32', 'HAUSSMANN_SL_V33', 'HAUSSMANN_SL_V34', 'MAGENTA_V51', 'MAGENTA_V52', 'MAGENTA_V53', 'MAGENTA_V54', 'PTA_SDP_T3', 'ROSA_PARKS_V1', 'ROSA_PARKS_V2', 'PANTIN_V1EOLE', 'PANTIN_V2EOLE']
    #gareTerminus = []

    remove_proxies()

    __graphe = GrapheSingleton.Load(GrapheSingleton,"D:\\SMT3_generation\\SMT3\\Graphe-NG_ReadyForSimu.gme")

    LoggerConfig.printAndLogInfo("Nombre de missions élémentaires de régulation : " + str(len(__graphe.missionsElementairesRegulation)))
    LoggerConfig.printAndLogInfo("Nombre de missions élémentaires : " + str(len(__graphe.missionsElementaires)))

    LoggerConfig.printAndLogInfo("Load empty Simulation.sme") 
    simuResults = SimulationResultsSingleton.Load(SimulationResultsSingleton, "D:\\SMT3_generation\\SMT3\\Simulation_empty.sme")


#      simuResults = SimulationResultsSingleton.Load(SimulationResultsSingleton, "D:\\SMT3_generation\\SMT3\\Simulation.sme")

    ignoredMER = ['TRAMTRAIN_P27_1TER_INOUT|NOISY_K_P27_1RN_INOUT']
    LoggerConfig.printAndLogInfo("ignoredMER : " + str(ignoredMER)) 

    LoggerConfig.printAndLogInfo("ProduireSimplesRuns") 
    pas_sauvegarde = 10
    __graphe.ProduireSimplesRuns("http://127.0.0.1:" + str(smt3_port), 0.4, 30.0, "D:\\SMT3_generation\\SMT3\\Simulation_output-" + str(smt3_port) + ".sme",pas_sauvegarde,1.1,ignoredMER,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)
  
    LoggerConfig.printAndLogInfo("ExporterSimplesRunsSimulations") 
    simuResults.ExporterSimplesRunsSimulations("D:\\SMT3_generation\\SMT3\\SimplesRunsSimulationsResults-" + str(smt3_port) + ".csv")
 
    #__graphe.EstimerNombreDeSimulation()

    LoggerConfig.printAndLogInfo("Liste des points de contrôle")
    for i in sorted (__graphe.pointsDeControle.keys()) :
        LoggerConfig.printAndLogInfo(i)

    LoggerConfig.printAndLogInfo("Liste des transitions")
    for i in __graphe.transitions.values():
        i.print()

def launch(smt3_port, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter):
    log_file_name = 'GenerateME-LoadGraphe-SimuSMT-' + str(smt3_port) + '' + "." +  str(random.randrange(100)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.printAndLogInfo('Start application')
    ProduireSimplesRunsAndExporterSimplesRunsSimulations(smt3_port,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)
    LoggerConfig.printAndLogInfo('End application')

if __name__ == '__main__':
    launch(8080, 1, 10000)
