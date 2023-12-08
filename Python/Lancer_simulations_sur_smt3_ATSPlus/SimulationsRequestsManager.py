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

#param
end_line_character_in_text_file = "\n"
csv_fields_separator = ";"



class SimulationsRequestsManager:
    def __init__(self):
        self.default_step_in_second = None
        self.defalut_dwell_time_in_second = None
        self.simulationsToBePerformed = list()


    def generateAllMissionElementaireCombinations(self, step_in_second, dwell_time_in_second, all_elementary_missions_names_as_list, all_nom_modele_as_list):
        LoggerConfig.printAndLogInfo("generateAllMissionElementaireCombinations begin")

        for elementary_mission_name in all_elementary_missions_names_as_list:
            for nom_modele in all_nom_modele_as_list:
                simulationToBePerformed = SimulationToBePerformed(elementary_mission_name, nom_modele, step_in_second, dwell_time_in_second)
                self.simulationsToBePerformed.append(simulationToBePerformed)

        LoggerConfig.printAndLogInfo("generateAllMissionElementaireCombinations end")


class SimulationToBePerformed:
    def __init__(self, elementary_mission_name, modele_name, step_in_second, dwellTimeInSecond):
        a = 1


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
    field_string_content_as_list.sort()
    #field_string_content_as_list.append("okok")

    LoggerConfig.printAndLogInfo(SMT2_Data_file_name_with_path + " has " + str(len(field_string_content_as_list)) + " objects " + field_name)

    return field_string_content_as_list



def Lancer_simulations_sur_smt3_ATSPlus(SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path, SMT2_Data_mE_file_name_with_path, numero_premiere_mission_elementaire_a_traiter, numero_derniere_mission_elementaire_a_traiter):


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
    

