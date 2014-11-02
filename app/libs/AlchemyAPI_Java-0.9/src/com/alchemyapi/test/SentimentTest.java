package com.alchemyapi.test;

import com.alchemyapi.api.AlchemyAPI;
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

class SentimentTest {
    public static void main(String[] args) throws IOException, SAXException,
            ParserConfigurationException, XPathExpressionException {
        // Create an AlchemyAPI object.
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");

        // Extract sentiment for a web URL.
        Document doc = alchemyObj.URLGetTextSentiment("http://www.techcrunch.com/");
        System.out.println(getStringFromDocument(doc));

        // Extract sentiment for a text string.
        doc = alchemyObj.TextGetTextSentiment(
            "That hat is ridiculous, Charles.");
        System.out.println(getStringFromDocument(doc));

        // Load a HTML document to analyze.
        String htmlDoc = getFileContents("data/example.html");

        // Extract sentiment for a HTML document.
        doc = alchemyObj.HTMLGetTextSentiment(htmlDoc, "http://www.test.com/");
	System.out.println(getStringFromDocument(doc));
	
	// Extract entity-targeted sentiment from a HTML document.	
	AlchemyAPI_NamedEntityParams entityParams = new AlchemyAPI_NamedEntityParams();
	entityParams.setSentiment(true);
	doc = alchemyObj.TextGetRankedNamedEntities("That Mike Tyson is such a sweetheart.", entityParams);
	System.out.println(getStringFromDocument(doc));
	
	// Extract keyword-targeted sentiment from a HTML document.	
	AlchemyAPI_KeywordParams keywordParams = new AlchemyAPI_KeywordParams();
	keywordParams.setSentiment(true);
	doc = alchemyObj.TextGetRankedKeywords("That Mike Tyson is such a sweetheart.", keywordParams);
	System.out.println(getStringFromDocument(doc));
        
	//Extract Targeted Sentiment from text
	AlchemyAPI_TargetedSentimentParams sentimentParams = new AlchemyAPI_TargetedSentimentParams();
	sentimentParams.setShowSourceText(true);
	doc = alchemyObj.TextGetTargetedSentiment("This car is terrible.", "car", sentimentParams);
	System.out.print(getStringFromDocument(doc));

	//Extract Targeted Sentiment from url
	doc = alchemyObj.URLGetTargetedSentiment("http://techcrunch.com/2012/03/01/keen-on-anand-rajaraman-how-walmart-wants-to-leapfrog-over-amazon-tctv/", "Walmart",sentimentParams);
	System.out.print(getStringFromDocument(doc));

	//Extract Targeted Sentiment from html
	doc = alchemyObj.HTMLGetTargetedSentiment(htmlDoc, "http://www.test.com/", "WujWuj", sentimentParams);
	System.out.print(getStringFromDocument(doc));
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
