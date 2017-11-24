#!/usr/bin/env python
# -*- coding: utf-8 -*-


import logging

import urllib.request

import Tirage
from Tirage import *
import param
import Constants

import os
import sys

import re

import getopt

import csv


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

		output_file_table_content_name = "Table_content_annee" + str(annee) + ".xml"		
		
		
		tree = ET.parse(output_file_table_content_name)
		root_table = tree.getroot()
	
		for tirage_xml in root_table.findall('tirage'):
			date_as_text = tirage_xml.get('date')
			winners = tirage_xml.find('winners').text
			jackpot = tirage_xml.find('jackpot').text
			
			boules_xml = tirage_xml.findall('boule')			
			boule1 = int(boules_xml[0].text)
			boule2 = int(boules_xml[1].text)
			boule3 = int(boules_xml[2].text)
			boule4 = int(boules_xml[3].text)
			boule5 = int(boules_xml[4].text)
			star1 = int(boules_xml[5].text)
			star2 = int(boules_xml[6].text)
	
			tirage = Tirage(date_as_text, boule1, boule2, boule3, boule4, boule5, star1, star2, winners, jackpot)				
				
			logging.info("Tirage " + str(tirage.insertion_rank) + ":" + tirage.date_as_text + ", boules:" + str(tirage.boules) + ", stars:" + str(tirage.stars) + ", jackpot:" + tirage.jackpot)
		
		output_file_name = "output_analyzis.csv"
		output_file = open(output_file_name, "w",newline='')
		
		output_file_fieldnames = ['date', 'win']
		for i in range(1,5+1):
			output_file_fieldnames.append("boule" + str(i))
		for i in range(1,2+1):
			output_file_fieldnames.append("star" + str(i))
		
		output_file_writer = csv.DictWriter(output_file, fieldnames=output_file_fieldnames)
		
		output_file_writer.writeheader()
		
		for tirage in Tirage.tirages_sorted_by_date():
			dictionnaire = {}
			dictionnaire["date"] = tirage.date_as_text
			
			for i in range(1,5+1):
				dictionnaire["boule" + str(i)] = tirage.boules[i-1]
				
			for i in range(1,2+1):
				dictionnaire["star" + str(i)] = tirage.stars[i-1]
			
			output_file_writer.writerow(dictionnaire)
		
		output_file.close()
			
	
if __name__ == "__main__":
   main(sys.argv[1:])

