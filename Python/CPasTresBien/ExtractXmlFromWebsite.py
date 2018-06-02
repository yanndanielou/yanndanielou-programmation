#!/usr/bin/env python
# -*- coding: utf-8 -*-


import logging

import urllib.request
from urllib.request import Request, urlopen

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
						filename=logger_directory+'\ExtractXmlFromWebsite.log',
						filemode='w')

	#logging.debug
	#logging.info
	#logging.warning
	#logging.error
	#logging.critical	

def main(argv):

	configureLogger()	
	
	logging.info('Start application')
	
	output_file_by_period_content_as_list = list()

	for filmRange in range(1, 201, 50):

		req = Request('http://www.cpasbiens.cc/torrents/films/' + str(filmRange), headers={'User-Agent': 'Mozilla/5.0'})
		website_content = urlopen(req).read()
			
		logging.info("Webpage content " + str(len(website_content)) )
		logging.info(website_content)
		
			
		# only extract <table> .... </table> content from the websites
		table_balise_content = ""
		inside_table_content = False
		after_table_content = False
		
		for i in range(0, len(website_content) -10 ):
			#logging.info(website_content[i])
			#logging.info(chr(website_content[i]))
			
			
			# logging.info(chr(website_content[i]) + chr(website_content[i+1]) + chr(website_content[i+2]))
			
			if after_table_content == False:
				if inside_table_content == False:
					if chr(website_content[i]) == '<' and chr(website_content[i+1]) == 't' and chr(website_content[i+2]) == 'a' and chr(website_content[i+3]) == 'b' and chr(website_content[i+4]) == 'l' and chr(website_content[i+5]) == 'e' and chr(website_content[i+6]) == ' ':
					#	logging.info("inside_table_content" )
						inside_table_content = True
					
				if inside_table_content == True:
					table_balise_content = table_balise_content + chr(website_content[i])
					if chr(website_content[i-7]) == '<' and chr(website_content[i-6]) == '/' and chr(website_content[i-5]) == 't' and chr(website_content[i-4]) == 'a' and chr(website_content[i-3]) == 'b' and chr(website_content[i-2]) == 'l' and chr(website_content[i-1]) == 'e' and chr(website_content[i]) == '>':
						after_table_content = True

		logging.info("Table balise content:" + table_balise_content)

		
		# Pre treatment on xml file because not valid XML
		# - <td colspan=10> sans guillement autour du 10
		# - Suppression accents		
		table_balise_content_as_xml = table_balise_content.replace('<tbody>', '')
		logging.info("Table balise compliant with XML format:" + table_balise_content_as_xml)
		
		
		logging.info("Indentation")
		table_balise_content_as_idented_xml = ""
		current_indentation = 0
		
		for i in range(0, len(table_balise_content_as_xml)):					
			if table_balise_content_as_xml[i] == '<' and table_balise_content_as_xml[i+1] != '/':
				current_indentation = current_indentation + 1
				table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + Constants.end_line_character_in_text_file
				for it in range (0,current_indentation):
					table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + '  '
				table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + table_balise_content_as_xml[i]
			
			elif table_balise_content_as_xml[i] == '<' and i < len(table_balise_content_as_xml)-1 and table_balise_content_as_xml[i+1] == '/':
				table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + Constants.end_line_character_in_text_file
				for it in range (0,current_indentation):
					table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + '  '
				table_balise_content_as_idented_xml = table_balise_content_as_idented_xml +  table_balise_content_as_xml[i]
				current_indentation = current_indentation - 1

			else:
				table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + table_balise_content_as_xml[i]
				
		
		output_file_by_period_content_as_list.append(table_balise_content_as_idented_xml)
		logging.info("Table balise content as XML format with identation:" + table_balise_content_as_idented_xml)


	output_file_name = "List_films.xml"
	logging.info('Create output file:' + output_file_name)
	output_file = open(output_file_name, "w")
	logging.info('Fill output file:' + output_file_name)
	output_file_content = "".join(output_file_by_period_content_as_list)
	logging.info("output_file_content:" + output_file_content)
	output_file.write(output_file_content)
	logging.info('Close output file:' + output_file_name)
	output_file.close()	
	
	
if __name__ == "__main__":
   main(sys.argv[1:])

