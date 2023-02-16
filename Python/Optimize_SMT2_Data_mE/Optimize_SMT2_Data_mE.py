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
logger_level = logging.DEBUG


#Constants
matlab_line_continuation_operator = "..."

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
        logging.info("Entering " +  self.f.__name__)
        logging.debug("Arguments passed to " + self.f.__name__ + ":" + str(locals()))
        start_time = time.time()
        
        #Call method
        ret = self.f(*args)
     
        if ret:
            arguments = args
            function_argument = args[0]
            function_name = self.f.__name__
            printAndLogInfo(self.f.__name__ + " returns true for :" + function_argument)
        
            #printAndLogInfo(self.f.__name__ + " returns true for :" + str(locals().get("line")))
        return ret        
        
class MatlabStruct:

    def __init__(self, name):
        self.name = name
        self.fields = list()
      
class MatlabFieldOfStructure:

    def __init__(self, name):
        self.name = name
        self.values = list()

class SMT2_Data_mE_Content:


    def __init__(self):
        self.first_file_lines_to_keep_unchanged = list()
        self.global_definition_lines = list()

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
def is_matlab_new_structure_creation_line(line):
    ret = "struct(" in line and "=" in line and matlab_line_continuation_operator in line
    
    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)
        
    return ret


@print_argument_if_function_returns_true
def is_matlab_structure_last_creation_line(line):
    ret = ");" in line.strip()
    
    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)
        
    return ret


@print_argument_if_function_returns_true
def is_matlab_filling_one_structure_specific_field_line(line):
    ret = "=" in line and ";" in line

    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)
        
    return ret


@print_argument_if_function_returns_true
def is_matlab_empty_line(line):
    ret = len(line.strip()) == 0

    #if ret:
    #    printAndLogInfo("is_matlab_comment_line returns True for line:" + line)

    return ret
    
@print_argument_if_function_returns_true
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
    
def load_SMT2_Data_mE(sMT2_Data_mE_file_name):
    sMT2_Data_mE_file_lines = open_text_file_and_return_lines(sMT2_Data_mE_file_name)
    sMT2_Data_mE_Content = SMT2_Data_mE_Content()

    
    is_reading_first_file_lines_to_keep_unchanged = True 
    is_reading_struct_construction_lines = False

    structures_constructions_lines = list()
    current_structure_construction_lines = None
    is_reading_struct_construction_lines = False

    is_filling_struct_cell_by_cell = False

    sMT2_Data_mE_line_number = 0

    for sMT2_Data_mE_file_line in sMT2_Data_mE_file_lines:
        sMT2_Data_mE_line_number = sMT2_Data_mE_line_number + 1

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

        if is_reading_first_file_lines_to_keep_unchanged:
            if is_matlab_new_structure_creation_line(sMT2_Data_mE_file_line):
                is_reading_first_file_lines_to_keep_unchanged = False
                is_reading_struct_construction_lines = True
                printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + ": no more reading first lines")
            else:
                sMT2_Data_mE_Content.first_file_lines_to_keep_unchanged.append(sMT2_Data_mE_file_line)

        if is_reading_struct_construction_lines and not is_matlab_filling_one_structure_specific_field_line(sMT2_Data_mE_file_line):
            if current_structure_construction_lines == None and is_matlab_new_structure_creation_line(sMT2_Data_mE_file_line) :
                printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + ": new structure detected")
                current_structure_construction_lines = list()

            if current_structure_construction_lines == None:
                printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + sMT2_Data_mE_file_line + ": will crash")

                current_structure_construction_lines.append(sMT2_Data_mE_file_line)

            if is_matlab_structure_last_creation_line(sMT2_Data_mE_file_line):
                structures_constructions_lines.append(current_structure_construction_lines)
                printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + ": end of current structure")
                current_structure_construction_lines = None

            if is_matlab_filling_one_structure_specific_field_line(sMT2_Data_mE_file_line) :
                
                if is_reading_struct_construction_lines:
                    is_reading_struct_construction_lines = False
                    is_filling_struct_cell_by_cell = True

                    printAndLogInfo("Line:" + str(sMT2_Data_mE_line_number) + ": start filling structure line by line")


    printAndLogInfo("Nombre de structures à créer:" + str(len(structures_constructions_lines)))
        

        
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
    load_SMT2_Data_mE(input_SMT2_Data_mE_file_name)

