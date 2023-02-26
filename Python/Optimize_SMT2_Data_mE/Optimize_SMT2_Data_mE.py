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
logger_level = logging.DEBUG


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



class MatlabMainLevel0Struct:

    def __init__(self, name):
        self.name = name
        self.fields = list()
        self.full_definition_lines = list()
        self.structure_full_definition_with_name_in_one_line = None
        self.structure_full_definition_without_name_in_one_line = None
        self.structure_inside_content_definition_in_one_line = None
        self.parent = None

    def add_full_definition_line(self, line):
        self.full_definition_lines.append(line)
    
    def fill_structure_full_definition_in_one_line(self):
        self.structure_full_definition_with_name_in_one_line = ""
        self.structure_full_definition_without_name_in_one_line = ""
        self.structure_inside_content_definition_in_one_line = ""

        full_definition_lines_line_number = 0

        for structure_full_definition_line in self.full_definition_lines:
            full_definition_lines_line_number += 1

            self.structure_full_definition_with_name_in_one_line += structure_full_definition_line.strip().replace(matlab_line_continuation_operator,"").strip()
      
            if full_definition_lines_line_number > 1 and full_definition_lines_line_number < len(self.full_definition_lines):
                self.structure_inside_content_definition_in_one_line += structure_full_definition_line.strip().replace(matlab_line_continuation_operator,"").strip()

            if full_definition_lines_line_number == 1:
                self.structure_full_definition_without_name_in_one_line += matlab_structure_begin
            elif full_definition_lines_line_number < len(self.full_definition_lines):
                self.structure_full_definition_without_name_in_one_line += structure_full_definition_line.strip().replace(matlab_line_continuation_operator,"").strip()
            else:
                self.structure_full_definition_without_name_in_one_line += ");"


    def decode_fields(self):

        #parsing_sMT2_Data_mE_struct_file_step = Parsing_sMT2_Data_mE_struct_file_step()
        remaining_characters_of_main_struct_definition_to_parse = self.structure_inside_content_definition_in_one_line
 
        decode_matlab_structure(self, remaining_characters_of_main_struct_definition_to_parse)          
                    
      
class MatlabFieldOfStructure:

    def __init__(self):
        self.name = ""
        self.is_name_complete = False
        self.elements = list()
        self.original_definition_in_one_line = None
        self.parent = None

    def build_yourself_with_remaining_characters_of_main_struct_definition(self, remaining_characters_of_main_struct_definition_to_parse):

        while(len(remaining_characters_of_main_struct_definition_to_parse) > 0):

            if remaining_characters_of_main_struct_definition_to_parse[0] == "{":
                #
                matlabArrayOfFieldOfStructure = MatlabArrayOfFieldOfStructure()
                matlabArrayOfFieldOfStructure.parent = self
                self.elements.append(matlabArrayOfFieldOfStructure)
                remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]
                remaining_characters_of_main_struct_definition_to_parse = matlabArrayOfFieldOfStructure.build_yourself_with_remaining_characters_of_main_struct_definition(remaining_characters_of_main_struct_definition_to_parse)
                logging.info("After building for structure:" + self.parent.name + " the field " + self.name + ", there are:" + str(len(remaining_characters_of_main_struct_definition_to_parse)) + " characters left to parse")

                if len(remaining_characters_of_main_struct_definition_to_parse) > 0 and remaining_characters_of_main_struct_definition_to_parse[0] == "'":
                    return remaining_characters_of_main_struct_definition_to_parse

            elif remaining_characters_of_main_struct_definition_to_parse.startswith("struct"):
                matlabstructureOfFieldOfStructure = MatlabStructureOfFieldOfStructure(self)
                self.elements.append(matlabstructureOfFieldOfStructure)
                remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[len("struct"):]
                remaining_characters_of_main_struct_definition_to_parse = matlabstructureOfFieldOfStructure.build_yourself_with_remaining_characters_of_main_struct_definition(remaining_characters_of_main_struct_definition_to_parse)

                logging.info("After building for structure:" + self.parent.name + " the field " + self.name + ", there are:" + str(len(remaining_characters_of_main_struct_definition_to_parse)) + " characters left to parse")


            elif remaining_characters_of_main_struct_definition_to_parse.startswith(matlab_structure_field_end):
                printAndLogCriticalAndKill("Structure:" + self.parent.name + " field " + self.name + " unsupported step " + remaining_characters_of_main_struct_definition_to_parse)

                #matlabstructureOfFieldOfStructure = MatlabStructureOfFieldOfStructure()
                #matlabstructureOfFieldOfStructure.parent = self
            else:
                current_parsed_character = remaining_characters_of_main_struct_definition_to_parse[0]
                remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]
                logging.info("Structure:" + self.parent.name + " field " + self.name + " ignored character " + current_parsed_character)



        return remaining_characters_of_main_struct_definition_to_parse

