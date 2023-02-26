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


class print_argument_if_function_returns_true(object):

    def __init__(self, f):
        self.f = f

    def __call__(self, *args):
        #logging.info("Entering " +  self.f.__name__)
        #logging.debug("Arguments passed to " + self.f.__name__ + ":" + str(locals()))
        #start_time = time.time()
        
        #Call method
        ret = self.f(*args)
     
        if ret:
            arguments = args
            function_argument = args[0]
            function_name = self.f.__name__
            logging.info(self.f.__name__ + " returns true for :" + function_argument)
        
            #printAndLogInfo(self.f.__name__ + " returns true for :" + str(locals().get("line")))
        return ret        

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

class Parsing_sMT2_Data_mE_file_step:

    step_reading_first_file_lines_to_keep_unchanged = "step_reading_first_file_lines_to_keep_unchanged" 
    step_reading_struct_construction_lines = "step_reading_struct_construction_lines"
    step_filling_struct_cell_by_cell = "step_filling_struct_cell_by_cell"
    step_has_parsed_last_return_of_file_and_waiting_end_of_file = "step_parsing_last_return_of_file"
    #step_waiting_end_of_file = "step_waiting_end_of_file"    


    def __init__(self):
        self.step = self.step_reading_first_file_lines_to_keep_unchanged

        

    def switch_to_step(self, new_step):
        if self.step != new_step:
           printAndLogInfo("Switch from state " +  self.step + " to state " + new_step)
           self.step = new_step
        else:
            printAndLogInfo("Trying to switch to state " + new_step + " wich is already the current step")

    def switch_to_step_reading_struct_construction_lines(self):
        self.switch_to_step(self.step_reading_struct_construction_lines)
        
    def switch_to_step_filling_struct_cell_by_cell(self):
        self.switch_to_step(self.step_filling_struct_cell_by_cell)
        
    def switch_to_step_has_parsed_last_return_of_file_and_waiting_end_of_file(self):
        self.switch_to_step(self.step_has_parsed_last_return_of_file_and_waiting_end_of_file)
        
    #def switch_to_step_waiting_end_of_file(self):
    #    self.switch_to_step(self.step_waiting_end_of_file)
        


    def is_step_reading_first_file_lines_to_keep_unchanged(self):
        return self.step == self.step_reading_first_file_lines_to_keep_unchanged

    def is_step_reading_struct_construction_lines(self):
        return self.step == self.step_reading_struct_construction_lines
        
    def is_step_filling_struct_cell_by_cell(self):
        return self.step == self.step_filling_struct_cell_by_cell
        
    def is_step_has_parsed_last_return_of_file_and_waiting_end_of_file(self):
        return self.step == self.step_has_parsed_last_return_of_file_and_waiting_end_of_file
        
    #def is_step_waiting_end_of_file(self):
    #    return self.step == self.step_waiting_end_of_file

def decode_matlab_structure(matlabStruct, remaining_line_to_decode):
    current_struct_field = None

    while(len(remaining_line_to_decode) > 0):
        current_parsed_character = remaining_line_to_decode[0]
        remaining_line_to_decode = remaining_line_to_decode[1:]


        if current_parsed_character == "'":
            if current_struct_field == None:
                current_struct_field = MatlabFieldOfStructure()
                current_struct_field.parent = matlabStruct
                matlabStruct.fields.append(current_struct_field)
                printAndLogInfo("Structure: " + matlabStruct.name  + " new field found")
                #parsing_sMT2_Data_mE_struct_file_step.step = parsing_sMT2_Data_mE_struct_file_step.step_reading_field_name
            elif current_struct_field.is_name_complete == False:
                current_struct_field.is_name_complete = True
                printAndLogInfo("Structure: " + matlabStruct.name  + " name decoded for field: " + current_struct_field.name)
                if remaining_line_to_decode.startswith(","):
                    remaining_line_to_decode = remaining_line_to_decode[len(","):]
                    remaining_line_to_decode = current_struct_field.build_yourself_with_remaining_characters_of_main_struct_definition(remaining_line_to_decode)
                    current_struct_field = None
        elif current_struct_field.is_name_complete == False:
            current_struct_field.name += current_parsed_character
  
    return remaining_line_to_decode


