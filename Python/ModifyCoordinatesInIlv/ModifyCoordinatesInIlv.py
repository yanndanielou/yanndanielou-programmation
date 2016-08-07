# -*-coding:Utf-8 -*

import logging

import os
import sys

import re

import getopt


def printAllAttributes(attributes):
	for attribute in attributes:
	 	logging.debug(attribute)

def getStringValue(attributeLine):
	return getAttributeValue(attributeLine, "[0-9A-Za-z_]*")
	
def getIntValue(attributeLine):
	return int(getAttributeValue(attributeLine, "\\d+"))

	
def getAttributeValue(attributeLine, valuePattern):
	pattern_as_string = ".*=[\s]*" + "(?P<value>" + valuePattern + ")" + "$"
	pattern = re.compile(pattern_as_string)
	match_attribute_searched = pattern.match(attributeLine)	
	return(match_attribute_searched.group('value'))	
	

def findNameAttributeLine(attributes):
	return findStringAttributeLine('name', attributes)

def findStringAttributeLine(attributeName, attributes):
	return findAttributeLine('String', attributeName, attributes, "[0-9A-Za-z_]*")

def findXAttributeLine(attributes):
	return findIntAttributeLine('x', attributes)
	
def findYAttributeLine(attributes):
	return findIntAttributeLine('y', attributes)

def findIntAttributeLine(attributeName, attributes):
	return findAttributeLine('Int', attributeName, attributes, "\\d+")

# 
def findAttributeLine(attributeType, attributeName, attributes, valuePattern):
	pattern_as_string = "[\s]*" + attributeType+"[\s]*"+ attributeName+ "[\s]*=[\s]*" + "(?P<value>" + valuePattern + ")" + "$"
	pattern = re.compile(pattern_as_string)

	for attribute in attributes:
		match_attribute_searched = pattern.match(attribute)
	
		if match_attribute_searched != None:
			return attribute

	logging.error('Could not find attribute with type:' + attributeType + " name:" + attributeName + " with pattern:" + pattern_as_string + " among attributes:")
	printAllAttributes(attributes)
	sys.exit()
	return ""

	
def printActionsDependingOnArguments_onlyForDebugPurpose(application_file_name, input_ilv_file_name ,  output_ilv_file_name ,   has_min_original_x 	, min_original_x ,  has_max_original_x 	, max_original_x, 
                                                         has_x_increment, x_increment , has_min_original_y 	, min_original_y , has_max_original_y , max_original_y, has_y_increment , y_increment ):		
	# Print all raw arguments
	logging.debug("Arguments:")
	logging.debug(" application_file_name:%s", application_file_name)
	logging.debug(" input_ilv_file_name:%s", input_ilv_file_name)
	logging.debug(" output_ilv_file_name:%s", output_ilv_file_name)
	logging.debug(" min_original_x:%d", min_original_x)
	logging.debug(" max_original_x:%d", max_original_x)
	logging.debug(" x_increment:%d", x_increment)
	logging.debug(" min_original_y:%d", min_original_y)
	logging.debug(" max_original_y:%d", max_original_y)
	logging.debug(" y_increment:%d", y_increment)
		
	# Print info about arguments (what this application will do and on which conditions)
	logging.debug("Actions:")
	
		
	if has_x_increment:
		x_coordinates_action_as_string = ""
		if x_increment > 0:
			x_coordinates_action_as_string = "  X coordinates must be incremented by " + str(x_increment)
		else:
			x_coordinates_action_as_string = "  X coordinates must be decremented by " + str(x_increment)
	
		if has_min_original_x and has_max_original_x:
			logging.info("%s only for objects with original X >= %d and original X =< %d", x_coordinates_action_as_string, min_original_x, max_original_x)
		elif has_min_original_x:
			logging.info("%s only for objects with original X => %d" , x_coordinates_action_as_string, min_original_x)
		elif has_max_original_x:
			logging.info("%s for objects with original X <= %d" , x_coordinates_action_as_string, max_original_x)
		else:
			logging.info(x_coordinates_action_as_string + " for all objects ")
	else:
		logging.info("  X coordinates must not be touched")
	

	if has_y_increment:
		y_coordinates_action_as_string = ""
		if y_increment > 0:
			y_coordinates_action_as_string = "  Y coordinates must be incremented by " + str(y_increment)
		else:
			y_coordinates_action_as_string = "  Y coordinates must be decremented by " + str(y_increment)
	
		if has_min_original_y and has_max_original_y:
			logging.info("%s only for objects with original Y >= %d and original Y =< %d" , y_coordinates_action_as_string,  min_original_y, max_original_y)
		elif has_min_original_y:
			logging.info("%s only for objects with original Y => %d" ,y_coordinates_action_as_string,  min_original_y)
		elif has_max_original_y:
			logging.info("%s for objects with original Y <= %d" , y_coordinates_action_as_string, max_original_y)
		else:
			logging.info(y_coordinates_action_as_string + " for all objects ")
	else:
		logging.info("  Y coordinates must not be touched")

	
