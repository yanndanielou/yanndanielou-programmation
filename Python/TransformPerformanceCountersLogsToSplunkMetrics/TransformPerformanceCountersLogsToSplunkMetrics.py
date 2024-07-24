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

import logging

import subprocess
import glob
import os
from os.path import basename
import sys

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

exitFlag = 0
queueLock = threading.Lock()
queueOfPerflogArchivesToTransform = queue.Queue(100)

def printOnly(toPrint):
    log_timestamp = time.asctime( time.localtime(time.time()))
    print(log_timestamp + '\t' + toPrint)

def printAndLog(toPrintAndLog):
    printOnly(toPrintAndLog)
    logging.info(toPrintAndLog)

def printAndLogInfo(toPrintAndLog):
    printOnly(toPrintAndLog)
    logging.info(toPrintAndLog)

def printAndLogInfoInThread( toPrintAndLog):    
    printAndLog(toPrintAndLog)
    
def printAndLogWarning(toPrintAndLog):
    printOnly(toPrintAndLog)
    logging.warning(toPrintAndLog)
    
def logging_debug_in_thread( debugMessage):
    if param.logger_level == logging.DEBUG:
        logging.debug(debugMessage)
    
def logging_info_in_thread( infoMessage):
    logging.info(infoMessage)

def configureLogger():
    logger_directory = "logs"
    
    if not os.path.exists(logger_directory):
        os.makedirs(logger_directory)
    
    logger_level = param.logger_level
    
    print("Logger level:" +str(logger_level))
    
    logging.basicConfig(level=logger_level,
                        format='%(asctime)s %(levelname)-8s %(message)s',
                        datefmt='%a, %d %b %Y %H:%M:%S',
                        filename=logger_directory+'\\TransformPerformanceCountersToSplunkMetrics.log',
                        filemode='w')
    #logging.debug
    #logging.info
    #logging.warning
    #logging.error
    #logging.critical
    
    
def get_metric_file_content_lines_from_metrics(metrics):
    splunk_ready_file_content_as_list = list()
    splunk_ready_file_content_as_list.append("metric_timestamp,metric_name,_value")

    for metric in metrics:
        splunk_ready_file_content_as_list.append(metric._time_stamp + "," + metric._name + "," + metric._value_as_string)

    return splunk_ready_file_content_as_list
    

def process_input_zip_files(input_zip_files):
    for input_zip_file in input_zip_files:
        process_input_zip_file( input_zip_file)


def process_input_zip_file(input_zip_file):
    printAndLogInfoInThread("processing " + basename(input_zip_file))

    input_zip_file_file_name = basename(input_zip_file)
    printAndLogInfoInThread("File name:" + input_zip_file_file_name)
    
    input_zip_file_directory_name = os.path.dirname(input_zip_file)    
    printAndLogInfoInThread("Directory name:" + input_zip_file_directory_name)
    
    input_zip_file_file_without_extension = os.path.splitext(input_zip_file_file_name)[0]
    printAndLogInfoInThread("File name without extension:" + input_zip_file_file_without_extension)
    
    temp_folder_name = "temp_" + input_zip_file_file_without_extension
    
    temp_folder_full_path_name = param.output_perflogs_ready_for_splunk_directory + "\\" + temp_folder_name
    
    if os.path.exists(temp_folder_full_path_name):
        logging_info_in_thread("\t" + "Removing:" + temp_folder_full_path_name + ". Previous execution of the tool was probably interrupted")
        shutil.rmtree(temp_folder_full_path_name, ignore_errors=False, onerror=None)
        
    os.makedirs(temp_folder_full_path_name)
    
    printAndLogInfoInThread("extract to temporary folder")
    subprocess.call(r'"C:\Program Files\7-Zip\7z.exe" e ' + "\"" + input_zip_file + "\"" + ' -o' + "\"" + temp_folder_full_path_name + "\"")
    printAndLogInfoInThread("Extraction done")
    
    output_zip_file_name = input_zip_file_file_without_extension
    if len(param.metric_name_fixed_prefix_for_all_mesures) > 0:
        output_zip_file_name = output_zip_file_name + "_" + param.metric_name_fixed_prefix_for_all_mesures
    
    output_zip_file_name = output_zip_file_name + param.output_files_suffix + "_ready_for_splunk." +  param.output_files_extension
    output_zip_file = zipfile.ZipFile(param.output_perflogs_ready_for_splunk_directory + "\\" + output_zip_file_name,'w')
    
    printAndLogInfoInThread("Convert all performance_counter_log to csv")
    
    for performance_counter_log_file in glob.glob(temp_folder_full_path_name + "\\*.csv"):
        handle_performance_counter_log_file(performance_counter_log_file, temp_folder_full_path_name, output_zip_file)

    #Close the zip
    printAndLogInfoInThread("Zip DONE")
    output_zip_file.close()            
        
    printAndLogInfoInThread("Remove temporary directory:" + temp_folder_full_path_name)
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

