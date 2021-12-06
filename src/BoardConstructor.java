import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Class BoardConstructor, board variable is the where the Property created will be stored in.
 */
public class BoardConstructor {

    private Board board;
    private BoardModel boardModel;

    public BoardConstructor(Board board){
        this.board = board;
    }
    /**
     *
     * @param xsdPath
     * @param xmlPath
     * @return
     */
    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {

        try {

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
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
     * Loads board from a file and parse it to create Property objects to store in board argument given.
     * @return true with board loaded successfully, else false
     */
    public Board loadBoardFromMapFile(String fileName, Boolean newBoard) {
            try {
                File file;
                if(newBoard) {
                    file = new File("src/"+fileName);
                }
                else{
                    file = new File("Save Files/" +fileName);
                }
                //an instance of factory that gives a document builder
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                //an instance of builder to parse the specified xml file
                DocumentBuilder db = dbf.newDocumentBuilder();


                InputStream in = getClass().getResourceAsStream("src/board.xml");

                Document doc = db.parse(file);

                doc.getDocumentElement().normalize();

                NodeList propertyList = doc.getElementsByTagName("Property");
                NodeList TaxList = doc.getElementsByTagName("Tax");
                NodeList utilitiesList = doc.getElementsByTagName("Utilites");
                NodeList railroadList = doc.getElementsByTagName("Railroad");
                NodeList goList = doc.getElementsByTagName("GO");
                NodeList jailList = doc.getElementsByTagName("Jail");
                NodeList goToJailList = doc.getElementsByTagName("GoToJail");
                NodeList currencyList = doc.getElementsByTagName("Currency");


                for(int i = 0; i < 40; i++){
                    Property property = new Property("None",0,0,Color.white,i);
                    board.addProperty(property);
                }
                //Gets currency of Game
                for (int itr = 0; itr < currencyList.getLength(); itr++) {
                    if(currencyList.item(itr).getNodeType() == Node.ELEMENT_NODE){
                        board.setCurrency(currencyList.item(itr).getTextContent());
                    }
                }

                //iterates through Property
                for (int itr = 0; itr < propertyList.getLength(); itr++) {
                    Node propertyNode = propertyList.item(itr);
                    if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element propertyElement = (Element) propertyNode;
                        Property newProperty = new Property(propertyElement.getElementsByTagName("name").item(0).getTextContent(),
                                                                  Integer.parseInt(propertyElement.getElementsByTagName("price").item(0).getTextContent()),
                                                                  Integer.parseInt(propertyElement.getElementsByTagName("rent").item(0).getTextContent()),
                                                                  new Color( Integer.parseInt(propertyElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                                                             Integer.parseInt(propertyElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                                                             Integer.parseInt(propertyElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                                                  Integer.parseInt(propertyElement.getElementsByTagName("index").item(0).getTextContent())
                                                                  );
                        if(!newBoard){
                            newProperty.setNumHouses(Integer.parseInt(propertyElement.getElementsByTagName("numHouses").item(0).getTextContent()));
                            newProperty.setNumHotels(Integer.parseInt(propertyElement.getElementsByTagName("numHotels").item(0).getTextContent()));
                        }
                        board.setProperty(newProperty.getLocation(), newProperty);
                    }
                }

                for (int itr = 0; itr < TaxList.getLength(); itr++) {
                    Node TaxNode = TaxList.item(itr);
                    if (TaxNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element TaxElement = (Element) TaxNode;
                        Property newProperty = new Property(TaxElement.getElementsByTagName("name").item(0).getTextContent(),0,
                                Integer.parseInt(TaxElement.getElementsByTagName("rent").item(0).getTextContent()), // rent/tax amount
                                new Color( Integer.parseInt(TaxElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(TaxElement.getElementsByTagName("g").item(0).getTextContent()),     //G
                                        Integer.parseInt(TaxElement.getElementsByTagName("b").item(0).getTextContent())),    //B
                                Integer.parseInt(TaxElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.setProperty(newProperty.getLocation(), newProperty);
                    }
                }

                for (int itr = 0; itr < railroadList.getLength(); itr++) {
                    Node railroadNode = railroadList.item(itr);
                    if (railroadNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element railroadElement = (Element) railroadNode;
                        Railroads newRailroad = new Railroads(railroadElement.getElementsByTagName("name").item(0).getTextContent(),
                                Integer.parseInt(railroadElement.getElementsByTagName("price").item(0).getTextContent()),
                                Integer.parseInt(railroadElement.getElementsByTagName("rent").item(0).getTextContent()),
                                new Color( Integer.parseInt(railroadElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(railroadElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                        Integer.parseInt(railroadElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                Integer.parseInt(railroadElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.setProperty(newRailroad.getLocation(), newRailroad);
                    }
                }

                for (int itr = 0; itr < utilitiesList.getLength(); itr++) {
                    Node utilitiesNode = utilitiesList.item(itr);
                    if (utilitiesNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element utilitiesElement = (Element) utilitiesNode;
                        Property newProperty = new Property(utilitiesElement.getElementsByTagName("name").item(0).getTextContent(),
                                Integer.parseInt(utilitiesElement.getElementsByTagName("price").item(0).getTextContent()),
                                Integer.parseInt(utilitiesElement.getElementsByTagName("rent").item(0).getTextContent()),
                                new Color( Integer.parseInt(utilitiesElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(utilitiesElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                        Integer.parseInt(utilitiesElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                Integer.parseInt(utilitiesElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.setProperty(newProperty.getLocation(), newProperty);
                    }
                }

                for (int itr = 0; itr < goList.getLength(); itr++) {
                    Node goNode = goList.item(itr);
                    if (goNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element goElement = (Element) goNode;
                        Property newProperty = new Property(goElement.getElementsByTagName("name").item(0).getTextContent(),
                                new Color( Integer.parseInt(goElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(goElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                        Integer.parseInt(goElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                Integer.parseInt(goElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.setProperty(newProperty.getLocation(), newProperty);
                    }
                }

                for (int itr = 0; itr < jailList.getLength(); itr++) {
                    Node jailNode = jailList.item(itr);
                    if (jailNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element jailElement = (Element) jailNode;
                        Property newProperty = new Property(jailElement.getElementsByTagName("name").item(0).getTextContent(),
                                new Color( Integer.parseInt(jailElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(jailElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                        Integer.parseInt(jailElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                Integer.parseInt(jailElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.setProperty(newProperty.getLocation(), newProperty);
                    }
                }

                for (int itr = 0; itr < goToJailList.getLength(); itr++) {
                    Node goToJailNode = goToJailList.item(itr);
                    if (goToJailNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element goToJailElement = (Element) goToJailNode;
                        Property newProperty = new Property(goToJailElement.getElementsByTagName("name").item(0).getTextContent(),
                                new Color( Integer.parseInt(goToJailElement.getElementsByTagName("r").item(0).getTextContent()),  //R
                                        Integer.parseInt(goToJailElement.getElementsByTagName("g").item(0).getTextContent()),  //G
                                        Integer.parseInt(goToJailElement.getElementsByTagName("b").item(0).getTextContent())), //B
                                Integer.parseInt(goToJailElement.getElementsByTagName("index").item(0).getTextContent())
                        );
                        board.setProperty(newProperty.getLocation(), newProperty);
                    }
                }

                // Color List made and Property grouped up by color into the Hash Map colorPropertyArrayList
                ArrayList<Color> colorsList = new ArrayList<>();
                colorsList.add(new Color(255,255,255));
                colorsList.add(new Color(136,69,19));
                colorsList.add(new Color(135,206,250));
                colorsList.add(new Color(250,140,0));
                colorsList.add(new Color(255,105,180));
                colorsList.add(new Color(255,140,0));
                colorsList.add(new Color(255,0,0));
                colorsList.add(new Color(255,255,0));
                colorsList.add(new Color(0,128,0));
                colorsList.add(new Color(0,0,200));
                colorsList.add(new Color (102,98,95));
                board.setAllColorsList(colorsList);


                int[] housePricesList = new int[]{0, 50, 50, 0, 100, 100, 150, 150, 200, 200,0};


                for(int i =0; i < colorsList.size(); i++){
                    ArrayList<Property> tempPropertyList = new ArrayList<>();
                    for(int j = 0; j < board.getPropertyArrayList().size(); j++){
                        if(colorsList.get(i).equals(board.getProperty(j).getColor())){
                            tempPropertyList.add(board.getProperty(j));
                            board.getProperty(j).setHousePrice(housePricesList[i]);
                        }
                    }
                    board.addColorPropertySet(colorsList.get(i),tempPropertyList);
                }

            } catch (FileNotFoundException | ParserConfigurationException f) {
                f.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                return board;
            }
    }

}
