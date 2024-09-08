# -*-coding:Utf-8 -*
import json
from json import JSONEncoder


class WebSiteResults:
    
    def __init__(self):
        self._filesDownloadedUrls = set()
        self._filesIgnoredUrls = set()
        self._failedToProcessUrls = set()
        self._alreadyProcessedLinksUrls = set()
        self._lastFilesDownloadedUrls = set()
        self._failedDownloadedUrls = set()
        self._notProcessedUrls = set()

    @property
    def filesDownloadedUrls(self):
        return self._filesDownloadedUrls

    @filesDownloadedUrls.setter
    def filesDownloadedUrls(self, value):
        self._filesDownloadedUrls = value

    @property
    def filesIgnoredUrls(self):
        return self._filesIgnoredUrls

    @filesIgnoredUrls.setter
    def filesIgnoredUrls(self, value):
        self._filesIgnoredUrls = value

    @property
    def failedToProcessUrls(self):
        return self._failedToProcessUrls

    @failedToProcessUrls.setter
    def failedToProcessUrls(self, value):
        self._failedToProcessUrls = value

    @property
    def alreadyProcessedLinksUrls(self):
        return self._alreadyProcessedLinksUrls

    @alreadyProcessedLinksUrls.setter
    def alreadyProcessedLinksUrls(self, value):
        self._alreadyProcessedLinksUrls = value

    @property
    def lastFilesDownloadedUrls(self):
        return self._lastFilesDownloadedUrls

    @lastFilesDownloadedUrls.setter
    def lastFilesDownloadedUrls(self, value):
        self._lastFilesDownloadedUrls = value

    @property
    def failedDownloadedUrls(self):
        return self._failedDownloadedUrls

    @failedDownloadedUrls.setter
    def failedDownloadedUrls(self, value):
        self._failedDownloadedUrls = value

    @property
    def notProcessedUrls(self):
        return self._notProcessedUrls

    @notProcessedUrls.setter
    def notProcessedUrls(self, value):
        self._notProcessedUrls = value



    def recordFilesDownloadedUrls(self, filesDownloadedUrls):
        self._lastFilesDownloadedUrls = filesDownloadedUrls
        self._filesDownloadedUrls.update(filesDownloadedUrls)

    def recordFileDownloadedUrl(self, fileDownloadedUrl):
        self._filesDownloadedUrls.add(fileDownloadedUrl)
        
    def has_already_treated_file(self, fileUrl: str):
        return fileUrl in self._filesDownloadedUrls

class WebSiteResultsEncoder(JSONEncoder):
        def default(self, obj):
            if isinstance(obj, set):
                return list(obj)
            #return json.JSONEncoder.default(self, obj)
            return obj.__dict__
        
