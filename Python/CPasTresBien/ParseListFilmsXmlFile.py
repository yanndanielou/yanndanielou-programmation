#!/usr/bin/env python
# -*- coding: utf-8 -*-


import logging

import urllib.request

import Film
from Film import *
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
						filename=logger_directory+'\ParseListFilmsXmlFile.log',
						filemode='w')

	#logging.debug
	#logging.info
	#logging.warning
	#logging.error
	#logging.critical	

def main(argv):

	configureLogger()	
	
	logging.info('Start application')
	
	List_films_xml_file_name = "List_films.xml"		
	
	tree = ET.parse(List_films_xml_file_name)
	root_table = tree.getroot()
	
	for pages in root_table.findall('Page'):
		logging.info('page')
		for film_xml in pages.findall('Film'):
			logging.info('Film')
			lien = film_xml.get('lien').strip()
			title2 = film_xml.get('title2').strip()
			title = film_xml.get('title').strip()
			poids = film_xml.find('poids').text.replace('\n', '').strip()
			seed_ok_up = film_xml.find('seed_ok_up').text.replace('\n', '').strip()
			down = film_xml.find('down').text.replace('\n', '').strip()

			film = Film(title, title2, lien, poids, seed_ok_up, down)				
			
	output_csv_file_name = "films.csv"
	output_csv_file = open(output_csv_file_name, "w",newline='')
	
	output_file_fieldnames = ['title', 'title2', 'lien', 'full_link', 'poids', 'seed_ok_up', 'down']

		
	output_file_writer = csv.DictWriter(output_csv_file, fieldnames=output_file_fieldnames, delimiter=';')
	
	output_file_writer.writeheader()

	
	films_sorted_by_title = Film.films_sorted_by_title()
	for film in films_sorted_by_title:
			
		dictionnaire = {}
		
		# 
		dictionnaire["title"] = film.title
		dictionnaire["title2"] = film.title2
		dictionnaire["lien"] = film.lien
		dictionnaire["full_link"] = film.full_link
		dictionnaire["poids"] = film.poids
		dictionnaire["seed_ok_up"] = film.seed_ok_up
		dictionnaire["down"] = film.down
			
		output_file_writer.writerow(dictionnaire)
	
	output_csv_file.close()
			
	
if __name__ == "__main__":
   main(sys.argv[1:])

