# -*-coding:Utf-8 -*


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
        

