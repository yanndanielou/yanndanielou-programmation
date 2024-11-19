""" https://tkdocs.com/tutorial/firstexample.html """
import random

import m3uToFreeboxMainView


import sys
import jsonInstructions

sys.path.append('.')
sys.path.append('../Logger')
import LoggerConfig

sys.path.append('../Common')
import date_time_formats

import time

import tkinter


import os


class M3uToFreeboxApplication():

    def __init__(self, m3uToFreeboxMainView):
        self.m3uToFreeboxMainView = m3uToFreeboxMainView


def main(argv):
    application_start_time = time.time()
    log_file_name = os.path.basename(__file__) + str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)

    LoggerConfig.printAndLogInfo('Start application')

    root = tkinter.Tk()
    main_view = m3uToFreeboxMainView.M3uToFreeboxMainView(root)
    app: M3uToFreeboxApplication = M3uToFreeboxApplication(main_view)
    root.mainloop()

    LoggerConfig.printAndLogInfo("End. Nominal end of application in " + date_time_formats.format_duration_to_string(
    time.time() - application_start_time))


if __name__ == "__main__":
    main(sys.argv[1:])
