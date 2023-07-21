package hu.unideb.inf.data;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * The XMLFileHandling class provides methods for handling XML files and saving winner names.
 */
public class XMLFileHandling {
    /**
     * Saves the name of a winner to an XML file.
     * @param winnerName The name of the winner to be saved.
     * @throws IOException if an I/O error occurs while accessing the file.
     * @throws JDOMException if an error occurs while processing the XML file.
     */
    public static void saveWinnerName(String winnerName) throws IOException, JDOMException {
        String filePath = "/winners.xml";

        InputStream inputStream = XMLFileHandling.class.getResourceAsStream(filePath);

        boolean fileExists = inputStream != null;

        Document document;
        Element rootElement;

        if (fileExists) {
            SAXBuilder saxBuilder = new SAXBuilder();
            document = saxBuilder.build(inputStream);
            rootElement = document.getRootElement();
        } else {
            document = new Document();
            rootElement = new Element("winners");
            document.setRootElement(rootElement);
        }

        Element winnerElement = new Element("winner");
        winnerElement.setText(winnerName);

        rootElement.addContent(winnerElement);

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        try (FileWriter fileWriter = new FileWriter(XMLFileHandling.class.getResource(filePath).getFile())) {
            outputter.output(document, fileWriter);
        }
    }
}
