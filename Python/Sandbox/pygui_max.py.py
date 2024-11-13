
import traceback
import builtins
import webbrowser
import re
import sys

import os
import io
import math
import threading
import subprocess
import tkinter as tk

from tkinter import (
  filedialog, 
  simpledialog, 
  messagebox, 
  scrolledtext, 
  Menu,
  colorchooser
  )

import random
import requests
from bs4 import BeautifulSoup
import textwrap
#import black
from googlesearch import search 




def random_all_theme():
	red_root = random.randint(0,255)
	green_root = random.randint(0,255)
	blue_root = random.randint(0,255)
	root_bg = "#{:02x}{:02x}{:02x}".format(red_root,green_root,blue_root)
	root.config(bg=root_bg)
	
	
	
	random_dark_theme()
	



def generate_words():
    current_widget = root.focus_get()
    prefix = current_widget.get(1.0, "1.end").strip()
    
    file_path = 'wordnet_word_list.txt'
    
    with open(file_path, 'r') as file:
        content = file.read()
        word_list = content.split()
    
    matching_words = [w for w in word_list if w.startswith(prefix)]
    
    text_entry.delete("1.end", tk.END)
    text_entry.insert("1.end", '\n\n'.join(matching_words))



def go_to_begingning():
	current_widget = root.focus_get()
	current_widget.mark_set(tk.INSERT, 1.0)
	current_widget.see(tk.INSERT)
	

def go_to_end():
	current_widget = root.focus_get()
	current_widget.mark_set(tk.INSERT, tk.END)
	current_widget.see(tk.INSERT)

			



def quick_hex(r,g,b):
	color = "#{:02x}{:02x}{:02x}".format(r,g,b)
	return color       	 


def rgb_machine():
    def use_colors():
    	background_c = text_widget.cget('bg')
    	foreground_c = text_widget.cget('fg')
    	text_entry.config(fg=foreground_c, bg=background_c)   	

    def quick_hex(r, g, b):
        return "#{:02x}{:02x}{:02x}".format(r, g, b)

    def rgb_from_hex(hex_color):
        hex_color = hex_color.lstrip('#')
        if len(hex_color) == 6:
            r, g, b = [int(hex_color[i:i+2], 16) for i in (0, 2, 4)]
            return r, g, b
        return None

    def update_from_rgb(*args):
        color_text = rgb_entry.get()
        try:
            r, g, b = map(int, color_text.split(','))
            hex_color = quick_hex(r, g, b)
            text_widget.config(bg=hex_color)
            hex_entry.delete(0, tk.END)
            hex_entry.insert(0, hex_color)
        except ValueError:
            text_widget.config(bg="white")
            hex_entry.delete(0, tk.END)
            hex_entry.insert(0, "Invalid input")

    def update_from_hex(*args):
        hex_color = hex_entry.get()
        try:
            r, g, b = rgb_from_hex(hex_color)
            if r is not None:
                text_widget.config(bg=hex_color)
                rgb_entry.delete(0, tk.END)
                rgb_entry.insert(0, f"{r},{g},{b}")
            else:
                text_widget.config(bg="white")
                rgb_entry.delete(0, tk.END)
                rgb_entry.insert(0, "Invalid hex")
        except ValueError:
            text_widget.config(bg="white")
            rgb_entry.delete(0, tk.END)
            rgb_entry.insert(0, "Invalid hex")

    def copy_selection():
        current_widget = root.focus_get()
        selected_text = current_widget.selection_get()
        root.clipboard_clear()
        root.clipboard_append(selected_text)

    def update_color():
        
        red = red_slider.get()
        green = green_slider.get()
        blue = blue_slider.get()
        hex_color = quick_hex(red, green, blue)
        text_widget.config(bg=hex_color)
        rgb_entry.delete(0, tk.END)
        rgb_entry.insert(0, f"{red},{green},{blue}")
        hex_entry.delete(0, tk.END)
        hex_entry.insert(0, hex_color)
        text_widget.delete(1.0, "2.end")
        text_widget.insert(1.0, f"bg = {text_widget.cget('bg')}\nfg = {text_widget.cget('fg')}")

    def randomize_bg():
        red_bg = random.randint(0, 255)
        green_bg = random.randint(0, 255)
        blue_bg = random.randint(0, 255)
        background_c = quick_hex(red_bg, green_bg, blue_bg)

        
        
        text_widget.config(bg=background_c)
        rgb_entry.delete(0, tk.END)
        rgb_entry.insert(0, f"{red_bg},{green_bg},{blue_bg}")
        hex_entry.delete(0, tk.END)
        hex_entry.insert(0, background_c)
        red_slider.set(red_bg)
        green_slider.set(green_bg)
        blue_slider.set(blue_bg)
        text_widget.delete(1.0, "2.end")
        text_widget.insert(1.0, f"bg = {background_c}\nfg = {text_widget.cget('fg')}")
        

    def randomize_fg():

        red_fg = random.randint(0, 255)
        green_fg = random.randint(0, 255)
        blue_fg = random.randint(0, 255)
        foreground_c = quick_hex(red_fg, green_fg, blue_fg)        
        text_widget.config(fg=foreground_c)
        text_widget.delete(1.0, "2.end")
        text_widget.insert(1.0, f"bg = {text_widget.cget('bg')}\nfg = {foreground_c}")   


    root = tk.Tk()
    root.title("Color Converter")
    root.geometry("690x500+0+360")
    

    text_frame = tk.Frame(root)
    text_frame.pack(pady=10, fill=tk.BOTH, expand=True)

    text_widget = tk.scrolledtext.ScrolledText(
        text_frame, 
        wrap=tk.WORD,
        font=("Courier New", 8),
        width=20, 
        height=3, 
        padx=10, 
        pady=12,
        bd=10
    )
    text_widget.place(x=20, y=10)
    text_widget.config(insertbackground="blue", insertwidth=6, bg="#ff0000")

    entry_frame = tk.Frame(root)
    entry_frame.pack(pady=10)



    rgb_entry = tk.Entry(
        entry_frame, 
        width=13, 
        font=("Courier New", 8)
    )
    rgb_entry.pack(
        side=tk.TOP, 
        padx=5, 
        pady=5
    )
    rgb_entry.bind("<KeyRelease>", update_from_rgb)
    rgb_entry.config(
        insertbackground="blue",
        insertwidth=6
    )
    rgb_entry.insert(0, "255,0,0")

    hex_label = tk.Label(
        entry_frame, 
        text="[r,g,b ↑]  [hex# →]", 
        font=("Courier New", 8)
    )
    hex_label.pack(
        side=tk.LEFT, 
        padx=5, 
        pady=5
    )

    hex_entry = tk.Entry(
        entry_frame, 
        width=10, 
        font=("Courier New", 8)
    )
    hex_entry.config(
        insertbackground="darkgreen",
        insertwidth=6
    )
    hex_entry.pack(
         side=tk.TOP, 
         padx=5, 
         pady=5
    )
    hex_entry.bind("<KeyRelease>", update_from_hex)
    hex_entry.insert(0, "#00ffff")



    copy_button = tk.Button(
        root, 
        text="Copy",
        font=("Times", 9, "bold"),
        command=copy_selection,
        bd=6, 
        width=2
    )
    copy_button.place(
        x=550, 
        y=20
    )
    
    set_button = tk.Button(
        root, 
        text="Use",
        font=("Times", 9, "bold"),
        command=use_colors, 
        bd=6,
        width=2
    )
    set_button.place(
        x=550, 
        y=100
    )
    
    
    
    

    bg_button = tk.Button(
        root, 
        text="bg",
        font=("Times", 9, "bold"),
        command=randomize_bg,
        bd=6, 
        width=2
    )
    bg_button.place(
        x=550, 
        y=180
    )

    fg_button = tk.Button(
        root, 
        text="fg",
        font=("Times", 9, "bold"),
        command=randomize_fg,
        bd=6,
        width=2
    )
    fg_button.place(
        x=550, 
        y=260
        )   
    

    red_slider = tk.Scale(
        root, 
        from_=0, 
        to=255, 
        orient=tk.VERTICAL,
        command=lambda val: update_color(), 
        width=30
    )
    red_slider.place(x=10, y=200)
    red_slider.set(0)

    green_slider = tk.Scale(root, from_=0, to=255, orient=tk.VERTICAL, command=lambda val: update_color(), width=30)
    green_slider.place(x=120, y=200)
    green_slider.set(255)

    blue_slider = tk.Scale(root, from_=0, to=255, orient=tk.VERTICAL, command=lambda val: update_color(), width=30)
    blue_slider.place(x=230, y=200)
    blue_slider.set(255)

    root.mainloop()
    


