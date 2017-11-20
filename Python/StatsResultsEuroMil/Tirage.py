class Tirage:
	"""Classe définissant un tirage"""
	tirages = list()
	
	def __init__(self, date_as_text, boule1, boule2, boule3, boule4, boule5, star1, star2, winners, jackpot):
		"""Constructeur de notre classe"""
		self.date_as_text = date_as_text
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
		