function SMT2_Data_mE_instrument_affectation_variables_globales(varargin)


%clears all the text from the Command Window, resulting in a clear screen
clc

global SMT_mE_aig


disp(string(datetime) + " SMT2_Data_mE_instrument_affectation_variables_globales begin");

affectation_variables_globales("Input\\Only_the_lines_of_One_Structure_filled_at_creation.m");


print_structure_content(SMT_mE_aig, "SMT_mE_aig")

disp(string(datetime) + " SMT2_Data_mE_instrument_affectation_variables_globales end");


return
end