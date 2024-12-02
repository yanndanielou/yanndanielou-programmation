# -*-coding:Utf-8 -*
#
#	This tools takes as input an extract of the ATS archive (in a specific format), and creates
#

import logging

import os
import sys

import re

import getopt

import param
import Constants


def recordDataForAverageOfADay(list_of_trains_with_secured_consist_by_period_in_current_day, list_of_trains_with_not_secured_consist_by_period_in_current_day, previous_line_date, output_file_average_by_day_content_as_list):

	average_number_trains_with_secured_consist_in_current_day = 0
	for i in range(0, len(list_of_trains_with_secured_consist_by_period_in_current_day) -1 ):
		average_number_trains_with_secured_consist_in_current_day += len(list_of_trains_with_secured_consist_by_period_in_current_day[i])
	average_number_trains_with_secured_consist_in_current_day = round(average_number_trains_with_secured_consist_in_current_day / len(list_of_trains_with_secured_consist_by_period_in_current_day))
	
	infoMessage = "list_of_trains_with_secured_consist_by_period_in_current_day" + str(list_of_trains_with_secured_consist_by_period_in_current_day) + " gives an average of:" + str(average_number_trains_with_secured_consist_in_current_day)
	logging.info(infoMessage)
	print(infoMessage)
	
	
	average_number_trains_with_not_secured_consist_in_current_day = 0
	for i in range(0, len(list_of_trains_with_not_secured_consist_by_period_in_current_day) -1 ):
		average_number_trains_with_not_secured_consist_in_current_day += len(list_of_trains_with_not_secured_consist_by_period_in_current_day[i])
	average_number_trains_with_not_secured_consist_in_current_day = round(average_number_trains_with_not_secured_consist_in_current_day / len(list_of_trains_with_not_secured_consist_by_period_in_current_day))
	
	infoMessage = "list_of_trains_with_not_secured_consist_by_period_in_current_day" + str(list_of_trains_with_not_secured_consist_by_period_in_current_day) + " gives an average of:" + str(average_number_trains_with_not_secured_consist_in_current_day)
	logging.info(infoMessage)
	print(infoMessage)
	
	infoMessage = "In day " + previous_line_date  + ", in average trains with secured consist:" + str(average_number_trains_with_secured_consist_in_current_day) + ", in average trains with not secured consist:" + str(average_number_trains_with_not_secured_consist_in_current_day)
	logging.info(infoMessage)
	print(infoMessage)
	
	output_file_average_by_day_content_as_list.append(previous_line_date + "\t" + str(average_number_trains_with_secured_consist_in_current_day) + "\t" + str(average_number_trains_with_not_secured_consist_in_current_day) + Constants.end_line_character_in_text_file)
	


def printAllAttributesAsDebug(Aattributes):
	for attribute in attributes:
	 	logging.debug(attribute)

def printAllAttributesAsError(attributes):
	for attribute in attributes:
	 	logging.error(attribute)

def printActionsDependingOnArguments_onlyForDebugPurpose(input_archive_extract_file_name ,  output_files_prefix):		
	# Print all raw arguments
	logging.debug("Arguments:")
	logging.debug(" input_archive_extract_file_name:%s", input_archive_extract_file_name)
	logging.debug(" output_files_prefix:%s", output_files_prefix)
	
def configure_logger():
	logger_directory = "logs"
	
	if not os.path.exists(logger_directory):
		os.makedirs(logger_directory)
	
	logger_level = param.logger_level
	
	print("Logger level:" +str(logger_level))
	
	logging.basicConfig(level=logger_level,
						format='%(asctime)s %(levelname)-8s %(message)s',
						datefmt='%a, %d %b %Y %H:%M:%S',
						filename=logger_directory+'\calculate_number_of_secured_consists_from_tracking_messages.log',
						filemode='w')
	#logging.debug
	#logging.info
	#logging.warning
	#logging.error
	#logging.critical	

