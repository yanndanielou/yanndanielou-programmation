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


def create_and_fill_output_file(output_directory, file_name, file_content_as_list_of_lines):
    logging.info('Create output file:' + file_name)
    output_file = open(file_name, "w")
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
    output_SMT2_Data_mE_part1_initialisation_structures_file_name = "SMT2_Data_mE_part1_initialisation_structures.m"
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines = list()
    output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append("function SMT2_Data_mE")

    #DOuble list. one list per file, and for each of those files a list of lines
    output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file = list()
    output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_name_prefix = "SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_"

    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_file_name = "SMT2_Data_mE_part1_initialisation_structures.m"
    output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines = list()
   
    before_parsing_function_recup_mE = True
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
            before_parsing_function_recup_mE = False
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

        if current_step == part_1_initialisation_structures:
            output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines.append(line)

        if current_step == part_2_to_99_fill_SMT_mE_aig:
            new_output_file_part_2_to_99_fill_SMT_mE_aig_to_create = False
            total_number_of_fill_SMT_mE_aig_lines = total_number_of_fill_SMT_mE_aig_lines + 1
            if (total_number_of_fill_SMT_mE_aig_lines % Constants.max_number_of_SMT_mE_aig_lines_per_output_files) == 1:
                LoggerConfig.printAndLogInfo(str(total_number_of_fill_SMT_mE_aig_lines) + " fill SMT_mE_aig lines, create a new file")
                output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file.append(list())
            
            output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file[len(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file)-1].append(line)

        if current_step == part_100_fill_SMT_mE_feu_BAL:
            output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines.append(line)

    output_directory = "output_" + datetime.datetime.now().strftime("%Y-%m-%d_%Hh%Mmn%Ss")

    LoggerConfig.printAndLogInfo("End of parsing input file")

    LoggerConfig.printAndLogInfo("Create output file part 1")
    create_and_fill_output_file(output_directory, output_SMT2_Data_mE_part1_initialisation_structures_file_name, output_SMT2_Data_mE_part1_initialisation_structures_content_as_list_of_lines)


    LoggerConfig.printAndLogInfo("Create output files part 2 to 99")
    for output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines in output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file:
        fill_SMT_mE_aig_file_number = output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_files_contents_as_list_of_lines_per_file.index(output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines)
        create_and_fill_output_file(output_directory, output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_name_prefix + str(fill_SMT_mE_aig_file_number) + ".m", output_SMT2_Data_mE_part_2_to_99_fill_SMT_mE_aig_file_contents_as_list_of_lines)


    LoggerConfig.printAndLogInfo("Create output file part 100")
    create_and_fill_output_file(output_directory, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_file_name, output_SMT2_Data_mE_part100_fill_SMT_mE_feu_BAL_content_as_list_of_lines)


def main():
    log_file_name = 'Split_SMT2_Data_mE' + "." +  str(random.randrange(10000)) + ".log"
    LoggerConfig.configureLogger(log_file_name)    
    LoggerConfig.printAndLogInfo('Start application. Log file name: ' + log_file_name)
    split_SMT2_Data_mE()
    LoggerConfig.printAndLogInfo('End application')

if __name__ == '__main__':
    main()
