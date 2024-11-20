import tkinter as tk
from tkinter import *
from tkinter import ttk
import mysql.connector

root = Tk()
root.title("Search, Filter And Display Data")

connection = mysql.connector.connect(host='localhost', user='root', password='',
                                     port='3306', database='test_py')
c = connection.cursor()

bkg = "grey"

frame = tk.Frame(root, bg=bkg)

options = ['A','B','C','D','E','F','G','H']
selected = StringVar(frame)
selected.set(options[0])

combobox = ttk.Combobox(frame, textvariable=selected, values=options,
                        font=('verdana',14))

trv = ttk.Treeview(frame, columns=(1,2,3,4,5), height=15, show="headings")

trv.column(1, anchor=CENTER, stretch=NO, width=100)
trv.column(2, anchor=CENTER, stretch=NO, width=100)
trv.column(3, anchor=CENTER, stretch=NO, width=100)
trv.column(4, anchor=CENTER, stretch=NO, width=100)
trv.column(5, anchor=CENTER, stretch=NO, width=100)

trv.heading(1, text="ID")
trv.heading(2, text="First Name")
trv.heading(3, text="Last Name")
trv.heading(4, text="Email")
trv.heading(5, text="Age")

def displayData():
    c.execute('SELECT * FROM `users_2`')
    users = c.fetchall()

    for user in users:
        trv.insert('','end', value=(user[0], user[1], user[2], user[3], user[4]))

def search(eventObject):
    # clear treeview
    for item in trv.get_children():
        trv.delete(item)

    val = combobox.get()
    c.execute("""SELECT * FROM `users_2` WHERE `firstname` LIKE %s or 
               `lastname` like %s""",("%"+val+"%","%"+val+"%"))
    users = c.fetchall()

    for row in users:
        trv.insert('',END, values=row)

combobox.bind("<<ComboboxSelected>>", search)

combobox.grid(row=0, column=0)
trv.grid(row=1, column=0)

frame.grid(row=0, column=0)

displayData()

root.mainloop()