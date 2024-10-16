﻿# -*-coding:Utf-8 -*
import logging
import os
import sys

import time

import param


# To get line number for logs
from inspect import currentframe, getframeinfo
import inspect

    
def printAndLogCriticalAndKill(toPrintAndLog):
    log_timestamp = time.asctime( time.localtime(time.time()))
    print(log_timestamp + '\t' + toPrintAndLog)
    logging.critical(toPrintAndLog)
    sys.exit()

def printAndLogInfo(toPrintAndLog):
    
    log_timestamp = time.asctime( time.localtime(time.time()))

    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno

    print(log_timestamp + '\t' + "line#" + str(line_number) + '\t' + str() + toPrintAndLog)
    logging.info("line#" + str(line_number) + '\t' +toPrintAndLog)
    
    
def printAndLogWarning(toPrintAndLog):
    log_timestamp = time.asctime( time.localtime(time.time()))
    print(log_timestamp + '\t' + toPrintAndLog)
    logging.warning(toPrintAndLog)
    
def printAndLogError(toPrintAndLog):
    log_timestamp = time.asctime( time.localtime(time.time()))
    print(log_timestamp + '\t' + "!!ERROR!!")
    print(log_timestamp + '\t' + toPrintAndLog)
    logging.error(toPrintAndLog)

    
def configureLogger(log_file_name):
    logger_directory = "logs"
    
    if not os.path.exists(logger_directory):
        os.makedirs(logger_directory)
    
    print(time.asctime( time.localtime(time.time())) + '\t' + "Logger level:" +str(param.logger_level))
    
    logging.basicConfig(level=param.logger_level,
                        format='%(asctime)s %(levelname)-8s %(message)s',
                        datefmt='%a, %d %b %Y %H:%M:%S',
                        filename=logger_directory+ '\\' + log_file_name,
                        filemode='w')
    #logging.debug
    #logging.info
    #logging.warning
    #logging.error
    #logging.critical
        
        
        
class execution_time(object):

    def __init__(self, f):
        self.f = f

    def __call__(self, *args):
        logging.info("Entering " +  self.f.__name__)
        logging.debug("Arguments passed to " + self.f.__name__ + ":" + str(locals()))
        start_time = time.time()
        
        #Call method
        ret = self.f(*args)
    
        elapsed_time = time.time() - start_time    
        printAndLogInfo("Exited " +  self.f.__name__ + ". Elapsed:" + format(elapsed_time, '.2f') + " s")
        return ret

class print_output(object):

    def __init__(self, f):
        self.f = f

    def __call__(self, *args):
        
        #Call method
        ret = self.f(*args)
    
        logging.debug(self.f.__name__ + " returns:" + str(ret) )
        return ret
        
class print_input_and_output(object):

    def __init__(self, f):
        self.f = f

    def __call__(self, *args):
        
        #Call method
        ret = self.f(*args)
    
        logging.debug("Arguments passed to " + self.f.__name__ + " called with:" + str(args) + " returns:" + str(ret) )
        return ret
    

    