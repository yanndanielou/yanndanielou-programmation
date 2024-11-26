# -*-coding:Utf-8 -*
#
#
# Input: file with columns
#metric_name,metric_timestamp,_value
#project.mesure,2024-07-15T00:25:46.992+03:00,"-1"
#
# Output: file with columns
#metric_timestamp,metric_name,_value
#2024-06-12T00:43:37.495+00:00,Computer.LogicalDisk_D.Free_Space,73.252541527397269761

import Dependencies.Logger.LoggerConfig as LoggerConfig

import subprocess
import glob
import os
from os.path import basename
import sys

import random

import zipfile

import re

import csv

import param
import metric

import pandas

import shutil

import queue
import threading
import time

#import datetime
import time

 
def get_metric_file_content_lines_from_metrics(metrics):
    splunk_ready_file_content_as_list = list()
    splunk_ready_file_content_as_list.append("metric_timestamp,metric_name,_value")

    for metric in metrics:
        splunk_ready_file_content_as_list.append(metric._time_stamp + "," + metric._name + "," + metric._value_as_string)

    return splunk_ready_file_content_as_list
    

def process_input_zip_files(input_zip_files):
    LoggerConfig.printAndLogInfo("process_input_zip_files")
    for input_zip_file in input_zip_files:
        process_input_zip_file( input_zip_file)


def process_input_zip_file(input_zip_file):
    LoggerConfig.printAndLogInfo("process_input_zip_files")

    LoggerConfig.printAndLogInfo("processing " + basename(input_zip_file))

    input_zip_file_file_name = basename(input_zip_file)
    LoggerConfig.printAndLogInfo("File name:" + input_zip_file_file_name)
    
    input_zip_file_directory_name = os.path.dirname(input_zip_file)    
    LoggerConfig.printAndLogInfo("Directory name:" + input_zip_file_directory_name)
    
    input_zip_file_file_without_extension = os.path.splitext(input_zip_file_file_name)[0]
    LoggerConfig.printAndLogInfo("File name without extension:" + input_zip_file_file_without_extension)
    
    temp_folder_name = "temp_" + input_zip_file_file_without_extension
    
    temp_folder_full_path_name = param.output_metrics_ready_for_splunk_directory + "\\" + temp_folder_name
    
    if os.path.exists(temp_folder_full_path_name):
        LoggerConfig.printAndLogInfo("\t" + "Removing:" + temp_folder_full_path_name + ". Previous execution of the tool was probably interrupted")
        shutil.rmtree(temp_folder_full_path_name, ignore_errors=False, onerror=None)
        
    os.makedirs(temp_folder_full_path_name)
    
    LoggerConfig.printAndLogInfo("extract to temporary folder")
    subprocess.call(r'"C:\Program Files\7-Zip\7z.exe" e ' + "\"" + input_zip_file + "\"" + ' -o' + "\"" + temp_folder_full_path_name + "\"")
    LoggerConfig.printAndLogInfo("Extraction done")
    
    output_zip_file_name = input_zip_file_file_without_extension
    if len(param.metric_name_fixed_prefix_for_all_mesures) > 0:
        output_zip_file_name = output_zip_file_name + "_" + param.metric_name_fixed_prefix_for_all_mesures
    
    output_zip_file_name = output_zip_file_name + param.output_files_suffix + "_ready_for_splunk." +  param.output_files_extension
    output_zip_file = zipfile.ZipFile(param.output_metrics_ready_for_splunk_directory + "\\" + output_zip_file_name,'w')
    
    LoggerConfig.printAndLogInfo("Convert all performance_counter_log to csv")
    
    for performance_counter_log_file in glob.glob(temp_folder_full_path_name + "\\*.csv"):
        handle_performance_counter_log_file(performance_counter_log_file, temp_folder_full_path_name, output_zip_file)

    #Close the zip
    LoggerConfig.printAndLogInfo("Zip DONE")
    output_zip_file.close()            
        
    LoggerConfig.printAndLogInfo("Remove temporary directory:" + temp_folder_full_path_name)
    shutil.rmtree(temp_folder_full_path_name)


def remove_faulty_characters_in_performance_counter_log_converted_to_csv( original_line, line_number):   
    
    return original_line
        
     
