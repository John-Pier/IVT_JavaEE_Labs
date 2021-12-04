import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Main {

    private static DocumentBuilder builder;
    private static Transformer transformer;

    static {
        try {
            builder  = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (builder == null || transformer == null) {
            System.err.println("Error, some instances is null!");
            return;
        }
        try {
            var document = builder.parse(args[0]);
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, document.getDoctype().getSystemId());

            var students = document.getElementsByTagName("student");
            var studentSubjects = ((Element)students.item(0)).getElementsByTagName("subject");
            var studentAverageNode = ((Element)students.item(0)).getElementsByTagName("average").item(0);

            var correctAverage = getCorrectAverage(studentSubjects);
            var currentAverage = Double.parseDouble(studentAverageNode.getTextContent());

            if(correctAverage != currentAverage) {
                studentAverageNode.setTextContent(String.valueOf(correctAverage));
            }

            System.out.println("Correct Average: " + correctAverage + "\nCurrent Average: " + currentAverage);

            createDocumentWithAverage(students, args[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double getCorrectAverage(NodeList nodeList) {
        double markSum = 0;
        for (int i = 0; i < nodeList.getLength(); i++) {
            markSum += Integer.parseInt(((Element)nodeList.item(i)).getAttribute("mark"));
        }
        return markSum / nodeList.getLength();
    }

    private static void createDocumentWithAverage(NodeList nodeList, String fileNamePath) throws TransformerException {
        if (builder == null || transformer == null) {
            System.err.println("Error, some instances is null!");
            return;
        }

        var correctDocument = builder.newDocument();
        var source = new DOMSource(correctDocument);
        var streamResult = new StreamResult(fileNamePath);

        for(int i = 0; i < nodeList.getLength(); i++) {
            correctDocument.appendChild(correctDocument.importNode(nodeList.item(i), true));
        }

        transformer.transform(source, streamResult);
    }
}
