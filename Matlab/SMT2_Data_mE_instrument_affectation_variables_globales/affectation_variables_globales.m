function affectation_variables_globales(nom_fichier)

disp(string(datetime) + " affectation_variables_globales of file " + nom_fichier + " begin");
affectation_variables_globales_begin_time = datetime;


%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
fid = fopen(nom_fichier);
list_var_global{:,1} = [];
ligne_list_var_global = 1;
tline = fgetl(fid);
while ischar(tline)
    tline = fgetl(fid);
    if ischar(tline)
        tline = strtrim(tline);
        if length(tline) > length('global')
            if strcmp(tline(1:length('global')),'global')
                list_var_global{ligne_list_var_global,1} = tline;
                ligne_list_var_global = ligne_list_var_global + 1;
            end
        end
    end
end
fclose(fid);
nom_var_global{:,1} = [];
ligne_nom_var_global = 1;
if ~isempty(list_var_global{1,1})
    for nb_var_global = 1 : size(list_var_global,1)
        var = strtrim(list_var_global{nb_var_global,1});
        var_ss_global = strtrim(var(length('global')+2:end));
        espace = regexp(var_ss_global,char(32));
        if isempty(espace)
            nom_var_global{ligne_nom_var_global,1} = var_ss_global;
            ligne_nom_var_global = ligne_nom_var_global + 1;
        else
            if isequal(size(espace,2),1)
                nom_var_global{ligne_nom_var_global,1} = var_ss_global(1:espace-1);
                ligne_nom_var_global = ligne_nom_var_global + 1;
                nom_var_global{ligne_nom_var_global,1} = var_ss_global(espace+1:end);
                ligne_nom_var_global = ligne_nom_var_global + 1;
            else
                for nb_espace = 1 : size(espace,2)
                    if isequal(nb_espace,1)
                        nom_var_global{ligne_nom_var_global,1} = var_ss_global(1:espace(1,nb_espace)-1);
                        ligne_nom_var_global = ligne_nom_var_global + 1;
                    else
                        nom_var_global{ligne_nom_var_global,1} = var_ss_global(espace(1,nb_espace-1)+1:espace(1,nb_espace)-1);
                        ligne_nom_var_global = ligne_nom_var_global + 1;
                    end
                end
                nom_var_global{ligne_nom_var_global,1} = var_ss_global(espace(1,nb_espace)+1:end);
                ligne_nom_var_global = ligne_nom_var_global + 1;
            end
        end
    end
end

