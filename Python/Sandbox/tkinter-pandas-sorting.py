from tkinter import ttk
import tkinter as tk
from tkinter import filedialog

my_w = tk.Tk()
my_w.geometry("560x310") 
my_w.title("www.plus2net.com")  

import pandas as pd  # Pandas library 
#df = pd.read_excel('D:\student.xlsx')  # create dataframe 
order=True # To set the sorting to ascending or descending

df = pd.DataFrame() # empty DataFrame object
b1 = tk.Button(my_w, text='Select File', bg='lightgreen',
   width=20,command = lambda:browse_file())
b1.grid(row=1,column=1,padx=30,pady=5) 
l1_dlt=tk.Label(my_w,text='',bg='lightgreen')
l1_dlt.grid(row=1,column=2)

l1_path=tk.Label(my_w,text='',bg='yellow')
l1_path.grid(row=1,column=3,columnspan=2)

def browse_file(): # user selection of file to create dataframe
    global df # access the dataframe
    file = filedialog.askopenfilename() # show file dialog
    if file: # user selected one file
       l1_path.config(text=file) # display the selected file path
       df = pd.read_excel(file)  # create dataframe  
       my_disp()
    else: # user cancel the file browser window
        l1_path.config(text='No file is selected')

def my_disp(): # display the Treeview with data
    global df 
    l1=list(df) # List of column names as headers 
    r_set=df.to_numpy().tolist() # Create list of list using rows 
    trv = ttk.Treeview(my_w, selectmode ='browse',
                       show='headings',height=10,columns=l1)
    trv.grid(row=2,column=1,columnspan=4,padx=15,pady=10)
    for col in l1:
        trv.column(col, width = 100, anchor ='w')
        trv.heading(col, text =col,command=lambda col=col :my_sort(col))

    ## Adding data to treeview 
    for dt in r_set:  
        v=[r for r in dt] # creating a list from each row 
        trv.insert("",'end',iid=v[0],values=v) # adding row
    l1_dlt.config(text= "Rows:"+str(df.shape[0])+" Cols:"+str(df.shape[1]))
    vs = ttk.Scrollbar(my_w,orient="vertical", command=trv.yview)#V Scrollbar
    trv.configure(yscrollcommand=vs.set)  # connect to Treeview
    vs.grid(row=2,column=5,sticky='ns')
def my_sort(col): # Update the dataframe after sorting
    global df,order 
    if order:
        order=False # set ascending value
    else:
        order=True
    
    print("col:" + str(col))
    df=df.sort_values(by=[col],ascending=order)
    my_disp() # refresh the Treeview 

my_w.mainloop()