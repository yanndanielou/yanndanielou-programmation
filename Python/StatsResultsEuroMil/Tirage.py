
import re
import getopt

from datetime import date, time, datetime

class Tirage:
	"""Classe définissant un tirage"""
	tirages = list()
	
	def __init__(self, date_as_text, boule1, boule2, boule3, boule4, boule5, star1, star2, winners, jackpot):
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
			self.month = int(match_date.group("month"))
			self.year = int(match_date.group("year"))
			
			#self._date = datetime.date(self.year, self.month, self.day_of_month)
		
		
		self.boule1 = boule1
		self.boule2 = boule2
		self.boule3 = boule3
		self.boule4 = boule4
		self.boule5 = boule5
		self.star1 = star1
		self.star2 = star2
		self.winners = winners
		self.jackpot = jackpot
		
		self.rank = len(Tirage.tirages)
		
		Tirage.tirages.append(self)
		