package ies.pedro.utils;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;



public class XMLtoHTML {

    public static void leveltoHTML(File levelXML) {
        String parent = levelXML.getParent();
        String name = levelXML.getName().replace(".xml", "");
        String dir = parent + "/htmls/" + name + ".html";
        File html = new File(dir);

        File xslF = new File(parent + "/transformer.xsl");
        StreamSource xls = new StreamSource(xslF);
        try {
        StreamSource xml = new StreamSource(levelXML);
        TransformerFactory tf = TransformerFactory.newInstance("org.apache.xalan.processor.TransformerFactoryImpl", null);

        StreamResult result = new StreamResult(html);
        Transformer transformer = tf.newTransformer(xls);
        transformer.transform(xml, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void levelstoHTML(File levelsXML) {
        String parent = levelsXML.getParent();
        String name = levelsXML.getName().replace(".xml", "");
        String dir = parent + "/htmls/" + name + ".html";
        File html = new File(dir);

        File xslF = new File(parent + "/transformerLevels.xsl");
        StreamSource xls = new StreamSource(xslF);

        try {
            StreamSource xml = new StreamSource(levelsXML);
            TransformerFactory tf = TransformerFactory.newInstance("org.apache.xalan.processor.TransformerFactoryImpl", null);

            StreamResult result = new StreamResult(html);
            Transformer transformer = tf.newTransformer(xls);
            transformer.transform(xml, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
}
