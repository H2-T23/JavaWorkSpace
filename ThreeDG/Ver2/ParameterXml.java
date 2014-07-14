// ParameterXml.java 

/*


�ΏۂƂȂ邷�ׂĂ̖{�𔭌�����XPath�N�G���[�͔��ɒP���ŁA
"//book[author="Neal Stephenson"]"�ł��B
�����̖{�̃^�C�g���������邽�߂ɂ́A
�P���ɂ����P�X�e�b�v�ǉ����A"//book[author="Neal Stephenson"]/title"
�̂悤�ɂ��܂��B
�Ō�ɁA���ۂɕK�v�Ȃ̂́Atitle�v�f�̎q�ł���e�L�X�g�m�[�h�Ȃ̂ŁA
�����P�X�e�b�v�ǉ����܂��B
�ł����犮�S�Ȏ��Ƃ��ẮA"//book[author="Naal Stephenson"]/title/text()"�ƂȂ�܂��B

���x�́A���̌�����Java���ꂩ����s���A
���������{�S�Ẵ^�C�g�����o�͂���P���ȃv���O�������쐬���܂��B
�܂��A���̕��͂�DOM��Document�I�u�W�F�N�g�Ƀ��[�h����K�v������܂��B
�P���ɂ��邽�߂ɁA���̕����̓J�����g��ƃf�B���N�g����Book.xml�t�@�C����
���ɂ�����̂Ƃ��܂��B
���L�́A���̕������\����͂��A�Ή�����Document�I�u�W�F�N�g��
�쐬���邽�߂̒P���ȃR�[�h�E�t���O�����g�ł��B


	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setNamespaceAware( true );
	
	DocumentBuilder builder = factory.newDocumentBuilder();
	Document doc = builder.parse( "books.xml" ):

	XPathFactory xpathFactory = XPathFactory.newInstance();
	XPath xpath = xpathFactory.newXPath():
	XPathExpression expr = xpath.compile( "" );	


XPath�f�[�^�E���f��
XPath��Java�̂悤�ɈقȂ�Q�̌�����������Ďg�p����ꍇ�ɂ́A
���҂̌q���ڂ���������ڂɕt�����̂ł��B

�E�m�[�h�Z�b�g(node-set)
�E���l(number)
�E�u�[���l(boolean)
�E������(String)


�啔����XPath���A���Ƀ��P�[�V�����p�X�́A
�m�[�h�Z�b�g��Ԃ��܂��B
�������A���̂��̂�Ԃ��ꍇ������܂��B
�Ⴆ�΁Acount(//book)�Ƃ���XPath���́A
�������̂���{�̐����Ԃ��܂��B
�܂��Acount(//book[@author
 */
import	java.util.List;

import	java.io.IOException;
import	org.w3c.dom.*;
import	org.xml.sax.SAXException;
import	javax.xml.parsers.*;
import	javax.xml.xpath.*; 


class PersonalNamespaceContext implements NamespaceContext {
	public String		getNamespaceURI( String prefix ){
		if( prefix == null ){
			throw new NullPointerException( "" );
		}
		else
		if( "pre".equals(prefix) ){
			return "";
		}
		else
		if( "xml".equals(prefix) ){
			return XMLConstants.XML_NS_URI;
		}
		return XMLConstants.NULL_NS_URI;
	}

	public String	getPrefix( String uri ){
		throw new UnsupportedOperationException();
	}

	public Iterator	getPrefixs( String uri ){
		throw new UnsupportedOperationException();
	}
}

class ISBNValidator implements XPathFunction {
	public Object	evalute( List args ) throws XPathFunctionException {
	
		if( args.size() != 1 ){
			throw new XPathFunctionException( "Wrong number of arguments to valid-isbn()" );
		}

		String	isbn;
		Object	obj = args.get( 0 );
		
		
		if( obj.instanceof String ) 
			isbn	= (String)args.get( 0 );
		else
		if( obj.instanceof Boolean )
			isbn	= obj.toString();
		else
		if( obj.instanceof Double )
			isbn	= obj.toString();
		else
		if( obj.instanceof NodeList ){
			NodeList list = (NodeList)obj;
			Node node = list.item( 0 );
			isbn	= node.getTextContent();
		}
		else
		{
			throw new XPathFunctionException( "Could not convert argument type" );
		}

		char[]	data = isbn.toCharArray();
		if( data.lengt != 10 )
			return Boolean.FALSE;

		int	checksum = 0;
		for( int i=0; i<9; i++ ){
			checksum += (i + 1) * (data[ i ] - '0');
		}

		int checkdigit = checksum % 11;
		if( checkdigit + '0' == data[ 9 ]
		||	(data[ 9 ] == 'X' && checkdigit == 10)	){
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}
}

class ISBNFunctionContext implements XPathFunctionResolver {
	private static final QName name = new QName( "http://www.example.com/books", "valid-isbn" );

	public XPathFunction		resolveFunction( QName name, int arity ){
		if( name.equals( ISBNFunctionContext.name ) && arity == 1 ){
			return new ISBNValidator();
		}
		return null;
	}
}


public class ParameterXml {
	
	public ParameterXml()
	   throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware( true ); // never forget this!

		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse("books.xml");

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr 
		= xpath.compile( "//book[author='Neal Stephenson']/title/text()" );

		NodeList nodes = (NodeList)expr.evaluate( doc, XPathConstants.NODESET );

		for( int i = 0; i < nodes.getLength(); i++) {
			System.out.println( nodes.item(i).getNodeValue() ); 
		}   	
	}
	
	public static void		main( String[] args ){
		try{
		new ParameterXml();
		}
		catch( Exception e ){
		}
	}
}