class MatlabStructureOfFieldOfStructure:

    def __init__(self, parent):
        self.parent = parent
        self.name = "Sub structure of field " + parent.name
        self.is_empty = None
        self.full_content_as_string = None
        self.fields = list()
        
    def decode_fields_of_structure(self):
        remaining_line_to_decode = decode_matlab_structure(self, self.full_content_as_string)
        if len(remaining_line_to_decode) > 0:
            printAndLogCriticalAndKill("After decoding sub structure, " + str(len(remaining_line_to_decode)) + " not parsed characters:" + remaining_line_to_decode)



    def build_yourself_with_remaining_characters_of_main_struct_definition(self, remaining_characters_of_main_struct_definition_to_parse):

        #Remove last "("
        current_parsed_character = remaining_characters_of_main_struct_definition_to_parse[0]       
        if current_parsed_character != "(":
            printAndLogCriticalAndKill("Unexpected character " +  current_parsed_character + " while expected " + matlab_structure_fields_table_end + ". Remaining to parse: " + remaining_characters_of_main_struct_definition_to_parse)
        remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]

        self.full_content_as_string = remaining_characters_of_main_struct_definition_to_parse.split(")")[0]
        remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[len(self.full_content_as_string):]

        #Decode structure content
        self.decode_fields_of_structure()

        #Remove last ")"
        current_parsed_character = remaining_characters_of_main_struct_definition_to_parse[0]       
        if current_parsed_character != ")":
            printAndLogCriticalAndKill("Unexpected character " +  current_parsed_character + " while expected " + matlab_structure_fields_table_end + ". Remaining to parse: " + remaining_characters_of_main_struct_definition_to_parse)
        remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]
  
        
        printAndLogInfo("Structure:" + self.parent.parent.name + " field:"  + self.parent.name +  " structure has " + str(len(self.fields)) + " elements")
        logging.debug("Structure:" + self.parent.parent.name + " field:"  + self.parent.name +  " structure full text content:" + self.full_content_as_string)


        return remaining_characters_of_main_struct_definition_to_parse






class MatlabArrayOfFieldOfStructure:

    def __init__(self):
        self.parent = None
        self.is_empty = None
        self.text_content = None
        self.elements = list()
        self.full_content_as_string = ""

    def build_yourself_with_remaining_characters_of_main_struct_definition(self, remaining_characters_of_main_struct_definition_to_parse):
        self.full_content_as_string = remaining_characters_of_main_struct_definition_to_parse.split("}")[0]
        remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[len(self.full_content_as_string):]

        remaining_array_content_as_string_to_parse = self.full_content_as_string
        while len(remaining_array_content_as_string_to_parse) > 0:
            field = MatlabFieldOfArrayOfFieldOfStructure(self)
            self.elements.append(field)
            remaining_array_content_as_string_to_parse = field.build_yourself_with_remaining_characters_of_main_struct_definition(remaining_array_content_as_string_to_parse)
            if len(remaining_array_content_as_string_to_parse) > 0:
                if remaining_array_content_as_string_to_parse[0] == matlab_field_separator:
                    remaining_array_content_as_string_to_parse = remaining_array_content_as_string_to_parse[1:]
                else:
                    printAndLogCriticalAndKill("Was not expected " + remaining_array_content_as_string_to_parse[0] + " remaining characters to parse:" + remaining_characters_of_main_struct_definition_to_parse[0:100])


        #Remove end of array character    
        current_parsed_character = remaining_characters_of_main_struct_definition_to_parse[0]
        if current_parsed_character != matlab_structure_fields_table_end:
            printAndLogCriticalAndKill("Unexpected character " +  current_parsed_character + " while expected " + matlab_structure_fields_table_end + ". Remaining to parse: " + remaining_characters_of_main_struct_definition_to_parse)
        
        remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]

        #Remove end of array character
        if len(remaining_characters_of_main_struct_definition_to_parse) > 0:   
            current_parsed_character = remaining_characters_of_main_struct_definition_to_parse[0]
            if current_parsed_character != matlab_field_separator:
                printAndLogCriticalAndKill("Unexpected character " +  current_parsed_character + " while expected " + matlab_structure_fields_table_end + ". Remaining to parse: " + remaining_characters_of_main_struct_definition_to_parse)

            remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]


        printAndLogInfo("Structure:" + self.parent.parent.name + " field:"  + self.parent.name +  " array has " + str(len(self.elements)) + " elements")
        #printAndLogInfo("Structure:" + self.parent.parent.name + " field:"  + self.parent.name +  " number of empty fields " + str(sum(elements.is_empty for elements in self.elements)) + " elements")
        #printAndLogInfo("Structure:" + self.parent.parent.name + " field:"  + self.parent.name +  " number of not empty fields " + str(sum(not elements.is_empty for elements in self.elements)) + " elements")
        logging.debug("Structure:" + self.parent.parent.name + " field:"  + self.parent.name +  " full text content:" + self.full_content_as_string)
        return remaining_characters_of_main_struct_definition_to_parse


