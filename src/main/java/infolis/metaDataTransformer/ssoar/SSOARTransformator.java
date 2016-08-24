
package infolis.metaDataTransformer.ssoar;


import infolis.metaDataTransformer.output.OutputWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Domi
 */
public class SSOARTransformator {

    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {

        String inputPath = args[0];
        String outputPath = args[1];

        File inDir = new File(inputPath);
        for (File f : inDir.listFiles()) {

            String lang = null, abstractDesc = null, title = null, recordIdentifier = null;
            List<String> authors = new ArrayList<>(), subjects = new ArrayList<>(), 
            datasetIdentifiers = new ArrayList<>();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(f);

            NodeList fieldNodesHeader = doc.getElementsByTagName("ns0:identifier");
            for (int j = 0; j < fieldNodesHeader.getLength(); j++) {
                Node fieldNode = fieldNodesHeader.item(j);
                recordIdentifier = fieldNode.getTextContent();
            }

            NodeList fieldNodesBegin = doc.getElementsByTagName("ns1:dcvalue");
            for (int j = 0; j < fieldNodesBegin.getLength(); j++) {
                Node fieldNode = fieldNodesBegin.item(j);
                NamedNodeMap attributes = fieldNode.getAttributes();
                Node attr = attributes.getNamedItem("element");
                if (attr != null) {
                    if (attr.getTextContent().equals("language")) {
                        lang = fieldNode.getTextContent();
                    }
                }
            }

            NodeList fieldNodes = doc.getElementsByTagName("ns1:dcvalue");

            for (int j = 0; j < fieldNodes.getLength(); j++) {
                Node fieldNode = fieldNodes.item(j);
                NamedNodeMap attributes = fieldNode.getAttributes();
                Node element = attributes.getNamedItem("element");
                Node language = attributes.getNamedItem("language");
                Node qualifier = attributes.getNamedItem("qualifier");

                if (element != null) {
                    if (element.getTextContent().equals("description")
                            && qualifier != null && qualifier.getTextContent().equals("abstract") && language != null && language.getTextContent().equals(lang)
                            && abstractDesc==null) {
                        abstractDesc = fieldNode.getTextContent();
                    }
                    if (element.getTextContent().equals("contributor") && qualifier != null && qualifier.getTextContent().equals("author")) {
                        authors.add(fieldNode.getTextContent());
                    }
                    if (element.getTextContent().equals("subject") && language != null && language.getTextContent().equals(lang)) {
                        subjects.add(fieldNode.getTextContent());
                    }
                    if (element.getTextContent().equals("title") && language != null && language.getTextContent().equals(lang) && qualifier ==null) {
                        title = fieldNode.getTextContent();
                    }
                    if (element.getTextContent().equals("identifier")) {
                        datasetIdentifiers.add(fieldNode.getTextContent());
                    }
                }
            }
           
            OutputWriter writer = new OutputWriter(lang, abstractDesc, title, recordIdentifier, datasetIdentifiers, authors, subjects, outputPath, f.getName());
            writer.writeOutput();
            
        }
    }
}