def print_information_about_filters_applied( all_used_machine_names, all_ignore_machine_names, all_used_application_names, all_ignore_application_names,  all_used_mesure_types, all_ignore_mesure_types):
    logging_info_in_thread( "\t" + "all_used_machine_names:" + ",".join(all_used_machine_names))
    logging_info_in_thread( "\t" + "all_ignore_machine_names:" + ",".join(all_ignore_machine_names))
    logging_info_in_thread( "\t" + "all_used_application_names:" + ",".join(all_used_application_names))
    logging_info_in_thread( "\t" + "all_ignore_application_names:" + ",".join(all_ignore_application_names))
    logging_info_in_thread( "\t" + "all_used_mesure_types:" + ",".join(all_used_mesure_types))
    logging_info_in_thread( "\t" + "all_ignore_mesure_types:" + ",".join(all_ignore_mesure_types))

    
def fill_tranformed_titles_file_content_as_list( converted_csv_file_content):
    tranformed_titles_file_content_as_list = list()  
       
    line_number = 0

    for original_line in converted_csv_file_content.split(param.end_line_character_in_text_file):
        line_number += 1
        transformed_line = remove_faulty_characters_in_performance_counter_log_converted_to_csv( original_line, line_number)
        tranformed_titles_file_content_as_list.append(transformed_line)
            
    return tranformed_titles_file_content_as_list
    
def add_new_aggregated_input_values_from_input_perflogs( step_3_file_with_aggregated_columns_file_full_path, tranformed_titles_file_full_path):
    printAndLogInfoInThread( "Read transformed csv perflog file:" + tranformed_titles_file_full_path + " and create new csv with aggregated columns")
    tranformed_titles_file = open(tranformed_titles_file_full_path, 'rt')
    readerTransp = csv.DictReader(tranformed_titles_file, quoting=csv.QUOTE_ALL)
    
    initial_columns = readerTransp.fieldnames[1:]
    logging_debug_in_thread( "\t initial columns" + str(initial_columns))
    
    
    output_file_fieldnames = list()
    output_file_fieldnames.append("Time")
    output_file_fieldnames = output_file_fieldnames + initial_columns
    
    for perflog_counter_to_aggregate_with_plus_operation in param.perflog_counters_to_aggregate_with_plus_operation:
        perflog_counter_aggregated_name = perflog_counter_to_aggregate_with_plus_operation[1]
        output_file_fieldnames.append(perflog_counter_aggregated_name)
        logging_debug_in_thread( "Add column:" + perflog_counter_aggregated_name)
    
    
    step_3_file_with_aggregated_columns_file = open(step_3_file_with_aggregated_columns_file_full_path, "w", newline='')
    output_file_writer = csv.DictWriter(step_3_file_with_aggregated_columns_file, fieldnames=output_file_fieldnames, delimiter=',')
    output_file_writer.writeheader()

    logging_debug_in_thread( "Columns are now " +  str(output_file_fieldnames))
    
    for initial_row in readerTransp:
        
        output_dictionary = {}        

        output_dictionary["Time"] = initial_row["Time"]

        for initial_column in initial_columns:        
            value_as_string = initial_row[initial_column]
            output_dictionary[initial_column] = value_as_string

    
        for perflog_counter_to_aggregate_with_plus_operation in param.perflog_counters_to_aggregate_with_plus_operation:

            perflog_counter_columns_to_aggregate_regex_pattern_as_string = perflog_counter_to_aggregate_with_plus_operation[0]            
            perflog_counter_columns_to_aggregate_regex_pattern = re.compile(perflog_counter_columns_to_aggregate_regex_pattern_as_string)
            perflog_counter_aggregated_name = perflog_counter_to_aggregate_with_plus_operation[1]

            aggregated_value = 0.0

            for initial_column in initial_columns:
                value_as_string = initial_row[initial_column]
                match_mesure_type = perflog_counter_columns_to_aggregate_regex_pattern.match(initial_column)
                if match_mesure_type != None:                  
                    if value_as_string == " ":
                        logging_debug_in_thread( "\t" + "Cannot agregate column:" + initial_column + " that matches " + perflog_counter_columns_to_aggregate_regex_pattern_as_string +  " because is blank:" + value_as_string) #in row:" + str(row))
                    else:
                        logging_debug_in_thread( "Aggregating column " + initial_column  + " because matches " + perflog_counter_columns_to_aggregate_regex_pattern_as_string+ " with value:" + value_as_string)
                        value_as_float = float(value_as_string)
                        aggregated_value = aggregated_value + value_as_float

            logging_debug_in_thread( "Aggregated column " + perflog_counter_aggregated_name  + " has value:" + str(aggregated_value))
            output_dictionary[perflog_counter_aggregated_name] = str(aggregated_value)

        
        output_file_writer.writerow(output_dictionary)
            
    step_3_file_with_aggregated_columns_file.close()

