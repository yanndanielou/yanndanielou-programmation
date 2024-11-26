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
import detailspopup
import main



from tkinter import (
  filedialog, 
  simpledialog, 
  messagebox, 
  scrolledtext, 
  Menu,
  colorchooser,
  ttk
  )

import main_view

class DetailsViewTab(ttk.Frame):

    
    def __init__(self, parent:main_view.M3uToFreeboxMainView, tab_control):
        super().__init__(tab_control)

        self._parent: main_view.M3uToFreeboxMainView = parent

        self._create_view()
        self._create_context_menu()
        self.create_filter_option_menu()


    def create_filter_option_menu(self):
        """ Filter """
        
        
        # padding for widgets using the grid layout
        paddings = {'padx': 5, 'pady': 5}
        
        
        # output label
        self.filter_option_description_label = ttk.Label(self, foreground='black')
        self.filter_option_description_label.grid(column=1, row=0, sticky=tkinter.W, **paddings)
        self.filter_option_description_label['text'] = f'Type of fikter:'
        
        # initialize data
        self.languages = ('Contains', 'Regex', 'Java',
                        'Swift', 'GoLang', 'C#', 'C++', 'Scala')

        # set up variable
        self.option_var = tkinter.StringVar(self)
        # option menu
        self._filter_option_menu = ttk.OptionMenu(
            self,
            self.option_var,
            self.languages[0],
            *self.languages,
            command=self.filter_option_changed)
        
        
        self._filter_option_menu.grid(column=2, row=0, sticky=tkinter.W, **paddings)
        

        
    def filter_option_changed(self, *args):
        LoggerConfig.printAndLogInfo(f'You selected: {self.option_var.get()}')

        
    
    def _create_context_menu(self):
        #Create context menu
        self.tree_view_context_menu = tkinter.Menu(self, tearoff=0)
        self.tree_view_context_menu.add_command(label="Next", command=self.selection)
        self.tree_view_context_menu.add_command(label="Show detail", command=self._open_m3u_entry_detail_popup)
        self.tree_view_context_menu.add_command(label="Reset", command=self._reset_list)
        self.tree_view_context_menu.add_separator()

        def do_popup(event):
            # display the popup menu
            try:
                self.tree_view_context_menu.selection = self.tree_view.set(self.tree_view.identify_row(event.y))
                self.tree_view_context_menu.post(event.x_root, event.y_root)
            finally:
                # make sure to release the grab (Tk 8.0a1 only)
                self.tree_view_context_menu.grab_release()
                
        self.tree_view.bind("<Button-3>", do_popup)


    def _open_m3u_entry_detail_popup(self):
        m3u_entry_line = self.tree_view_context_menu.selection
        m3u_entry_detail_popup = detailspopup.M3uEntryDetailPopup(self, None)
        
    
    def _create_view(self):
                
        options = ['A','B','C','D','E','F','G','H']
        selected = tkinter.StringVar(self)
        selected.set(options[0])
        
        self._filter_input_text = tkinter.Entry(self,font=('verdana',14))
        self._filter_input_text.bind("<KeyRelease>", self.filter_updated)

                
        columns = ('ID','Title', 'Group')

        self.tree_view = ttk.Treeview(self, columns=columns, show='headings')
        
        for col in columns:
            self.tree_view.heading(col, text=col, command=lambda: \
                     self.treeview_sort_column(self.tree_view, col, False), anchor='center')


        #self.tree_view.pack()
        
   
        
        self._filter_input_text.grid(row=0, column=0)
        self.tree_view.grid(row=1, column=0)
        
        self.create_scrollbar()
        
        # create a StringVar class
        my_string_var = tkinter.StringVar()

        # set the text
        my_string_var.set("What should I learn")

        bottom_status_label = tkinter.Label(self,font=('verdana',14),textvariable = my_string_var)
        bottom_status_label.grid(row=2, column=0)


      
    def create_scrollbar(self):  
        #self._tab_list_details.grid(row=0, column=0)
        
        # Create a Scrollbar
        scrollbar = ttk.Scrollbar(self, orient="vertical", command=self.tree_view.yview)

        # Configure the Treeview to use the scrollbar
        self.tree_view.configure(yscrollcommand=scrollbar.set)

        # Place the scrollbar on the right side of the Treeview
        #scrollbar.pack(side="right", fill="y")
        scrollbar.grid(row=1, column=1)

    def filter_updated(self, *args):
        filter_text = self._filter_input_text.get()
        self.fill_m3u_entries()


    def treeview_sort_column(self, tv, col, reverse):
        """ Sort """
        l = [(tv.set(k, col), k) for k in tv.get_children('')]
        l.sort(reverse=reverse)

        # rearrange items in sorted positions
        for index, (val, k) in enumerate(l):
            tv.move(k, '', index)

        # reverse sort next time
        tv.heading(col, command=lambda: \
                self.treeview_sort_column(tv, col, not reverse))

        @property
        def parent(self) -> main_view.M3uToFreeboxMainView:
            return self._parent

        @parent.setter
        def parent(self, value: main_view.M3uToFreeboxMainView):
            self._parent = value


    def _reset_list(self):
        self.tree_view.delete(* self.tree_view.get_children())


    def fill_m3u_entries(self):
        """ fill_m3u_entries """
        self._reset_list()
        m3u_entry_number = 0
        
        for m3u_entry in self._parent.m3u_to_freebox_application.m3u_library.get_m3u_entries_with_filter(self._filter_input_text.get()):
            m3u_entry_number = m3u_entry_number + 1
            self.tree_view.insert("",'end', iid=m3u_entry.id, values=(m3u_entry.id,m3u_entry.title, m3u_entry.group_title))

            if m3u_entry_number % 10000 == 0:
                LoggerConfig.printAndLogInfo(str(m3u_entry_number) + " entries filled")
        
        
    def selection(self):
        print(self.tree_view_context_menu.selection)
        

if __name__ == "__main__":
    # sys.argv[1:]
    main.main()
