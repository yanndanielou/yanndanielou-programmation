function Test_load_SMT2_Data_mE_and_dump_to_test_SMT2_Data_mE_refactoring_non_reg(varargin)

% Time kept to track performances and initialization time by step
application_launch_time = datetime;

global SMT_mE_aig

disp("Load initial structure");

affectation_variables_globales("input_files\SMT2_Data_mE.m");


disp("Print initial structure:");
print_SMT_mE_aig_content(SMT_mE_aig);

nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne = "input_files\fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne.m";
if isfile(nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne)
	disp(string(datetime) + " " + "optionnal nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne file " + nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne + " exists: will be parsed by execution_fichier_m_ligne_par_ligne function." + " (Time since application startup:" + (string(datetime - application_launch_time)) + ")");
	execution_fichier_m_ligne_par_ligne(nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne);
else
	disp(string(datetime) + " " + "optionnal nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne file " + nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne + " does not exist: step skipped." + " (Time since application startup:" + (string(datetime - application_launch_time)) + ")");
end

nom_fichier_a_executer_dans_affectation_variables_globales = "input_files\fichier_a_executer_dans_affectation_variables_globales.m";
if isfile(nom_fichier_a_executer_dans_affectation_variables_globales)
	disp(string(datetime) + " " + "optionnal nom_fichier_a_executer_dans_affectation_variables_globales file " + nom_fichier_a_executer_dans_affectation_variables_globales + " exists: will be parsed by affectation_variables_globales function." + " (Time since application startup:" + (string(datetime - application_launch_time)) + ")");
	affectation_variables_globales(nom_fichier_a_executer_dans_affectation_variables_globales);
else
	disp(string(datetime) + " " + "optionnal nom_fichier_a_executer_dans_affectation_variables_globales file " + nom_fichier_a_executer_dans_affectation_variables_globales + " does not exist: step skipped." + " (Time since application startup:" + (string(datetime - application_launch_time)) + ")");
end



disp("Print final structure after changes:");
print_SMT_mE_aig_content(SMT_mE_aig);


return
end
