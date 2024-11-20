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

        self._create_menu()
        self._tab_control = ttk.Notebook(self)
        self.create_tab_list_details()
        self.create_tab_empty()
        self._tab_control.pack(expand = 1, fill ="both") 

        
        self._tab_control = None
        self._tab_list_details = None
        self._tab_empty = None
        
        #self._create_main_frame()
        self._m3u_entries = None


    def create_tab_list_details(self):
        self._tab_list_details = ttk.Frame(self._tab_control) 
        self._tab_control.add(self._tab_list_details, text ='List details') 
        self._create_tree_view()

    def create_tab_empty(self):
        self._tab_empty = ttk.Frame(self._tab_control) 
        self._tab_control.add(self._tab_empty, text ='Empty') 



    """ 
    def _create_main_frame(self):

        
        self.mainframe = tkinter.ttk.Frame(self, padding="3 3 12 12")
        self.mainframe.grid(column=0, row=0, sticky=(tkinter.N, tkinter.W, tkinter.E, tkinter.S))
        self.mainframe.columnconfigure(0, weight=1)
        self.mainframe.rowconfigure(0, weight=1)

        self._create_tree_view()

        self.feet = tkinter.StringVar()
        feet_entry = tkinter.ttk.Entry(self.mainframe, width=7, textvariable=self.feet)
        feet_entry.grid(column=2, row=1, sticky=(tkinter.W, tkinter.E))
        self.mainframe.meters = tkinter.StringVar()

        tkinter.ttk.Label(self.mainframe, textvariable=self.mainframe.meters).grid(column=2, row=2, sticky=(tkinter.W, tkinter.E))
        tkinter.ttk.Button(self.mainframe, text="Calculate", command=self.calculate).grid(column=3, row=3, sticky=tkinter.W)

        tkinter.ttk.Label(self.mainframe, text="feet").grid(column=3, row=1, sticky=tkinter.W)
        tkinter.ttk.Label(self.mainframe, text="is equivalent to").grid(column=1, row=2, sticky=tkinter.E)
        tkinter.ttk.Label(self.mainframe, text="meters").grid(column=3, row=2, sticky=tkinter.W)

        for child in self.mainframe.winfo_children():
            child.grid_configure(padx=5, pady=5)

        feet_entry.focus()
        self.bind("<Return>", self.calculate)
        
        self.mainframe.pack()
        self.tree_view.pack()
     """
        
    def _create_tree_view(self):
                
        options = ['A','B','C','D','E','F','G','H']
        selected = tkinter.StringVar(self._tab_list_details)
        selected.set(options[0])
        
        combobox = ttk.Combobox(self._tab_list_details, textvariable=selected, values=options,font=('verdana',14))
                
        self.tree_view = ttk.Treeview(self._tab_list_details, columns=(1,2,3,4), show='headings')


        self.tree_view.column(1, anchor='center', width=100)
        self.tree_view.column(2, anchor='center', width=100)
        self.tree_view.column(3, anchor='center', width=100)
        self.tree_view.column(4, anchor='center', width=100)

        self.tree_view.heading(1, text='ID')
        self.tree_view.heading(2, text='Name')
        self.tree_view.heading(3, text='Quantity')
        self.tree_view.heading(4, text='Price')

        #self.tree_view.pack()
        
   
        
        combobox.grid(row=0, column=0)
        self.tree_view.grid(row=1, column=0)

        #self._tab_list_details.grid(row=0, column=0)
        
        
        # add items to the treeview

        
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
        LoggerConfig.printAndLogInfo("open menu executed")

        current_widget = self.focus_get()
        file_path = filedialog.askopenfilename(
            filetypes=[
                ("M3u", "*.m3u*"),
            ]
        )
        
        if file_path:
            LoggerConfig.printAndLogInfo("Open file:" + file_path)

            m3u_file_parser =  m3u.M3uFileParser()
            self._m3u_entries = m3u_file_parser.parse_file(file_path)
            self._fill_m3u_entries()
            
        else:
            LoggerConfig.printAndLogInfo("Open menu cancelled")

    def _fill_m3u_entries(self):
        m3u_entry_number = 0
        
        """self.tree_view.insert("",'end', iid=1, values=(1,"Product 1",100, 10))
        self.tree_view.insert("",'end', iid=2, values=(2,"Product 2",200, 20))
        self.tree_view.insert("",'end', iid=3, values=(3,"Product 3",300, 30))
        self.tree_view.insert("",'end', iid=4, values=(4,"Product 4",400, 400))
        self.tree_view.insert("",'end', iid=5, values=(5,"Product 5",500, 50))
        self.tree_view.insert("",'end', iid=6, values=(6,"Product 6",600, 60))"""
        
      
        
        for m3u_entry in self._m3u_entries:
            m3u_entry_number = m3u_entry_number + 1
            self.tree_view.insert("",'end', iid=m3u_entry_number, values=(m3u_entry_number,m3u_entry.title,m3u_entry.title, m3u_entry.group_title))

            if m3u_entry_number % 100 == 0:
                LoggerConfig.printAndLogInfo(str(m3u_entry_number) + " entries filled")
        
        
        #self.tree_view.pack()
        
        #self.pack()
        

    def calculate(self, *args):
        try:
            value = float(self.feet.get())
            self.mainframe.meters.set(int(0.3048 * value * 10000.0 + 0.5) / 10000.0)
        except ValueError:
            pass


    def get_m3uToFreeboxApplication(self):
        return self._m3u_to_freebox_application

    def set_m3uToFreeboxApplication(self, value):
        self._m3u_to_freebox_application = value


class M3uToFreeboxApplication():

    def __init__(self, m3uToFreeboxMainView):
        self.m3uToFreeboxMainView = m3uToFreeboxMainView


def main(argv):
    application_start_time = time.time()
    log_file_name = os.path.basename(__file__) + str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)

    LoggerConfig.printAndLogInfo('Start application')

    main_view = M3uToFreeboxMainView()
    app: M3uToFreeboxApplication = M3uToFreeboxApplication(main_view)
    main_view.mainloop()

    LoggerConfig.printAndLogInfo("End. Nominal end of application in " + date_time_formats.format_duration_to_string(
    time.time() - application_start_time))


if __name__ == "__main__":
    main(sys.argv[1:])
