﻿# -*-coding:Utf-8 -*

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

#Reges
import re

#param
end_line_character_in_text_file = "\n"
input_SMT2_Data_mE_file_name = "SMT2_Data_mE.m"
logger_level = logging.INFO


#Constants
matlab_line_continuation_operator = "..."
matlab_return_operator = "return"
matlab_field_separator = ","
matlab_end_instruction_separator = ";"
matlab_structure_fields_table_begin = "{"
matlab_structure_fields_table_end = "}"
matlab_structure_field_end = "}"
matlab_structure_operator = "struct"
matlab_structure_begin = matlab_structure_operator + "("
matlab_empty_array_field = "[]"

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

class TableFieldInMainStructureModificationInstruction:

    def __init__(self, full_content_as_string, match_result):
        self.full_content_as_string = full_content_as_string

        self.main_structure_name = match_result.group("main_structure_name")
        self.main_structure_index = int(match_result.group("main_structure_index"))
        self.field_name = match_result.group("field_name")
        self.field_index_1 = int(match_result.group("field_index_1"))
        self.field_index_2 = int(match_result.group("field_index_2"))
        self.new_value = match_result.group("new_value")

class ArrayItemOfFieldOfStructureWithModificationInstruction:
    def __init__(self, parent):
        self.fields = list()
        self.last_index_computed = None
        self.assingment_instructions = list()
        self.parent = parent

    def get_table_dimension(self):
        max_dimension = 0

        for assingment_instruction in self.assingment_instructions:
            if assingment_instruction.field_index_1 > max_dimension:
                max_dimension = assingment_instruction.field_index_1
        
        return max_dimension

class FieldOfStructureWithModificationInstruction:
    def __init__(self, name, parent):
        self.fields = list()
        self.name = name
        self.last_index_computed = None
        self.array_items = list()
        self.parent = parent
        printAndLogInfo("Create " + self.__class__.__name__ + " created with name:" + self.name)

    def add_assignment_instructions(self, tableFieldInMainStructureModificationInstruction):
        main_structure_index_starting_at_0 = tableFieldInMainStructureModificationInstruction.main_structure_index - 1
        arrayItemOfFieldOfStructureWithModificationInstruction = None
        
        if main_structure_index_starting_at_0 > len(self.array_items):
            arrayItemOfFieldOfStructureWithModificationInstruction = ArrayItemOfFieldOfStructureWithModificationInstruction(self)
            self.array_items.append(arrayItemOfFieldOfStructureWithModificationInstruction)
        else:
            arrayItemOfFieldOfStructureWithModificationInstruction = self.array_items[main_structure_index_starting_at_0]

        arrayItemOfFieldOfStructureWithModificationInstruction.assingment_instructions.append(tableFieldInMainStructureModificationInstruction)
    

class StructureWithModificationInstruction:
    def __init__(self, name):
        self.fields = list()
        self.name = name
        printAndLogInfo("Create " + self.__class__.__name__ + " created with name:" + self.name)

class SMT2_Data_mE_Content:

    def __init__(self):
        self.tableFieldInMainStructureModificationInstructions = list()
        self.structuresWithModificationInstructions = list()

    def create_structure_fields_objects(self):
        for tableFieldInMainStructureModificationInstruction in self.tableFieldInMainStructureModificationInstructions:
            structureWithModificationInstruction = None
            #check if there is already a known structure
            for structureWithModificationInstructionIt in self.structuresWithModificationInstructions:
                if structureWithModificationInstructionIt.name == tableFieldInMainStructureModificationInstruction.main_structure_name:
                    structureWithModificationInstruction = structureWithModificationInstructionIt

            if structureWithModificationInstruction is None:
                structureWithModificationInstruction = StructureWithModificationInstruction(tableFieldInMainStructureModificationInstruction.main_structure_name)  
                self.structuresWithModificationInstructions.append(structureWithModificationInstruction)

            #check if there is already a known field
            fieldOfStructureWithModificationInstruction = None
            for fieldWithModificationInstructionIt in structureWithModificationInstruction.fields:
                if fieldWithModificationInstructionIt.name == tableFieldInMainStructureModificationInstruction.field_name:
                    fieldOfStructureWithModificationInstruction = fieldWithModificationInstructionIt

            if fieldOfStructureWithModificationInstruction is None:
                fieldOfStructureWithModificationInstruction = FieldOfStructureWithModificationInstruction(tableFieldInMainStructureModificationInstruction.field_name, structureWithModificationInstruction)  
                structureWithModificationInstruction.fields.append(fieldOfStructureWithModificationInstruction)

            fieldOfStructureWithModificationInstruction.add_assignment_instructions(tableFieldInMainStructureModificationInstruction)