class MatlabFieldOfArrayOfFieldOfStructureType:
    type_undefined = "type_undefined" 
    type_table = "type_table" 
    type_float = "type_float"
    type_string = "type_string"

    def __init__(self, parent):
        self.type = MatlabFieldOfArrayOfFieldOfStructureType.type_undefined
    

class MatlabFieldOfArrayOfFieldOfStructure:

    def __init__(self, parent):
        self.parent = parent
        self.full_content_as_string = None
        self.is_empty = None
        self.type =  MatlabFieldOfArrayOfFieldOfStructureType(self)
        self.value_as_table = None
        self.value_as_float = None
        
    def build_yourself_with_remaining_characters_of_main_struct_definition(self, remaining_characters_of_main_struct_definition_to_parse):
        
        original_remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse
        self.full_content_as_string = ""

        first_character = remaining_characters_of_main_struct_definition_to_parse[0]

        if first_character == "[":
            self.type.type = MatlabFieldOfArrayOfFieldOfStructureType.type_table
            remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]
        elif first_character == "'":
            self.type.type = MatlabFieldOfArrayOfFieldOfStructureType.type_string
            remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]
        elif re.compile("[A-Za-z0-9]+").fullmatch(first_character) or first_character == "-":
            self.type.type = MatlabFieldOfArrayOfFieldOfStructureType.type_float
        else:
            printAndLogCriticalAndKill("Cannot find type for element starting with " + original_remaining_characters_of_main_struct_definition_to_parse)

        logging.debug("Build " + self.__class__.__name__ + " with type :" + self.type.type + " from string " +  original_remaining_characters_of_main_struct_definition_to_parse[0:200])

        while len(remaining_characters_of_main_struct_definition_to_parse) > 0:
            first_character = remaining_characters_of_main_struct_definition_to_parse[0]

            if self.type.type == MatlabFieldOfArrayOfFieldOfStructureType.type_table:
                remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]

                if first_character == "]":

                    self.value_as_table = list()

                    if len(self.full_content_as_string) > 0:
                        for item_of_table_as_string in self.full_content_as_string.split(matlab_field_separator):
                            self.value_as_table.append(item_of_table_as_string)

                    logging.debug("Has built " + self.type.type + " with content as string:"  + self.full_content_as_string + " and content as table:" + str(self.value_as_table))

                    return remaining_characters_of_main_struct_definition_to_parse
                else:
                    self.full_content_as_string += first_character
      
            elif self.type.type == MatlabFieldOfArrayOfFieldOfStructureType.type_string:

                remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]

                if first_character == "'":
                    logging.debug("Has built " + self.type.type + " with content as string:" + self.full_content_as_string)
                    return remaining_characters_of_main_struct_definition_to_parse
                else:
                    self.full_content_as_string += first_character
            
            elif self.type.type == MatlabFieldOfArrayOfFieldOfStructureType.type_float:
                if first_character == matlab_field_separator or first_character == matlab_structure_fields_table_end:
                    self.value_as_float = float(self.full_content_as_string)
                    logging.debug("Has built " + self.type.type + " with content as string:" + str(self.value_as_float))
                    return remaining_characters_of_main_struct_definition_to_parse
                else:
                    self.full_content_as_string += first_character
                    remaining_characters_of_main_struct_definition_to_parse = remaining_characters_of_main_struct_definition_to_parse[1:]
            

        return remaining_characters_of_main_struct_definition_to_parse




