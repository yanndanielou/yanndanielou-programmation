# -*-coding:Utf-8 -*
#

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

def print_and_log_info(toPrintAndLog):
    printOnly(toPrintAndLog)
    logging.info(toPrintAndLog)

def print_and_log_infoInThread(thread, toPrintAndLog):    
    if thread != None:
        toPrintAndLog  = thread.name + "\t" + toPrintAndLog
    printAndLog(toPrintAndLog)
    
def print_and_log_warning(toPrintAndLog):
    printOnly(toPrintAndLog)
    logging.warning(toPrintAndLog)
    
def logging_debug_in_thread(thread, debugMessage):
    if param.logger_level == logging.DEBUG:
        if thread != None:
            debugMessage  = thread.name + "\t" + debugMessage
        logging.debug(debugMessage)
    
def logging_info_in_thread(thread, infoMessage):
    if thread != None:
        debugMessage  = thread.name + "\t" + infoMessage
    logging.info(infoMessage)

def configure_logger():
    logger_directory = "logs"
    
    if not os.path.exists(logger_directory):
        os.makedirs(logger_directory)
    
    logger_level = param.logger_level
    
    print("Logger level:" +str(logger_level))
    
    logging.basicConfig(level=logger_level,
                        format='%(asctime)s %(levelname)-8s %(message)s',
                        datefmt='%a, %d %b %Y %H:%M:%S',
                        filename=logger_directory+'\COVID-19.log',
                        filemode='w')
    #logging.debug
    #logging.info
    #logging.warning
    #logging.error
    #logging.critical
    
    
class Metric:

    def __init__(self, time_stamp, name, value_as_string):
        self._time_stamp = time_stamp        
        self._value_as_string = value_as_string
        self._name = name
        
    def getDayAsString(self):
        return getIntAsTwoDigitsString(self._time_stamp.day)
        
    def getMonthAsString(self):
        return getIntAsTwoDigitsString(self._time_stamp.month)
        
    def getYearAsString(self):
        return str(self._time_stamp.year)
        
    def getHourAsString(self):
        return getIntAsTwoDigitsString(self._time_stamp.hour)
        
    def getMinuteAsString(self):
        return getIntAsTwoDigitsString(self._time_stamp.minute)
        
    def getSecondAsString(self):
        return getIntAsTwoDigitsString(self._time_stamp.second)
        
    def getMillisecondAsString(self):
        millisecondAsString = str(int(self._time_stamp.microsecond/1000))
        while len(millisecondAsString) < 3:
            millisecondAsString = "0" + millisecondAsString
        return millisecondAsString        
        
    def getDateAsSplunkMetricsFormat(self):
        """ Ex of Splunk date time format:12/30/2018 04:11:53.466 """
        metrics_date_format = self.getMonthAsString() + "/" + self.getDayAsString() + "/" + self.getYearAsString() + " " + self.getHourAsString() + ":" + self.getMinuteAsString() + ":" + self.getSecondAsString() + "." + self.getMillisecondAsString()
        return metrics_date_format
        
    def getContentForSplunkMetrics(self):
        return self._name + "," + self.getDateAsSplunkMetricsFormat() + "," + self._value_as_string + param.end_line_character_in_text_file
        
    def getDateAsJsonMetricsFormat(self):
        """ {"treatment_date":"2018-12-20 03:03:38.753","utc_treatment_date":"2018-12-20 00:03:38.753","time_stamp":"","id":"S_SRV_B_CPU_TEMPERATURE","equipment_id":"EQ_SRV_B_SRV_B","signal_type":"TG","equipment":"SERVER_B","localisation":"SERVER_B","label":"CPU temperature","old_state":36,"new_state":37,"exec_status":"","caller":"","orders":"","cat_ala":0,"jdb":"0"}, """ 
        json_metrics_date_format = self.getYearAsString() + "-" + self.getMonthAsString() + "-" + self.getDayAsString() + " " + self.getHourAsString() + ":" + self.getMinuteAsString() + ":" + self.getSecondAsString() + "." + self.getMillisecondAsString()
        return json_metrics_date_format
        
    def getContentForJson(self):
        """ Ex: {"time_stamp":"2018-12-19 22:00:49.700","id":"S_TRAIN_CC_214_KM_COUNTER","equipment_id":"EQ_CET_214","signal_type":"TG","equipment":"CET_214","localisation":"TB_PSED009_04","label":"Number of kilometers","old_state":4652,"new_state":4653,"exec_status":"","caller":"","orders":"","cat_ala":0,"jdb":"0"},"""
        contentForJson = '{"time_stamp":"' + self.getDateAsJsonMetricsFormat() + '","name":"' + self._name +  '","value":"' + self._value_as_string + '"},' + param.end_line_character_in_text_file
        return contentForJson
    
