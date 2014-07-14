// ParameterXML.java 


import	java.io.File;

import	javax.xml.parsers.DocumentBuilder;
import	javax.xml.parsers.DocumentBuilderFactory;
import	javax.xml.xpath.XPath;
import	javax.xml.xpath.XPathConstants;
import	javax.xml.xpath.XPathFactory;

import	org.w3c.dom.Docuemnt;
import	org.w3c.dom.Node;
import	org.w3c.dom.NodeList;
import	org.w3c.dom.traversal.NodeIterator;

import	com.sun.org.apache.xpath.internal.XPathAPI;


class Test {
	public Test(){
		try{
			DocumentBuilder	builder = DocumentBuilderFactory.newInstace().newDocumentBuilder();
			
			Document doc = builder.parse( new File("file.xml") );
			
			XPath xpath = XPathFactory.newInstance().newXPath();
			
			String	strLocation = "/text/t1/text/text()";
			System.out.println( xpath.evaluate( strLocation, doc ) );
			
			strLocation	= "//t1/t2[2]/text()";
			NodeList list = (NodeList)xpath.eveluate( strLocation, doc, XPathConstants.NODESET );
		
		
			
			
			strLocation = "";
			System.out.println( XPathAPI.eval( doc, strLocation ) );
			
			
			list = 
			
		}
		catch( ){
		}
	}
}

/***

<xml>
<Parameters>
	<Parameter name="" typ="" max="" min="">
		
	</Parameter>
</Parameters>


[SAXの概要]
DOMを用いたプログラミングでは、
まずMXL文書を構文解析し、文書の構造に対応したオブジェクトのツリーを取得していました。

対してSAXのプログラミングでは、
XML文書の構文解析を始める点は同じですが、構文解析を済ませてから処理するのではなく、
構文解析を進めながら必要な情報を取得していきます。


どうやってそんなことを行うか？
という話ですが、原理は簡単です。
構文解析が進むにつれ、パーサーはタグやテキストデータを読み込んで認識していきます。
これらのタグやテキストデータを認識できた時点でイベントを発生させる、
というのがSAXパーサーのポイントになります。

プログラマーはあらかじめ、
コンテントハンドラと呼ばれる特殊な形式のイベントリスナをパーサーに登録して
おきます。プログラマはこのコンテントハンドラを通じて、
構文解析処理の過程で発生するイベント情報を受け取ることができます。



[XPathの概要]
XPathとは、
XML関連の規格の１つでXSLT version1.0



*/

public class ParameterXML {
	public ParameterXML(){
		
	}
	
	
	Document	doc;
	
	public Element	createDocumentElement( String strName, String strEncoding ){
		Element		root = null;
		try{
			
		}
		catch( ParserConfigurationException e ){
			e.printStackTrace();
		}
		
		return root;
	}
	
	public Document	getDocument(){
		return doc;
	}
	
	public static main( String[] args ){
		ParameterXML param = new ParameterXML();
	}
}
