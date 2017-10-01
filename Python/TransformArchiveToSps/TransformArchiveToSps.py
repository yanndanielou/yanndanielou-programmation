# -*-coding:Utf-8 -*
#
#	This tools takes as input an extract of the ATS archive (in a specific format), and creates an SPS that sends to the server the same inputs that it received on site, at the same flow (same speed)
#
#
#	
#
#	Example: send to the server all incomming messages and TS that changed on site from 9 O'clock to 9:00 on te 20th of July on the PATH project
#	
#	SELECT
#	  [treatment_date]
#      , DATEPART(HOUR,treatment_date) * 60 * 60 * 1000 + DATEPART(MINUTE,treatment_date) * 60 * 1000 + DATEPART(SECOND,treatment_date) * 1000 + DATEPART(MILLISECOND,treatment_date) as ms_since_midnight
#      ,[id]
#	  , signal_type
#      ,[new_state]
#  FROM [PATHArchiveSite].[dbo].[message4]
#  where 
#  (id like 'M%CC[_]ATS%' or id like 'M%CC[_]EQPT%'   or id like 'M%EQPT[_]ATS%'  or id like 'M%EQPT[_]ATS%' or id like 'M%ZC[_]ATS%' or id like 'M%LC[_]ATS%'  or id like 'M[_]SSI%%' or id like 'S[_]%' or id like 'SM[_]%')
#  and signal_type not like 'log.alarm.type%'
#  and treatment_date between '2017-07-20 09:00:00.307' and '2017-07-20 09:10:00.307'
#  order by treatment_date
#

import logging

import os
import sys

import re

import getopt

import param
import Constants


def printAllAttributesAsDebug(Aattributes):
	for attribute in attributes:
	 	logging.debug(attribute)

def printAllAttributesAsError(attributes):
	for attribute in attributes:
	 	logging.error(attribute)

def printActionsDependingOnArguments_onlyForDebugPurpose(application_file_name, input_archive_extract_file_name ,  output_sps_file_name):		
	# Print all raw arguments
	logging.debug("Arguments:")
	logging.debug(" application_file_name:%s", application_file_name)
	logging.debug(" input_archive_extract_file_name:%s", input_archive_extract_file_name)
	logging.debug(" output_sps_file_name:%s", output_sps_file_name)
	
def configureLogger():
	logger_directory = "logs"
	
	if not os.path.exists(logger_directory):
		os.makedirs(logger_directory)
	
	logger_level = param.logger_level
	
	print("Logger level:" +str(logger_level))
	
	logging.basicConfig(level=logger_level,
						format='%(asctime)s %(levelname)-8s %(message)s',
						datefmt='%a, %d %b %Y %H:%M:%S',
						filename=logger_directory+'\TransformArchiveToSps.log',
						filemode='w')
	#logging.debug
	#logging.info
	#logging.warning
	#logging.error
	#logging.critical	

