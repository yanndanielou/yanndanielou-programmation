function SMT2_Data_mE_fichier_texte(varargin)

disp(string(datetime) + " SMT2_Data_mE_fichier_texte begin");

affectation_variables_globales_to_test_load_data("D:\GitHub\yanndanielou-programmation\Matlab\SMT2_Data_mE_fichier_texte\Sources\fichiers_m_generes\SMT2_Data_mE_part_001_initialisation_structures.m");
execution_fichier_m_ligne_par_ligne("D:\GitHub\yanndanielou-programmation\Matlab\SMT2_Data_mE_fichier_texte\Sources\fichiers_txt_generes\SMT2_Data_mE_part_002.txt");

disp(string(datetime) + " SMT2_Data_mE_fichier_texte end");

return
end