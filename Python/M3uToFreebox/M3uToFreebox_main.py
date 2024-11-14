""" https://tkdocs.com/tutorial/firstexample.html """
import random

import tkinter
from tkinter import (
    filedialog,
    simpledialog,
    messagebox,
    scrolledtext,
    Menu,
    colorchooser,
    ttk
)

import sys

sys.path.append('.')
sys.path.append('../Logger')
import LoggerConfig

sys.path.append('../Common')
import date_time_formats

import time

from M3uToFreeboxMainView import M3uToFreeboxMainView

import os


class M3uToFreeboxApplication():

    def __init__(self, m3uToFreeboxMainView):
        self.m3uToFreeboxMainView = m3uToFreeboxMainView


def main(argv):
    application_start_time = time.time()
    log_file_name = os.path.basename(__file__) + str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)

    LoggerConfig.printAndLogInfo('Start application')

    mainView = M3uToFreeboxMainView()
    app: M3uToFreeboxApplication = M3uToFreeboxApplication(mainView)
    mainView.mainloop()

    LoggerConfig.printAndLogInfo("End. Nominal end of application in " + date_time_formats.format_duration_to_string(
    time.time() - application_start_time))


if __name__ == "__main__":
    main(sys.argv[1:])