def transform_time_field_to_splunk_metrics_format(time_field):
    """ Input  
        PATH: 11/14/2019 02:02:03.843 
        PAR1: 08/31/2019 07:52:40.016

        Output
        [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss][.fff]±[hh]:[mm]
        Example: 2020-01-06T04:00:00.134+01:00
    """
    year_yyyy = time_field[6:10]
    month_mm = time_field[0:2]
    day_dd = time_field[3:5]
    
    hour_hh = time_field[11:13]
    minute_mm = time_field[14:16]
    second_ss = time_field[17:19]
    
    millisecond_fff = time_field[20:23]
    
    time_field_as_splunk_metrics_format = year_yyyy + "-" + month_mm + "-" + day_dd + "T" + hour_hh + ":" + minute_mm + ":" + second_ss + "." + millisecond_fff + "+00:00"
    return time_field_as_splunk_metrics_format

def add_datapoint_in_output_mesure_file(metric_name,metric_timestamp,value,splunk_ready_file_content_as_list):
    if len(param.metric_name_fixed_prefix_for_all_mesures) > 0:
        metric_name = param.metric_name_fixed_prefix_for_all_mesures + "." + metric_name
    splunk_ready_file_content_as_list.append(metric_timestamp + "," + metric_name + "," + value)
    

class myThread (threading.Thread):
    def __init__(self, threadID, name, queueOfPerflogArchivesToTransform):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.name = name
        self.queueOfPerflogArchivesToTransform = queueOfPerflogArchivesToTransform
        
    def run(self):
        print_and_log_infoInThread (self, "Starting " + self.name)
        process_data(self)
        print_and_log_infoInThread (self, "Exiting " + self.name)



def process_csse_covid_19_daily_reports(csse_covid_19_daily_reports):
    for csse_covid_19_daily_report in csse_covid_19_daily_reports:
        process_csse_covid_19_daily_report(csse_covid_19_daily_report)
        
        
def process_csse_covid_19_daily_report(csse_covid_19_daily_report_path_name):
    print_and_log_info("Open file: " + csse_covid_19_daily_report_path_name)        
    csse_covid_19_daily_report_file = open(csse_covid_19_daily_report_path_name, "r")
    #csse_covid_19_daily_report_file_content = csse_covid_19_daily_report_file.read()
    readerTransp = csv.DictReader(csse_covid_19_daily_report_file, quoting=csv.QUOTE_ALL)
    columns = readerTransp.fieldnames[1:]
    print_and_log_info("Columns: \t" + str(columns))

    print_and_log_info("Close  file")
    csse_covid_19_daily_report_file.close()       
    

