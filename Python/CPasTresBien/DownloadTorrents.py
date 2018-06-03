#!/usr/bin/env python
# -*- coding: utf-8 -*-


import logging

import urllib.request
from urllib.request import Request, urlopen, urlretrieve

import urllib
import urllib.request

import Film
from Film import *

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
						filename=logger_directory+'\DownloadTorrents.log',
						filemode='w')

	#logging.debug
	#logging.info
	#logging.warning
	#logging.error
	#logging.critical	

def main(argv):

	configureLogger()	
	
	logging.info('Start application')
	
	List_films_xml_file_name = "testlistfilms.xml"		
	
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
			
			
	opener=urllib.request.build_opener()
	opener.addheaders=[('User-Agent','Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1941.0 Safari/537.36')]
	urllib.request.install_opener(opener)
				
	films_sorted_by_title = Film.films_sorted_by_title()	
	
	for film in films_sorted_by_title:
			
		logging.info('Open: '+ film.full_link)
		req = Request(film.full_link, headers={'User-Agent': 'Mozilla/5.0'})
		website_content = urlopen(req).read()
		website_content_text = str(website_content)
			
		logging.info("Webpage content " + str(len(website_content_text)) )
		logging.info(website_content_text)
		
		website_content_with_pre_treatment = website_content_text.replace('"', '')
		website_content_with_pre_treatment = website_content_with_pre_treatment.replace("'", '')
		website_content_with_pre_treatment = website_content_with_pre_treatment.replace("\\", '')
		
		torrent_link_group_pattern_as_string = '.*(?P<torrent_link_group>/get_torrent/[0-9A-Za-z_]*)'
		torrent_link_group_pattern = re.compile(torrent_link_group_pattern_as_string)
	
		match_torrent_link = torrent_link_group_pattern.match(website_content_with_pre_treatment)
	
		if match_torrent_link != None:
			torrent_link_group = match_torrent_link.group('torrent_link_group')
			logging.info('Torrent link: '+ torrent_link_group)
			full_torrent_link = "http://www.cpasbiens.cc" + torrent_link_group
			logging.info('Torrent full link: '+ full_torrent_link)
			
			urlretrieve(full_torrent_link, 'torrents\\' + film.title + '.torrent')
			
		else:
			logging.info("No match, pattern:" + torrent_link_group_pattern_as_string + " with text" + website_content_with_pre_treatment)

	
	
if __name__ == "__main__":
   main(sys.argv[1:])

