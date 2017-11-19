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
		
	for annee in range(2004, 2017+1):

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
		table_balise_content_as_xml = table_balise_content.replace('colspan=10', 'colspan="10"')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('é', 'e')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('û', 'u')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('</th></th>', '</th>')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('&nbsp;', '')
		
		logging.info("Table balise compliant with XML format:" + table_balise_content_as_xml)
		
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml.replace('<tr><th>Date du tirage</th><th>N1</th><th>N2</th><th>N3</th><th>N4</th><th>N5</th><th>E1</th><th>E2</th><th>Gagnants</th><th>Jackpot</th></tr>', '')
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace('<td><div class', '<boule class')
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace('</div></td>', '</boule>')
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace("<td align='center'", "<winners")
		#table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace("<td align='center'", "<jackpot")
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace("</td><td align='right'", "</winners><jackpot")
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace("</td></tr>", "</jackpot></tr>")
		
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace('<tr><td>', '<tirage date="')
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace("</td><boule", '"><boule')
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace("<winners><strong>", "<winners>")
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace("</strong></winners>", "</winners>")
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace("</jackpot></tr>", "</jackpot></tirage>")
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace('<tr><td colspan="10"><strong>', "<mois>")
		table_balise_content_as_xml_transformed_good_fields = table_balise_content_as_xml_transformed_good_fields.replace("</strong></jackpot></tirage>", "</mois>")
		
		logging.info("Table balise compliant with XML format transformed with good fields:" + table_balise_content_as_xml_transformed_good_fields)
		
		logging.info("Indentation")
		table_balise_content_as_idented_xml = ""
		current_indentation = 0
		
		for i in range(0, len(table_balise_content_as_xml_transformed_good_fields)):					
			if table_balise_content_as_xml_transformed_good_fields[i] == '<' and table_balise_content_as_xml_transformed_good_fields[i+1] != '/':
				current_indentation = current_indentation + 1
				table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + Constants.end_line_character_in_text_file
				for it in range (0,current_indentation):
					table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + '  '
				table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + table_balise_content_as_xml_transformed_good_fields[i]
			
			elif table_balise_content_as_xml_transformed_good_fields[i] == '<' and i < len(table_balise_content_as_xml_transformed_good_fields)-1 and table_balise_content_as_xml_transformed_good_fields[i+1] == '/':
				table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + Constants.end_line_character_in_text_file
				for it in range (0,current_indentation):
					table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + '  '
				table_balise_content_as_idented_xml = table_balise_content_as_idented_xml +  table_balise_content_as_xml_transformed_good_fields[i]
				current_indentation = current_indentation - 1

			else:
				table_balise_content_as_idented_xml = table_balise_content_as_idented_xml + table_balise_content_as_xml_transformed_good_fields[i]
				
		
		logging.info("Table balise content as XML format with identation:" + table_balise_content_as_idented_xml)


		output_file_table_content_name = "Table_content_annee" + str(annee) + ".xml"
		logging.info('Create output file:' + output_file_table_content_name)
		output_file_table_content = open(output_file_table_content_name, "w")
		logging.info('Fill output file:' + output_file_table_content_name)
		output_file_table_content.write(table_balise_content_as_idented_xml)
		logging.info('Close output file:' + output_file_table_content_name)
		output_file_table_content.close()
		
		
		#tree = ET.parse(output_file_table_content_name)

				
				
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

