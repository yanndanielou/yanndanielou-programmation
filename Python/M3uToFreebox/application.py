# -*-coding:Utf-8 -*

import random


import sys

import Dependencies.Logger.LoggerConfig as LoggerConfig
import Dependencies.Common.date_time_formats as date_time_formats

import m3u

import M3uToFreebox_main


class M3uToFreeboxApplication:
    """ Application """

    def __init__(self, main_view:M3uToFreebox_main.M3uToFreeboxMainView):

        self._m3u_library: m3u.M3uEntriesLibrary = m3u.M3uEntriesLibrary()
        self._main_view:M3uToFreebox_main.M3uToFreeboxMainView = main_view
        
    def load_file(self, file_path):
        m3u_file_parser =  m3u.M3uFileParser()
        self._m3u_entries = m3u_file_parser.parse_file(file_path)
        self._tab_list_details.fill_m3u_entries(self._m3u_entries)