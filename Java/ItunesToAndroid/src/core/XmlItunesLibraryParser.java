package core;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlItunesLibraryParser {

  public Document parse(File file) {
    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    try {
      final DocumentBuilder builder = factory.newDocumentBuilder();
      final Document document = builder.parse(file);
      return document;
    } catch (final ParserConfigurationException e) {
      e.printStackTrace();
    } catch (final SAXException e) {
      e.printStackTrace();
    } catch (final IOException e) {
      e.printStackTrace();
    }
    return null;
  }

}
