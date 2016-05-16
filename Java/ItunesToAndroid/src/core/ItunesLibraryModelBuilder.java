package core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.ItunesLibraryModel;
import model.ListOfSongs;
import model.Song;

public class ItunesLibraryModelBuilder {

  public static final String TOP_lEVEL_DICTIONNARY_BALISE_NAME = "dict";
  public static final String LIST_OF_SONGS_DICTIONNARY_BALISE_NAME = "dict";
  public static final String SONG_BALISE_NAME = "dict";

  public ItunesLibraryModel build(Document document) {
    ItunesLibraryModel itunesLibraryModel = new ItunesLibraryModel();

    final Element root = document.getDocumentElement();
    final NodeList rootChildNodes = root.getChildNodes();
    final int rootChildNodesCount = rootChildNodes.getLength();

    final NamedNodeMap rootAttributes = root.getAttributes();
    final int rootAttributesCount = rootAttributes != null ? rootAttributes.getLength() : 0;

    System.out.println("Root: " + root.getNodeName() + ", rootChildNodesCount: " + rootChildNodesCount + ", rootAttributesCount: " + rootAttributesCount);

    for (int i = 0; i < rootChildNodesCount; i++) {
      Node rootChildNode = rootChildNodes.item(i);

      printNodeGeneralInfos("rootChildNode", rootChildNode);

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
      String songFieldName = songFieldNameNode.getNodeName();

      final NodeList songFieldNameNodeChildNodes = songFieldNameNode.getChildNodes();
      final int songFieldNameNodeChildNodesCount = songFieldNameNodeChildNodes.getLength();

      if ("#text".equals(songFieldName) || "key".equals(songFieldName)) {
        continue;
      }

      Node songFieldValueNode = songNodeChildNodes.item(i + 1);

      String songFieldValue = songFieldValueNode.getNodeName();

      song.setAttribute(songFieldName, songFieldValue);
      i++;
    }

    printNodeGeneralInfos("Song", songNode);
  }

  private void fillSongField(Node songFieldNode, Song song) {
    final NodeList songFieldNodeChildNodes = songFieldNode.getChildNodes();
    final int songFieldNodeChildNodesCount = songFieldNodeChildNodes.getLength();

    String songFieldNodeName = songFieldNode.getNodeName();

    if (songFieldNodeChildNodesCount == 1) {
      Node songFieldValue = songFieldNodeChildNodes.item(0);
      String songFieldValueNodeName = songFieldValue.getNodeName();

      String songFieldNodeTextContent = songFieldNode.getTextContent();
      String songFieldValueTextContent = songFieldValue.getTextContent();

      final NodeList songFieldValueChildNodes = songFieldValue.getChildNodes();
      final int songFieldValueChildNodesCount = songFieldValueChildNodes.getLength();

      printNodeGeneralInfos("songFieldValue", songFieldValue);

      song.setAttribute(songFieldNodeName, songFieldValueNodeName);
    }
    if (songFieldNodeChildNodesCount > 1) {
      System.out.println("ERROR; More than 1 child for songFieldNode (" + songFieldNodeChildNodesCount + ")");
    }

    //  printNodeGeneralInfos("Song field", songFieldNode);
  }

  private void printReccursively(Node node) {
    System.out.println("Start printing reccursively node " + node.getNodeName());
    doPrintReccursively(node, 0);
    System.out.println("End printing reccursively node " + node.getNodeName());
  }

  private void doPrintReccursively(Node node, int reccursivite) {
    final NodeList nodeChildNodes = node.getChildNodes();
    final int nodeChildNodesCount = nodeChildNodes.getLength();

    NamedNodeMap attributes = node.getAttributes();
    int attributesCount = getAttributesCount(attributes);

    for (int i = 0; i <= reccursivite; i++) {
      System.out.print("  ");
    }

    System.out.println("Node: " + node.getNodeName() + ", number of attributes: " + attributesCount + ", number of child nodes: " + nodeChildNodesCount);

    for (int i = 0; i < nodeChildNodesCount; i++) {
      Node childNode = nodeChildNodes.item(i);
      doPrintReccursively(childNode, reccursivite + 1);
    }
  }

  private void printNodeGeneralInfos(String context, Node node) {
    NodeList childNodes = node.getChildNodes();
    int childNodesCount = childNodes.getLength();

    NamedNodeMap attributes = node.getAttributes();
    int attributesCount = getAttributesCount(attributes);

    String textContent = node.getTextContent();

    System.out.println(context + ". Node: " + node.getNodeName() + ", number of attributes: " + attributesCount + ", number of child nodes: " + childNodesCount + ", text content: " + textContent);
  }

  private int getAttributesCount(NamedNodeMap map) {
    return map != null ? map.getLength() : 0;

  }
}
