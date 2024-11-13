import subprocess,re
def shell(x):
    print("="*75)
    print(">",x)
    out = subprocess.call(re.split(" ",x))
    print("<",out)
    print("-"*75)
    return out

#https://www.tutorialspoint.com/python3/python_gui_programming.htm
#https://likegeeks.com/python-gui-examples-tkinter-tutorial/
example = 3

if example==0: #blank tkinter frame
    import tkinter # note that module name has changed from Tkinter in Python 2 to tkinter in Python 3
    window = tkinter.Tk()
    window.title("New Window")
    window.geometry('350x200')    
    # Code to add widgets will go here...
    window.mainloop()

if example==1: #example with canvas and drawing
    from tkinter import *
    from tkinter import messagebox
    top = Tk()
    C = Canvas(top, bg = "blue", height = 250, width = 300)
    coord = 10, 50, 240, 210
    arc = C.create_arc(coord, start = 0, extent = 150, fill = "red")
    line = C.create_line(10,10,200,200,fill = 'white')
    C.pack()
    top.mainloop()

if example==2: #example with label
    from tkinter import *
    window = Tk()
    window.title("Welcome to LikeGeeks app")
    lbl = Label(window, text="Hello, World!", font=("Arial Bold", 50))
    lbl.grid(column=0, row=0)
    window.mainloop()

if example==3:
    from tkinter import *
    window = Tk()
    window.title("Welcome to LikeGeeks app")
    window.geometry('350x200')
    lbl = Label(window, text="Hello")
    lbl.grid(column=0, row=0)
    btn = Button(window, text="Click Me")
    btn.grid(column=1, row=0)
    window.mainloop()
