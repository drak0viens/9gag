/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg9gag;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Drak
 */
public class PageParser {
    
    public static Map getImages() throws ParserConfigurationException, IOException, SAXException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("http://9gag.com");
        
        NodeList imgList = doc.getElementsByTagName("img-wrap");        
        
        for (int i = 0; i < imgList.getLength(); i++) {
            System.out.println(imgList.item(i).getFirstChild().getFirstChild().getTextContent());
        } 
        
        return null;
    }
}
