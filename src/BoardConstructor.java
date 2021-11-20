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

/**
 * Class BoardConstructor, board variable is the where the properties created will be stored in.
 */
public class BoardConstructor {

    private Board board;


    /**
     *
     * @param xsdPath
     * @param xmlPath
     * @return
     */
    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {

        try {

            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            //System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }


    /**
     * Loads board from a file and parse it to create properties objects to store in board argument given.
     * @param board the variable to store the created board in.
     * @return true with board loaded successfully, else false
     */
    public boolean loadBoardFromMapFile(Board board) {
        if (true) {
            try {
                File file = new File("src/board.xml");
                //an instance of factory that gives a document builder
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                //an instance of builder to parse the specified xml file
                DocumentBuilder db = dbf.newDocumentBuilder();

                InputStream in = getClass().getResourceAsStream("board.xml");

                Document doc = db.parse(file);

                doc.getDocumentElement().normalize();

                NodeList propertyList = doc.getElementsByTagName("Property");
                NodeList TaxList = doc.getElementsByTagName("Tax");
                NodeList utilitiesList = doc.getElementsByTagName("Utilites");
                NodeList railroadList = doc.getElementsByTagName("Railroad");
                NodeList goList = doc.getElementsByTagName("GO");
                NodeList jailList = doc.getElementsByTagName("Jail");
                NodeList goToJailList = doc.getElementsByTagName("GoToJail");

                for(int i = 0; i < 40; i++){
                    Properties property = new Properties("None",0,0,Color.white,i);
                    board.getPropertiesArrayList().add(property);
                }

                //iterates through Properties
                for (int itr = 0; itr < propertyList.getLength(); itr++) {
                    Node propertyNode = propertyList.item(itr);
                    if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element propertyElement = (Element) propertyNode;
                        Properties newProperties = new Properties(propertyElement.getElementsByTagName("name").item(0).getTextContent(),
                                                                  Integer.parseInt(propertyElement.getElementsByTagName("price").item(0).getTextContent()),
                                                                  Integer.parseInt(propertyElement.getElementsByTagName("rent").item(0).getTextContent()),
                                                                  new Color( Integer.parseInt(propertyElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                                                             Integer.parseInt(propertyElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                                                             Integer.parseInt(propertyElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                                                  Integer.parseInt(propertyElement.getElementsByTagName("index").item(0).getTextContent())
                                                                  );

                        board.getPropertiesArrayList().set(newProperties.getLocation(),newProperties);
                    }
                }

                for (int itr = 0; itr < TaxList.getLength(); itr++) {
                    Node TaxNode = TaxList.item(itr);
                    if (TaxNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element TaxElement = (Element) TaxNode;
                        Properties newProperties = new Properties(TaxElement.getElementsByTagName("name").item(0).getTextContent(),0,
                                Integer.parseInt(TaxElement.getElementsByTagName("rent").item(0).getTextContent()), // rent/tax amount
                                new Color( Integer.parseInt(TaxElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(TaxElement.getElementsByTagName("g").item(0).getTextContent()),     //G
                                        Integer.parseInt(TaxElement.getElementsByTagName("b").item(0).getTextContent())),    //B
                                Integer.parseInt(TaxElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().set(newProperties.getLocation(),newProperties);
                    }
                }

                for (int itr = 0; itr < railroadList.getLength(); itr++) {
                    Node railroadNode = railroadList.item(itr);
                    if (railroadNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element railroadElement = (Element) railroadNode;
                        Properties newProperties = new Properties(railroadElement.getElementsByTagName("name").item(0).getTextContent(),
                                Integer.parseInt(railroadElement.getElementsByTagName("price").item(0).getTextContent()),
                                Integer.parseInt(railroadElement.getElementsByTagName("rent").item(0).getTextContent()),
                                new Color( Integer.parseInt(railroadElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(railroadElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                        Integer.parseInt(railroadElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                Integer.parseInt(railroadElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().set(newProperties.getLocation(),newProperties);
                    }
                }

                for (int itr = 0; itr < utilitiesList.getLength(); itr++) {
                    Node utilitiesNode = utilitiesList.item(itr);
                    if (utilitiesNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element utilitiesElement = (Element) utilitiesNode;
                        Properties newProperties = new Properties(utilitiesElement.getElementsByTagName("name").item(0).getTextContent(),
                                Integer.parseInt(utilitiesElement.getElementsByTagName("price").item(0).getTextContent()),
                                Integer.parseInt(utilitiesElement.getElementsByTagName("rent").item(0).getTextContent()),
                                new Color( Integer.parseInt(utilitiesElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(utilitiesElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                        Integer.parseInt(utilitiesElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                Integer.parseInt(utilitiesElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().set(newProperties.getLocation(),newProperties);
                    }
                }

                for (int itr = 0; itr < goList.getLength(); itr++) {
                    Node goNode = goList.item(itr);
                    if (goNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element goElement = (Element) goNode;
                        Properties newProperties = new Properties(goElement.getElementsByTagName("name").item(0).getTextContent(),
                                new Color( Integer.parseInt(goElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(goElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                        Integer.parseInt(goElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                Integer.parseInt(goElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().set(newProperties.getLocation(),newProperties);
                    }
                }

                for (int itr = 0; itr < jailList.getLength(); itr++) {
                    Node jailNode = jailList.item(itr);
                    if (jailNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element jailElement = (Element) jailNode;
                        Properties newProperties = new Properties(jailElement.getElementsByTagName("name").item(0).getTextContent(),
                                new Color( Integer.parseInt(jailElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(jailElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                        Integer.parseInt(jailElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                Integer.parseInt(jailElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().set(newProperties.getLocation(),newProperties);
                    }
                }

                for (int itr = 0; itr < goToJailList.getLength(); itr++) {
                    Node goToJailNode = goToJailList.item(itr);
                    if (goToJailNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element goToJailElement = (Element) goToJailNode;
                        Properties newProperties = new Properties(goToJailElement.getElementsByTagName("name").item(0).getTextContent(),
                                new Color( Integer.parseInt(goToJailElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(goToJailElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                        Integer.parseInt(goToJailElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                Integer.parseInt(goToJailElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.getPropertiesArrayList().set(newProperties.getLocation(),newProperties);
                    }
                }

                // Color List made and Properties grouped up by color into the Hash Map colorPropertiesArrayList
                ArrayList<Color> colorsList = new ArrayList<>();
                colorsList.add(new Color(255,255,255));colorsList.add(new Color(136,69,19));
                colorsList.add(new Color(135,206,250));colorsList.add(new Color(250,140,0));
                colorsList.add(new Color(255,105,180));colorsList.add(new Color(255,140,0));
                colorsList.add(new Color(255,0,0));colorsList.add(new Color(255,255,0));
                colorsList.add(new Color(0,128,0));colorsList.add(new Color(0,0,128));
                for(int i =0; i < colorsList.size(); i++){
                    ArrayList<Properties> tempPropertyList = new ArrayList<>();
                    for(int j = 0; j < board.getPropertiesArrayList().size(); j++){
                        if(colorsList.get(i).equals(board.getProperty(j).getColor())){
                            tempPropertyList.add(board.getProperty(j));
                        }
                    }
                    board.addColorPropertySet(colorsList.get(i),tempPropertyList);
                }

                return true;
            } catch (Exception e) {

            }
        }
        return false;
    }
}
