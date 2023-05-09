%Yann Danielou 14th february 2023 
function execution_fichier_m_complet(nom_fichier)

disp(string(datetime) + " execution_fichier_m_complet of file " + nom_fichier + " begin");
execution_fichier_m_complet_begin_time = datetime;

line_number = 0;
nombre_de_characteres=0;

fid = fopen(nom_fichier);
list_var_global{:,1} = [];
ligne_list_var_global = 1;
tline = fgetl(fid);
while ischar(tline)
	nombre_de_characteres = nombre_de_characteres + length(tline)
end

disp(string(datetime) + " execution_fichier_m_complet of file " + nom_fichier + " end." + line_number +  " lines executed." + " Time elapsed in fonction:" + string(datetime - execution_fichier_m_complet_begin_time));
end
