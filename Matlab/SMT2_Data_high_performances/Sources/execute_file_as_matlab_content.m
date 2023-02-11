function execute_file_as_matlab_content(nom_fichier)

disp(string(datetime) + " execute_file_as_matlab_content of file " + nom_fichier + " begin");
affectation_variables_globales_begin_time = datetime;


%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
fid = fopen(nom_fichier);
tline = fgetl(fid);


while ischar(tline)
    evalc(tline + ";")
    tline = fgetl(fid);
end

fclose(fid);



disp(string(datetime) + " execute_file_as_matlab_content of file " + nom_fichier + " end."  + " Time elapsed in fonction:" + string(datetime - affectation_variables_globales_begin_time));
end