def main(argv):

	configure_logger()	
	
	logging.info('Start application')
			
	#arguments
	input_archive_extract_file_name 	= ""
	output_files_prefix 	= ""	
	
	list_arguments_names = ["input_archive_extract_file_name=","output_files_prefix="]
	
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
		elif opt == "--input_archive_extract_file_name":
			input_archive_extract_file_name = arg
		elif opt == "--output_files_prefix":
			output_files_prefix = arg
		else:
			errorMessage = " Option:" + opt + " unknown with value:" + opt + ". Allowed arguments:" + str(list_arguments_names) + ". Application stopped"
			logging.critical(errorMessage)
			print(errorMessage)
			sys.exit()
		

	printActionsDependingOnArguments_onlyForDebugPurpose(input_archive_extract_file_name ,  output_files_prefix)
		
	# open input file
	logging.info('Opening input file:' + input_archive_extract_file_name)

	if not os.path.exists(input_archive_extract_file_name):
		logging.critical("Input file:" + input_archive_extract_file_name + " does not exist. Application stopped")
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
	output_file_by_period_content = ""
	output_file_by_period_content_as_list = list()
	output_file_by_day_content_as_list = list()
	output_file_average_by_day_content_as_list = list()
	
	previous_line_timestamp_date = ""
	previous_line_timestamp_hour_and_minute = ""
	
	# Output files headers
	output_file_by_period_content_as_list.append("Date" + "\t" + "Number of trains with secured consist" + "\t" + "Trains with secured consist" + "\t" + "Number of trains with not secured consist" + "\t" + "Trains with not secured consist"+ Constants.end_line_character_in_text_file)
	output_file_by_day_content_as_list.append("Date" + "\t" + "Number of trains with secured consist" + "\t" + "Trains with secured consist" + "\t" + "Number of trains with not secured consist" + "\t" + "Trains with not secured consist"+ Constants.end_line_character_in_text_file)
	output_file_average_by_day_content_as_list.append("Date" + "\t" + "Average number of trains with secured consist" + "\t" +  "Average number of trains with not secured consist" + Constants.end_line_character_in_text_file)

	
	
	# 1	09/23/2017	05:00:02	TSA 	M_TRAIN_CC_5670_CC_EQPT_TRACKING 	TRAIN CC ID 5670 	INDETERMINATE 	TRAIN : CC_ATS_TRACKING [20] 	log.succ 	 	40852-9030 	16 26 80 0 4 0 1 2 29 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 20 0 18 80 0 18 68 11 A9 80 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 4 F1 BA 0 A FC 80 1B 6  	20	CCId1  =  5670	CCId1PilotStatus  =  1	CCId1PositionStatus  =  Front_Position (0)	CCId2  =  0	CCId2PilotStatus  =  0	CCId2PositionStatus  =  Intermediate_Position (1)	CCId3  =  0	CCId3PilotStatus  =  0	CCId3PositionStatus  =  Back_Position (2)	TrainBerthed  =  0	PlatformId  =  1106	LocSecured  =  0	ExtFrontSegId  =  0	ExtFrontOffset  =  0	IntFrontSegId  =  0	IntFrontOffset  =  0	FrontRunDirection  =  NoDirection_RunDirection (0)	ExtRearSegId  =  0	ExtRearOffset  =  0	IntRearSegId  =  0	IntRearOffset  =  0	RearRunDirection  =  NoDirection_RunDirection (0)	LocationUncertainty  =  0	SpeedTracking  =  0	TrainAtStop  =  1	UnderCbtcControl  =  0	CbtcTrainConsist  =  Unknown_UnitTrain (0)	SecuredTrainConsist  =  0	UnitExtremityType[0]  =  0	UnitExtremityType[1]  =  0	UnitExtremityType[2]  =  0	UnitExtremityType[3]  =  0	UnitExtremityType[4]  =  0	UnitExtremityType[5]  =  0	TrainEvacNoSupervisionReq  =  1	TrainCollisionNoSupervisionReq  =  1	TrainEvacAlarm  =  0	MvtDirection  =  Undetermined_MvtDirection (0)	TrainProbablyAtStop  =  1	MajorRsAlarm  =  00000	TrainLength  =  1562	RefPtPolarity  =  Minus_RefPtPolarity (0)	RefPtSegId  =  9043	RefPtDbVersion  =  0	CCIdPolarity  =  Minus_RefPtPolarity (0)	CCId1RPHMinStep[0]  =  0	CCId1RPHMinStep[1]  =  0	CCId1RPHMaxStep[0]  =  0	CCId1RPHMaxStep[1]  =  0	CCId3RPHMinStep[0]  =  0	CCId3RPHMinStep[1]  =  0	CCId3RPHMaxStep[0]  =  0	CCId3RPHMaxStep[1]  =  0	ZcMalRequest  =  0	CCId1RefPtSegId  =  0	CCId1NvRefPtOffset  =  0	CCId1RphNvStep[0]  =  0	CCId1RphNvStep[1]  =  0	CCId3RefPtSegId  =  0	CCId3NvRefPtOffset  =  0	CCId3RphNvStep[0]  =  0	CCId3RphNvStep[1]  =  0	NvFrontSegId  =  0	NvFrontOffset  =  0	FrontTravelDirectionNv  =  NoDirection_RunDirection (0)	NvRearSegId  =  0	NvRearOffset  =  0	TrainPilotPolarity  =  Minus_RefPtPolarity (0)	EmStaTrainBerthed  =  0	EmStaId  =  0	Padding_1  =  00000	Time  =  324026	TimeOffset  =  720000	Decade  =  1	DayOnDecade  =  2822
	pattern_as_string = "(?P<RecNo>\\d+)[\s]*(?P<date>\\d+[/]\\d+[/]\\d+)[\s]*(?P<hour_minute>\\d+[:]\\d+)[:](?P<second>\\d+)[\s]*(TSA)[\s]*(?P<message_id>[M][0-9A-Z_]*).*(CCId1)[\s]*(=)[\s]*(?P<ccId1>\\d+).*(CCId3)[\s]*(=)[\s]*(?P<ccId3>\\d+).*.*(SecuredTrainConsist)[\s]*(=)[\s]*(?P<SecuredTrainConsist>[01]).*"
	
	pattern = re.compile(pattern_as_string)
	
	tracking_message_pattern = re.compile(param.tracking_message_pattern_as_string)
	
	previous_line_date = ""
	previous_line_hour_minutes = ""	
	

	list_of_trains_with_secured_consist_in_current_period = set()
	list_of_trains_with_not_secured_consist_in_current_period = set()
	
	list_of_trains_with_secured_consist_in_current_day = set()
	list_of_trains_with_not_secured_consist_in_current_day = set()

	list_of_trains_with_secured_consist_by_period_in_current_day = list()
	list_of_trains_with_not_secured_consist_by_period_in_current_day = list()
	
	for line in input_archive_extract_file_content.split(Constants.end_line_character_in_text_file):

		line_number += 1

		match_line = pattern.match(line)
		if match_line == None:
			infoMessage = "Ignore line in input file:" + line
			logging.info(infoMessage)
			print(infoMessage)
			
		else:
						
			current_line_date = match_line.group("date")
			current_line_hour_minutes = match_line.group("hour_minute")
			#current_line_minutes = int(match_line.group("minutes"))
			current_line_second = int(match_line.group("second"))
			current_line_message_id =  match_line.group("message_id")
			current_line_ccId1 =  int(match_line.group("ccId1"))
			current_line_ccId3 =  int(match_line.group("ccId3"))
			current_line_securedTrainConsist =  int(match_line.group("SecuredTrainConsist"))
			
			debugMessage = "date:"  + current_line_date + ", time:" + current_line_hour_minutes  + ", message_id:" + current_line_message_id + ", ccId1:" + str(current_line_ccId1) + ", ccId3:" + str(current_line_ccId3) + ", securedTrainConsist:" + str(current_line_securedTrainConsist)
			logging.debug(debugMessage)
			#print(debugMessage)
			
			if current_line_message_id != "M_TRAIN_CC_" + str(current_line_ccId1) + "_CC_EQPT_TRACKING":
				errorMessage = "Inconsistency between ccid1 and cbtc header in " + line
				logging.error(errorMessage)
				print(errorMessage)
			elif current_line_securedTrainConsist == 1 and current_line_ccId3 > 0:
				list_of_trains_with_secured_consist_in_current_period.add(current_line_ccId1)
				list_of_trains_with_secured_consist_in_current_day.add(current_line_ccId1)
			else:
				list_of_trains_with_not_secured_consist_in_current_period.add(current_line_ccId1)
				list_of_trains_with_not_secured_consist_in_current_day.add(current_line_ccId1)
			
			
			if (previous_line_date != current_line_date or previous_line_hour_minutes != current_line_hour_minutes):
				
				if(previous_line_date != current_line_date):
				
					infoMessage = "New day:"  + current_line_date
					logging.info(infoMessage)
					print(infoMessage)
				
					if line_number > 2:
						infoMessage = "In day " + current_line_date  + ", trains with secured consist:" + str(sorted(list_of_trains_with_secured_consist_in_current_day)) + ", trains with not secured consist:" + str(sorted(list_of_trains_with_not_secured_consist_in_current_day))
						logging.info(infoMessage)
						print(infoMessage)
				
						output_file_by_day_content_as_list.append(previous_line_date  + "\t" + str(len(list_of_trains_with_secured_consist_in_current_day)) + "\t" + str(sorted(list_of_trains_with_secured_consist_in_current_day)) + "\t" + str(len(list_of_trains_with_not_secured_consist_in_current_day)) + "\t" + str(sorted(list_of_trains_with_not_secured_consist_in_current_day)) + Constants.end_line_character_in_text_file)
						
						recordDataForAverageOfADay(list_of_trains_with_secured_consist_by_period_in_current_day, list_of_trains_with_not_secured_consist_by_period_in_current_day, previous_line_date, output_file_average_by_day_content_as_list)

						
					list_of_trains_with_secured_consist_in_current_day.clear()
					list_of_trains_with_not_secured_consist_in_current_day.clear()
					
					list_of_trains_with_secured_consist_by_period_in_current_day.clear()
					list_of_trains_with_not_secured_consist_by_period_in_current_day.clear()
			
				
				infoMessage = "New period:"  + current_line_date + " " + current_line_hour_minutes
				logging.info(infoMessage)
				print(infoMessage)
				
				if line_number > 2:
					infoMessage = "In period " + previous_line_date + " " + previous_line_hour_minutes + ", trains with secured consist:" + str(sorted(list_of_trains_with_secured_consist_in_current_period)) + ", trains with not secured consist:" + str(sorted(list_of_trains_with_not_secured_consist_in_current_period))
					logging.info(infoMessage)
					print(infoMessage)
									
					output_file_by_period_content_as_list.append(previous_line_date + " " + previous_line_hour_minutes  + "\t" + str(len(list_of_trains_with_secured_consist_in_current_period)) + "\t" + str(sorted(list_of_trains_with_secured_consist_in_current_period)) + "\t" + str(len(list_of_trains_with_not_secured_consist_in_current_period)) + "\t" + str(sorted(list_of_trains_with_not_secured_consist_in_current_period))+ Constants.end_line_character_in_text_file)

					list_of_trains_with_secured_consist_by_period_in_current_day.append(list(list_of_trains_with_secured_consist_in_current_period))
					list_of_trains_with_not_secured_consist_by_period_in_current_day.append(list(list_of_trains_with_not_secured_consist_in_current_period))
					
				previous_line_date = current_line_date
				previous_line_hour_minutes = current_line_hour_minutes
				
				list_of_trains_with_secured_consist_in_current_period.clear()
				list_of_trains_with_not_secured_consist_in_current_period.clear()
						

	infoMessage = "In day " + current_line_date  + ", trains with secured consist:" + str(sorted(list_of_trains_with_secured_consist_in_current_day)) + ", trains with not secured consist:" + str(sorted(list_of_trains_with_not_secured_consist_in_current_day))
	logging.info(infoMessage)
	print(infoMessage)
	output_file_by_day_content_as_list.append(current_line_date  + "\t" + str(len(list_of_trains_with_secured_consist_in_current_day)) + "\t" + str(sorted(list_of_trains_with_secured_consist_in_current_day)) + "\t" + str(len(list_of_trains_with_not_secured_consist_in_current_day)) + "\t" + str(sorted(list_of_trains_with_not_secured_consist_in_current_day)) + Constants.end_line_character_in_text_file)

	
	infoMessage = "In period " + current_line_date + " " + current_line_hour_minutes + ", trains with secured consist:" + str(sorted(list_of_trains_with_secured_consist_in_current_period)) + ", trains with not secured consist:" + str(sorted(list_of_trains_with_not_secured_consist_in_current_period))
	logging.info(infoMessage)
	print(infoMessage)		
	output_file_by_period_content_as_list.append(current_line_date + " " + current_line_hour_minutes  + "\t" + str(len(list_of_trains_with_secured_consist_in_current_period)) + "\t" + str(sorted(list_of_trains_with_secured_consist_in_current_period)) + "\t" + str(len(list_of_trains_with_not_secured_consist_in_current_period)) + "\t" + str(sorted(list_of_trains_with_not_secured_consist_in_current_period))+ Constants.end_line_character_in_text_file)

	
	recordDataForAverageOfADay(list_of_trains_with_secured_consist_by_period_in_current_day, list_of_trains_with_not_secured_consist_by_period_in_current_day, current_line_date, output_file_average_by_day_content_as_list)

	
	output_file_by_period_name = output_files_prefix + "_by_periods"
	
	logging.info('Create output file:' + output_file_by_period_name)
	output_file_by_period = open(output_file_by_period_name, "w")
	logging.info('Fill output file:' + output_file_by_period_name)

	output_file_by_period_content = "".join(output_file_by_period_content_as_list)
	output_file_by_period.write(output_file_by_period_content)
	logging.info('Close output file:' + output_file_by_period_name)
	output_file_by_period.close()
	
	
	
	output_file_by_day_name = output_files_prefix + "_by_day"
	
	logging.info('Create output file:' + output_file_by_day_name)
	output_file_by_day = open(output_file_by_day_name, "w")
	logging.info('Fill output file:' + output_file_by_day_name)

	output_file_by_day_content = "".join(output_file_by_day_content_as_list)
	output_file_by_day.write(output_file_by_day_content)
	logging.info('Close output file:' + output_file_by_day_name)
	output_file_by_day.close()
	
	
	
	output_file_average_by_day_name = output_files_prefix + "_average_by_day"
	
	logging.info('Create output file:' + output_file_average_by_day_name)
	output_file_average_by_day = open(output_file_average_by_day_name, "w")
	logging.info('Fill output file:' + output_file_average_by_day_name)

	output_file_average_by_day_content = "".join(output_file_average_by_day_content_as_list)
	output_file_average_by_day.write(output_file_average_by_day_content)
	logging.info('Close output file:' + output_file_average_by_day_name)
	output_file_average_by_day.close()
	
	

	logging.info("End. Nominal end of application")

if __name__ == "__main__":
   main(sys.argv[1:])
