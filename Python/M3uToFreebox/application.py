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
        