def convert_performance_counter_log_file_to_metric_file( performance_counter_log_file_file_name, performance_counter_log_file_full_path):
    printAndLogInfoInThread( "Convert performance_counter_log file:" + performance_counter_log_file_file_name)  

    printAndLogInfoInThread( "Open file to tranform: " + performance_counter_log_file_full_path)        
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

def format_csv_from_performance_counter_log_and_fix_titles( step_1_performance_counter_log_converted_csv_file_content, performance_counter_log_file_without_extension, temp_folder_full_path_name):

    step_2_tranformed_titles_file_content_as_list = fill_tranformed_titles_file_content_as_list( step_1_performance_counter_log_converted_csv_file_content)

    step_2_tranformed_titles_file_name = performance_counter_log_file_without_extension + "_step_2_titles_transformed"
    step_2_tranformed_titles_file_full_path = temp_folder_full_path_name + "\\" + step_2_tranformed_titles_file_name + ".csv"

    printAndLogInfoInThread( "Create transformed file: " + step_2_tranformed_titles_file_name)    
    step_2_tranformed_titles_file = open(step_2_tranformed_titles_file_full_path, "w")

    printAndLogInfoInThread( "Fill transformed csv perflog file: " + step_2_tranformed_titles_file_name)    
    step_2_tranformed_titles_file_content = param.end_line_character_in_text_file.join(step_2_tranformed_titles_file_content_as_list)
    # Remove useless " charactere around fields
    step_2_tranformed_titles_file_content = step_2_tranformed_titles_file_content.replace('"',"")
    step_2_tranformed_titles_file.write(step_2_tranformed_titles_file_content)

    printAndLogInfoInThread( "Close transformed file:" + step_2_tranformed_titles_file_name)
    step_2_tranformed_titles_file.close()

    return step_2_tranformed_titles_file_full_path

    
def handle_performance_counter_log_file( performance_counter_log_file_full_path, temp_folder_full_path_name, output_zip_file):
    performance_counter_log_file_file_name = basename(performance_counter_log_file_full_path)        
    performance_counter_log_file_without_extension = os.path.splitext(performance_counter_log_file_file_name)[0]
    
    metrics = convert_performance_counter_log_file_to_metric_file( performance_counter_log_file_file_name, performance_counter_log_file_full_path)
    printAndLogInfoInThread("Number of metrics created:" + str(len(metrics)))

    metric_file_content_lines = get_metric_file_content_lines_from_metrics(metrics)
    printAndLogInfoInThread("Metric file content computed")
    

    splunk_ready_file_name = performance_counter_log_file_without_extension + "_as_splunk_metric.csv"
    splunk_ready_file_full_path =  temp_folder_full_path_name + "\\" + splunk_ready_file_name

    
    splunk_ready_file_full_path = create_splunk_ready_metric_file(splunk_ready_file_full_path, metric_file_content_lines)

    # add to zip
    add_splunk_ready_metric_file_to_zip(output_zip_file, splunk_ready_file_full_path, splunk_ready_file_name)

    os.remove(splunk_ready_file_full_path)
 
def create_splunk_ready_metric_file( splunk_ready_file_name, splunk_ready_file_content_as_list):
    printAndLogInfoInThread( "Create final Splunk ready file: " + splunk_ready_file_name)    
    splunk_ready_file = open(splunk_ready_file_name, "w")

    printAndLogInfoInThread( "Fill final Splunk ready file: " + splunk_ready_file_name)    
    splunk_ready_file_content = param.end_line_character_in_text_file.join(splunk_ready_file_content_as_list)
    splunk_ready_file.write(splunk_ready_file_content)

    printAndLogInfoInThread( "Close final Splunk ready file:" + splunk_ready_file_name)
    splunk_ready_file.close()
    
    return splunk_ready_file_name
 

