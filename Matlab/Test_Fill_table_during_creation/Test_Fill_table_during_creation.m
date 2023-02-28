function Test_Fill_table_during_creation(varargin)

disp(string(datetime) + " Test_Fill_table_during_creation begin");


first_table_created_empty = {[],[],[],[],[]}
first_table_created_empty(1,1) = 011;
first_table_created_empty(1,2) = 012;

first_table_created_empty(2,1) = 021;
first_table_created_empty(2,2) = 022;

print("first_table_created_empty:");
print(first_table_created_empty);

first_table_created_full = {[],[],[],[],[]}


disp(string(datetime) + " Test_Fill_table_during_creation end");

return
end