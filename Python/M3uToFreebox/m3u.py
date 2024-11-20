# -*-coding:Utf-8 -*

import unittest


import Dependencies.Logger.LoggerConfig as LoggerConfig
import Dependencies.Common.Constants


MRU_FIRST_LINE = "#EXTM3U"
M3U_ENTRY_FIRST_LINE_BEGIN = "#EXTINF"


class M3uEntryStringDefinition:
    """ To build M3uEntry """
    def __init__(self) -> None:
        self._line1 = None
        self._line2 = None

    @property
    def line1(self):
        return self._line1

    @line1.setter
    def line1(self, value):
        self._line1 = value

    @property
    def line2(self):
        return self._line2

    @line2.setter
    def line2(self, value):
        self._line2 = value

class M3uEntry:
    """ M3u entry"""
    def __init__(self, current_m3u_entry_lines_definition: M3uEntryStringDefinition) -> None:
        
        self._link = current_m3u_entry_lines_definition.line2
        self._line1 = current_m3u_entry_lines_definition.line1
        
        tvg_id = self.decode_field(self._line1, "tvg-id")
        tvg_name = self.decode_field(self._line1, "tvg-name")
        tvg_name = self.decode_field(self._line1, "tvg-name")
        tvg_logo = self.decode_field(self._line1, "tvg-logo")
        group_title = self.decode_field(self._line1, "group-title")
        title = self._line1.split('"')[len(self._line1.split('"'))-1]
        pause = 1
        
    def decode_field(self, line, field_name):
        field_content = line.split(field_name)[1].split('"')[1]
        pause = 1
        
        return ""
        



class M3uFileParser:
    """ Parse m3u file """
    def __init__(self) -> None:
        pass
    
    def parse_file(self,file_path) -> set[M3uEntry]:
        m3u_entries: set[M3uEntry] = set()
        with open(file_path, "r", encoding="utf-8") as file:
            content = file.read()

            current_m3u_entry_string_definition = None
            lines = content.split(Dependencies.Common.Constants.end_line_character_in_text_file)
            for line in lines:
                if MRU_FIRST_LINE == line:
                    continue
                                
                if line.startswith(M3U_ENTRY_FIRST_LINE_BEGIN):
                    current_m3u_entry_string_definition = M3uEntryStringDefinition()
                    current_m3u_entry_string_definition.line1 = line
                    
                if not line.startswith(M3U_ENTRY_FIRST_LINE_BEGIN):
                    current_m3u_entry_string_definition.line2 = line
                    m3u_entry = M3uEntry(current_m3u_entry_string_definition)
                    m3u_entries.add(m3u_entry)
                    LoggerConfig.logging.debug("M3u entry created: " + str(m3u_entry))

                  
               
        LoggerConfig.printAndLogInfo("File " + file_path + " parsed. " + str(len(m3u_entries)) + " M3u entries found")
        return m3u_entries


