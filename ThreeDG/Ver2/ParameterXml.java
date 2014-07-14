// ParameterXml.java 

/*


対象となるすべての本を発見するXPathクエリーは非常に単純で、
"//book[author="Neal Stephenson"]"です。
これらの本のタイトルを見つけるためには、
単純にもう１ステップ追加し、"//book[author="Neal Stephenson"]/title"
のようにします。
最後に、実際に必要なのは、title要素の子であるテキストノードなので、
もう１ステップ追加します。
ですから完全な式としては、"//book[author="Naal Stephenson"]/title/text()"となります。

今度は、この検索をJava言語から実行し、
発見した本全てのタイトルを出力する単純なプログラムを作成します。
まず、この文章をDOMのDocumentオブジェクトにロードする必要があります。
単純にするために、この文書はカレント作業ディレクトリのBook.xmlファイルの
中にあるものとします。
下記は、この文書を構文解析し、対応したDocumentオブジェクトを
作成するための単純なコード・フラグメントです。


	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setNamespaceAware( true );
	
	DocumentBuilder builder = factory.newDocumentBuilder();
	Document doc = builder.parse( "books.xml" ):

	XPathFactory xpathFactory = XPathFactory.newInstance();
	XPath xpath = xpathFactory.newXPath():
	XPathExpression expr = xpath.compile( "" );	


XPathデータ・モデル
XPathとJavaのように異なる２つの言語を混合して使用する場合には、
両者の繋ぐ目が何かしら目に付くものです。

・ノードセット(node-set)
・数値(number)
・ブール値(boolean)
・文字列(String)


大部分のXPath式、特にロケーションパスは、
ノードセットを返します。
しかし、他のものを返す場合もあります。
例えば、count(//book)というXPath式は、
文書中のある本の数ｗ返します。
また、count(//book[@author
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
