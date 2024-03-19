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


#For hmi
import tkinter as tk
from tkinter import filedialog

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

import SMT3Server

import param

import SimulationsRequestsManager

#param
end_line_character_in_text_file = "\n"
csv_fields_separator = ";"



class SMT3SimulationRequest:
    def __init__(self, xml_request_to_SMT3_as_indented_text, stepInSecond, dwellTimeInSecond):
        self.xml_request_to_SMT3_as_indented_text = xml_request_to_SMT3_as_indented_text
        self.stepInSecond = stepInSecond
        self.dwellTimeInSecond = dwellTimeInSecond


class SMT3SimulationResult:
    def __init__(self, smt3Server, received_from_smt3, xml_response_from_smt3_response_as_indented_text, error_text, totalTravelTimeInSecond_text, numberOfTravelTimeAndSpeedAtControlPointElements, smt3_execution_time):
        self.smt3Server = smt3Server
        self.received_from_smt3 = received_from_smt3
        self.xml_response_from_smt3_response_as_indented_text = xml_response_from_smt3_response_as_indented_text
        self.error_text = error_text
        self.error_text_in_one_line = error_text.replace("\n", "\\n")
        self.totalTravelTimeInSecond_text = totalTravelTimeInSecond_text
        self.smt3_execution_time = smt3_execution_time
        self.numberOfTravelTimeAndSpeedAtControlPointElements = numberOfTravelTimeAndSpeedAtControlPointElements


class SMT3Simulation:
    def __init__(self, sMT3SimulationRequest, sMT3SimulationResults, elementary_mission_name, modele_name):
        self.sMT3SimulationRequest = sMT3SimulationRequest
        self.sMT3SimulationResults = sMT3SimulationResults
        self.elementary_mission_name= elementary_mission_name
        self.modele_name = modele_name


def prepare_SMT3_Request(stepInSecond, dwellTimeInSecond, elementary_mission_name, modele_name):


    travelTimesRequestTree = ET.Element('travelTimesRequest')
    computationStepInSecondTree = ET.SubElement(travelTimesRequestTree, 'computationStepInSecond')
    computationStepInSecondTree.text = str(stepInSecond)
    dwellTimeInSecondTree = ET.SubElement(travelTimesRequestTree, 'dwellTimeInSecond')
    dwellTimeInSecondTree.text = str(dwellTimeInSecond)
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

    return travelTimesRequestTree


def decode_smt3_result(smt3Server, url, received_from_smt3, elapsed_time_simulation_SMT3):

    #received_from_smt3.raise_for_status()
    logging.info("HTTP status:" +  str(received_from_smt3.status_code))
        
    if received_from_smt3.status_code == 200:

        received_from_smt3_text = received_from_smt3.text

        received_from_smt3_as_ET = ET.fromstring(received_from_smt3_text)
        received_from_smt3_as_tree = ET.ElementTree(received_from_smt3_as_ET)
        received_from_smt3_as_tree_root = received_from_smt3_as_tree.getroot()

        totalTravelTimeInSecond_text = ""
        errorMessage_text = ""

        numberOfTravelTimeAndSpeedAtControlPointElements = 0

        if received_from_smt3_as_tree_root is not None:
            travelTimes_element = received_from_smt3_as_tree_root.find('travelTimes')
        #get_totalTravelTimeInSecond = travelTimes_element.get("totalTravelTimeInSecond")
            if travelTimes_element is not None:

                totalTravelTimeInSecond_element = travelTimes_element.find("totalTravelTimeInSecond")
                if travelTimes_element is not None:
                    totalTravelTimeInSecond_text = totalTravelTimeInSecond_element.text

                travelTimesAndSpeedAtControlPoints_element = travelTimes_element.find("travelTimesAndSpeedAtControlPoints")
                if travelTimesAndSpeedAtControlPoints_element is not None:
                    travelTimeAndSpeedAtControlPoint_all_elements = travelTimesAndSpeedAtControlPoints_element.findall("travelTimeAndSpeedAtControlPoint")
                    if travelTimeAndSpeedAtControlPoint_all_elements is not None:
                        numberOfTravelTimeAndSpeedAtControlPointElements = len(travelTimeAndSpeedAtControlPoint_all_elements)

        errorMessage_Element = received_from_smt3_as_tree_root.find('errorMessage')
        if errorMessage_Element is not None:
            errorMessage_text = errorMessage_Element.text

        received_from_smt3_element = ET.XML(received_from_smt3_text)
        ET.indent(received_from_smt3_element)
        #LoggerConfig.printAndLogInfo(ET.tostring(received_from_smt3_element, encoding='unicode'))
        
        xml_response_from_smt3_response_as_indented_text = ET.tostring(received_from_smt3_element, encoding='unicode')

        sMT3SimulationResult = SMT3SimulationResult(smt3Server, received_from_smt3, xml_response_from_smt3_response_as_indented_text, errorMessage_text, totalTravelTimeInSecond_text, numberOfTravelTimeAndSpeedAtControlPointElements, elapsed_time_simulation_SMT3)
    
    if received_from_smt3.status_code != 200:
        LoggerConfig.printAndLogInfo("status_code:" + str(received_from_smt3.status_code))
        LoggerConfig.printAndLogInfo(str(received_from_smt3.raw))

        xml_response_from_smt3_response_as_indented_text = str(received_from_smt3.raw)
        sMT3SimulationResult = SMT3SimulationResult(received_from_smt3, xml_response_from_smt3_response_as_indented_text, str(received_from_smt3.raw), "", "")
        


    return sMT3SimulationResult