def compute_splunk_ready_file_name(performance_counter_log_file_without_extension, all_used_machine_names, all_ignore_machine_names, all_used_application_names, all_ignore_application_names,  all_used_mesure_types, all_ignore_mesure_types):
    splunk_ready_file_name_without_extension = performance_counter_log_file_without_extension 
    
    if len(param.metric_name_fixed_prefix_for_all_mesures) > 0:
        splunk_ready_file_name_without_extension = splunk_ready_file_name_without_extension + "_" + param.metric_name_fixed_prefix_for_all_mesures
    
    if len(all_ignore_machine_names) > 0 or len(all_ignore_application_names) > 0 or len(all_ignore_mesure_types) > 0:
        splunk_ready_file_name_without_extension = splunk_ready_file_name_without_extension + "_partial"
        
        if len(all_ignore_machine_names) > 0:
            splunk_ready_file_name_without_extension = splunk_ready_file_name_without_extension + "_".join(all_used_machine_names)
            
        if len(all_ignore_application_names) > 0:
            splunk_ready_file_name_without_extension = splunk_ready_file_name_without_extension + "_".join(all_used_application_names).replace("Process_","")
            
        #if len(all_ignore_mesure_types) > 0:
        #    splunk_ready_file_name = splunk_ready_file_name + "_".join(all_used_mesure_types)
    
    splunk_ready_file_name_with_extension = splunk_ready_file_name_without_extension  + param.output_files_suffix
    return splunk_ready_file_name_with_extension

def convert_performance_counter_log_file_to_metric_file( performance_counter_log_file_file_name, performance_counter_log_file_full_path):
    LoggerConfig.printAndLogInfo( "Convert performance_counter_log file:" + performance_counter_log_file_file_name)  

    LoggerConfig.printAndLogInfo( "Open file to tranform: " + performance_counter_log_file_full_path)        
    performance_counter_log_file_pandas_data = pandas.read_csv(performance_counter_log_file_full_path)  

    # Obtenir la liste des noms de colonnes
    performance_counter_log_file_pandas_columns_names = performance_counter_log_file_pandas_data.columns

    #metric_name,metric_timestamp,_value
    metrics = list()
    # Itérer sur les lignes du DataFrame
    for index, row in performance_counter_log_file_pandas_data.iterrows():
        
        # Itérer sur les cellules de la ligne courante en utilisant les noms des colonnes
        original_time_stamp = row["metric_timestamp"]
        original_value = row["_value"]
        original_metric_name = row['metric_name']

        value = str(original_value)
        metricCreated = metric.Metric(original_time_stamp, original_metric_name, value)
        metrics.append(metricCreated)

    return metrics
    
def handle_performance_counter_log_file( performance_counter_log_file_full_path, temp_folder_full_path_name, output_zip_file):
    performance_counter_log_file_file_name = basename(performance_counter_log_file_full_path)        
    performance_counter_log_file_without_extension = os.path.splitext(performance_counter_log_file_file_name)[0]
    
    metrics = convert_performance_counter_log_file_to_metric_file( performance_counter_log_file_file_name, performance_counter_log_file_full_path)
    LoggerConfig.printAndLogInfo("Number of metrics created:" + str(len(metrics)))

    metric_file_content_lines = get_metric_file_content_lines_from_metrics(metrics)
    LoggerConfig.printAndLogInfo("Metric file content computed")
    

    splunk_ready_file_name = performance_counter_log_file_without_extension + "_as_splunk_metric.csv"
    splunk_ready_file_full_path =  temp_folder_full_path_name + "\\" + splunk_ready_file_name

    
    splunk_ready_file_full_path = create_splunk_ready_metric_file(splunk_ready_file_full_path, metric_file_content_lines)

    # add to zip
    add_splunk_ready_metric_file_to_zip(output_zip_file, splunk_ready_file_full_path, splunk_ready_file_name)

    os.remove(splunk_ready_file_full_path)
 
def create_splunk_ready_metric_file( splunk_ready_file_name, splunk_ready_file_content_as_list):
    LoggerConfig.printAndLogInfo( "Create final Splunk ready file: " + splunk_ready_file_name)    
    splunk_ready_file = open(splunk_ready_file_name, "w")

    LoggerConfig.printAndLogInfo( "Fill final Splunk ready file: " + splunk_ready_file_name)    
    splunk_ready_file_content = param.end_line_character_in_text_file.join(splunk_ready_file_content_as_list)
    splunk_ready_file.write(splunk_ready_file_content)

    LoggerConfig.printAndLogInfo( "Close final Splunk ready file:" + splunk_ready_file_name)
    splunk_ready_file.close()
    
    return splunk_ready_file_name
 