fid = fopen(nom_fichier);
chaine_a_traiter = fread(fid,'*char')';
fclose(fid);
disp(string(datetime) + " affectation_variables_globales. Second read of file. Will now start parsing " + size(nom_var_global,1) +  " nom_var_globals" + ". Time elapsed in fonction:" + string(datetime - affectation_variables_globales_begin_time));
for aa = 1 : size(nom_var_global,1)

	%Just to track progress and performances
	disp(string(datetime) + " affectation_variables_globales. Iterating nom_var_global " + nom_var_global{aa,1} + " number:" + aa + ". Time elapsed in fonction:" + string(datetime - affectation_variables_globales_begin_time));
	nombre_exception_raised = 0;
    nombre_evaluations_matlab_reussies = 0;

    flag_index_nom_multiple = 0;
    index_nom_var = strfind(chaine_a_traiter,[nom_var_global{aa,1} ' =']);
    if isempty(index_nom_var)
        index_nom_var = strfind(chaine_a_traiter,[nom_var_global{aa,1} ' =']);
        if isempty(index_nom_var)
            index_nom_var = strfind(chaine_a_traiter,nom_var_global{aa,1});
            new_index_nom_var = cell(1,size(index_nom_var,2));
            nb = 1;
            for nb_index_nom_var = 1 : size(index_nom_var,2)
                caractere_suivant_index_nom_var = chaine_a_traiter(index_nom_var(1,nb_index_nom_var):index_nom_var(1,nb_index_nom_var)+length(nom_var_global{aa,1}));
                if strcmp(caractere_suivant_index_nom_var(end),char(32)) || ...
                        strcmp(caractere_suivant_index_nom_var(end),char(61)) || ...
                        strcmp(caractere_suivant_index_nom_var(end),char(40)) || ...
                        strcmp(caractere_suivant_index_nom_var(end),char(46))
                    new_index_nom_var{1,nb} = index_nom_var(1,nb_index_nom_var);
                    nb = nb + 1;
                end
            end
            disp(string(datetime) + " affectation_variables_globales. Iterating nom_var_global " + nom_var_global{aa,1} + " number:" + aa + " Step 1. Time elapsed in fonction:" + string(datetime - affectation_variables_globales_begin_time));
            flag_index_nom_multiple = 1;
        end
    end
    if isequal(flag_index_nom_multiple,0)
        index_crochet_points = strfind(chaine_a_traiter(index_nom_var:end),'[ ...');
        if isempty(index_crochet_points)
            index_crochet_points = strfind(chaine_a_traiter(index_nom_var:end),'[...');
        end
        if ~isempty(index_crochet_points)
            index_fin_instruction = strfind(chaine_a_traiter(index_nom_var:end),'];');
        else
            index_fin_instruction = strfind(chaine_a_traiter(index_nom_var:end),char(59));
        end
        valeur_var_global = chaine_a_traiter(index_nom_var:index_nom_var+min(index_fin_instruction)-1);
    else
        index_crochet_points = strfind(chaine_a_traiter(new_index_nom_var{1,2}:end),'[ ...');
        if isempty(index_crochet_points)
            index_crochet_points = strfind(chaine_a_traiter(new_index_nom_var{1,2}:end),'[...');
        end
        if ~isempty(index_crochet_points)
            index_fin_instruction = strfind(chaine_a_traiter(new_index_nom_var{1,2}:end),'];');
        else
            index_fin_instruction = strfind(chaine_a_traiter(new_index_nom_var{1,2}:end),char(59));
        end
        valeur_var_global = chaine_a_traiter(new_index_nom_var{1,2}:new_index_nom_var{1,2}+max(index_fin_instruction)-1);
    end
    declaration_var_global = ['global ' nom_var_global{aa,1}];
    
    evalc(declaration_var_global);
    nombre_evaluations_matlab_reussies = nombre_evaluations_matlab_reussies + 1;
    evalc(valeur_var_global);
    nombre_evaluations_matlab_reussies = nombre_evaluations_matlab_reussies + 1;
    
    if strcmp(nom_var_global{aa,1},'SMT_param')
        index_remplissage_param = strfind(chaine_a_traiter(1,max(index_fin_instruction):end),[nom_var_global{aa,1} '(']);
    else
        index_remplissage_param = strfind(chaine_a_traiter,[nom_var_global{aa,1} '(']);
    end
    if ~isempty(index_remplissage_param)
        index_fin_instruction_param = strfind(chaine_a_traiter(1,index_remplissage_param(1,1):end),';');
        disp(string(datetime) + " affectation_variables_globales. Iterating nom_var_global " + nom_var_global{aa,1} + " number:" + aa + ". Nombre d instructions Matlab a evaluer:" + size(index_remplissage_param,2) + ". Time elapsed in fonction:" + string(datetime - affectation_variables_globales_begin_time));
        for nb_index_remplissage_param = 1 : size(index_remplissage_param,2)
            if ~isequal(nb_index_remplissage_param,1)
                index_dans_chaine_a_traiter = index_remplissage_param(1,nb_index_remplissage_param):index_remplissage_param(1,nb_index_remplissage_param) + index_fin_instruction_param(1,nb_index_remplissage_param)-index_fin_instruction_param(1,nb_index_remplissage_param-1);
            else
                index_dans_chaine_a_traiter = index_remplissage_param(1,nb_index_remplissage_param):index_remplissage_param(1,nb_index_remplissage_param)+index_fin_instruction_param(1,nb_index_remplissage_param);
            end
            chaine_a_evaluer = chaine_a_traiter(index_dans_chaine_a_traiter);
            try
                evalc(chaine_a_evaluer);
                nombre_evaluations_matlab_reussies = nombre_evaluations_matlab_reussies + 1;
            catch ME
                exception_identifier = ME.identifier;
                nombre_exception_raised = nombre_exception_raised + 1;
                nombre_maximum_d_exceptions_a_loguer = 100;
                if nombre_exception_raised <= nombre_maximum_d_exceptions_a_loguer
                    disp(string(datetime) + " affectation_variables_globales. Exception " + exception_identifier + " raised when executing evalc for:" + chaine_a_evaluer);
                    if nombre_exception_raised == nombre_maximum_d_exceptions_a_loguer                        
                        disp(string(datetime) + " affectation_variables_globales. Future exceptions raised will not be logged in order to avoid flooding (only the first " + nombre_maximum_d_exceptions_a_loguer + " were printed)");
                    end
                elseif mod(nombre_exception_raised, 10000) == 0 
                    %Just to track progress
                    disp(string(datetime) + " affectation_variables_globales. " + nombre_exception_raised +  " nth exception raised");
                end
            end
        end
    end
    disp(string(datetime) + " affectation_variables_globales. Iterating nom_var_global " + nom_var_global{aa,1} + " number:" + aa + " End. Nombre evaluations reussies: " + nombre_evaluations_matlab_reussies +  ", nombre exceptions levees:"  + nombre_exception_raised + ". Time elapsed in fonction:" + string(datetime - affectation_variables_globales_begin_time));

end
disp(string(datetime) + " affectation_variables_globales of file " + nom_fichier + " end."  + " Time elapsed in fonction:" + string(datetime - affectation_variables_globales_begin_time));
end