def remove_faulty_characters_in_blg_converted_to_csv(thread, original_line, line_number):   
    #To avoid exception: UnicodeEncodeError: 'charmap' codec can't encode character '\u2019' in position 7371: character maps to <undefined>
    original_line_without_forbidden_chararcters =  original_line.replace(u"\u2019", "_") # Example: simple quote in "Longueur de la file d’attente de sortie"
           
    # Some metric name have coma (ex: "\\ATS_CEN1\Processor Information(0,_Total)\% Processor Performance")
    # So replace temporay the coma fields (mesures) separator by another string
    initial_fields_separator_in_relog_blg = '","'
    temporary_fields_separator_to_remove_coma_that_must_not_be_in_initial_file = '"azertyuiopiuyez"'

    transformed_line = original_line_without_forbidden_chararcters

    if line_number == 1:
        print_and_log_infoInThread(thread, "Header line to transform:" + original_line_without_forbidden_chararcters)
        
        # Find time field
        transformed_line = transformed_line.replace("(PDH-CSV 4.0) (Paris, Madrid)(-60)", "Time")
        transformed_line = transformed_line.replace("(PDH-CSV 4.0) (Romance Standard Time)(-60)", "Time")
        transformed_line = transformed_line.replace("(PDH-CSV 4.0) (Paris, Madrid (heure d", "Time")
        
        
        # So replace temporay the coma fields (mesures) separator by another string to remove coma.
        # Ex: 
        transformed_line = transformed_line.replace(initial_fields_separator_in_relog_blg, temporary_fields_separator_to_remove_coma_that_must_not_be_in_initial_file)
        transformed_line = transformed_line.replace(",", "_")
        transformed_line = transformed_line.replace(temporary_fields_separator_to_remove_coma_that_must_not_be_in_initial_file, initial_fields_separator_in_relog_blg)
        
        transformed_line = transformed_line.replace(", ", "_") # Ex: \\ATS_CEN1\Interfacex\Paquets reçus, inconnus
        
        transformed_line = transformed_line.replace(".{", "_") # Ex: "ATS_CEN1.Network_Interface_isatap.{0210E056-E9DB-4F55-8389-5EA3D29A7B45}.Bytes_Received_per_sec"
        transformed_line = transformed_line.replace("}", "") # Uusually at the end of the name, so no need to replace by an underscodr. Ex: "ATS_CEN1.Network_Interface_isatap.{0210E056-E9DB-4F55-8389-5EA3D29A7B45}.Bytes_Received_per_sec"
        transformed_line = transformed_line.replace("-", "_") # Ex: "ATS_CEN1.Network_Interface_isatap.{0210E056-E9DB-4F55-8389-5EA3D29A7B45}.Bytes_Received_per_sec"
                        
        transformed_line = transformed_line.replace(".net", "dotnet") # The character point . is reserved (it splits the information in the datapoint). Ex: \\ATS_CEN1\Process(tcpdsvc_62_x86_.net2010_10.0)\Private Bytes
        
        transformed_line = transformed_line.replace("\\\\", "")
        transformed_line = transformed_line.replace("\\% ", ".")
        transformed_line = transformed_line.replace("\\", ".")
        transformed_line = transformed_line.replace("(", "_")
        transformed_line = transformed_line.replace(")", "")
        transformed_line = transformed_line.replace(" ", "_")
        transformed_line = transformed_line.replace(":", "")
        transformed_line = transformed_line.replace("__", "_")
        transformed_line = transformed_line.replace("/", "_per_")    
        
        transformed_line = transformed_line.replace("[", "_")
        transformed_line = transformed_line.replace("]", "_")                        

        transformed_line = transformed_line.replace("'", "_") # avoid special french characters. Ex: "\\ATS_CEN1\Interfacex\Longueur de la file d'attente de sortie"
        transformed_line = transformed_line.replace("é", "e") # avoid special french characters. Ex: "\\ATS_CEN1\Interface réseau"
        
    return transformed_line
        
        
