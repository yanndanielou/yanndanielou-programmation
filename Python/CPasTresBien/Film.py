
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
				
		self.title_in_windows_format = title.replace("(","")
		self.title_in_windows_format = self.title_in_windows_format.replace(")","")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xb1","n")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xa7","c")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xb6","o")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xb2","o")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xb4","o")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xa2","a")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xaf","i")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xae","i")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc5\\x92","oe")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xbb","u")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xbc","u") #û
		self.title_in_windows_format = self.title_in_windows_format.replace("\\'"," ")
		self.title_in_windows_format = self.title_in_windows_format.replace("'"," ")
		self.title_in_windows_format = self.title_in_windows_format.replace("?","")
		self.title_in_windows_format = self.title_in_windows_format.replace("/","")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xe2\\x80\\x99"," ")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xaa","e")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xa8","e")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xab","e")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xa0","a")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xaa","e") #ê
		self.title_in_windows_format = self.title_in_windows_format.replace("\xc3\\xaa","e")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xa9","e")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\xa9","e")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\x8a","E")
		self.title_in_windows_format = self.title_in_windows_format.replace("\xc3\xa9","e")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\x94","O")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc5\\x93","o")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\x80","A")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\x87","C")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3\\x89","E")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xb0","°")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc2","A")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xc3","e")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\exa8","e")
		self.title_in_windows_format = self.title_in_windows_format.replace("\\xb3","p")







		self.title = title
		self.title2 = title2
		self.lien = lien
		self.full_link = "http://www.cpasbiens.cc/"+ lien
		self.poids = poids
		self.seed_ok_up = seed_ok_up
		self.down = down

		
		Film.films.append(self)
		