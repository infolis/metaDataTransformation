package infolis.metaDataTransformer.springer;

import infolis.metaDataTransformer.output.OutputWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author domi
 */
public class SpringerTransformer {

    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {

        String inputPath = args[0];
        String outputPath = args[1];
        String textOutput = args[2];
//        String inputPath = "E:\\out\\out";
//        String outputPath = "C:\\Users\\domi\\InFoLiS2\\dataFormats\\springerOutNew2";
//        String textOutput = "C:\\Users\\domi\\InFoLiS2\\dataFormats\\subset";

        File inDir = new File(inputPath);
        for (File topic : inDir.listFiles()) {
            System.out.println(topic.getName());
            File folder = new File(textOutput+"/"+topic.getName());
            folder.mkdir();
            for (File journal : topic.listFiles()) {
                for (File f : journal.listFiles()) {
                    

                    String lang = null, abstractDesc = null, title = null, identifier = null, uri = null, text = null;
                    List<String> authors = new ArrayList<>(), subjects = new ArrayList<>();

                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc;
                    try {
                    doc = db.parse(f);
                    } catch(Exception e) {
                        System.out.println("not parseable " + f.getName());
                        continue;
                    }

                    NodeList fieldNodesHeader = doc.getElementsByTagName("ns0:DOI");
                    for (int j = 0; j < fieldNodesHeader.getLength(); j++) {
                        Node fieldNode = fieldNodesHeader.item(j);
                        identifier = fieldNode.getTextContent();
                        uri = identifier;
                    }

//            NodeList fieldNodesAuthor = doc.getElementsByTagName("ns0:CompleteName");
                    NodeList fieldNodesAuthor = doc.getElementsByTagName("ns0:Author");
                    for (int j = 0; j < fieldNodesAuthor.getLength(); j++) {
                        Node fieldNode = fieldNodesAuthor.item(j);
                        authors.add(fieldNode.getTextContent());
                    }

                    NodeList fieldNodesTitle = doc.getElementsByTagName("ns0:Title");
                    for (int j = 0; j < fieldNodesTitle.getLength(); j++) {
                        Node fieldNode = fieldNodesTitle.item(j);
                        title = fieldNode.getTextContent();
                    }

                    NodeList fieldNodesSubject = doc.getElementsByTagName("ns0:Subject");
                    for (int j = 0; j < fieldNodesSubject.getLength(); j++) {
                        Node fieldNode = fieldNodesSubject.item(j);
                        subjects.add(fieldNode.getTextContent());
                    }

                    NodeList fieldNodesBegin = doc.getElementsByTagName("ArticleInfo");
                    for (int j = 0; j < fieldNodesBegin.getLength(); j++) {
                        Node fieldNode = fieldNodesBegin.item(j);
                        NamedNodeMap attributes = fieldNode.getAttributes();
                        Node attr = attributes.getNamedItem("Language");
                        if (attr != null) {
                            lang = attr.getTextContent().toLowerCase();
                        }
                    }

                    NodeList fieldNodesAbstract = doc.getElementsByTagName("Abstract");
                    for (int j = 0; j < fieldNodesAbstract.getLength(); j++) {
                        Node fieldNode = fieldNodesAbstract.item(j);
                        abstractDesc = fieldNode.getTextContent();
                        abstractDesc = abstractDesc.trim();
                        abstractDesc = abstractDesc.replaceAll("\\s+", " ");
                    }

                    try {
                        NodeList articleContent = doc.getElementsByTagName("Body");
                        text = articleContent.item(0).getTextContent();
                        text = text.trim();
                        text = text.replaceAll("\\s+", " ");
                    } catch (Exception e) {
                        System.out.println("no text: " + f.getAbsolutePath());
                        continue;
                    }

                    String fileName= topic.getName() + "_" +journal.getName()+ "_" + f.getName();
                    File metaFile = new File(outputPath+"/"+fileName);
                    
                    if(metaFile.exists()) {
                        continue;
                    }
                    try {
                    OutputWriter writer = new OutputWriter(lang, abstractDesc, title, identifier, uri, authors, subjects, outputPath, fileName);
                    writer.writeOutput();

                   // File textFile = new File(textOutput + "/" +fileName.replace(".xml", ".txt"));
                    File textFile = new File(folder + "/" +fileName.replace(".xml", ".txt"));
                    BufferedWriter writeText = new BufferedWriter(new FileWriter(textFile));
                    writeText.write(text);
                    writeText.flush();
                    } catch(Exception e) {
                    System.out.println("not created: " + f.getAbsolutePath());
                }
                }
            }
        }
    }
}
