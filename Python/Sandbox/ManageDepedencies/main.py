# -*-coding:Utf-8 -*

import random

#import sys

import time

import os

import sys
import importlib.util

def import_from_path(module_name, file_path):
    spec = importlib.util.spec_from_file_location(module_name, file_path)
    module = importlib.util.module_from_spec(spec)
    sys.modules[module_name] = module
    spec.loader.exec_module(module)
    return module

logger_config = import_from_path("logger_config", "../../Logger/logger_config.py")
date_time_formats = import_from_path("date_time_formats", "../../Common/date_time_formats.py")

def _import(func,*args):
    import os
    from importlib import util
    module_name = "my_module"
    BASE_DIR = "wanted module directory path"
    path =  os.path.join(BASE_DIR,module_name)
    spec = util.spec_from_file_location(func, path)
    mod = util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return getattr(mod,func)(*args)

def main():

    """ Main function """
    application_start_time = time.time()
    log_file_name = os.path.basename(__file__) + str(random.randrange(100000)) + ".log"
    logger_config.configure_logger(log_file_name)

    logger_config.print_and_log_info("End. Nominal end of application in " + date_time_formats.format_duration_to_string(
    time.time() - application_start_time))


if __name__ == "__main__":
    # sys.argv[1:]
    main()
