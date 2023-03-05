function execution_fichier_m_ligne_par_ligne_avec_multilines(nom_fichier)

disp(string(datetime) + " execution_fichier_m_ligne_par_ligne_avec_multilines of file " + nom_fichier + " begin");
execution_fichier_m_ligne_par_ligne_begin_time = datetime;


fid = fopen(nom_fichier);
list_var_global{:,1} = [];
ligne_list_var_global = 1;
tline = fgetl(fid);

current_multiline_instruction_str = "";

line_number=0;
while ischar(tline)
    line_number = line_number +1;
    
    %just to track progress
    if mod(line_number, 10000) == 0
        disp(string(datetime) + " execution_fichier_m_ligne_par_ligne_avec_multilines, processing line:" + line_number);
    end
    
	line_strimmed = strtrim(tline);
	is_instruction_ending_in_next_line = endsWith(line_strimmed,"...");
	
	
	if is_instruction_ending_in_next_line
        % remove the "..."
        line_strimmed_without_continuation_char_as_str = line_strimmed(1:end-3);
        current_multiline_instruction_str  = current_multiline_instruction_str + line_strimmed_without_continuation_char_as_str;
        
        %line_strimmed_without_continuation_char_as_table = convertStringsToChars(line_strimmed_without_continuation_char_as_str);
        %current_multiline_instruction_str  = current_multiline_instruction_str + line_strimmed_without_continuation_char_as_str;
		%current_multiline_instruction = strcat(current_multiline_instruction, line_strimmed_without_continuation_char_as_table);		
	else
		if strlength(current_multiline_instruction_str) > 1
            
            current_multiline_instruction_str = current_multiline_instruction_str + line_strimmed;
            
            %line_strimmed_without_continuation_char_as_table = convertStringsToChars(line_strimmed);
            current_multiline_instruction_table = convertStringsToChars(current_multiline_instruction_str);
            
            disp(string(datetime) + " execution_fichier_m_ligne_par_ligne_avec_multilines, executing multi line with size:" + strlength(current_multiline_instruction_table));
            
            disp(current_multiline_instruction_str);
			evalc(current_multiline_instruction_table);
			current_multiline_instruction_str = "";
        else
            disp(line_strimmed);
			eval(tline);
		end	
    end
    
    
    tline = fgetl(fid);

end

disp(string(datetime) + " execution_fichier_m_ligne_par_ligne_avec_multilines of file " + nom_fichier + " end."  + " Time elapsed in fonction:" + string(datetime - execution_fichier_m_ligne_par_ligne_begin_time));

end