def perform_averages_for_mesure_matching_filters(thread, splunk_ready_file_content_as_list, perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average, perflog_mesures_to_average_in_progress, column, value_as_string, time_field_splunk_metrics_format):
    perflog_mesure_to_average_is_in_progress = False
    current_perflog_must_be_averaged = False
            
    for perflog_name_to_average_and_number_of_mesures_to_average in perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average:
        perfolg_name_to_average_regex_pattern_as_string = perflog_name_to_average_and_number_of_mesures_to_average[0]
        perfolg_name_to_average_regex_pattern = perflog_name_to_average_and_number_of_mesures_to_average[1]
        
        match_perflog_name_to_average = perfolg_name_to_average_regex_pattern.match(column)
        
        if match_perflog_name_to_average != None:
            current_perflog_must_be_averaged = True
            number_of_mesures_points_to_average = perflog_name_to_average_and_number_of_mesures_to_average[2]
            #logging_debug_in_thread(thread, "\t" + column + " must be averaged with coeff:" + str(number_of_mesures_points_to_average)+ ". Value of current perflog line: " + value_as_string)
            
            for perflog_mesure_to_average_in_progress in perflog_mesures_to_average_in_progress:
                if perflog_mesure_to_average_in_progress[0] == column:
                    perflog_mesure_to_average_is_in_progress = True
                    current_coeff_for_average = perflog_mesure_to_average_in_progress[1] + 1
                    new_sum =  perflog_mesure_to_average_in_progress[2] + float(value_as_string)
                    
                    if current_coeff_for_average >= number_of_mesures_points_to_average:
                        average_calculated =  new_sum/current_coeff_for_average
                        average_calculated_as_str = str(average_calculated)
                        logging_debug_in_thread(thread, "\t" + column + " has reached the average calculation. Average is:" + average_calculated_as_str)
                        add_datapoint_in_output_mesure_file(column, time_field_splunk_metrics_format, average_calculated_as_str, splunk_ready_file_content_as_list)
                        perflog_mesures_to_average_in_progress.remove(perflog_mesure_to_average_in_progress)                                                    
                    else:                                                                                                            
                        new_number_of_mesures = perflog_mesure_to_average_in_progress[1] + 1                                                                                                        
                        logging_debug_in_thread(thread, "\t" + column + " has not reached yet the average calculation. Current sum is:" + str(new_sum) + ", based on:" + str(new_number_of_mesures) + " number of mesures")                                                    
                        perflog_mesures_to_average_in_progress.remove(perflog_mesure_to_average_in_progress)
                        perflog_mesures_to_average_in_progress.append((column, new_number_of_mesures, new_sum))
                        
                    break
            
            if perflog_mesure_to_average_is_in_progress == True:
                break
                
            if perflog_mesure_to_average_is_in_progress == False:
                if number_of_mesures_points_to_average == 1:                            
                    logging_debug_in_thread(thread, "\t" + column + " is inserted because must not be averaged (all points indexed)")    
                    add_datapoint_in_output_mesure_file(column, time_field_splunk_metrics_format, value_as_string, splunk_ready_file_content_as_list)
                else:
                    value_as_float = float(value_as_string)
                    logging_debug_in_thread(thread, "\t" + column + " is inserted to the current averages in progress. Current sum is:" + value_as_string)
                    perflog_mesures_to_average_in_progress.append((column, 1, value_as_float))
                    
                break
            
        #else:
        #    logging_debug_in_thread(thread, "\t" + column + " must not be averaged for regex:" + perfolg_name_to_average_regex_pattern_as_string)
    
    if current_perflog_must_be_averaged == False:
        logging_debug_in_thread(thread, "\t" + column + " must not be averaged. Directly added without transformation")        
        add_datapoint_in_output_mesure_file(column, time_field_splunk_metrics_format, value_as_string, splunk_ready_file_content_as_list)
        