#used for sure 
def SimulerSimpleRunSimulation(simulationToBePerformed, smt3Servers, stepInSecond, dwellTimeInSecond, elementary_mission_name, modele_name):
    #logging.info("Start calling SimulerSimpleRunSimulation")

    if simulationToBePerformed.fullXmlRequestText is not None:
        travelTimesRequestTree = ET.fromstring(simulationToBePerformed.fullXmlRequestText)
    else:
        travelTimesRequestTree = prepare_SMT3_Request(stepInSecond, dwellTimeInSecond, elementary_mission_name, modele_name)

    travelTimesRequestTree_as_str = ET.tostring(travelTimesRequestTree, encoding='utf8', method='xml')

    #ET.dump(travelTimesRequestTree)
    element = ET.XML(travelTimesRequestTree_as_str)
    ET.indent(element)
    
    #LoggerConfig.printAndLogInfo(ET.tostring(element, encoding='unicode'))
    
    xml_request_to_SMT3_as_indented_text = ET.tostring(element, encoding='unicode')


    elapsed_time_simulation_SMT3 = None

    sMT3SimulationRequest = SMT3SimulationRequest(xml_request_to_SMT3_as_indented_text, stepInSecond, dwellTimeInSecond)    
    sMT3SimulationResults = list()

    headers = {'Content-Type': 'application/xml'}
    for smt3Server in smt3Servers:
        LoggerConfig.printAndLogInfo("Smt3 server " + smt3Server.smt3Version + " on port " + str(smt3Server.port))
 
        smt3Server.full_url = smt3Server.url + '/SMT3-REST-Server/computeTravelTimes'

        exception_in_post_request = None

        try:
            start_time_simulation_SMT3 = time.time()            
            received_from_smt3 = requests.post(smt3Server.full_url, data=ET.tostring(travelTimesRequestTree), headers=headers)
        except requests.exceptions.HTTPError as errh:
            exception_in_post_request = errh 
        except requests.exceptions.ConnectionError as errc:
            exception_in_post_request = errc
        except requests.exceptions.Timeout as errt:
            exception_in_post_request = errt
        except requests.exceptions.RequestException as err:
            exception_in_post_request = err
        except:
            print('Erreur de requête au serveur')
            #print(xml)
            quit()

        end_time_simulation_SMT3 = time.time()
        elapsed_time_simulation_SMT3 = end_time_simulation_SMT3 - start_time_simulation_SMT3            

        if exception_in_post_request is not None:
            print(exception_in_post_request)
            sMT3SimulationResult = SMT3SimulationResult(smt3Server, "!!Error!!", str(exception_in_post_request), str(exception_in_post_request), "", 0, elapsed_time_simulation_SMT3)

        else:
            sMT3SimulationResult = decode_smt3_result(smt3Server, smt3Server.url, received_from_smt3, elapsed_time_simulation_SMT3)
        
        sMT3SimulationResults.append(sMT3SimulationResult)

    sMT3Simulation = SMT3Simulation(sMT3SimulationRequest, sMT3SimulationResults, elementary_mission_name, modele_name)

    return sMT3Simulation


