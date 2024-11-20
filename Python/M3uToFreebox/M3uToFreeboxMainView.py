# -*-coding:Utf-8 -*


import tkinter

from tkinter import ttk

class M3uToFreeboxMainView (tkinter.Tk):
    """ Main view of application """


    def __init__(self):
        super().__init__()        

        self._m3uToFreeboxApplication = None

        self._create_menu()
        self._create_main_frame()

    def _create_main_frame(self):

        self.title("M3U to Freebox")

        self.mainframe = tkinter.ttk.Frame(self, padding="3 3 12 12")
        self.mainframe.grid(column=0, row=0, sticky=(tkinter.N, tkinter.W, tkinter.E, tkinter.S))
        self.mainframe.columnconfigure(0, weight=1)
        self.mainframe.rowconfigure(0, weight=1)

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

        file_menu.add_command(label="Open", command=None)


    def calculate(self, *args):
        try:
            value = float(self.feet.get())
            self.mainframe.meters.set(int(0.3048 * value * 10000.0 + 0.5) / 10000.0)
        except ValueError:
            pass


    def get_m3uToFreeboxApplication(self):
        return self._m3uToFreeboxApplication

    def set_m3uToFreeboxApplication(self, value):
        self._m3uToFreeboxApplication = value
