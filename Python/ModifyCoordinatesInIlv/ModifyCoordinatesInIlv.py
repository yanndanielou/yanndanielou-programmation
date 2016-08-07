# -*-coding:Utf-8 -*

import logging

import os
import sys

import re


def printAllAttributes(attributes):
	for attribute in attributes:
	 	logging.debug(attribute)

def getStringValue(attributeLine):
	attributeLine.split("=")

def findNameAttribute(attributes):
	return findStringAttributeLine('name', attributes)

def findStringAttributeLine(attributeName, attributes):
	return findAttributeValue('String', attributeName, attributes, "[0-9A-Za-z_]*")

	
def findXAttribute(attributes):
	return findIntAttributeLine('x', attributes)

def findYAttribute(attributes):
	return findIntAttributeLine('y', attributes)

def findIntAttributeLine(attributeName, attributes):
	return findAttributeValue('Int', attributeName, attributes, "\\d+")

# Return the attribute attributeName in attributes
def findAttributeValue(attributeType, attributeName, attributes, valuePattern):
	pattern_as_string = "[\s]*" + attributeType+"[\s]*"+ attributeName+ "[\s]*=[\s]*" + "(?P<value>" + valuePattern + ")" + "$"
	pattern = re.compile(pattern_as_string)

	for attribute in attributes:
		match_attribute_searched = pattern.match(attribute)
	
		if match_attribute_searched != None:
			return(match_attribute_searched.group('value'))	

	logging.error('Could not find attribute with type:' + attributeType + " name:" + attributeName + " with pattern:" + pattern_as_string + " among attributes:")
	printAllAttributes(attributes)
	sys.exit()
	return ""
	


# configure logger
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

logging.info('Start application')


# check arguments count
application_file_name_arg_number	= 0
input_ilv_file_name_arg_number 		= application_file_name_arg_number + 1
output_ilv_file_name_arg_number		= input_ilv_file_name_arg_number + 1
min_original_x_arg_number			= output_ilv_file_name_arg_number + 1
max_original_x_arg_number			= min_original_x_arg_number + 1
x_increment_arg_number				= max_original_x_arg_number + 1
min_original_y_arg_number			= x_increment_arg_number + 1
max_original_y_arg_number			= min_original_y_arg_number + 1
y_increment_arg_number				= max_original_y_arg_number + 1
arg_count							= y_increment_arg_number + 1



if len(sys.argv) != arg_count:
	logging.critical("Incorrect number of arguments. %d Arguments given (%s), %d were expected. End application", len(sys.argv), str(sys.argv), arg_count)
	sys.exit()
	

# Retrieve arguments
application_file_name 	= sys.argv[application_file_name_arg_number]
input_ilv_file_name 	= sys.argv[input_ilv_file_name_arg_number]
output_ilv_file_name 	= sys.argv[output_ilv_file_name_arg_number]
min_original_x 			= sys.argv[min_original_x_arg_number]
max_original_x			= sys.argv[max_original_x_arg_number]
x_increment 			= sys.argv[x_increment_arg_number]
min_original_y 			= sys.argv[min_original_y_arg_number]
max_original_y 			= sys.argv[max_original_y_arg_number]
y_increment				= sys.argv[y_increment_arg_number]


# Print info about arguments (what this application will do and on which conditions)

if x_increment > 0:
	logging.info("X coordinates must be incremented by:" + y_increment)
elif x_increment == 0:
	logging.info("X coordinates must not be touched");
else:
	logging.info("X coordinates must be decremented by:" + y_increment)


if y_increment > 0:
	logging.info("Y coordinates must be incremented by:" + y_increment)
elif y_increment == 0:
	logging.info("Y coordinates must not be touched");
else:
	logging.info("Y coordinates must be decremented by:" + y_increment)

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

logging.info("Size of input file: %d" , len(input_ilv_file_content_in_one_line))

# Retrieve all values { } in the file
all_values = re.findall(r'values {[^}]*}', input_ilv_file_content_in_one_line)

logging.info("Number of values{} found: %d" , len(all_values))

value_number = 0
for value in all_values:
	value_number = value_number + 1
	logging.debug("Value %d", value_number)
	value_attributes = value.split(replacement_string_for_new_line_caracter)
	name_attribute = findNameAttribute(value_attributes)
	x_attribute = findXAttribute(value_attributes)
	y_attribute = findYAttribute(value_attributes)
	
	logging.debug("    name_attribute:" + name_attribute)
	logging.debug("    x_attribute:" + x_attribute)
	logging.debug("    y_attribute:" + y_attribute)
	
	
	

	
	

#print(all_values)


# Extracting content from input file
#current_line_number = 0
#for line in input_ilv_file:
#	current_line_number = current_line_number + 1
#	print("Line ", current_line_number, ":", line)


# close ilv file
logging.info('Closing input file:' + input_ilv_file_name)
input_ilv_file.close()

