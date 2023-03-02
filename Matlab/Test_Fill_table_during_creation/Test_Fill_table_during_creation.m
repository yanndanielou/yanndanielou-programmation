function Test_Fill_table_during_creation(varargin)

%clears all the text from the Command Window, resulting in a clear screen
clc

line_num=dbstack;disp("line:" + line_num(end).line + " " + string(datetime) + " Test_Fill_table_during_creation begin");


  %SMT_mE_aig = struct( ...
  %					  'no',{[10010],[10020],[10031,10032,10033],[],[],[]},...
  %					  'first_table_created_empty',{[],[],[],[],[],[]},...
  %					  'first_table_created_full',{[1001],[[0],[10012]],[[0],[0];[30021],[30022]],[],[],[]}...
  %					  );

  SMT_mE_aig = struct( ...
					  'no',{[10010],[10020],[10031,10032,10033],[],[],[]},...
					  'first_table_created_empty',{[],[],[],[],[],[]},...
					  'first_table_created_full',{....
                                                  [1001],...
                                                  [[0],[10012]],...
                                                  [[0],[0];[30021],[30022]],...
                                                  [[0],[0],[0];[0],[0],[0];[0],[0],[0];[40021],[0],[0]],...
                                                  [[[50011], [50012],[],[0]];[[0],[0],[0]];[[0],[0],[50033]]],...
                                                  []....
                                                  }...
					  );
       

%Structure 1
SMT_mE_aig(1).first_table_created_empty(1,1) = 1001;

%Structure 2
SMT_mE_aig(2).first_table_created_empty(1,2) = 10012;

%Structure 3
SMT_mE_aig(3).first_table_created_empty(2,1) = 30021;
SMT_mE_aig(3).first_table_created_empty(2,2) = 30022;

%Structure 4
SMT_mE_aig(4).first_table_created_empty(4,1) = 40021;

%Structure 5
SMT_mE_aig(5).first_table_created_empty(1,1) = 50011;
SMT_mE_aig(5).first_table_created_empty(1,2) = 50012;

SMT_mE_aig(5).first_table_created_empty(3,3) = 50033;

line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(1).first_table_created_empty):" + length(SMT_mE_aig(1).first_table_created_empty));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(1).first_table_created_full):" + length(SMT_mE_aig(1).first_table_created_full));
disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(1).first_table_created_empty(1)):" + length(SMT_mE_aig(1).first_table_created_empty(1)));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(1).first_table_created_full(1)):" + length(SMT_mE_aig(1).first_table_created_full(1)));
disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(1).first_table_created_empty(1,1):" + SMT_mE_aig(1).first_table_created_empty(1,1));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(1).first_table_created_full(1,1):" + SMT_mE_aig(1).first_table_created_full(1,1));
disp(" ");


%line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(1).first_table_created_empty(1,2):" + SMT_mE_aig(1).first_table_created_empty(1,2));
%line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(1).first_table_created_full(1,2):" + SMT_mE_aig(1).first_table_created_full(1,2));
%disp(" ");

disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(2).first_table_created_empty):" + length(SMT_mE_aig(2).first_table_created_empty));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(2).first_table_created_full):" + length(SMT_mE_aig(2).first_table_created_full));
disp(" ");

%line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(2).first_table_created_empty:" + SMT_mE_aig(2).first_table_created_empty);
%line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(2).first_table_created_full:" + SMT_mE_aig(2).first_table_created_full);
%disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(2).first_table_created_empty(1)):" + length(SMT_mE_aig(2).first_table_created_empty(1)));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(2).first_table_created_full(1)):" + length(SMT_mE_aig(2).first_table_created_full(1)));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(2).first_table_created_empty(1):" + SMT_mE_aig(2).first_table_created_empty(1));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(2).first_table_created_full(1):" + SMT_mE_aig(2).first_table_created_full(1));
disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(2).first_table_created_empty(2)):" + length(SMT_mE_aig(2).first_table_created_empty(2)));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(2).first_table_created_full(2)):" + length(SMT_mE_aig(2).first_table_created_full(2)));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(2).first_table_created_empty(2):" + SMT_mE_aig(2).first_table_created_empty(2));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(2).first_table_created_full(2):" + SMT_mE_aig(2).first_table_created_full(2));
disp(" ");


%line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(2).first_table_created_empty(2,1):" + SMT_mE_aig(2).first_table_created_empty(2,1));
%line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(2).first_table_created_full(2,1):" + SMT_mE_aig(2).first_table_created_full(2,1));
%disp(" ");