def fill_datapoints_in_output_mesure_file(thread, readerTransp, columns, all_used_machine_names, all_ignore_machine_names, all_used_application_names, all_ignore_application_names,  all_used_mesure_types, all_ignore_mesure_types):

    splunk_ready_file_content_as_list = list()
    splunk_ready_file_content_as_list.append("metric_timestamp,metric_name,_value")        
    
    perflog_mesures_to_average_in_progress = list()
    
    # Done before for CPU optimisation
    machines_to_keep_regex_pattern = re.compile(param.machines_to_keep_regex_pattern_as_string)
    applications_to_keep_regex_pattern = re.compile(param.applications_to_keep_regex_pattern_as_string)
    mesure_type_to_keep_regex_pattern = re.compile(param.mesure_type_to_keep_regex_pattern_as_string)
    
    #
    perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average = list()

    for perflog_name_to_average_and_number_of_mesures_to_average in param.perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average:
        perfolg_name_to_average_regex_pattern_as_string = perflog_name_to_average_and_number_of_mesures_to_average[0]
        perfolg_name_to_average_regex_pattern = re.compile(perfolg_name_to_average_regex_pattern_as_string)
        number_of_mesures_points_to_average = perflog_name_to_average_and_number_of_mesures_to_average[1]
        perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average.append((perfolg_name_to_average_regex_pattern_as_string, perfolg_name_to_average_regex_pattern, number_of_mesures_points_to_average))
    
    for row in readerTransp:
        time_field = (row['Time'])
        time_field_splunk_metrics_format = transform_time_field_to_splunk_metrics_format(time_field)
                        
        for column in columns:        
            value_as_string = row[column]
            if value_as_string == " ":
                logging_debug_in_thread(thread, "\t" + "Filter column:" + column + " that is blank") #in row:" + str(row))
            else:
                
                #if len(column.split(".")) < 3:
                #    column = column.replace("ATS_ARCH1","ATS_ARCH1.")
                #    column = column.replace("ATS_ARCH2","ATS_ARCH2.")
                #    column = column.replace("ATS_CEN1","ATS_CEN1.")
                #    column = column.replace("ATS_CEN2","ATS_CEN2.")
            
                machine_name = column.split(".")[0];
                application_name = column.split(".")[1];
                mesure_type = column.split(".")[2];
                
                
                match_machine = machines_to_keep_regex_pattern.match(machine_name)
                
                match_application = applications_to_keep_regex_pattern.match(application_name)
                
                match_mesure_type = mesure_type_to_keep_regex_pattern.match(mesure_type)

                if match_machine != None:
                    all_used_machine_names.add(machine_name)
                else:
                    all_ignore_machine_names.add(machine_name)
                    #logging_debug_in_thread(thread, "\t" + machine_name + " did not match machine name pattern. Capture " + column + " is ignored")

                if match_application != None:
                    all_used_application_names.add(application_name)
                else:
                    all_ignore_application_names.add(application_name)
                    #logging_debug_in_thread(thread, "\t" + application_name + " did not match application name pattern. Capture " + column + " is ignored")

                if match_mesure_type != None:
                    all_used_mesure_types.add(mesure_type)
                else:
                    all_ignore_mesure_types.add(mesure_type)
                    #logging_debug_in_thread(thread, "\t" + mesure_type + " did not match mesure type pattern. Capture " + column + " is ignored")

                if match_machine != None and match_application != None and match_mesure_type != None:
                    perform_averages_for_mesure_matching_filters(thread, splunk_ready_file_content_as_list, perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average, perflog_mesures_to_average_in_progress, column, value_as_string, time_field_splunk_metrics_format)
                    
    
    return splunk_ready_file_content_as_list
       
def compute_splunk_ready_file_name(blg_file_without_extension, all_used_machine_names, all_ignore_machine_names, all_used_application_names, all_ignore_application_names,  all_used_mesure_types, all_ignore_mesure_types):
    splunk_ready_file_name = blg_file_without_extension 
    
    if len(param.metric_name_fixed_prefix_for_all_mesures) > 0:
        splunk_ready_file_name = splunk_ready_file_name + "_" + param.metric_name_fixed_prefix_for_all_mesures
    
    if len(all_ignore_machine_names) > 0 or len(all_ignore_application_names) > 0 or len(all_ignore_mesure_types) > 0:
        splunk_ready_file_name = splunk_ready_file_name + "_partial"
        
        if len(all_ignore_machine_names) > 0:
            splunk_ready_file_name = splunk_ready_file_name + "_".join(all_used_machine_names)
            
        if len(all_ignore_application_names) > 0:
            splunk_ready_file_name = splunk_ready_file_name + "_".join(all_used_application_names).replace("Process_","")
            
        #if len(all_ignore_mesure_types) > 0:
        #    splunk_ready_file_name = splunk_ready_file_name + "_".join(all_used_mesure_types)
        
    return splunk_ready_file_name

