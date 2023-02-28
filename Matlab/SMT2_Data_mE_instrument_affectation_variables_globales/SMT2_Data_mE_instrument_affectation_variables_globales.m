function SMT2_Data_mE_instrument_affectation_variables_globales(varargin)

if ~isdeployed
    diary SMT2_Data_mE_instrument_affectation_variables_globales.log 
else
    diary off    
end

disp(string(datetime) + " SMT2_Data_mE_instrument_affectation_variables_globales begin");

affectation_variables_globales("Input\\SMT2_Data_mE_2022_10_light.m");

disp(string(datetime) + " SMT2_Data_mE_instrument_affectation_variables_globales end");

diary off

return
end