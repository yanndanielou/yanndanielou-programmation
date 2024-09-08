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

import logging

import os
from urllib.parse import urlparse
from urllib.request import urlretrieve

import random

import json

import sys

import re

import time

import requests

from bs4 import BeautifulSoup

sys.path.append('.')
sys.path.append('../Common')
import date_time_formats

import LoggerConfig

import jsonInstructions
import webSiteResults

import param


MAILTO_TYPE = 'mailto'
LINK_TO_CHAPTER_IN_SAME_PAGE_TYPE = 'linkToChapterInSamePageType'

application_start_time = time.time()

def print_current_status(results: webSiteResults.WebSiteResults):
    print('Duration since application start: ' + date_time_formats.format_duration_to_string(time.time() - application_start_time) + " s. So far: " + str(len(results.filesDownloadedUrls)) + " files downloaded and " + str(len(results._alreadyProcessedLinksUrls))  + " pages treated")


def saveResultsToJsonFile(initialInstructions: jsonInstructions.JsonInstructions, results: webSiteResults.WebSiteResults):
    json_file_full_path = param.output_directory + '/' + initialInstructions._outputResultJsonFile

    json_file = open(json_file_full_path, "w")
            
    result_json_dump = json.dumps(results, indent=4, cls=webSiteResults.WebSiteResultsEncoder)

    json_file.write(result_json_dump)

    json_file.close()
    


def processJsonInstructionFile(json_instruction_file_path: str):
    with open(json_instruction_file_path) as fh:
        jsonInstructionsDict = json.loads(fh.read())
        print(jsonInstructionsDict)

        results = webSiteResults.WebSiteResults()
        initialInstructions = jsonInstructions.JsonInstructions(jsonInstructionsDict)
        downloadAllFilesFromWebPageLink(initialInstructions._mainPage, initialInstructions, results)

        saveResultsToJsonFile(initialInstructions, results)

def dowloadFilesFromURL(urls: set[str], results: webSiteResults.WebSiteResults) -> None:
    for url in urls:
        dowloadFileFromURL(url, results)

#@LoggerConfig.execution_time
def dowloadFileFromURL(url: str, results: webSiteResults.WebSiteResults) -> None:
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
                
    #printCurrentStatus(results)

def isLinkToChapterInSamePage(url: str):
    patternAsString = '^#[a-zA-Z\\_0-9]*$'
    pattern = re.compile(patternAsString)
    match = pattern.match(url)
    return match is not None

def get_content_type(url: str):
    if 'mailto:' in url:
        return MAILTO_TYPE
    
    if isLinkToChapterInSamePage(url):
        return LINK_TO_CHAPTER_IN_SAME_PAGE_TYPE

    response = requests.head(url)
    return response.headers['Content-Type']

def isWebPage(url: str):
    return 'text/html' in get_content_type(url) 

def is_url_a_file_that_has_already_be_downloaded(url: str, results: webSiteResults.WebSiteResults):
    return results.has_already_treated_file(url)

def is_url_a_file_that_must_be_downloaded(url: str, initialInstructions: jsonInstructions.JsonInstructions, results: webSiteResults.WebSiteResults):

    for filesExtensionToDownload in initialInstructions._filesExtensionsToDownload:
        if url.endswith(filesExtensionToDownload):
            return True
        
    return False

def is_url_a_page_that_must_be_parsed(url, initialInstructions: jsonInstructions.JsonInstructions, results: webSiteResults.WebSiteResults):
    
    for pageToExclude in initialInstructions._pagesToExclude:
        if pageToExclude in url:
            logging.info(url + " must be excluded because matches:" + url)
            return False

    # To be tested early to avoid heavy treatment    
    if url in results._alreadyProcessedLinksUrls:
        logging.info(url + " already processed")
        return False

    if is_url_a_file_that_must_be_downloaded(url, initialInstructions, results) or is_url_a_file_that_has_already_be_downloaded(url, results):
        logging.info(url + " is a file, not a page!")
        return False

    page_is_among_those_to_include_in_instructions = False
    for pageToInclude in initialInstructions._pagesToInclude:
        if pageToInclude in url:
            logging.info(url + " must be processed because matchs:" + url)
            page_is_among_those_to_include_in_instructions = True
    
    if not page_is_among_those_to_include_in_instructions:
        logging.info(url + " must not be processed because does not match any allowed link")
        return False

    url_content_type = None
    try:
        url_content_type = get_content_type(url)
    except:
        LoggerConfig.printAndLogError(url + " could not get content type")
        results._failedToProcessUrls.add(url)
        return False
        
    logging.info(url + " is type: " + url_content_type)

    if not isWebPage(url):
        logging.info(url + " is not a web page but is:" + url_content_type)
        return False
    
    return True


def retrieveFilesToDownloadURLs(url: str, aHrefLinks: set[str], initialInstructions: jsonInstructions.JsonInstructions, results: webSiteResults.WebSiteResults):
    newFilesToDownloadUrls = set[str]()
    for aHrefLink in aHrefLinks:
        if is_url_a_file_that_must_be_downloaded(aHrefLink, initialInstructions, results):
            if not is_url_a_file_that_has_already_be_downloaded(url, results):
                LoggerConfig.printAndLogInfo("Must download:" + url)
                newFilesToDownloadUrls.add(aHrefLink)
            else:
                logging.debug(aHrefLink + " has already been downloaded")
        else:
            results._filesIgnoredUrls.add(aHrefLink)

    LoggerConfig.printAndLogInfo("In " + url + ", " + str(len(newFilesToDownloadUrls)) + " files to download")
    return newFilesToDownloadUrls


def processSubLinks(aHrefLinks, initialInstructions: jsonInstructions.JsonInstructions, results: webSiteResults.WebSiteResults) -> None:
    
    for aHrefLink in aHrefLinks:
        if is_url_a_page_that_must_be_parsed(aHrefLink, initialInstructions, results):
            downloadAllFilesFromWebPageLink(aHrefLink, initialInstructions, results)
        else:
            results._notProcessedUrls.add(aHrefLink)

def downloadAllFilesFromWebPageLink(url, initialInstructions: jsonInstructions.JsonInstructions, results: webSiteResults.WebSiteResults) -> None:
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

    newFilesToDownloadUrls = set[str](retrieveFilesToDownloadURLs(url, aHrefLinks, initialInstructions , results))
    dowloadFilesFromURL(newFilesToDownloadUrls, results)

    LoggerConfig.printAndLogInfo("After processing " + url + " (without children)")
    print_current_status(results)

    if initialInstructions._exploreLinks:
        processSubLinks(aHrefLinks, initialInstructions, results)

    LoggerConfig.printAndLogInfo("After processing " + url + ", (with children) ")
    print_current_status(results)


def main(argv):
   
    log_file_name = os.path.basename(__file__) +  str(random.randrange(100000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    
    LoggerConfig.printAndLogInfo('Start application')
    
    processJsonInstructionFile(param.json_instruction_file_path)
    
    LoggerConfig.printAndLogInfo("End. Nominal end of application in " + date_time_formats.format_duration_to_string(time.time() - application_start_time))
    

if __name__ == "__main__":
   main(sys.argv[1:])