def print_information_about_filters_applied(thread, all_used_machine_names, all_ignore_machine_names, all_used_application_names, all_ignore_application_names,  all_used_mesure_types, all_ignore_mesure_types):
    logging_info_in_thread(thread, "\t" + "all_used_machine_names:" + ",".join(all_used_machine_names))
    logging_info_in_thread(thread, "\t" + "all_ignore_machine_names:" + ",".join(all_ignore_machine_names))
    logging_info_in_thread(thread, "\t" + "all_used_application_names:" + ",".join(all_used_application_names))
    logging_info_in_thread(thread, "\t" + "all_ignore_application_names:" + ",".join(all_ignore_application_names))
    logging_info_in_thread(thread, "\t" + "all_used_mesure_types:" + ",".join(all_used_mesure_types))
    logging_info_in_thread(thread, "\t" + "all_ignore_mesure_types:" + ",".join(all_ignore_mesure_types))

    
def fill_tranformed_titles_file_content_as_list(thread, converted_csv_file_content):
    tranformed_titles_file_content_as_list = list()  
       
    line_number = 0

    for original_line in converted_csv_file_content.split(param.end_line_character_in_text_file):
        line_number += 1
        transformed_line = remove_faulty_characters_in_blg_converted_to_csv(thread, original_line, line_number)
        tranformed_titles_file_content_as_list.append(transformed_line)
            
    return tranformed_titles_file_content_as_list
    
    
