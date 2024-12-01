# -*-coding:Utf-8 -*

import pyconvert.pyconv



class XspfFileContent:
    """  """
    class Track:
        """ """
        
        class Extension:
            """ """
            def __init__(self, vlc_id = 0, application = "http://www.videolan.org/vlc/playlist/0") -> None:
                self._vlc_id = vlc_id
                self._application = application
        
        
        def __init__(self, location, duration = 9999) -> None:
            self._location = location
            self._duration = duration
            self._extension = XspfFileContent.Track.Extension()
    
    
    def __init__(self, title:str, location:str) -> None:
        self._title:str = title
        self._tracks:list[XspfFileContent.Track] = []
        self._tracks.append(XspfFileContent.Track(location))
    


class XspfFileCreator:
    """  """
    def __init__(self) -> None:
        pass
    
    def write(self, xspf_file_content:XspfFileContent, output_file_name: str) -> None:
        xml_content = pyconvert.pyconv.convert2XML(xspf_file_content)
        pretty_xml = xml_content.toprettyxml()
        print(pretty_xml)
        
        with open(output_file_name, 'w') as f:
              f.write(pretty_xml)
    
