import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Main {

    private static DocumentBuilder builder;
    private static Transformer transformer;

    static {
        var factory = DocumentBuilderFactory.newInstance();
        var transformerFactory =  TransformerFactory.newInstance();
        try {
            builder  = factory.newDocumentBuilder();
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if(builder == null) {
            return;
        }
        try {
            var document = builder.parse(args[0]);

            var students = document.getElementsByTagName("student");
            var studentSubjects = ((Element)students.item(0)).getElementsByTagName("subject");
            var studentAverageNode = ((Element)students.item(0)).getElementsByTagName("average").item(0);

            var correctAverage = getCorrectAverage(studentSubjects);
            var currentAverage = Double.parseDouble(studentAverageNode.getTextContent());

            if(correctAverage != currentAverage) {
                studentAverageNode.setTextContent(String.valueOf(correctAverage));
            }

            createDocumentWithAverage(students, args[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double getCorrectAverage(NodeList nodeList) {
        double markSum = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            markSum += Integer.parseInt(((Element)nodeList.item(i)).getAttribute("mark")); // todo: checks
        }
        return markSum / nodeList.getLength();
    }

    private static void createDocumentWithAverage(NodeList nodeList, String fileNamePath) throws IOException, TransformerException {
        if (builder == null || transformer == null) {
            return;
        }

        var correctDocument = builder.newDocument();
        var source = new DOMSource();
        var streamResult = new StreamResult(new FileWriter(fileNamePath)); // todo: ref

        for(int i = 0; i < nodeList.getLength(); i++) {
            correctDocument.importNode(nodeList.item(i), true);
        }

        transformer.transform(source, streamResult);
    }
}
