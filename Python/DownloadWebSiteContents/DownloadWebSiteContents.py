# -*-coding:Utf-8 -*
#
#
# Input: file with columns
#metric_name,metric_timestamp,_value
#project.mesure,2024-07-15T00:25:46.992+03:00,"-1"
#
# Output: file with columns
#metric_timestamp,metric_name,_value
#2024-06-12T00:43:37.495+00:00,Computer.LogicalDisk_D.Free_Space,73.252541527397269761

from bs4 import BeautifulSoup

import LoggerConfig

import jsonInstructions

import logging
import param

import os
from urllib.parse import urlparse

import random

from urllib.request import urlretrieve


import json
from types import SimpleNamespace

import requests
import urllib.request

import subprocess
import glob
import os
from os.path import basename
import sys

import zipfile

import re

import param

import pandas

import shutil

import time

#import datetime
import time

mailtoType = 'mailto'
linkToChapterInSamePageType = 'linkToChapterInSamePageType'

application_start_time = time.time()


def processJsonInstructionFile(json_instruction_file_path):
    with open(json_instruction_file_path) as fh:
        jsonInstructionsDict = json.loads(fh.read())
        print(jsonInstructionsDict)

        alreadyProcessedLinksUrls = set()
        filesDownloadedUrls = set()
        initialInstructions = jsonInstructions.JsonInstructions(jsonInstructionsDict)
        downloadAllFilesFromWebPageLink(initialInstructions._mainPage, initialInstructions, alreadyProcessedLinksUrls,filesDownloadedUrls)

        LoggerConfig.printAndLogInfo(str(len(filesDownloadedUrls)) + " files to download")

def dowloadFilesFromURL(urls):
    for url in urls:
        dowloadFileFromURL(url)

def dowloadFileFromURL(url):
    url_parsed = urlparse(url)
    file_path = url_parsed.path
    fileBaseName = os.path.basename(file_path)
    LoggerConfig.printAndLogInfo("Download " + fileBaseName + " from " + url)
    try:
        urlretrieve(url, fileBaseName)
    except:
        LoggerConfig.printAndLogError("Failed to download " + fileBaseName + " from " + url)
                
    print('Duration since application start: ' + format(time.time() - application_start_time, '.2f'))

def isLinkToChapterInSamePage(url):
    patternAsString = '^#[a-zA-Z\\_0-9]*$'
    pattern = re.compile(patternAsString)
    match = pattern.match(url)
    return match is not None

def get_content_type(url):
    if 'mailto:' in url:
        return mailtoType
    
    if isLinkToChapterInSamePage(url):
        return linkToChapterInSamePageType
    
    if '#blognav' in url:
        pause = 1
    if '#search' in url:
        pause = 1
    if '#main' in url:
        pause = 1

    response = requests.head(url)
    return response.headers['Content-Type']

def isWebPage(url):
    return 'text/html' in get_content_type(url) 

def fileMustBeDownload(url, initialInstructions, filesToDownloadUrls):
    if url in filesToDownloadUrls:
        return False

    for filesExtensionToDownload in initialInstructions._filesExtensionsToDownload:
        if url.endswith(filesExtensionToDownload):
            LoggerConfig.printAndLogInfo("Must download:" + url)
            filesToDownloadUrls.add(url)
            return True
        
    return False

def linkMustBeProcessed(url, initialInstructions, alreadyProcessedLinks):
    
    for pageToExclude in initialInstructions._pagesToExclude:
        if pageToExclude in url:
            logging.info(url + " must be excluded because matches:" + url)
            return False
        
    url_content_type = get_content_type(url)
    logging.info(url + " is type: " + url_content_type)

    if not isWebPage(url):
        logging.info(url + " is not a web page but is:" + url_content_type)
        return False

    if url in alreadyProcessedLinks:
        logging.info(url + " already processed")
        return False
    
    
    for pageToInclude in initialInstructions._pagesToInclude:
        if pageToInclude in url:
            logging.info(url + " must be processed because matchs:" + url)
            return True
        
    logging.info(url + " must not be processed because does not match any allowed link")
    return False




def retrieveFilesToDownloadURLs(url, aHrefLinks, initialInstructions, filesAlreadedDownloadedUrls):
    newFilesToDownloadUrls = set()
    for aHrefLink in aHrefLinks:
        if fileMustBeDownload(aHrefLink, initialInstructions, filesAlreadedDownloadedUrls):
            newFilesToDownloadUrls.add(aHrefLink)

    LoggerConfig.printAndLogInfo("After " + url + ", " + str(len(filesAlreadedDownloadedUrls)) + " files to download in total")
    return newFilesToDownloadUrls


def processSubLinks(aHrefLinks, initialInstructions, alreadyProcessedLinks, filesToDownloadUrls):
    
    subLinksToProcess = set()
    for aHrefLink in aHrefLinks:
        if linkMustBeProcessed(aHrefLink, initialInstructions, alreadyProcessedLinks):
            subLinksToProcess.add(aHrefLink)
            downloadAllFilesFromWebPageLink(aHrefLink, initialInstructions, alreadyProcessedLinks, filesToDownloadUrls)

    return subLinksToProcess

def downloadAllFilesFromWebPageLink(url, initialInstructions, alreadyProcessedLinks, filesAlreadyDownloadedUrls):
    LoggerConfig.printAndLogInfo("process link:" + url)
    alreadyProcessedLinks.add(url)
    
    #page = urllib.request.urlopen(url)
    #data = page.read()
    #html_content_unused = data.decode('utf-8')
    #print(html_content)

    html_content = requests.get(url).content
    bsObj = BeautifulSoup(html_content, 'lxml')

    aLinks = bsObj.findAll('a')
    aHrefLinks = set()
    for linkUrl in aLinks:
        aHrefLinks.add(linkUrl.attrs['href'])

    newFilesToDownloadUrls = retrieveFilesToDownloadURLs(url, aHrefLinks, initialInstructions, filesAlreadyDownloadedUrls)
    dowloadFilesFromURL(newFilesToDownloadUrls)
    filesAlreadyDownloadedUrls.update(newFilesToDownloadUrls)

    LoggerConfig.printAndLogInfo("After processing " + url + " (without children), " + str(len(filesAlreadyDownloadedUrls)) + " files already downloaded, and " + str(len(alreadyProcessedLinks)) + " links processed")
    print('Duration since application start: ' + format(time.time() - application_start_time, '.2f'))

    processSubLinks(aHrefLinks, initialInstructions, alreadyProcessedLinks, filesAlreadyDownloadedUrls)

    LoggerConfig.printAndLogInfo("After processing " + url + ", (with children) " + str(len(filesAlreadyDownloadedUrls)) + " files to already downloaded, and " + str(len(alreadyProcessedLinks)) + " links processed")
    print('Duration since application start: ' + format(time.time() - application_start_time, '.2f'))

    return filesAlreadyDownloadedUrls

def main(argv):
   
    log_file_name = os.path.basename(__file__) +  str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    
    LoggerConfig.printAndLogInfo('Start application')
    
    processJsonInstructionFile(param.json_instruction_file_path)
    
    LoggerConfig.printAndLogInfo("End. Nominal end of application")
    

if __name__ == "__main__":
   main(sys.argv[1:])