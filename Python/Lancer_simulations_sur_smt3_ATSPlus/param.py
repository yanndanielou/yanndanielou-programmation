# -*-coding:Utf-8 -*

import SMT3Server

import SimulationsRequestsManager

import logging
logger_level = logging.INFO

pas_sauvegarde = 1


sMT3Servers = list()
#sMT3Servers.append(SMT3Server.SMT3Server(8081, "D_5_3_0", "A"))
#sMT3Servers.append(SMT3Server.SMT3Server(8082, "D5_3_2", ""))
sMT3Servers.append(SMT3Server.SMT3Server(8083, "D5_3_2_1",""))
#sMT3Servers.append(SMT3Server.SMT3Server(8084, "D5_3_2_Beta2", "B"))
#sMT3Servers.append(SMT3Server.SMT3Server(8081, "D_5_3_0", "C"))
#sMT3Servers.append(SMT3Server.SMT3Server(8084, "D5_3_2_Beta3", "D"))

listNumerosSimulationsAEffectuer = None
#listNumerosSimulationsAEffectuer = list()
#listNumerosSimulationsAEffectuer.append(750)
#listNumerosSimulationsAEffectuer.append(751)
#listNumerosSimulationsAEffectuer.append(752)
                            


SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path = '7316_ME_D5_3_0_P1\\SMT2_Data_param_for_SMT3_launched_in_Matlab.m'
SMT2_Data_mE_file_name_with_path = '7316_ME_D5_3_0_P1\\SMT2_Data_mE.m'

all_elementary_missions_names_as_list = SimulationsRequestsManager.retrieve_all_field_string_content(SMT2_Data_mE_file_name_with_path, "nom")
all_nom_modele_as_list = SimulationsRequestsManager.retrieve_all_field_string_content(SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path, "nom_modele")
#all_nom_train_as_list = SimulationsRequestsManager.retrieve_all_field_string_content(SMT2_Data_param_for_SMT3_launched_in_Matlab_file_name_with_path, "nom_train")

#LoggerConfig.printAndLogInfo("Nombre de missions élémentaires : " + str(len(all_elementary_missions_names_as_list)))
#LoggerConfig.printAndLogInfo("Nombre de modeles : " + str(len(all_nom_modele_as_list)))

                        
step_in_second = 0.2
dwell_time_in_second = 30.0
simulationsRequestsManager = SimulationsRequestsManager.SimulationsRequestsManager()
simulationsRequestsManager.generateAllMissionElementaireCombinations(step_in_second, dwell_time_in_second, all_elementary_missions_names_as_list, all_nom_modele_as_list)
#simulationsRequestsManager.default_step_in_second.append