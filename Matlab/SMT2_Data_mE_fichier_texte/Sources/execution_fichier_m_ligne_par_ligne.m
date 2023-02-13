function execution_fichier_m_ligne_par_ligne(nom_fichier)

disp(string(datetime) + " execution_fichier_m_ligne_par_ligne of file " + nom_fichier + " begin");
execution_fichier_m_ligne_par_ligne_begin_time = datetime;


fid = fopen(nom_fichier);
list_var_global{:,1} = [];
ligne_list_var_global = 1;
tline = fgetl(fid);
while ischar(tline)
	%disp("Execute line:" + tline)
    evalc(tline);
    tline = fgetl(fid);
end

disp(string(datetime) + " execution_fichier_m_ligne_par_ligne of file " + nom_fichier + " end."  + " Time elapsed in fonction:" + string(datetime - execution_fichier_m_ligne_par_ligne_begin_time));
end
