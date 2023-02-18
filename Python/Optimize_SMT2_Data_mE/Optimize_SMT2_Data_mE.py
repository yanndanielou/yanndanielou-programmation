# -*-coding:Utf-8 -*

#For logs
import random
import logging
import os
import sys

#Dates
import datetime
import time

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
matlab_structure_operator = "struct"
matlab_structure_begin = matlab_structure_operator + "("

def printAndLogCriticalAndKill(toPrintAndLog):
    log_timestamp = time.asctime( time.localtime(time.time()))
    print(log_timestamp + '\t' + toPrintAndLog)
    logging.critical(toPrintAndLog)
    sys.exit()

def printAndLogInfo(toPrintAndLog):
    log_timestamp = time.asctime( time.localtime(time.time()))
    print(log_timestamp + '\t' + toPrintAndLog)
    logging.info(toPrintAndLog)
    
    
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
            printAndLogInfo(self.f.__name__ + " returns true for :" + function_argument)
        
            #printAndLogInfo(self.f.__name__ + " returns true for :" + str(locals().get("line")))
        return ret        
        


class Parsing_sMT2_Data_mE_struct_file_step:

    step_reading_first_file_lines_to_keep_unchanged = "step_reading_first_file_lines_to_keep_unchanged" 
    step_reading_struct_construction_lines = "step_reading_struct_construction_lines"
    step_filling_struct_cell_by_cell = "step_filling_struct_cell_by_cell"
    step_has_parsed_last_return_of_file_and_waiting_end_of_file = "step_parsing_last_return_of_file"
    #step_waiting_end_of_file = "step_waiting_end_of_file" 


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

class MatlabStruct:

    def __init__(self, name):
        self.name = name
        self.fields = list()
        self.full_definition_lines = list()
        self.structure_full_definition_with_name_in_one_line = None
        self.structure_full_definition_without_name_in_one_line = None
        self.structure_inside_content_definition_in_one_line = None

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


      
class MatlabFieldOfStructure:

    def __init__(self):
        self.name = None
        self.values = list()
        self.original_definition_in_one_line = None


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

            current_matlab_structure = MatlabStruct(current_matlab_structure_name)
            self.structures.append(current_matlab_structure)

            printAndLogInfo("Structure name:" + current_matlab_structure.name)

            for structure_construction_line in structure_constructions_lines:
                current_matlab_structure.add_full_definition_line(structure_construction_line)

        for matlab_structure in self.structures:
            matlab_structure.fill_structure_full_definition_in_one_line()
            logging.info("matlab_structure full definition witout name in one line:" + matlab_structure.structure_full_definition_without_name_in_one_line)
            logging.info("matlab_structure full definition with name in one line:" + matlab_structure.structure_full_definition_with_name_in_one_line)

            logging.info("matlab_structure inside content one line:" + matlab_structure.structure_inside_content_definition_in_one_line)

    def parse_structures__not_working_for_struct_in_struct(self):
        structure_number = 0

        for structure_constructions_lines in self.structures_constructions_lines_as_list_by_structure:
            current_matlab_structure = MatlabStruct()
            self.structures.append(current_matlab_structure)
            structure_number += 1
            

            current_matlab_structure_field = None

            structure_construction_line_number = 0

            for structure_construction_line in structure_constructions_lines:
                structure_construction_line_number += 1
                if structure_construction_line_number == 1:
                    current_matlab_structure.name = get_structure_name_from_struct_creation_line(structure_construction_line)             
                    printAndLogInfo("Structure name:" + current_matlab_structure.name)

                    #current_matlab_structure.structure_full_definition_in_one_line = structure_construction_first_line_split_with_separator[1].strip().replace(matlab_line_continuation_operator,"")



                elif is_matlab_new_structure_field_line(structure_construction_line):
                    if current_matlab_structure_field != None:
                        printAndLogCriticalAndKill(current_matlab_structure.name + " :new structure field not expected in line:" + structure_construction_line + " , current field was " + current_matlab_structure_field.name)
                    
                    current_matlab_structure_field = MatlabFieldOfStructure()
                    current_matlab_structure.fields.append(current_matlab_structure_field)
                    current_matlab_structure_field.name = get_structure_field_name_from_field_creation_line(structure_construction_line)     
                    logging.info("Structure :" + current_matlab_structure.name + " new field:" + current_matlab_structure_field.name + " in line:" + structure_construction_line)
        
                    printAndLogInfo("Structure :" + current_matlab_structure.name + " parsing field:" + current_matlab_structure_field.name)


                    structure_construction_first_line_split_with_separator = structure_construction_line.split(matlab_field_separator)
                    current_matlab_structure_field.original_definition_in_one_line = structure_construction_first_line_split_with_separator[1].strip().replace(matlab_line_continuation_operator,"")


                elif is_matlab_last_structure_field_line(structure_construction_line):
                    if current_matlab_structure_field == None:
                        printAndLogCriticalAndKill(current_matlab_structure.name + " :end of structure field not expected in line:" + str(structure_construction_line_number))

                    logging.info("Structure :" + current_matlab_structure.name + " end of fied:" + current_matlab_structure_field.name + " in line:" + structure_construction_line)
                    printAndLogInfo("Structure :" + current_matlab_structure.name + " field " + current_matlab_structure_field.name )
                    logging.info("Structure :" + current_matlab_structure.name + " field content:" + current_matlab_structure_field.original_definition_in_one_line)
                    current_matlab_structure_field = None

                elif is_matlab_structure_last_creation_line(structure_construction_line):
                    current_matlab_structure = None

                else:
                    current_matlab_structure_field.original_definition_in_one_line += structure_construction_line.strip().replace(matlab_line_continuation_operator,"")
            
            #printAndLogInfo("Field content:" + current_matlab_structure.structure_full_definition_in_one_line)

            
                

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
    

        

        
#chemin_fichier_SMT2_Data_mE_part_001_initialisation_structures = fullfile(SMT_Repertoire_outil,nom_projet,'SMT2_Data_mE_part_001_initialisation_structures.m);
#affectation_variables_globales(chemin_fichier_SMT2_Data_mE_part_001_initialisation_structures);    
def fill_affectation_variables_globales(output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, SMT2_Data_mE_xfunction_name):
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append("chemin_fichier_" + SMT2_Data_mE_xfunction_name + " = fullfile(SMT_Repertoire_outil,nom_projet,'" + SMT2_Data_mE_xfunction_name + ".m');")
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append("affectation_variables_globales(chemin_fichier_" + SMT2_Data_mE_xfunction_name + ");")

#disp(string(datetime) + " SMT2_Data_mE");
#SMT2_Data_mE;
def fill_call_file_with_disp(output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, SMT2_Data_mE_xfunction_name):
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append('disp(string(datetime) + " ' + SMT2_Data_mE_xfunction_name + '");')
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append(SMT2_Data_mE_xfunction_name + ";")

		
def fill_matlab_code_file_containings_code_to_copy_paste(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix, output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part1_initialisation_structures_function_name, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name, prefix, suffix):

    #call m files
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append(prefix + output_SMT2_Data_mE_part1_initialisation_structures_function_name + suffix)
    
    for output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines in output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file:
        fill_SMT_mE_aig_file_number = output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file.index(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines)
        
        file_part_number_with_offset = fill_SMT_mE_aig_file_number + 2
        file_part_number_with_offset_3_digits= f"{file_part_number_with_offset:03}"
        
        output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append(prefix + output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix + file_part_number_with_offset_3_digits + suffix)

    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append(prefix + output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name + suffix)
     
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append("")
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append("")

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
