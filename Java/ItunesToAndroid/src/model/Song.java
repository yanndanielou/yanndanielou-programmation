package model;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import common.Logger;
import core.FileUtils;

public class Song {

  public static final String FILE_LOCATION_PATH = "Location";
  public static final String DISABLED = "Disabled";

  private Map<String, Object> attributes;

  private List<File> parentFilesHierarchy = null;

  private String path;
  private File file;

  public Song() {
    attributes = new HashMap<String, Object>();
  }

  public Boolean getBooleanAttributeValue(String key) {
    Object attribute = getAttributeValue(key);
    if (attribute instanceof Boolean) {
      return (Boolean) attribute;
    }

    Logger.error("Attribute [" + key + "] is not a boolean. Value is:" + attribute);
    return null;
  }

  public String getStringAttributeValue(String key) {
    Object attribute = getAttributeValue(key);
    if (attribute instanceof String) {
      return (String) attribute;
    }

    Logger.error("Attribute [" + key + "] is not a string. Value is:" + attribute);
    return null;
  }

  public boolean containsAttribute(String key) {
    return attributes.containsKey(key);
  }

  public Object getAttributeValue(String key) {
    if (containsAttribute(key)) {
      return attributes.get(key);
    }

    Logger.error("Cannot find attribute: [" + key + "] among attributes:" + attributes.keySet());
    return null;
  }

  public void setAttribute(String attributeName, Object attributeValue) {
    if (containsAttribute(attributeName)) {
      Logger.error("Attribute: [" + attributeName + "] is already defined. Cannot set value [" + attributeValue + "]");
    }
    else {
      attributes.put(attributeName, attributeValue);
    }
  }

  public void printAllAttributes() {
    Set<Entry<String, Object>> entrySet = attributes.entrySet();
    for (Entry<String, Object> entry : entrySet) {
      Logger.debug(entry.getKey() + " = " + entry.getValue());
    }
  }

  public void extractPath() {
    path = getStringAttributeValue(FILE_LOCATION_PATH);

    // To avoid IllegalArgumentException "URI has an authority component"
    path = path.replace("//localhost", "");

    URI uri;
    try {
      uri = new URI(path);
    } catch (URISyntaxException | IllegalArgumentException e) {
      e.printStackTrace();
      Logger.error("path [" + path + "[ is not a valid URI for song. Error:" + e.getMessage());
      printAllAttributes();
      return;
    }

    try {
      file = new File(uri);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      Logger.error("Cannot create file with path:" + path + ", uri:" + uri + ", error:" + e.getMessage());
      printAllAttributes();
      return;
    }
  }

  public File getFile() {
    return file;
  }

  public List<File> getParentFilesHierarchy() {
    if (parentFilesHierarchy == null) {
      parentFilesHierarchy = FileUtils.getParentFilesHierarchy(file);
    }
    return parentFilesHierarchy;
  }

  public boolean isDisabled() {
    return containsAttribute(DISABLED) && getBooleanAttributeValue(DISABLED) == true;
  }

}