def cut_above():
	current_widget= root.focus_get()
	current_widget.delete(1.0, tk.INSERT)
	
	
def cut_below():
	current_widget= root.focus_get()
	current_widget.delete(tk.INSERT, tk.END)
	

window_expanded=0
def expand_window():
	global window_expanded
	window_expanded+=1
	if window_expanded>1:
		window_expanded=0
		
	if window_expanded:
		root.geometry("700x1200+0+0")
		
	else:
		root.geometry("700x800+0+0")




def go_to_string():
    current_widget = root.focus_get()
    current_bg_color = current_widget.cget("bg")
    current_fg_color = current_widget.cget("fg")
    
    text_to_find = simpledialog.askstring(
        "Go To String", "Enter the string to go to:"
    )
    if text_to_find:
        index = "1.0"
        while index:
            index = text_entry.search(text_to_find, index, stopindex=tk.END)
            if index:
                text_entry.mark_set("insert", index)
                text_entry.see(index)
                
                text_entry.tag_add("highlight_line", "insert linestart", "insert lineend+1c")
                text_entry.tag_config("highlight_line", background=current_fg_color, foreground=current_bg_color)
                break  # Stop searching after the first occurrence is found




def read_only_mode():
        text_entry.config(state=tk.DISABLED)
        


def edit_mode():
        text_entry.config(state=tk.NORMAL)        
        




def view_file_at_cursor():
    current_widget = root.focus_get()
    
    file = current_widget.get(
        f"{current_widget.index('insert linestart')}",
        f"{current_widget.index('insert lineend')}",
    ).strip()
    
    if file:
        global_entry.delete(1.0, tk.END)
        global_entry.insert(1.0, f"{file}")
        try:
            with open(file, "r") as file:
                content = file.read()
            current_widget.delete("1.0", tk.END)
            current_widget.insert("1.0", content)     
            
            current_widget.focus_set()
            
            

            
        except:
        	pass





def quick_save_file(event=None):
    page = text_entry.get(1.0, tk.END)
    
    file_path = global_entry.get(
        f"{global_entry.index('insert linestart')}",
        f"{global_entry.index('insert lineend')}",
    ).strip()
    
    if file_path and len(page) > 1:
        confirmed = messagebox.askyesno("Save Confirm", f"Save Page Content as {file_path}")
        
        if confirmed:
        	content = text_entry.get("1.0", tk.END)
        	with open(file_path, "w") as file:
        	   file.write(content)
        	   
        	   
        	   global_entry.config(fg=quick_hex(random.randint(170,255),random.randint(170,255),random.randint(170,255)))
 
    else:
     
        save_file() 





unix_words = ["clear","awk", "bc", "cat", "cd", "chmod", "chown", "cp", "date", "df", "echo", "find", "grep", "gunzip", "head", "install", "ls", "mkdir", "mv", "pip", "ps", "pwd", "rm", "rmdir", "sed", "sort", "tail", "tar", "top", "uniq", "wc", "wget"]



def detect_command(event=None):
		
	current_widget=root.focus_get()
	command = str(current_widget.get(f"{current_widget.index('insert linestart')}", f"{current_widget.index('insert lineend')}")).strip().split()
	if "clear" in command:
		text_entry.delete(1.0, tk.END)
	if ":o" in command:
		open_file()
	if ":s" in command:
		save_file()
	if "clr" in command:
		text_entry.delete(1.0, tk.END)
		
			
	
	for w in unix_words:
		if w in command:
			subprocess_command()
	
	
				
	

	

def list_files(event=None):
    current_widget = root.focus_get()
    
        
    cursor_index = current_widget.index(tk.INSERT)
    cursor_line = cursor_index.split('.')[0]
    cursor_line_start = f"{cursor_line}.0"
    cursor_line_end = f"{cursor_line}.end"
    line_content = current_widget.get(cursor_line_start, cursor_line_end).strip()
    letter = line_content.strip()
    process = subprocess.Popen(
        ["ls"], stdout=subprocess.PIPE, stderr=subprocess.PIPE, shell=True
    )
    output, _ = process.communicate()
    command_list = output.decode().splitlines()
    filtered_commands = [cmd for cmd in command_list if cmd.startswith(letter)]
    if filtered_commands:
        text_entry.delete(2.0, tk.END)        
     
        for cmd in filtered_commands:
            
            text_entry.insert(tk.END, f"\n{cmd}")
    else:
        pass
    text_entry.see("1.0")
    text_entry.mark_set(tk.INSERT, "1.end")

	
	

def subprocess_command():
    current_widget = root.focus_get()
    command = current_widget.get(f"{current_widget.index('insert linestart')}", f"{current_widget.index('insert lineend')}").strip()
    def run_command():
        process = subprocess.Popen(
        	command, 
        	shell=True, 
        	stdout=subprocess.PIPE, 
        	stderr=subprocess.PIPE,
        	text=True
        )
        for line in process.stdout:
            current_widget.insert(tk.END, line)
            current_widget.see(tk.END) 
        for line in process.stderr:
            current_widget.insert(tk.END, line)
            current_widget.see(tk.END)
        
        process.stdout.close()
        process.stderr.close()
        process.wait()
        if "wget" in command:
        	current_widget.insert(tk.END, "\nDone.\n")
        current_widget.see(tk.END)
    threading.Thread(target=run_command).start()        
        




