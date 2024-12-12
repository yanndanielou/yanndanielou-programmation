# -*-coding:Utf-8 -*

import importlib

import random


import sys

import Dependencies.Logger.logger_config as logger_config
import Dependencies.Common.date_time_formats as date_time_formats
import Dependencies.Common.Constants

from m3u import M3uEntriesLibrary, M3uFileParser
import xspf

import m3u_search_filters

import param

class M3uToFreeboxApplication:
    """ Application """

    def __init__(self, m3u_to_freebox_main_view):

        self._m3u_library: M3uEntriesLibrary = M3uEntriesLibrary()
        self._main_view:m3u_to_freebox_main_view.M3uToFreeboxMainView = m3u_to_freebox_main_view
        
    def create_xspf_file_by_id_str(self, directory_path_name:str, m3u_entry_id:str, print_result:bool=True) -> bool:
        m3u_entry_id_int = int(m3u_entry_id)
        return self.create_xspf_file_by_id(directory_path_name, m3u_entry_id_int, print_result)
        
    def create_xspf_file_by_id(self, directory_path_name:str, m3u_entry_id:int, print_result:bool= True) -> bool:
        
        m3u_entry = self.m3u_library.get_m3u_entry_by_id(m3u_entry_id)

        xspf_file_content = xspf.XspfFileContent(m3u_entry.original_raw_title, m3u_entry.link)
        xsp_file_creator = xspf.XspfFileCreator()
        return xsp_file_creator.write(xspf_file_content, directory_path_name + "/" + m3u_entry.title_as_valid_file_name + ".xspf", print_result)
        
    def reset_library(self):
        """ clear library """
        self._m3u_library = M3uEntriesLibrary()
    
        
    def load_file(self, file_path):
        """ Load file """
        logger_config.print_and_log_info("Load file:" + file_path)

        m3u_file_parser =  M3uFileParser()
        for m3u_entry in m3u_file_parser.parse_file(file_path):
            self._m3u_library.add(m3u_entry)
            
        if param.LIST_ALL_TITLES_CHANGED:
            logger_config.print_and_log_info("List titles changed")
            m3u_entries_with_title_changed_from_comprehensive = [x for x in self._m3u_library.m3u_entries if x.original_raw_title != x.title_as_valid_file_name]
            logger_config.print_and_log_info("m3u_entries_with_title_changed_from_comprehensive:" + str(len(m3u_entries_with_title_changed_from_comprehensive)))

            m3u_entries_with_title_changed_from_lambda = list(filter(lambda d : d.original_raw_title != d.title_as_valid_file_name, self._m3u_library.m3u_entries))
            logger_config.print_and_log_info("m3u_entries_with_title_changed:" + str(len(m3u_entries_with_title_changed_from_lambda)))
            
            with open(param.ALL_TITLES_CHANGED_FILE_NAME_BEFORE, "w", encoding="utf-8") as file:
                attr=(o.original_raw_title for o in m3u_entries_with_title_changed_from_lambda)
                list_original_title = list(attr)
                list_original_title = list(map(lambda x: x + Dependencies.Common.Constants.end_line_character_in_text_file, list_original_title))
                file.writelines(list_original_title)
                logger_config.print_and_log_info(param.ALL_TITLES_CHANGED_FILE_NAME_BEFORE + " filled")

    
            with open(param.ALL_TITLES_CHANGED_FILE_NAME_AFTER, "w", encoding="utf-8") as file:
                attr=(o.title_as_valid_file_name for o in m3u_entries_with_title_changed_from_lambda)
                list_title_changed = list(attr)
                list_title_changed = list(map(lambda x: x + Dependencies.Common.Constants.end_line_character_in_text_file, list_title_changed))
                file.writelines(list_title_changed)
                logger_config.print_and_log_info(param.ALL_TITLES_CHANGED_FILE_NAME_AFTER + " filled")


        
    @property
    def m3u_library(self) -> M3uEntriesLibrary :
        """ getter """
        return self._m3u_library

    @m3u_library.setter
    def m3u_library(self, value:M3uEntriesLibrary):
        self._m3u_library = value

if __name__ == "__main__":
    # sys.argv[1:]
    
    main = importlib.import_module("main")
    main.main()
