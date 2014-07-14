// ParameterSender.java 

//package ThreeDG.INET;

import	java.net.*;
import	java.io.*;

public class  ParameterSender {
	public String		strServerAddress; 
	public int			nPort;
	
	public ParameterSender( String strServerAddress, int nPort ){
		this.strServerAddress	= strServerAddress;
		this.nPort				= nPort;
	}
	
	public void		write( String strFilePath ){
		
		try{
			Socket			sock	= new Socket( strServerAddress, nPort );
		//	FileInputStream	in		= new FileInputStream( strFilePath );
			BufferedReader	in		= new BufferedReader( new FileReader( strFilePath ) );
		//	OutputStream	out		= sock.getOutputStream();
			OutputStreamWriter	out	= new OutputStreamWriter( sock.getOutputStream() );
			
			String str;
			while( (str = in.readLine()) != null )
			{
				System.out.println( str );
				out.write( str, 0, (int)str.length() );
			}
			
		//	byte[] buf = new byte[ (1024 * 1024) ];
		//	
		//	int TotalLen	= 0;
		//	int Len			= 0;
		//	while( (Len = in.read(buf)) != -1 )
		//	{
		//		System.out.println( buf );
		//		TotalLen += Len;
		//		out.write( buf, 0, Len );
		//	}
		
			in.close();
			out.close();
			sock.close();
		}
		catch( UnknownHostException e ){
		}
		catch( FileNotFoundException e ){
		}
		catch( IOException e ){
		}
				
	}
	
	public static void	main( String[] args ){
		System.out.println( args[ 0 ] + " : " + args[ 1 ] + " : " + args[ 2 ] );
		
		String	strServerAddress	= args[ 0 ];
		int		nPort				= Integer.parseInt( args[ 1 ] );
		String	strFilePath			= args[ 2 ];
		
	//	try{
	//		BufferedReader buf = new BufferedReader( new FileReader( strFilePath ) );
	//		String str;
	//		while( (str = buf.readLine()) != null ){
	//			System.out.println( str );
	//		}
	//		
	//		return;
	//	}
	//	catch( Exception e ){
	//		e.printStackTrace();
	//		System.exit( 0 );
	//	}
		
		ParameterSender sender = new ParameterSender( strServerAddress, nPort );
		sender.write( strFilePath );
	}
}