def compile_and_execute_c():
    current_window = root.focus_get()
    code = current_window.get("1.0", tk.END)
    try:

        result = subprocess.run(
            ["gcc", "-x", "c", "-o", "/data/data/ru.iiec.pydroid3/files/temp", "-"],
            input=code,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            check=True,
        )
        output = result.stdout
        result = subprocess.run(
            ["/data/data/ru.iiec.pydroid3/files/temp"],
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
        )
        c_output = result.stdout

        popup_window = tk.Toplevel(root)
        popup_window.title("C Programming")
        popup_window.geometry("690x500+0+200")

        popup_text = scrolledtext.ScrolledText(
            popup_window,
            wrap=tk.WORD,
            width=24, 
            height=7, 
            font=("Courier", 8),
            padx=12, 
            pady=12,
            bd=10
                  
            
        )
        
        
        popup_text.insert("end", f"{c_output}\n")
        popup_text.pack(
            padx=12, 
            pady=12,
            fill=tk.BOTH,
            side=tk.TOP,
            expand=True
        )
        popup_window.config(bg="cyan")             
               

    except subprocess.CalledProcessError as e:

        popup_window = tk.Toplevel(root)
        popup_window.title("C Programming")
        popup_window.geometry("690x500+0+200")

        popup_text = scrolledtext.ScrolledText(
            popup_window,
            wrap=tk.WORD,
            width=24, 
            height=7, 
            font=("Courier", 8),
            padx=12, 
            pady=12,
            bd=14
                  
            
        )
        
        popup_text.insert("end", f"{e.output}\n")
        popup_text.pack(
            padx=12, 
            pady=12,
            fill=tk.BOTH,
            side=tk.TOP,
            expand=True,
        )
        popup_window.config(bg=quick_hex(80,0,0))
        popup_text.config(fg="darkred")
                     
         
        


def compile_and_execute_cpp():
    current_widget = root.focus_get()
    
    code = current_widget.get(1.0, tk.END)
    try:
        result = subprocess.run(
            [
                "g++",
                "-x",
                "c++",
                "-o",
                "/data/data/ru.iiec.pydroid3/files/temp_cpp",
                "-",
            ],
            input=code,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            check=True,
        )
        output = result.stdout

        result = subprocess.run(
            ["/data/data/ru.iiec.pydroid3/files/temp_cpp"],
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
        )
        cpp_output = result.stdout
        popup_window = tk.Toplevel(root)
        popup_window.title("C++ Programming")
        popup_window.geometry("690x500+0+200")

        popup_text = scrolledtext.ScrolledText(
            popup_window,
            wrap=tk.WORD,
            width=24, 
            height=7, 
            font=("Courier", 8),
            padx=12, 
            pady=12, 
                  
            
        )
        popup_text.insert(1.0, f"{cpp_output}\n")
     

        popup_text.pack(
            padx=12, 
            pady=12,
            fill=tk.BOTH,
            side=tk.TOP,
            expand=True,
        )                
        

    except subprocess.CalledProcessError as e:
    	
    	
        popup_window = tk.Toplevel(root)
        popup_window.title("C++ Programming")
        popup_window.geometry("690x500+0+200")

        popup_text = scrolledtext.ScrolledText(
            popup_window,
            wrap=tk.WORD,
            width=40,
            height=12, 
            font=("Courier", 8), 
            padx=12, 
            pady=12
        )
        popup_text.insert("end", f"{e.output}\n")
        popup_text.pack(
            padx=12, 
            pady=12,
            fill=tk.BOTH,
            side=tk.TOP,
            expand=True,
        )          
    	
  
        
def python_exec(event=None):
    current_widget = root.focus_get()

    code = current_widget.get(1.0, tk.END)
    output = io.StringIO()
    sys.stdout = output
    try:
        exec(code)
    except Exception as e:
        output.write(f"{type(e).__name__}: {e}")
        
        
    finally:
        sys.stdout = sys.__stdout__
        result = output.getvalue()
               
        
                
        
        popup_window = tk.Toplevel(root)
        popup_window.title("Python Coding")
        popup_window.geometry("690x600+0+300")

        popup_text = scrolledtext.ScrolledText(
            popup_window,
            wrap=tk.WORD,
            width=40,
            height=7, 
            font=("Courier New", 9),
            bg="cyan",
            fg="darkblue",
            padx=12, 
            pady=12,
            bd=10,
            insertbackground="darkred",
            insertwidth=7,
            insertborderwidth=5,
           
        )
        
        popup_text.insert(tk.END, f"{result}")
        popup_text.pack(
            padx=8, 
            pady=8,
            fill=tk.BOTH,
            side=tk.TOP,
            expand=True,
        )
        
        
        
          
                                
        
def python_exec_partial(event=None):
    current_widget = root.focus_get()

    code = current_widget.get(1.0, tk.INSERT)
    output = io.StringIO()
    sys.stdout = output
    try:
        exec(code)
    except Exception as e:
        output.write(f"{type(e).__name__}: {e}")
    finally:
        sys.stdout = sys.__stdout__
        result = output.getvalue()
        current_widget.delete(tk.INSERT, tk.END)
        current_widget.insert(tk.INSERT, f"\n-------------------------\nOutput:\n\n{result}")

              
                            
                                                        


def eval_math(event=None):
	current_widget = root.focus_get()
	
	expression = str(current_widget.get(f"{current_widget.index('insert linestart')}", f"{current_widget.index('insert lineend')}")).strip().replace("x","*").replace("t","*").replace("d","/").replace("p","+").replace("m","-").replace(",","+")
	
	try:
				solved=eval(expression)
				current_widget.insert(tk.INSERT, f" = {round(solved, 5)}")
		
	except:
		pass
		
		
	
	


def open_file():
    current_widget = root.focus_get()
    file_path = filedialog.askopenfilename(
        filetypes=[
        ("Python", "*.py*"), 
        ("CC++", "*.c"),
        ("Text", "*.txt"),
        ("CSV", "*.csv")
        ]
    )
    if file_path:
        with open(file_path, "r") as file:
            content = file.read()
            current_widget.delete(1.0, tk.END)
            current_widget.insert(1.0, content)
            with open("last_file.txt", 'w') as file:
            	file.write(f"{file_path}")
            
            current_widget.mark_set(tk.INSERT, tk.END)
            current_widget.see(tk.INSERT)




def save_file():
    current_widget = root.focus_get()
    file_path = filedialog.asksaveasfilename(
        defaultextension=".txt",
        filetypes=[("Text Files", "*.txt"), ("All Files", "*.*")],
    )
    if file_path:
        content = current_widget.get("1.0", tk.END)
        with open(file_path, "w") as file:
            file.write(content)
            

     
        
