
package infolis.metaDataTransformer.output;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Domi
 */
public class OutputWriter {

    String lang = null, abstractDesc = null, title = null, recordIdentifier = null, uri = null;
    List<String> authors = new ArrayList<>(), subjects = new ArrayList<>(), datasetIdentifiers = new ArrayList<>();
    String outputPath = null, fileName = null;
    
    public OutputWriter(String lang, String abstractDesc, String title, String recordIdentifier, String uri, List<String> authors, List<String> subjects, String outputPath, String fileName) {
        this.lang = lang;
        this.abstractDesc = abstractDesc;
        this.title = title;
        this.recordIdentifier = recordIdentifier;
        this.uri = uri;
        this.authors = authors;
        this.subjects = subjects;
        this.outputPath = outputPath;
        this.fileName = fileName;
    }
    
    public OutputWriter(String lang, String abstractDesc, String title, String recordIdentifier, 
    List<String> datasetIdentifiers, List<String> authors, List<String> subjects, String outputPath, String fileName) {
        this.lang = lang;
        this.abstractDesc = abstractDesc;
        this.title = title;
        this.recordIdentifier = recordIdentifier;
        this.datasetIdentifiers = datasetIdentifiers;
        this.authors = authors;
        this.subjects = subjects;
        this.outputPath = outputPath;
        this.fileName = fileName;
    }

    public void writeOutput() {
        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try {
            icBuilder = icFactory.newDocumentBuilder();
            Document docOut = icBuilder.newDocument();
            Element node = docOut.createElement("record");
            docOut.appendChild(node);

            Element header = docOut.createElement("header");
            node.appendChild(header);

            // append child elements to root element
            if (null != recordIdentifier) {
                Element identifierEle = docOut.createElement("identifier");
                identifierEle.setTextContent(recordIdentifier);
                header.appendChild(identifierEle);
            }

            Element metadata = docOut.createElement("metadata");
            node.appendChild(metadata);

            Element ns1 = docOut.createElement("oai_dc:dc");
            ns1.setAttribute("xmlns:oai_dc", "http://www.openarchives.org/OAI/2.0/oai_dc/");
            ns1.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
            ns1.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            ns1.setAttribute("xsi:schemaLocation", "http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd");
            metadata.appendChild(ns1);

            Element titleEle = docOut.createElement("dc:title");
            titleEle.setTextContent(title);
            ns1.appendChild(titleEle);

            Element langEle = docOut.createElement("dc:language");
            langEle.setTextContent(lang);
            ns1.appendChild(langEle);

            if (null != uri) {
                Element uriEle = docOut.createElement("dc:identifier");
                uriEle.setTextContent(uri);
                ns1.appendChild(uriEle);
            }
            
            for (String id : datasetIdentifiers) {
                Element idEle = docOut.createElement("dc:identifier");
                idEle.setTextContent(id);
                ns1.appendChild(idEle);
            }

            Element absEle = docOut.createElement("dc:description");
            absEle.setTextContent(abstractDesc);
            ns1.appendChild(absEle);

            for (String auth : authors) {
                Element authorEle = docOut.createElement("dc:creator");
                authorEle.setTextContent(auth);
                ns1.appendChild(authorEle);
            }

            for (String subj : subjects) {
                Element subjectEle = docOut.createElement("dc:subject");
                subjectEle.setTextContent(subj);
                ns1.appendChild(subjectEle);
            }

            // output DOM XML to console 
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(docOut);
            StreamResult console = new StreamResult(new File(outputPath + "/" + fileName));
            transformer.transform(source, console);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

