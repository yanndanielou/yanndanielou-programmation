""" Filters to search M3u entry by criteria """
# -*-coding:Utf-8 -*

from Dependencies.Common.singleton import Singleton

class DestinationsFolders(metaclass=Singleton):
    """ Manager of m3u filters"""
    def __init__(self):
        self._destinations_folders:list[tuple[str, str]] = [("Freebox", "\\\\Freebox_Server\\NO NAME\\M3U_Playlist"),("Local", "\\\\Freebox_Server\\NO NAME\\M3U_Playlist")]


    def add_destination(self, name:str, path:str):
        """ Add destinations """
        self._destinations_folders.append((name, path))
        
    @property
    def destinations_folders(self):
        """ Getter filters """
        #return self._destinations_folders
        return self._destinations_folders