def search_replace():
    current_widget = root.focus_get()
    text_to_search = simpledialog.askstring("Search", "Enter text to search:")
    text_to_replace = simpledialog.askstring("Replace", "Enter text to replace:")
    index = "1.0"
    while index:
        index = current_widget.search(text_to_search, index, stopindex=tk.END)
        if index:
            end_index = f"{index}+{len(text_to_search)}c"
            current_widget.delete(index, end_index)
            current_widget.insert(index, text_to_replace)
            index = end_index





def remove_empty_lines():
    current_widget = root.focus_get()
    text_content = current_widget.get("1.0", "end")
    non_empty_lines = [line for line in text_content.split("\n") if line.strip()]
    new_text = "\n".join(non_empty_lines)
    current_widget.delete("1.0", "end")
    current_widget.insert("1.0", new_text)



    
def select_all():
    current_widget = root.focus_get()
    current_widget.tag_add("sel", "1.0", "end")
def copy():
    current_widget = root.focus_get()
    if hasattr(current_widget, "selection_get"):
        selected_text = current_widget.selection_get()
        root.clipboard_clear()
        root.clipboard_append(selected_text)



def cut():
    focused_widget = root.focus_get()
    if focused_widget:
        if focused_widget.tag_ranges("sel"):
            focused_widget.event_generate("<<Cut>>")
        else:
            cursor_pos = focused_widget.index("insert")
            if cursor_pos != "1.0":
                focused_widget.delete(cursor_pos + "-1c")


def paste():
    current_widget = root.focus_get()
    if current_widget:
        if current_widget.tag_ranges("sel"):
            current_widget.delete("sel.first", "sel.last")
    current_widget.insert("insert", root.clipboard_get())
    




def clear_widget():
    current_widget = root.focus_get()
    response = messagebox.askyesno("Clear Window", "Clear the window?")
    if response:
        current_widget.delete(1.0, tk.END)




def text_wrap():
    data = str(text_entry.get(1.0, tk.END))
    wrapped_text = textwrap.fill(data, width=25)
    text_entry.delete(1.0, tk.END)
    text_entry.insert(1.0, wrapped_text)
    


def webpage(event=None):
    current_widget = root.focus_get()
    query = str(current_widget.get(1.0, tk.END).strip())
    if ".com" in str(current_widget.get(1.0, tk.END)):
        current_widget.delete(1.0, tk.END)
    url = "https://" + query if not query.startswith("https://") else query
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/535.36 (KHTML, like Gecko) Chrome/54.0.3026.110 Safari/535.3"
    }
    response = requests.get(url, headers=headers)
    if response.status_code == 200:
        soup = BeautifulSoup(response.text, "html.parser")
        current_widget.insert(tk.END, soup.get_text())
        text_wrap()
        
        
    else:
        current_widget.insert(tk.END, "\n\nCheck Internet Connection..")





def center_text():
    current_widget = root.focus_get()
    current_widget.tag_configure("centered", justify="center")
    content = current_widget.get("1.0", "end-1c")
    current_widget.delete("1.0", tk.END)
    current_widget.insert(tk.END, content, "centered")





def auto_indent(event=None):
    detect_command()
    current_widget = root.focus_get()
    current_line = int(current_widget.index("insert").split(".")[0])
    start_of_line = f"{current_line}.0"
    text_contents = current_widget.get(start_of_line, start_of_line + " lineend")
    indentation = text_contents[: len(text_contents) - len(text_contents.lstrip())]
    if (
        indentation
        and current_line == int(current_widget.index("insert").split(".")[0])
        and event.keysym == "Return"
    ):
        current_widget.insert("insert", "\n" + indentation)
        current_widget.see("insert")
        return "break"






def select_all_copy():
    select_all()
    copy()
    

def search_highlight():
    text_to_search = simpledialog.askstring(
        "Search", "Enter text to search and highlight:"
    )
    index = "1.0"
    while index:
        index = text_entry.search(text_to_search, index, stopindex=tk.END)
        if index:
            end_index = f"{index}+{len(text_to_search)}c"
            background_color = "yellow"
            text_entry.tag_add("search", index, end_index)
            text_entry.tag_config("search", background=background_color)
            index = end_index



 

            



def highlight_python_functions():
    current_widget = root.focus_get()
    current_widget.config(font=("Courier New", 6))
    current_bg = current_widget.cget("background")
    current_fg = current_widget.cget("foreground")
    current_widget.tag_configure("highlight", background=current_fg, foreground=current_bg)
    # Get the total number of lines in the text widget
    total_lines = int(current_widget.index(tk.END).split('.')[0])
    for line_number in range(1, total_lines + 1):
        line_start = f"{line_number}.0"
        line_end = f"{line_number}.end"
        line_content = current_widget.get(line_start, line_end)
        if "):" in line_content:
            current_widget.tag_add("highlight", line_start, line_end)
            
            
            

                       
            




def find_function():
    current_widget = root.focus_get()
    cursor_position = current_widget.index("insert")
    next_index = current_widget.search("def", cursor_position, stopindex=tk.END)
    if next_index:
        current_widget.mark_set("insert", next_index)
        current_widget.see(next_index)
        current_widget.focus_set()
        line_num = int(next_index.split(".")[0])
        current_widget.mark_set("insert", f"{line_num}.end")
        highlight_python_functions()


def python_functions_info():
    global highlight_python_description
    
    current_widget = root.focus_get()
    letter = current_widget.get("1.0", tk.END).strip().replace("$", "").strip()
   
    
    
    all_functions = [
        name for name in dir(builtins) if callable(getattr(builtins, name))
    ]
    filtered_functions = [func for func in all_functions if func.startswith(letter)]
    
    
    if filtered_functions:
        current_widget.delete("2.0", tk.END) 
        for func in filtered_functions:
            func_obj = getattr(builtins, func)
            docstring = func_obj.__doc__
            current_widget.insert(tk.END, f"\n{func}:")
            if docstring:
                current_widget.insert(tk.END, f"{docstring.strip()}\n\n")
                description = str(current_widget.get(1.0, tk.END))
                formatted_description = description.replace(":", ":\n")
                current_widget.delete(1.0, tk.END)
                current_widget.insert(1.0, formatted_description)
                highlight_python_description()
          


def alphabetize():
    current_widget = root.focus_get()
    content = current_widget.get("1.0", tk.END)
    lines = [line.strip() for line in content.split("\n") if line.strip()]
    sorted_lines = sorted(lines)
    current_widget.delete(tk.INSERT, tk.END)
    for line in sorted_lines:
        current_widget.insert(tk.INSERT, line + "\n")
        
        
        