def handle_blg_file(thread, blg_file, temp_folder_full_path_name, output_zip_file):
    blg_file_file_name = basename(blg_file)        
    blg_file_without_extension = os.path.splitext(blg_file_file_name)[0]
    
    
    converted_csv_file_name =  blg_file_without_extension + ".csv"
    converted_csv_file_full_path_name =  temp_folder_full_path_name + "\\" + converted_csv_file_name
    
    print_and_log_infoInThread(thread, "Convert blg file:" + blg_file_file_name)        
    return_code = subprocess.call(r'relog "' + blg_file + '" -f CSV -o "' + converted_csv_file_full_path_name + '"')
    
    if(return_code != 0):
        print_and_log_infoInThread(thread, "!!!!!!!!!!!!        ERROR        !!!!!!!!!!!!")
        print_and_log_infoInThread(thread, "Could not convert blg file:" + blg_file_file_name + " to " + converted_csv_file_full_path_name)
        print_and_log_infoInThread(thread, "!!!!!!!!!!!!        ERROR        !!!!!!!!!!!!")
        subprocess.call(r'timeout /T 10')
    else:
        print_and_log_infoInThread(thread, "Remove converted blg file:" + blg_file_file_name)
        os.remove(blg_file)
    
        print_and_log_infoInThread(thread, "Open file to tranform: " + converted_csv_file_name)        
        converted_csv_file_file = open(converted_csv_file_full_path_name, "r")
        converted_csv_file_content = converted_csv_file_file.read()
        
        converted_csv_file_file.close()            
        os.remove(converted_csv_file_full_path_name)            
                
        tranformed_titles_file_content_as_list = fill_tranformed_titles_file_content_as_list(thread, converted_csv_file_content)
        
        tranformed_titles_file_name = blg_file_without_extension + "_transformed"
        tranformed_titles_file_full_path = temp_folder_full_path_name + "\\" + tranformed_titles_file_name + ".csv"
        
        print_and_log_infoInThread(thread, "Create transformed file: " + tranformed_titles_file_name)    
        tranformed_titles_file = open(tranformed_titles_file_full_path, "w")
        
        print_and_log_infoInThread(thread, "Fill transformed csv perflog file: " + tranformed_titles_file_name)    
        tranformed_titles_file_content = param.end_line_character_in_text_file.join(tranformed_titles_file_content_as_list)
        tranformed_titles_file.write(tranformed_titles_file_content)
        
        print_and_log_infoInThread(thread, "Close transformed file:" + tranformed_titles_file_name)
        tranformed_titles_file.close()
        
        print_and_log_infoInThread(thread, "Read transformed csv perflog file:" + tranformed_titles_file_name + " and create metrics content")
        csvfilePerflog = open(tranformed_titles_file_full_path, 'rt')
        readerTransp = csv.DictReader(csvfilePerflog, quoting=csv.QUOTE_ALL)
        
        columns = readerTransp.fieldnames[1:]
        logging_debug_in_thread(thread, "\t" + str(columns))
        
        # kept for stats
        all_used_machine_names = set()
        all_ignore_machine_names = set()
        
        # kept for stats
        all_used_application_names = set()
        all_ignore_application_names = set()
        
        # kept for stats
        all_used_mesure_types = set()
        all_ignore_mesure_types = set()
                
        splunk_ready_file_content_as_list = fill_datapoints_in_output_mesure_file(thread, readerTransp, columns, all_used_machine_names, all_ignore_machine_names, all_used_application_names, all_ignore_application_names,  all_used_mesure_types, all_ignore_mesure_types)
        print_and_log_infoInThread(thread, "End of metrics content computation")
        
        print_and_log_infoInThread(thread, "Close transformed csv perflog file:" + tranformed_titles_file_name + " and create metrics content")
        csvfilePerflog.close()        
        os.remove(tranformed_titles_file_full_path)
                       
        splunk_ready_file_name = compute_splunk_ready_file_name(blg_file_without_extension, all_used_machine_names, all_ignore_machine_names, all_used_application_names, all_ignore_application_names,  all_used_mesure_types, all_ignore_mesure_types)
        
        print_information_about_filters_applied(thread, all_used_machine_names, all_ignore_machine_names, all_used_application_names, all_ignore_application_names,  all_used_mesure_types, all_ignore_mesure_types)
        
        splunk_ready_file_full_path = create_splunk_ready_metric_file(thread, splunk_ready_file_name, splunk_ready_file_content_as_list)

        # add to zip
        add_splunk_ready_metric_file_to_zip(output_zip_file, splunk_ready_file_full_path, splunk_ready_file_name)
    
        os.remove(splunk_ready_file_full_path)
 
def create_splunk_ready_metric_file(thread, splunk_ready_file_name, splunk_ready_file_content_as_list):
    splunk_ready_file_name = splunk_ready_file_name + "_as_splunk_metrics"
    splunk_ready_file_full_path = param.output_perflogs_ready_for_splunk_directory + "\\" + splunk_ready_file_name + ".csv"

    print_and_log_infoInThread(thread, "Create final Splunk ready file: " + splunk_ready_file_name)    
    splunk_ready_file = open(splunk_ready_file_full_path, "w")

    print_and_log_infoInThread(thread, "Fill final Splunk ready file: " + splunk_ready_file_name)    
    splunk_ready_file_content = param.end_line_character_in_text_file.join(splunk_ready_file_content_as_list)
    splunk_ready_file.write(splunk_ready_file_content)

    print_and_log_infoInThread(thread, "Close final Splunk ready file:" + splunk_ready_file_name)
    splunk_ready_file.close()
    
    return splunk_ready_file_full_path
 

def add_splunk_ready_metric_file_to_zip(output_zip_file, splunk_ready_file_full_path, splunk_ready_file_name):
    output_zip_file.write(splunk_ready_file_full_path, arcname=splunk_ready_file_name + ".csv", compress_type=zipfile.ZIP_DEFLATED)
            
