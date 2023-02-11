#For logs
import param
#For logs
import LoggerConfig
import logging
import sys
import random

import Constants

import os
import sys

import datetime

part_0_before_function_recup_mE = "part_0_before_function_recup_mE"
part_1_initialisation_structures = "part_1_initialisation_structures"
part_2_to_99_fill_SMT_mE_aig = "part_2_to_99_fill_SMT_mE_aig"
part_100_fill_SMT_mE_feu_BAL = "part_100_fill_SMT_mE_feu_BAL"

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

def create_and_fill_output_file(output_directory, file_name, file_content_as_list_of_lines):
    LoggerConfig.printAndLogInfo('Create output file:' + file_name)
    output_file = open(output_directory + "\\" + file_name, "w")
    logging.info('Fill output file:' + file_name)

    logging.info('Converting output content to string')
    output_file_content = Constants.end_line_character_in_text_file.join(file_content_as_list_of_lines)

    output_file.write(output_file_content)
    logging.info('Close output file:' + file_name)
    output_file.close()

def split_SMT2_Data_mE():

    input_SMT2_Data_mE_file_name = "SMT2_Data_mE.m"
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

    input_SMT2_Data_mE_file_content_as_lines = input_SMT2_Data_mE_file_content.split(Constants.end_line_character_in_text_file)
    LoggerConfig.printAndLogInfo(input_SMT2_Data_mE_file_name + " has " + str(len(input_SMT2_Data_mE_file_content_as_lines)) + " lines")


    for line in input_SMT2_Data_mE_file_content_as_lines:

        previous_step = current_step
        line_number += 1

        
        if (line_number % 1000_000) == 0:
            LoggerConfig.printAndLogInfo("Processing line number:" + str(line_number))

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
            LoggerConfig.printAndLogInfo("Current step is now:" + current_step)

        if current_step == part_0_before_function_recup_mE:
            logging.info("Ignore line " + line)

        if current_step == part_1_initialisation_structures and previous_step == part_1_initialisation_structures:
            output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append(line)

        if current_step == part_2_to_99_fill_SMT_mE_aig:
            new_output_file_part_2_to_99_fill_SMT_mE_aig_to_create = False
            total_number_of_fill_SMT_mE_aig_lines = total_number_of_fill_SMT_mE_aig_lines + 1
            if (total_number_of_fill_SMT_mE_aig_lines % Constants.max_number_of_SMT_mE_aig_lines_per_output_files) == 1:
                LoggerConfig.printAndLogInfo(str(total_number_of_fill_SMT_mE_aig_lines) + " fill SMT_mE_aig lines, create a new file")
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

    LoggerConfig.printAndLogInfo("End of parsing input file")

    output_directory_name = "output_" + datetime.datetime.now().strftime("%Y-%m-%d_%Hh%Mmn%Ss")
    LoggerConfig.printAndLogInfo("Create output directory " + output_directory_name)
    os.mkdir(output_directory_name)

   
    LoggerConfig.printAndLogInfo("Compute output file part 1")
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("")
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append('   disp(string(datetime) + " ' + output_SMT2_Data_mE_part1_initialisation_structures_function_name + ' fin");')
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("return")
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("")
    create_and_fill_output_file(output_directory_name, output_SMT2_Data_mE_part1_initialisation_structures_file_name, output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines)


    LoggerConfig.printAndLogInfo("Compute output files part 2 to 99")
    for output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines in output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file:
        
        fill_SMT_mE_aig_file_number = output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file.index(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines)
        file_part_number = fill_SMT_mE_aig_file_number + 2
        file_part_number_3_digits= f"{file_part_number:03}"
        
        output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines.append("")
        output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines.append('   disp(string(datetime) + " ' + output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix + file_part_number_3_digits + ' fin");')
        output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines.append("return")

        output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines.append("")
        create_and_fill_output_file(output_directory_name, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix + file_part_number_3_digits + ".m", output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines)


    LoggerConfig.printAndLogInfo("Compute output file part 100")
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append("")
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append('   disp(string(datetime) + " " + "SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL fin");')
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append("")
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append("return")
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append("")
    create_and_fill_output_file(output_directory_name, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_file_name, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines)

    LoggerConfig.printAndLogInfo("Compute output matlab file to call functions")
    
    fill_matlab_code_file_containings_code_to_copy_paste(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix,output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part1_initialisation_structures_function_name, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name, "", "")
    fill_matlab_code_file_containings_code_to_copy_paste(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix,output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part1_initialisation_structures_function_name, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name, "clear ", ".m;")
    fill_matlab_code_file_containings_code_to_copy_paste(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix,output_matlab_file_containing_code_to_call_functions_file_content_as_list_of_lines, output_SMT2_Data_mE_part1_initialisation_structures_function_name, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_functions_prefix, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_function_name, 'affectation_variables_globales_to_test_load_data_to_test_load_data("D:\Pour_Yann_Melnotte\SMT3_Package\Fichiers .m extraits\\', '.m");')
     
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
    log_file_name = 'Split_SMT2_Data_mE' + "." +  str(random.randrange(10000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.printAndLogInfo('Start application. Log file name: ' + log_file_name)
    split_SMT2_Data_mE()
    LoggerConfig.printAndLogInfo('End application')

if __name__ == '__main__':
    main()
