# -*-coding:Utf-8 -*

""" https://tkdocs.com/tutorial/firstexample.html """
import random


import sys

import Dependencies.Logger.LoggerConfig as LoggerConfig
import Dependencies.Common.date_time_formats as date_time_formats


import time

import tkinter


import os

import Dependencies.Logger.LoggerConfig as LoggerConfig

import tkinter
import m3u
import detailsivew
import application


from tkinter import (
  filedialog, 
  simpledialog, 
  messagebox, 
  scrolledtext, 
  Menu,
  colorchooser,
  ttk
  )



class M3uToFreeboxMainView (tkinter.Tk):
    """ Main view of application """

    def __init__(self):
        super().__init__()        

        self.title("M3U to Freebox")

        self._m3u_to_freebox_application = None
        
        self._m3u_to_freebox_application

        self._create_menu()
        self._tab_control = ttk.Notebook(self)
        self.create_tab_list_details()
        self.create_tab_empty()
        self._tab_control.pack(expand = 1, fill ="both") 

        self._tab_control.pack()
       
        #self._create_main_frame()
        self._m3u_entries: list[m3u.M3uEntry] = []


    def create_tab_list_details(self):
        self._tab_list_details = detailsivew.DetailsViewTab(self._tab_control) 
        self._tab_control.add(self._tab_list_details, text ='List detail') 
        self._tab_list_details._parent = self

    def create_tab_empty(self):
        self._tab_empty = ttk.Frame(self._tab_control) 
        self._tab_control.add(self._tab_empty, text ='Empty') 

        
    def _create_menu(self):

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

        file_menu.add_command(label="Open", accelerator="Ctrl+o", command=self.menu_open_file)
        self.bind_all("<Control-o>", lambda x: self.menu_open_file())



    def menu_open_file(self):
        """ Open new file """
        LoggerConfig.printAndLogInfo("open menu executed")

        file_path = filedialog.askopenfilename(
            filetypes=[
                ("M3u", "*.m3u*"),
            ]
        )
        
        if file_path:
            LoggerConfig.printAndLogInfo("Open file:" + file_path)

            m3u_file_parser =  m3u.M3uFileParser()
            self._m3u_entries = m3u_file_parser.parse_file(file_path)
            self._tab_list_details.fill_m3u_entries(self._m3u_entries)
            
        else:
            LoggerConfig.printAndLogInfo("Open menu cancelled")


def main():
    """ Main function """
    application_start_time = time.time()
    log_file_name = os.path.basename(__file__) + str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)

    LoggerConfig.printAndLogInfo('Start application')

    main_view = M3uToFreeboxMainView()
    app: application.M3uToFreeboxApplication = application.M3uToFreeboxApplication(main_view)
    main_view.mainloop()

    LoggerConfig.printAndLogInfo("End. Nominal end of application in " + date_time_formats.format_duration_to_string(
    time.time() - application_start_time))


if __name__ == "__main__":
    # sys.argv[1:]
    main()
