# -*-coding:Utf-8 -*

# This tools does some simple text edition of an ilv file.
#
# It reads the input file inputIlvFile and generates as output the file outputIlvFile
#
# It update the coordinates (x and y attributes) of objects, based on initial object coordonates
# To be updated, the object must be located:
# 	in X: between minOriginalXToChangeCoordinates (if defined) and maxOriginalXToChangeCoordinates (if defined) 
# 	in Y: between minOriginalYToChangeCoordinates (if defined) and maxOriginalYToChangeCoordinates (if defined) 
#
# If the object is located in those ranges, it is updated as follow:
# -  X value is incremented by xIncrement (if defined)
# -  Y value is incremented by yIncrement (if defined)


import logging

import os
import sys

import re

import getopt


def printAllAttributes(attributes):
	for attribute in attributes:
	 	logging.debug(attribute)

def getStringValue(attributeLine):
	return getAttributeValue(attributeLine, "[0-9A-Za-z_\"]*")
	
def getIntValue(attributeLine):
	return int(getAttributeValue(attributeLine, "\\d+"))

def updateIntValue(originalAttributeLine, oldValue, newValue):
	return updateValue(originalAttributeLine, str(oldValue), str(newValue))
	
def updateValue(originalAttributeLine, oldValue, newValue):
	return originalAttributeLine.replace(oldValue, newValue)
	
def getAttributeValue(attributeLine, valuePattern):
	pattern_as_string = ".*=[\s]*" + "(?P<value>" + valuePattern + ")" + "$"
	pattern = re.compile(pattern_as_string)
	match_attribute_searched = pattern.match(attributeLine)	
	return(match_attribute_searched.group('value'))	
	
def findAfGadgetXOrigineAttributeName(attributes):
	pattern_as_string = "[\s]*" + "Int" +"[\s]*(?P<attributeName>[0-9A-Za-z_\"]*)\.xOrigin[\s]*=.*"
	pattern = re.compile(pattern_as_string)

	for attribute in attributes:
		match_attribute_searched = pattern.match(attribute)
	
		if match_attribute_searched != None:
			logging.debug("xOrigin found in:" + attribute)
			return match_attribute_searched.group('attributeName')

	return None	
	
def findAfGadgetXOrigineAttributeLine(afGadgetXOrigine_class_name, attributes):
	return findIntAttributeLine(afGadgetXOrigine_class_name+ ".xOrigin", attributes)
	
def findAfGadgetYOrigineAttributeLine(afGadgetXOrigine_class_name, attributes):
	return findIntAttributeLine(afGadgetXOrigine_class_name+ ".yOrigin", attributes)

def findNameAttributeLine(attributes):
	return findStringAttributeLine('name', attributes)

def findStringAttributeLine(attributeName, attributes):
	return findAttributeLine('String', attributeName, attributes, "[0-9A-Za-z_\"]*")

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
	
	
def isObjectInsideRange(original_x, original_y, has_min_original_x, min_original_x, has_max_original_x, max_original_x, has_min_original_y, min_original_y, has_max_original_y, max_original_y):
	x_is_inside_range = True
	if has_min_original_x:
		x_is_inside_range = x_is_inside_range and (original_x >= min_original_x)
	if has_max_original_x:
		x_is_inside_range = x_is_inside_range and (original_x <= max_original_x)
	
	y_is_inside_range = True
	if has_min_original_y:
		y_is_inside_range = y_is_inside_range and (original_y >= min_original_y)
	if has_max_original_y:
		y_is_inside_range = y_is_inside_range and (original_y <= max_original_y)
	
	is_inside_range = x_is_inside_range and y_is_inside_range
	return is_inside_range


