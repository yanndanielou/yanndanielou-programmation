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

import os


class M3uToFreeboxApplication():

    def __init__(self, root):

        root.title("M3U to Freebox")

        menu_bg = "black"
        menu_fg = "lightgrey"


        submenu_bg = "black"
        submenu_fg = "lightgrey"

        menu_bar = tkinter.Menu(root)
        root.config(menu=menu_bar)
        menu_bar.config(
            font=("Courier", 11),
            bg=menu_bg,
            fg=menu_fg,
            borderwidth=6,
            relief=tkinter.RAISED
        )

        file_menu = tkinter.Menu(
            menu_bar,
            tearoff=0,
            font=("Courier New", 10),
            bg=submenu_bg,
            fg=submenu_fg
        )
        menu_bar.add_cascade(label="File", menu=file_menu)

        file_menu.add_command(label="Open", command=None)

        mainframe = ttk.Frame(root, padding="3 3 12 12")
        mainframe.grid(column=0, row=0, sticky=(tkinter.N, tkinter.W, tkinter.E, tkinter.S))
        mainframe.columnconfigure(0, weight=1)
        mainframe.rowconfigure(0, weight=1)

        self.feet = tkinter.StringVar()
        feet_entry = ttk.Entry(mainframe, width=7, textvariable=self.feet)
        feet_entry.grid(column=2, row=1, sticky=(tkinter.W, tkinter.E))
        mainframe.meters = tkinter.StringVar()

        ttk.Label(mainframe, textvariable=mainframe.meters).grid(column=2, row=2, sticky=(tkinter.W, tkinter.E))
        ttk.Button(mainframe, text="Calculate", command=self.calculate).grid(column=3, row=3, sticky=tkinter.W)

        ttk.Label(mainframe, text="feet").grid(column=3, row=1, sticky=tkinter.W)
        ttk.Label(mainframe, text="is equivalent to").grid(column=1, row=2, sticky=tkinter.E)
        ttk.Label(mainframe, text="meters").grid(column=3, row=2, sticky=tkinter.W)

        for child in mainframe.winfo_children():
            child.grid_configure(padx=5, pady=5)

        feet_entry.focus()
        root.bind("<Return>", self.calculate)

    def calculate(self, *args):
        try:
            value = float(self.feet.get())
            self.meters.set(int(0.3048 * value * 10000.0 + 0.5) / 10000.0)
        except ValueError:
            pass


def main(argv):
    application_start_time = time.time()
    log_file_name = os.path.basename(__file__) + str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)

    LoggerConfig.printAndLogInfo('Start application')

    root = tkinter.Tk()
    app: M3uToFreeboxApplication = M3uToFreeboxApplication(root)
    root.mainloop()

    LoggerConfig.printAndLogInfo("End. Nominal end of application in " + date_time_formats.format_duration_to_string(
    time.time() - application_start_time))


if __name__ == "__main__":
    main(sys.argv[1:])