def list_functions():
    current_widget = root.focus_get()
    content = current_widget.get("1.0", tk.END)
    lines = content.split("\n")
    functions = []
    
    for line_num, line in enumerate(lines, start=1):
        line = line.strip()
        if line.startswith("def "):
            function_name = line.split("(")[0].split("def ")[1]
            functions.append((function_name, line_num))
    
    functions.sort(key=lambda x: x[0])  # Sort functions alphabetically by function name
    
    popup = tk.Toplevel(root)
    popup.title("List of Functions")
    popup.geometry("620x520+0+300")
    
    scrollbar = tk.Scrollbar(popup)
    scrollbar.pack(side=tk.RIGHT, fill=tk.Y)
    
    text_widget_popup = tk.Text(popup, wrap=tk.WORD, yscrollcommand=scrollbar.set)
    
    text_widget_popup.config(
        font=("Courier New", 8),
        bg="black",
        fg="white",
        padx=10, 
        pady=10,
        bd=8,
        insertwidth=6,
        insertbackground="cyan"
    )
    text_widget_popup.pack(
        expand=True, 
        fill=tk.BOTH,
        padx=10,
        pady=10
    )
    
    scrollbar.config(command=text_widget_popup.yview)
    
    for function_name, line_num in functions:
        text_widget_popup.insert(tk.END, f"{function_name} : {line_num}\n")
    text_widget_popup.bind("<Return>", detect_command)
    
    popup.mainloop()












def xterminal():
    
    def quick_save_file(event=None):
        file_path = simpledialog.askstring("File Save", "Save As: ")
        if file_path:
        	confirmed = messagebox.askyesno("Save Confirm", f"Save Page Content as {file_path}")
        	if confirmed:
        		content = window.get("1.0", tk.END)
        		with open(file_path, "w") as file:
        		  file.write(content)
        		  
        	   

 
        else:
        	pass
    
    	
    		
    			
    					
    terminal_root = tk.Toplevel(root)
    terminal_root.title("List of Functions")
    terminal_root.geometry("700x800+0+0")
    
    scrollbar = tk.Scrollbar(terminal_root)
    scrollbar.pack(side=tk.RIGHT, fill=tk.Y)
    
    window = tk.Text(
        terminal_root, 
        wrap=tk.WORD,
        yscrollcommand=scrollbar.set)
    window.pack(
        expand=True, 
        fill=tk.BOTH
    )
    window.config(
        font=("Courier New", 9), 
        padx=18, 
        pady=18,
        bg="black", 
        fg="white"
        )
    
    scrollbar.config(command=window.yview)
    window.bind("<Return>", subprocess_command)
    window.focus_set()
    
    window.config(
        insertbackground="orange",
        insertwidth=5,
        insertborderwidth=1
        )
    ########
    menu_bar = tk.Menu(terminal_root)
    terminal_root.config(menu=menu_bar)
    menu_bar.config(
        font=("Courier", 11),
        bg=menu_bg, 
        fg=menu_fg, 
        borderwidth=6, 
        relief=tk.RAISED
    )
    options_menu = tk.Menu(
    menu_bar, 
    tearoff=0, 
    font=("Courier New", 10),
    bg=submenu_bg,
    fg=submenu_fg
    )
    menu_bar.add_cascade(label="Options", menu=options_menu)   
    
    options_menu.add_command(label="Clear", command=clear_widget)
    options_menu.add_command(label="Font Size", command=choose_font_size)
    options_menu.add_command(label="ls *.*", command=list_files)
    options_menu.add_command(label="List Functions ()", command=list_functions)
    
    options_menu.add_command(label="View File At Cursor", command=view_file_at_cursor)
    options_menu.add_command(label="Save", command=quick_save_file)
    
    
    
    options_menu.add_command(label="Cut", command=cut)
    options_menu.add_command(label="Copy", command=copy)
    options_menu.add_command(label="Paste", command=paste)
    options_menu.add_command(label="Python", command=python_exec)
    options_menu.add_command(label="C++", command=compile_and_execute_cpp)
    options_menu.add_command(label="Exit", command=lambda: terminal_root.destroy())
    
    
    
    

   
    web_menu = tk.Menu(
    menu_bar, 
    tearoff=0, 
    font=("Courier New", 10),
    bg=submenu_bg,
    fg=submenu_fg
    )
    menu_bar.add_cascade(label="Internet", menu=web_menu)
    web_menu.add_command(label="Soup Search", command=search_web)
    
    
    
    
    window.insert(1.0, "    Bourne Again SHell, 2024\n×××××××××××××××××××××××××××××\n")
   
   
   
    
    
    
    
    
    
   
    terminal_root.mainloop()  
        
    



                




    
   
    
    
def sort_lines_with_numbers():
    current_widget = root.focus_get()
    content = current_widget.get("1.0", tk.END)
    lines = content.split("\n")
    sorted_lines = []
    for line in lines:
        words = line.split()
        if words and words[-1].isdigit():
            sorted_lines.append((int(words[-1]), line))
        else:
            sorted_lines.append((float("inf"), line))
    sorted_lines.sort(key=lambda x: x[0])
    sorted_content = "\n".join(line[1] for line in sorted_lines)
    current_widget.delete("1.0", tk.END)
    current_widget.insert("1.0", sorted_content)


    
    
def left_side_text():
    current_widget = root.focus_get()
    current_widget.tag_configure("left_aligned", justify="left")
    content = current_widget.get("1.0", "end-1c")

    current_widget.delete("1.0", tk.END)

    current_widget.insert(tk.END, content, "left_aligned")




def grey_theme():
    root.config(bg="#6a4e4f")
    menu_bar.config(
        bg="#cff3e6", 
        fg="#2c5c50"
        )
    text_entry.config(
        bg="#dae5e4", 
        fg="#132d1b"
        )
    
    
    text_entry.config(insertbackground="darkred")
    







	

def quick_save():
    current_widget = root.focus_get()
    
    with open("last_code.txt", "w") as f:
        f.write(current_widget.get("1.0", "end-1c"))




def load_last_content():
    current_widget = root.focus_get()
    
    
    try:
        with open("last_code.txt", "r") as f:
            current_widget.delete("1.0", "end")
            current_widget.insert("1.0", f.read())
            
            
    except FileNotFoundError:
        pass





        
def go_to_line():
    current_widget=root.focus_get()
    line_number = simpledialog.askinteger("Go To Line", "Enter line number:")
    if line_number is not None:
    	line=float(line_number)
    	current_widget.mark_set("insert", line)
    	current_widget.see(line)
    
    
           
	
def grep_search():
        current_widget=root.focus_get()
        search_string = current_widget.get(1.0, tk.END).strip().replace("$ ", "")
        command = f'grep -l "{search_string}" *'
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        current_widget.delete(1.0, tk.END)
        
        
        text_entry.insert(1.0, f"Files with '{search_string}': \n\n")
        
        
        text_entry.insert(tk.END, '\n'.join(result.stdout.splitlines()) + '\n')
        
        text_entry.mark_set(tk.INSERT, "1.0")
        
        text_entry.see(tk.INSERT)
        text_entry.focus_set()

      

        


