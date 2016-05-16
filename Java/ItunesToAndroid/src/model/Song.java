package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Song {

  private Map<String, Object> fields;

  public Song() {
    fields = new HashMap<String, Object>();
  }

  public void setAttribute(String songFieldNodeName, String songFieldValueNodeName) {
    if (fields.containsKey(songFieldNodeName)) {
      System.out.println("Field: [" + songFieldNodeName + "] is alreadt defined. Cannot set value [" + songFieldValueNodeName + "]");
    }
    else {
      fields.put(songFieldNodeName, songFieldValueNodeName);
    }
  }

  public void printFields() {
    Set<Entry<String, Object>> entrySet = fields.entrySet();
    for (Entry<String, Object> entry : entrySet) {
      System.out.println(entry.getKey() + " = " + entry.getValue());
    }
  }
}
