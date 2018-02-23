# -*-coding:Utf-8 -*

# This file contains static configuration of the tool
# Constants are defined here so they can be changed by project (or temporary for debugging purpose for example)

import logging

logger_level = logging.INFO

# constants
def bouleMaxValue():
	return 50
	
def starMaxValue():
	return 12
	
def numberOfStars():
	return 2
	
def numberOfBoules():
	return 5

def star_introduction_tirage_number():
	introduction_tirage_number = list()
	
	# 9 stars introduced on first tirage
	introduction_tirage_number.append(1)
	introduction_tirage_number.append(1)
	introduction_tirage_number.append(1)
	introduction_tirage_number.append(1)
	introduction_tirage_number.append(1)
	introduction_tirage_number.append(1)
	introduction_tirage_number.append(1)
	introduction_tirage_number.append(1)
	introduction_tirage_number.append(1)
	
	#tandis qu'entre 10 mai 2011 et 23 septembre 2016, il fallait choisir 5 numéros entre 1 et 50 et 2 étoiles numérotées de 1 à 11
	introduction_tirage_number.append(379)
	introduction_tirage_number.append(379)
	
	#
	introduction_tirage_number.append(941)

	return introduction_tirage_number