package ctf.worldcorp.collaborators;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ParseCollaboratorXml {
    private final String collaboratorXmlPath;

    public ParseCollaboratorXml(String collaboratorXmlPath) {
        this.collaboratorXmlPath = collaboratorXmlPath;
    }

    public Collaborator parseContent() {
        try {
            SAXBuilder sax = new SAXBuilder();
            Document doc = sax.build(new File(collaboratorXmlPath));
            System.out.println(this.collaboratorXmlPath);
            Element rootNode = doc.getRootElement();

            return new Collaborator(
                    rootNode.getChildText("name"),
                    rootNode.getChildText("age"),
                    rootNode.getChildText("role")
            );

        } catch (JDOMException | IOException e) {
            return Collaborator.empty();
        }
    }

    public void updateCollaboratorFile() {
        String currentDate = recoverCurrentDate();
        try {
            SAXBuilder sax = new SAXBuilder();
            Document doc = sax.build(new File(collaboratorXmlPath));
            System.out.println(this.collaboratorXmlPath);
            Element rootNode = doc.getRootElement();

            rootNode.getChild("last_html_generation").setText(currentDate);

            XMLOutputter xmloutput = new XMLOutputter();
            xmloutput.setFormat(Format.getPrettyFormat());

            FileOutputStream output = new FileOutputStream(this.collaboratorXmlPath);
            xmloutput.output(doc, output);

        } catch (JDOMException | IOException e) {
            return;
        }
    }

    private String recoverCurrentDate() {
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");
        return currentDate.format(formatter);
    }
}
