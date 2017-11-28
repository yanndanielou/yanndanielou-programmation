
import re
import getopt

import operator
from operator import attrgetter

from datetime import date, time, datetime

class Tirage:
	"""Classe définissant un tirage"""
	tirages = list()
	
	@staticmethod
	def tirages_sorted_by_date():
		return sorted(Tirage.tirages, key=attrgetter("date_as_date"))
	
	def __init__(self, date_as_text, boules, stars, winners, jackpot):
		"""Constructeur de notre classe"""
		
		#Mardi 21/11/2017		
		self.date_as_text = date_as_text
		pattern_date_as_text_as_string = "(?P<weekday>.*) (?P<day_of_month>\\d+)/(?P<month>\\d+)/(?P<year>\\d+)"
		pattern_date_as_text = re.compile(pattern_date_as_text_as_string)
		match_date = pattern_date_as_text.match(date_as_text)
		
		if match_date == None:
			print("No match for line " + date_as_text + " with pattern " + pattern_date_as_text_as_string)
		else:
			self.weekday = match_date.group("weekday")
			self.day_of_month = int(match_date.group("day_of_month"))			
			self.day_of_month_two_digits = match_date.group("day_of_month")
			
			self.month = int(match_date.group("month"))
			self.month_two_digits = match_date.group("month")
			
			self.year = int(match_date.group("year"))
			
			self.date_as_date = date(self.year, self.month, self.day_of_month)
			#self.number_of_days_since_first_january_2000 = (self.date_as_date-date(2000, 1, 1)).days
		
		self.boules = list()
		self.stars = list()
		
		self.boules = boules
		
		
		self.stars = stars
		
		self.winners = winners
		self.jackpot = jackpot
		
		self.insertion_rank = len(Tirage.tirages)
		
		Tirage.tirages.append(self)
		