function Test_Fill_table_during_creation(varargin)

disp(string(datetime) + " Test_Fill_table_during_creation begin");


  SMT_mE_aig = struct( ...
					  'no',{[41],[41],[41],[41],[41,42,158]},...
					  'first_table_created_empty',{[],[],[],[],[]},...
					  'first_table_created_full',{[011, 012],[],[],[],[]}...
					  );


SMT_mE_aig(1).first_table_created_empty(1,1) = 111;
SMT_mE_aig(1).first_table_created_empty(1,2) = 112;

SMT_mE_aig(3).first_table_created_empty(2,1) = 021;
SMT_mE_aig(3).first_table_created_empty(2,2) = 022;


print_structure_content(SMT_mE_aig, "SMT_mE_aig")


disp(string(datetime) + " Test_Fill_table_during_creation end");

return
end