def saveSimulation(sMT3Simulation, result_csv_file, numero_mission_elementaire_courante, elementary_mission_name, numero_modele, modele_name, nombre_simulations_smt3_effectuees):

    sMT3SimulationRequest = sMT3Simulation.sMT3SimulationRequest
    
    
    for sMT3SimulationResult in sMT3Simulation.sMT3SimulationResults:
        sMT3SimulationResult.smt3Server.input_output_dump_file.write(end_line_character_in_text_file)
        sMT3SimulationResult.smt3Server.input_output_dump_file.write("Lancement simulation " + str(numero_mission_elementaire_courante) + " eme mission elementaire ["+ elementary_mission_name +"] " + str(nombre_simulations_smt3_effectuees) + " eme simulation "+ str(numero_modele) + " eme modele : ["+modele_name+"] " +  " : Simulation ["+elementary_mission_name+","+modele_name+"] ")

        sMT3SimulationResult.smt3Server.input_output_dump_file.write("Send to SMT3 "+ end_line_character_in_text_file)
        sMT3SimulationResult.smt3Server.input_output_dump_file.write(sMT3SimulationRequest.xml_request_to_SMT3_as_indented_text)
        sMT3SimulationResult.smt3Server.input_output_dump_file.write(end_line_character_in_text_file)
        
    result_csv_file.write(elementary_mission_name + csv_fields_separator + modele_name + csv_fields_separator + str(sMT3SimulationRequest.stepInSecond) + csv_fields_separator + str(sMT3SimulationRequest.dwellTimeInSecond) + csv_fields_separator)


    for sMT3SimulationResult in sMT3Simulation.sMT3SimulationResults:
        sMT3SimulationResult.smt3Server.input_output_dump_file.write(sMT3SimulationResult.xml_response_from_smt3_response_as_indented_text)
        sMT3SimulationResult.smt3Server.input_output_dump_file.write(end_line_character_in_text_file)
        sMT3SimulationResult.smt3Server.input_output_dump_file.write("Received from SMT3 " + end_line_character_in_text_file)

        result_csv_file.write(str(sMT3SimulationResult.smt3_execution_time) + csv_fields_separator + sMT3SimulationResult.totalTravelTimeInSecond_text + csv_fields_separator + str(sMT3SimulationResult.numberOfTravelTimeAndSpeedAtControlPointElements) + csv_fields_separator + sMT3SimulationResult.error_text_in_one_line + csv_fields_separator)

    result_csv_file.write( end_line_character_in_text_file)


    if(not (nombre_simulations_smt3_effectuees % param.pas_sauvegarde)):
        LoggerConfig.printAndLogInfo("Save output file with partial results")
        for sMT3SimulationResult in sMT3Simulation.sMT3SimulationResults:
            sMT3SimulationResult.smt3Server.input_output_dump_file.flush()
            # typically the above line would do. however this is used to ensure that the file is written
            os.fsync(sMT3SimulationResult.smt3Server.input_output_dump_file.fileno())
            
        result_csv_file.flush()
        # typically the above line would do. however this is used to ensure that the file is written
        os.fsync(result_csv_file.fileno())

def create_output_text_file(output_directory, output_file_name):
    
    if not os.path.exists(output_directory):
        LoggerConfig.printAndLogInfo('Create output directory:' + output_directory)
        os.makedirs(output_directory)

    output_file_full_path = output_directory +  "\\" + output_file_name

    input_output_dump_file = open(output_file_full_path, "w")
    logging.info('Create output file:' + output_file_name)

    return input_output_dump_file

