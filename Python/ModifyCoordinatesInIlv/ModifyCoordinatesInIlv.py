# -*-coding:Utf-8 -*

import logging
import os
import sys

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
logging.info("Size of input file: %d'" , len(input_ilv_file_content))


# Extracting content from input file
current_line_number = 0
for line in input_ilv_file:
	current_line_number = current_line_number + 1
	print("Line ", current_line_number, ":", line)


# close ilv file
logging.info('Closing input file:' + input_ilv_file_name)
input_ilv_file.close()
