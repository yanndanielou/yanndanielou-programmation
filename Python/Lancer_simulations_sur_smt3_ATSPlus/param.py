# -*-coding:Utf-8 -*

import SMT3Server

import logging
logger_level = logging.INFO

sMT3Servers = list()
sMT3Servers.append(SMT3Server.SMT3Server(8081, "D_5_3_0"))
sMT3Servers.append(SMT3Server.SMT3Server(8082, "D_5_3_1"))
sMT3Servers.append(SMT3Server.SMT3Server(8083, "D_5_3_2"))

