# -*-coding:Utf-8 -*

import random


import sys

import Dependencies.Logger.LoggerConfig as LoggerConfig
import Dependencies.Common.date_time_formats as date_time_formats

import m3u

import main_view


class M3uToFreeboxApplication:
    """ Application """

    def __init__(self, main_view):

        self._m3u_library: m3u.M3uEntriesLibrary = m3u.M3uEntriesLibrary()
        self._main_view:main_view.M3uToFreeboxMainView = main_view
        
    def load_file(self, file_path):
        """ Load file """
        LoggerConfig.printAndLogInfo("Load file:" + file_path)

        m3u_file_parser =  m3u.M3uFileParser()
        for m3u_entry in m3u_file_parser.parse_file(file_path):
            self._m3u_library.add(m3u_entry)
        