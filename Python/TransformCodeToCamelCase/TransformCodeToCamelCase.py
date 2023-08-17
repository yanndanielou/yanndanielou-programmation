# -*-coding:Utf-8 -*


# For logs
import random
import logging
import os
import sys

# To get line number for logs
from inspect import currentframe, getframeinfo
import inspect

# Dates
import datetime
import time

# For args
import argparse

import getopt

# Regex
import re

# param
end_line_character_in_text_file = "\n"
logger_level = logging.INFO

camelCaseRegexPatternAsString = "^[a-z][a-zA-Z0-9]*$"
camelCaseRegexCompiledPattern = re.compile(camelCaseRegexPatternAsString)

classRegexPatternAsString = "^[A-Z][a-zA-Z0-9]*$"


javaAnnotationRegexPatternAsString = "^@[a-zA-Z0-9]*$"

constantRegexPatternAsString = "^[A-Z][_a-zA-Z0-9]*$"

wordRegexPatternAsString = "^[a-zA-Z][_a-zA-Z0-9]*$"
wordRegexCompiledPattern = re.compile(wordRegexPatternAsString)

wordContainingUnderscoreToTranformRegexPatternAsString = "^[a-zA-Z]*[_([a-zA-Z0-9])]*$"
wordContainingUnderscoreToTranformRegexPatternAsString = "^[a-zA-Z]*[_([a-zA-Z0-9])]*$"

# wordContainingUnderscoreToTranformRegexCompiledPattern = re.compile(wordContainingUnderscoreToTranformRegexPatternAsString)


allowedRegexPatternAsStringsList = [
    camelCaseRegexPatternAsString, classRegexPatternAsString, constantRegexPatternAsString]
allowedRegexCompiledPatternsList = [re.compile(
    i) for i in allowedRegexPatternAsStringsList]


def printAndLogCriticalAndKill(toPrintAndLog):
    log_timestamp = time.asctime(time.localtime(time.time()))

    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno

    print(log_timestamp + '\t' + "line#" +
          str(line_number) + '\t' + toPrintAndLog)
    logging.critical("line#" + str(line_number) + '\t' + toPrintAndLog)
    sys.exit()


def printAndLogInfo(toPrintAndLog):

    log_timestamp = time.asctime(time.localtime(time.time()))

    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno

    print(log_timestamp + '\t' + "line#" +
          str(line_number) + '\t' + str() + toPrintAndLog)
    logging.info("line#" + str(line_number) + '\t' + toPrintAndLog)


def printAndLogWarning(toPrintAndLog):
    log_timestamp = time.asctime(time.localtime(time.time()))

    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno

    print(log_timestamp + '\t' + "line#" +
          str(line_number) + '\t' + toPrintAndLog)
    logging.warning("line#" + str(line_number) + '\t' + toPrintAndLog)


def printAndLogError(toPrintAndLog):
    log_timestamp = time.asctime(time.localtime(time.time()))

    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno

    print(log_timestamp + '\t' + "!!ERROR!!")
    print(log_timestamp + '\t' "line#" + str(line_number))
    print(log_timestamp + '\t' + toPrintAndLog)
    logging.error("line#" + str(line_number) + '\t' + toPrintAndLog)


def configureLogger(log_file_name):
    logger_directory = "logs"

    if not os.path.exists(logger_directory):
        os.makedirs(logger_directory)

    print(time.asctime(time.localtime(time.time())) +
          '\t' + "Logger level:" + str(logger_level))

    logging.basicConfig(level=logger_level,
                        format='%(asctime)s %(levelname)-8s %(message)s',
                        datefmt='%a, %d %b %Y %H:%M:%S',
                        filename=logger_directory + '\\' + log_file_name,
                        filemode='w')
    # logging.debug
    # logging.info
    # logging.warning
    # logging.error
    # logging.critical


# Creating a function to
# replace the text
def replacetext(search_text, replace_text):

    # Opening the file in read and write mode
    with open('SampleFile.txt', 'r+') as f:

        # Reading the file data and store
        # it in a file variable
        file = f.read()

        # Replacing the pattern with the string
        # in the file data
        file = re.sub(search_text, replace_text, file)

        # Setting the position to the top
        # of the page to insert data
        f.seek(0)

        # Writing replaced data in the file
        f.write(file)

        # Truncating the file size
        f.truncate()

    # Return "Text replaced" string
    return "Text replaced"


