// XmlDomHelper.java 

import	java.io.*;

import	org.w3c.dom.Node;
import	org.w3c.dom.Element;
import	org.w3c.dom.Document;

import	javax.xml.parsers.DocumentBuilderFactory;
import	javax.xml.parsers.DocumentBuilder;

import	javax.xml.parsers.ParserConfigurationException;

public class XmlDomHelper {
	public Document			doc	= null;

	public DocumentBuilder	newDocumentBuilder() throws ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder();
	}

	public Document			newDocument() throws ParserConfigurationException {
		return newDocumentBuilder().newDocument();
	}

	public Document			newDocument( String strRoot ){
		try{
			Document document = newDocument();
			Element	root = document.createElement( strRoot );
			document.appendChild( root );
			return document;
		}
		catch( ParserConfigurationException e ){
			e.printStackTrace();
		}
		return null;
	}

	public Document		loadFile( String strFilename ){
		try{
			FileInputStream	in = new FileInputStream( strFilename );
			
			DocumentBuilder builder = newDocumentBuilder();				
			return builder.parse( in );
		}
		catch( ParserConfigurationException e ){
			e.printStackTrace();
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		return null;
	}

	/***
	 */
	public void			saveFile( String strFilename, String strEncoding ){
		
	}
}
