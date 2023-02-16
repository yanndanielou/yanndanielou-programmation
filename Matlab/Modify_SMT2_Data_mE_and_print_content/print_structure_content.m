function print_structure_content(structure_to_print, structure_name)

disp(string(datetime) + " print_structure_content " + structure_name + " begin");

disp("print_structure_content : structure " + structure_name + " fields:");

disp("print_structure_content : structure " + structure_name + " size:" + length(structure_to_print));
disp(structure_to_print);


for structure_it = 1 : length(structure_to_print)

	disp(structure_name + " at " +  structure_it + " : ");
	disp(structure_to_print(structure_it));

end

disp(string(datetime) + " print_structure_content " + structure_name + " end")
end