def open_text_file_and_return_lines(input_file_name):  
    logging.info('Check existence of input file:' + input_file_name)

    if not os.path.exists(input_file_name):
        logging.critical("Input file:" + input_file_name + " does not exist. Application stopped")
        sys.exit()

    printAndLogInfo('Full path:' + os.path.abspath(input_file_name))


    printAndLogInfo('Opening input file:' + input_file_name)    
    input_file = open(input_file_name, "r")
    
    printAndLogInfo('Read input file:' + input_file_name)
    input_file_read = input_file.read()
    
    printAndLogInfo('Close input file:' + input_SMT2_Data_mE_file_name)
    input_file.close()

    input_file_lines = input_file_read.split(end_line_character_in_text_file)
    printAndLogInfo(input_file_name + " has " + str(len(input_file_lines)) + " lines")

    return input_file_lines
 

def load_SMT2_Data_mE(sMT2_Data_mE_file_name, sMT2_Data_mE_Content):
    sMT2_Data_mE_file_lines = open_text_file_and_return_lines(sMT2_Data_mE_file_name)

    table_field_of_main_structure_modification_instruction_line_regex_as_string = "(?P<main_structure_name>[0-9A-Za-z_]*)[(](?P<main_structure_index>\\d+)[)][.](?P<field_name>[0-9A-Za-z_]*)[(](?P<field_index_1>\\d+)[,](?P<field_index_2>\\d+)[)]\s[=]\s(?P<new_value>-?\\d+)[;]"
    table_field_of_main_structure_modification_instruction_line_regex_compiled = re.compile(table_field_of_main_structure_modification_instruction_line_regex_as_string)
        
    structure_field_of_main_structure_modification_instruction_line_regex_as_string = "(?P<main_structure_name>[0-9A-Za-z_]*)[(](?P<main_structure_index>\\d+)[)][.](?P<second_level_structure_name>[0-9A-Za-z_]*)[(](?P<second_level_structure_index>\\d+)[)][.](?P<field_name>[0-9A-Za-z_]*)[(](?P<field_index>\\d+)[)]\s[=]\s(?P<new_value>-?\\d+)[;]"
    structure_field_of_main_structure_modification_instruction_line_regex_compiled = re.compile(structure_field_of_main_structure_modification_instruction_line_regex_as_string)

    sMT2_Data_mE_line_number = 0

    for sMT2_Data_mE_file_line in sMT2_Data_mE_file_lines:
        sMT2_Data_mE_file_line_stripped = sMT2_Data_mE_file_line.strip()
        sMT2_Data_mE_line_number += 1

        match_table_field_of_main_structure_modification_instruction = table_field_of_main_structure_modification_instruction_line_regex_compiled.match(sMT2_Data_mE_file_line_stripped)
        match_structure_field_of_main_structure_modification_instruction = structure_field_of_main_structure_modification_instruction_line_regex_compiled.match(sMT2_Data_mE_file_line_stripped)

        if match_table_field_of_main_structure_modification_instruction is not None:
            structureModificationInstruction = TableFieldInMainStructureModificationInstruction(sMT2_Data_mE_file_line_stripped, match_table_field_of_main_structure_modification_instruction)
            sMT2_Data_mE_Content.tableFieldInMainStructureModificationInstructions.append(structureModificationInstruction)


    

    
def create_and_fill_output_file(output_directory, input_file_name, file_content_as_list_of_lines):
    printAndLogInfo('Create output file:' + input_file_name)
    output_file = open(output_directory + "\\" + input_file_name, "w")
    logging.info('Fill output file:' + input_file_name)

    logging.info('Converting output content to string')
    output_file_content = end_line_character_in_text_file.join(file_content_as_list_of_lines)

    output_file.write(output_file_content)
    logging.info('Close output file:' + input_file_name)
    output_file.close()


def Generate_SMT2_Data_mE_fields_contents_from_field_post_assignment_lines():
    sMT2_Data_mE_Content = SMT2_Data_mE_Content()

    load_SMT2_Data_mE(input_SMT2_Data_mE_file_name, sMT2_Data_mE_Content)


    sMT2_Data_mE_Content.create_structure_fields_objects()



def main():
    log_file_name = 'Generate_SMT2_Data_mE_fields_contents_from_field_post_assignment_lines' + ".log"
    #log_file_name = 'Generate_SMT2_Data_mE_fields_contents_from_field_post_assignment_lines' + "." +  str(random.randrange(10000)) + ".log"
    configureLogger(log_file_name)    
    printAndLogInfo('Start application. Log file name: ' + log_file_name)
    Generate_SMT2_Data_mE_fields_contents_from_field_post_assignment_lines()
    printAndLogInfo('End application')

if __name__ == '__main__':
    main()