class StructureFieldInMainStructureModificationInstruction:
    
    def __init__(self, full_content_as_string, match_result):
        self.full_content_as_string = full_content_as_string

        self.structure_name = match_result.group("main_structure_name")
        self.structure_index = match_result.group("main_structure_index")
        self.second_level_structure_name = match_result.group("second_level_structure_name")
        self.second_level_structure_index = match_result.group("second_level_structure_index")
        self.field_index = match_result.group("field_index")
        self.new_value = match_result.group("new_value")




class TableFieldInMainStructureModificationInstruction:

    def __init__(self, full_content_as_string, match_result):
        self.full_content_as_string = full_content_as_string



        self.structure_name = match_result.group("main_structure_name")
        self.structure_index = match_result.group("main_structure_index")
        self.field_index_1 = match_result.group("field_index_1")
        self.field_index_2 = match_result.group("field_index_2")
        self.new_value = match_result.group("new_value")

def get_structure_name_from_struct_creation_line(struct_creation_line):
    structure_name = struct_creation_line.split("=")[0].strip()
    return structure_name

def get_structure_field_name_from_field_creation_line(struct_field_creation_line):
    structure_field_name = struct_field_creation_line.split(matlab_field_separator)[0].strip()
    return structure_field_name

class FieldOfStructureWithModificationInstruction:
    def __init__(self, name):
        self.fields = list()
        self.name = name
        self.last_index_computed = None
        printAndLogInfo("Create " + self.__class__.__name__ + " created with name:" + self.name)


class StructureWithModificationInstruction:
    def __init__(self, name):
        self.fields = list()
        self.name = name
        printAndLogInfo("Create " + self.__class__.__name__ + " created with name:" + self.name)

class SMT2_Data_mE_Content:

    def __init__(self):
        self.tableFieldInMainStructureModificationInstructions = list()
        self.structuresWithModificationInstructions = list()


    def print_stats(self):
        printAndLogInfo("Nombre de structures à créer:" + str(len(self.structures_constructions_lines_as_list_by_structure)))
        printAndLogInfo("Nombre d'affectation de champs:" + str(len(self.filling_one_structure_specific_field_lines)))

    def create_structure_fields_objects(self):
        for tableFieldInMainStructureModificationInstruction in self.tableFieldInMainStructureModificationInstructions:
            structureWithModificationInstruction = None
            #check if there is already a known structure
            for structureWithModificationInstructionIt in self.structuresWithModificationInstructions:
                if structureWithModificationInstructionIt.name == tableFieldInMainStructureModificationInstruction.name:
                    structureWithModificationInstruction = StructureWithModificationInstruction(tableFieldInMainStructureModificationInstruction.name)

            if structureWithModificationInstructionIt is None:
                structureWithModificationInstruction = StructureWithModificationInstruction()  
            
            #check if there is already a known field
            for fieldWithModificationInstructionIt in structureWithModificationInstruction.fields:
                if fieldWithModificationInstructionIt.name == tableFieldInMainStructureModificationInstruction.name:
                    structureWithModificationInstruction = FieldOfStructureWithModificationInstruction(tableFieldInMainStructureModificationInstruction.fi)

            if structureWithModificationInstructionIt is None:
                structureWithModificationInstruction = StructureWithModificationInstruction()  
            

            structureWithModificationInstruction.fields.
            #self.main_structure_name
            #self.main_structure_index
            #self.field_name
            #self.field_index_1
            #self.field_index_2
            #self.new_value
  





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
 