word_completions = {
    "im": "import",
    "ne": "news",
    "pr": "print",
    "re": "return",
    "va": "variable",
    "co": "continue",
    "pa": "parameter",
    "cl": "class",
    "me": "method",
    "fu": "function",
    "if": "if",
    "el": "else",
    "wh": "while",
    "fo": "for",
    "ra": "range",
    "tr": "try",
    "ex": "except",
    "ra": "raise",
    "gl": "global",
    "nl": "nonlocal",
    "de": "delete",
    "br": "break",
    "pa": "pass",
    "co": "continue",
    "yi": "yield",
    "re": "return",
    "ex": "except",
    "im": "import",
    "Tr": "True",
    "Fa": "False",
    "cl": "class",
    "me": "method",
    "de": "define",
    "tr": "trying",
    "el": "elif",
    "no": "not",
    "le": "length",
    "ra": "range",
    "wh": "while",
    "re": "return",
    "ex": "except",
}

def complete_word(event=None):
    current_widget = root.focus_get()
    
    partial_word = current_widget.get(f"{current_widget.index('insert linestart')}", f"{current_widget.index('insert lineend')}").strip()
    
    last_two_letters = partial_word[-2:] 

    if last_two_letters in word_completions:
        completion = word_completions[last_two_letters]
        current_widget.delete("end-3c", tk.INSERT)  # Delete last two letters
        current_widget.insert(tk.INSERT, completion)  # Insert completion
                                                       


                         
                

def highlight_keywords(event=None):
    key_words = [
        "assert", 
        "break", 
        "class", 
        "continue", 
        "def", 
        "if", 
        "elif", 
        "else", 
        "else", 
        "except", 
        "finally", 
        "for", 
        "from", 
        "global", 
        "import", 
        "lambda", 
        "nonlocal", 
        "pass", 
        "raise", 
        "return", 
        "try", 
        "while", 
        "with", 
        "yield", 
        "True",
        "False",
        "#include",
        "cmath",
        "cout",
        "int",
        "float",
        "char",
        "bool",
        "double",
        "auto",
        "switch",
        "case",
        "default",
        "struct",
        ";",
        ",",
        "/",
        "*",
        "+",
        "-",
        "=",
        "{",
        "}"
        
        
        
        
        
        
        
        
        
        
        
        ]
    
    for word in key_words:
        start_index = "1.0"
        while True:
            start_index = text_entry.search(word, start_index, stopindex=tk.END)
            if not start_index:
                break
            end_index = f"{start_index.split('.')[0]}.{int(start_index.split('.')[1]) + len(word)}"
            text_entry.tag_add("orange", start_index, end_index)
            text_entry.tag_config("orange", foreground="orange")
            start_index = end_index      
        
	
		
	
def search_web(event=None):
    current_widget = root.focus_get()
    query = current_widget.get(f"{current_widget.index('insert linestart')}", f"{current_widget.index('insert lineend')}").strip()
    
    current_widget.delete(tk.INSERT, tk.END)
    current_widget.insert(tk.INSERT, f"\n\nResults for '{query}' :\n\n")
    url = f"https://www.google.com/search?q={query}"
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
    }
    response = requests.get(url, headers=headers)
    if response.status_code == 200:
        soup = BeautifulSoup(response.text, "html.parser")
        search_results = soup.find_all("div", class_="BNeawe s3v9rd AP7Wnd")
        if search_results:
            current_widget.delete(3.0, tk.END)
            current_widget.insert(3.0, f"\n\nResults for {query} :\n\n")
            for index, result in enumerate(search_results, 1):
                color = "black"
                current_widget.insert(tk.END, f"{index}. {result.get_text()}\n------------------------\n")
                current_widget.tag_add(f"result_{index}", f"{3+index}.0", f"{3+index}.end")
                current_widget.tag_config(f"result_{index}", background=color, foreground=random.choice(["white", "cyan", "lightblue", "lightgreen"]))
        else:
            pass

    current_widget.mark_set(tk.INSERT, "1.end")
    current_widget.see(tk.INSERT)                                        




def highlight_line(event=None):
    current_widget = root.focus_get()
    bg=current_widget.cget("bg")
    fg=current_widget.cget("fg")
            
    current_widget.tag_remove("highlight", 1.0, "end")
    current_widget.tag_add("highlight", "insert linestart", "insert lineend+1c")
    current_widget.tag_configure("highlight", 
    	background=fg,
    	foreground=bg    
    )


def highlight_line_2(event=None):
    current_widget = root.focus_get()
    current_widget.tag_remove("highlight", 1.0, "end")
    current_widget.tag_add("highlight", "insert linestart", "insert lineend+1c")
    current_widget.tag_configure("highlight", 
    	background="#203d49",
    	foreground="white"  
    )

 







def find_string():
    current_widget = root.focus_get()
    cursor_position = current_widget.index("insert")
    string_to_search = simpledialog.askstring("Search", "Enter String to Search:")
    
    next_index = current_widget.search(f"{string_to_search}", cursor_position, stopindex=tk.END)
    if next_index:
        current_widget.mark_set("insert", next_index)
        current_widget.see(next_index)
        current_widget.focus_set()
        line_num = int(next_index.split(".")[0])
        current_widget.mark_set("insert", f"{line_num}.end")
        
    highlight_line()
        


def choose_font_size():
	current_widget = root.focus_get()
	font_size = simpledialog.askinteger("Font Size Integer", "Font Size:")
	current_widget.config(font=("Courier New", font_size))



def quick_string_search():
    current_widget = root.focus_get()
    cursor_position = text_entry.index("insert")
    string_to_search = global_entry.get(1.0, tk.INSERT).lower().strip()
    
    if "def" in string_to_search:
    	highlight_python_functions()
    	
    if string_to_search:
    	next_index = text_entry.search(f"{string_to_search}", cursor_position, stopindex=tk.END)
    	if next_index:
    	   text_entry.mark_set("insert", next_index)
    	   text_entry.see(next_index)
    	   text_entry.focus_set()
    	   line_num = int(next_index.split(".")[0])
    	   text_entry.mark_set("insert", f"{line_num}.end")
    	   index = cursor_position
    	   while index:
    	   	index = text_entry.search(string_to_search, index, stopindex=tk.END)
    	   	if index:
    	   	   end_index = f"{index}+{len(string_to_search)}c"
    	   	   background_color = "cyan"
    	   	   text_entry.tag_add("search", index, end_index)
    	   	   text_entry.tag_config("search", background="cyan", foreground="black")
    	   	   index = end_index
    	   	   highlight_line()
  
    else:
        find_string()
        

