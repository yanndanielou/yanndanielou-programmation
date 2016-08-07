# -*-coding:Utf-8 -*

import logging

import os
import sys

import re



def findNameAttributeLine(attributes):
	return findStringAttributeLine('name', attributes)

def findStringAttributeLine(attributeName, attributes):
	return findAttributeLine('String', attributeName, attributes, "[0-9A-Za-z_]*")

	
def findXAttributeLine(attributes):
	return findIntAttributeLine('x', attributes)

def findYAttributeLine(attributes):
	return findIntAttributeLine('y', attributes)

def findIntAttributeLine(attributeName, attributes):
	return findAttributeLine('Int', attributeName, attributes, "[0-9]*")

# Return the attribute attributeName in attributes
def findAttributeLine(attributeType, attributeName, attributes, valuePattern):
	pattern = re.compile(attributeName+"[\s]*"+ attributeName+ "[\s]*=[\s]*" + valuePattern + "$")

	for attribute in attributes:
		match_attribute_searched = pattern.match(attribute)
		if match_attribute_searched != None:
			return attribute

	logging.error('Could not find attribute with type:' + attributeType + " name:" + attributeName + " among attributes:")# + attributes)
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


# check arguments
if len(sys.argv) < 2:
	logging.critical("Not enough arguments. End application")
	sys.exit()


# open input ilv file
input_ilv_file_name = sys.argv[1]

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
	print("Value ", value_number)
	logging.debug("Value %d", value_number)
	value_attributes = value.split(replacement_string_for_new_line_caracter)
	name_attribute_line = findNameAttributeLine(value_attributes)
	x_attribute_line = findXAttributeLine(value_attributes)
	y_attribute_line = findYAttributeLine(value_attributes)
	
	logging.debug("    name_attribute_line:" + name_attribute_line)
	logging.debug("    x_attribute_line:" + x_attribute_line)
	logging.debug("    y_attribute_line:" + y_attribute_line)
	
	for value_attribute in value_attributes:
		print("    Attribute:",value_attribute)
		logging.debug("    Attribute:" + value_attribute)
		#search for x, y and name attributes

#print(all_values)


# Extracting content from input file
#current_line_number = 0
#for line in input_ilv_file:
#	current_line_number = current_line_number + 1
#	print("Line ", current_line_number, ":", line)


# close ilv file
logging.info('Closing input file:' + input_ilv_file_name)
input_ilv_file.close()

