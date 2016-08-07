# -*-coding:Utf-8 -*

import logging

import os
import sys

import re


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
	print("Value ", value_number, ":", value)



#print(all_values)


# Extracting content from input file
#current_line_number = 0
#for line in input_ilv_file:
#	current_line_number = current_line_number + 1
#	print("Line ", current_line_number, ":", line)


# close ilv file
logging.info('Closing input file:' + input_ilv_file_name)
input_ilv_file.close()