def get_structure_name_from_struct_creation_line(struct_creation_line):
    structure_name = struct_creation_line.split("=")[0].strip()
    return structure_name

def get_structure_field_name_from_field_creation_line(struct_field_creation_line):
    structure_field_name = struct_field_creation_line.split(matlab_field_separator)[0].strip()
    return structure_field_name

class SMT2_Data_mE_Content:

    def __init__(self):
        self.first_file_lines_to_keep_unchanged = list()
        self.global_definition_lines = list()
        #self.all_structures_constructions_lines = list()
        self.structures_constructions_lines_as_list_by_structure = list()
        self.filling_one_structure_specific_field_lines = list()
        self.structures = list()

    def print_stats(self):
        printAndLogInfo("Nombre de structures à créer:" + str(len(self.structures_constructions_lines_as_list_by_structure)))
        printAndLogInfo("Nombre d'affectation de champs:" + str(len(self.filling_one_structure_specific_field_lines)))

    def print_structures(self):
        for structure_constructions_lines in self.structures_constructions_lines_as_list_by_structure:
            logging.debug("print new structure")
            for structure_constructions_line in structure_constructions_lines:
                logging.debug(structure_constructions_line)

        
        #logging.debug("print all structures lines")
        #for all_structures_constructions_line in self.all_structures_constructions_lines:
        #    logging.debug(all_structures_constructions_line)

    def create_structure_objects(self):
        for structure_constructions_lines in self.structures_constructions_lines_as_list_by_structure:
            current_matlab_structure_name = get_structure_name_from_struct_creation_line(structure_constructions_lines[0])             

            current_matlab_structure = MatlabMainLevel0Struct(current_matlab_structure_name)
            self.structures.append(current_matlab_structure)
            current_matlab_structure.parent = self

            printAndLogInfo("Structure name:" + current_matlab_structure.name)

            for structure_construction_line in structure_constructions_lines:
                current_matlab_structure.add_full_definition_line(structure_construction_line)

        for matlab_structure in self.structures:
            matlab_structure.fill_structure_full_definition_in_one_line()
            logging.info("matlab_structure " + matlab_structure.name + " full definition witout name in one line:" + matlab_structure.structure_full_definition_without_name_in_one_line)
            logging.info("matlab_structure " + matlab_structure.name + " full definition with name in one line:" + matlab_structure.structure_full_definition_with_name_in_one_line)

            logging.info("matlab_structure " + matlab_structure.name + " inside content one line:" + matlab_structure.structure_inside_content_definition_in_one_line)


    def decode_main_structure_objects(self):

        for matlab_structure in self.structures:
            matlab_structure.decode_fields()
  

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

    parsing_sMT2_Data_mE_file_current_step = Parsing_sMT2_Data_mE_file_step()

    current_structure_construction_lines = None


    sMT2_Data_mE_line_number = 0

    for sMT2_Data_mE_file_line in sMT2_Data_mE_file_lines:
        sMT2_Data_mE_line_number += 1

        #printAndLogInfo("Current line:" + str(sMT2_Data_mE_line_number))
        #printAndLogInfo("line:" + str(sMT2_Data_mE_line_number) + " is_matlab_comment_line:" + str(is_matlab_comment_line(sMT2_Data_mE_file_line)))
        #printAndLogInfo("line:" + str(sMT2_Data_mE_line_number) + " is_matlab_filling_one_structure_specific_field_line:" + str(is_matlab_filling_one_structure_specific_field_line(sMT2_Data_mE_file_line)))
        #printAndLogInfo("line:" + str(sMT2_Data_mE_line_number) + " is_matlab_empty_line:" + str(is_matlab_empty_line(sMT2_Data_mE_file_line)))
        #printAndLogInfo("line:" + str(sMT2_Data_mE_line_number) + " is_matlab_new_structure_creation_line:" + str(is_matlab_new_structure_creation_line(sMT2_Data_mE_file_line)))
        #printAndLogInfo("line:" + str(sMT2_Data_mE_line_number) + " is_matlab_structure_last_creation_line:" + str(is_matlab_structure_last_creation_line(sMT2_Data_mE_file_line)))

        if is_matlab_comment_line(sMT2_Data_mE_file_line):
            logging.debug("Line:" + str(sMT2_Data_mE_line_number) + "(" + sMT2_Data_mE_file_line + ")" + " ignored because is matlab comment")
            continue
        
        if is_matlab_empty_line(sMT2_Data_mE_file_line):
            logging.debug("Line:" + str(sMT2_Data_mE_line_number) + "(" + sMT2_Data_mE_file_line + ")" + " ignored because is empty")
            continue

        if parsing_sMT2_Data_mE_file_current_step.is_step_reading_first_file_lines_to_keep_unchanged():
            if is_matlab_new_structure_creation_line(sMT2_Data_mE_file_line):
                parsing_sMT2_Data_mE_file_current_step.switch_to_step_reading_struct_construction_lines()

                printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + ": no more reading first lines")
            else:
                sMT2_Data_mE_Content.first_file_lines_to_keep_unchanged.append(sMT2_Data_mE_file_line)

        if parsing_sMT2_Data_mE_file_current_step.is_step_reading_struct_construction_lines() :
 
            if is_matlab_filling_one_structure_specific_field_line(sMT2_Data_mE_file_line):
                parsing_sMT2_Data_mE_file_current_step.switch_to_step_filling_struct_cell_by_cell()

            else:
                #sMT2_Data_mE_Content.all_structures_constructions_lines.append(sMT2_Data_mE_file_line)
                if current_structure_construction_lines == None and is_matlab_new_structure_creation_line(sMT2_Data_mE_file_line) :
                    printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + ": new structure detected")
                    current_structure_construction_lines = list()

                if current_structure_construction_lines == None:
                    printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + sMT2_Data_mE_file_line + ": will crash")

                else:
                    current_structure_construction_lines.append(sMT2_Data_mE_file_line)

                if is_matlab_structure_last_creation_line(sMT2_Data_mE_file_line):
                    sMT2_Data_mE_Content.structures_constructions_lines_as_list_by_structure.append(current_structure_construction_lines)
                    printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + ": end of current structure")
                    current_structure_construction_lines = None

                if is_matlab_filling_one_structure_specific_field_line(sMT2_Data_mE_file_line) :
                    
                    if parsing_sMT2_Data_mE_file_current_step.is_step_reading_struct_construction_lines():
                        parsing_sMT2_Data_mE_file_current_step.switch_to_step_filling_struct_cell_by_cell()
                        

                        printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + ": start filling structure line by line")

        if parsing_sMT2_Data_mE_file_current_step.is_step_filling_struct_cell_by_cell():
            if is_matlab_filling_one_structure_specific_field_line(sMT2_Data_mE_file_line):
                sMT2_Data_mE_Content.filling_one_structure_specific_field_lines.append(sMT2_Data_mE_file_line)
            elif is_matlab_return_function_line(sMT2_Data_mE_file_line):
                parsing_sMT2_Data_mE_file_current_step.switch_to_step_has_parsed_last_return_of_file_and_waiting_end_of_file()
                printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + " is last return of file")
        
        if parsing_sMT2_Data_mE_file_current_step.is_step_has_parsed_last_return_of_file_and_waiting_end_of_file():
                printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + " is not considered because waiting end of file")

        
    sMT2_Data_mE_Content.print_stats()
    sMT2_Data_mE_Content.print_structures()
    

    
