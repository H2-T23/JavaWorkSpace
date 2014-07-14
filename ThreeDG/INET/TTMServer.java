// TTMServer.java 
// TTMServer.java 

import	java.io.*;
import	java.net.*;


public class TTMServer {

	enum SessionState {
		STATE_1, STATE_2, STATE_3, STATE_ERR,
	}

	class Session {
		private BufferedReader		in	= null;
		private PrintWriter			out	= null;
		private SessionState		state = SessionState.STATE_1;

		public Session( Socket sock ) throws IOException {
			this.in		= new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
			this.out	= new PrintWriter( sock.getOutputStream(), true );
		}

		public String	recive() throws IOException, NullPointerException {
			if( in  == null ) return null;
			if( out == null ) return null;

			String	str = null;
			str = in.readLine();

			switch( state ){
			case STATE_1:
				{
					System.out.println( str );	

					if( str.equals( "BEGIN-SYN" ) ){
						out.println( "BEGIN-ACK" );

						state = SessionState.STATE_2;
						System.out.println( "STATE_1 -> STATE_2" );
					}
					else{
						out.println();
						System.out.println( "Unsuppored command:" + str );
					}

					str = "";
				}
				break;

			case STATE_2:
				{
					System.out.println( str );	
		
					if( str.equals( "END-SYN" ) ){
						out.println( "END-ACK" );

						str = null;
						state = SessionState.STATE_1;
						System.out.println( "STATE_2 -> STATE_1" );
					}
					else
					{
						out.println();
					}
				}
				break;
			}
	
			return str;
		}
	}

	public TTMServer( int nPort ){
		ServerSocket sockServer = null;
		Socket sock = null;

		try{
			sockServer = new ServerSocket( nPort );
			
			while( true )
			{
				sock = sockServer.accept();

				StringBuilder strBuilder = null;
				Session session = null;
				while( true ){
					try{
						strBuilder = new StringBuilder();
						session = new Session( sock );

						String str = null;
						while( (str = session.recive()) != null ){
							strBuilder.append( str );
						}

						System.out.println( strBuilder );
						System.out.println( "next session(1)" );
					}
					catch( IOException e ){
						System.out.println( "IOException" );
					}
					catch( NullPointerException e ){
						System.out.println( "NullPointerException" );
						break;
					}
				}
			}
		}
		catch( IOException e ){
			e.printStackTrace();
		}
		finally{
			try{
				if( sock != null ){
					sock.close();
				}
			}
			catch( IOException e ){
				e.printStackTrace();
			}

			try{
				if( sockServer != null ){
					sockServer.close();
				}
			}
			catch( IOException e ){
				e.printStackTrace();
			}
		}
	}

	public static void	main( String[] args ){
		new TTMServer( 12345 );
	}
}