def open_file_2(event=None):
    file_name = global_entry.get(1.0, tk.END).strip()
    if file_name:
        try:
            with open(file_name, "r") as file:
                content = file.read()
            text_entry.delete(1.0, tk.END)
            text_entry.insert(1.0, f"{content}")
            text_entry.mark_set(tk.INSERT, 1.0)
            text_entry.see(tk.INSERT)
            

        except:
        	pass
    else:
        open_file()




	

def scroll_up():    
    text_entry.event_generate("<Up>")    
    highlight_line()
  




def scroll_down():
    text_entry.event_generate("<Down>")    
    highlight_line()
  	

def get_colors_2():
    
    text_entry.focus_set()
    
    root_color = root.cget("bg")
    menu_bg_color = menu_bar.cget("background")
    
    menu_fg_color = menu_bar.cget("foreground")
    bg_top = text_entry.cget("background")
    fg_top = text_entry.cget("foreground")
    cursor_color_top = text_entry.cget("insertbackground")
    
    text_entry.delete(tk.INSERT, tk.END)
    text_entry.insert(tk.END, f"\n\ndef new_theme():\n  root.config(bg='{root_color}')\n")
    text_entry.insert(tk.END, f"  menu_bar.config(\n      bg = '{menu_bg_color}',\n      fg = '{menu_fg_color}'\n  )\n")
    text_entry.insert(tk.END, f"  text_entry.config(\n      bg = '{bg_top}',\n      fg = '{fg_top}'\n  )\n  text_entry.config(insertbackground = '{cursor_color_top}')")

    
    
    
    select_all()
    copy()

    
        




def menu_bg_choose():
	color = colorchooser.askcolor()
	menu_bar.config(bg=f"{color[-1]}")

def menu_fg_choose():
	color = colorchooser.askcolor()
	menu_bar.config(fg=f"{color[-1]}")


def foreground_choose():
	current_widget = root.focus_get()
	color = colorchooser.askcolor()
	current_widget.insert(1.0, color)
	current_widget.config(fg=f"{color[-1]}")



def background_choose():
	current_widget = root.focus_get()
	color = colorchooser.askcolor()
	current_widget.insert(1.0, color)
	current_widget.config(bg=f"{color[-1]}")
	
	
	
def root_choose():
	current_widget = root.focus_get()
	color = colorchooser.askcolor()
	current_widget.insert(1.0, color)
	root.config(bg=f"{color[-1]}")	


	
	
def help_search():
    current_widget = root.focus_get()
    word = current_widget.get(f"{current_widget.index('insert linestart')}", f"{current_widget.index('insert lineend')}").strip()
   
   
    current_widget.delete(1.0, tk.END)
    current_widget.insert(1.0, f"print(help('{word}'))")
    python_exec()
    

def random_dark_theme():
    current_widget = root.focus_get()
    random_bg_color = "#{:02x}{:02x}{:02x}".format(
        random.randint(0, 100), random.randint(0, 100), random.randint(0, 100)
    )
    random_fg_color = "#{:02x}{:02x}{:02x}".format(
        random.randint(190, 255), random.randint(190, 255), random.randint(190, 255)
    )

    current_widget.config(bg=random_bg_color, fg=random_fg_color)

    current_widget.focus_set()
    



def tuple_to_hex():
    current_widget = root.focus_get()
    rgb_str = current_widget.get(
        f"{current_widget.index('insert linestart')}",
        f"{current_widget.index('insert lineend')}",
    ).strip()
    
    
    rgb_values = tuple(map(int, rgb_str.split(",")))
    hex_color = "#{:02x}{:02x}{:02x}".format(*rgb_values)   
    current_widget.insert(tk.INSERT, f" = '{hex_color}'\n")




def new_theme():
  root.config(bg='#4f4745')
  menu_bar.config(
      bg = '#0b0818',
      fg = '#ecefeb'
  )
  text_entry.config(
      bg = '#142b33',
      fg = '#e0f1de'
  )
  text_entry.config(insertbackground = 'orange')











def default_theme():
  root.config(bg="black")
  menu_bar.config(
      bg = "#38404e",
      fg = "#c8c3b3"
      
  )
  text_entry.config(
      bg = "black",
      fg = "white"
  )
  text_entry.config(insertbackground = 'orange')
  
  
  

submenu_bg = "black"
submenu_fg = "lightgrey"

button_bg = "black"
button_fg = "lightblue"

menu_bg = "black"
menu_fg = "lightgrey"





##begin   ################

root = tk.Tk()
root.geometry("270x300+0+0")
root.title("[ G-UNIX ]")
root.config(bg="black")


text_entry = scrolledtext.ScrolledText(
    root, 
    wrap=tk.WORD,
    font=("Courier New", 9),    
    width=30,
    height=14,
    bg="black", 
    fg="white", 
    padx=12, 
    pady=12,
    bd=12,
    
    )

text_entry.place(
    x=10,
    y=90
    )
text_entry.config(state=tk.NORMAL)



global_entry = tk.Text(
    root, 
    height=1, 
    width=16,
    font=("Courier New", 9),
    bg="black",
    fg="white",
    
    padx=6,
    pady=4,
    bd=6
    
)

global_entry.place(
	x=10, 
	y=10
)








########################
menu_bar = tk.Menu(root)
root.config(menu=menu_bar)

menu_bar.config(
    font=("Courier New", 12),
    bg=menu_bg, 
    fg=menu_fg, 
    borderwidth=6, 
    relief=tk.RAISED
)




file_menu = tk.Menu(
    menu_bar, 
    tearoff=0, 
    font=("Courier New", 10),
    bg=submenu_bg,
    fg=submenu_fg
    )
menu_bar.add_cascade(label="File", menu=file_menu)
file_menu.add_command(label="Open", command=open_file)
file_menu.add_command(label="Save", command=save_file)

file_menu.add_command(label="Quick Save", command=quick_save)

file_menu.add_command(label="Quick Load", command=load_last_content)

file_menu.add_command(label="Help", command=help_search)

file_menu.add_command(label="Save File 2", command=quick_save_file)


file_menu.add_command(label="GoTo Line Number", command=go_to_line)


file_menu.add_command(label="Search&Highlight", command=search_highlight)

file_menu.add_command(label="Search&Replace", command=search_replace)

file_menu.add_command(label="Find String", command=find_string)

file_menu.add_command(label="View File", command=view_file_at_cursor)

file_menu.add_command(label="XTerm", command=xterminal)

file_menu.add_command(label="Get Colors", command=get_colors_2)



edit_menu = tk.Menu(
    menu_bar, 
    tearoff=0, 
    font=("Courier New", 10),
    bg=submenu_bg,
    fg=submenu_fg
    )
menu_bar.add_cascade(label="Edit", menu=edit_menu)
edit_menu.add_command(label="Select All", command=select_all)
edit_menu.add_command(label="Copy", command=copy)
edit_menu.add_command(label="Cut", command=cut)
edit_menu.add_command(label="Paste", command=paste)


edit_menu.add_command(label="Cut ↑", command=cut_above)
edit_menu.add_command(label="Cut ↓", command=cut_below)



