# -*-coding:Utf-8 -*

# This file contains static configuration of the tool
# Constants are defined here so they can be changed by project (or temporary for debugging purpose for example)
# You can create a symbolic link of that param file, or change its content (values) it depending on the use

import logging

logger_level = logging.INFO

tracking_message_pattern_as_string = "M[_]TRAIN[_]CC[_]\\d+[_]CC[_]EQPT[_]TRACKING"
duplication_number_of_tracking_messages = 5

