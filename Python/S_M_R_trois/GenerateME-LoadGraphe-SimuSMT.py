#For logs
import param
#For logs
import LoggerConfig
import logging
import sys
import random


import os
import sys
sys.path.append("..")

from GenerateMEDataModel import GrapheSingleton
from GenerateMEDataModel import SimulationResultsSingleton
from GenerateMEDataModel import Graphe
from GenerateMEDataModel import PT2ADir
from GenerateMEDataModel import remove_proxies

    

def testAdonfImport():
    pointsOptimisationsCBTC = ['HAUSSMANN_SL_V31', 'HAUSSMANN_SL_V32', 'HAUSSMANN_SL_V33', 'HAUSSMANN_SL_V34', 'MAGENTA_V51', 'MAGENTA_V52', 'MAGENTA_V53', 'MAGENTA_V54', 'PTA_SDP_T3', 'ROSA_PARKS_V1', 'ROSA_PARKS_V2', 'PANTIN_V1EOLE', 'PANTIN_V2EOLE']
    gareTerminus = []
    __AdonfFile = "D:\\SMT3_generation\\SMT3\\Donnees\\TGF_7_6_a_DC_SYS_ATSP_V00_01_75c_03_2020_07_13_WITHOUT_Rosa_Parks_area.xlsm"
    __PT2ADir = "D:\\SMT3_generation\\SMT3\\Donnees\\Postes new corrige\\"
    __IHMP_3_2_bisFile = "D:\\SMT3_generation\\SMT3\\Donnees\\Pièce 3.2 bis V3.xls"
    __dVisi = 10.
    __dBonGlissement = 200.
    __vBonGlissement = 30.
    __dMauvaisGlissement = 100.
    __vMauvaisGlissement = 10.
    __vA = 60.
    __dA = 100.
    __vMav = 30.
    __dRep = 10.
    __T_S = 20.
    __seuilMauvaisGlissement = 200.
    __DLibMG = 150.
    __DLibBG = 0.

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
    __graphe.ProduireSimplesRuns("http://127.0.0.1:8080", 0.4, 30.0, "D:\\SMT3_generation\\SMT3\\Simulation_output.sme",pas_sauvegarde,1.1,ignoredMER)
  
    LoggerConfig.print_and_log_info("ExporterSimplesRunsSimulations") 
    simuResults.ExporterSimplesRunsSimulations("D:\\SMT3_generation\\SMT3\\SimplesRunsSimulationsResults.csv")
 
    #__graphe.EstimerNombreDeSimulation()

    LoggerConfig.print_and_log_info("Liste des points de contrôle")
    for i in sorted (__graphe.pointsDeControle.keys()) :
        LoggerConfig.print_and_log_info(i)

    LoggerConfig.print_and_log_info("Liste des transitions")
    for i in __graphe.transitions.values():
        i.print()

def main():
    log_file_name = 'GenerateME-LoadGraphe-SimuSMT' + "." +  str(random.randrange(10000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.print_and_log_info('Start application. Log file name: ' + log_file_name)
    testAdonfImport()
    LoggerConfig.print_and_log_info('End application')

if __name__ == '__main__':
    main()
