// XmlWrite.java 

package ThreeDG.UI;


import	java.io.*;
import	java.util.*;
import	java.util.regex.*;
import	javax.xml.*;
import	javax.xml.parsers.*;
import	javax.xml.transform.*;
import	javax.xml.transform.dom.DOMSource;
import	javax.xml.transform.stream.StreamResult;
import	org.w3c.dom.*;
import	org.xml.sax.SAXException;


public class XmlWrite {
	private Document		doc;
	
	public void		delSpace( Node node ){
		Node root = node.getParentNode();
		Node prev = node.getPreviousSibling();
		
		if( "#text".equals( prev.getNodeName() ) ){
			String str = prev.getNodeValue();
			String regex = "";
			Pattern pattern = Pattern.compile( regex );
			Matcher matcher = pattern.matcher( str );
			if( matcher.find() ){
				root.removeChild( prev );
			}
		}
	}
	
	public Element	createDocumentElement( String root, String encode ){
		Element	rootElement = null;
		try{
			DocumentBuilderFactory	docBuilderFactory	= DocumentBuilderFactory.newInstance();
			DocumentBuilder			docBuilder			= docBuilderFactory.newDocumentBuilder();
			doc	= docBuilder.newDocument();
			rootElement	= doc.createElement( root );
			doc.appendChild( rootElement );
		}
		catch( ParserConfigurationException e ){
			e.printStackTrace();
		}
		return rootElement;
	}
		
	public Document	load( String filename ){
		try{
			DocumentBuilderFactory	docBuilderFactory	= DocumentBuilderFactory.newInstance();
			DocumentBuilder			docBuilder			= docBuilderFactory.newDocumentBuilder();
			FileInputStream	input = new FileInputStream( filename );
			doc	= docBuilder.parse( input );
			input.close();
		}
		catch( ParserConfigurationException e ){
			e.printStackTrace();
		}
		catch( FileNotFoundException e ){
			e.printStackTrace();
		}
		catch( SAXException e ){
			e.printStackTrace();
		}
		catch( IOException e ){
			e.printStackTrace();
		}
		
		return doc;
	}
		
	public void		write( String filename ){
		try{
			TransformerFactory	transFactory	= TransformerFactory.newInstance();
			transFactory.setAttribute( "indent-number", "4" );
			
			Transformer			trans			= transFactory.newTransformer();
			trans.setOutputProperty( OutputKeys.INDENT, "yes" );
			
			OutputStreamWriter	output = new OutputStreamWriter( new FileOutputStream(filename), "utf-8" );
			trans.transform( new DOMSource( doc ), new StreamResult( output ) );
		}
		catch( TransformerConfigurationException e ){
			e.printStackTrace();
		}
		catch( FileNotFoundException e ){
			e.printStackTrace();
		}
		catch( UnsupportedEncodingException e ){
			e.printStackTrace();
		}
		catch( TransformerException e ){
			e.printStackTrace();
		}
	}
		
	public void		output( String encode ){
		StringWriter	stringWriter = null;
		
		try{
			TransformerFactory	transFactory	= TransformerFactory.newInstance();
			transFactory.setAttribute( "indent-number", "4" );
			
			Transformer	trans	= transFactory.newTransformer();
			trans.setOutputProperty( OutputKeys.ENCODING, encode );
			trans.setOutputProperty( OutputKeys.INDENT, "yes" );
			
			stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult( stringWriter );
			DOMSource	domSource	= new DOMSource( doc );
			
			trans.transform( domSource, streamResult );
			
			System.out.println( stringWriter.toString() );
		}
		catch( TransformerConfigurationException e  ){
			e.printStackTrace();
		}
		catch( TransformerException e ){
			e.printStackTrace();
		}
		
		try{
			stringWriter.close();
		}
		catch( IOException e ){
			e.printStackTrace();
		}
	}
		
	public Document		getDocuemnt(){
		return doc;
	}
	
	
	public static void	main( String[] args ){
		XmlWrite xml = new XmlWrite();
		Element root = xml.createDocumentElement( "root", "utf-8" );
		Document doc = xml.getDocuemnt();
		
		Element	customer, address, name;
		
		
		customer	= doc.createElement( "customer" );
		
		address		= doc.createElement( "address" );
		address.setTextContent( "Tokyo" );
		
		name		= doc.createElement( "name" );
		name.setTextContent( "tokugawa ieyashu" );

		customer.appendChild( address );
		customer.appendChild( name );
		root.appendChild( customer );
		
		
		customer	= doc.createElement( "customer" );
		
		address		= doc.createElement( "address" );
		address.setTextContent( "osaka" );
		
		name		= doc.createElement( "name" );
		name.setTextContent( "toyotomi hideyoshi" );

		customer.appendChild( address );
		customer.appendChild( name );
		root.appendChild( customer );
		
		xml.output( "utf-8" );
		xml.write( "output.xml" );
		
		
		Node	target;
		Element	mail, tel;
		
		xml	= new XmlWrite();
		doc	= xml.load( "output.xml" );
		root	= (Element)doc.getDocumentElement();
		
		target	= root.getElementsByTagName( "customer" ).item( 1 );
		xml.delSpace( target );
		root.removeChild( target );
		
		target	= doc.getElementsByTagName( "customer" ).item( 0 );
		mail	= doc.createElement( "mail" );
		mail.appendChild( doc.createTextNode( "xxxxx.co.jp" ) );
		target.appendChild( mail );
		
		tel		= doc.createElement( "tel" );
		tel.appendChild( doc.createTextNode( "030-0000-0000" ) );
		target.appendChild( tel );
		
		customer	= doc.createElement( "customer" );
		
		address		= doc.createElement( "address" );
		address.appendChild( doc.createTextNode( "Fukuoka" ) );
		customer.appendChild( address );
		
		name		= doc.createElement( "name" );
		name.appendChild( doc.createTextNode( "Fujiwara no" ) );
		customer.appendChild( name );
		
		root.appendChild( customer );
		
		xml.output( "utf-8" );
		xml.write( "result.xml" );
	}
}
