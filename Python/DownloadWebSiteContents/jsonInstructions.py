# -*-coding:Utf-8 -*


class JsonInstructions:

    def __init__(self, jsonInstructionsDict):
        self._mainPage = jsonInstructionsDict['mainPage']
        self._pagesToInclude = jsonInstructionsDict['pagesToInclude']
        self._pagesToExclude = jsonInstructionsDict['pagesToExclude']
        self._filesExtensionsToDownload = jsonInstructionsDict['filesExtensionsToDownload']
        self._filesToNotDownload = jsonInstructionsDict['filesToNotDownload']
        