#@execution_time 
def ProduireSimplesRuns( smt3Servers, simulationsRequestsManager, now_as_string_for_file_suffix):
    logging.info("Start calling ProduireSimplesRuns")
    start_time_ProduireSimplesRuns = time.time()
    numero_mission_elementaire_courante = 0
    nombre_simulations_smt3_effectuees = 0
    
    output_directory = "output"
    if not os.path.exists(output_directory):
        LoggerConfig.printAndLogInfo('Create output directory:' + output_directory)
        os.makedirs(output_directory)

    for smt3Server in smt3Servers:
        smt3Server.input_output_dump_file_name = "Inputs_and_output_" + now_as_string_for_file_suffix + smt3Server.description() + ".txt"
        smt3Server.input_output_dump_file = create_output_text_file(output_directory, smt3Server.input_output_dump_file_name)

    result_csv_file_name =  "ProduireSimplesRuns_csv_results_" + now_as_string_for_file_suffix + ".csv"
    result_csv_file = create_output_text_file(output_directory, result_csv_file_name)

    result_csv_file.write("elementary_mission_name" + csv_fields_separator + "modele_name" + csv_fields_separator + "stepInSecond" + csv_fields_separator + "dwellTimeInSecond" + csv_fields_separator)
    for smt3Server in smt3Servers:
        result_csv_file.write("sMT3SimulationResult." + smt3Server.description() + ".smt3_execution_time" + csv_fields_separator + "sMT3SimulationResult." + smt3Server.description() + ".totalTravelTimeInSecond_text" + csv_fields_separator + "sMT3SimulationResult." + smt3Server.description() + ".numberOfTravelTimeAndSpeedAtControlPointElements" + csv_fields_separator + "sMT3SimulationResult." + smt3Server.description() + ".error_text" + csv_fields_separator)

    result_csv_file.write( end_line_character_in_text_file)

    previous_elementary_mission_name = None
    previous_modele_name = None

    for simulationToBePerformed in simulationsRequestsManager.simulationsToBePerformed:
        elementary_mission_name = simulationToBePerformed.elementary_mission_name
        modele_name = simulationToBePerformed.modele_name  
        step_in_second = simulationToBePerformed.step_in_second
        dwellTimeInSecond = simulationToBePerformed.dwellTimeInSecond       

        if previous_elementary_mission_name != elementary_mission_name:
            numero_mission_elementaire_courante = numero_mission_elementaire_courante + 1
            numero_modele = 0

        LoggerConfig.printAndLogInfo(str(numero_mission_elementaire_courante) + " eme ME " + elementary_mission_name + " sur " + str(len(simulationsRequestsManager.simulationsToBePerformed)) + " simulations . Avancement:" + str(round(nombre_simulations_smt3_effectuees*100/len(simulationsRequestsManager.simulationsToBePerformed),2)) + "%")

        if previous_modele_name != modele_name:
            numero_modele = numero_modele + 1
        
        #Envoi de la requête
        start_time_SimulerSimpleRunSimulation = time.time()
        nombre_simulations_smt3_effectuees = nombre_simulations_smt3_effectuees + 1
        LoggerConfig.printAndLogInfo("Lancement simulation " + str(numero_mission_elementaire_courante) + " eme mission elementaire ["+ elementary_mission_name +"] " + str(nombre_simulations_smt3_effectuees) + " eme simulation "+ str(numero_modele) + " eme modele : ["+modele_name+"]  stepInSecondToApply:" + str(step_in_second))

        if nombre_simulations_smt3_effectuees in simulationsRequestsManager.simulationsNumbersToIgnore:
            LoggerConfig.printAndLogInfo("Ignore simulation " + str(numero_mission_elementaire_courante) + " eme mission elementaire ["+ elementary_mission_name +"] " + str(nombre_simulations_smt3_effectuees) + " eme simulation "+ str(numero_modele) + " eme modele : ["+modele_name+"]  stepInSecondToApply:" + str(step_in_second))
        else:
            if param.listNumerosSimulationsAEffectuer is None or nombre_simulations_smt3_effectuees in param.listNumerosSimulationsAEffectuer:
                try:
                    sMT3Simulation = SimulerSimpleRunSimulation(simulationToBePerformed, smt3Servers, step_in_second, dwellTimeInSecond, elementary_mission_name, modele_name)


                    elapsed_time_SimulerSimpleRunSimulation = time.time() - start_time_SimulerSimpleRunSimulation 
                    LoggerConfig.printAndLogInfo("Simulation " + str(nombre_simulations_smt3_effectuees) + " [" + elementary_mission_name + "," + modele_name + "]" + ". computed in: " + format(elapsed_time_SimulerSimpleRunSimulation, '.2f') + " s")
                
                    
                    if elapsed_time_SimulerSimpleRunSimulation > 4:
                        LoggerConfig.printAndLogWarning("SMT3 was slow for mission elementaire " + str(numero_modele) + " [" + elementary_mission_name + "," + modele_name + "]" + ". Elapsed: " + format(elapsed_time_SimulerSimpleRunSimulation, '.2f') + " s")
                    

                    saveSimulation(sMT3Simulation, result_csv_file, numero_mission_elementaire_courante, elementary_mission_name, numero_modele, modele_name, nombre_simulations_smt3_effectuees)

                except requests.exceptions.ConnectionError as err:
                    # eg, no internet
                    LoggerConfig.printAndLogInfo(err)
                    raise SystemExit(err)
                except requests.exceptions.HTTPError as err:
                    # eg, url, server and other errors
                    LoggerConfig.printAndLogInfo(str(err))
                    raise SystemExit(err)
                # the rest of my code is going here
            else:
                LoggerConfig.printAndLogInfo("Simulation ignorée (filtrée par paramétrage): " + str(numero_mission_elementaire_courante) + " eme mission elementaire ["+ elementary_mission_name +"] " + str(nombre_simulations_smt3_effectuees) + " eme simulation "+ str(numero_modele) + " eme modele : ["+modele_name+"]  stepInSecondToApply:" + str(step_in_second))

        previous_modele_name = modele_name
        previous_elementary_mission_name = elementary_mission_name

    for smt3Server in smt3Servers: 
        logging.info('Close output files:' + smt3Server.input_output_dump_file_name)
        smt3Server.input_output_dump_file.close()
    result_csv_file.close()
    



