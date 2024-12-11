""" Filters to search M3u entry by criteria """
# -*-coding:Utf-8 -*

from abc import ABC
from abc import abstractmethod


import Dependencies.Common.string_utils as string_utils
import Dependencies.Logger.logger_config as logger_config

import m3u

class M3uEntryByTitleFilter:
    """ base class """
    def __init__(self, case_sensitive:bool, label:str):
        self._case_sensitive:bool = case_sensitive
        self._label:str = label
      
    @abstractmethod
    def match_m3u(self, m3u_entry:m3u.M3uEntry, filter_text:str) -> bool:
        """ Check if match m3u """
        raise NotImplementedError()


class TitleContainsExactlyFilter(M3uEntryByTitleFilter):
    """ TitleContainsExactlyFilter """
    def __init__(self, case_sensitive, label):
        super().__init__(case_sensitive, label)
        pass
    
    def match_m3u(self, m3u_entry, filter_text):
        if self._case_sensitive:
            return filter_text in m3u_entry.title
        return filter_text.lower() in m3u_entry.title.lower()
        

    