@print_argument_if_function_returns_true
def is_matlab_new_structure_field_line(line):
    #      'aig_asso',struct( ...
    # or
    #or   'nb_cdv_commut',{[1],[1],[1],[1],[1,1,3],...
    ret = "',"+matlab_structure_fields_table_begin in line or "',"+matlab_structure_begin in line
    
    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)
        
    return ret
    
@print_argument_if_function_returns_true
def is_matlab_last_structure_field_line(line):
    ret = matlab_structure_fields_table_end in line
    
    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)
        
    return ret

@print_argument_if_function_returns_true
def is_matlab_new_structure_creation_line(line):
    ret = matlab_structure_begin in line and "=" in line and matlab_line_continuation_operator in line
    
    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)
        
    return ret


@print_argument_if_function_returns_true
def is_matlab_structure_last_creation_line(line):
    ret = ");" in line.strip()
    
    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)
        
    return ret


#@print_argument_if_function_returns_true
def is_matlab_filling_one_structure_specific_field_line(line):
    ret = "=" in line and ";" in line

    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)
        
    return ret

@print_argument_if_function_returns_true
def is_matlab_return_function_line(line):
    ret = matlab_return_operator == line.strip()
    
    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)
        
    return ret

#@print_argument_if_function_returns_true
def is_matlab_empty_line(line):
    ret = len(line.strip()) == 0

    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)

    return ret
    
#@print_argument_if_function_returns_true
def is_matlab_comment_line(line):
    ret = None
    line_stripped = line.strip()

    line_stripped_is_empty = len(line_stripped) == 0
    if line_stripped_is_empty:
        ret = False
    else:
        ret = line_stripped[0] == '%'

    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)
    
    return ret 

def get_variable_name(obj, namespace):
    variable_name = [name for name in namespace if namespace[name] is obj]
    return variable_name

def load_SMT2_Data_mE(sMT2_Data_mE_file_name, sMT2_Data_mE_Content):
    sMT2_Data_mE_file_lines = open_text_file_and_return_lines(sMT2_Data_mE_file_name)

    table_field_of_main_structure_modification_instruction_line_regex_as_string = "(?P<main_structure_name>[0-9A-Za-z_]*)[(](?P<main_structure_index>\\d+)[)][.](?P<field_name>[0-9A-Za-z_]*)[(](?P<field_index_1>\\d+)[,](?P<field_index_2>\\d+)[)]\s[=]\s(?P<new_value>-?\\d+)[;]"
    table_field_of_main_structure_modification_instruction_line_regex_compiled = re.compile(table_field_of_main_structure_modification_instruction_line_regex_as_string)

        
    structure_field_of_main_structure_modification_instruction_line_regex_as_string = "(?P<main_structure_name>[0-9A-Za-z_]*)[(](?P<main_structure_index>\\d+)[)][.](?P<second_level_structure_name>[0-9A-Za-z_]*)[(](?P<second_level_structure_index>\\d+)[)][.](?P<field_name>[0-9A-Za-z_]*)[(](?P<field_index>\\d+)[)]\s[=]\s(?P<new_value>-?\\d+)[;]"
    structure_field_of_main_structure_modification_instruction_line_regex_compiled = re.compile(structure_field_of_main_structure_modification_instruction_line_regex_as_string)

    sMT2_Data_mE_line_number = 0

    for sMT2_Data_mE_file_line in sMT2_Data_mE_file_lines:
        sMT2_Data_mE_line_number += 1

        match_table_field_of_main_structure_modification_instruction = table_field_of_main_structure_modification_instruction_line_regex_compiled.match(sMT2_Data_mE_file_line)
        match_structure_field_of_main_structure_modification_instruction = structure_field_of_main_structure_modification_instruction_line_regex_compiled.match(sMT2_Data_mE_file_line)

        if match_table_field_of_main_structure_modification_instruction is not None:
            structureModificationInstruction = TableFieldInMainStructureModificationInstruction(sMT2_Data_mE_file_line, match_table_field_of_main_structure_modification_instruction)
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
