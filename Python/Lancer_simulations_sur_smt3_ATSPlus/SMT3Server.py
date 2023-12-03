class SMT3Server:
    def __init__(self, port, smt3Version):
        self.port = port
        self.smt3Version = smt3Version
        self.url = None
        self.full_url = None 

    def description(self):
        return self.smt3Version + "_" + str(self.port)
    