def add_splunk_ready_metric_file_to_zip(output_zip_file, splunk_ready_file_full_path, splunk_ready_file_name):
    output_zip_file.write(splunk_ready_file_full_path, arcname=splunk_ready_file_name + ".csv", compress_type=zipfile.ZIP_DEFLATED)
            
def process_input_zip_file( input_zip_file):
    printAndLogInfoInThread( "processing " + basename(input_zip_file))

    input_zip_file_file_name = basename(input_zip_file)
    printAndLogInfoInThread( "File name:" + input_zip_file_file_name)
    
    input_zip_file_directory_name = os.path.dirname(input_zip_file)    
    printAndLogInfoInThread( "Directory name:" + input_zip_file_directory_name)
    
    input_zip_file_file_without_extension = os.path.splitext(input_zip_file_file_name)[0]
    printAndLogInfoInThread( "File name without extension:" + input_zip_file_file_without_extension)
    
    temp_folder_name = "temp_" + input_zip_file_file_without_extension
    
    temp_folder_full_path_name = param.output_metrics_ready_for_splunk_directory + "\\" + temp_folder_name
    
    if os.path.exists(temp_folder_full_path_name):
        logging_info_in_thread( "\t" + "Removing:" + temp_folder_full_path_name + ". Previous execution of the tool was probably interrupted")
        shutil.rmtree(temp_folder_full_path_name, ignore_errors=False, onerror=None)
        
    os.makedirs(temp_folder_full_path_name)
    
    printAndLogInfoInThread( "extract to temporary folder")
    subprocess.call(r'"C:\Program Files\7-Zip\7z.exe" e ' + "\"" + input_zip_file + "\"" + ' -o' + "\"" + temp_folder_full_path_name + "\"")
    printAndLogInfoInThread( "Extraction done")
    
    output_zip_file_name = input_zip_file_file_without_extension
    if len(param.metric_name_fixed_prefix_for_all_mesures) > 0:
        output_zip_file_name = output_zip_file_name + "_" + param.metric_name_fixed_prefix_for_all_mesures
    
    output_zip_file_name = output_zip_file_name + param.output_files_suffix + "_ready_for_splunk." +  param.output_files_extension
    output_zip_file = zipfile.ZipFile(param.output_metrics_ready_for_splunk_directory + "\\" + output_zip_file_name,'w')
    
    printAndLogInfoInThread( "Convert all performance counters logs to csv")
    
    handle_performance_counter_log_files = glob.glob(temp_folder_full_path_name + "\\*" + param.input_performance_counter_file_extension)
    for performance_counter_log_file in handle_performance_counter_log_files:
        handle_performance_counter_log_file( performance_counter_log_file, temp_folder_full_path_name, output_zip_file)

    #Close the zip
    printAndLogInfoInThread( "Zip DONE")
    output_zip_file.close()            
        
    printAndLogInfoInThread( "Remove temporary directory:" + temp_folder_full_path_name)
    shutil.rmtree(temp_folder_full_path_name)


def process_data(thread):
    queueOfPerflogArchivesToTransform = thread.queueOfPerflogArchivesToTransform
    threadName = thread.name
    while not exitFlag:
        printAndLogInfoInThread( "Acquire queueLock")
        queueLock.acquire()
        if not queueOfPerflogArchivesToTransform.empty():
            input_zip_file = queueOfPerflogArchivesToTransform.get()
            printAndLogInfoInThread( "Release queueLock")
            queueLock.release()
            
            process_input_zip_file( input_zip_file)
        else:
            printAndLogInfoInThread( "Queue queueOfPerflogArchivesToTransform is empty")
            printAndLogInfoInThread( "Release queueLock")
            queueLock.release()
            time.sleep(1)

def main(argv):

    configureLogger()    
    
    printAndLog('Start application')
    
    # Get the list of input zips

    input_zip_files = glob.glob(param.input_files_directory + "\\*.zip")
    input_7z_files = glob.glob(param.input_files_directory + "\\*.7z")
    
    logging.info('input_zip_files:' + ",".join(input_zip_files))
    logging.info('input_7z_files:' + ",".join(input_7z_files))
    
    input_files = input_zip_files+input_7z_files
       
    logging.info('input_csv_files:' + ",".join(input_zip_files))
        
    process_input_zip_files(input_files)    
    

    printAndLog("Exiting main thread")

    
    printAndLog("End. Nominal end of application")
    

if __name__ == "__main__":
   main(sys.argv[1:])