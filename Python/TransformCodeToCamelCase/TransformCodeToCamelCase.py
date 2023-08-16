# -*-coding:Utf-8 -*

#For logs
import random
import logging
import os
import sys

# To get line number for logs
from inspect import currentframe, getframeinfo
import inspect

#Dates
import datetime
import time

#For args
import getopt

#Regex
import re

#param
end_line_character_in_text_file = "\n"
logger_level = logging.INFO


def printAndLogCriticalAndKill(toPrintAndLog):
    log_timestamp = time.asctime( time.localtime(time.time()))
    
    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno

    print(log_timestamp + '\t' + "line#" + str(line_number) + '\t' +toPrintAndLog)
    logging.critical("line#" + str(line_number) + '\t' +toPrintAndLog)
    sys.exit()

def printAndLogInfo(toPrintAndLog):
    
    log_timestamp = time.asctime( time.localtime(time.time()))

    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno

    print(log_timestamp + '\t' + "line#" + str(line_number) + '\t' + str() + toPrintAndLog)
    logging.info("line#" + str(line_number) + '\t' +toPrintAndLog)
    
    
def printAndLogWarning(toPrintAndLog):
    log_timestamp = time.asctime( time.localtime(time.time()))

    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno

    print(log_timestamp + '\t' + "line#" + str(line_number) + '\t' +toPrintAndLog)
    logging.warning("line#" + str(line_number) + '\t' +toPrintAndLog)
    
def printAndLogError(toPrintAndLog):
    log_timestamp = time.asctime( time.localtime(time.time()))
    
    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno

    print(log_timestamp + '\t' + "!!ERROR!!")
    print(log_timestamp + '\t' "line#" + str(line_number))
    print(log_timestamp + '\t' + toPrintAndLog)
    logging.error("line#" + str(line_number) + '\t' +toPrintAndLog)

    
def configureLogger(log_file_name):
    logger_directory = "logs"
    
    if not os.path.exists(logger_directory):
        os.makedirs(logger_directory)
    
    print(time.asctime( time.localtime(time.time())) + '\t' + "Logger level:" +str(logger_level))
    
    logging.basicConfig(level=logger_level,
                        format='%(asctime)s %(levelname)-8s %(message)s',
                        datefmt='%a, %d %b %Y %H:%M:%S',
                        filename=logger_directory+ '\\' + log_file_name,
                        filemode='w')
    #logging.debug
    #logging.info
    #logging.warning
    #logging.error
    #logging.critical





  
# Creating a function to
# replace the text
def replacetext(search_text,replace_text):
  
    # Opening the file in read and write mode
    with open('SampleFile.txt','r+') as f:
  
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

def TransformCodeToCamelCase(argv):

    list_arguments_names = ["parentFolderContainingCode","SMT2_Data_mE_file_name_with_path","numero_premiere_mission_elementaire_a_traiter=","numero_derniere_mission_elementaire_a_traiter=","port_smt3="]
    
    parentFolderContainingCode = "C:\\Temp\\TowerDefense"
    codeExtensionConsideredSplitByComa = "java,cpp,c"

    try:
        opts, args = getopt.getopt(argv,"hi:o:", list_arguments_names)
    except getopt.GetoptError as err:
        errorMessage = "Unsupported arguments list." + str(err) + " Allowed arguments:" + str(list_arguments_names) + ". Application stopped"
        printAndLogCriticalAndKill(errorMessage)
    for opt, arg in opts:
        if opt in ("-h", "--help"):
            printAndLogInfo("Allowed arguments:" + str(list_arguments_names) + ". Application stopped")
            sys.exit()
        elif opt == "--parentFolderContainingCode":
            parentFolderContainingCode = arg
        elif opt == "--codeExtensionConsideredSplitByComa":
            codeExtensionConsideredSplitByComa = arg
        else:
            printAndLogCriticalAndKill (" Option:" + opt + " unknown with value:" + opt + ". Allowed arguments:" + str(list_arguments_names) + ". Application stopped")


    codeExtensionConsideredList = codeExtensionConsideredSplitByComa.split(",")

    for path, subdirs, files in os.walk(parentFolderContainingCode):
        for fileNameWithExtension in files:
            fileNameWithoutExtension = fileNameWithExtension.split(".")[0]
            fileExtension = fileNameWithExtension.split(".")[1]
            if fileExtension in codeExtensionConsideredList:
                #print(os.path.join(path, fileNameWithExtension))
                printAndLogInfo("Must treat file " +fileNameWithExtension)

            else:
                logging.info("Ignored file " +fileNameWithExtension + " because its extension :" + fileExtension + " is not one of the selected ones:" +str(codeExtensionConsideredList) )



def main(argv):
    log_file_name = 'TransformCodeToCamelCase' + ".log"
    #log_file_name = 'TransformCodeToCamelCase' + "." +  str(random.randrange(10000)) + ".log"
    configureLogger(log_file_name)    
    printAndLogInfo('Start application. Log file name: ' + log_file_name)
    TransformCodeToCamelCase(argv)
    printAndLogInfo('End application')

if __name__ == '__main__':
    main(sys.argv[1:])

