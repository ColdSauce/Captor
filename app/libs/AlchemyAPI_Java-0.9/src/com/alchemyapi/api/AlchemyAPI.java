package com.alchemyapi.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class AlchemyAPI {

    private String _apiKey;
    private String _requestUri = "http://access.alchemyapi.com/calls/";

    private AlchemyAPI() {
    }


    static public AlchemyAPI GetInstanceFromFile(String keyFilename)
        throws FileNotFoundException, IOException
    {
        AlchemyAPI api = new AlchemyAPI();
        api.LoadAPIKey(keyFilename);

        return api;
    }

    static public AlchemyAPI GetInstanceFromString(String apiKey)
    {
        AlchemyAPI api = new AlchemyAPI();
        api.SetAPIKey(apiKey);

        return api;
    }

    public void LoadAPIKey(String filename) throws IOException, FileNotFoundException
    {
        if (null == filename || 0 == filename.length())
            throw new IllegalArgumentException("Empty API key file specified.");

        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);

        BufferedReader breader = new BufferedReader(new InputStreamReader(fis));

        _apiKey = breader.readLine().replaceAll("\\n", "").replaceAll("\\r", "");

        fis.close();
        breader.close();

        if (null == _apiKey || _apiKey.length() < 5)
            throw new IllegalArgumentException("Too short API key.");
    }

    public void SetAPIKey(String apiKey) {
        _apiKey = apiKey;

        if (null == _apiKey || _apiKey.length() < 5)
            throw new IllegalArgumentException("Too short API key.");
    }

    public void SetAPIHost(String apiHost) {
        if (null == apiHost || apiHost.length() < 2)
            throw new IllegalArgumentException("Too short API host.");

        _requestUri = "http://" + apiHost + ".alchemyapi.com/calls/";
    }

    public Document URLGetAuthor(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetAuthor(url, new AlchemyAPI_Params());
    }

    public Document URLGetAuthor(String url, AlchemyAPI_Params params)
        throws IOException, SAXException, ParserConfigurationException,
               XPathExpressionException
    {
        CheckURL(url);

        params.setUrl(url);

        return GET("URLGetAuthor", "url", params);
    }

    public Document HTMLGetAuthor(String html, String url)
        throws IOException, SAXException, ParserConfigurationException,
               XPathExpressionException
    {
        return HTMLGetAuthor(html, url, new AlchemyAPI_Params());
    }

    public Document HTMLGetAuthor(String html, String url,
			          AlchemyAPI_Params params)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        CheckHTML(html, url);

        params.setHtml(html);
        params.setUrl(url);

        return POST("HTMLGetAuthor", "html", params);
    }

    public Document URLGetRankedNamedEntities(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetRankedNamedEntities(url, new AlchemyAPI_NamedEntityParams());
    }
    
    public Document URLGetRankedNamedEntities(String url, AlchemyAPI_NamedEntityParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
    {
    	CheckURL(url);
    	
    	params.setUrl(url);

    	return GET("URLGetRankedNamedEntities", "url", params);
    }

    public Document HTMLGetRankedNamedEntities(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetRankedNamedEntities(html, url, new AlchemyAPI_NamedEntityParams());
    }
    

    public Document HTMLGetRankedNamedEntities(String html, String url, AlchemyAPI_NamedEntityParams params)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        CheckHTML(html, url);
        
        params.setUrl(url);
        params.setHtml(html);

        return POST("HTMLGetRankedNamedEntities", "html", params);
    }

    public Document TextGetRankedNamedEntities(String text)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return TextGetRankedNamedEntities(text, new AlchemyAPI_NamedEntityParams());
    }
    
    public Document TextGetRankedNamedEntities(String text, AlchemyAPI_NamedEntityParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
    {
    	CheckText(text);
    
    	params.setText(text);

    	return POST("TextGetRankedNamedEntities", "text", params);
    }
    
    

    public Document URLGetRankedConcepts(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetRankedConcepts(url, new AlchemyAPI_ConceptParams());
    }
    
    public Document URLGetRankedConcepts(String url, AlchemyAPI_ConceptParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
    {
    	CheckURL(url);
    
    	params.setUrl(url);

    	return GET("URLGetRankedConcepts", "url", params);
    }    
    

    public Document HTMLGetRankedConcepts(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetRankedConcepts(html, url, new AlchemyAPI_ConceptParams());
    }
    
    public Document HTMLGetRankedConcepts(String html, String url, AlchemyAPI_ConceptParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);

	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetRankedConcepts", "html", params);
	}

    public Document TextGetRankedConcepts(String text) throws IOException, SAXException,
            ParserConfigurationException, XPathExpressionException {
        return TextGetRankedConcepts(text, new AlchemyAPI_ConceptParams());
    }
    
    public Document TextGetRankedConcepts(String text, AlchemyAPI_ConceptParams params) throws IOException, SAXException,
    ParserConfigurationException, XPathExpressionException 
    {
		CheckText(text);
		
		params.setText(text);
		
		return POST("TextGetRankedConcepts", "text", params);
	}
    
    

    public Document URLGetRankedKeywords(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetRankedKeywords(url, new AlchemyAPI_KeywordParams());
    }
    
    public Document URLGetRankedKeywords(String url, AlchemyAPI_KeywordParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
    {
    	CheckURL(url);
    
    	params.setUrl(url);

    	return GET("URLGetRankedKeywords", "url", params);
    }    
    

    public Document HTMLGetRankedKeywords(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetRankedKeywords(html, url, new AlchemyAPI_KeywordParams());
    }
    
    public Document HTMLGetRankedKeywords(String html, String url, AlchemyAPI_KeywordParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);

	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetRankedKeywords", "html", params);
	}

    public Document TextGetRankedKeywords(String text) throws IOException, SAXException,
            ParserConfigurationException, XPathExpressionException {
        return TextGetRankedKeywords(text, new AlchemyAPI_KeywordParams());
    }
    
    public Document TextGetRankedKeywords(String text, AlchemyAPI_KeywordParams params) throws IOException, SAXException,
    ParserConfigurationException, XPathExpressionException 
    {
		CheckText(text);
		
		params.setText(text);
		
		return POST("TextGetRankedKeywords", "text", params);
	}

    public Document URLGetLanguage(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetLanguage(url, new AlchemyAPI_LanguageParams());
    }
    
    public Document URLGetLanguage(String url, AlchemyAPI_LanguageParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);
	    
	    params.setUrl(url);
	
	    return GET("URLGetLanguage", "url", params);
	}

    public Document HTMLGetLanguage(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetLanguage(html, url, new AlchemyAPI_LanguageParams());
    }
    
    public Document HTMLGetLanguage(String html, String url, AlchemyAPI_LanguageParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);
	    
	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetLanguage", "html", params);
	}

    public Document TextGetLanguage(String text)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return TextGetLanguage(text, new AlchemyAPI_LanguageParams());
    }
    
    public Document TextGetLanguage(String text, AlchemyAPI_LanguageParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckText(text);

	    params.setText(text);
	
	    return POST("TextGetLanguage", "text", params);
	}

    public Document URLGetCategory(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetCategory(url, new AlchemyAPI_CategoryParams());
    }
    
    public Document URLGetCategory(String url, AlchemyAPI_CategoryParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);

	    params.setUrl(url);
	
	    return GET("URLGetCategory", "url", params);
	}

    public Document HTMLGetCategory(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetCategory(html, url, new AlchemyAPI_CategoryParams());
    }
    
    public Document HTMLGetCategory(String html, String url, AlchemyAPI_CategoryParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);
	    
	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetCategory", "html", params);
	}

    public Document TextGetCategory(String text)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return TextGetCategory(text, new AlchemyAPI_TextParams());
    }
    
    public Document TextGetCategory(String text, AlchemyAPI_TextParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckText(text);
	    
	    params.setText(text);
	
	    return POST("TextGetCategory", "text", params);
	}

    public Document URLGetText(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetText(url, new AlchemyAPI_TextParams());
    }
    
    public Document URLGetText(String url, AlchemyAPI_TextParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);
	    
	    params.setUrl(url);
	
	    return GET("URLGetText", "url", params);
	}

    public Document HTMLGetText(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetText(html, url, new AlchemyAPI_TextParams());
    }
    
    public Document HTMLGetText(String html, String url, AlchemyAPI_TextParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);
	    
	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetText", "html", params);
	}

    public Document URLGetRawText(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetRawText(url, new AlchemyAPI_Params());
    }
    
    public Document URLGetRawText(String url, AlchemyAPI_Params params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);

	    params.setUrl(url);
	
	    return GET("URLGetRawText", "url", params);
	}

    public Document HTMLGetRawText(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetRawText(html, url, new AlchemyAPI_Params());
    }
    
    public Document HTMLGetRawText(String html, String url, AlchemyAPI_Params params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);

	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetRawText", "html", params);
	}

    public Document URLGetTitle(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetTitle(url, new AlchemyAPI_Params());
    }
    
    public Document URLGetTitle(String url, AlchemyAPI_Params params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);
	    
	    params.setUrl(url);
	
	    return GET("URLGetTitle", "url", params);
	}

    public Document HTMLGetTitle(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetTitle(html, url, new AlchemyAPI_Params());
    }
    
    public Document HTMLGetTitle(String html, String url, AlchemyAPI_Params params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);
	    
	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetTitle", "html", params);
	}

    public Document URLGetFeedLinks(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetFeedLinks(url, new AlchemyAPI_Params());
    }
    
    public Document URLGetFeedLinks(String url, AlchemyAPI_Params params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);
	    
	    params.setUrl(url);
	
	    return GET("URLGetFeedLinks", "url", params);
	}

    public Document HTMLGetFeedLinks(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetFeedLinks(html, url, new AlchemyAPI_Params());
    }
    
    public Document HTMLGetFeedLinks(String html, String url, AlchemyAPI_Params params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);
	    
	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetFeedLinks", "html", params);
	}

    public Document URLGetMicroformats(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetMicroformats(url, new AlchemyAPI_Params());
    }
    
    public Document URLGetMicroformats(String url, AlchemyAPI_Params params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);

	    params.setUrl(url);
	
	    return GET("URLGetMicroformatData", "url", params);
	}

    public Document HTMLGetMicroformats(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetMicroformats(html, url, new AlchemyAPI_Params());
    }
    
    public Document HTMLGetMicroformats(String html, String url, AlchemyAPI_Params params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);
	    
	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetMicroformatData", "html", params);
	}

    public Document URLGetConstraintQuery(String url, String query)
        throws IOException, XPathExpressionException,
               SAXException, ParserConfigurationException
    {
        return URLGetConstraintQuery(url, query, new AlchemyAPI_ConstraintQueryParams());
    }
    
    public Document URLGetConstraintQuery(String url, String query, AlchemyAPI_ConstraintQueryParams params)
    throws IOException, XPathExpressionException,
           SAXException, ParserConfigurationException
	{
	    CheckURL(url);
	    if (null == query || query.length() < 2)
	        throw new IllegalArgumentException("Invalid constraint query specified");
	    
	    params.setUrl(url);
	    params.setCQuery(query);
	
	    return POST("URLGetConstraintQuery", "url", params);
	}


    public Document HTMLGetConstraintQuery(String html, String url, String query)
        throws IOException, XPathExpressionException,
               SAXException, ParserConfigurationException
    {
        return HTMLGetConstraintQuery(html, url, query, new AlchemyAPI_ConstraintQueryParams());
    }
    
    public Document HTMLGetConstraintQuery(String html, String url, String query, AlchemyAPI_ConstraintQueryParams params)
    throws IOException, XPathExpressionException,
           SAXException, ParserConfigurationException
	{
	    CheckHTML(html, url);
	    if (null == query || query.length() < 2)
	        throw new IllegalArgumentException("Invalid constraint query specified");

	    params.setUrl(url);
	    params.setHtml(html);
	    params.setCQuery(query);
	
	    return POST("HTMLGetConstraintQuery", "html", params);
	}
	
	public Document URLGetTextSentiment(String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetTextSentiment(url, new AlchemyAPI_Params());
    }
    
    public Document URLGetTextSentiment(String url, AlchemyAPI_Params params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
    {
    	CheckURL(url);
    
    	params.setUrl(url);

    	return GET("URLGetTextSentiment", "url", params);
    }    
    

    public Document HTMLGetTextSentiment(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetTextSentiment(html, url, new AlchemyAPI_Params());
    }
    
    public Document HTMLGetTextSentiment(String html, String url, AlchemyAPI_Params params)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        CheckHTML(html, url);

        params.setUrl(url);
        params.setHtml(html);
	
        return POST("HTMLGetTextSentiment", "html", params);
    }

    public Document TextGetTextSentiment(String text)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return TextGetTextSentiment(text, new AlchemyAPI_Params());
    }
    
    public Document TextGetTextSentiment(String text, AlchemyAPI_Params params)
        throws IOException, SAXException,
        ParserConfigurationException, XPathExpressionException 
    {
        CheckText(text);
		
        params.setText(text);
		
        return POST("TextGetTextSentiment", "text", params);
    }
	
	//------------------
	
    public Document URLGetTargetedSentiment(String url, String target)
        throws IOException, SAXException, ParserConfigurationException,
               XPathExpressionException
    {
        return URLGetTargetedSentiment(url, target,
                                       new AlchemyAPI_TargetedSentimentParams());
    }

    public Document URLGetTargetedSentiment(String url, String target,
                                            AlchemyAPI_TargetedSentimentParams params)
        throws IOException, SAXException, ParserConfigurationException,
               XPathExpressionException
    {
        CheckURL(url);
        CheckText(target);

        params.setUrl(url);
        params.setTarget(target);

        return GET("URLGetTargetedSentiment", "url", params);
    }
	
    public Document HTMLGetTargetedSentiment(String html, String url, String target)
        throws IOException, SAXException, ParserConfigurationException,
               XPathExpressionException
    {
        return HTMLGetTargetedSentiment(html, url, target,
                                        new AlchemyAPI_TargetedSentimentParams());
    }

    public Document HTMLGetTargetedSentiment(String html, String url, String target,
                                             AlchemyAPI_TargetedSentimentParams params)
        throws IOException, SAXException, ParserConfigurationException,
               XPathExpressionException
    {
        CheckHTML(html, url);
        CheckText(target);
		
        params.setHtml(html);
        params.setUrl(url);
        params.setTarget(target);

        return POST("HTMLGetTargetedSentiment", "html", params);
    }
	
    public Document TextGetTargetedSentiment(String text, String target)
        throws IOException, SAXException, ParserConfigurationException,
               XPathExpressionException
    {
        return TextGetTargetedSentiment(text, target,
                                        new AlchemyAPI_TargetedSentimentParams());
    }

    public Document TextGetTargetedSentiment(String text, String target,
                                             AlchemyAPI_TargetedSentimentParams params)
        throws IOException, SAXException, ParserConfigurationException,
	       XPathExpressionException
    {
        CheckText(text);
        CheckText(target);

        params.setText(text);
        params.setTarget(target);

        return POST("TextGetTargetedSentiment", "text", params);
    }

	//------------------
	
    public Document URLGetRelations(String url) throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetRelations(url, new AlchemyAPI_RelationParams());
    }
    
    public Document URLGetRelations(String url, AlchemyAPI_RelationParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);

	    params.setUrl(url);
	
	    return GET("URLGetRelations", "url", params);
	}

    public Document HTMLGetRelations(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetRelations(html, url, new AlchemyAPI_RelationParams());
    }
    
    public Document HTMLGetRelations(String html, String url, AlchemyAPI_RelationParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);
	    
	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetRelations", "html", params);
	}

    public Document TextGetRelations(String text)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return TextGetRelations(text, new AlchemyAPI_RelationParams());
    }
    
    public Document TextGetRelations(String text, AlchemyAPI_RelationParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckText(text);
	    
	    params.setText(text);
	
	    return POST("TextGetRelations", "text", params);
	}

	//------------------
	
    public Document URLGetCombined(String url) throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
	AlchemyAPI_CombinedParams params = new AlchemyAPI_CombinedParams();
	params.setExtractAll();
        return URLGetCombined(url, params);
    }
    
    public Document URLGetCombined(String url, AlchemyAPI_CombinedParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);

	    params.setUrl(url);
	
	    return GET("URLGetCombinedData", "url", params);
	}

    public Document TextGetCombined(String text)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
	AlchemyAPI_CombinedParams params = new AlchemyAPI_CombinedParams();
	params.setExtractAll();
        return TextGetCombined(text, params);
    }
    
    public Document TextGetCombined(String text, AlchemyAPI_CombinedParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckText(text);
	    
	    params.setText(text);
	
	    return POST("TextGetCombinedData", "text", params);
	}
    
	//------------------
	
    public Document URLGetTaxonomy(String url) throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetTaxonomy(url, new AlchemyAPI_TaxonomyParams());
    }
    
    public Document URLGetTaxonomy(String url, AlchemyAPI_TaxonomyParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);

	    params.setUrl(url);
	
	    return GET("URLGetRankedTaxonomy", "url", params);
	}

    public Document HTMLGetTaxonomy(String html, String url)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return HTMLGetTaxonomy(html, url, new AlchemyAPI_TaxonomyParams());
    }
    
    public Document HTMLGetTaxonomy(String html, String url, AlchemyAPI_TaxonomyParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckHTML(html, url);
	    
	    params.setUrl(url);
	    params.setHtml(html);
	
	    return POST("HTMLGetRankedTaxonomy", "html", params);
	}

    public Document TextGetTaxonomy(String text)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return TextGetTaxonomy(text, new AlchemyAPI_TaxonomyParams());
    }
    
    public Document TextGetTaxonomy(String text, AlchemyAPI_TaxonomyParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckText(text);
	    
	    params.setText(text);
	
	    return POST("TextGetRankedTaxonomy", "text", params);
	}

	//------------------
	
    public Document URLGetImage(String url) throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        return URLGetImage(url, new AlchemyAPI_ImageParams());
    }
    
    public Document URLGetImage(String url, AlchemyAPI_ImageParams params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    CheckURL(url);

	    params.setUrl(url);
	
	    return GET("URLGetImage", "url", params);
	}

    private void CheckHTML(String html, String url) {
        if (null == html || html.length() < 5)
            throw new IllegalArgumentException("Enter a HTML document to analyze.");

        if (null == url || url.length() < 10)
            throw new IllegalArgumentException("Enter an URL to analyze.");
    }

    private void CheckText(String text) {
        if (null == text || text.length() < 5)
            throw new IllegalArgumentException("Enter some text to analyze.");
    }

    private void CheckURL(String url) {
        if (null == url || url.length() < 10)
            throw new IllegalArgumentException("Enter an URL to analyze.");
    }
    
    private Document GET(String callName, String callPrefix, AlchemyAPI_Params params)
    throws IOException, SAXException,
           ParserConfigurationException, XPathExpressionException
	{
	    StringBuilder uri = new StringBuilder();
	    uri.append(_requestUri).append(callPrefix).append('/').append(callName)
	       .append('?').append("apikey=").append(this._apiKey);
	    uri.append(params.getParameterString());
	
	    URL url = new URL(uri.toString());
	    HttpURLConnection handle = (HttpURLConnection) url.openConnection();
	    handle.setDoOutput(true);
	
	    return doRequest(handle, params.getOutputMode());
	}

    private Document POST(String callName, String callPrefix, AlchemyAPI_Params params)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        URL url = new URL(_requestUri + callPrefix + "/" + callName);

        HttpURLConnection handle = (HttpURLConnection) url.openConnection();
        handle.setDoOutput(true);

        StringBuilder data = new StringBuilder();

        data.append("apikey=").append(this._apiKey);
        data.append(params.getParameterString());

        handle.addRequestProperty("Content-Length", Integer.toString(data.length()));

        DataOutputStream ostream = new DataOutputStream(handle.getOutputStream());
        ostream.write(data.toString().getBytes());
        ostream.close();

        return doRequest(handle, params.getOutputMode());
    }

    private Document doRequest(HttpURLConnection handle, String outputMode)
        throws IOException, SAXException,
               ParserConfigurationException, XPathExpressionException
    {
        DataInputStream istream = new DataInputStream(handle.getInputStream());
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(istream);

        istream.close();
        handle.disconnect();

        XPathFactory factory = XPathFactory.newInstance();

        if(AlchemyAPI_Params.OUTPUT_XML.equals(outputMode)) {
        	String statusStr = getNodeValue(factory, doc, "/results/status/text()");
        	if (null == statusStr || !statusStr.equals("OK")) {
        		String statusInfoStr = getNodeValue(factory, doc, "/results/statusInfo/text()");
        		if (null != statusInfoStr && statusInfoStr.length() > 0)
        			throw new IOException("Error making API call: " + statusInfoStr + '.');

        		throw new IOException("Error making API call: " + statusStr + '.');
        	}
        }
        else if(AlchemyAPI_Params.OUTPUT_RDF.equals(outputMode)) {
        	String statusStr = getNodeValue(factory, doc, "//RDF/Description/ResultStatus/text()");
        	if (null == statusStr || !statusStr.equals("OK")) {
        		String statusInfoStr = getNodeValue(factory, doc, "//RDF/Description/ResultStatus/text()");
        		if (null != statusInfoStr && statusInfoStr.length() > 0)
        			throw new IOException("Error making API call: " + statusInfoStr + '.');

        		throw new IOException("Error making API call: " + statusStr + '.');
        	}
        }

        return doc;
    }

    private String getNodeValue(XPathFactory factory, Document doc, String xpathStr)
        throws XPathExpressionException
    {
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(xpathStr);
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList results = (NodeList) result;

        if (results.getLength() > 0 && null != results.item(0))
            return results.item(0).getNodeValue();

        return null;
    }
}
