# -*-coding:Utf-8 -*

# 
from collections import namedtuple

import logging
logger_level = logging.INFO


end_line_character_in_text_file = "\n"
class Metric:

    def __init__(self, time_stamp, name, value_as_string):
        self._time_stamp = time_stamp        
        self._value_as_string = value_as_string
        self._name = name
        
#    def getDayAsString(self):
#        return getIntAsTwoDigitsString(self._time_stamp.day)
        
 #   def getMonthAsString(self):
 #       return getIntAsTwoDigitsString(self._time_stamp.month)
        
    def getYearAsString(self):
        return str(self._time_stamp.year)
        
#    def getHourAsString(self):
#        return getIntAsTwoDigitsString(self._time_stamp.hour)
        
#    def getMinuteAsString(self):
#        return getIntAsTwoDigitsString(self._time_stamp.minute)
        
#    def getSecondAsString(self):
#        return getIntAsTwoDigitsString(self._time_stamp.second)
        
    def getMillisecondAsString(self):
        millisecondAsString = str(int(self._time_stamp.microsecond/1000))
        while len(millisecondAsString) < 3:
            millisecondAsString = "0" + millisecondAsString
        return millisecondAsString        
        
    def getDateAsSplunkMetricsFormat(self):
        """ Ex of Splunk date time format:12/30/2018 04:11:53.466 """
        metrics_date_format = self.getMonthAsString() + "/" + self.getDayAsString() + "/" + self.getYearAsString() + " " + self.getHourAsString() + ":" + self.getMinuteAsString() + ":" + self.getSecondAsString() + "." + self.getMillisecondAsString()
        return metrics_date_format
        
    def getContentForSplunkMetrics(self):
        return self._name + "," + self.getDateAsSplunkMetricsFormat() + "," + self._value_as_string + param.end_line_character_in_text_file
        
    def getDateAsJsonMetricsFormat(self):
        """ {"treatment_date":"2018-12-20 03:03:38.753","utc_treatment_date":"2018-12-20 00:03:38.753","time_stamp":"","id":"S_SRV_B_CPU_TEMPERATURE","equipment_id":"EQ_SRV_B_SRV_B","signal_type":"TG","equipment":"SERVER_B","localisation":"SERVER_B","label":"CPU temperature","old_state":36,"new_state":37,"exec_status":"","caller":"","orders":"","cat_ala":0,"jdb":"0"}, """ 
        json_metrics_date_format = self.getYearAsString() + "-" + self.getMonthAsString() + "-" + self.getDayAsString() + " " + self.getHourAsString() + ":" + self.getMinuteAsString() + ":" + self.getSecondAsString() + "." + self.getMillisecondAsString()
        return json_metrics_date_format
        
    def getContentForJson(self):
        """ Ex: {"time_stamp":"2018-12-19 22:00:49.700","id":"S_TRAIN_CC_214_KM_COUNTER","equipment_id":"EQ_CET_214","signal_type":"TG","equipment":"CET_214","localisation":"TB_PSED009_04","label":"Number of kilometers","old_state":4652,"new_state":4653,"exec_status":"","caller":"","orders":"","cat_ala":0,"jdb":"0"},"""
        contentForJson = '{"time_stamp":"' + self.getDateAsJsonMetricsFormat() + '","name":"' + self._name +  '","value":"' + self._value_as_string + '"},' + param.end_line_character_in_text_file
        return contentForJson