def create_and_fill_output_file(output_directory, input_file_name, file_content_as_list_of_lines):
    printAndLogInfo('Create output file:' + input_file_name)
    output_file = open(output_directory + "\\" + input_file_name, "w")
    logging.info('Fill output file:' + input_file_name)

    logging.info('Converting output content to string')
    output_file_content = end_line_character_in_text_file.join(file_content_as_list_of_lines)

    output_file.write(output_file_content)
    logging.info('Close output file:' + input_file_name)
    output_file.close()


def Optimize_SMT2_Data_mE():
    sMT2_Data_mE_Content = SMT2_Data_mE_Content()

    load_SMT2_Data_mE(input_SMT2_Data_mE_file_name, sMT2_Data_mE_Content)
    sMT2_Data_mE_Content.create_structure_objects()
    sMT2_Data_mE_Content.decode_main_structure_objects()

    sMT2_Data_mE_Content.create_structure_modification_objects()
    #sMT2_Data_mE_Content.parse_structures__not_working_for_struct_in_struct()



def main():
    log_file_name = 'Optimize_SMT2_Data_mE' + ".log"
    #log_file_name = 'Optimize_SMT2_Data_mE' + "." +  str(random.randrange(10000)) + ".log"
    configureLogger(log_file_name)    
    printAndLogInfo('Start application. Log file name: ' + log_file_name)
    Optimize_SMT2_Data_mE()
    printAndLogInfo('End application')

if __name__ == '__main__':
    main()
