// ResultFrame.java 

import	javax.swing.JFrame;


public class ResultFrame extends JFrame implements EventHandler.Observer {
	
	public void		OnUpdate(){
		System.out.println( "ResultFrame::OnUpdate()" );
	}
	
	public ResultFrame( int x, int y, int w, int h ){
		setBounds( x, y, w, h );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	
	}
	
	public static void	main( String[] args ){
		new ResultFrame( 100, 100, 300, 300 ).setVisible( true );
	}
}
