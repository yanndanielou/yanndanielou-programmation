# -*-coding:Utf-8 -*

""" https://tkdocs.com/tutorial/firstexample.html """
import random


import sys

import Dependencies.Logger.logger_config as logger_config
import Dependencies.Common.date_time_formats as date_time_formats


import time

import tkinter


import os

import tkinter
import m3u


from tkinter import (
  filedialog, 
  simpledialog, 
  messagebox, 
  scrolledtext, 
  Menu,
  colorchooser,
  ttk
  )


class M3uEntryDetailPopup(tkinter.Toplevel):
  """ To show all details """
  def __init__(self, main_window, m3u_entry: m3u.M3uEntry) -> None:
    super().__init__(main_window)

    self._m3u_entry = m3u_entry
    self.geometry("750x250")
    self.title("M3u entry " + self._m3u_entry._original_raw_title)
    tkinter.Label(self, text= self._m3u_entry._original_raw_title, font=('Mistral 18 bold')).place(x=150,y=80)