def process_input_zip_file(thread, input_zip_file):
    print_and_log_infoInThread(thread, "processing " + basename(input_zip_file))

    input_zip_file_file_name = basename(input_zip_file)
    print_and_log_infoInThread(thread, "File name:" + input_zip_file_file_name)
    
    input_zip_file_directory_name = os.path.dirname(input_zip_file)    
    print_and_log_infoInThread(thread, "Directory name:" + input_zip_file_directory_name)
    
    input_zip_file_file_without_extension = os.path.splitext(input_zip_file_file_name)[0]
    print_and_log_infoInThread(thread, "File name without extension:" + input_zip_file_file_without_extension)
    
    temp_folder_name = "temp_" + input_zip_file_file_without_extension
    
    temp_folder_full_path_name = param.output_perflogs_ready_for_splunk_directory + "\\" + temp_folder_name
    
    if os.path.exists(temp_folder_full_path_name):
        logging_info_in_thread(thread, "\t" + "Removing:" + temp_folder_full_path_name + ". Previous execution of the tool was probably interrupted")
        shutil.rmtree(temp_folder_full_path_name, ignore_errors=False, onerror=None)
        
    os.makedirs(temp_folder_full_path_name)
    
    print_and_log_infoInThread(thread, "extract to temporary folder")
    subprocess.call(r'"C:\Program Files\7-Zip\7z.exe" e ' + "\"" + input_zip_file + "\"" + ' -o' + "\"" + temp_folder_full_path_name + "\"")
    print_and_log_infoInThread(thread, "Extraction done")
    
    output_zip_file_name = input_zip_file_file_without_extension
    if len(param.metric_name_fixed_prefix_for_all_mesures) > 0:
        output_zip_file_name = output_zip_file_name + "_" + param.metric_name_fixed_prefix_for_all_mesures
    
    output_zip_file_name = output_zip_file_name + "_ready_for_splunk." +  param.output_files_extension
    output_zip_file = zipfile.ZipFile(param.output_perflogs_ready_for_splunk_directory + "\\" + output_zip_file_name,'w')
    
    print_and_log_infoInThread(thread, "Convert all blg to csv")
    
    for blg_file in glob.glob(temp_folder_full_path_name + "\\*.blg"):
        handle_blg_file(thread, blg_file, temp_folder_full_path_name, output_zip_file)

    #Close the zip
    print_and_log_infoInThread(thread, "Zip DONE")
    output_zip_file.close()            
        
    print_and_log_infoInThread(thread, "Remove temporary directory:" + temp_folder_full_path_name)
    shutil.rmtree(temp_folder_full_path_name)


def process_data(thread):
    queueOfPerflogArchivesToTransform = thread.queueOfPerflogArchivesToTransform
    threadName = thread.name
    while not exitFlag:
        print_and_log_infoInThread(thread, "Acquire queueLock")
        queueLock.acquire()
        if not queueOfPerflogArchivesToTransform.empty():
            input_zip_file = queueOfPerflogArchivesToTransform.get()
            print_and_log_infoInThread(thread, "Release queueLock")
            queueLock.release()
            
            process_input_zip_file(thread, input_zip_file)
        else:
            print_and_log_infoInThread(thread, "Queue queueOfPerflogArchivesToTransform is empty")
            print_and_log_infoInThread(thread, "Release queueLock")
            queueLock.release()
            time.sleep(1)

def main(argv):

    configure_logger()    
    
    printAndLog('Start application')
    
    # Get the list of input zips
    printAndLog(param.input_files_directory)
    
    csse_covid_19_daily_reports = glob.glob(param.input_files_directory + "\\*.csv")
    
    logging.info('input_files:' + ",".join(csse_covid_19_daily_reports))
       
    process_csse_covid_19_daily_reports(csse_covid_19_daily_reports)

    printAndLog("Exiting main thread")

    
    printAndLog("End. Nominal end of application")
    

if __name__ == "__main__":
   main(sys.argv[1:])
