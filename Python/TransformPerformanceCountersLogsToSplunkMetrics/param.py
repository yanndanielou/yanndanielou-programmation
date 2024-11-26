# -*-coding:Utf-8 -*

# 
from collections import namedtuple

import logging
logger_level = logging.INFO


end_line_character_in_text_file = "\n"

#directory containing performance counters log fils
input_files_directory = "D:\\GitHub\\yanndanielou-programmation\\Python\\TransformPerformanceCountersLogsToSplunkMetrics\\Input"
#input_files_directory = dict({'folder': 'C:\\Users\\fr232487\\Downloads\\L1_14_07_24_Logs_V2\\L1_14_07_24_Logs\\LOGGER_BACKUP_ATS_CEN1_2024_07_14' ,'filesNamesMasks': 'RIYL1SpServerCountersRecorder1_*_actual.zip'})


#Splunk does not support the .7z format for data to index.
#Metrics files must be archived in a zip file
output_files_extension = "zip"

#Directory containing output metrics archives (to index into Splunk)
output_metrics_ready_for_splunk_directory = "D:\\GitHub\\yanndanielou-programmation\\Python\\TransformPerformanceCountersLogsToSplunkMetrics\\Output_Metrics_Ready_For_Splunk"



#Add possibility to add a fixed prefix of the metric_name in the output file.
#It can be used to index into the same Splunk index perflogs from different sources
#For example in Riyadh, we will index perflogs from: the site, the TCM factory, Wildenrath test track, etc. to the same index (to avoid additional work in Splunk configuration)
#Each source will have a distinct prefix so we can identify for each mesure its source
#In the output metrics file will look like:
# if metric_name_fixed_prefix_for_all_mesures is left empty			: machine_name.application_name.mesure_type
# if metric_name_fixed_prefix_for_all_mesures is set to <value>		: <value>.machine_name.application_name.mesure_type
#Examples of use:
# Example for Riyadh L2 TCM factory
#   metric_name_fixed_prefix_for_all_mesures = "TCM_L2"
# Example for Riyadh Wildenrath
#   metric_name_fixed_prefix_for_all_mesures = "Wildenrath"
metric_name_fixed_prefix_for_all_mesures = ""


# Suffix of .csv (metrics) and .zip files
# It can be useful when we want to generate a second time the metrics (with different arguments) for further anlysis on Splunk
# This way files don't have the same name and indexation to Splunk is easy (and we don't loose the other ones)
output_files_suffix = ""

input_performance_counter_file_extension = ".csv"

output_splunk_metrics_file_extension = ".csv"


input_performance_counter_file_seperator = ","
