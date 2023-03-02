function Test_Fill_table_during_creation(varargin)

%clears all the text from the Command Window, resulting in a clear screen
clc

disp(string(datetime) + " Test_Fill_table_during_creation begin");


  SMT_mE_aig = struct( ...
					  'no',{[10010],[10020],[10031,10032,10033]},...
					  'first_table_created_empty',{[],[],[]},...
					  'first_table_created_full',{[[1001,10012];[0,0]],[[0,0];[0,0]],[[0,0];[30021, 30022]]}...
					  );


SMT_mE_aig(1).first_table_created_empty(1,1) = 1001;
SMT_mE_aig(2).first_table_created_empty(1,2) = 10012;

SMT_mE_aig(3).first_table_created_empty(2,1) = 30021;
SMT_mE_aig(3).first_table_created_empty(2,2) = 30022;





outside_struct_table_created_empty = [];
outside_struct_table_created_full = [[10011, 10012];[10021, 10022]];


outside_struct_table_created_empty(1,1) = 10011;
outside_struct_table_created_empty(1,2) = 10012;


outside_struct_table_created_empty(2,1) = 10021;
outside_struct_table_created_empty(2,2) = 10022;

print_table_content(outside_struct_table_created_empty, "outside_struct_table_created_empty")
print_table_content(outside_struct_table_created_full, "outside_struct_table_created_full")

for structure_it = 1 : length(SMT_mE_aig)
    %print_table_content(SMT_mE_aig(structure_it).no, "SMT_mE_aig" + " at " +  structure_it + " field no")

    %disp("SMT_mE_aig" + " at " +  structure_it + " first_table_created_empty:");
	%for field_it = 1 : length(SMT_mE_aig(structure_it).no)
	%	disp("SMT_mE_aig" + " at " +  structure_it + " field no at " + field_it + " = "  + SMT_mE_aig(structure_it).no(field_it));
	%end
end


for structure_it = 1 : length(SMT_mE_aig)
	print_table_content(SMT_mE_aig(structure_it).first_table_created_empty, "SMT_mE_aig" + " at " +  structure_it + " field first_table_created_empty")
    disp("SMT_mE_aig" + " at " +  structure_it + " first_table_created_empty:");

    disp("SMT_mE_aig(" + structure_it + ").no");
    disp(SMT_mE_aig(structure_it).no);   
	for field_it = 1 : length(SMT_mE_aig(structure_it).no)
		disp("SMT_mE_aig" + " at " +  structure_it + " field no at " + field_it + " = "  + SMT_mE_aig(structure_it).no(field_it));
    end
    
    disp("SMT_mE_aig(" + structure_it + ").first_table_created_empty");
    disp(SMT_mE_aig(structure_it).first_table_created_empty);   
    for field_it = 1 : length(SMT_mE_aig(structure_it).first_table_created_empty)
		disp("SMT_mE_aig" + " at " +  structure_it + " field first_table_created_empty at " + field_it + " = "  + SMT_mE_aig(structure_it).first_table_created_empty(field_it));
    end
    
    first_table_created_full_length = length(SMT_mE_aig(structure_it).first_table_created_full);
    first_table_created_empty_length = length(SMT_mE_aig(structure_it).first_table_created_empty);
    
    if first_table_created_full_length ~= first_table_created_empty_length
        warning("SMT_mE_aig at " + structure_it + " table first_table_created_full has size" + first_table_created_full_length +  " and first_table_created_empty has size:" + first_table_created_empty_length)
    end
    
    disp("SMT_mE_aig(" + structure_it + ").first_table_created_full");
    disp(SMT_mE_aig(structure_it).first_table_created_full); 
    for field_it = 1 : length(SMT_mE_aig(structure_it).first_table_created_full)
		disp("SMT_mE_aig" + " at " +  structure_it + " field first_table_created_full at " + field_it + " = "  + SMT_mE_aig(structure_it).first_table_created_full(field_it));
    end
end



%print_structure_content(SMT_mE_aig, "SMT_mE_aig");


disp(string(datetime) + " Test_Fill_table_during_creation end");
disp(" ");
disp(" ");
disp(" ");
disp(" ");

return
end