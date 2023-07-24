import pandas as panda
import numpy as np
import os
import math
import re
import pickle
import xml.etree.ElementTree as ET
from datetime import datetime
import requests
import sys
import joblib


#For logs
import LoggerConfig
import logging
import sys
import random
from datetime import datetime

#For args
import getopt

#For application title
from os import system

import time


#param
end_line_character_in_text_file = "\n"




#used for sure 
def SimulerSimpleRunSimulation(_url, _stepInSecond, _dwellTimeInSecond, _coeffOnRunTime, elementary_mission_name, modele_name, output_file, _ignoredMER = None):
    #logging.info("Start calling SimulerSimpleRunSimulation")
    error = ""

    travelTimesRequestTree = ET.Element('travelTimesRequest')
    computationStepInSecondTree = ET.SubElement(travelTimesRequestTree, 'computationStepInSecond')
    computationStepInSecondTree.text = str(_stepInSecond)
    dwellTimeInSecondTree = ET.SubElement(travelTimesRequestTree, 'dwellTimeInSecond')
    dwellTimeInSecondTree.text = str(_dwellTimeInSecond)
    simulationTypeTree = ET.SubElement(travelTimesRequestTree, 'simulationType')
    simulationTypeTree.text = "SIMPLE_RUN_PROFILE_SIMULATION"
    trainsTree = ET.SubElement(travelTimesRequestTree, 'trains')
    #train 1
    trainTree_1 = ET.SubElement(trainsTree, 'train')
    blockTypeTree_1 = ET.SubElement(trainTree_1, 'blockType')
    blockTypeTree_1.text = "VB_WITH_MOVING_TARGET"
    elementaryTripIdentifierTree_1 = ET.SubElement(trainTree_1, 'elementaryTripIdentifier')
    elementaryTripIdentifierTree_1.text = elementary_mission_name
    loadCaseTree_1 = ET.SubElement(trainTree_1, 'loadCase')
    loadCaseTree_1.text = "AW0"
    modelTree_1 = ET.SubElement(trainTree_1, 'model')
    modelTree_1.text = modele_name
    nextElemTrip = '' # FIXME mE.FindNextElementaryTrip()
    #if(nextElemTrip is not None):
    #    nextElementaryTripIdentifierTree_1 = ET.SubElement(trainTree_1, 'nextElementaryTripIdentifier')
    nextElementaryTripIdentifierTree_1 = ET.SubElement(trainTree_1, 'nextElementaryTripIdentifier')
    nextElementaryTripIdentifierTree_1.text = nextElemTrip
    #else:
    #    if(mE.missionElementaireRegulation.poDestination.isPAFQuai or mE.missionElementaireRegulation.poDestination.isPTA):
    #        print("Erreur Grave : mission élémentaire " + mE.nom + " sans nextElementaryTrip")
    #        error = "Erreur Grave : mission élémentaire " + mE.nom + " sans nextElementaryTrip"
    #        os.system("pause")
    #        # sys.exit()
    
    regulationTypeTree_1 = ET.SubElement(trainTree_1, 'regulationType')
    regulationTypeTree_1.text = "ACCELERATED_RUN_PROFILE"

    #ET.dump(travelTimesRequestTree)
    result = ""
    travelTimesRequestTree_as_str = ET.tostring(travelTimesRequestTree, encoding='utf8', method='xml')
    element = ET.XML(travelTimesRequestTree_as_str)
    ET.indent(element)
    #LoggerConfig.printAndLogInfo(ET.tostring(element, encoding='unicode'))
    
    output_file.write("Send to SMT3 \n")
    output_file.write(ET.tostring(element, encoding='unicode'))
    output_file.write("\n")


    headers = {'Content-Type': 'application/xml'}
    full_url = _url + '/SMT3-REST-Server/computeTravelTimes'
    try:
        r = requests.post(full_url, data=ET.tostring(travelTimesRequestTree), headers=headers)
    except:
        print('Erreur de requête au serveur')
        #print(xml)
        quit()
    r.raise_for_status()
    logging.info("HTTP status:" +  str(r.status_code))
    
    element = ET.XML(r.text)
    ET.indent(element)
    #LoggerConfig.printAndLogInfo(ET.tostring(element, encoding='unicode'))
    
    output_file.write("Received from SMT3 \n")
    output_file.write(ET.tostring(element, encoding='unicode'))
    output_file.write("\n")



