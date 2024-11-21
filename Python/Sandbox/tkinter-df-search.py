import pandas as pd
from tkinter import ttk
from tkinter import filedialog

file = filedialog.askopenfilename() # show file dialog

df=pd.read_excel(file) # create dataframe
import tkinter as tk

my_w = tk.Tk()
my_w.geometry("620x350")  # width x height
my_w.title("plus2net.com")  # Adding a title
font1=['Times',28,'normal']
l1=tk.Label(my_w,text="Search", width=5,font=18)
l1.grid(row=1,column=1,padx=3,pady=10)
e1=tk.Entry(my_w,width=15,bg='yellow',font=font1)
e1.grid(row=1,column=2,padx=1)
b1=tk.Button(my_w,text='Save Result',bg='lightgreen', 
                font=18, command=lambda:my_save())
b1.grid(row=1,column=3,padx=2)
l2=tk.Label(my_w,text='',bg='lightgreen',anchor='w')
l2.grid(row=2,column=1,sticky='W',padx=3,pady=3)
l3=tk.Label(my_w,text='',bg='lightyellow',anchor='w')
l3.grid(row=2,column=2,columnspan=2,sticky='W',padx=3,pady=3)
b2=tk.Button(my_w,text='Copy',bg='lightblue',
             font=18,command=lambda:my_copy())
b2.grid(row=2,column=3,padx=2)

df2 = df.iloc[:0] 
def my_search(*args):
    global df2
    df2 = df.iloc[:0]  # one blank dataframe
    l1=list(df) # List of column names as header 
    query=e1.get().strip() 
    if query.isdigit():
        str1=df['id']==int(query)
    else:
        words=query.split(' ') # list of words 
        words_stop=['to','and','or']
        words=list(set(words)-set(words_stop))
        for w in words:
              #df2=df2.append(df[df.name.str.contains(w,case=False)],ignore_index=True)
              df2=pd.concat([df2,df[df.name.str.contains(w,case=False)]])
    df2=df2.drop_duplicates()
    l2.config(text='No of records: '+str(len(df2)))
    r_set=df2.to_numpy().tolist() # create list of list using rows
    trv=ttk.Treeview(my_w,selectmode='browse')
    trv.grid(row=3,column=1,columnspan=3,padx=10,pady=20)
    trv['height']=10
    trv['show']='headings'
    trv['columns']=l1
    for i in l1:
        trv.column(i,width=90,anchor='c')
        trv.heading(i,text=i)
    for dt in r_set:
        v=[r for r in dt]
        trv.insert("",'end',iid=v[0],values=v)
e1.bind("<KeyRelease>",my_search) 
def my_save():
    global df2
    file_path=filedialog.asksaveasfilename(filetypes=[('Excel File','.xlsx'),
                            ('CSV file','.csv')],defaultextension='.xlsx')
    if file_path: # user has not canceled the operation 
        if file_path.endswith('.csv'):
            df2.to_csv(file_path,index=False)
        else:
            df2.to_excel(file_path,index=False)
def my_copy():
    df2.to_clipboard()
    l3.config(text='Copied')
    l3.after(3000,lambda:l3.config(text=''))
my_w.mainloop()