# Update values {attributes...} sections in ILV file 
# Each section has at least "x", "y" and "name" attributes
#
#Example:
#     values {
#        String length = 230
#        String question = spocc.workstation.command_confirmation_message
#        String arg1 = "RESET ALL ORIGIN COUNTERS"
#        String arg2 = ATS
#        String label = "&Reset All Origin Counters"
#        Int x = 368
#        Int y = 3
#        Double scaleX = 4.6
#        String name = SQ_ATS_SWITCH_RESET_ORIGIN_MOVEMENTS_COUNTERS
#    }
def updateValuesSections(output_ilv_file_content, has_min_original_x, min_original_x, has_max_original_x, max_original_x, has_x_increment, x_increment, has_min_original_y, min_original_y, has_max_original_y, max_original_y, has_y_increment, y_increment):
	replacement_string_for_new_line_caracter = 'YANNNNNNNNNNNNNNNNNN'

	# Temporary remove all new lines caracters (replace by temporary string) to be able to use regular expressions
	output_ilv_file_content_in_one_line = output_ilv_file_content.replace('\n', replacement_string_for_new_line_caracter)
	
	# Retrieve all values { } in the file
	all_values = re.findall(r'values {[^}]*}', output_ilv_file_content_in_one_line)

	logging.info("Number of values{} found: %d" , len(all_values))

	value_number = 0
	for value_section in all_values:
		value_number = value_number + 1
		logging.debug("Value %d", value_number)
		
		value_attributes = value_section.split(replacement_string_for_new_line_caracter)
		
		name_attribute_line = findNameAttributeLine(value_attributes)
		name = getStringValue(name_attribute_line)
		x_attribute_line = findXAttributeLine(value_attributes)
		original_x = getIntValue(x_attribute_line)
		y_attribute_line = findYAttributeLine(value_attributes)
		original_y = getIntValue(y_attribute_line)
		afGadgetXOrigine_class_name = findAfGadgetXOrigineAttributeName(value_attributes)
		afGadgetXOrigine_attribute_line = ""
		afGadgetYOrigine_attribute_line = ""
		original_afGadgetXOrigine = 0
		original_afGadgetYOrigine = 0
		if afGadgetXOrigine_class_name != None:
			logging.debug("afGadgetXOrigine_class_name:" + name)
			afGadgetXOrigine_attribute_line = findAfGadgetXOrigineAttributeLine(afGadgetXOrigine_class_name, value_attributes)
			afGadgetYOrigine_attribute_line = findAfGadgetYOrigineAttributeLine(afGadgetXOrigine_class_name, value_attributes)
			original_afGadgetXOrigine = getIntValue(afGadgetXOrigine_attribute_line)
			original_afGadgetYOrigine = getIntValue(afGadgetYOrigine_attribute_line)
		
		logging.debug("    name:" + name)
		logging.debug("    original_x:" + str(original_x))
		logging.debug("    original_y:" + str(original_y))
		
		is_inside_range = isObjectInsideRange(original_x, original_y, has_min_original_x, min_original_x, has_max_original_x, max_original_x, has_min_original_y, min_original_y, has_max_original_y, max_original_y)

		if is_inside_range:
			if has_x_increment or has_y_increment:
				value_section_updated = value_section
				
				if has_x_increment:
					new_x = original_x + x_increment
					logging.debug("    original_x attribute:" + str(original_x) + " of object:" + name + " must be updated to:" + str(new_x))
					new_x_attribute_line = updateIntValue(x_attribute_line,original_x,  new_x)
					logging.debug("x attribute line was:" + x_attribute_line + " and beomes:" + new_x_attribute_line)
					value_section_updated = value_section_updated.replace(x_attribute_line, new_x_attribute_line)
					
					if afGadgetXOrigine_class_name != None:
						new_afGadgetXOrigine = original_afGadgetXOrigine + x_increment
						logging.debug("    original_afGadgetXOrigine attribute:" + str(original_afGadgetXOrigine) + " of object:" + name + " must be updated to:" + str(new_afGadgetXOrigine))
						new_afGadgetXOrigine_attribute_line = updateIntValue(afGadgetXOrigine_attribute_line,original_afGadgetXOrigine,  new_afGadgetXOrigine)
						logging.debug("x attribute line was:" + afGadgetXOrigine_attribute_line + " and beomes:" + new_afGadgetXOrigine_attribute_line)
						value_section_updated = value_section_updated.replace(afGadgetXOrigine_attribute_line, new_afGadgetXOrigine_attribute_line)
				
				if has_y_increment:
					new_y = original_y + y_increment
					logging.debug("    original_y attribute:" + str(original_y) + " of object:" + name + " must be updated to:" + str(new_y))
					new_y_attribute_line = updateIntValue(y_attribute_line,original_y,  new_y)
					logging.debug("y attribute line was:" + y_attribute_line + " and beomes:" + new_y_attribute_line)
					value_section_updated = value_section_updated.replace(y_attribute_line, new_y_attribute_line)
					
					if afGadgetXOrigine_class_name != None:
						new_afGadgetYOrigine = original_afGadgetYOrigine + y_increment
						logging.debug("    original_afGadgetYOrigine attribute:" + str(original_afGadgetYOrigine) + " of object:" + name + " must be updated to:" + str(new_afGadgetYOrigine))
						new_afGadgetYOrigine_attribute_line = updateIntValue(afGadgetYOrigine_attribute_line,original_afGadgetYOrigine,  new_afGadgetYOrigine)
						logging.debug("y attribute line was:" + afGadgetYOrigine_attribute_line + " and beomes:" + new_afGadgetYOrigine_attribute_line)
						value_section_updated = value_section_updated.replace(afGadgetYOrigine_attribute_line, new_afGadgetYOrigine_attribute_line)
			
				logging.debug("Values section was:" + value_section + " and beomes:" + value_section_updated)
				
				output_ilv_file_content_in_one_line = output_ilv_file_content_in_one_line.replace(value_section, value_section_updated)
				
	
	# Reput initial new lines caracters that were temporary replaced by temporary string
	return output_ilv_file_content_in_one_line.replace(replacement_string_for_new_line_caracter, '\n')

		
