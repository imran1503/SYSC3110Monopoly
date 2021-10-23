import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardConstructor {

    private Board board;


    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }


    public boolean loadBoardFromMapFile(String filename, Board board) {
        if (validateXMLSchema("src/board.xsd", "src/board.xml")) {
            try {
                //an instance of factory that gives a document builder
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                //an instance of builder to parse the specified xml file
                DocumentBuilder db = dbf.newDocumentBuilder();

                InputStream in = getClass().getResourceAsStream("board.xml");

                Document doc = db.parse(in);

                doc.getDocumentElement().normalize();

                NodeList propertyList = doc.getElementsByTagName("Property");
                NodeList TaxList = doc.getElementsByTagName("Tax");
                NodeList utilitiesList = doc.getElementsByTagName("Utilites");
                NodeList railroadList = doc.getElementsByTagName("Railroad");
                NodeList goList = doc.getElementsByTagName("GO");
                NodeList jailList = doc.getElementsByTagName("Jail");
                NodeList goToJailList = doc.getElementsByTagName("GoToJail");

                //iterates through Properties
                for (int itr = 0; itr < propertyList.getLength(); itr++) {
                    Node propertyNode = propertyList.item(itr);
                    if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element propertyElement = (Element) propertyNode;
                        Properties newProperties = new Properties(propertyElement.getElementsByTagName("name").item(0).getTextContent(),
                                                                  Integer.parseInt(propertyElement.getElementsByTagName("price").item(0).getTextContent()),
                                                                  Integer.parseInt(propertyElement.getElementsByTagName("rent").item(0).getTextContent()),
                                                                  new Color( Integer.parseInt(propertyElement.getElementsByTagName("color").item(0).getTextContent()),  //R
                                                                             Integer.parseInt(propertyElement.getElementsByTagName("color").item(1).getTextContent()),  //G
                                                                             Integer.parseInt(propertyElement.getElementsByTagName("color").item(2).getTextContent())), //B
                                                                  Integer.parseInt(propertyElement.getElementsByTagName("index").item(0).getTextContent())
                                                                  );
                        board.getPropertiesArrayList().add(newProperties);
                    }
                }

                for (int itr = 0; itr < TaxList.getLength(); itr++) {
                    Node TaxNode = TaxList.item(itr);
                    if (TaxNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element TaxElement = (Element) TaxNode;
                        Properties newProperties = new Properties(TaxElement.getElementsByTagName("name").item(0).getTextContent(),
                                new Color( Integer.parseInt(TaxElement.getElementsByTagName("color").item(0).getTextContent()),  //R
                                        Integer.parseInt(TaxElement.getElementsByTagName("color").item(1).getTextContent()),     //G
                                        Integer.parseInt(TaxElement.getElementsByTagName("color").item(2).getTextContent())),    //B
                                Integer.parseInt(TaxElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().add(newProperties);
                    }
                }

                for (int itr = 0; itr < railroadList.getLength(); itr++) {
                    Node railroadNode = railroadList.item(itr);
                    if (railroadNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element railroadElement = (Element) railroadNode;
                        Properties newProperties = new Properties(railroadElement.getElementsByTagName("name").item(0).getTextContent(),
                                Integer.parseInt(railroadElement.getElementsByTagName("price").item(0).getTextContent()),
                                Integer.parseInt(railroadElement.getElementsByTagName("rent").item(0).getTextContent()),
                                new Color( Integer.parseInt(railroadElement.getElementsByTagName("color").item(0).getTextContent()),  //R
                                        Integer.parseInt(railroadElement.getElementsByTagName("color").item(1).getTextContent()),  //G
                                        Integer.parseInt(railroadElement.getElementsByTagName("color").item(2).getTextContent())), //B
                                Integer.parseInt(railroadElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().add(newProperties);
                    }
                }

                for (int itr = 0; itr < utilitiesList.getLength(); itr++) {
                    Node utilitiesNode = utilitiesList.item(itr);
                    if (utilitiesNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element utilitiesElement = (Element) utilitiesNode;
                        Properties newProperties = new Properties(utilitiesElement.getElementsByTagName("name").item(0).getTextContent(),
                                Integer.parseInt(utilitiesElement.getElementsByTagName("price").item(0).getTextContent()),
                                Integer.parseInt(utilitiesElement.getElementsByTagName("rent").item(0).getTextContent()),
                                new Color( Integer.parseInt(utilitiesElement.getElementsByTagName("color").item(0).getTextContent()),  //R
                                        Integer.parseInt(utilitiesElement.getElementsByTagName("color").item(1).getTextContent()),  //G
                                        Integer.parseInt(utilitiesElement.getElementsByTagName("color").item(2).getTextContent())), //B
                                Integer.parseInt(utilitiesElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().add(newProperties);
                    }
                }

                for (int itr = 0; itr < goList.getLength(); itr++) {
                    Node goNode = goList.item(itr);
                    if (goNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element goElement = (Element) goNode;
                        Properties newProperties = new Properties(goElement.getElementsByTagName("name").item(0).getTextContent(),
                                new Color( Integer.parseInt(goElement.getElementsByTagName("color").item(0).getTextContent()),  //R
                                        Integer.parseInt(goElement.getElementsByTagName("color").item(1).getTextContent()),  //G
                                        Integer.parseInt(goElement.getElementsByTagName("color").item(2).getTextContent())), //B
                                Integer.parseInt(goElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().add(newProperties);
                    }
                }

                for (int itr = 0; itr < jailList.getLength(); itr++) {
                    Node jailNode = jailList.item(itr);
                    if (jailNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element jailElement = (Element) jailNode;
                        Properties newProperties = new Properties(jailElement.getElementsByTagName("name").item(0).getTextContent(),
                                new Color( Integer.parseInt(jailElement.getElementsByTagName("color").item(0).getTextContent()),  //R
                                        Integer.parseInt(jailElement.getElementsByTagName("color").item(1).getTextContent()),  //G
                                        Integer.parseInt(jailElement.getElementsByTagName("color").item(2).getTextContent())), //B
                                Integer.parseInt(jailElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().add(newProperties);
                    }
                }

                for (int itr = 0; itr < goToJailList.getLength(); itr++) {
                    Node goToJailNode = goToJailList.item(itr);
                    if (goToJailNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element goToJailElement = (Element) goToJailNode;
                        Properties newProperties = new Properties(goToJailElement.getElementsByTagName("name").item(0).getTextContent(),
                                new Color( Integer.parseInt(goToJailElement.getElementsByTagName("color").item(0).getTextContent()),  //R
                                        Integer.parseInt(goToJailElement.getElementsByTagName("color").item(1).getTextContent()),  //G
                                        Integer.parseInt(goToJailElement.getElementsByTagName("color").item(2).getTextContent())), //B
                                Integer.parseInt(goToJailElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().add(newProperties);
                    }
                }


                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