%line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(2).first_table_created_empty(2,2):" + SMT_mE_aig(2).first_table_created_empty(2,2));
%line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(2).first_table_created_full(2,2):" + SMT_mE_aig(2).first_table_created_full(2,2));
%disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(3).first_table_created_empty):" + length(SMT_mE_aig(3).first_table_created_empty));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(3).first_table_created_full):" + length(SMT_mE_aig(3).first_table_created_full));
disp(" ");



line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(3).first_table_created_empty(1)):" + length(SMT_mE_aig(3).first_table_created_empty(1)));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(3).first_table_created_full(1)):" + length(SMT_mE_aig(3).first_table_created_full(1)));
disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_empty(1):" + SMT_mE_aig(3).first_table_created_empty(1));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_full(1):" + SMT_mE_aig(3).first_table_created_full(1));
disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_empty(1,1):" + SMT_mE_aig(3).first_table_created_empty(1,1));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_full(1,1):" + SMT_mE_aig(3).first_table_created_full(1,1));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_empty(1,2):" + SMT_mE_aig(3).first_table_created_empty(1,2));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_full(1,2):" + SMT_mE_aig(3).first_table_created_full(1,2));
disp(" ");

%line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_empty(1,2):" + SMT_mE_aig(3).first_table_created_empty(1,3));
%line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_full(1,2):" + SMT_mE_aig(3).first_table_created_full(1,3));
%disp(" ");



line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(3).first_table_created_empty(2)):" + length(SMT_mE_aig(3).first_table_created_empty(2)));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(3).first_table_created_full(2)):" + length(SMT_mE_aig(3).first_table_created_full(2)));
disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_empty(2):" + SMT_mE_aig(3).first_table_created_empty(2));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_full(2):" + SMT_mE_aig(3).first_table_created_full(2));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_empty(2,1):" + SMT_mE_aig(3).first_table_created_empty(2,1));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_full(2,1):" + SMT_mE_aig(3).first_table_created_full(2,1));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_empty(2,2):" + SMT_mE_aig(3).first_table_created_empty(2,2));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(3).first_table_created_full(2,2):" + SMT_mE_aig(3).first_table_created_full(2,2));
disp(" ");
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(4).first_table_created_empty):" + length(SMT_mE_aig(4).first_table_created_empty));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(4).first_table_created_full):" + length(SMT_mE_aig(4).first_table_created_full));
disp(" ");



line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(4).first_table_created_empty(1):" + SMT_mE_aig(4).first_table_created_empty(1));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(4).first_table_created_full(1):" + SMT_mE_aig(4).first_table_created_full(1));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(4).first_table_created_empty(2):" + SMT_mE_aig(4).first_table_created_empty(2));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(4).first_table_created_full(2):" + SMT_mE_aig(4).first_table_created_full(2));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(4).first_table_created_empty(3):" + SMT_mE_aig(4).first_table_created_empty(3));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(4).first_table_created_full(3):" + SMT_mE_aig(4).first_table_created_full(3));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(4).first_table_created_empty(4):" + SMT_mE_aig(4).first_table_created_empty(4));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(4).first_table_created_full(4):" + SMT_mE_aig(4).first_table_created_full(4));
disp(" ");


disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(5).first_table_created_empty):" + length(SMT_mE_aig(5).first_table_created_empty));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(5).first_table_created_full):" + length(SMT_mE_aig(5).first_table_created_full));
disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(1):" + SMT_mE_aig(5).first_table_created_empty(1));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(1):" + SMT_mE_aig(5).first_table_created_full(1));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(1,1):" + SMT_mE_aig(5).first_table_created_empty(1,1));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(1,1):" + SMT_mE_aig(5).first_table_created_full(1,1));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(1,2):" + SMT_mE_aig(5).first_table_created_empty(1,2));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(1,2):" + SMT_mE_aig(5).first_table_created_full(1,2));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(1,3):" + SMT_mE_aig(5).first_table_created_empty(1,3));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(1,3):" + SMT_mE_aig(5).first_table_created_full(1,3));
disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(2):" + SMT_mE_aig(5).first_table_created_empty(2));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(2):" + SMT_mE_aig(5).first_table_created_full(2));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(2,1):" + SMT_mE_aig(5).first_table_created_empty(2,1));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(2,1):" + SMT_mE_aig(5).first_table_created_full(2,1));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(2,2):" + SMT_mE_aig(5).first_table_created_empty(2,2));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(2,2):" + SMT_mE_aig(5).first_table_created_full(2,2));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(2,3):" + SMT_mE_aig(5).first_table_created_empty(2,3));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(2,3):" + SMT_mE_aig(5).first_table_created_full(2,3));
disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(3):" + SMT_mE_aig(5).first_table_created_empty(3));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(3):" + SMT_mE_aig(5).first_table_created_full(3));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(3,1):" + SMT_mE_aig(5).first_table_created_empty(3,1));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(3,1):" + SMT_mE_aig(5).first_table_created_full(3,1));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(3,2):" + SMT_mE_aig(5).first_table_created_empty(3,2));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(3,2):" + SMT_mE_aig(5).first_table_created_full(3,2));
disp(" ");

