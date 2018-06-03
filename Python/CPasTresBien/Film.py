
import re
import getopt

import operator
from operator import attrgetter

from datetime import date, time, datetime

class Film:
	"""Classe définissant un tirage"""
	films = list()
	
	@staticmethod
	def films_sorted_by_title():
		return sorted(Film.films, key=attrgetter("title"))
	
	def __init__(self, title, title2, lien, poids, seed_ok_up, down):
		"""Constructeur de notre classe"""
				
		self.title_in_windows_format = title.replace("\\","").replace("(","").replace(")","").replace("'","").replace("?","").replace("/","")
		self.title = title
		self.title2 = title2
		self.lien = lien
		self.full_link = "http://www.cpasbiens.cc/"+ lien
		self.poids = poids
		self.seed_ok_up = seed_ok_up
		self.down = down

		
		Film.films.append(self)
		