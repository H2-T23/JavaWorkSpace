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


[SAX�̊T�v]
DOM��p�����v���O���~���O�ł́A
�܂�MXL�������\����͂��A�����̍\���ɑΉ������I�u�W�F�N�g�̃c���[���擾���Ă��܂����B

�΂���SAX�̃v���O���~���O�ł́A
XML�����̍\����͂��n�߂�_�͓����ł����A�\����͂��ς܂��Ă��珈������̂ł͂Ȃ��A
�\����͂�i�߂Ȃ���K�v�ȏ����擾���Ă����܂��B


�ǂ�����Ă���Ȃ��Ƃ��s�����H
�Ƃ����b�ł����A�����͊ȒP�ł��B
�\����͂��i�ނɂ�A�p�[�T�[�̓^�O��e�L�X�g�f�[�^��ǂݍ���ŔF�����Ă����܂��B
�����̃^�O��e�L�X�g�f�[�^��F���ł������_�ŃC�x���g�𔭐�������A
�Ƃ����̂�SAX�p�[�T�[�̃|�C���g�ɂȂ�܂��B

�v���O���}�[�͂��炩���߁A
�R���e���g�n���h���ƌĂ΂�����Ȍ`���̃C�x���g���X�i���p�[�T�[�ɓo�^����
�����܂��B�v���O���}�͂��̃R���e���g�n���h����ʂ��āA
�\����͏����̉ߒ��Ŕ�������C�x���g�����󂯎�邱�Ƃ��ł��܂��B



[XPath�̊T�v]
XPath�Ƃ́A
XML�֘A�̋K�i�̂P��XSLT version1.0



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
