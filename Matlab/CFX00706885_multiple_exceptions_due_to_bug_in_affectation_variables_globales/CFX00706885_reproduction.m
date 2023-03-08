function CFX00706885_reproduction(varargin)


%clears all the text from the Command Window, resulting in a clear screen
clc

global SMT_mE_aig


disp(string(datetime) + "CFX00706885_reproduction begin");

%will raise many exceptions due to CFX00706885
affectation_variables_globales("Input\\Structure_created_empty_then_filled.m");


disp(string(datetime) + " CFX00706885_reproduction end");


return
end