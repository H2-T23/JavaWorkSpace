// Bootloader.java 

public class Bootloader {
	public static void	main( String[] args ){
		try{
			Runtime runtime = Runtime.getRuntime();
			runtime.exec( "java MatrixFrame" );
		}
		catch( java.io.IOException ex ){
			ex.printStackTrace();
		}
	}
}
