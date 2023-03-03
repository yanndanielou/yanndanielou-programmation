function SMT2_Data_high_performances(varargin)


%clears all the text from the Command Window, resulting in a clear screen
clc

global SMT_mE_aig


disp(string(datetime) + " SMT2_Data_high_performances begin");

execution_fichier_m_ligne_par_ligne_avec_multilines("Input\\Only_the_lines_of_One_Structure_filled_at_creation.m");

print_structure_content(SMT_mE_aig, "SMT_mE_aig")

disp(string(datetime) + " SMT2_Data_high_performances end");


return
end