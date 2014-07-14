// WebServer.java 

import	java.net.*;
import	java.io.*;
import	java.util.*;

/***********************************************************
 *
 *
 */
class Log extends Thread {
	private PrintWriter		m_LogWriter		= null;
	
	private Vector<String>	m_Message		= new Vector<String>();
	private Boolean			m_bContinue		= Boolean.FALSE;
	
	/***********************************************************
	 *
	 *
	 */
	boolean		createLog( String strFilename )
	{
		if( m_LogWriter == null )
		{
			try{
				FileWriter filewriter = new FileWriter( strFilename, true );
				m_LogWriter	= new PrintWriter( filewriter );
				
				m_bContinue = Boolean.TRUE;
				
				start();
				return true;
			}
			catch( Exception e ){
				m_LogWriter = null;
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	/***********************************************************
	 *
	 *
	 */
	public void		run()
	{
		if( m_LogWriter != null )
		{
			while( m_bContinue.booleanValue() )
			{
				try{
					synchronized( this )
					{
						wait();
					}
				}
				catch( InterruptedException e ){
					e.printStackTrace();
				}
				
				synchronized( m_bContinue )
				{
					for( int i=0; i<m_Message.size(); i++ )
					{
						m_LogWriter.println( (String)m_Message.elementAt( i ) );
					}
					m_Message.removeAllElements();
					
					m_LogWriter.flush();
				}
			}
		}
	}
	
	/***********************************************************
	 *
	 *
	 */
	public void		start()
	{
		if( m_LogWriter != null )
		{
			super.start();
		}
	}
	
	/***********************************************************
	 *
	 *
	 */
	void	terminate()
	{
		synchronized( m_bContinue )
		{
			m_bContinue	= Boolean.FALSE;
		}
		
		synchronized( this )
		{
			notify();
		}
		
		try{
			join();
		}
		catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	/***********************************************************
	 *
	 */
	void	writeLog( String str )
	{
		if( (m_LogWriter != null) && (m_bContinue.booleanValue()) )
		{
			synchronized( m_bContinue )
			{
				m_Message.addElement( str );
			}
			
			synchronized( this )
			{
				notify();
			}
		}
	}
}

/***********************************************************
 *
 *
 */
class Listener extends Thread {
	private Vector<HttpThread>	m_HttpThread	= new Vector<HttpThread>();
	private int					m_nPort			= 0;
	private ServerSocket		m_sockServer	= null;
	private boolean				m_bContinue		= false;
	
	/***********************************************************
	 *
	 */
	public Listener()
	{
	}
	
	/***********************************************************
	 *
	 */
	boolean	setPort( int nPort )
	{
		if( 0 < nPort && nPort < 65535 && isAlive() == false )
		{
			try{
				m_sockServer	= new ServerSocket( nPort );
				m_nPort			= nPort;
				return true;
			}
			catch( Exception e ){
			}
		}
		
		m_sockServer	= null;
		m_bContinue		= false;
		return false;
	}
	
	/***********************************************************
	 *
	 */
	public void		start()
	{
		if( m_sockServer == null ){
			return;
		}
		
		m_bContinue	= true;
		super.start();
	}
	
	/***********************************************************
	 *
	 */
	public void	terminate()
	{
		if( m_sockServer == null ){
			return;
		}
		
		m_bContinue	= false;
		
		try{
			m_sockServer.close();
		}
		catch( Exception e ){
		}
		
		synchronized( m_HttpThread )
		{
			for( int i=0; i<m_HttpThread.size(); i++ )
			{
				HttpThread httpd = (HttpThread)m_HttpThread.elementAt( i );
				httpd.terminate();
			}
		}
		
		try{
			join();
		}
		catch( Exception e ){
		}
	}
	
	/***********************************************************
	 *
	 */
	public void		run()
	{
		if( m_sockServer == null ){
			return;
		}
		
		while( m_bContinue )
		{
			Socket	sock;
			
			try{
				synchronized( m_HttpThread )
				{
					sock	= m_sockServer.accept();
					
					HttpThread http = new HttpThread( this, sock );
					
					m_HttpThread.addElement( http );
					
					http.start();
				}
			}
			catch( Exception e ){
			}
		}
	}
	
	/***********************************************************
	 *
	 */
	private void	threadEnd( HttpThread http )
	{
		m_HttpThread.removeElement( http );
	}
}


/***********************************************************
 *
 *
 */
class HttpThread extends Thread {
	private Socket		m_sock		= null;
	private Listener	m_listener	= null;
	
	/***********************************************************
	 *
	 */
	public HttpThread( Listener listener, Socket sock ){
		if( sock == null ){
			throw new NullPointerException();
		}
		
		if( listener == null ){
			throw new NullPointerException();
		}
		
		m_sock		= sock;
		m_listener	= listener;
	}
	
	/***********************************************************
	 *
	 */
	public void		run()
	{
		String		strToken	= "";
		String		strLog		= "";
		
		InputStream		in	= null;
		OutputStream	out	= null;
		
		try{
			in		= m_sock.getInputStream();
			out		= m_sock.getOutputStream();
			
			BufferedReader rBuffer = new BufferedReader( new InputStreamReader(in) );
			
			
			String	strLine = rBuffer.readLine();
			
			StringTokenizer tokenizer = StringTokenizer( strLine );
			while( tokenizer.hasMoreTokens() )
			{
				strToken = tokenizer.nextToken();
				
				if( strToken.equals( "GET" ) )
				{
					if( tokenizer.hasMoreTokens() )
					{
						strToken = tokenizer.nextToken();
						
						if( sendTarget( strToken ) )
						{
							strLog	+= (new Date() + '\t');
							strLog	+= (m_sock.getInetAddress().getHostName() + '\t');
							strLog	+= "OK\t";
							strLog	+= strToken;
						}
						else
						{
							strLog	+= (new Date() + '\t');
							strLog	+= (m_sock.getInetAddress().getHostName() + '\t');
							strLog	+= "Failed\t";
							strLog	+= strToken;
						}

						WebServer.writeLog( strLog );
					
						in.close();
						out.close();
						m_sock.close();
						
						m_listener.threadEnd( this );
					}
				}
			}// end of "while( tokenizer.hasMoreTokens())".
		}
		catch( IOException e ){
		}
		finally{

			strLog	+= (new Date() + '\t');
			strLog	+= (m_sock.getInetAddress().getHostName() + '\t');
			strLog	+= "Faled\t";
			
			WebServer.writeLog( strLog );
			
	
			
			try{
				if( in != null ){
					in.close();
				}
			}
			catch( IOException e ){
			}
			
			try{
				if( out != null ){
					out.close();
				}
			}
			catch( IOExcption e ){
			}
			
			try{
				m_sock.close();
			}
			catch( IOException e ){
			}
			
			m_listener.threadEnd( this );
		}
		
	}
	
	/***********************************************************
	 *
	 */
	public void		terminate()
	{
		try{
			m_sock.close();
			join();
		}
		catch( Exception e ){
		}
	}
	
	/***********************************************************
	 *
	 */
	private boolean	sendTarget( String strTarget )
	{
		try{
			FileInputStream	fin = new FileInputStream( WebServer.getFolder() + strTarget );
			BufferedOutputStream buf = new BufferedOutputStream( m_sock.getOutputStream() );
			OutputStreamWriter out = new OutputStreamWriter( buf );
			
			out.write( "" );
			out.write( "" );
			out.write( "" );
			out.flush();
			
			byte[] data = new byte[ 1024 ];
			
			int nRet = 0;
			while( (nRet = fin.read(data)) > 0 )
			{
				buf.write( data, 0, nRet );
			}
			fin.close();
			
			return true;
		}
		catch( FileNotFoundException e ){
			sendFileNotFound();
		}
		catch( Exception e ){
		}
		
		return false;
	}
	
	/***********************************************************
	 *
	 */
	private	void	sendFileNotFound()
	{
		try{
			BufferedOutputStream buf = new BufferedOutputStream( m_sock.getOutputStream() );
			OutputStreamWriter out = new OutputStreamWriter( buf );
			
			out.write( "HTTP/1.1 404 Not Found\r\n" );
			out.flush();
		}
		catch( Exception e ){
		}
	}
}

/***********************************************************
 *
 *
 */
public class WebServer {
	private static String		m_strFolder;
	private static Log			m_Log		= new Log();
	private static Listener		m_Listener	= new Listener();

	/***********************************************************
	 *
	 */
	public static void	main( String[] args )
	{
		showTitle();
		
		if( args.length != 1 ){
			showUsage();
			return;
		}
		
		if( initialize(args[0]) == false ){
			return;
		}
		
		m_Listener.start();
		
		InputStreamReader in = new InputStreamReader( System.in );
		BufferedReader reader = new BufferedReader( in );
		
		String str;
		while( true ){
			try{
				str	= reader.readLine();
				str = str.toLowerCase();
				if( str.equals("exit") || str.equals("quit") ){
					System.out.println( "EXIT" );
					m_Listener.terminate();
					m_Log.terminate();
					break;
				}
			}
			catch( Exception e ){
			}
		}
	}
	
	/***********************************************************
	 *
	 */
	static void		writeLog( String str ){
		m_Log.writeLog( str );
		System.out.println( str );
	}
	
	/***********************************************************
	 *
	 */
	static String	getFolder(){
		return m_strFolder;
	}
	
	/***********************************************************
	 *
	 */
	private static boolean	initialize( String strFileName )
	{
		FileReader	filereader;
		
		try{
			filereader	= new FileReader( strFileName );
			BufferedReader	reader	= new BufferedReader( filereader );
			
			String	strLine;
			
			while( (strLine = reader.readLine()) != null ){
				StringReader	strReader	= new StringReader( strLine );
				StreamTokenizer	tokenizer	= new StreamTokenizer( strReader );
				
				String	strFirstToken	= "";
				String	strSecondToken	= "";
				
				int	num = -1;
				
				if( (tokenizer.nextToken() != tokenizer.TT_EOF)
				&&	(tokenizer.ttype == tokenizer.TT_WORD) )
				{
					strFirstToken	= tokenizer.sval;
				}
				else
				{
					showInitFormatError();
					filereader.close();
					return false;
				}
				
				if( (tokenizer.nextToken() != tokenizer.TT_EOF)
				&&	(tokenizer.ttype == (int)'=') )
				{
					;
				}
				else
				{
					showInitFormatError();
					filereader.close();
					return false;
				}
				
				if( tokenizer.nextToken() != tokenizer.TT_EOF )
				{
					if( (tokenizer.ttype == tokenizer.TT_WORD) 
					||	(tokenizer.ttype == '\"') )
					{
						strSecondToken = tokenizer.sval;
					}
					else
					if( tokenizer.ttype == tokenizer.TT_NUMBER )
					{
						num = (int)tokenizer.nval;
					}
				}
				else
				{
					showInitFormatError();
					filereader.close();
					return false;
				}
				
				strFirstToken	= strFirstToken.toLowerCase();
				if( strSecondToken.length() > 1 && strSecondToken.startsWith("\"") ){
					strSecondToken	= strSecondToken.substring( 1 );
				}
				
				if( strSecondToken.length() > 1 && strSecondToken.endsWith("\"") ){
					strSecondToken	= strSecondToken.substring( 0, strSecondToken.length() - 1);
				}
				
				if( strFirstToken.equals("folder") ){
					File file = new File( strSecondToken );
					if( file.isDirectory() == true ){
						m_strFolder	= strSecondToken;
					}else{
						showInitFolderError();
						return false;
					}
				}
				
				if( strFirstToken.equals("log") ){
					if( m_Log.createLog( strSecondToken ) == false ){
						showInitLogError();
						return false;
					}
				}
								
				if( strFirstToken.equals("port") ){
					try{
						if( m_Listener.setPort( num ) == false ){
							showInitPortError();
							return false;
						}
					}
					catch( NumberFormatException e ){
						showInitPortError();
						return false;
					}
				}
			}
			
			if( m_strFolder == null ){
				showInitFormatError();
				return false;
			}				
			
			return true;
		}
		catch( FileNotFoundException e ){
			showInitLoadError();
			return false;
		}
		catch( IOException e ){
			showInitLoad2Error();
			return false;
		}
		finally{
			filereader.close();
		}
	}
	
	/***********************************************************
	 *
	 */
	private static void		showInitPortError(){
		System.out.println( "" );
	}
	
	/***********************************************************
	 *
	 */
	private static void		showInitLoadError(){
		System.out.println( "" );
	}
	
	/***********************************************************
	 *
	 */
	private static void		showInitLoad2Error(){
		System.out.println( "" );
	}
	
	/***********************************************************
	 *
	 */
	private static void		showInitFolderError(){
		System.out.println( "" );
	}
	
	/***********************************************************
	 *
	 */
	private static void		showInitLogError(){
		System.out.println( "" );
	}
	
	/***********************************************************
	 *
	 */
	private static void		showInitFormatError(){
	}
	
	/***********************************************************
	 *
	 */
	private static void		showTitle(){
	}
	
	/***********************************************************
	 *
	 */
	private static void		showUsage(){
		System.out.println( "usage: java WebServer [int file]" );
	}
}

