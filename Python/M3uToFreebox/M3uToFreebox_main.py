""" https://tkdocs.com/tutorial/firstexample.html """
import random
from tkinter import *
from tkinter import ttk

import sys
sys.path.append('.')
sys.path.append('../Logger')
import LoggerConfig


sys.path.append('../Common')
import date_time_formats

import time

import os


class M3uToFreeboxApplication(Tk):

    def __init__(self, root):

        root.title("Feet to Meters")

        mainframe = ttk.Frame(root, padding="3 3 12 12")
        mainframe.grid(column=0, row=0, sticky=(N, W, E, S))
        root.columnconfigure(0, weight=1)
        root.rowconfigure(0, weight=1)
       
        self.feet = StringVar()
        feet_entry = ttk.Entry(mainframe, width=7, textvariable=self.feet)
        feet_entry.grid(column=2, row=1, sticky=(W, E))
        self.meters = StringVar()

        ttk.Label(mainframe, textvariable=self.meters).grid(column=2, row=2, sticky=(W, E))
        ttk.Button(mainframe, text="Calculate", command=self.calculate).grid(column=3, row=3, sticky=W)

        ttk.Label(mainframe, text="feet").grid(column=3, row=1, sticky=W)
        ttk.Label(mainframe, text="is equivalent to").grid(column=1, row=2, sticky=E)
        ttk.Label(mainframe, text="meters").grid(column=3, row=2, sticky=W)

        for child in mainframe.winfo_children(): 
            child.grid_configure(padx=5, pady=5)

        feet_entry.focus()
        root.bind("<Return>", self.calculate)
        
    def calculate(self, *args):
        try:
            value = float(self.feet.get())
            self.meters.set(int(0.3048 * value * 10000.0 + 0.5)/10000.0)
        except ValueError:
            pass



def main(argv):
   
    application_start_time = time.time()
    log_file_name = os.path.basename(__file__) +  str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    
    LoggerConfig.printAndLogInfo('Start application')
    
    root = Tk()
    app:M3uToFreeboxApplication = M3uToFreeboxApplication(root)
    root.mainloop()


    LoggerConfig.printAndLogInfo("End. Nominal end of application in " + date_time_formats.format_duration_to_string(time.time() - application_start_time))
    

if __name__ == "__main__":
   main(sys.argv[1:])