# -*-coding:Utf-8 -*

"""
https://1bestcsharp.blogspot.com/2023/07/python-get-treeview-column-max-min-sum-avg-value.html
https://www.youtube.com/watch?v=LTaZMq2fvl4
"""

import tkinter as tk
from tkinter import *
from tkinter import ttk


root = Tk()
root.title("MAX-MIN-SUM-AVG")

frame_container = tk.Frame(root, padx=10, pady=10, bg='#badc58')
frame_fields = tk.Frame(frame_container, bg='#badc58')

# create treeview
trv = ttk.Treeview(frame_container, columns=(1,2,3,4), show='headings')

trv.column(1, anchor='center', width=100)
trv.column(2, anchor='center', width=100)
trv.column(3, anchor='center', width=100)
trv.column(4, anchor='center', width=100)

trv.heading(1, text='ID')
trv.heading(2, text='Name')
trv.heading(3, text='Quantity')
trv.heading(4, text='Price')

# add items to the treeview
trv.insert("",'end', iid=1, values=(1,"Product 1",100, 10))
trv.insert("",'end', iid=2, values=(2,"Product 2",200, 20))
trv.insert("",'end', iid=3, values=(3,"Product 3",300, 30))
trv.insert("",'end', iid=4, values=(4,"Product 4",400, 400))
trv.insert("",'end', iid=5, values=(5,"Product 5",500, 50))
trv.insert("",'end', iid=6, values=(6,"Product 6",600, 60))

# sum value
sum_lbl = tk.Label(frame_fields, text='Sum:', font=('Verdana',14), bg='#badc58')
sum_entry = tk.Entry(frame_fields, font=('Verdana',14))

# average value
avg_lbl = tk.Label(frame_fields, text='Average:', font=('Verdana',14), bg='#badc58')
avg_entry = tk.Entry(frame_fields, font=('Verdana',14))

# minimum value
min_lbl = tk.Label(frame_fields, text='Min:', font=('Verdana',14), bg='#badc58')
min_entry = tk.Entry(frame_fields, font=('Verdana',14))

# maximum value
max_lbl = tk.Label(frame_fields, text='Max:', font=('Verdana',14), bg='#badc58')
max_entry = tk.Entry(frame_fields, font=('Verdana',14))

sum_lbl.grid(row=0, column=0, padx=10, pady=10, sticky='e')
sum_entry.grid(row=0, column=1)

avg_lbl.grid(row=1, column=0, padx=10, pady=10, sticky='e')
avg_entry.grid(row=1, column=1)

min_lbl.grid(row=2, column=0, padx=10, pady=10, sticky='e')
min_entry.grid(row=2, column=1)

max_lbl.grid(row=3, column=0, padx=10, pady=10, sticky='e')
max_entry.grid(row=3, column=1)


# function to display the sum value
def getSum(item=""):
    val = 0
    for row in trv.get_children(item):
        #print(trv.item(row)["values"][3])# print price
        val = val + trv.item(row)["values"][3]
    print(val)
    sum_entry.insert(0,val)


# function to display the average value
def getAvg(item=""):
    val = 0
    for row in trv.get_children(item):
        #print(trv.item(row)["values"][3])# print price
        val = val + trv.item(row)["values"][3]

    val = val/len(trv.get_children())
    avg_entry.insert(0,val)


# function to display the minimum value
def getMin():
    val = trv.item("1")["values"][3] # get the first value
    for row in trv.get_children():
        #print(trv.item(row)["values"][3])# print price
        if val > trv.item(row)["values"][3]:
           val = trv.item(row)["values"][3]
    
    min_entry.insert(0,val)


# function to display the maximum value
def getMax():
    val = trv.item("1")["values"][3] # get the first value
    for row in trv.get_children():
        #print(trv.item(row)["values"][3])# print price
        if val < trv.item(row)["values"][3]:
           val = trv.item(row)["values"][3]
    
    max_entry.insert(0,val)



getSum()
getAvg()
getMin()
getMax()

trv.pack()
frame_container.pack()
frame_fields.pack()

root.mainloop()