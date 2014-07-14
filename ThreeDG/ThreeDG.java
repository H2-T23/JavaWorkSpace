// ThreeDG.java 

class Vector4D {
	public float		x,y,z,w;
	public Vector4D(){
		Zero();
	}
	
	public Vector4D( Vector4D v ){
		Set( v );
	}
	
	public Vector4D( float x, float y, float z, float w ){
		Set( x, y, z, w );
	}
	
	public void		Set( Vector4D v ){
		this.x = v.x; this.y = v.y; this.z = v.z; this.w = v.w;
	}
	
	public void		Set( float x, float y, float z, float w ){
		this.x = x;	this.y = y;	this.z = z;	this.w = w;
	}
	
	public void		Zero(){
		Set( 0, 0, 0, 0 );
	}
	
	public void		Add(){
	}
	
	public void		Sub(){
	}
	
	public void		Dot(){
	}
	
	public void		Cross(){
	}
}

class Matrix4D {
	public float[]		m	= new float[ 16 ];
	
	public Matrix4D(){
	}
	
	public void		Zero(){
	}
	
	public void		Identity(){
	}
}

class ViewMatrix4D extends Matrix4D {
	public ViewMatrix4D( Vector4D vEye, Vector4D vLookAt, Vector4D vUp ){
		
	}
}

class ProjectionMatrix4D extends Matrix4D {
	public ProjectionMatrix4D(){	
	}
}

public class ThreeDG {
	public static void	main( String[] args ){
		ViewMatrix4D mtxView = new ViewMatrix4D( new Vector4D(), new Vector4D(), new Vector4D() );
	}
}