# x is always in the third position
# y is always in the fourth position
# Example
# 7 IlvMessageLabel 4602 1311 96 68 F268435468 0 { IlvMessageItem 262145 4 16 4 "KEARNY\nPOCKET"  }   } 0
# 83 AfGadgetTrain1Path 11466 2005 34 36 F268435456 2 1 6 1 1 0 2 0 -1 7 -1 "" "XX" "" 0 0 1 -1 0 } 32 "TB_3T_WT_01"
def updateSimpleObjectDefinitionLines(output_ilv_file_content, has_min_original_x, min_original_x, has_max_original_x, max_original_x, has_x_increment, x_increment, has_min_original_y, min_original_y, has_max_original_y, max_original_y, has_y_increment, y_increment):
	pattern_as_string = "(?P<id>\\d+) (?P<objectClass>[A-Za-z_][0-9A-Za-z_]*) (?P<x>\\d+) (?P<y>\\d+) (?P<width>\\d+) (?P<height>\\d+) .*"
	pattern = re.compile(pattern_as_string)
	
	for line in output_ilv_file_content.split("\n"):
	#	logging.debug(line)
		match_line = pattern.match(line)
	
		if match_line != None:
			logging.debug("updateSimpleIlvObjectLines: line:" + line + " is a simpe ilv object definition line")	
			name = "todo"
			id = match_line.group('id')
			objectClass = match_line.group('objectClass')
			original_x = int(match_line.group('x'))
			original_y = int(match_line.group('y'))
			
			is_inside_range = isObjectInsideRange(original_x, original_y, has_min_original_x, min_original_x, has_max_original_x, max_original_x, has_min_original_y, min_original_y, has_max_original_y, max_original_y)
			
			if is_inside_range:
				if has_x_increment or has_y_increment:
					updated_line = line
					
					new_x = original_x
					new_y = original_y
					
					if has_x_increment:
						new_x = original_x + x_increment
						logging.debug("    original_x attribute:" + str(original_x) + " of object:" + name + " must be updated to:" + str(new_x))
					
					if has_y_increment:
						new_y = original_y + y_increment
						logging.debug("    original_y attribute:" + str(original_y) + " of object:" + name + " must be updated to:" + str(new_y))
						
					
					updated_line = updateValue(updated_line, id + " " + objectClass + " " + str(original_x) + " " + str(original_y),  id + " " + objectClass + " " + str(new_x) + " " + str(new_y))
					
					logging.debug("Line was:" + line + " and beomes:" + updated_line)
					output_ilv_file_content = output_ilv_file_content.replace(line, updated_line)
			
	return output_ilv_file_content
	
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
	logging.info('Read input file:' + input_ilv_file_name)
	input_ilv_file_content = input_ilv_file.read()
	
	# close ilv file
	logging.info('Close input file:' + input_ilv_file_name)
	input_ilv_file.close()

	output_ilv_file_content = input_ilv_file_content
	
	output_ilv_file_content = updateValuesSections(output_ilv_file_content, has_min_original_x, min_original_x, has_max_original_x, max_original_x, has_x_increment, x_increment, has_min_original_y, min_original_y, has_max_original_y, max_original_y, has_y_increment, y_increment)
	output_ilv_file_content = updateSimpleObjectDefinitionLines(output_ilv_file_content, has_min_original_x, min_original_x, has_max_original_x, max_original_x, has_x_increment, x_increment, has_min_original_y, min_original_y, has_max_original_y, max_original_y, has_y_increment, y_increment)
			
	
	logging.info('Create output file:' + output_ilv_file_name)
	output_ilv_file = open(output_ilv_file_name, "w")
	logging.info('Fill output file:' + output_ilv_file_name)
	output_ilv_file.write(output_ilv_file_content)
	logging.info('Close output file:' + output_ilv_file_name)
	output_ilv_file.close()

	logging.info("End. Nominal end of application")

if __name__ == "__main__":
   main(sys.argv[1:])