# -*-coding:Utf-8 -*

# This file contains static configuration of the tool
# Constants are defined here so they can be changed by project (or temporary for debugging purpose for example)

import logging

logger_level = logging.INFO


#https://www.cpasbiens.co/torrents_films.html,page-
#http://www.cpasbiens.cc/torrents/films/
site_address_prefix = 'https://www.cpasbiens.co/torrents_films.html,page-'

# for cpasbiens.cc: 50
page_range = 1

sleep_in_second_between_two_requests_to_avoid_ban_from_website = 10