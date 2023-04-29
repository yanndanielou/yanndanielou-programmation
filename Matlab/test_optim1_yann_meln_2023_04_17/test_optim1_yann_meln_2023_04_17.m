function test_optim1_yann_meln_2023_04_17(varargin)

% Time kept to track performances and initialization time by step
application_launch_time = datetime;

global SMT_mE SMT_mE_seg SMT_mE_aig SMT_mE_feu SMT_mE_feu_BAL SMT_nb_mE

disp(string(datetime) + " " + "Load initial structure");

affectation_variables_globales("input\\SMT2_Data_mE.m");
execution_fichier_m_ligne_par_ligne("input\\SMT2_Data_mE_aig_for_compiled_binary_application.m");
affectation_variables_globales("input\\SMT2_Data_mE_choix.m");
affectation_variables_globales("input\\SMT2_Data_mE_feu.m");
execution_fichier_m_ligne_par_ligne("input\\SMT2_Data_mE_feu_BAL_for_compiled_binary_application.m");
affectation_variables_globales("input\\SMT2_Data_mE_seg.m");


disp(string(datetime) + " " + "end of application");

return
end
