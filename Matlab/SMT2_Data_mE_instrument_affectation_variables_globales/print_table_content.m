function print_table_content(table_to_print, table_name)

disp(" print_table_content " + table_name + " begin");

disp(table_to_print);


for table_it = 1 : length(table_to_print)

	disp(table_name + " at " +  table_it + " : ");
	disp(table_to_print(table_it));

end

disp(" print_table_content " + table_name + " end")
end
