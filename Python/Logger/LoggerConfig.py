# -*-coding:Utf-8 -*
import logging
import os
import sys

import time


# To get line number for logs
#from inspect import currentframe, getframeinfo
import inspect

logger_level = logging.INFO
    
def __get_calling_file_name_and_line_number():
    previous_stack = inspect.stack(1)[2]
    file_name = previous_stack.filename
    line_number = previous_stack.lineno
    return file_name  + ", line " + str(line_number)
    
def __get_calling_file_name():
    previous_stack = inspect.stack(1)[1]
    file_name = previous_stack.filename
    return file_name
    
def __get_calling_line_number():
    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno
    return line_number

def print_and_log_critical_and_kill(toPrintAndLog):
    log_timestamp = time.asctime( time.localtime(time.time()))
    
    previous_stack = inspect.stack(1)[1]
    line_number = previous_stack.lineno
    file_name = previous_stack.filename
    
    print(log_timestamp + '\t' + toPrintAndLog)
    logging.critical(toPrintAndLog)
    sys.exit()

def print_and_log_info(to_print_and_log):
    """ Print in standard output and log in file as info level"""
    log_timestamp = time.asctime( time.localtime(time.time()))

    print(log_timestamp + '\t' + __get_calling_file_name_and_line_number() + '\t' + str() + to_print_and_log)
    logging.info(__get_calling_file_name_and_line_number() + '\t' +to_print_and_log)
    
    
def print_and_log_warning(to_print_and_log):
    """ Print in standard output and log in file as info level"""
    log_timestamp = time.asctime( time.localtime(time.time()))

    print(log_timestamp + '\t' + __get_calling_file_name_and_line_number() + '\t' + str() + to_print_and_log)
    logging.warning(__get_calling_file_name_and_line_number() + '\t' +to_print_and_log)

def print_and_log_error(to_print_and_log):
    """ Print in standard output and log in file as error level"""
    log_timestamp = time.asctime( time.localtime(time.time()))
    print(log_timestamp + '\t' + "!!ERROR!!")
    print(log_timestamp + '\t' + __get_calling_file_name_and_line_number() + '\t' + str() + to_print_and_log)
    logging.error(__get_calling_file_name_and_line_number() + '\t' +to_print_and_log)

def configure_logger(log_file_name):
    """ Configure the logger """
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
        logging.info("Exited " +  self.f.__name__ + ". Elapsed:" + format(elapsed_time, '.2f') + " s")
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
    """ print input and output of function"""

    def __init__(self, f):
        self.f = f

    def __call__(self, *args):
        
        #Call method
        ret = self.f(*args)
    
        logging.debug("Arguments passed to " + self.f.__name__ + " called with:" + str(args) + " returns:" + str(ret) )
        return ret
