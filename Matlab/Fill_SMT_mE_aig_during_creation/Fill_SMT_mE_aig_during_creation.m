function Fill_SMT_mE_aig_during_creation(varargin)

%clears all the text from the Command Window, resulting in a clear screen
clc

line_num=dbstack;disp("line:" + line_num(end).line + " " + string(datetime) + " Fill_SMT_mE_aig_during_creation begin");


execution_fichier_m_ligne_par_ligne_avec_multilines("input\\SMT_mE_aig_avec_champs_reduits_cdv_commut_vide_et_rempli.m");
execution_fichier_m_ligne_par_ligne("input\\lignes_remplissage_cdv_commut_created_empty.txt");
       
	   
global SMT_mE_aig

 for structure_it = 1 : length(SMT_mE_aig)
    line_num=dbstack;disp("line:" + line_num(end).line + " " + " Check structure indice:" + structure_it);
    
    
    are_equal = size(SMT_mE_aig(structure_it).cdv_commut_created_empty) == size(SMT_mE_aig(structure_it).cdv_commut_created_full);
    line_num=dbstack;disp("line:" + line_num(end).line + " " +  " size(SMT_mE_aig(" + structure_it + ") are_equal:" + are_equal);
    
    line_num=dbstack;disp("line:" + line_num(end).line + " " +  " size(SMT_mE_aig(" + structure_it + ").cdv_commut_created_empty):");
    disp(size(SMT_mE_aig(structure_it).cdv_commut_created_empty));
    line_num=dbstack;disp("line:" + line_num(end).line + " " + " size(SMT_mE_aig(" + structure_it + ").cdv_commut_created_full):");
    disp(size(SMT_mE_aig(structure_it).cdv_commut_created_full));
    
       
    size_SMT_mE_aig_structure_it_cdv_commut_created_empty = size(SMT_mE_aig(structure_it).cdv_commut_created_empty);
    length_size_SMT_mE_aig_structure_it_cdv_commut_created_empty = length(size_SMT_mE_aig_structure_it_cdv_commut_created_empty);
    for first_dimension_it = 1 : size_SMT_mE_aig_structure_it_cdv_commut_created_empty(1)
        line_num=dbstack;disp("line:" + line_num(end).line + " SMT_mE_aig(" + structure_it + ") first_dimension_it: " + first_dimension_it);
        
            
        are_equal = SMT_mE_aig(structure_it).cdv_commut_created_empty(first_dimension_it) == SMT_mE_aig(structure_it).cdv_commut_created_full(first_dimension_it);
        line_num=dbstack;disp("line:" + line_num(end).line + " " +  " size(SMT_mE_aig(" + structure_it + ") are_equal:" + are_equal);
    
        
        line_num=dbstack;disp("line:" + line_num(end).line + " " + " SMT_mE_aig(" + structure_it + ").cdv_commut_created_empty(" + first_dimension_it + "))");
        disp(SMT_mE_aig(structure_it).cdv_commut_created_empty(first_dimension_it));
        line_num=dbstack;disp("line:" + line_num(end).line + " " + " SMT_mE_aig(" + structure_it + ").cdv_commut_created_full(" + first_dimension_it + "))");
        disp(SMT_mE_aig(structure_it).cdv_commut_created_full(first_dimension_it));
        
        for second_dimension_it = 1 : size_SMT_mE_aig_structure_it_cdv_commut_created_empty(2)
            line_num=dbstack;disp("line:" + line_num(end).line + " SMT_mE_aig(" + structure_it + ") first_dimension_it: " + first_dimension_it  +  " second dimension:" + second_dimension_it);
            
            line_num=dbstack;disp("line:" + line_num(end).line + " " + " SMT_mE_aig(" + structure_it + ").cdv_commut_created_empty(" + first_dimension_it + ", " + second_dimension_it + "))");
            disp(SMT_mE_aig(structure_it).cdv_commut_created_empty(first_dimension_it, second_dimension_it));
            line_num=dbstack;disp("line:" + line_num(end).line + " " + " SMT_mE_aig(" + structure_it + ").cdv_commut_created_full(" + first_dimension_it + ", " + second_dimension_it + "))");
            disp(SMT_mE_aig(structure_it).cdv_commut_created_full(first_dimension_it, second_dimension_it));
        end
        
        %        line_num=dbstack;disp("line:" + line_num(end).line + " " + " size(SMT_mE_aig(" + structure_it + ").cdv_commut_created_empty(" + cdv_commut_created_empty_dimension + ")))");
%        disp(size(SMT_mE_aig(structure_it).cdv_commut_created_empty(cdv_commut_created_empty_dimension)));
%        
%        line_num=dbstack;disp("line:" + line_num(end).line + " " + " size(SMT_mE_aig(" + structure_it + ").cdv_commut_created_full(" + cdv_commut_created_empty_dimension + ")))");
%        disp(size(SMT_mE_aig(structure_it).cdv_commut_created_full(cdv_commut_created_empty_dimension)));
   end
%     
%    
   disp(" ");
%    
end



line_num=dbstack;disp("line:" + line_num(end).line + " " + string(datetime) + " Test_Fill_table_during_creation end");

return
end