package core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import common.Logger;
import model.ItunesLibraryModel;
import model.ListOfSongs;
import model.Song;

public class ItunesLibraryModelBuilder {

  public static final String TOP_lEVEL_DICTIONNARY_BALISE_NAME = "dict";
  public static final String LIST_OF_SONGS_DICTIONNARY_BALISE_NAME = "dict";
  public static final String SONG_BALISE_NAME = "dict";

  public ItunesLibraryModel build(Document document, UserInputs userInputs) {
    ItunesLibraryModel itunesLibraryModel = new ItunesLibraryModel(userInputs);

    final Element root = document.getDocumentElement();

    final NodeList rootChildNodes = root.getChildNodes();
    final int rootChildNodesCount = rootChildNodes.getLength();

    final NamedNodeMap rootAttributes = root.getAttributes();
    final int rootAttributesCount = rootAttributes != null ? rootAttributes.getLength() : 0;

    Logger.debug("Root: " + root.getNodeName() + ", rootChildNodesCount: " + rootChildNodesCount + ", rootAttributesCount: " + rootAttributesCount);

    for (int i = 0; i < rootChildNodesCount; i++) {
      Node rootChildNode = rootChildNodes.item(i);

      if (TOP_lEVEL_DICTIONNARY_BALISE_NAME.equals(rootChildNode.getNodeName())) {
        buildTopLevelDictionnary(rootChildNode, itunesLibraryModel);
      }
    }

    return itunesLibraryModel;
  }

  private void buildTopLevelDictionnary(Node topLevelDictionnaryNode, ItunesLibraryModel itunesLibraryModel) {

    final NodeList topLevelDictionnaryChildNodes = topLevelDictionnaryNode.getChildNodes();
    final int topLevelDictionnaryChildNodesCount = topLevelDictionnaryChildNodes.getLength();
    for (int i = 0; i < topLevelDictionnaryChildNodesCount; i++) {
      Node topLevelDictionnaryChildNode = topLevelDictionnaryChildNodes.item(i);

      if (LIST_OF_SONGS_DICTIONNARY_BALISE_NAME.equals(topLevelDictionnaryChildNode.getNodeName())) {
        buildListOfSongsDictionnary(topLevelDictionnaryChildNode, itunesLibraryModel);
      }
    }
  }

  private void buildListOfSongsDictionnary(Node listOfSongsDictionnary, ItunesLibraryModel itunesLibraryModel) {
    ListOfSongs listOfSongs = new ListOfSongs();
    itunesLibraryModel.add(listOfSongs);

    final NodeList listOfSongsDictionnaryChildNodes = listOfSongsDictionnary.getChildNodes();
    final int listOfSongsDictionnaryChildNodesCount = listOfSongsDictionnaryChildNodes.getLength();

    for (int i = 0; i < listOfSongsDictionnaryChildNodesCount; i++) {
      Node listOfSongsDictionnaryChildNode = listOfSongsDictionnaryChildNodes.item(i);
      if (SONG_BALISE_NAME.equals(listOfSongsDictionnaryChildNode.getNodeName())) {
        addSong(listOfSongsDictionnaryChildNode, listOfSongs);
      }
    }
  }

  private void addSong(Node songNode, ListOfSongs listOfSongs) {
    Song song = new Song();
    listOfSongs.add(song);

    final NodeList songNodeChildNodes = songNode.getChildNodes();
    final int songNodeChildNodesCount = songNodeChildNodes.getLength();

    for (int i = 0; i < songNodeChildNodesCount; i++) {
      Node songFieldNameNode = songNodeChildNodes.item(i);
      String songFieldNodeName = songFieldNameNode.getNodeName();
      String songFieldTextContent = songFieldNameNode.getTextContent();

      if (!"key".equals(songFieldNodeName)) {
        continue;
      }

      Node songFieldValueNode = songNodeChildNodes.item(i + 1);

      Object value = getValue(songFieldValueNode);

      song.setAttribute(songFieldTextContent, value);
      i++;
    }

  }

  private Object getValue(Node node) {
    String type = node.getNodeName();
    String valueAsString = node.getTextContent();
    Object ret = null;

    switch (type) {
    case "string":
      ret = asString(valueAsString);
      break;
    case "integer":
      ret = asInt(valueAsString);
      break;
    case "date":
      ret = asDate(valueAsString);
      break;
    case "true":
      ret = true;
      break;
    case "false":
      ret = false;
      break;
    }

    if (ret == null) {
      Logger.error("Unsupported type [" + type + "] with value [" + valueAsString + "]. Will be handled as a string");
      ret = valueAsString;
    }

    return ret;
  }

  protected String asString(String valueAsString) {
    return valueAsString;
  }

  protected Long asInt(String valueAsString) {
    try {
      return Long.valueOf(valueAsString);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      Logger.error("Could not parse [" + valueAsString + "] as int");
      return null;
    }
  }

  protected Date asDate(String valueAsString) {
    String ISO_8061_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssX";
    DateFormat dateFormat = new SimpleDateFormat(ISO_8061_DATE_FORMAT);
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    try {
      Date date = dateFormat.parse(valueAsString);
      return date;
    } catch (ParseException e) {
      e.printStackTrace();
      Logger.error("Could not parse [" + valueAsString + "] as date with format " + ISO_8061_DATE_FORMAT);
      return null;
    }
  }

  protected void printReccursively(Element element) {
    Logger.fullDebug("Start printing reccursively element " + element);
    doPrintReccursively(element, 0);
    Logger.fullDebug("End printing reccursively node " + element);
  }

  protected void printReccursively(Node node) {
    Logger.fullDebug("Start printing reccursively node " + node.getNodeName());
    doPrintReccursively(node, 0);
    Logger.fullDebug("End printing reccursively node " + node.getNodeName());
  }

  private void doPrintReccursively(Node node, int reccursivite) {
    final NodeList nodeChildNodes = node.getChildNodes();
    final int nodeChildNodesCount = nodeChildNodes.getLength();

    NamedNodeMap attributes = node.getAttributes();
    int attributesCount = getAttributesCount(attributes);

    for (int i = 0; i <= reccursivite; i++) {
      Logger.fullDebug("  ");
    }

    Logger.fullDebug("Node: " + node.getNodeName() + ", number of attributes: " + attributesCount + ", number of child nodes: " + nodeChildNodesCount + ", text content: " + node.getTextContent());

    for (int i = 0; i < nodeChildNodesCount; i++) {
      Node childNode = nodeChildNodes.item(i);
      doPrintReccursively(childNode, reccursivite + 1);
    }
  }

  protected void printNodeGeneralInfos(String context, Node node) {
    NodeList childNodes = node.getChildNodes();
    int childNodesCount = childNodes.getLength();

    NamedNodeMap attributes = node.getAttributes();
    int attributesCount = getAttributesCount(attributes);

    String textContent = node.getTextContent();

    Logger.fullDebug(context + ". Node: " + node.getNodeName() + ", number of attributes: " + attributesCount + ", number of child nodes: " + childNodesCount + ", text content: " + textContent);
  }

  private int getAttributesCount(NamedNodeMap map) {
    return map != null ? map.getLength() : 0;
  }
}