def launch(smt3_port, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter):

    
    log_file_name = 'Lancer_simulations_sur_smt3_ATSPlus-' + str(smt3_port) + '' + "." +  str(random.randrange(100)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.printAndLogInfo('Start application. Log file name: ' + log_file_name)
    Lancer_simulations_sur_smt3_ATSPlus(smt3_port,numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter)
    LoggerConfig.printAndLogInfo('End application')
    

def Lancer_simulations_sur_smt3_ATSPlus(smt3Servers, simulationsRequestsManager):

    now_as_datetime = datetime.now()
    now_as_string_for_file_suffix = now_as_datetime.strftime("%Y_%m_%d %H_%M_%S %f")


    for smt3Server in smt3Servers:
        smt3Server.url = "http://127.0.0.1:" + str(smt3Server.port)

    ProduireSimplesRuns(smt3Servers, simulationsRequestsManager, now_as_string_for_file_suffix)
  
    LoggerConfig.printAndLogInfo("End of application") 
    

def main(argv):
    
    log_file_name = 'Lancer_simulations_sur_smt3_ATSPlus-' +  str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.printAndLogInfo('Start application. Log file name: ' + log_file_name)

    root = tk.Tk()
    root.withdraw()

    system("title " + " Lancer_simulations_sur_smt3 " )
    Lancer_simulations_sur_smt3_ATSPlus(param.sMT3Servers,param.simulationsRequestsManager)

    LoggerConfig.printAndLogInfo('End application')

if __name__ == "__main__":
    main(sys.argv[1:])