def main(argv):

	configureLogger()	
	
	logging.info('Start application')
			
	#arguments
	application_file_name 	= ""
	input_archive_extract_file_name 	= ""
	output_sps_file_name 	= ""	
	
	list_arguments_names = ["inputIlvFile=","outputIlvFile="]
	
	try:
		opts, args = getopt.getopt(argv,"hi:o:", list_arguments_names)
	except getopt.GetoptError as err:
		errorMessage = "Unsupported arguments list." + str(err) + " Allowed arguments:" + str(list_arguments_names) + ". Application stopped"
		logging.critical(errorMessage)
		print(errorMessage)
		sys.exit()
	for opt, arg in opts:
		if opt in ("-h", "--help"):
			message = "Allowed arguments:" + str(list_arguments_names) + ". Application stopped"
			logging.info(message)
			print(message)
			sys.exit()
		elif opt == "--inputIlvFile":
			input_archive_extract_file_name = arg
		elif opt == "--outputIlvFile":
			output_sps_file_name = arg
		else:
			errorMessage = " Option:" + opt + " unknown with value:" + opt + ". Allowed arguments:" + str(list_arguments_names) + ". Application stopped"
			logging.critical(errorMessage)
			print(errorMessage)
			sys.exit()
		

	printActionsDependingOnArguments_onlyForDebugPurpose(application_file_name, input_archive_extract_file_name ,  output_sps_file_name)
		
	# open input ilv file
	logging.info('Opening input file:' + input_archive_extract_file_name)

	if not os.path.exists(input_archive_extract_file_name):
		logging.critical("Input ilv file:" + input_archive_extract_file_name + " does not exist. Application stopped")
		sys.exit()

	input_archive_extract_file = open(input_archive_extract_file_name, "r")

	#
	logging.info('Read input file:' + input_archive_extract_file_name)
	input_archive_extract_file_content = input_archive_extract_file.read()
					
	
	# close input file
	logging.info('Close input file:' + input_archive_extract_file_name)
	input_archive_extract_file.close()

	#init
	line_number = 0
	output_sps_file_content = ""
	output_sps_file_content_as_list = list()
	all_objects_ids = set()
	previous_line_timestamp_as_ms_since_midnight = None
		
	pattern_as_string = "(?P<timestamp_as_datetime>\\d+[-]\\d+[-]\\d+[ ]\\d+[:]\\d+[:]\\d+[.]\\d+)[\s]*(?P<timestamp_as_ms_since_midnight>\\d+)[\s]*(?P<id>[A-Z][0-9A-Z_]*)[\s]*(?P<signal_type>[A-Z]*)[\s]*(?P<new_state>.*)"
	pattern = re.compile(pattern_as_string)
	
	tracking_message_pattern = re.compile(param.tracking_message_pattern_as_string)
	
	for line in input_archive_extract_file_content.split(Constants.end_line_character_in_text_file):

		line_number += 1

		match_line = pattern.match(line)
		if match_line == None:
			infoMessage = "Ignore line in input file:" + line
			logging.info(infoMessage)
			print(infoMessage)
			
		else:
			fields_in_line = line.split(Constants.field_separator_in_input_file)
						
			current_line_field_timestamp_as_datetime = match_line.group("timestamp_as_datetime")
			current_line_field_timestamp_as_ms_since_midnight = int(match_line.group("timestamp_as_ms_since_midnight"))
			current_line_field_id = match_line.group("id")
			current_line_field_signal_type = match_line.group("signal_type")
			current_line_field_new_state = match_line.group("new_state")
			
			output_sps_file_content_as_list.append(Constants.end_line_character_in_text_file)
			output_sps_file_content_as_list.append("// " + current_line_field_timestamp_as_datetime + Constants.end_line_character_in_text_file)
			
			current_line_input_to_sent_to_server = ""
			
			if previous_line_timestamp_as_ms_since_midnight != None:
				delay_in_ms_to_apply = current_line_field_timestamp_as_ms_since_midnight - previous_line_timestamp_as_ms_since_midnight
				
				if delay_in_ms_to_apply > 0:
					output_sps_file_content_as_list.append("delay(" + str(int(delay_in_ms_to_apply/1000)) + "." + str(delay_in_ms_to_apply %1000) + ");" + Constants.end_line_character_in_text_file)
			
			previous_line_timestamp_as_ms_since_midnight = current_line_field_timestamp_as_ms_since_midnight
			
			current_line_input_to_sent_to_server += current_line_field_id + "."
			
			if current_line_field_signal_type == Constants.signal_type_message:
				current_line_input_to_sent_to_server += "setContentsAsStringInHexadecimalFormat(\"" + current_line_field_new_state.rstrip() + "\");"
			elif current_line_field_signal_type == Constants.signal_type_TS or current_line_field_signal_type == Constants.signal_type_TSM:
				current_line_input_to_sent_to_server += "setState(\"" + current_line_field_new_state + "\");"
			elif current_line_field_signal_type == Constants.signal_type_TG:
				current_line_input_to_sent_to_server += "setValue(" + current_line_field_new_state + ");"
			else:
				errorMessage = "Unsupported signal type:" + current_line_field_signal_type + " in line:" + str(line_number) + "(" + line + ")" + ". Application stopped"
				logging.critical(errorMessage)
				print(errorMessage)
				sys.exit()					
							
			current_line_input_to_sent_to_server += Constants.end_line_character_in_text_file
			
			output_sps_file_content_as_list.append(current_line_input_to_sent_to_server)
			
			# Tracking messages can be sent multiple times (because not all occurences are archived by the server to reduce the archive size)
			# This isuseful to reproduce the server load
			match_id_with_tracking_message_pattern = tracking_message_pattern.match(current_line_field_id)			
			if match_id_with_tracking_message_pattern != None:
				for i in range (1, param.duplication_number_of_tracking_messages):
					output_sps_file_content_as_list.append("delay(0.01);" + Constants.end_line_character_in_text_file)
					output_sps_file_content_as_list.append(current_line_input_to_sent_to_server)

			all_objects_ids.add(current_line_field_id)
			
		if (line_number % 10_000) == 0:
			infoMessage = "Processing line number:" + str(line_number)
			logging.info(infoMessage)
			print(infoMessage)
	
	
	# First, add the method with the name of the script
	method_name = ""
	if output_sps_file_name.find(".") != -1:
		method_name = output_sps_file_name.split(".")[0]
	else:
		method_name = output_sps_file_name
		
	output_sps_file_content = "void " + method_name + "()" + Constants.end_line_character_in_text_file + "{" + Constants.end_line_character_in_text_file
		
	# Then, add the USE of each object
	for object_id in all_objects_ids:
		output_sps_file_content = output_sps_file_content + "use " + object_id + ";" + Constants.end_line_character_in_text_file
		
	output_sps_file_content = output_sps_file_content + Constants.end_line_character_in_text_file+ Constants.end_line_character_in_text_file
	
	output_sps_file_content = output_sps_file_content + "println(\"Begin of values update\");" + Constants.end_line_character_in_text_file
	
	logging.info('Converting output content to string')
	output_sps_file_content = output_sps_file_content + "".join(output_sps_file_content_as_list)

	
	output_sps_file_content = output_sps_file_content + "println(\"end of script\");" + Constants.end_line_character_in_text_file
	output_sps_file_content = output_sps_file_content + Constants.end_line_character_in_text_file + "}" + Constants.end_line_character_in_text_file
	
	logging.info('Create output file:' + output_sps_file_name)
	output_sps_file = open(output_sps_file_name, "w")
	logging.info('Fill output file:' + output_sps_file_name)
	output_sps_file.write(output_sps_file_content)
	logging.info('Close output file:' + output_sps_file_name)
	output_sps_file.close()

	logging.info("End. Nominal end of application")

if __name__ == "__main__":
   main(sys.argv[1:])