line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_empty(3,3):" + SMT_mE_aig(5).first_table_created_empty(3,3));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(5).first_table_created_full(3,3):" + SMT_mE_aig(5).first_table_created_full(3,3));
disp(" ");

disp(" ");


line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(6).first_table_created_empty):" + length(SMT_mE_aig(6).first_table_created_empty));
line_num=dbstack;disp("line:" + line_num(end).line + " " + "length(SMT_mE_aig(6).first_table_created_full):" + length(SMT_mE_aig(6).first_table_created_full));
disp(" ");

% 
% outside_struct_table_created_empty = [];
% outside_struct_table_created_full = [[10011, 10012];[10021, 10022]];
% 
% 
% outside_struct_table_created_empty(1,1) = 10011;
% outside_struct_table_created_empty(1,2) = 10012;
% 
% 
% outside_struct_table_created_empty(2,1) = 10021;
% outside_struct_table_created_empty(2,2) = 10022;

%print_table_content(outside_struct_table_created_empty, "outside_struct_table_created_empty")
%print_table_content(outside_struct_table_created_full, "outside_struct_table_created_full")

for structure_it = 1 : length(SMT_mE_aig)
    %print_table_content(SMT_mE_aig(structure_it).no, "SMT_mE_aig" + " at " +  structure_it + " field no")

    %line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig" + " at " +  structure_it + " first_table_created_empty:");
	%for field_it = 1 : length(SMT_mE_aig(structure_it).no)
	%	line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig" + " at " +  structure_it + " field no at " + field_it + " = "  + SMT_mE_aig(structure_it).no(field_it));
	%end
end



for structure_it = 1 : length(SMT_mE_aig)
	print_table_content(SMT_mE_aig(structure_it).first_table_created_empty, "SMT_mE_aig" + " at " +  structure_it + " field first_table_created_empty")
    line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig" + " at " +  structure_it + " first_table_created_empty:");

    %line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(" + structure_it + ").no");
    %line_num=dbstack;disp("line:" + line_num(end).line + " " + SMT_mE_aig(structure_it).no);   
	%for field_it = 1 : length(SMT_mE_aig(structure_it).no)
	%	line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig" + " at " +  structure_it + " field no at " + field_it + " = "  + SMT_mE_aig(structure_it).no(field_it));
    %end
    
    line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(" + structure_it + ").first_table_created_empty");
    line_num=dbstack;disp("line:" + line_num(end).line + " " + SMT_mE_aig(structure_it).first_table_created_empty);   
    for field_it = 1 : length(SMT_mE_aig(structure_it).first_table_created_empty)
		line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig" + " at " +  structure_it + " field first_table_created_empty at " + field_it + " = "  + SMT_mE_aig(structure_it).first_table_created_empty(field_it));
    end
    
    first_table_created_full_length = length(SMT_mE_aig(structure_it).first_table_created_full);
    first_table_created_empty_length = length(SMT_mE_aig(structure_it).first_table_created_empty);
    
    if first_table_created_full_length ~= first_table_created_empty_length
        warning("SMT_mE_aig at " + structure_it + " table first_table_created_full has size:" + first_table_created_full_length +  " and first_table_created_empty has size:" + first_table_created_empty_length)
    end
    
    line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig(" + structure_it + ").first_table_created_full");
    line_num=dbstack;disp("line:" + line_num(end).line + " " + SMT_mE_aig(structure_it).first_table_created_full); 
    for field_it = 1 : length(SMT_mE_aig(structure_it).first_table_created_full)
		line_num=dbstack;disp("line:" + line_num(end).line + " " + "SMT_mE_aig" + " at " +  structure_it + " field first_table_created_full at " + field_it + " = "  + SMT_mE_aig(structure_it).first_table_created_full(field_it));
    end
end



%print_structure_content(SMT_mE_aig, "SMT_mE_aig");


line_num=dbstack;disp("line:" + line_num(end).line + " " + string(datetime) + " Test_Fill_table_during_creation end");

return
end