def configureLogger():
	logger_directory = "logs"
	
	if not os.path.exists(logger_directory):
		os.makedirs(logger_directory)
	
	logging.basicConfig(level=logging.DEBUG,
						format='%(asctime)s %(levelname)-8s %(message)s',
						datefmt='%a, %d %b %Y %H:%M:%S',
						filename=logger_directory+'\ModifyCoordinatesInIlv.log',
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
	input_ilv_file_name 	= ""
	output_ilv_file_name 	= ""
	has_min_original_x 		= False
	min_original_x 			= 0
	has_max_original_x 		= False
	max_original_x			= 0
	has_x_increment 		= False
	x_increment 			= 0
	has_min_original_y 		= False
	min_original_y 			= 0
	has_max_original_y 		= False
	max_original_y			= 0
	has_y_increment 		= False
	y_increment 			= 0
	
	
	list_arguments_names = ["inputIlvFile=","outputIlvFile=","minOriginalXToChangeCoordinates=","maxOriginalXToChangeCoordinates=","xIncrement=","minOriginalYToChangeCoordinates=","maxOriginalYToChangeCoordinates=","yIncrement="]
	
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
			input_ilv_file_name = arg
		elif opt == "--outputIlvFile":
			output_ilv_file_name = arg
		elif opt == "--minOriginalXToChangeCoordinates":
			min_original_x_str = arg
			min_original_x = int(min_original_x_str)
			has_min_original_x = True
		elif opt == "--maxOriginalXToChangeCoordinates":
			max_original_x_str = arg
			max_original_x = int(max_original_x_str)
			has_max_original_x = True
		elif opt == "--xIncrement":
			x_increment_str = arg
			x_increment = int(x_increment_str)
			has_x_increment = True
		elif opt == "--minOriginalYToChangeCoordinates":
			min_original_y_str = arg
			min_original_y = int(min_original_y_str)
			has_min_original_y = True
		elif opt == "--maxOriginalYToChangeCoordinates":
			max_original_y_str = arg
			max_original_y = int(max_original_y_str)
			has_max_original_y = True
		elif opt == "--yIncrement":
			y_increment_str = arg
			y_increment = int(y_increment_str)
			has_y_increment = True
		else:
			errorMessage = " Option:" + opt + " unknown with value:" + opt + ". Allowed arguments:" + str(list_arguments_names) + ". Application stopped"
			logging.critical(errorMessage)
			print(errorMessage)
			sys.exit()
		

	printActionsDependingOnArguments_onlyForDebugPurpose(application_file_name, input_ilv_file_name ,  output_ilv_file_name ,   has_min_original_x 	, min_original_x ,  has_max_original_x 	, max_original_x, 
                                                         has_x_increment, x_increment , has_min_original_y 	, min_original_y , has_max_original_y , max_original_y, has_y_increment , y_increment )
		

	# open input ilv file
	logging.info('Opening input file:' + input_ilv_file_name)


	if not os.path.exists(input_ilv_file_name):
		logging.critical("Input ilv file:" + input_ilv_file_name + " does not exist. Application stopped")
		sys.exit()

	input_ilv_file = open(input_ilv_file_name, "r")

	#
	input_ilv_file_content = input_ilv_file.read()

	replacement_string_for_new_line_caracter = 'YANNNNNNNNNNNNNNNNNN'

	# Temporary remove all new lines caracters (replace by temporary string) to be able to use regular expressions
	input_ilv_file_content_in_one_line = input_ilv_file_content.replace('\n', replacement_string_for_new_line_caracter)

	logging.debug("Size of input file: %d" , len(input_ilv_file_content_in_one_line))

	# Retrieve all values { } in the file
	all_values = re.findall(r'values {[^}]*}', input_ilv_file_content_in_one_line)

	logging.info("Number of values{} found: %d" , len(all_values))

	value_number = 0
	for value_section in all_values:
		value_number = value_number + 1
		logging.debug("Value %d", value_number)
		value_attributes = value_section.split(replacement_string_for_new_line_caracter)
		name_attribute_line = findNameAttributeLine(value_attributes)
		name = getStringValue(name_attribute_line)
		x_attribute_line = findXAttributeLine(value_attributes)
		x = getIntValue(x_attribute_line)
		y_attribute_line = findYAttributeLine(value_attributes)
		y = getIntValue(y_attribute_line)
		
		logging.debug("    name:" + name)
		logging.debug("    x:" + str(x))
		logging.debug("    y:" + str(y))
		
		# check if coordinates must be updated
		update_x_coordinate = False
		new_x_coordinate = None
		update_y_coordinate = False
		new_y_coordinate = None
		
		if has_x_increment:
			x_is_inside_range = True
			if has_min_original_x:
				x_is_inside_range = x_is_inside_range and (x >= min_original_x)
			if has_max_original_x:
				x_is_inside_range = x_is_inside_range and (x <= max_original_x)
			
			if x_is_inside_range:
				update_x_coordinate = True
				new_x_coordinate = x + x_increment
				logging.debug("    x attribute:" + str(x) + " of object:" + name + " must be updated to:" + str(new_x_coordinate))
				
		if has_y_increment:
			y_is_inside_range = True
			if has_min_original_y:
				y_is_inside_range = y_is_inside_range and (y >= min_original_y)
			if has_max_original_y:
				y_is_inside_range = y_is_inside_range and (y <= max_original_y)
			
			if y_is_inside_range:
				update_y_coordinate = True
				new_y_coordinate = y + y_increment
				logging.debug("    y attribute:" + str(y) + " of object:" + name + " must be updated to:" + str(new_y_coordinate))
			
		
		if has_x_increment or has_y_increment:
			value_section_updated = value_section
		

	# close ilv file
	logging.info('Closing input file:' + input_ilv_file_name)
	input_ilv_file.close()


if __name__ == "__main__":
   main(sys.argv[1:])