#@execution_time 
def ProduireSimplesRuns( _url, all_elementary_missions_names_as_list, all_nom_modele_as_list, all_nom_train_as_list, _stepInSecond, _dwellTimeInSecond, _PasSauvegarde, _coeffOnRunTime, _ignoredMER, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter, now_as_string_for_file_suffix):
    logging.info("Start calling ProduireSimplesRuns")
    start_time_ProduireSimplesRuns = time.time()
    numero_mission_elementaire_courante = 0
    nombre_simulations_smt3_effectuees = 0
    nbMissionsElementaires = len(all_elementary_missions_names_as_list)
    
    output_directory = "output"
    if not os.path.exists(output_directory):
        LoggerConfig.printAndLogInfo('Create output directory:' + output_directory)
        os.makedirs(output_directory)

    output_file_name = output_directory + "\\ProduireSimplesRuns_xml_inputs_and_output-" + now_as_string_for_file_suffix + ".txt"
    output_file = open(output_file_name, "w")
    logging.info('Create output file:' + output_file_name)

    for elementary_mission_name in all_elementary_missions_names_as_list:
        numero_mission_elementaire_courante = numero_mission_elementaire_courante + 1
        LoggerConfig.printAndLogInfo(str(numero_mission_elementaire_courante) + " eme ME " + elementary_mission_name + " sur " + str(nbMissionsElementaires) + " . Avancement:" + str(round(numero_mission_elementaire_courante*100/nbMissionsElementaires,2)) + "%")

        numero_modele = 0
        for modele_name in all_nom_modele_as_list:
            numero_modele = numero_modele + 1
            
            #Envoi de la requête
            output_file.write("\n")
            start_time_SimulerSimpleRunSimulation = time.time()
            nombre_simulations_smt3_effectuees = nombre_simulations_smt3_effectuees + 1
            LoggerConfig.printAndLogInfo("Lancement simulation " + str(numero_mission_elementaire_courante) + " eme mission elementaire ["+ elementary_mission_name +"] " + str(nombre_simulations_smt3_effectuees) + " eme simulation "+ str(numero_modele) + " eme modele : ["+modele_name+"] ")
            output_file.write("Lancement simulation " + str(numero_mission_elementaire_courante) + " eme mission elementaire ["+ elementary_mission_name +"] " + str(nombre_simulations_smt3_effectuees) + " eme simulation "+ str(numero_modele) + " eme modele : ["+modele_name+"] " +  " : Simulation ["+elementary_mission_name+","+modele_name+"] ")

            SimulerSimpleRunSimulation(_url, _stepInSecond, _dwellTimeInSecond, _coeffOnRunTime, elementary_mission_name, modele_name, output_file, _ignoredMER)

            elapsed_time_SimulerSimpleRunSimulation = time.time() - start_time_SimulerSimpleRunSimulation 
            LoggerConfig.printAndLogInfo("Simulation " + str(nombre_simulations_smt3_effectuees) + " [" + elementary_mission_name + "," + modele_name + "]" + ". computed in: " + format(elapsed_time_SimulerSimpleRunSimulation, '.2f') + " s")
            
            
            if elapsed_time_SimulerSimpleRunSimulation > 4:
                LoggerConfig.printAndLogWarning("SMT3 was slow for mission elementaire " + str(numero_modele) + " [" + elementary_mission_name + "," + modele_name.nom + "]" + ". Elapsed: " + format(elapsed_time_SimulerSimpleRunSimulation, '.2f') + " s")
            
            if(not (nombre_simulations_smt3_effectuees % _PasSauvegarde)):
                LoggerConfig.printAndLogInfo("Save output file with partial results")
                output_file.flush()
                # typically the above line would do. however this is used to ensure that the file is written
                os.fsync(output_file.fileno())


    logging.info('Close output file:' + output_file_name)
    output_file.close()
    



