#!/usr/bin/env python
# -*- coding: utf-8 -*-


import logging

import urllib.request

import Tirage
from Tirage import *
import param
from param import *
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
		
	for annee in range(2004, 2018+1):

		output_file_table_content_name = "Table_content_annee" + str(annee) + ".xml"		
		
		
		tree = ET.parse(output_file_table_content_name)
		root_table = tree.getroot()
	
		for tirage_xml in root_table.findall('tirage'):
			date_as_text = tirage_xml.get('date')
			winners = tirage_xml.find('winners').text
			jackpot = tirage_xml.find('jackpot').text
			
			boules_xml = tirage_xml.findall('boule')	

			boules = list()
			stars = list()
				
			for i in range (0, numberOfBoules()):
				boules.append(int(boules_xml[i].text))
				
			for i in range (numberOfBoules(), numberOfBoules() + numberOfStars()):
				stars.append(int(boules_xml[i].text))
	
			tirage = Tirage(date_as_text, boules, stars, winners, jackpot)				
				
		output_file_name = "output_analyzis.csv"
		output_file = open(output_file_name, "w",newline='')
		
		output_file_fieldnames = ['date_tir', 'no_tirage', 'win', 'mean', 'boules', 'stars']
		for i in range(1,numberOfBoules()+1):
			output_file_fieldnames.append("boule" + str(i))
			
		for i in range(1,numberOfStars()+1):
			output_file_fieldnames.append("star" + str(i))
			
		for i in range(1,bouleMaxValue()+1):
			output_file_fieldnames.append("counter_boule_" + str(i))
			output_file_fieldnames.append("ratio_boule_" + str(i))
			
		for i in range(1,starMaxValue()+1):
			output_file_fieldnames.append("counter_star_" + str(i))
			output_file_fieldnames.append("ratio_star_" + str(i))
			output_file_fieldnames.append("presence_in_tirage_count_star_" + str(i))
			output_file_fieldnames.append("ratio_per_presence_in_tirage_star_" + str(i))
			
		output_file_writer = csv.DictWriter(output_file, fieldnames=output_file_fieldnames, delimiter='|')
		
		output_file_writer.writeheader()
		
		
		#init stats by boule
		counter_by_boule = {}
		for i in range(1,bouleMaxValue()+1):
			counter_by_boule[i] = 0
			
		counter_by_star = {}
		for i in range(1,starMaxValue()+1):
			counter_by_star[i] = 0
		
		tirages_sorted_by_date = Tirage.tirages_sorted_by_date()
		for tirage in tirages_sorted_by_date:
			logging.info("Tirage " + str(tirage.insertion_rank) + ":" + tirage.date_as_text + ", boules:" + str(tirage.boules) + ", stars:" + str(tirage.stars) + ", jackpot:" + tirage.jackpot)
		
			dictionnaire = {}
			
			no_tirage = tirages_sorted_by_date.index(tirage)+1
			
			#%Y-%m-%d 
			dictionnaire["date_tir"] = str(tirage.year) + "-" + str(tirage.month_two_digits) + "-" + str(tirage.day_of_month_two_digits)
			dictionnaire["boules"] = tirage.boules
			dictionnaire["stars"] = tirage.stars
			dictionnaire["no_tirage"] = no_tirage
			
			for i in range(1,numberOfBoules()+1):
				dictionnaire["boule" + str(i)] = tirage.boules[i-1]				
				counter_by_boule[tirage.boules[i-1]] = counter_by_boule[tirage.boules[i-1]] + 1
				
			for i in range(1,numberOfStars()+1):
				dictionnaire["star" + str(i)] = tirage.stars[i-1]
				counter_by_star[tirage.stars[i-1]] = counter_by_star[tirage.stars[i-1]] + 1
				
			for i in range(1,bouleMaxValue()+1):
				dictionnaire["counter_boule_" + str(i)] = counter_by_boule[i]
				dictionnaire["ratio_boule_" + str(i)] = format(counter_by_boule[i] / (no_tirage)*100, '.3f')
				
			for i in range(1,starMaxValue()+1):
				dictionnaire["counter_star_" + str(i)] = counter_by_star[i]
				dictionnaire["ratio_star_" + str(i)] = format(counter_by_star[i] / (no_tirage)*100, '.3f')
				stars_introduction_tirage_number = param.star_introduction_tirage_number()
				star_introduction_tirage_number = stars_introduction_tirage_number[i-1]
				
				if no_tirage >= star_introduction_tirage_number:
					presence_in_tirage_count_star = no_tirage - star_introduction_tirage_number	+ 1			
					dictionnaire["presence_in_tirage_count_star_" + str(i)] = presence_in_tirage_count_star
					dictionnaire["ratio_per_presence_in_tirage_star_" + str(i)] = format(counter_by_star[i] / (presence_in_tirage_count_star)*100, '.3f')
				else:
					dictionnaire["presence_in_tirage_count_star_" + str(i)] = 0
					dictionnaire["ratio_per_presence_in_tirage_star_" + str(i)] = 0
					
			output_file_writer.writerow(dictionnaire)
		
		output_file.close()
			
	
if __name__ == "__main__":
   main(sys.argv[1:])

