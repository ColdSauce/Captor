package com.alchemyapi.test;

import com.alchemyapi.api.*;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

class RelationsTest {
    public static void main(String[] args)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        // Create an AlchemyAPI object.
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");

        // Extract a ranked list of relations for a web URL.
        Document doc = alchemyObj.URLGetRelations("http://www.techcrunch.com/");
        System.out.println(getStringFromDocument(doc));

        // Extract a ranked list of relations from a text string.
        doc = alchemyObj.TextGetRelations(
            "Hello there, my name is Bob Jones.  I live in the United States of America.  " +
            "Where do you live, Fred?");
        System.out.println(getStringFromDocument(doc));

        // Load a HTML document to analyze.
        String htmlDoc = getFileContents("data/example.html");

        // Extract a ranked list of relations from a HTML document.
        doc = alchemyObj.HTMLGetRelations(htmlDoc, "http://www.test.com/");
        System.out.println(getStringFromDocument(doc));
		
		AlchemyAPI_RelationParams relationParams = new AlchemyAPI_RelationParams();
		relationParams.setSentiment(true);
		relationParams.setEntities(true);
		relationParams.setDisambiguate(true);
		relationParams.setSentimentExcludeEntities(true);
		doc = alchemyObj.TextGetRelations("Madonna enjoys tasty Pepsi.  I love her style.", relationParams);
        System.out.println(getStringFromDocument(doc));
		
		relationParams.setSentiment(true);
		relationParams.setRequireEntities(true);
		relationParams.setSentimentExcludeEntities(true);
		doc = alchemyObj.TextGetRelations("Madonna enjoys tasty Pepsi.  I love her style.", relationParams);
        System.out.println(getStringFromDocument(doc));
    }

    // utility function
    private static String getFileContents(String filename)
        throws IOException, FileNotFoundException
    {
        File file = new File(filename);
        StringBuilder contents = new StringBuilder();

        BufferedReader input = new BufferedReader(new FileReader(file));

        try {
            String line = null;

            while ((line = input.readLine()) != null) {
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        } finally {
            input.close();
        }

        return contents.toString();
    }

    // utility method
    private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
