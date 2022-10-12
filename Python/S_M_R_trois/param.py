# -*-coding:Utf-8 -*

# 
from collections import namedtuple

import logging
logger_level = logging.INFO


field_separator_in_input_file = ","
end_line_character_in_text_file = "\n"

input_files_extension = "csv"
input_files_directory = "Input_data\\COVID-19-master\\csse_covid_19_data\\csse_covid_19_daily_reports"
#input_files_directory = "C:\\UserData\\fr232487\\Documents\\\GitHub\\yanndanielou-programmation\\Python\\COVID-19\\Input_data\\COVID-19-master\\csse_covid_19_data\\csse_covid_19_daily_reports"
output_files_extension = "zip"
output_perflogs_ready_for_splunk_directory = "D:\\temp\\PreparePerflogsForSplunk\\Output_Perflogs_Ready_For_Splunk"


# Regular expression pattern of machine names to keep
machines_to_keep_regex_pattern_as_string = ".*"
#machines_to_keep_regex_pattern_as_string = "(ATS_CEN1|ATS_CEN2)"

# Regular expression pattern of applications to keep
applications_to_keep_regex_pattern_as_string = ".*"
#applications_to_keep_regex_pattern_as_string = "(Process_ServerApplication|Process_AfAutomaticRouteSettingApplication2|Processor_Total|Memory|LogicalDisk_C|LogicalDisk_D)"

# Regular expression pattern of mesures to keep
mesure_type_to_keep_regex_pattern_as_string = ".*"
#mesure_type_to_keep_regex_pattern_as_string = "^((?!Handle_Count).*)"

# List of regular expressions 
# For each of them, the tools keeps an average of the value, on 
# If you don't want any filtering, keep the default value (empty list): this functionnality will be disabled
perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average = list()
perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average.append((".*Interface_reseau.*", 300))
perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average.append((".*espace_libre.*", 60))
perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average.append((".*Octets_disponibles.*", 60))
perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average.append((".*Octets_prives.*", 30))
perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average.append((".*Plage_de_travail.*", 30))
perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average.append((".*Nombre_de_handles.*", 30))
perflog_mesures_to_average_as_list_by_perflog_name_and_number_of_mesures_to_average.append((".*temps_processeur", 5))