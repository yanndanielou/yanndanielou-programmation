# -*-coding:Utf-8 -*


class WebSiteResults:

    def __init__(self):
        self._filesDownloadedUrls = set()
        self._alreadyProcessedLinksUrls = set()
        self._lastFilesDownloadedUrls = set()
        self._failedDownloadedUrls = set()


    def recordFilesDownloadedUrls(self, filesDownloadedUrls):
        self._lastFilesDownloadedUrls = filesDownloadedUrls
        self._filesDownloadedUrls.update(filesDownloadedUrls)

    def recordFileDownloadedUrl(self, fileDownloadedUrl):
        self._filesDownloadedUrls.add(fileDownloadedUrl)
        