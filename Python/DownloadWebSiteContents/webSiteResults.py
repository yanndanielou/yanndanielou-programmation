# -*-coding:Utf-8 -*
import json
from json import JSONEncoder


class WebSiteResults:
    _failedDownloadedUrls = set()
    def __init__(self):
        self._filesDownloadedUrls = set()
        self._alreadyProcessedLinksUrls = set()
        self._lastFilesDownloadedUrls = set()


    def recordFilesDownloadedUrls(self, filesDownloadedUrls):
        self._lastFilesDownloadedUrls = filesDownloadedUrls
        self._filesDownloadedUrls.update(filesDownloadedUrls)

    def recordFileDownloadedUrl(self, fileDownloadedUrl):
        self._filesDownloadedUrls.add(fileDownloadedUrl)
        

class WebSiteResultsEncoder(JSONEncoder):
        def default(self, obj):
            if isinstance(obj, set):
                return list(obj)
            #return json.JSONEncoder.default(self, obj)
            return obj.__dict__
        
