// ParameterManager.java 

class DefineNumber {
	final public static int			I_ZERO	= 0;
	final public static float		F_ZERO	= 0.0f;
	final public static double		D_ZERO	= 0.0;

	final public static int			I_ONE	= 1;
	final public static float		F_ONE	= 1.0f;
	final public static double		D_ONE	= 1.0;
}

class Vector3D {
	public float		x, y, z;
	
	public Vector3D(){
		this( 0.0f, 0.0f, 0.0f );		
	}
	
	public Vector3D( Vector3D vec ){
		this( vec.x, vec.y, vec.z );
	}
	
	public Vector3D( float x, float y, float z ){
		this.set( x, y, z );
	}
	
	public void		set( float x, float y, float z ){
		this.x	= x;
		this.y	= y;
		this.z	= z;
	}
}


interface Observer {
	public void		update( Paramter param );
}

abstract class Subject {
	private java.util.ArrayList<Observer>		listObserver = new java.util.ArrayList<Observer>();
	
	public void		addObserver( Observer obs ){
		listObserver.add( obs );
	}
	
	public void		delObserver( Observer obs ){
		int idx = listObserver.indexOf( obs )+
		listObserver.remove( idx );
	}
	
	public void		notifyObserver( Parameter param ){
		java.util.Iterator it = listObserver.iterator();
		while( it.hasNext() ){
			((Observer)it.next()).update( param );
		}
	}
}

enum ParameterID {
}

class Parameter {
	public Vector3D			vecEye			= new Vector3D();
	public Vector3D			vecLookAt		= new Vector3D();
	public Vector3D			vecUp			= new Vector3D();
}



class Scene extends Subject {

}

class RenderFrame extends JFrame implements Observer {

	Scene scene = new Scene();
	
	public RenderFrame(){
		scene.addObserver( this );
	}
	
	public void		update(){
	}
}

public class ParameterManager extends Subject implements ChangeListener {
	
	private static ParameterManager		ins = new ParameterManager();
	
	public static ParameterManager	getInstance(){
		return ins;
	}
	
	public void			stateChanged( ChangeEvent event ){
		System.out.println( "stateChanged" );
	}
	
	
	private Parameter		params = new Parameter();
	
	public void		write( Parameter param ){
		
	}
	
	public void		read(){
		
	}
	
	public static void	main( String[] args ){
		
	}
}
