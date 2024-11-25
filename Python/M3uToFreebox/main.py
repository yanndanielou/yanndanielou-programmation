# -*-coding:Utf-8 -*

import random


#import sys

import time

import tkinter


import os


from tkinter import (
  filedialog, 
  ttk
)


from Dependencies.Logger import LoggerConfig
from Dependencies.Common import date_time_formats


import m3u
import detailsivew
import application
import main_view



def main():
    """ Main function """
    application_start_time = time.time()
    log_file_name = os.path.basename(__file__) + str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)

    LoggerConfig.printAndLogInfo('Start application')

    mainview = main_view.M3uToFreeboxMainView()
    app: application.M3uToFreeboxApplication = application.M3uToFreeboxApplication(mainview)
    mainview.m3u_to_freebox_application = app
    mainview.mainloop()

    LoggerConfig.printAndLogInfo("End. Nominal end of application in " + date_time_formats.format_duration_to_string(
    time.time() - application_start_time))


if __name__ == "__main__":
    # sys.argv[1:]
    main()
