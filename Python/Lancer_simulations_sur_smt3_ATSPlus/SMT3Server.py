class SMT3Server:
    def __init__(self, port, description, smt3Version):
        self.port = port
        self.description = description
        self.smt3Version = smt3Version
        self.url = None
        self.full_url = None 
    