def getAllTextWordsInFile(fileReadContent):
    allTexts = set()

    currentWord = ""
    for letter in fileReadContent:
        if letter.isLetter():
            a = 2

        logging.info("Letter:" + letter)

    return allTexts


def getAllUndescoreContainingTextWordsInFile(fileReadContent):

    allUndescoreContainingTextWordsAsSet = set()

    fileReadContent = fileReadContent.replace(
        end_line_character_in_text_file, " ")
    fileReadContent = fileReadContent.replace(".", " ")
    fileReadContent = fileReadContent.replace("  ", " ")
    fileReadContent = fileReadContent.replace("\t", " ")

    fileReadContent = fileReadContent.replace("(", " ")
    fileReadContent = fileReadContent.replace(")", " ")
    fileReadContent = fileReadContent.replace(";", " ")

    allCodeSyntaxElementsAsList = fileReadContent.split(" ")
    allCodeSyntaxElementsAsSet = set(allCodeSyntaxElementsAsList)
    for codeSyntaxElement in allCodeSyntaxElementsAsSet:
        if "_" in codeSyntaxElement:
            allUndescoreContainingTextWordsAsSet.add(codeSyntaxElement)

    return allUndescoreContainingTextWordsAsSet


def getAllTextWordsInFileOldWay(fileReadContent):
    allWords = set()

    # fileReadContent = fileReadContent.replace("<"," ")
    # fileReadContent = fileReadContent.replace("="," ")
    # fileReadContent = fileReadContent.replace(">"," ")
    # fileReadContent = fileReadContent.replace("&"," ")

    # fileReadContent = fileReadContent.replace("+"," ")
    # fileReadContent = fileReadContent.replace("-"," ")

    # fileReadContent = fileReadContent.replace(","," ")
    # fileReadContent = fileReadContent.replace("."," ")
    # fileReadContent = fileReadContent.replace(":"," ")
    # fileReadContent = fileReadContent.replace(end_line_character_in_text_file," ")
    # fileReadContent = fileReadContent.replace("("," ")
    # fileReadContent = fileReadContent.replace(")"," ")
    # fileReadContent = fileReadContent.replace(";"," ")
    # fileReadContent = fileReadContent.replace("->"," ")

    # fileReadContent = fileReadContent.replace("}"," ")
    parentFolderContainingCode = "C:\\Temp\\TowerDefense"
    # fileReadContent = fileReadContent.replace("{"," ")
    # fileReadContent = fileReadContent.replace("*"," ")

    fileReadContent = fileReadContent.replace(
        end_line_character_in_text_file, " ")

    for fileReadContentSplit in fileReadContent.split():
        if wordRegexCompiledPattern.match(fileReadContentSplit):
            allWords.add(fileReadContentSplit)
        else:
            logging.info("Ignored because not a word:" + fileReadContentSplit)

    return allWords


def getUnderscoreContainingCodeWordTransformedIntoCamelCase(initialWordWithUnderscore):
    codeWordTransformedIntoCamelCase = ""
    nextLetterMustBeCapitalLetter = False

    for letter in initialWordWithUnderscore:
        if letter == "_":
            nextLetterMustBeCapitalLetter = True
        elif nextLetterMustBeCapitalLetter:
            codeWordTransformedIntoCamelCase += letter.upper()
            nextLetterMustBeCapitalLetter = False
        else:
            codeWordTransformedIntoCamelCase += letter

    printAndLogInfo(initialWordWithUnderscore +
                    " transformed to camel case is:" + codeWordTransformedIntoCamelCase)
    return codeWordTransformedIntoCamelCase


