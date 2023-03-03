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
        self.new_value = int(match_result.group("new_value"))

class ArrayItemOfFieldOfStructureWithModificationInstruction:
    def __init__(self, parent):
        self.fields = list()
        self.last_index_computed = None
        self.assingment_instructions = list()
        self.max_dimension1 = None
        self.max_dimension2 = None
        self.parent = parent



    def create_tables_for_empty_fields_depending_on_dimension(self):

        #self.fields = list()
        #current_fields = self.fields

        for i in range(0, self.max_dimension1):
            self.fields.append(list())

    def fill_fields_until_size(self, new_size):

        while len(self.fields) < new_size - 1:
            self.fields.append(list())    

    def compute_max_dimensions(self):

        self.max_dimension1 = 0
        self.max_dimension2 = 0

        for assingment_instruction in self.assingment_instructions:
            if assingment_instruction.field_index_1 > self.max_dimension1:
                self.max_dimension1 = assingment_instruction.field_index_1
            if assingment_instruction.field_index_2 > self.max_dimension2:
                self.max_dimension2 = assingment_instruction.field_index_2

    def compute_fields(self):

        for assingment_instruction in self.assingment_instructions:
            field_index_1 = assingment_instruction.field_index_1
            field_index_2 = assingment_instruction.field_index_2
            
            table =  self.fields[field_index_1-1]

            while len(table) < field_index_2 - 1:
                table.append(list())

            if len(table) != field_index_2 - 1:
                printAndLogCriticalAndKill("Error when assigning " + assingment_instruction.full_content_as_string + " in " + str(table))

            table.append(assingment_instruction.new_value)

            
                

class FieldOfStructureWithModificationInstruction:
    def __init__(self, name, parent):
        self.name = name
        self.last_index_computed = None
        self.array_items = list()
        self.parent = parent
        printAndLogInfo("Create " + self.__class__.__name__ + " created with name:" + self.name)

    def add_assignment_instructions(self, tableFieldInMainStructureModificationInstruction):
        main_structure_index_starting_at_0 = tableFieldInMainStructureModificationInstruction.main_structure_index - 1
        arrayItemOfFieldOfStructureWithModificationInstruction = None
        
        if tableFieldInMainStructureModificationInstruction.main_structure_index > len(self.array_items):
            arrayItemOfFieldOfStructureWithModificationInstruction = ArrayItemOfFieldOfStructureWithModificationInstruction(self)
            self.array_items.append(arrayItemOfFieldOfStructureWithModificationInstruction)
        else:
            arrayItemOfFieldOfStructureWithModificationInstruction = self.array_items[main_structure_index_starting_at_0]

        arrayItemOfFieldOfStructureWithModificationInstruction.assingment_instructions.append(tableFieldInMainStructureModificationInstruction)
    

    def save_field(self, file_content_as_list_of_lines):
        content_as_string = ""

        array_item_number = 0
        for array_item in self.array_items:
            if array_item.max_dimension1 > 1:
                content_as_string += "["

            array_item_number += 1
            array_item_as_string = str(array_item.fields)
            
            field_number = 0
            for field in array_item.fields:
                field_number += 1

                content_as_string += str(field)

                if field_number < len (array_item.fields):
                    content_as_string += " "

                    
            if array_item.max_dimension1 > 1:
                content_as_string += "]"
                    
            if array_item_number < len (self.array_items):
                content_as_string += ","

        file_content_as_list_of_lines.append("content of field " + self.name + " for structure:" + self.parent.name)
        file_content_as_list_of_lines.append(content_as_string)
        file_content_as_list_of_lines.append("")

        printAndLogInfo("Print content of field " + self.name + " for structure:" + self.parent.name + " = " + content_as_string[:60] + ", ...,  etc.")
        logging.debug("Print content of field " + self.name + " for structure:" + self.parent.name + " = " + content_as_string)

class StructureWithModificationInstruction:
    def __init__(self, name):
        self.fields = list()
        self.name = name
        printAndLogInfo("Create " + self.__class__.__name__ + " created with name:" + self.name)

class SMT2_Data_mE_Content:

    def __init__(self):
        self.tableFieldInMainStructureModificationInstructions = list()
        self.structureFieldInMainStructureModificationInstructionLines = list()
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

    def fill_structure_fields_objects(self):
        printAndLogInfo("fill_structure_fields_objects")
        
        printAndLogInfo("compute_max_dimensions")                
        for structureWithModificationInstruction in self.structuresWithModificationInstructions:
            for fieldWithModificationInstruction in structureWithModificationInstruction.fields:
                for array_item in fieldWithModificationInstruction.array_items:
                    array_item.compute_max_dimensions()

        printAndLogInfo("create_tables_for_empty_fields_depending_on_dimension")
        for structureWithModificationInstruction in self.structuresWithModificationInstructions:
            for fieldWithModificationInstruction in structureWithModificationInstruction.fields:
                for array_item in fieldWithModificationInstruction.array_items:
                    array_item.create_tables_for_empty_fields_depending_on_dimension()

        printAndLogInfo("compute_fields")                
        for structureWithModificationInstruction in self.structuresWithModificationInstructions:
            for fieldWithModificationInstruction in structureWithModificationInstruction.fields:
                for array_item in fieldWithModificationInstruction.array_items:
                    array_item.compute_fields()
                    
        # for structureWithModificationInstruction in self.structuresWithModificationInstructions:
        #     for fieldWithModificationInstruction in structureWithModificationInstruction.fields:
        #         for array_item in fieldWithModificationInstruction.array_items:
        #             array_item.fill_fields_until_size(7042)

    def save_results(self, file_content_as_list_of_lines):
        printAndLogInfo("save_results")

        for structureWithModificationInstruction in self.structuresWithModificationInstructions:
            for fieldWithModificationInstruction in structureWithModificationInstruction.fields:
                fieldWithModificationInstruction.save_field(file_content_as_list_of_lines)

        file_content_as_list_of_lines.append("")
        file_content_as_list_of_lines.append("structureFieldInMainStructureModificationInstructionLines:")
        file_content_as_list_of_lines += self.structureFieldInMainStructureModificationInstructionLines


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

        elif match_structure_field_of_main_structure_modification_instruction is not None:
            sMT2_Data_mE_Content.structureFieldInMainStructureModificationInstructionLines.append(sMT2_Data_mE_file_line)

    

    
def create_and_fill_output_file(output_directory, input_file_name, file_content_as_list_of_lines):

    if not os.path.exists(output_directory):
        printAndLogInfo('Create output directory:' + output_directory)
        os.makedirs(output_directory)

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
    sMT2_Data_mE_Content.fill_structure_fields_objects()
    
    file_content_as_list_of_lines = list()
    sMT2_Data_mE_Content.save_results(file_content_as_list_of_lines)
    create_and_fill_output_file("output", "Generate_SMT2_Data_mE_fields_contents_from_field_post_assignment_lines.txt", file_content_as_list_of_lines)


def main():
    log_file_name = 'Generate_SMT2_Data_mE_fields_contents_from_field_post_assignment_lines' + ".log"
    #log_file_name = 'Generate_SMT2_Data_mE_fields_contents_from_field_post_assignment_lines' + "." +  str(random.randrange(10000)) + ".log"
    configureLogger(log_file_name)    
    printAndLogInfo('Start application. Log file name: ' + log_file_name)
    Generate_SMT2_Data_mE_fields_contents_from_field_post_assignment_lines()
    printAndLogInfo('End application')

if __name__ == '__main__':
    main()