edit_menu.add_command(label="Read-only", command=read_only_mode)

edit_menu.add_command(label="Edit_mode", command=edit_mode)


edit_menu.add_command(label="Alphabetize", command=alphabetize)

edit_menu.add_command(label="Center_text", command=center_text)

edit_menu.add_command(label="Sort by Number", command=sort_lines_with_numbers)

edit_menu.add_command(label="left_side_text", command=left_side_text)





developer_menu = tk.Menu(
     menu_bar, 
     tearoff=0, 
     font=("Courier New", 10),
     bg=submenu_bg,
     fg=submenu_fg
     )


menu_bar.add_cascade(label="Tools", menu=developer_menu)


developer_menu.add_command(label="Python Exe", command=python_exec)    

developer_menu.add_command(label="Python Partial", command=python_exec_partial) 

developer_menu.add_command(label="Compile C", command=compile_and_execute_c)  

developer_menu.add_command(label="Compile C++", command=compile_and_execute_cpp)

developer_menu.add_command(label="RGB Decoder", command=rgb_machine)

developer_menu.add_command(label="Generate Words", command=generate_words)

developer_menu.add_command(label="Grep -l *", command=grep_search)


developer_menu.add_command(label="Search&Replace", command=search_replace)


developer_menu.add_command(label="list_files", command=list_files)

developer_menu.add_command(label="eval(4+5/6)", command=eval_math)


web_menu = tk.Menu(
    menu_bar, 
    tearoff=0, 
    font=("Courier New", 10),
    bg=submenu_bg, 
    fg=submenu_fg
)
menu_bar.add_cascade(label="WEB", menu=web_menu)
    

web_menu.add_command(label="URL via Soup", command=webpage)




web_menu.add_command(label="Soup Search", command=search_web)




colors_menu = tk.Menu(
    menu_bar, 
    tearoff=0, 
    font=("Courier New", 10),
    bg=submenu_bg, 
    fg=submenu_fg
)

menu_bar.add_cascade(label="Th", menu=colors_menu)

colors_menu.add_command(label="Root Color", command=root_choose)

colors_menu.add_command(label="Menu bg", command=menu_bg_choose)

colors_menu.add_command(label="Menu fg", command=menu_fg_choose)

colors_menu.add_command(label="Background", command=background_choose)

colors_menu.add_command(label="Foreground", command=foreground_choose)



colors_menu.add_command(label="Dark Random", command=random_dark_theme)

colors_menu.add_command(label="Tuple → Hex#", command=tuple_to_hex)



colors_menu.add_command(label="Default Theme", command=default_theme)


navigate_menu = tk.Menu(
    menu_bar, 
    tearoff=0, 
    font=("Courier New", 10),
    bg=submenu_bg, 
    fg=submenu_fg
)

menu_bar.add_cascade(label="Nav", menu=navigate_menu)


navigate_menu.add_command(label="GoTo Top", command=go_to_begingning)

navigate_menu.add_command(label="GoTo Bottom", command=go_to_end)

navigate_menu.add_command(label="GoTo Line", command=go_to_line)


navigate_menu.add_command(label="GoTo String", command=go_to_string)


search_button = tk.Button(
    root,
    command=quick_string_search,
    text="Qs",
    font=("Times",8, "bold"),
    bg=button_bg,
    fg=button_fg,       
    width=1,
    relief="raised",    
    borderwidth=5,
    highlightthickness=3,
)
search_button.place(
    x=370, 
    y=7
)




save_button = tk.Button(
    root,
    command=quick_save_file,
    text="S",
    font=("Times",8, "bold"),
    bg=button_bg,
    fg=button_fg,       
    width=1,
    relief="raised",    
    borderwidth=5,
    highlightthickness=3,
)
save_button.place(
    x=485, 
    y=7
)



theme_button = tk.Button(
    root,
    command=random_all_theme,
    text="th",
    font=("Times",8, "bold"),
    bg=button_bg,
    fg=button_fg,       
    width=1,
    relief="raised",    
    borderwidth=5,
    highlightthickness=3,
)
theme_button.place(
    x=600, 
    y=7
)




color_button = tk.Button(
    root,     
    command=python_exec_partial,
    text="P]",
    font=("Courier New", 10),
    bg=button_bg,
    fg=button_fg,       
    width=1,
    relief="raised",    
    borderwidth=8,
    highlightthickness=1,
)
    
color_button.place(x=10, y=760)


clear_button = tk.Button(
    root,
    command=complete_word,
    text="W→",
    font=("Courier New", 10),
    bg=button_bg,
    fg=button_fg,  
    width=1,
    relief="raised",    
    borderwidth=8,
    highlightthickness=1,       
  
)

    
clear_button.place(x=130, y=760)


view_file_button = tk.Button(
    root,
    command=list_files,
    text="ls",
    font=("Courier New", 10),
    bg=button_bg,
    fg=button_fg,       
    width=1,
    relief="raised",    
    borderwidth=8,
    highlightthickness=1,
)
    
view_file_button.place(x=250, y=760)


list_files_button = tk.Button(
    root,
    command=view_file_at_cursor,
    text="O",
    font=("Courier New", 10),
    bg=button_bg,
    fg=button_fg,       
    width=1,
    relief="raised",    
    borderwidth=8,
    highlightthickness=1,
)
    
list_files_button.place(x=370, y=760)


list_functions_button = tk.Button(
    root,
    command=list_functions,
    text="()",
    font=("Courier New", 10),
    bg=button_bg,
    fg=button_fg,       
    width=1,
    relief="raised",    
    borderwidth=8,
    highlightthickness=1,
)
    
list_functions_button.place(x=490, y=760)


clear_button = tk.Button(
    root,
    command=clear_widget,
    text="Clr",
    font=("Courier New", 10),
    bg=button_bg,
    fg="lightgrey",       
    width=1,
    relief="raised",    
    borderwidth=8,
    highlightthickness=1,
)
    
clear_button.place(x=610, y=760)





text_entry.config(
    insertwidth=8,
    insertborderwidth=5,
    insertbackground="orange",
    
)


global_entry.config(
    insertwidth=4,
    #insertborderwidth=5,
    insertbackground="cyan",
    
)


def on_closing():
    with open("last_code.txt", "w") as f:
        f.write(text_entry.get("1.0", "end-1c"))


root.protocol("WM_DELETE_WINDOW", on_closing)

try:
    with open("last_code.txt", "r") as f:
        text_entry.delete("1.0", "end")
        text_entry.insert("1.0", f.read())


except FileNotFoundError:
    pass






text_entry.focus_set()




text_entry.bind("<space>", highlight_keywords)




text_entry.bind("<Return>", auto_indent)

text_entry.bind("<Button-1>", eval_math)


#text_entry.bind("<KeyRelease>", highlight_line_2)


new_theme()

root.mainloop()
 