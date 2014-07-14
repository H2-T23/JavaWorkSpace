// ParameterREceiver.java 

//package ThreeDG.INET;

import	java.io.*;
import	java.net.*;

public class ParameterReceiver {
	public int		nPort;
	
	public ParameterReceiver( int nPort ){
		this.nPort	= nPort;

		try{
			ServerSocket sockServer = new ServerSocket( nPort );
			while( true )
			{
				System.out.println( "Wait for acceptiing... " );
				Socket sockClient =  sockServer.accept();
				System.out.println( "Accepted client at " + sockClient.getInetAddress().getHostAddress() + " on port " + sockClient.getPort() );
			
			//	InputStreamReader	out	= new InputStreamReader( sockClient.getInputStream() );

				InputStream	in = sockClient.getInputStream();
				
				byte[] buf = new byte[ (1024 * 1024) ];
				
				int TotalSize	= 0;
				int RecvSize	= 0;
				while( (RecvSize = in.read(buf)) != -1 )
				{
					String str;
					for( int i=0; i<RecvSize; i++ )
					{
						
					}
					
					TotalSize += RecvSize;
				}
			}
		}
		catch( IOException e ){
			e.printStackTrace();
		}
	}
	
	public static void	main( String[] args ){
		System.out.println( args[ 0 ] );
		
		int nPort	= Integer.parseInt( args[ 0 ] );
		
		new ParameterReceiver( nPort );
	}
}
