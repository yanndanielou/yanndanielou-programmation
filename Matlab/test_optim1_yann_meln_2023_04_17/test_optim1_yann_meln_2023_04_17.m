function test_optim1_yann_meln_2023_04_17(varargin)

% Time kept to track performances and initialization time by step
application_launch_time = datetime;

global SMT_mE SMT_mE_seg SMT_mE_aig SMT_mE_feu SMT_mE_feu_BAL SMT_nb_mE

disp(string(datetime) + " " + "Load initial structure");

affectation_variables_globales("input\\SMT2_Data_mE.m");
affectation_variables_globales("input\\SMT2_Data_mE_aig.m");
affectation_variables_globales("input\\SMT2_Data_mE_choix.m");
affectation_variables_globales("input\\SMT2_Data_mE_feu.m");
affectation_variables_globales("input\\SMT2_Data_mE_feu_BAL.m");
affectation_variables_globales("input\\SMT2_Data_mE_seg.m");


disp(string(datetime) + " " + "end of application");

return
end
