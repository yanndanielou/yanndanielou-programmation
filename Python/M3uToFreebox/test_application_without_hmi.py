# -*-coding:Utf-8 -*


import os
import unittest

import application
import main_view
import Dependencies.Common.unit_tests_helpers


class TestApplicationWithoutHmi(Dependencies.Common.unit_tests_helpers.TestCaseBase):

    def __init__(self, methodName='runTest'):  
        super().__init__(methodName)  
        self._main_view:main_view.M3uToFreeboxMainView = main_view.M3uToFreeboxMainView()

        self.checker:application.M3uToFreeboxApplication = application.M3uToFreeboxApplication(self._main_view)


    def test_create_all_xspf(self):
        """ Number of results """
        # Positive test case
        
        xspf_output_files_directory = "output"
        
        if not os.path.exists(xspf_output_files_directory):
           os.makedirs(xspf_output_files_directory)
        
        self.checker.load_file("tv_channels_412910643GRB_plus_2024-10-08.m3u")
        
        number_files_created = 0
        for m3u_entry in self.checker.m3u_library.m3u_entries:
            number_files_created += 1
            self.assertTrue(self.checker.create_xspf_file_by_id(xspf_output_files_directory + "/",m3u_entry.id, number_files_created%100 == 0))
        
        
        
        
        
if __name__ == "__main__":
    unittest.main()
    