def launch(smt3_port, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter):

    
    log_file_name = 'Lancer_simulations_sur_smt3_ATSPlus-' + str(smt3_port) + '' + "." +  str(random.randrange(100)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.printAndLogInfo('Start application. Log file name: ' + log_file_name)
    Lancer_simulations_sur_smt3_ATSPlus(smt3_port,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)
    LoggerConfig.printAndLogInfo('End application')
    


def open_text_file_and_return_lines(input_file_name):  
    logging.info('Check existence of input file:' + input_file_name)

    if not os.path.exists(input_file_name):
        logging.critical("Input file:" + input_file_name + " does not exist. Application stopped")
        sys.exit()

    LoggerConfig.printAndLogInfo('Full path:' + os.path.abspath(input_file_name))


    LoggerConfig.printAndLogInfo('Opening input file:' + input_file_name)    
    input_file = open(input_file_name, "r")
    
    LoggerConfig.printAndLogInfo('Read input file:' + input_file_name)
    input_file_read = input_file.read()
    
    LoggerConfig.printAndLogInfo('Close input file:' + input_file_name)
    input_file.close()

    input_file_lines = input_file_read.split(end_line_character_in_text_file)
    LoggerConfig.printAndLogInfo(input_file_name + " has " + str(len(input_file_lines)) + " lines")

    return input_file_lines


def retrieve_all_field_string_content(SMT2_Data_file_name_with_path, field_name):

    SMT2_Data_file_lines = open_text_file_and_return_lines(SMT2_Data_file_name_with_path)
    SMT2_Data_file_content_in_one_line = "".join(SMT2_Data_file_lines)

    SMT2_Data_file_content_description = SMT2_Data_file_content_in_one_line.split("'" + field_name +"',{")[1]
    SMT2_Data_file_content_description = SMT2_Data_file_content_description.split("}")[0]
    SMT2_Data_file_content_description = SMT2_Data_file_content_description.replace("...","")
    SMT2_Data_file_content_description = SMT2_Data_file_content_description.replace("'","")
    SMT2_Data_file_content_description = SMT2_Data_file_content_description.replace(end_line_character_in_text_file,"")
    SMT2_Data_file_content_description = SMT2_Data_file_content_description.replace(" ","")

    field_string_content_as_list = SMT2_Data_file_content_description.split(",")

    LoggerConfig.printAndLogInfo(SMT2_Data_file_name_with_path + " has " + str(len(field_string_content_as_list)) + " objects " + field_name)

    return field_string_content_as_list



def Lancer_simulations_sur_smt3_ATSPlus(smt3_port, SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path, SMT2_Data_mE_file_name_with_path, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter):


    all_elementary_missions_names_as_list = retrieve_all_field_string_content(SMT2_Data_mE_file_name_with_path, "nom")
    all_nom_modele_as_list = retrieve_all_field_string_content(SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path, "nom_modele")
    all_nom_train_as_list = retrieve_all_field_string_content(SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path, "nom_train")



    LoggerConfig.printAndLogInfo("Nombre de missions élémentaires : " + str(len(all_elementary_missions_names_as_list)))
    LoggerConfig.printAndLogInfo("Nombre de modeles : " + str(len(all_nom_modele_as_list)))
    LoggerConfig.printAndLogInfo("Nombre de trains : " + str(len(all_nom_train_as_list)))
   
    now_as_datetime = datetime.now()
    now_as_string_for_file_suffix = now_as_datetime.strftime("%Y_%m_%d %H_%M_%S %f")

    ignoredMER = ['']
    LoggerConfig.printAndLogInfo("ignoredMER : " + str(ignoredMER)) 

    LoggerConfig.printAndLogInfo("ProduireSimplesRuns") 
    pas_sauvegarde = 10
    ProduireSimplesRuns("http://127.0.0.1:" + str(smt3_port), all_elementary_missions_names_as_list, all_nom_modele_as_list, all_nom_train_as_list, 0.4, 30.0,pas_sauvegarde,1.1,ignoredMER,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter, now_as_string_for_file_suffix)
  
    LoggerConfig.printAndLogInfo("End of application") 
    

def main(argv):
    
    log_file_name = 'Lancer_simulations_sur_smt3_ATSPlus-' +  str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.printAndLogInfo('Start application. Log file name: ' + log_file_name)


    list_arguments_names = ["SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path","SMT2_Data_mE_file_name_with_path","numero_premiere_mission_elementaire_a_traiter=","numero_derniere_mission_elementaire_a_traiter=","port_smt3="]
    
    numero_premiere_mission_elementaire_a_traiter = None
    numero_derniere_mission_elementaire_a_traiter = None

    
    SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path = 'SMT2_Data_param_for_SMT3_launched_in_Matlab.m'
    SMT2_Data_mE_file_name_with_path = 'SMT2_Data_mE.m'


    port_smt3 = 8081

    try:
        opts, args = getopt.getopt(argv,"hi:o:", list_arguments_names)
    except getopt.GetoptError as err:
        errorMessage = "Unsupported arguments list." + str(err) + " Allowed arguments:" + str(list_arguments_names) + ". Application stopped"
        LoggerConfig.printAndLogCriticalAndKill(errorMessage)
    for opt, arg in opts:
        if opt in ("-h", "--help"):
            LoggerConfig.printAndLogInfo("Allowed arguments:" + str(list_arguments_names) + ". Application stopped")
            sys.exit()
        elif opt == "--numero_premiere_mission_elementaire_a_traiter":
            numero_premiere_mission_elementaire_a_traiter = int(arg)
        elif opt == "--numero_derniere_mission_elementaire_a_traiter":
            numero_derniere_mission_elementaire_a_traiter= int(arg)
        elif opt == "--port_smt3":
            port_smt3 = arg
        elif opt == "--SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path":
            SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path = arg
        elif opt == "--SMT2_Data_mE_file_name_with_path":
            SMT2_Data_mE_file_name_with_path = arg
        else:
            LoggerConfig.printAndLogCriticalAndKill (" Option:" + opt + " unknown with value:" + opt + ". Allowed arguments:" + str(list_arguments_names) + ". Application stopped")

    system("title " + " Lancer_simulations_sur_smt3 " + str(numero_premiere_mission_elementaire_a_traiter) + " "  + str(numero_derniere_mission_elementaire_a_traiter) + " "  + str(port_smt3) + " " )
    Lancer_simulations_sur_smt3_ATSPlus(port_smt3,SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path, SMT2_Data_mE_file_name_with_path, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)

    LoggerConfig.printAndLogInfo('End application')

if __name__ == "__main__":
    main(sys.argv[1:])


