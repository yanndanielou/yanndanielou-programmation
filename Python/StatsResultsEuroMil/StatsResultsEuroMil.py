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


	website_content = urllib.request.urlopen("http://www.tirage-euromillions.net/euromillions/annees/annee-2017/").read().decode("utf-8")
			
	
	# only extract <table> .... </table> content from the websites
	
	table_cablise_content = ""
	
	inside_table_content = False
	for i in range(0, len(website_content) -1 ):
		if 
	
	
	logging.info("website_content size:%d", len(website_content)
	
	
			
	# Pre treatment on xml file because not valid XML
	# - <td colspan=10> sans guillement autour du 10
	# - Suppression accents		
	
	
			
	# open input file
	logging.info('Opening input file:' + input_file_name_with_results_as_xml)

	
	if not os.path.exists(input_file_name_with_results_as_xml):
		logging.critical("Input file:" + input_file_name_with_results_as_xml + " does not exist. Application stopped")
		sys.exit()

	original_imput_file = open(input_file_name_with_results_as_xml, "r")

	#
	logging.info('Read input file:' + input_file_name_with_results_as_xml)
	original_imput_file_content = original_imput_file.read()
					
	
	# close input file
	logging.info('Close input file:' + input_file_name_with_results_as_xml)
	original_imput_file.close()
	
	
	
	
	
			
			
	tree = ET.parse(input_file_name_with_results_as_xml)


if __name__ == "__main__":
   main(sys.argv[1:])

