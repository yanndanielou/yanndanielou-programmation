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
import webSiteResults

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

        results = webSiteResults.WebSiteResults()
        alreadyProcessedLinksUrls = set()
        filesDownloadedUrls = set()
        initialInstructions = jsonInstructions.JsonInstructions(jsonInstructionsDict)
        downloadAllFilesFromWebPageLink(initialInstructions._mainPage, initialInstructions, results)

        LoggerConfig.printAndLogInfo(str(len(filesDownloadedUrls)) + " files to download")

def dowloadFilesFromURL(urls, results):
    for url in urls:
        dowloadFileFromURL(url, results)

@LoggerConfig.execution_time
def dowloadFileFromURL(url, results):
    if not os.path.exists(param.output_directory):
        LoggerConfig.printAndLogInfo('Create output directory:' + param.output_directory)
        os.makedirs(param.output_directory)
    
    url_parsed = urlparse(url)
    file_path = url_parsed.path
    fileBaseName = os.path.basename(file_path)
    LoggerConfig.printAndLogInfo("Download " + fileBaseName + " from " + url)
    try:
        urlretrieve(url, param.output_directory + "/" + fileBaseName)
        results.recordFileDownloadedUrl(url)
        
    except:
        results._failedDownloadedUrls.add(url)
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

def fileMustBeDownload(url, initialInstructions, results):
    if url in results._filesDownloadedUrls:
        return False

    for filesExtensionToDownload in initialInstructions._filesExtensionsToDownload:
        if url.endswith(filesExtensionToDownload):
            LoggerConfig.printAndLogInfo("Must download:" + url)
            results._filesDownloadedUrls.add(url)
            return True
        
    return False

def linkMustBeProcessed(url, initialInstructions, results):
    
    for pageToExclude in initialInstructions._pagesToExclude:
        if pageToExclude in url:
            logging.info(url + " must be excluded because matches:" + url)
            return False
        
    url_content_type = get_content_type(url)
    logging.info(url + " is type: " + url_content_type)

    if not isWebPage(url):
        logging.info(url + " is not a web page but is:" + url_content_type)
        return False

    if url in results._alreadyProcessedLinksUrls:
        logging.info(url + " already processed")
        return False
    
    
    for pageToInclude in initialInstructions._pagesToInclude:
        if pageToInclude in url:
            logging.info(url + " must be processed because matchs:" + url)
            return True
        
    logging.info(url + " must not be processed because does not match any allowed link")
    return False




def retrieveFilesToDownloadURLs(url, aHrefLinks, initialInstructions, results):
    newFilesToDownloadUrls = set()
    for aHrefLink in aHrefLinks:
        if fileMustBeDownload(aHrefLink, initialInstructions, results):
            newFilesToDownloadUrls.add(aHrefLink)

    LoggerConfig.printAndLogInfo("After " + url + ", " + str(len(results._filesDownloadedUrls)) + " files to download in total")
    return newFilesToDownloadUrls


def processSubLinks(aHrefLinks, initialInstructions, results):
    
    subLinksToProcess = set()
    for aHrefLink in aHrefLinks:
        if linkMustBeProcessed(aHrefLink, initialInstructions, results):
            subLinksToProcess.add(aHrefLink)
            downloadAllFilesFromWebPageLink(aHrefLink, initialInstructions, results)

    return subLinksToProcess

def downloadAllFilesFromWebPageLink(url, initialInstructions, results):
    LoggerConfig.printAndLogInfo("process link:" + url)
    results._alreadyProcessedLinksUrls.add(url)
    
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

    newFilesToDownloadUrls = retrieveFilesToDownloadURLs(url, aHrefLinks, initialInstructions, results)
    LoggerConfig.printAndLogInfo(str(len(newFilesToDownloadUrls)) +  " new files to download")

    dowloadFilesFromURL(newFilesToDownloadUrls, results)

    LoggerConfig.printAndLogInfo("After processing " + url + " (without children), " + str(len(results._filesDownloadedUrls)) + " files already downloaded, and " + str(len(results._alreadyProcessedLinksUrls)) + " links processed")
    print('Duration since application start: ' + format(time.time() - application_start_time, '.2f'))

    processSubLinks(aHrefLinks, initialInstructions, results)

    LoggerConfig.printAndLogInfo("After processing " + url + ", (with children) " + str(len(results._filesDownloadedUrls)) + " files to already downloaded, and " + str(len(results._alreadyProcessedLinksUrls)) + " links processed")
    print('Duration since application start: ' + format(time.time() - application_start_time, '.2f'))


def main(argv):
   
    log_file_name = os.path.basename(__file__) +  str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    
    LoggerConfig.printAndLogInfo('Start application')
    
    processJsonInstructionFile(param.json_instruction_file_path)
    
    LoggerConfig.printAndLogInfo("End. Nominal end of application")
    

if __name__ == "__main__":
   main(sys.argv[1:])