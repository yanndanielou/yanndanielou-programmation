function Test_load_SMT2_Data_mE_contents(varargin)

% Time kept to track performances and initialization time by step
application_launch_time = datetime;

global SMT_mE SMT_mE_seg SMT_mE_aig SMT_mE_feu SMT_mE_feu_BAL SMT_nb_mE

disp("Load initial structure");

nom_fichier_SMT2_Data_mE_initial = "input_files\\SMT2_Data_mE.txt";
if isfile(nom_fichier_SMT2_Data_mE_initial)
	disp(string(datetime) + " " + "optionnal nom_fichier_SMT2_Data_mE_initial file " + nom_fichier_SMT2_Data_mE_initial + " exists: will be parsed by affectation_variables_globales function." + " (Time since application startup:" + (string(datetime - application_launch_time)) + ")");
	affectation_variables_globales(nom_fichier_SMT2_Data_mE_initial);
else
	disp(string(datetime) + " " + "optionnal nom_fichier_initial_a_executer_dans_affectation_variables_globales file " + nom_fichier_SMT2_Data_mE_initial + " does not exist: step skipped." + " (Time since application startup:" + (string(datetime - application_launch_time)) + ")");
end

%Do not Print initial structure
%disp("Print initial structure:");
%print_structure_content(SMT_mE_aig,"SMT_mE_aig");
%print_structure_content(SMT_mE_feu_BAL,"SMT_mE_feu_BAL");

nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne = "input_files\\fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne.m";
if isfile(nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne)
	disp(string(datetime) + " " + "optionnal nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne file " + nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne + " exists: will be parsed by execution_fichier_m_ligne_par_ligne function." + " (Time since application startup:" + (string(datetime - application_launch_time)) + ")");
	execution_fichier_m_ligne_par_ligne(nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne);
else
	disp(string(datetime) + " " + "optionnal nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne file " + nom_fichier_a_executer_dans_execution_fichier_m_ligne_par_ligne + " does not exist: step skipped." + " (Time since application startup:" + (string(datetime - application_launch_time)) + ")");
end

nom_fichier_a_executer_dans_affectation_variables_globales = "input_files\\fichier_a_executer_dans_affectation_variables_globales.m";
if isfile(nom_fichier_a_executer_dans_affectation_variables_globales)
	disp(string(datetime) + " " + "optionnal nom_fichier_a_executer_dans_affectation_variables_globales file " + nom_fichier_a_executer_dans_affectation_variables_globales + " exists: will be parsed by affectation_variables_globales function." + " (Time since application startup:" + (string(datetime - application_launch_time)) + ")");
	affectation_variables_globales(nom_fichier_a_executer_dans_affectation_variables_globales);
else
	disp(string(datetime) + " " + "optionnal nom_fichier_a_executer_dans_affectation_variables_globales file " + nom_fichier_a_executer_dans_affectation_variables_globales + " does not exist: step skipped." + " (Time since application startup:" + (string(datetime - application_launch_time)) + ")");
end

disp("Print final structure after changes:");
print_structure_content(SMT_mE,"SMT_mE");
print_structure_content(SMT_mE_seg,"SMT_mE_seg");
print_structure_content(SMT_mE_aig,"SMT_mE_aig");
print_structure_content(SMT_mE_feu,"SMT_mE_feu");
print_structure_content(SMT_mE_feu_BAL,"SMT_mE_feu_BAL");
print_structure_content(SMT_nb_mE,"SMT_nb_mE");



return
end
