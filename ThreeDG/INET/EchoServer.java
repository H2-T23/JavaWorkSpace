// EchoServer.java 

import	java.io.*;
import	java.net.*;



/*

// Flyweightパターンとは、このように、同じインスタンスを共有することで、
// 無駄なインスタンス生成をしないようにして、プログラム全体を軽くすることを
// 目的としたパターン。

class HumanLetter {
}

class HumanLetterFactroy {
	Map<String, HumanLetter> map = new HashMap<String, HumanLetter>();
	
	private static HumanLetterFactroy	ins = new HumanLetterFactory();
	private HumanLetterFactroy(){}
	public HumanLetterFactroy	getInstance(){ return ins; }
	
	public syncrhonizd HumanLetter	getHumanLetter( String letter ){
		HumanLetter humanLetter = map.get( letter );
		if( humanLetter == null ){
			humanLetter = new HumanLetter( letter );
			map.put( letter, humanLetter );
		}
		return humanLetter;
	}
}
*/


class GLOBAL {
	final public static int		ECHO_PORT = 10007;
}

class Task {
}

class EchoTask extends Task {
	public EchoTask( Socket sock ) throws IOException {
		BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
		PrintWriter out = new PrintWriter( sock.getOutputStream(), true );
		
		String str;
		while( (str = in.readLine()) != null ){
			System.out.println( str );
		}
		
		out.close();
		in.close();
	}
}

class EchoThread extends Thread {
	public Socket		sock = null;
	
	public EchoThread( Socket sock ){
		this.sock = sock;
		System.out.println( "Entry EchoThread(" + sock.getRemoteSocketAddress() + ")" );
	}
	
	public void		run(){
		try{
			new EchoTask( sock );
		//	throw new IOException();
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
			}
			
			System.out.println( "Terminate EchoThread(" + sock.getRemoteSocketAddress() + "(" );
		}
	}
}
		
class InetListener {
	public InetListener(){
		this( GLOBAL.ECHO_PORT );
	}
	
	public InetListener( int nPort ){
		ServerSocket sockServer = null;
		
		try{
			sockServer = new ServerSocket( nPort );
			
			while( true ){
				Socket sock = sockServer.accept();
				new EchoThread( sock ).start();
			}
		}
		catch( IOException e ){
			e.printStackTrace();
		}
		finally{
			try{
				if( sockServer != null ){
					sockServer.close();
				}
			}
			catch( IOException e ){
			}
		}
	}
}
		
class SingleThreadEchoServer {
	public SingleThreadEchoServer(){
		ServerSocket sockServer = null;
		Socket sock = null;
		
		try{
			sockServer = new ServerSocket( GLOBAL.ECHO_PORT );
			System.out.println( "Wait for accepting(port:" + sockServer.getLocalPort() + ")..." );
			
			sock = sockServer.accept();
			System.out.println( "Accepted client at " + sock.getRemoteSocketAddress() );

			BufferedReader in = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );
			PrintWriter out = new PrintWriter( sock.getOutputStream(), true );
			
			String str;
			while( (str = in.readLine()) != null )
			{
				System.out.println( "Receive: " + str );
			//	out.println( str );
			//	System.out.println( "Send: " + str );
			}
		}
		catch( IOException e ){
			e.printStackTrace();
		}
		finally
		{
			try{
				if( sock != null ){
					sock.close();
				}
			}
			catch( IOException e ){
			}
			
			try{
				if( sockServer != null ){
					sockServer.close();
				}
			}
			catch( IOException e ){
			}
		}
	}
}

public class EchoServer {	
	public static void	main( String[] args ){
		new SingleThreadEchoServer();
	}
}
