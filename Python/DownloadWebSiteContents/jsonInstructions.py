# -*-coding:Utf-8 -*


class JsonInstructions:

    def __init__(self, jsonInstructionsDict):
        self._mainPage = jsonInstructionsDict['mainPage']
        self._outputResultJsonFile = jsonInstructionsDict['outputResultJsonFile']
        self._pagesToInclude = jsonInstructionsDict['pagesToInclude']
        self._pagesToExclude = jsonInstructionsDict['pagesToExclude']
        self._filesExtensionsToDownload = jsonInstructionsDict['filesExtensionsToDownload']
        self._filesToNotDownload = jsonInstructionsDict['filesToNotDownload']
        self._exploreLinks = bool(jsonInstructionsDict['exploreLinks'])

    @property
    def mainPage(self):
        return self._mainPage

    @mainPage.setter
    def mainPage(self, value):
        self._mainPage = value

    @property
    def outputResultJsonFile(self):
        return self._outputResultJsonFile

    @outputResultJsonFile.setter
    def outputResultJsonFile(self, value):
        self._outputResultJsonFile = value

    @property
    def pagesToInclude(self):
        return self._pagesToInclude

    @pagesToInclude.setter
    def pagesToInclude(self, value):
        self._pagesToInclude = value

    @property
    def pagesToExclude(self):
        return self._pagesToExclude

    @pagesToExclude.setter
    def pagesToExclude(self, value):
        self._pagesToExclude = value

    @property
    def filesExtensionsToDownload(self):
        return self._filesExtensionsToDownload

    @filesExtensionsToDownload.setter
    def filesExtensionsToDownload(self, value):
        self._filesExtensionsToDownload = value

    @property
    def filesToNotDownload(self):
        return self._filesToNotDownload

    @filesToNotDownload.setter
    def filesToNotDownload(self, value):
        self._filesToNotDownload = value

    @property
    def exploreLinks(self):
        return self._exploreLinks

    @exploreLinks.setter
    def exploreLinks(self, value):
        self._exploreLinks = value

        