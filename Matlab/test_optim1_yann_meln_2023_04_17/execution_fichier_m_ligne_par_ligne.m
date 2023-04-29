%Yann Danielou 14th february 2023 
function execution_fichier_m_ligne_par_ligne(nom_fichier)

disp(string(datetime) + " execution_fichier_m_ligne_par_ligne of file " + nom_fichier + " begin");
execution_fichier_m_ligne_par_ligne_begin_time = datetime;

line_number = 0;

fid = fopen(nom_fichier);
list_var_global{:,1} = [];
ligne_list_var_global = 1;
tline = fgetl(fid);
while ischar(tline)
	line_number = line_number + 1;
		
	%Just to track progress and performances
	if mod(line_number, 100000) == 0
		disp(string(datetime) + " Execute line number:" + line_number + " of file:" + nom_fichier + ". Time elapsed in fonction:" + string(datetime - execution_fichier_m_ligne_par_ligne_begin_time));
	end
	
	disp(string(datetime) + " Execute line with size:" + length(tline) + " : " + tline)
    evalc(tline);
    tline = fgetl(fid);
end

disp(string(datetime) + " execution_fichier_m_ligne_par_ligne of file " + nom_fichier + " end." + line_number +  " lines executed." + " Time elapsed in fonction:" + string(datetime - execution_fichier_m_ligne_par_ligne_begin_time));
end
