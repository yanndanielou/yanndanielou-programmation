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

	for filmRange in range(1, 50000, param.page_range):

		site_address = param.site_address_prefix + str(filmRange)
		logging.info('Open: '+ site_address)
		req = Request(site_address, headers={'User-Agent': 'Mozilla/5.0'})
		
		timeout_in_s = 60
		try:
			url_open = urlopen(req, data=None, timeout = timeout_in_s)
		except urllib.error.URLError as e:
			print(e.reason)
		except socket.timeout as e:
			logging.info('Socket timeout')
		
		website_content_text = str(website_content)
			
		logging.info("Webpage content " + str(len(website_content_text)) )
		logging.info(website_content_text)
		
			
		# only extract <table> .... </table> content from the websites
		table_balise_content = ""
		inside_table_content = False
		after_table_content = False
		
		for i in range(0, len(website_content_text) -10 ):
			#logging.info(website_content_text[i])
			#logging.info(website_content_text[i]))
			
			
			# logging.info(website_content_text[i]) + website_content_text[i+1]) + website_content_text[i+2]))
			
			if after_table_content == False:
				if inside_table_content == False:
					if website_content_text[i] == '<' and website_content_text[i+1] == 't' and website_content_text[i+2] == 'a' and website_content_text[i+3] == 'b' and website_content_text[i+4] == 'l' and website_content_text[i+5] == 'e' and website_content_text[i+6] == ' ':
					#	logging.info("inside_table_content" )
						inside_table_content = True
					
				if inside_table_content == True:
					table_balise_content = table_balise_content + website_content_text[i]
					if website_content_text[i-7] == '<' and website_content_text[i-6] == '/' and website_content_text[i-5] == 't' and website_content_text[i-4] == 'a' and website_content_text[i-3] == 'b' and website_content_text[i-2] == 'l' and website_content_text[i-1] == 'e' and website_content_text[i] == '>':
						after_table_content = True

		logging.info("Table balise content:" + table_balise_content)
		
		if not table_balise_content:
			logging.info("Page:" + site_address + " is empty. Stop parsing website since there is no more movie")
			break

		
		# Pre treatment on xml file because not valid XML
		# - <td colspan=10> sans guillement autour du 10
		# - Suppression accents		
		table_balise_content_as_xml = table_balise_content.replace('<tbody>', '')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('é', 'e')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('\\x82', 'e')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('\\n', '')
		
		
		table_balise_content_as_xml = table_balise_content_as_xml.replace('table class="table-corps', 'Page film_range="' + str(filmRange) + '"')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('""' ,'"')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('</table>' , '</Page>')
		
		
		table_balise_content_as_xml = table_balise_content_as_xml.replace('<tr><td>', '<Film')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('</td></tr>', '')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('<div class="poid">', '<poids>')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('GB</div>', 'GB</poids>')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('MB</div>', 'MB</poids>')
		
		table_balise_content_as_xml = table_balise_content_as_xml.replace('<a href=', ' lien=')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('class="titre">', ' title2="')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('</a>', '">')
		
		
		table_balise_content_as_xml = table_balise_content_as_xml.replace('<div class="up"><span class="seed_ok">', '<seed_ok_up>')
		table_balise_content_as_xml = table_balise_content_as_xml.replace('</span></div>', '</seed_ok_up>')
		
		table_balise_content_as_xml = table_balise_content_as_xml.replace('<div class="down">', '<down>')
		
		table_balise_content_as_xml = table_balise_content_as_xml.replace('</div>', '</down></Film>')
		
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
		
		temp_file_name = "List_films" + str(filmRange)+".temp.xml"
		logging.info('Create tmp file:' + temp_file_name)
		temp_file = open(temp_file_name, "w")
		logging.info('Fill tmp file:' + temp_file_name)
		temp_file_content = "".join(table_balise_content_as_idented_xml)
		temp_file.write(temp_file_content)
		logging.info('Close tmp file:' + temp_file_name)
		temp_file.close()	


	output_file_name = "List_films_as_xml.xml"
	logging.info('Create output file:' + output_file_name)
	output_file = open(output_file_name, "w")
	logging.info('Fill output file:' + output_file_name)
	output_file_content = "<root>"
	output_file_content = output_file_content + "".join(output_file_by_period_content_as_list)
	output_file_content = output_file_content + "</root>"
	logging.info("output_file_content:" + output_file_content)
	output_file.write(output_file_content)
	logging.info('Close output file:' + output_file_name)
	output_file.close()	
	
	
if __name__ == "__main__":
   main(sys.argv[1:])