def add_splunk_ready_metric_file_to_zip(output_zip_file, splunk_ready_file_full_path, splunk_ready_file_name):
    output_zip_file.write(splunk_ready_file_full_path, arcname=splunk_ready_file_name, compress_type=zipfile.ZIP_DEFLATED)
            
def process_input_zip_file( input_zip_file):
    LoggerConfig.printAndLogInfo( "processing " + basename(input_zip_file))

    input_zip_file_file_name = basename(input_zip_file)
    LoggerConfig.printAndLogInfo( "File name:" + input_zip_file_file_name)
    
    input_zip_file_directory_name = os.path.dirname(input_zip_file)    
    LoggerConfig.printAndLogInfo( "Directory name:" + input_zip_file_directory_name)
    
    input_zip_file_file_without_extension = os.path.splitext(input_zip_file_file_name)[0]
    LoggerConfig.printAndLogInfo( "File name without extension:" + input_zip_file_file_without_extension)
    
    temp_folder_name = "temp_" + input_zip_file_file_without_extension
    
    temp_folder_full_path_name = param.output_metrics_ready_for_splunk_directory + "\\" + temp_folder_name
    
    if os.path.exists(temp_folder_full_path_name):
        LoggerConfig.printAndLogInfo( "\t" + "Removing:" + temp_folder_full_path_name + ". Previous execution of the tool was probably interrupted")
        shutil.rmtree(temp_folder_full_path_name, ignore_errors=False, onerror=None)
        
    os.makedirs(temp_folder_full_path_name)
    
    LoggerConfig.printAndLogInfo( "extract to temporary folder")
    subprocess.call(r'"C:\Program Files\7-Zip\7z.exe" e ' + "\"" + input_zip_file + "\"" + ' -o' + "\"" + temp_folder_full_path_name + "\"")
    LoggerConfig.printAndLogInfo( "Extraction done")
    
    output_zip_file_name = input_zip_file_file_without_extension
    if len(param.metric_name_fixed_prefix_for_all_mesures) > 0:
        output_zip_file_name = output_zip_file_name + "_" + param.metric_name_fixed_prefix_for_all_mesures
    
    output_zip_file_name = output_zip_file_name + param.output_files_suffix + "_ready_for_splunk." +  param.output_files_extension
    output_zip_file = zipfile.ZipFile(param.output_metrics_ready_for_splunk_directory + "\\" + output_zip_file_name,'w')
    
    LoggerConfig.printAndLogInfo( "Convert all performance counters logs to csv")
    
    handle_performance_counter_log_files = glob.glob(temp_folder_full_path_name + "\\*" + param.input_performance_counter_file_extension)
    for performance_counter_log_file in handle_performance_counter_log_files:
        handle_performance_counter_log_file( performance_counter_log_file, temp_folder_full_path_name, output_zip_file)

    #Close the zip
    LoggerConfig.printAndLogInfo( "Zip DONE")
    output_zip_file.close()            
        
    LoggerConfig.printAndLogInfo( "Remove temporary directory:" + temp_folder_full_path_name)
    shutil.rmtree(temp_folder_full_path_name)

def main(argv):

    log_file_name = os.path.basename(__file__) + str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    
    LoggerConfig.printAndLogInfo('Start application')
    
    # Get the list of input zips

    input_zip_files = glob.glob(param.input_files_directory + "\\*.zip")
    input_7z_files = glob.glob(param.input_files_directory + "\\*.7z")
    
    LoggerConfig.printAndLogInfo('input_zip_files:' + ",".join(input_zip_files))
    LoggerConfig.printAndLogInfo('input_7z_files:' + ",".join(input_7z_files))

    input_files = input_zip_files+input_7z_files
       
    LoggerConfig.printAndLogInfo('input_files:' + ",".join(input_zip_files))
        
    process_input_zip_files(input_files)
    

    LoggerConfig.printAndLogInfo("Exiting main thread")

    
    LoggerConfig.printAndLogInfo("End. Nominal end of application")
    

if __name__ == "__main__":
   main(sys.argv[1:])