# coding: utf-8

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

import time

import os


class M3uToFreeboxMainView(tkinter.Tk):



    def __init__(self):
        tkinter.Tk.__init__(self)

        self.m3uToFreeboxApplication = None




        self.title("M3U to Freebox")

        menu_bg = "black"
        menu_fg = "lightgrey"


        submenu_bg = "black"
        submenu_fg = "lightgrey"

        menu_bar = tkinter.Menu(self)
        self.config(menu=menu_bar)
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

        mainframe = ttk.Frame(self, padding="3 3 12 12")
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
        self.bind("<Return>", self.calculate)

    def calculate(self, *args):
        try:
            value = float(self.feet.get())
            self.meters.set(int(0.3048 * value * 10000.0 + 0.5) / 10000.0)
        except ValueError:
            pass


    def get_m3uToFreeboxApplication(self):
        return self.m3uToFreeboxApplication

    def set_m3uToFreeboxApplication(self, value):
        self.m3uToFreeboxApplication = value
