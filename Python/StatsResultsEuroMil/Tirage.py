class Tirage:
    """Classe définissant un tirage"""

    
    def __init__(self, date_as_string, boule1, boule2, boule3, boule4, boule5, star1, star2, nb_winners, jackpot):
        """Constructeur de notre classe"""
        self.date_as_string = date_as_string
        self.boule1 = boule1
        self.boule2 = boule2
        self.boule3 = boule3
        self.boule4 = boule4
        self.boule5 = boule5
        self.star1 = star1
        self.star2 = star2
        self.nb_winners = nb_winners
        self.jackpot = jackpot