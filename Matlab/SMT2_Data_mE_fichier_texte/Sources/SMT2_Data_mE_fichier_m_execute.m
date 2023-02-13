function SMT2_Data_mE_fichier_m_execute(varargin)

disp(string(datetime) + " SMT2_Data_mE_fichier_m_execute begin");

affectation_variables_globales_to_test_load_data("D:\GitHub\yanndanielou-programmation\Matlab\SMT2_Data_mE_fichier_texte\Sources\fichiers_m_generes\SMT2_Data_mE_part_001_initialisation_structures.m");
eval("D:\GitHub\yanndanielou-programmation\Matlab\SMT2_Data_mE_fichier_texte\Sources\fichiers_m_generes\SMT2_Data_mE_part_002.m");

disp(string(datetime) + " SMT2_Data_mE_fichier_m_execute end");

return
end