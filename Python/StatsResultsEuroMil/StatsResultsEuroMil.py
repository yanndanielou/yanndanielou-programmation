#!/usr/bin/env python
# -*- coding: utf-8 -*-


import logging

import urllib.request

import param
import Constants

import os
import sys

import re

import getopt

import xml.etree.ElementTree as ET
#from lxml import etree

	
def configureLogger():
	logger_directory = "logs"
	
	if not os.path.exists(logger_directory):
		os.makedirs(logger_directory)
	
	logger_level = param.logger_level
	
	print("Logger level:" +str(logger_level))
	
	logging.basicConfig(level=logger_level,
						format='%(asctime)s %(levelname)-8s %(message)s',
						datefmt='%a, %d %b %Y %H:%M:%S',
						filename=logger_directory+'\StatsResultsEuroMil.log',
						filemode='w')

	#logging.debug
	#logging.info
	#logging.warning
	#logging.error
	#logging.critical	

def main(argv):

	configureLogger()	
	
	logging.info('Start application')
	
	annee = 2017

	website_content = urllib.request.urlopen("http://www.tirage-euromillions.net/euromillions/annees/annee-" + str(annee) + "/").read().decode("utf-8")
			
	# only extract <table> .... </table> content from the websites
	table_balise_content = ""
	inside_table_content = False
	after_table_content = False
	
	for i in range(0, len(website_content) -1 ):
		if after_table_content == False:
			if inside_table_content == False:
				if website_content[i] == '<' and website_content[i+1] == 't' and website_content[i+2] == 'a' and website_content[i+3] == 'b' and website_content[i+4] == 'l' and website_content[i+5] == 'e' and website_content[i+6] == ' ':
					inside_table_content = True
				
			if inside_table_content == True:
				table_balise_content = table_balise_content + website_content[i]
				if website_content[i-7] == '<' and website_content[i-6] == '/' and website_content[i-5] == 't' and website_content[i-4] == 'a' and website_content[i-3] == 'b' and website_content[i-2] == 'l' and website_content[i-1] == 'e' and website_content[i] == '>':
					after_table_content = True

	logging.info("Table balise content:" + table_balise_content)
				
	# Pre treatment on xml file because not valid XML
	# - <td colspan=10> sans guillement autour du 10
	# - Suppression accents		
	table_balise_content = table_balise_content.replace('colspan=10', 'colspan="10"')
	table_balise_content = table_balise_content.replace('é', 'e')
	
	logging.info("Table balise compliant with XML format:" + table_balise_content)

	output_file_table_content_name = "Table_content_annee" + str(annee) + ".xml"
	logging.info('Create output file:' + output_file_table_content_name)
	output_file_table_content = open(output_file_table_content_name, "w")
	logging.info('Fill output file:' + output_file_table_content_name)
	output_file_table_content.write(table_balise_content)
	logging.info('Close output file:' + output_file_table_content_name)
	output_file_table_content.close()
	
			
	# open input file
	# logging.info('Opening input file:' + input_file_name_with_results_as_xml)

	
	# if not os.path.exists(input_file_name_with_results_as_xml):
		# logging.critical("Input file:" + input_file_name_with_results_as_xml + " does not exist. Application stopped")
		# sys.exit()

	# original_imput_file = open(input_file_name_with_results_as_xml, "r")

	# #
	# logging.info('Read input file:' + input_file_name_with_results_as_xml)
	# original_imput_file_content = original_imput_file.read()
					
	
	# # close input file
	# logging.info('Close input file:' + input_file_name_with_results_as_xml)
	# original_imput_file.close()
	
	
if __name__ == "__main__":
   main(sys.argv[1:])