def unused():
    # open input ilv file
    logging.info('Opening input file:' + input_SMT2_Data_mE_file_name)

    if not os.path.exists(input_SMT2_Data_mE_file_name):
        logging.critical("Input file:" + input_SMT2_Data_mE_file_name + " does not exist. Application stopped")
        sys.exit()

    input_SMT2_Data_mE_file = open(input_SMT2_Data_mE_file_name, "r")

    logging.info('Read input file:' + input_SMT2_Data_mE_file_name)
    input_SMT2_Data_mE_file_content = input_SMT2_Data_mE_file.read()

    
    
    
	# close input file
    logging.info('Close input file:' + input_SMT2_Data_mE_file_name)
    input_SMT2_Data_mE_file.close()

    # initialize output files
    output_SMT2_Data_mE_part1_initialisation_structures_function_name = "SMT2_Data_mE_part_001_initialisation_structures" 
    output_SMT2_Data_mE_part1_initialisation_structures_file_name = output_SMT2_Data_mE_part1_initialisation_structures_function_name + ".m"
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines = list()
    #output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("*Generated")
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("%***************************************************************")
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("function " + output_SMT2_Data_mE_part1_initialisation_structures_function_name)
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("%***************************************************************")
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append('   disp(string(datetime) + " ' + output_SMT2_Data_mE_part1_initialisation_structures_function_name + ' debut");')

    #Double list. one list per file, and for each of those files a list of lines
    output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file = list()
    output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix = "SMT2_Data_mE_part_" 
    #output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_name_prefix = "SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_"

    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name = "SMT2_Data_mE_part_100_fill_SMT_mE_feu_BAL" 
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_file_name = output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name + ".m"
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines = list()
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append("function " + output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name)
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append('   disp(string(datetime) + " ' + output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name + ' debut");')
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append('')
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append('global SMT_mE_feu_BAL')
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append('')

    output_matlab_file_containing_code_to_call_functions_file_name = "output_matlab_file_containing_code_to_call_functions" + ".txt"
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines = list()
  
   
    #before_parsing_function_recup_mE = True
    current_step = part_0_before_function_recup_mE
    total_number_of_fill_SMT_mE_aig_lines = 0

    line_number = 0

    input_SMT2_Data_mE_file_content_as_lines = input_SMT2_Data_mE_file_content.split(end_line_character_in_text_file)
    printAndLogInfo(input_SMT2_Data_mE_file_name + " has " + str(len(input_SMT2_Data_mE_file_content_as_lines)) + " lines")


    for line in input_SMT2_Data_mE_file_content_as_lines:

        previous_step = current_step
        line_number += 1

        
        if (line_number % 1000_000) == 0:
            printAndLogInfo("Processing line number:" + str(line_number))

        if "function recup_mE" in line:
            #before_parsing_function_recup_mE = False
            current_step = part_1_initialisation_structures
      
        elif "SMT_mE_aig(" in line:
            if current_step == part_1_initialisation_structures:
                current_step = part_2_to_99_fill_SMT_mE_aig

        else:
            if current_step == part_2_to_99_fill_SMT_mE_aig:
                current_step = part_100_fill_SMT_mE_feu_BAL
                
            
        if current_step != previous_step:
            printAndLogInfo("Current step is now:" + current_step)

        if current_step == part_0_before_function_recup_mE:
            logging.info("Ignore line " + line)

        if current_step == part_1_initialisation_structures and previous_step == part_1_initialisation_structures:
            output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append(line)

        if current_step == part_2_to_99_fill_SMT_mE_aig:
            new_output_file_part_2_to_99_fill_SMT_mE_aig_to_create = False
            total_number_of_fill_SMT_mE_aig_lines = total_number_of_fill_SMT_mE_aig_lines + 1
            if (total_number_of_fill_SMT_mE_aig_lines % max_number_of_SMT_mE_aig_lines_per_output_files) == 1:
                printAndLogInfo(str(total_number_of_fill_SMT_mE_aig_lines) + " fill SMT_mE_aig lines, create a new file")
                output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file.append(list())
                file_part_number = len(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file)-1
                file_part_number_with_offset = file_part_number + 2
                file_part_number_with_offset_3_digits= f"{file_part_number_with_offset:03}"
                output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file[file_part_number].append("function " + output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix + file_part_number_with_offset_3_digits)
                output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file[file_part_number].append('   disp(string(datetime) + " ' + output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix + file_part_number_with_offset_3_digits + ' debut");')
                output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file[file_part_number].append('')
                output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file[file_part_number].append('global SMT_mE_aig')
                output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file[file_part_number].append('')
                    
            
            output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file[len(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file)-1].append(line)

        if current_step == part_100_fill_SMT_mE_feu_BAL:
            output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append(line)

    printAndLogInfo("End of parsing input file")

    output_directory_name = "output_" + datetime.datetime.now().strftime("%Y-%m-%d_%Hh%Mmn%Ss")
    printAndLogInfo("Create output directory " + output_directory_name)
    os.mkdir(output_directory_name)

   
    printAndLogInfo("Compute output file part 1")
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("")
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append('   disp(string(datetime) + " ' + output_SMT2_Data_mE_part1_initialisation_structures_function_name + ' fin");')
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("return")
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("")
    create_and_fill_output_file(output_directory_name, output_SMT2_Data_mE_part1_initialisation_structures_file_name, output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines)


    printAndLogInfo("Compute output files part 2 to 99")
    for output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines in output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file:
        
        fill_SMT_mE_aig_file_number = output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file.index(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines)
        file_part_number = fill_SMT_mE_aig_file_number + 2
        file_part_number_3_digits= f"{file_part_number:03}"
        
        output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines.append("")
        output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines.append('   disp(string(datetime) + " ' + output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix + file_part_number_3_digits + ' fin");')
        output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines.append("return")

        output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines.append("")
        create_and_fill_output_file(output_directory_name, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix + file_part_number_3_digits + ".m", output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines)


    printAndLogInfo("Compute output file part 100")
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append("")
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append('   disp(string(datetime) + " " + "SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL fin");')
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append("")
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append("return")
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append("")
    create_and_fill_output_file(output_directory_name, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_file_name, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines)

    printAndLogInfo("Compute output matlab file to call functions")
    
    fill_matlab_code_file_containings_code_to_copy_paste(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix,output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part1_initialisation_structures_function_name, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name, "", "")
    fill_matlab_code_file_containings_code_to_copy_paste(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix,output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part1_initialisation_structures_function_name, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name, "clear ", ".m;")
    fill_matlab_code_file_containings_code_to_copy_paste(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix,output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part1_initialisation_structures_function_name, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name, 'affectation_variables_globales_to_test_load_data("D:\Pour_Yann_Melnotte\SMT3_Package\Fichiers .m extraits\\', '.m");')
     
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append("")
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append("")
    fill_affectation_variables_globales(output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part1_initialisation_structures_function_name)
    for output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines in output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file:
        fill_SMT_mE_aig_file_number = output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file.index(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines)
        
        file_part_number_with_offset = fill_SMT_mE_aig_file_number + 2
        file_part_number_with_offset_3_digits= f"{file_part_number_with_offset:03}"
        
        SMT2_Data_mE_part_2_to_99_function_name = output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix + file_part_number_with_offset_3_digits
        fill_affectation_variables_globales(output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, SMT2_Data_mE_part_2_to_99_function_name)
    fill_affectation_variables_globales(output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name)

    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append("")
    output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines.append("")
    fill_call_file_with_disp(output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part1_initialisation_structures_function_name)
    for output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines in output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file:
        fill_SMT_mE_aig_file_number = output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file.index(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines)
        
        file_part_number_with_offset = fill_SMT_mE_aig_file_number + 2
        file_part_number_with_offset_3_digits= f"{file_part_number_with_offset:03}"
        
        SMT2_Data_mE_part_2_to_99_function_name = output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix + file_part_number_with_offset_3_digits
        fill_call_file_with_disp(output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, SMT2_Data_mE_part_2_to_99_function_name)
    fill_call_file_with_disp(output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name)



    create_and_fill_output_file(output_directory_name, output_matlab_file_containing_code_to_call_functions_file_name, output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines)


    

def main():
    log_file_name = 'Optimize_SMT2_Data_mE' + "." +  str(random.randrange(10000)) + ".log"
    configureLogger(log_file_name)    
    printAndLogInfo('Start application. Log file name: ' + log_file_name)
    Optimize_SMT2_Data_mE()
    printAndLogInfo('End application')

if __name__ == '__main__':
    main()
