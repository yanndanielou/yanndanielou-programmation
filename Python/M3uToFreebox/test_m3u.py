# -*-coding:Utf-8 -*

import m3u
import unittest


class TestM3uFileParser(unittest.TestCase):  

    def __init__(self, methodName='runTest'):  
        super().__init__(methodName)  

        # Test data 
        self.a1 = 20
        self.a2 = 'Ram'
        self.b1 = 10
        self.b2 = 0
        self.checker = m3u.M3uFileParser()  # Creating the object

    def test_parse_file(self):
        # Positive test case
        result:set[m3u.M3uEntry] = self.checker.parse_file("tv_channels_412910643GRB_plus_2024-10-08.m3u")
        self.assertEqual(len(result), 100914)
        

        # Negative test case for data type
        

if __name__ == "__main__":
    unittest.main()
    