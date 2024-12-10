# -*-coding:Utf-8 -*

import importlib

import random


import sys

import Dependencies.Logger.logger_config as logger_config
import Dependencies.Common.date_time_formats as date_time_formats

import m3u
import xspf

import main_view


class M3uToFreeboxApplication:
    """ Application """

    def __init__(self, m3u_to_freebox_main_view):

        self._m3u_library: m3u.M3uEntriesLibrary = m3u.M3uEntriesLibrary()

        self._main_view:m3u_to_freebox_main_view.M3uToFreeboxMainView = m3u_to_freebox_main_view
        
    def create_xspf_file_by_id_str(self, m3u_entry_id:str):
        m3u_entry_id_int = int(m3u_entry_id)
        self.create_xspf_file_by_id(m3u_entry_id_int)
        
    def create_xspf_file_by_id(self, m3u_entry_id:int):
        
        m3u_entry = self.m3u_library.get_m3u_entry_by_id(m3u_entry_id)

        xspf_file_content = xspf.XspfFileContent(m3u_entry.title, m3u_entry.link)
        xsp_file_creator = xspf.XspfFileCreator()
        xsp_file_creator.write(xspf_file_content,m3u_entry.title_as_valid_file_name + ".xspf")
        
    def reset_library(self):
        self._m3u_library = m3u.M3uEntriesLibrary()
    
        
    def load_file(self, file_path):
        """ Load file """
        logger_config.print_and_log_info("Load file:" + file_path)

        m3u_file_parser =  m3u.M3uFileParser()
        for m3u_entry in m3u_file_parser.parse_file(file_path):
            self._m3u_library.add(m3u_entry)
        
    @property
    def m3u_library(self) -> m3u.M3uEntriesLibrary :
        return self._m3u_library

    @m3u_library.setter
    def m3u_library(self, value:m3u.M3uEntriesLibrary):
        self._m3u_library = value

if __name__ == "__main__":
    # sys.argv[1:]
    
    main = importlib.import_module("main")
    main.main()
