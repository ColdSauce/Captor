package com.alchemyapi.test;

import com.alchemyapi.api.AlchemyAPI;

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

class TextExtractTest {
    public static void main(String[] args)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        // Create an AlchemyAPI object.
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");

        // Extract page text from a web URL. (ignoring ads, navigation links,
        // and other content).
        Document doc = alchemyObj.URLGetText("http://www.techcrunch.com/");
        System.out.println(getStringFromDocument(doc));

        // Extract raw page text from a web URL. (including ads, navigation
        // links, and other content).
        doc = alchemyObj.URLGetRawText("http://www.techcrunch.com/");
        System.out.println(getStringFromDocument(doc));

        // Extract a title from a web URL.
        doc = alchemyObj.URLGetTitle("http://www.techcrunch.com/");
        System.out.println(getStringFromDocument(doc));

        // Load a HTML document to analyze.
        String htmlDoc = getFileContents("data/example.html");

        // Extract page text from a HTML document. (ignoring ads, navigation
        // links, and other content).
        doc = alchemyObj.HTMLGetText(htmlDoc, "http://www.test.com/");
        System.out.println(getStringFromDocument(doc));

        // Extract raw page text from a HTML document. (including ads,
        // navigation links, and other content).
        doc = alchemyObj.HTMLGetRawText(htmlDoc, "http://www.test.com/");
        System.out.println(getStringFromDocument(doc));

        // Extract a title from a HTML document.
        doc = alchemyObj.HTMLGetTitle(htmlDoc, "http://www.test.com/");
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