def getAllNonCamelCaseTextInFileAsUniqueList(fileReadContent):
    allNonCamelCaseTextInFileAsSet = set()
    allWordsInFile = getAllTextWordsInFileOldWay(fileReadContent)
    for word in allWordsInFile:
        wordIsAllowed = False
        for allowedRegexCompiledPattern in allowedRegexCompiledPatternsList:
            if allowedRegexCompiledPattern.match(word) is not None:
                wordIsAllowed = True
                break
        if not wordIsAllowed:
            printAndLogInfo(word + " is not camel case")
            allNonCamelCaseTextInFileAsSet.add(word)

    return allNonCamelCaseTextInFileAsSet


def replaceAllTextByCamelCaseInFile(fileFullPath, path, fileNameWithoutExtension, fileExtension, noOperation):
    camelCaseFileContent = None

    # Opening the file in read and write mode
    with open(fileFullPath, 'r+') as f:
        # Reading the file data and store
        # it in a file variable
        fileReadContent = f.read()
        camelCaseFileContent = fileReadContent

        allUndescoreContainingTextWordsInFileAsSet = getAllUndescoreContainingTextWordsInFile(
            camelCaseFileContent)

        for undescoreContainingTextWord in allUndescoreContainingTextWordsInFileAsSet:
            allowed = False
            for allowedRegexCompiledPattern in allowedRegexCompiledPatternsList:
                if allowedRegexCompiledPattern.match(undescoreContainingTextWord):
                    allowed = True

            if not allowed:
                codeWordTransformedIntoCamelCase = getUnderscoreContainingCodeWordTransformedIntoCamelCase(
                    undescoreContainingTextWord)


    if not noOperation:
        # Opening our text file in write only
        # mode to write the replaced content
        with open(fileFullPath, 'w') as file:

            # Writing the replaced data in our
            # text file
            file.write(camelCaseFileContent)
    else:
        logging.info("Output file would be:" + camelCaseFileContent)

def TransformCodeToCamelCase(argv):

    parser = argparse.ArgumentParser()
    parser.add_argument('-p', '--parentFolderContainingCode')
    parser.add_argument('-c', '--codeExtensionConsideredSplitByComa')
    parser.add_argument('-nop')
    args_parsed = parser.parse_args(argv)

    parentFolderContainingCode = args_parsed.parentFolderContainingCode
    if parentFolderContainingCode is None:
        parentFolderContainingCode = "C:\\Temp\\TowerDefense"
        printAndLogWarning(
            "Argument parentFolderContainingCode is not defined. Use default value:" + parentFolderContainingCode)

    codeExtensionConsideredSplitByComa = args_parsed.codeExtensionConsideredSplitByComa
    if codeExtensionConsideredSplitByComa is None:
        codeExtensionConsideredSplitByComa = "java,cpp,c"
        printAndLogWarning(
            "Argument codeExtensionConsideredSplitByComa is not defined. Use default value:" + codeExtensionConsideredSplitByComa)

    noOperation = args_parsed.nop
    if noOperation is None:
        noOperation = False
        printAndLogWarning(
            "Argument noOperation is not defined. Use default value:" + str(noOperation))

    codeExtensionConsideredList = codeExtensionConsideredSplitByComa.split(",")

    for path, subdirs, files in os.walk(parentFolderContainingCode):
        for fileNameWithExtension in files:
            fileNameWithoutExtension = fileNameWithExtension.split(".")[0]
            fileExtension = fileNameWithExtension.split(".")[1]
            if fileExtension in codeExtensionConsideredList:
                fileFullPath = os.path.join(path, fileNameWithExtension)
                printAndLogInfo("Must treat file " + fileFullPath)
                replaceAllTextByCamelCaseInFile(
                    fileFullPath, path, fileNameWithoutExtension, fileExtension, noOperation)

            else:
                logging.info("Ignored file " + fileNameWithExtension + " because its extension :" +
                             fileExtension + " is not one of the selected ones:" + str(codeExtensionConsideredList))


def main(argv):
    log_file_name = 'TransformCodeToCamelCase_' + \
        str(random.randrange(100000)) + ".log"

    # log_file_name = 'TransformCodeToCamelCase' + "." +  str(random.randrange(10000)) + ".log"
    configureLogger(log_file_name)

    printAndLogInfo('Start application. Log file name: ' + log_file_name)
    TransformCodeToCamelCase(argv)
    printAndLogInfo('End application')


if __name__ == '__main__':
    main(sys.argv[1:])
