function print_SMT_mE_aig_content(SMT_mE_aig)

disp(string(datetime) + " print_SMT_mE_aig_content " + " begin");

disp(string(datetime) + " SMT_mE_aig fields:");
disp(SMT_mE_aig);


for SMT_mE_aig_it = 1 : length(SMT_mE_aig)

	disp("SMT_mE_aig_it " +  SMT_mE_aig_it + " : ");
	disp(SMT_mE_aig(SMT_mE_aig_it));

end

disp(string(datetime) + " print_SMT_mE_aig_content end")
end
