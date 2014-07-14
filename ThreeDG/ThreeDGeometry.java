 
// 3DGeometry.java 
//package ThreeDGeometry;

import	java.util.*;
import	java.lang.Math;
import	java.awt.*;
import	java.awt.event.*;
import	javax.swing.*;
import	javax.swing.border.*;
import	javax.swing.event.*;

/********************************************
 * 
 */
class Rect {
	public int	top, left, bottom, right;

	public Rect(){
		Set( 0, 0, 0, 0 );
	}

	public Rect( int t, int l, int b, int r ){
		Set( t, l, b, r );
	}

	public Rect( Rect rc ){
		Set( rc );
	}

	public void	Set( int t, int l, int b, int r ){
		top = t; bottom = b; left = l; right = r;
	}

	public void	Set( Rect rc ){
		Set( rc.top, rc.left, rc.bottom, rc.right );
	}

	public int	Width(){
		return(Math.abs(right - left));
	}

	public int	Height(){
		return(Math.abs(bottom - top));
	}
}

/********************************************
 * 
 */
class Vector4D {
	public float	x, y, z, w;

	public Vector4D(){
		set( 0, 0, 0 );
	}

	public Vector4D( Vector4D vec ){
		set( vec.x, vec.y, vec.z, vec.w );
	}

	public Vector4D( float x, float y, float z, float w ){
		set( x, y, z, w );
	}

	public Vector4D( float x, float y, float z ){
		set( x, y, z );
	}

	public void		set( Vector4D vec ){
		set( vec.x, vec.y, vec.z, vec.w );
	}

	public void		set( float x, float y, float z, float w ){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public void		set( float x, float y, float z ){
		set( x, y, z, 1 );
	}

	public Vector4D		addition( Vector4D vec ){
		x += vec.x;
		y += vec.y;
		z += vec.z;
		w += vec.w;
		return this;
	}

	public Vector4D		subtraction( Vector4D vec ){
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		w -= vec.w;
		return this;
	}

	/********************************************
	 * dot:ì‡êœ
	 */
	public float		dot( float fScaler ){
		return(x * fScaler	+ y * fScaler	+ z * fScaler	+ w * fScaler);
	}

	public float		dot( Vector4D vec ){
		return(x * vec.x	+ y * vec.y		+ z * vec.z		+ w * vec.w);	
	}

	/********************************************
	 * cross:äOêœ 
	 */
	public Vector4D		cross( Vector4D b ){
		Vector4D	a = new Vector4D( x, y, z, w );
		x	= a.y * b.z - a.z * b.y;
		y	= a.z * b.x - a.x * b.z;
		z	= a.x * b.y - a.y * b.x;
	//	x *= vec.x;
	//	y *= vec.y;
	//	z *= vec.z;
	//	w *= vec.w;
		return this;
	}

	/********************************************
	 * í∑Ç≥ÅEëÂÇ´Ç≥ÅEê‚ëŒíl 
	 * |A| = Å„(a^2 + b^2 + c^3)
	 */
	public float		absolute(){
		double	
		dResult	 = Math.pow( (double)x, 2 );
		dResult	+= Math.pow( (double)y, 2 );
		dResult	+= Math.pow( (double)z, 2 );
	//	dResult	+= Math.pow( (double)w, 2 );
		return (float)Math.sqrt( dResult );
	}

	/********************************************
	 * Normalize:íPà ÉxÉNÉgÉã(unit vector)ÇãÅÇﬂÇÈ 
	 * A/|A|
	 */
	public Vector4D		Normalize(){
		float	fInverseAbs = 1.0f / absolute();	// ëÂÇ´Ç≥ÇÃãtêî 
		x	= x * fInverseAbs;
		y	= y * fInverseAbs;
		z	= z * fInverseAbs;
	//	w	= w * fInverseAbs;
		return this;
	}

	/********************************************
	 *	îΩì] 
	 */
	public Vector4D		Invert(){
		x *= -1;
		y *= -1;
		z *= -1;
		w *= -1;
		return this;
	}

	/********************************************
	 */
	public String	toString(){
		return("[" + x + ", " + y + ", " + z + ", " + w + "]");
	}
}

/********************************************
 * 
 */
class Matrix4D {
	float		m00,m01,m02,m03
			,	m10,m11,m12,m13
			,	m20,m21,m22,m23
			,	m30,m31,m32,m33;

	public Matrix4D(){
		Identity();
	}

	public void		Set(	float m00, float m01, float m02, float m03,
							float m10, float m11, float m12, float m13,
							float m20, float m21, float m22, float m23,
							float m30, float m31, float m32, float m33 ){
		this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
		this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
		this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
		this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
	}

	public void		Set( Matrix4D mtx ){
		Set(	mtx.m00, mtx.m01, mtx.m02, mtx.m03
			,	mtx.m10, mtx.m11, mtx.m12, mtx.m13
			,	mtx.m20, mtx.m21, mtx.m22, mtx.m23
			,	mtx.m30, mtx.m31, mtx.m32, mtx.m33	);
	}

	public void		Zero(){
		m00 = m01 = m02 = m03 =
		m10 = m11 = m12 = m13 = 
		m20 = m21 = m22 = m23 =
		m30 = m31 = m32 = m33 = 0.0f;
	}

	public void		Identity(){
		Zero();
		m00 = m11 = m22 = m33 = 1.0f;
	}

	public Matrix4D	Mult( Matrix4D m ){
		float _00 = m00*m.m00 + m01*m.m10 + m02*m.m20 + m03*m.m30;
		float _01 = m00*m.m01 + m01*m.m11 + m02*m.m21 + m03*m.m31;
		float _02 = m00*m.m02 + m01*m.m12 + m02*m.m22 + m03*m.m32;
		float _03 = m00*m.m03 + m01*m.m13 + m02*m.m23 + m03*m.m33;
		float _10 = m10*m.m00 + m11*m.m10 + m12*m.m20 + m13*m.m30;
		float _11 = m10*m.m01 + m11*m.m11 + m12*m.m21 + m13*m.m31;
		float _12 = m10*m.m02 + m11*m.m12 + m12*m.m22 + m13*m.m32;
		float _13 = m10*m.m03 + m11*m.m13 + m12*m.m23 + m13*m.m33;
		float _20 = m20*m.m00 + m21*m.m10 + m22*m.m20 + m23*m.m30;
		float _21 = m20*m.m01 + m21*m.m11 + m22*m.m21 + m23*m.m31;
		float _22 = m20*m.m02 + m21*m.m12 + m22*m.m22 + m23*m.m32;
		float _23 = m20*m.m03 + m21*m.m13 + m22*m.m23 + m23*m.m33;
		float _30 = m30*m.m00 + m31*m.m10 + m32*m.m20 + m33*m.m30;
		float _31 = m30*m.m01 + m31*m.m11 + m32*m.m21 + m33*m.m31;
		float _32 = m30*m.m02 + m31*m.m12 + m32*m.m22 + m33*m.m32;
		float _33 = m30*m.m03 + m31*m.m13 + m32*m.m23 + m33*m.m33;
		
		m00 = _00;	m01 = _01;	m02 = _02;	m03 = _03;
		m10 = _10;	m11 = _11;	m12 = _12;	m13 = _13;
		m20 = _20;	m21 = _21;	m22 = _22;	m23 = _23;
		m30 = _30;	m31 = _31;	m32 = _32;	m33 = _33;
		return this;
	}

	/********************************************
	 * ägëÂèkè¨
	 * 
	 * float sx ...Çwî{ó¶
	 * float sy ...Çxî{ó¶
	 * float sz ...Çyî{ó¶
	 */
	void	Scale( float sx, float sy, float sz ){		
		//| sx  0  0  0|
		//|  0 sy  0  0|
		//|  0  0 sz  0|
		//|  0  0  0  1|
		Matrix4D m = new Matrix4D();
		m.m00 =   sx;	m.m01 = 0.0f;	m.m02 = 0.0f;	m.m03 = 0.0f;
		m.m10 = 0.0f;	m.m11 =   sy;	m.m12 = 0.0f;	m.m13 = 0.0f;
		m.m20 = 0.0f;	m.m21 = 0.0f;	m.m22 =   sz;	m.m23 = 0.0f;
		m.m30 = 0.0f;	m.m31 = 0.0f;	m.m32 = 0.0f;	m.m33 = 1.0f;

		//çsóÒÇÃçáê¨
		this.Mult( m );
	}
	
	/********************************************
	 * Çwé≤âÒì]
	 * 
	 * float r ...âÒì]äpÅiíPà ÉâÉWÉAÉìÅj
	 */
	void	RotateX( float r ){
		float sinx = (float)Math.sin(r);
		float cosx = (float)Math.cos(r);	

		//Xé≤âÒì]
		//| 1     0    0 0|
		//| 0  cosX sinX 0|
		//| 0 -sinX cosX 0|
		//| 0     0    0 1|	
		Matrix4D m = new Matrix4D();
		m.m00 = 1.0f;	m.m01 = 0.0f;	m.m02 = 0.0f;	m.m03 = 0.0f;
		m.m10 = 0.0f;	m.m11 = cosx;	m.m12 = sinx;	m.m13 = 0.0f;
		m.m20 = 0.0f;	m.m21 =-sinx;	m.m22 = cosx;	m.m23 = 0.0f;
		m.m30 = 0.0f;	m.m31 = 0.0f;	m.m32 = 0.0f;	m.m33 = 1.0f;

		//çsóÒÇÃçáê¨
		this.Mult(m);
	}
	
	/********************************************
	 * Çxé≤âÒì]
	 * 
	 * float r ...âÒì]äpÅiíPà ÉâÉWÉAÉìÅj
	 */
	void	RotateY( float r ){
		float siny = (float)Math.sin(r);
		float cosy = (float)Math.cos(r);

		//Yé≤âÒì]
		//| cosY 0 -sinY 0|
		//|    0 1     0 0|
		//| sinY 0  cosY 0|
		//|    0 0     0 1|
		Matrix4D m = new Matrix4D();
		m.m00 = cosy;	m.m01 = 0.0f;	m.m02 =-siny;	m.m03 = 0.0f;
		m.m10 = 0.0f;	m.m11 = 1.0f;	m.m12 = 0.0f;	m.m13 = 0.0f;
		m.m20 = siny;	m.m21 = 0.0f;	m.m22 = cosy;	m.m23 = 0.0f;
		m.m30 = 0.0f;	m.m31 = 0.0f;	m.m32 = 0.0f;	m.m33 = 1.0f;

		//çsóÒÇÃçáê¨
		this.Mult(m);
	}

	/********************************************
	 * Çyé≤âÒì]	
	 * 
	 * float r ...âÒì]äpÅiíPà ÉâÉWÉAÉìÅj
	 */
	void	RotateZ( float r ){
		float sinz = (float)Math.sin(r);
		float cosz = (float)Math.cos(r);	

		//Zé≤âÒì]
		//|  cosZ sinZ 0 0|
		//| -sinZ cosZ 0 0|
		//|     0    0 1 0|
		//|     0    0 0 1|	
		Matrix4D m = new Matrix4D();
		m.m00 = cosz;	m.m01 = sinz;	m.m02 = 0.0f;	m.m03 = 0.0f;
		m.m10 =-sinz;	m.m11 = cosz;	m.m12 = 0.0f;	m.m13 = 0.0f;
		m.m20 =  0.0f;	m.m21 =  0.0f;	m.m22 = 1.0f;	m.m23 = 0.0f;
		m.m30 =  0.0f;	m.m31 =  0.0f;	m.m32 = 0.0f;	m.m33 = 1.0f;

		//çsóÒÇÃçáê¨
		this.Mult(m);
	}

	/********************************************
	 * ïΩçsà⁄ìÆ
	 * 
	 * float dx ...Çwà⁄ìÆó 
	 * float dy ...Çxà⁄ìÆó 
	 * float dz ...Çyà⁄ìÆó 
	 */
	void	Translate( float dx, float dy, float dz ){
		//|  1  0  0  0|
		//|  0  1  0  0|
		//|  0  0  1  0|
		//| dx dy dz  1|
		Matrix4D m = new Matrix4D();
		m.m00 = 1.0f;	m.m01 = 0.0f;	m.m02 = 0.0f;	m.m03 = 0.0f;
		m.m10 = 0.0f;	m.m11 = 1.0f;	m.m12 = 0.0f;	m.m13 = 0.0f;
		m.m20 = 0.0f;	m.m21 = 0.0f;	m.m22 = 1.0f;	m.m23 = 0.0f;
		m.m30 = dx;		m.m31 = dy;		m.m32 = dz;		m.m33 = 1.0f;

		//çsóÒÇÃçáê¨
		this.Mult(m);	
	}

	/********************************************
	 * àÍéüïœä∑
	 * 
	 * Vector a ...ïœä∑Ç∑ÇÈç¿ïW
	 * 
	 * ñﬂílÅFïœä∑Ç≥ÇÍÇΩç¿ïWÇ™ï‘ÇÈÅB
	 */
	public Vector4D	Transform( Vector4D a ){
		Vector4D v = new Vector4D();
		/*
		 * ç∂éËç¿ïWånÇ≈ÇÃïœä∑
		 */
		v.x		= m00 * a.x + m10 * a.y + m20 * a.z + m30 * a.w;
		v.y		= m01 * a.x + m11 * a.y + m21 * a.z + m31 * a.w;
		v.z		= m02 * a.x + m12 * a.y + m22 * a.z + m32 * a.w;
		v.w		= m03 * a.x + m13 * a.y + m23 * a.z + m33 * a.w;
		/*
		 *Å@âEéËç¿ïWånÇ≈ÇÃïœä∑ 
		 */
	/*	v.x		= m00 * a.x + m01 * a.y + m02 * a.z + m03 * a.w;
		v.y		= m10 * a.x + m11 * a.y + m12 * a.z + m13 * a.w;
		v.z		= m20 * a.x + m21 * a.y + m22 * a.z + m23 * a.w;
		v.w		= m30 * a.x + m31 * a.y + m32 * a.z + m33 * a.w;
	*/
		return v;
	}
	
	/********************************************
	 *
	 */
	public String toString(){
		String m0 = "m00="+m00+"\tm01="+m01+"\tm02="+m02+"\tm03="+m03+"\n";
		String m1 = "m10="+m10+"\tm11="+m11+"\tm12="+m12+"\tm13="+m13+"\n";
		String m2 = "m20="+m20+"\tm21="+m21+"\tm22="+m22+"\tm23="+m23+"\n";
		String m3 = "m30="+m30+"\tm31="+m31+"\tm32="+m32+"\tm33="+m33+"\n";
		return m0+m1+m2+m3;
	}
}

/********************************************
 * 
 */
class ModelMatrix extends Matrix4D {
	public ModelMatrix(){
	}
}

/********************************************
 * 
 */
class ViewMatrix extends Matrix4D {
	public Vector4D		vEye, vLookAt, vUp;
	public ViewMatrix( Vector4D Eye, Vector4D LookAt, Vector4D Up ){
		vEye.set( Eye );
		vLookAt.set( LookAt );
		vUp.set( Up );
	}
}

/********************************************
 * 
 */
class ProjectionMatrix extends Matrix4D {
	public ProjectionMatrix( float near, float far, float fov, float aspect ){
	}
}

/********************************************
 * 
 */
class ViewportMatrix extends Matrix4D {
	public ViewportMatrix( float width, float height, float z_max ){
	}
}

/********************************************
 * 
 */
class Camera {
	Vector4D		vecEye;		// eye
	Vector4D		vecLook;	// look
	Vector4D		vecUp;		// Up vector
	Vector4D		vecN;		// n = (eye - look) / |eye - look|
	Vector4D		vecU;		// u = (up x n) / |up x n|
	Vector4D		vecV;		// v = n x u

	public Camera(){
		vecEye		= new Vector4D( 0, 0, 0 );
		vecLook		= new Vector4D( 0, 0, 0 );
		vecUp		= new Vector4D( 0, 1, 0 );
		vecN		= new Vector4D();
		vecU		= new Vector4D();
		vecV		= new Vector4D();
	}

	public void		Eye( float x, float y, float z ){
		vecEye.set( x, y, z );
		Update();
	}

	public void		Look( float x, float y, float z ){
		vecLook.set( x, y, z );
		Update();
	}

	public void		Up( float x, float y, float z ){
		vecUp.set( x, y, z );
		Update();
	}

	public void		LookAt( float eyeX, float eyeY, float eyeZ
						,	float centerX, float centerY, float centerZ
						,	float upX, float upY, float upZ )
	{
		vecEye.set( eyeX, eyeY, eyeZ );
		vecLook.set( centerX, centerY, centerZ );
		vecUp.set( upX, upY, upZ );

		Update();
	}

	private void	Update(){
		vecN.set( vecEye );
		vecN.subtraction( vecLook );
		vecN.dot( 1.0f / vecN.absolute() );

		vecU.set( vecUp );
		vecU.cross( vecN );
		vecU.dot( 1.0f / vecU.absolute() );

		vecV.set( vecN );
		vecV.cross( vecU );
	}
}

/********************************************
 * 
 */
class Edge {
	public int		from = 0,to = 0;
	public Edge( int f, int t ){
		from=f; to=t;
	}
}

/********************************************
 * 
 */
class Face {
	int[]	idx	= null;

	public Face( int v1, int v2, int v3 ){
		idx	= new int[ 3 ];
		idx[ 0 ]	= v1;
		idx[ 1 ]	= v2;
		idx[ 2 ]	= v3;
	}

	public Face( int v1, int v2, int v3, int v4 ){
		idx	= new int[ 4 ];
		idx[ 0 ]	= v1;
		idx[ 1 ]	= v2;
		idx[ 2 ]	= v3;
		idx[ 3 ]	= v4;
	}
}

/********************************************
 * 
 */
class Model {
	Vector4D		Vertexs[]	= null;
	int				Index[]	= null;
	Edge			edges[]	= null;
	Face			faces[]	= null;
	Vector4D		TranslatedVertexs[]	= null;

	public String	toString(){
		String	strA = "[í∏ì_ÉfÅ[É^]\n";
		for( int i=0; i<Vertexs.length; i++ ){
			strA += Vertexs[ i ].toString();
		}

		String	strB = "[í∏ì_ÉCÉìÉfÉbÉNÉX]\n";
		for( int i=0; i<Index.length; i++ ){
			strB += (" " + Index[ i ] +  ",");
		}
		strB += "\n";

		if( TranslatedVertexs != null ){		
			String	strC = "[ç¿ïWïœä∑å„ÇÃí∏ì_ÉfÅ[É^]\n";
			for( int i=0; i<TranslatedVertexs.length; i++ ){
				strC += TranslatedVertexs[ i ].toString();
			}

			return(strA + strB + strC);
		}

		return(strA + strB);
	}

	public Model(){}

	protected void		set( Vector4D[] v, int[] idx ){
		this.Vertexs			= v;
		this.Index				= idx;
		this.TranslatedVertexs	= new Vector4D[ v.length ];
	}

	protected void		set( Vector4D[] v, Face[] f ){
		this.Vertexs			= v;
		this.faces				= f;
		this.TranslatedVertexs	= new Vector4D[ v.length ];
	}

	protected void		set( Vector4D[] v, Edge[] e ){
		this.Vertexs			= v;
		this.edges				= e;
		this.TranslatedVertexs	= new Vector4D[ v.length ];
	}

	public void		createAxis(){
		Vector4D	v[] = new Vector4D[ 6 ];
		v[ 0 ]	= new Vector4D(  1, 0, 0 );
		v[ 1 ]	= new Vector4D( -1, 0, 0 );
		v[ 2 ]	= new Vector4D(  0, 1, 0 );
		v[ 3 ]	= new Vector4D(  0,-1, 0 );
		v[ 4 ]	= new Vector4D(  0, 0, 1 );
		v[ 5 ]	= new Vector4D(  0, 0,-1 );
		
		Edge	e[] = new Edge[ 3 ];
		e[ 0 ]	= new Edge( 0, 1 );
		e[ 1 ]	= new Edge( 2, 3 );
		e[ 2 ]	= new Edge( 4, 5 );

		set( v, e );
	}

	public void		createTriangle(){
		Vector4D	v[] = new Vector4D[ 3 ];
		v[ 0 ]	= new Vector4D( -1, -1, 0 );	// new Vertex( new Vector(-1f, -1f, 0f), new Vector(0f, 1f, 0f), 0f, 1f, 0 );
		v[ 1 ]	= new Vector4D( +1, -1, 0 );	// new Vertex( new Vector(+1f, -1f, 0f), new Vector(0f, 1f, 0f), 1f, 1f, 0 );
		v[ 2 ]	= new Vector4D(  0, +1, 0 );	// new Vertex( new Vector( 0f, +1f, 0f), new Vector(0f, 1f, 0f), 1f, 0f, 0 );

		int i[] = new int[ 3 ];
		i[ 0 ]	= 0;
		i[ 1 ]	= 2;
		i[ 2 ]	= 1;

		this.set( v, i );
	}

	public void		createCube(){
		Vector4D	v[]	= new Vector4D[ 8 ];
		v[ 0 ]	= new Vector4D( -1, -1, -1 );
		v[ 1 ]	= new Vector4D( +1, -1, -1 );
		v[ 2 ]	= new Vector4D( +1, +1, -1 );
		v[ 3 ]	= new Vector4D( -1, +1, -1 );
		
		v[ 4 ]	= new Vector4D( -1, -1, +1 );
		v[ 5 ]	= new Vector4D( +1, -1, +1 );
		v[ 6 ]	= new Vector4D( +1, +1, +1 );
		v[ 7 ]	= new Vector4D( -1, +1, +1 );

		Face		f[]	= new Face[ 4 ];
		f[ 0 ]	= new Face( 0, 1, 2, 3 );
		f[ 1 ]	= new Face( 4, 5, 6, 7 );
		f[ 2 ]	= new Face( 1, 5, 6, 2 );
		f[ 3 ]	= new Face( 0, 4, 7, 3 );

	//	this.set( v, i );
	//	this.Vertexs			= v;
	//	this.faces				= f;
	//	this.TranslatedVertexs	= new Vector4D[ v.length ];
		set( v, f );
	}

	public void		createPlane( int n ){
		int	num = n + 1;

		Vector4D	v[]	= new Vector4D[ n * n ];

		float	pz	= -1.0f;
		float	px	= 0;
		float	add	= 2.0f / n;

		int	p = 0;
		for( int z=0; z<n; z++, pz+=add )
		{
			px = -1.0f;
			for( int x=0; x<n; x++, px+=add )
			{
				v[ p ]	= new Vector4D( px, 0f, pz );
				p++;
			}
		}

		// UVç¿ïW(ÉeÉNÉXÉ`ÉÉç¿ïW)
	//	for( int i=0; i<v.length; i++ )
	//	{
	//		v[ i ].setUV( (v[ i ].x / 2 + 0.5f), 1.0f - (v[ i ].z / 2 + 0.5f) );
	//	}

		int	idx[] = new int[ n * n * 6 ];
		
		p = 0;
		int	q	= 0;
		for( int z=0; z<n; z++ )
		{
			for( int x=0; x<n; x++ )
			{
				idx[ p++ ]	= z * (n + 1) + x + 0;
				idx[ p++ ]	= z * (n + 1) + x + 1;
				idx[ p++ ]	= (z + 1) * (n + 1) + x + 1;

				q++;

				idx[ p++ ]	= (z + 1) * (n + 1) + x + 1;
				idx[ p++ ]	= (z + 1) * (n + 1) + x + 0;
				idx[ p++ ]	= z * (n + 1) + x;

				q++;
			}
		}

		this.set( v, idx );
	}
}

/********************************************
 * 
 */
class World {
//	Camera			camara	= new Camera();
//	Model[]			modele;

	public Model			axis		= null;
	public Model			cube		= null;
	public Model			traiangle	= null;
	public Model			plane		= null;

	Matrix4D	mtxWorld	= new Matrix4D();
	Matrix4D	mtxView		= new Matrix4D();
	Matrix4D	mtxProj		= new Matrix4D();
	Matrix4D	mtxViewport	= new Matrix4D();
	Matrix4D	mtxMaster	= new Matrix4D();


	public int		azimuth		= 0;		// ï˚à äp 
	public int		elevation	= 10;		// çÇÇ≥ 

	public World(){
		axis		= new Model();
		axis.createAxis();

		traiangle	= new Model();
		traiangle.createTriangle();
	
		plane		= new Model();
		plane.createPlane(8);

		cube		= new Model();
		cube.createCube();
	}

	public void		MakeViewMatrix( Vector4D eye ){
		Vector4D	vEye	= new Vector4D( eye );
		vEye.Normalize();

		Vector4D	vLookAt	= new Vector4D( vEye.x, vEye.y - 0.1f, vEye.z - 1.0f );
		Vector4D	vUp		= new Vector4D( 0, 1, 0 );
		vUp.Normalize();

		vEye.set( eye );

		// w = LookAt - Eye
		Vector4D	w	= new Vector4D( vLookAt );
		w.subtraction( vEye );
		w.Normalize();

		// u = up x w
		Vector4D	u	= new Vector4D( vUp );
		u.cross( w );
		u.Normalize();

		// v = w  x u
		Vector4D	v	= new Vector4D( w );
		v.cross( u );
		v.Normalize();


		System.out.println( "vEye: " + vEye.toString() );
		System.out.println( "vLookAt: " + vLookAt.toString() );
		System.out.println( "vUp: " + vUp.toString() );
		System.out.println( "w: " + w.toString() );
		System.out.println( "u: " + u.toString() );
		System.out.println( "v: " + v.toString() );

		// x = -(eye . u)
		// y = -(eye . v)
		// z = -(eye . w)
		Vector4D	d	= new Vector4D();
		d.x		= -vEye.dot( u );
		d.y		= -vEye.dot( v );
		d.z		= -vEye.dot( w );

		mtxView.Zero();
		mtxView.Set(		u.x		,	v.x		,	w.x		,	0.0f
						,	u.y		,	v.y		,	w.y		,	0.0f
						,	u.z		,	v.z		,	w.z		,	0.0f
						,	d.x		,	d.y		,	d.z		,	1.0f	);

		System.out.println( "[View Matrix]\n" + mtxView.toString() );
	}

	public void		MakeProjectionMatrix( float near, float far, double fov, float aspect ){
		float	tan	= (float)Math.tan( fov * 0.5f );
		
		float	w	= 1.0f / (tan * aspect);
		float	h	= 1.0f / tan;
		float	q	= far  / (far - near);

		mtxProj.Zero();
		mtxProj.Set(		w		,	0.0f	,	0.0f	,	0.0f
						,	0.0f	,	h		,	0.0f	,	0.0f
						,	0.0f	,	0.0f	,	q		,	1.0f
						,	0.0f	,	0.0f	,	-q * near,	0.0f	);

		System.out.println( "[Proj Matrix]\n" + mtxProj.toString() );
	}

	public void		MakeViewportMatrix( float fWidth, float fHeight, float fDepth ){
		float	sx	= fWidth  * 0.5f;
		float	sy	= fHeight * 0.5f;
		float	sz	= fDepth;

		mtxViewport.Zero();
		mtxViewport.Set(	sx		,	0.0f	,	0.0f	,	0.0f
						,	0.0f	,	-sy		,	0.0f	,	0.0f
						,	0.0f	,	0.0f	,	sz		,	0.0f
						,	sx		,	sy		,	0.0f	, 	1.0f	);

		System.out.println( "[Viewport Matrix]\n" + mtxViewport.toString() );
	}

	public void		Render( Graphics g, Rect rc ){
		Parameter	param = Parameter.getInstance();
		
		MakeViewMatrix( param.vEye );
//		MakeProjectionMatrix( 100.0f, 100000.0f, (float)Math.PI * 0.25f, 1.0f );
		MakeProjectionMatrix( param.fNear, param.fFar, param.fFov, param.fAspect );
		MakeViewportMatrix( rc.Width(), rc.Height(), param.fDepth );

		mtxWorld.Identity();
		mtxWorld.Scale( 400, 400, 400 );
		mtxWorld.RotateX( 0 );
		mtxWorld.RotateY( 0 );
		mtxWorld.RotateZ( 0 );
		mtxWorld.Translate( 0,-80, 0 );

		System.out.println( "[World Matrix]\n" + mtxWorld.toString() );

//		GeometricalTransformation( plane );

//		System.out.println( "[Plane]\n" + plane.toString() );

//		Draw( g, plane );
		
		mtxWorld.Identity();
//		mtxWorld.Scale( 150, 150, 150 );
//		mtxWorld.RotateX( (float)(Math.PI * azimuth   / 180.0) );
//		mtxWorld.RotateY( (float)(Math.PI * elevation / 180.0) );
//		mtxWorld.RotateZ( 0 );
		mtxWorld.Scale( param.vScale.x, param.vScale.y, param.vScale.z );
		mtxWorld.RotateX( param.vRotation.x );
		mtxWorld.RotateY( param.vRotation.y );
		mtxWorld.RotateZ( param.vRotation.z );
		mtxWorld.Translate( param.vTranslate.x, param.vTranslate.y, param.vTranslate.z );

		System.out.println( "[World Matrix]\n" + mtxWorld.toString() );

		GeometricalTransformation( axis );
		DrawEdge( g, axis );

		GeometricalTransformation( cube );
		DrawFace( g, cube );
//		System.out.println( "[Traiangle]\n" + traiangle.toString() );

//		Draw( g, traiangle );

	}

	public void		GeometricalTransformation( Model model ){
		mtxMaster.Set( mtxWorld );
		mtxMaster.Mult( mtxView );
		mtxMaster.Mult( mtxProj );
		mtxMaster.Mult( mtxViewport );

		System.out.println( "[Master Matrix]\n" + mtxMaster.toString() );

		Vector4D	tp = new Vector4D();

		for( int i=0; i<model.Vertexs.length; i++ )
		{
			model.TranslatedVertexs[ i ]	= new Vector4D();

			System.out.println( "From:" +  model.Vertexs[ i ].toString() );

			tp	= mtxMaster.Transform( model.Vertexs[ i ] );

			float wp	= 1.0f / tp.w;
			model.TranslatedVertexs[ i ].x	= (int)(tp.x * wp);
			model.TranslatedVertexs[ i ].y	= (int)(tp.y * wp);
			model.TranslatedVertexs[ i ].w	= wp;

			System.out.println( "To:" + model.TranslatedVertexs[ i ].toString() );
		}		
	}

	public void		Draw( Graphics g,  Model model ){
		
		int		idx1, idx2, idx3;
		Point	pt1	= new Point();
		Point	pt2	= new Point();
		Point	pt3	= new Point();
	
		for( int i=0; i<model.Index.length; i+=3 ){
			idx1	= model.Index[ i + 0 ];
			idx2	= model.Index[ i + 1 ];
			idx3	= model.Index[ i + 2 ];

			pt1.x	= (int)model.TranslatedVertexs[ idx1 ].x;
			pt1.y	= (int)model.TranslatedVertexs[ idx1 ].y;

			pt2.x	= (int)model.TranslatedVertexs[ idx2 ].x;
			pt2.y	= (int)model.TranslatedVertexs[ idx2 ].y;

			pt3.x	= (int)model.TranslatedVertexs[ idx3 ].x;
			pt3.y	= (int)model.TranslatedVertexs[ idx3 ].y;

			g.setColor( Color.WHITE );
			g.drawLine( pt1.x, pt1.y, pt2.x, pt2.y );
			g.drawLine( pt1.x, pt1.y, pt3.x, pt3.y );
			g.drawLine( pt2.x, pt2.y, pt3.x, pt3.y );
		}
	}

	public void		DrawEdge( Graphics g, Model model ){
		g.setColor( Color.BLUE );

		for( int i=0; i<model.edges.length; i++ )
		{
			int i0 = model.edges[ i ].from;
			int i1 = model.edges[ i ].to;
			
			g.drawLine( (int)model.TranslatedVertexs[ i0 ].x, (int)model.TranslatedVertexs[ i0 ].y
					,	(int)model.TranslatedVertexs[ i1 ].x, (int)model.TranslatedVertexs[ i1 ].y );
		}
	}

	public void		DrawFace( Graphics g, Model model ){
		g.setColor( Color.WHITE );

		for( int i=0; i<model.faces.length; i++ )
		{
			if( model.faces[ i ].idx.length == 3 )
			{
				int	i0 = model.faces[ i ].idx[ 0 ];
				int	i1 = model.faces[ i ].idx[ 1 ];
				int	i2 = model.faces[ i ].idx[ 2 ];

				g.drawLine(	(int)model.TranslatedVertexs[ i0 ].x, (int)model.TranslatedVertexs[ i0 ].y
						,	(int)model.TranslatedVertexs[ i1 ].x, (int)model.TranslatedVertexs[ i1 ].y );
				g.drawLine(	(int)model.TranslatedVertexs[ i0 ].x, (int)model.TranslatedVertexs[ i0 ].y
						,	(int)model.TranslatedVertexs[ i2 ].x, (int)model.TranslatedVertexs[ i2 ].y );
				g.drawLine(	(int)model.TranslatedVertexs[ i1 ].x, (int)model.TranslatedVertexs[ i1 ].y
						,	(int)model.TranslatedVertexs[ i2 ].x, (int)model.TranslatedVertexs[ i2 ].y );
			}
			else
			if( model.faces[ i ].idx.length == 4 )
			{
				int	i0 = model.faces[ i ].idx[ 0 ];
				int	i1 = model.faces[ i ].idx[ 1 ];
				int	i2 = model.faces[ i ].idx[ 2 ];
				int	i3 = model.faces[ i ].idx[ 3 ];

				g.drawLine(	(int)model.TranslatedVertexs[ i0 ].x, (int)model.TranslatedVertexs[ i0 ].y
						,	(int)model.TranslatedVertexs[ i1 ].x, (int)model.TranslatedVertexs[ i1 ].y );
				g.drawLine(	(int)model.TranslatedVertexs[ i1 ].x, (int)model.TranslatedVertexs[ i1 ].y
						,	(int)model.TranslatedVertexs[ i2 ].x, (int)model.TranslatedVertexs[ i2 ].y );
				g.drawLine(	(int)model.TranslatedVertexs[ i2 ].x, (int)model.TranslatedVertexs[ i2 ].y
						,	(int)model.TranslatedVertexs[ i3 ].x, (int)model.TranslatedVertexs[ i3 ].y );
				g.drawLine(	(int)model.TranslatedVertexs[ i3 ].x, (int)model.TranslatedVertexs[ i3 ].y
						,	(int)model.TranslatedVertexs[ i0 ].x, (int)model.TranslatedVertexs[ i0 ].y );
			}
		}
		
		g.setColor( Color.YELLOW );
		for( int i=0; i<model.TranslatedVertexs.length; i++ )
		{
			String	str = "["+i+"]";
			g.drawString( str, (int)model.TranslatedVertexs[ i ].x, (int)model.TranslatedVertexs[ i ].y );
		}
	}
}

/********************************************
 * 
 */
enum ParamType {
	UNDEFIND,

	EYE_X, 
	EYE_Y, 
	EYE_Z,

	LOOKAT_X, 
	LOOKAT_Y, 
	LOOKAT_Z,

	UP_X, 
	UP_Y, 
	UP_Z,

	NEAR, 
	FAR, 
	FOV, 
	ASPECT,

	WIDTH, 
	HEIGHT, 
	DEPTH,

	YAW, 
	ROLLING, 
	PITCH,

	SCALE_X,
	SCALE_Y,
	SCALE_Z,

	ROTATION_X,
	ROTATION_Y,
	ROTATION_Z,

	TRANSLATE_X,
	TRANSLATE_Y,
	TRANSLATE_Z,
	
	AZIMUTH,
	ELEVATION,
}

/********************************************
 * 
 */
class Parameter implements ChangeListener {
	private static Parameter		instance	= new Parameter();
	
	private Parameter(){}
	
	public static Parameter	getInstance(){
		return instance;
	}
	
	public JPanel			Panel		= null;
	public Vector4D			vEye		= new Vector4D();
	public Vector4D			vLookAt		= new Vector4D();
	public Vector4D			vUp			= new Vector4D();
	public float			fNear		= 0.0f;
	public float			fFar		= 0.0f;
	public float			fFov		= 0.0f;
	public float			fAspect		= 0.0f;
	public int				nWidth		= 0;
	public int				nHeight		= 0;
	public float			fDepth		= 0.0f;
	public float			fYaw		= 0.0f;
	public float			fRolling	= 0.0f;
	public float			fPitch		= 0.0f;
	public Vector4D			vScale		= new Vector4D();
	public Vector4D			vRotation	= new Vector4D();
	public Vector4D			vTranslate	= new Vector4D();
	public int				azimuth		= 0;
	public int				elevation	= 0;

	private boolean			bUpdate		= true;
	
	private Map<ParamType, PrimitiveCtrl>	mapObject	= new HashMap<ParamType, PrimitiveCtrl>();

	public void		bind( ParamType type, SpinCtrl spin ){
		spin.setId( type );
		spin.addChangeListener( this );
		mapObject.put( type, spin );
	}
	
	public void		stateChanged( ChangeEvent event ){
		System.out.println( "stateChanged" );

		if( bUpdate )
			updateUIfromCtrl();
		
		if( Panel != null )
			Panel.repaint();
	}
	
	public void		InitValue(){
		vEye		.set( 0, 256, -1024 );
		vLookAt		.set( 0, 0.14253563f, -1.9701426f );
		vUp			.set( 0, 1, 0 );
		vScale		.set( 150, 150, 150 );
		vRotation	.set( 0, 0, 0 );
		vTranslate	.set( 0, 0, 0 );
		
		nHeight		= 600;
		nWidth		= 800;
		fDepth		= 16834.0f;
		
		fNear		= 100.0f;
		fFar		= 100000.0f;
		fFov		= (float)Math.PI * 0.25f;
		fAspect		= 1.0f;

		fYaw 		= vRotation.x;
		fRolling	= vRotation.y;
		fPitch		= vRotation.z;
		
		updateUIfromValue();
	}
	
	public void		updateUIfromCtrl(){
		for( Map.Entry<ParamType, PrimitiveCtrl> e : mapObject.entrySet() ){
			switch( e.getKey() )
			{
			case EYE_X:
				vEye.x			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case EYE_Y:
				vEye.y			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case EYE_Z:
				vEye.z			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;

			case LOOKAT_X:
				vLookAt.x		= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case LOOKAT_Y:
				vLookAt.y		= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case LOOKAT_Z:
				vLookAt.z		= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;

			case UP_X:
				vUp.x			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case UP_Y:
				vUp.y			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case UP_Z:
				vUp.z			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;

			case NEAR:
				fNear			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case FAR: 
				fFar			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case FOV: 
				fFov			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case ASPECT:
				fAspect			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;

			case WIDTH:
				nWidth			= ((SpinCtrl)e.getValue()).getIntValue();
				break;
			case HEIGHT: 
				nHeight			= ((SpinCtrl)e.getValue()).getIntValue();
				break;
			case DEPTH:
				fDepth			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;

			case YAW: 
				fYaw			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case ROLLING: 
				fRolling		= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case PITCH:
				fPitch			= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
				
			case SCALE_X:
				vScale.x		= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case SCALE_Y:
				vScale.y		= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case SCALE_Z:
				vScale.z		= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
				
			case ROTATION_X:
				vRotation.x		= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case ROTATION_Y:
				vRotation.y		= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case ROTATION_Z:
				vRotation.z		= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
				
			case TRANSLATE_X:
				vTranslate.x	= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case TRANSLATE_Y:
				vTranslate.y	= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			case TRANSLATE_Z:
				vTranslate.z	= (float)((SpinCtrl)e.getValue()).getDoubleValue();
				break;
			
			case AZIMUTH:
				azimuth			= ((SpinCtrl)e.getValue()).getIntValue();
				break;
			case ELEVATION:
				elevation		= ((SpinCtrl)e.getValue()).getIntValue();
				break;
			}
		}
	}
	
	public void		updateUIfromValue(){
		bUpdate	= false;
		
		for( Map.Entry<ParamType, PrimitiveCtrl> e : mapObject.entrySet() ){
			switch( e.getKey() )
			{
			case EYE_X:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vEye.x);
				break;
			case EYE_Y:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vEye.y);
				break;
			case EYE_Z:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vEye.z);
				break;

			case LOOKAT_X:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vLookAt.x);
				break;
			case LOOKAT_Y:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vLookAt.y);
				break;
			case LOOKAT_Z:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vLookAt.z);
				break;

			case UP_X:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vUp.x);
				break;
			case UP_Y:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vUp.y);
				break;
			case UP_Z:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vUp.z);
				break;

			case NEAR:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)fNear);
				break;
			case FAR: 
				((SpinCtrl)e.getValue()).setDoubleValue( (double)fFar);
				break;
			case FOV: 
				((SpinCtrl)e.getValue()).setDoubleValue( (double)fFov);
				break;
			case ASPECT:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)fAspect);
				break;

			case WIDTH:
				((SpinCtrl)e.getValue()).setIntValue(  (int)nWidth );
				break;
			case HEIGHT: 
				((SpinCtrl)e.getValue()).setIntValue(  (int)nHeight );
				break;
			case DEPTH:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)fDepth );
				break;

			case YAW: 
				((SpinCtrl)e.getValue()).setDoubleValue( (double)fYaw );
				break;
			case ROLLING: 
				((SpinCtrl)e.getValue()).setDoubleValue( (double)fRolling );
				break;
			case PITCH:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)fPitch );
				break;

			case SCALE_X:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vScale.x );
				break;
			case SCALE_Y:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vScale.y );
				break;
			case SCALE_Z:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vScale.z );
				break;
				
			case ROTATION_X:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vRotation.x );
				break;
			case ROTATION_Y:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vRotation.y );
				break;
			case ROTATION_Z:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vRotation.z );
				break;
				
			case TRANSLATE_X:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vTranslate.x );
				break;
			case TRANSLATE_Y:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vTranslate.y );
				break;
			case TRANSLATE_Z:
				((SpinCtrl)e.getValue()).setDoubleValue( (double)vTranslate.z );
				break;
				
			case AZIMUTH:
				((SpinCtrl)e.getValue()).setIntValue( azimuth );
				break;
			case ELEVATION:
				((SpinCtrl)e.getValue()).setIntValue( elevation );
				break;
			}
		}
		bUpdate	= true;
	}
}

/********************************************
 * 
 */
class PrimitiveCtrl {
	protected ParamType	ID = ParamType.UNDEFIND;
	
	public PrimitiveCtrl(){}
	
	public ParamType		getId(){
		return ID;
	}
	
	public void		setId( ParamType nId ){
		ID = nId;
	}
}

/********************************************
 * 
 */
class SpinCtrl extends PrimitiveCtrl {
	final public JLabel				label	= new JLabel();
	final public JSpinner			spin	= new JSpinner();
	protected SpinnerNumberModel	model	= null;
	
	public SpinCtrl(){
		Dimension dim = new Dimension(90, 20);
		spin.setPreferredSize( dim );
	//	spin.setMaximumSize( dim );
	//	spin.setMinimumSize( dim );
	}
	
	public SpinCtrl( String str, SpinnerNumberModel model ){
		this();
		label.setText( str );
		spin.setModel( model );
		this.model = model;
	}
	
	public SpinCtrl( SpinnerNumberModel model ){
		spin.setModel( model );
		this.model = model;
	}
	
	public void		setText( String str ){
		label.setText( str );
	}
	
	public void		setModel( int value, int min, int max, int step ){
		model = new SpinnerNumberModel( value, min, max, step );
		spin.setModel( model );
	}
	
	public void		setModel( double value, double min, double max, double step ){
		model = new SpinnerNumberModel( value, min, max, step );
		spin.setModel( model );
	}
	
	public void		setEditor( String str ){
		spin.setEditor( new JSpinner.NumberEditor( spin, str ) );
	}
		
	public void		add( JPanel panel ){
		panel.add( label );
		panel.add( spin );
	}
	
	public void		addChangeListener( ChangeListener listener ){
		spin.addChangeListener( listener );
	}
	
	public int		getIntValue(){
		if( model != null ){
			Integer value = (Integer)model.getValue();
			return value.intValue();
		}
		return 0;
	}
	
	public double	getDoubleValue(){
		if( model != null ){
			Double value = (Double)model.getValue();
			return value.doubleValue();
		}
		return 0.0;
	}
	
	public void		setIntValue( int value ){
		if( model != null ){
			model.setValue( new Integer(value) );
		}
	}
	
	public void		setDoubleValue( double value ){
		if( model != null ){
			model.setValue( new Double(value) );
		}
	}
}

/********************************************
 * 
 */
class VectorPanel extends JPanel {
	final public SpinCtrl	spinX	= new SpinCtrl( "X"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	final public SpinCtrl	spinY	= new SpinCtrl( "Y"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	final public SpinCtrl	spinZ	= new SpinCtrl( "Z"	, new SpinnerNumberModel(100.0, null, null, 0.25) );

	public VectorPanel(){
		spinX.setEditor( "###0.000" );
		spinY.setEditor( "###0.000" );
		spinZ.setEditor( "###0.000" );
		
		setLayout();
	}
	
	public VectorPanel( String strTitle ){
		this();

		setBorder( new TitledBorder( strTitle ) );
		setLayout();
	}
	
	public void	setLayout(){
		GroupLayout layout = new GroupLayout( this );
		setLayout( layout );
	
		GroupLayout.SequentialGroup	hGroup	= layout.createSequentialGroup();
		hGroup.addGroup( layout.createParallelGroup().addComponent( spinX.label ).addComponent( spinY.label ).addComponent( spinZ.label ) );
		hGroup.addGroup( layout.createParallelGroup().addComponent( spinX.spin ).addComponent( spinY.spin ).addComponent( spinZ.spin ) );
		layout.setHorizontalGroup( hGroup );

		GroupLayout.SequentialGroup	vGroup	= layout.createSequentialGroup();
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinX.label ).addComponent( spinX.spin ) );
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinY.label ).addComponent( spinY.spin ) );
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinZ.label ).addComponent( spinZ.spin ) );
		layout.setVerticalGroup( vGroup );

		spinX.add( this );
		spinY.add( this );
		spinZ.add( this );
	}
}

/********************************************
 * 
 */
class CameraPanel extends JPanel {
	final protected VectorPanel		panelEye		= new VectorPanel( "Eye" );
	final protected VectorPanel		panelLookAt		= new VectorPanel( "LookAt" );
	final protected VectorPanel		panelUp			= new VectorPanel( "Up" );
	
	public CameraPanel( String strTitle ){
		setBorder( new TitledBorder( strTitle ) );
		
		setLayout( new BoxLayout(this, BoxLayout.LINE_AXIS) );

		add( panelEye );
		add( panelLookAt );
		add( panelUp );

		Parameter.getInstance().bind( ParamType.EYE_X, panelEye.spinX );
		Parameter.getInstance().bind( ParamType.EYE_Y, panelEye.spinY );
		Parameter.getInstance().bind( ParamType.EYE_Z, panelEye.spinZ );

		Parameter.getInstance().bind( ParamType.LOOKAT_X, panelLookAt.spinX );
		Parameter.getInstance().bind( ParamType.LOOKAT_Y, panelLookAt.spinY );
		Parameter.getInstance().bind( ParamType.LOOKAT_Z, panelLookAt.spinZ );

		Parameter.getInstance().bind( ParamType.UP_X, panelUp.spinX );
		Parameter.getInstance().bind( ParamType.UP_Y, panelUp.spinY );
		Parameter.getInstance().bind( ParamType.UP_Z, panelUp.spinZ );
	}
}

/********************************************
 * 
 */
class ModelPanel extends JPanel {
	final protected VectorPanel		panelScale		= new VectorPanel( "Scale" );
	final protected VectorPanel		panelRotation	= new VectorPanel( "Rotation" );
	final protected VectorPanel		panelTranslate	= new VectorPanel( "Translate" );
	
	public ModelPanel( String strTitle ){
		setBorder( new TitledBorder( strTitle ) );
		
		setLayout( new BoxLayout(this, BoxLayout.LINE_AXIS) );

		add( panelScale );
		add( panelRotation );
		add( panelTranslate );

		Parameter.getInstance().bind( ParamType.SCALE_X, panelScale.spinX );
		Parameter.getInstance().bind( ParamType.SCALE_Y, panelScale.spinY );
		Parameter.getInstance().bind( ParamType.SCALE_Z, panelScale.spinZ );

		Parameter.getInstance().bind( ParamType.ROTATION_X, panelRotation.spinX );
		Parameter.getInstance().bind( ParamType.ROTATION_Y, panelRotation.spinY );
		Parameter.getInstance().bind( ParamType.ROTATION_Z, panelRotation.spinZ );

		Parameter.getInstance().bind( ParamType.TRANSLATE_X, panelTranslate.spinX );
		Parameter.getInstance().bind( ParamType.TRANSLATE_Y, panelTranslate.spinY );
		Parameter.getInstance().bind( ParamType.TRANSLATE_Z, panelTranslate.spinZ );
	}
}

/********************************************
 * 
 */
class ProjectionPanel extends JPanel {
	// float near, float far, double fov, float aspect 
	final protected SpinCtrl	spinNear	= new SpinCtrl( "Near"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	final protected SpinCtrl	spinFar		= new SpinCtrl( "Far"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	final protected SpinCtrl	spinFov		= new SpinCtrl( "Fov"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	final protected SpinCtrl	spinAspect	= new SpinCtrl( "Aspect", new SpinnerNumberModel(100.0, null, null, 0.25) );

	public ProjectionPanel(){
		spinNear.setEditor( "###0.000");
		spinFar.setEditor( "###0.000" );
		spinFov.setEditor( "###0.000" );
		spinAspect.setEditor( "###0.000" );
		
		setLayout();
	}
	
	public ProjectionPanel( String strTitle ){
		this();
		setBorder( new TitledBorder( strTitle ) );
		
		setLayout();
	}
	
	public void	setLayout(){
		GroupLayout layout = new GroupLayout( this );
		setLayout( layout );
	
		GroupLayout.SequentialGroup	hGroup	= layout.createSequentialGroup();
		hGroup.addGroup( layout.createParallelGroup().addComponent( spinNear.label ).addComponent( spinFar.label ).addComponent( spinFov.label ).addComponent( spinAspect.label ) );
		hGroup.addGroup( layout.createParallelGroup().addComponent( spinNear.spin ).addComponent( spinFar.spin ).addComponent( spinFov.spin ).addComponent( spinAspect.spin ) );
		layout.setHorizontalGroup( hGroup );

		GroupLayout.SequentialGroup	vGroup	= layout.createSequentialGroup();
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinNear.label ).addComponent( spinNear.spin ) );
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinFar.label ).addComponent( spinFar.spin ) );
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinFov.label ).addComponent( spinFov.spin ) );
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinAspect.label ).addComponent( spinAspect.spin ) );
		layout.setVerticalGroup( vGroup );

		spinNear.add( this );
		spinFar.add( this );
		spinFov.add( this );
		spinAspect.add( this );

		Parameter.getInstance().bind( ParamType.NEAR, spinNear );
		Parameter.getInstance().bind( ParamType.FAR, spinFar );
		Parameter.getInstance().bind( ParamType.FOV, spinFov );
		Parameter.getInstance().bind( ParamType.ASPECT, spinAspect );
	}
}

/********************************************
 * 
 */
class TitlePanel extends JPanel {
	public TitlePanel( String strTitle ){
		setBorder( new TitledBorder( strTitle ) );
	}
}

/********************************************
 * 
 */
class ViewportPanel extends TitlePanel {
	final protected SpinCtrl	spinWidth	= new SpinCtrl( "Width" , new SpinnerNumberModel(1024, null, null, 1) );
	final protected SpinCtrl	spinHeigth	= new SpinCtrl( "Height", new SpinnerNumberModel(1024, null, null, 1) );
	final protected SpinCtrl	spinDepth	= new SpinCtrl( "Depth" , new SpinnerNumberModel(1.0, null, null, 0.25) );
	
	public ViewportPanel( String strTitle ){
		super( strTitle );
		
		spinWidth.add( this );
		spinHeigth.add( this );
		spinDepth.add( this );
		
		spinWidth.setEditor( "####" );
		spinHeigth.setEditor( "####" );
		spinDepth.setEditor( "###0.000" );

		Parameter.getInstance().bind( ParamType.WIDTH, spinWidth );
		Parameter.getInstance().bind( ParamType.HEIGHT, spinHeigth );
		Parameter.getInstance().bind( ParamType.DEPTH, spinDepth );
	}
}

/********************************************
 * 
 */
class PitchingPanel extends TitlePanel {
	final protected SpinCtrl	spinYaw		= new SpinCtrl( "Yaw" 		, new SpinnerNumberModel(1.0, 0.0, 1.0, 0.1) );
	final protected SpinCtrl	spinRolling	= new SpinCtrl( "Rolling"	, new SpinnerNumberModel(1.0, 0.0, 1.0, 0.1) );
	final protected SpinCtrl	spinPitch	= new SpinCtrl( "Pitch" 	, new SpinnerNumberModel(1.0, 0.0, 1.0, 0.1) );
	
	public PitchingPanel( String strTitle ){
		super( strTitle );
		
		spinYaw.add( this );
		spinRolling.add( this );
		spinPitch.add( this );
		
		spinYaw.setEditor( "0.0" );
		spinRolling.setEditor( "0.0" );
		spinPitch.setEditor( "0.0" );

		Parameter.getInstance().bind( ParamType.YAW, spinYaw );
		Parameter.getInstance().bind( ParamType.ROLLING, spinRolling );
		Parameter.getInstance().bind( ParamType.PITCH, spinPitch );
	}
}

/********************************************
 * 
 */
class PlanePanel extends TitlePanel {
	final public SpinCtrl	spinAzimuth		= new SpinCtrl( "Azimuth"	, new SpinnerNumberModel(0,null,null,1) );
	final public SpinCtrl	spinElevation	= new SpinCtrl( "Elevation"	, new SpinnerNumberModel(0,null,null,1) );

	public PlanePanel( String strTitle ){
		super( strTitle );
		
		spinAzimuth.add( this );
		spinElevation.add( this );
		
		spinAzimuth.setEditor( "####0" );
		spinElevation.setEditor( "####0" );
		
		GroupLayout layout = new GroupLayout( this );
		setLayout( layout );
	
		GroupLayout.SequentialGroup	hGroup	= layout.createSequentialGroup();
		hGroup.addGroup( layout.createParallelGroup().addComponent( spinAzimuth.label ).addComponent( spinElevation.label ) );
		hGroup.addGroup( layout.createParallelGroup().addComponent( spinAzimuth.spin ).addComponent( spinElevation.spin ) );
		layout.setHorizontalGroup( hGroup );

		GroupLayout.SequentialGroup	vGroup	= layout.createSequentialGroup();
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinAzimuth.label ).addComponent( spinAzimuth.spin ) );
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinElevation.label ).addComponent( spinElevation.spin ) );
		layout.setVerticalGroup( vGroup );
		
		Parameter.getInstance().bind( ParamType.AZIMUTH, spinAzimuth );
		Parameter.getInstance().bind( ParamType.ELEVATION, spinElevation );
	}	
}

/********************************************
 * 
 */
class Matrix4x4Panel extends JPanel {
	final protected SpinCtrl[]	spins = new SpinCtrl[ 16 ];
	
	public Matrix4x4Panel( String strTitle ){
		setBorder( new TitledBorder( strTitle ) );
	
		for( int i=0; i<16; i++ ){
			spins[ i ]	= new SpinCtrl( "[" + i + "]" , new SpinnerNumberModel(100.0, 0.0, 999.9, 0.1) );
			spins[ i ].setEditor( "####.000" );
			spins[ i ].add( this );
		}
/*
		GroupLayout layout = new GroupLayout( this );
		setLayout( layout );

		GroupLayout.SequentialGroup	hGroup	= layout.createSequentialGroup();
		GroupLayout.SequentialGroup	vGroup	= layout.createSequentialGroup();
		int idx = 0;
		for( int i=0; i<4; i++ ){
			GroupLayout.ParallelGroup	hParalell	= layout.createParallelGroup();
			GroupLayout.ParallelGroup	vParalell	= layout.createParallelGroup();
			for( int j=0; j<4; j++ ){
				hParalell.addComponent( spins[ idx ] );
				vParalell.
			}
			hGroup.addGroup( hParalell );
		}
		layout.setHorizontalGroup( hGroup );
		

		GroupLayout.SequentialGroup	vGroup	= layout.createSequentialGroup();
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinNear.label ).addComponent( spinNear.spin ) );
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinFar.label ).addComponent( spinFar.spin ) );
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinFov.label ).addComponent( spinFov.spin ) );
		vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinAspect.label ).addComponent( spinAspect.spin ) );
		layout.setVerticalGroup( vGroup );
*/
	}
}

/********************************************
 * 
 */
class ParameterPanel extends JPanel {
	public ParameterPanel(){
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
		add( new PlanePanel( "Plane" ) );
		add( new CameraPanel( "Camera" ) );
		add( new ModelPanel( "Model" ) );
		add( new ProjectionPanel( "Projection" ) );
		add( new ViewportPanel( "Viewport" ) );
		add( new PitchingPanel( "Pitching" ) );
	}
}

/********************************************
 * 
 */
class CanvasPanel extends JPanel implements ComponentListener, MouseListener, MouseMotionListener {
	private boolean 	initialized = false;
	private	Image		memImage;
	private	Graphics	memGraphics;

	public int			nWidth	= 0;
	public int			nHeight = 0;

	public Rect			rect = new Rect();

	World world = new World();

	Point		ptCur	= new Point(0,0);

	public CanvasPanel(){
		Dimension dim = new Dimension(600, 600);
		setPreferredSize( dim );
		setMaximumSize( dim );
		setMinimumSize( dim );
		
		addComponentListener( this );
		addMouseListener( this );
		addMouseMotionListener( this );
	}

	@Override
	public void		componentResized( ComponentEvent e ){
		Resize();
		System.out.println( "componentResize" );
	}
	
	@Override public void		componentHidden( ComponentEvent e ){}
	@Override public void		componentShown( ComponentEvent e ){}
	@Override public void		componentMoved( ComponentEvent e ){}

	@Override
	public void		paintComponent( Graphics g ){
		super.paintComponent( g );
		if( initialized == false ){
			initialize();
		}

		if( Render( memGraphics ) )
		{
			g.drawImage( memImage, 0, 0, this );
		}

		System.out.println( "paintComponent" );
	}

	@Override public void	mouseEntered( MouseEvent e ){}
	@Override public void	mouseExited( MouseEvent e ){}
	@Override public void	mouseClicked( MouseEvent e ){}
	@Override public void	mousePressed( MouseEvent e ){
		ptCur.x	= e.getX();
		ptCur.y	= e.getY();
		e.consume();
	}
	@Override public void	mouseReleased( MouseEvent e ){}
	@Override public void	mouseMoved( MouseEvent e ){}
	@Override public void	mouseDragged( MouseEvent e ){
		int	x	= e.getX();
		int y	= e.getY();

		world.azimuth		-= x - ptCur.x;
		world.elevation		+= y - ptCur.y;
		
		Parameter.getInstance().Panel = this;
		Parameter.getInstance().vRotation.x = (float)(Math.PI * world.azimuth   / 180.0);
		Parameter.getInstance().vRotation.y = (float)(Math.PI * world.elevation / 180.0);
		Parameter.getInstance().azimuth		= world.azimuth;
		Parameter.getInstance().elevation	= world.elevation;
		Parameter.getInstance().updateUIfromValue();

		ptCur.x	= x;
		ptCur.y = y;

		repaint();
		e.consume();
	}

	protected void	initialize(){
		initialized = true;
		Resize();
		
		Parameter.getInstance().InitValue();
	}

	protected boolean	Render( Graphics g ){
		g.setColor( Color.black );
		g.fillRect( 0, 0, nWidth, nHeight );

		world.Render( g, rect );

		return true;
	}

	protected void		Resize(){
		nWidth	= getSize().width;
		nHeight	= getSize().height;

		rect.Set( 0, 0, nHeight, nWidth );
		
		Parameter.getInstance().Panel = this;
		Parameter.getInstance().nHeight	= nHeight;
		Parameter.getInstance().nWidth	= nWidth;
		Parameter.getInstance().updateUIfromValue();
		
		memImage	= createImage( nWidth, nHeight );
		memGraphics	= memImage.getGraphics();
		
		repaint();
	}
}

/********************************************
 * 
 */
class TextPanel extends JScrollPane {
	public	JTextArea	Text	= null;

	public TextPanel(){
		Text	= new JTextArea(50,20);
		setViewportView( Text );
		setPreferredSize( null );
	}
}

/********************************************
 * 
 */
class MainFrame extends JFrame {
	public MainFrame(){

		add( new CanvasPanel()		, BorderLayout.CENTER );
		add( new ParameterPanel()	, BorderLayout.LINE_END );
	//	add( new TextPanel()		, BorderLayout.EAST );
	
		setSize( 800, 600 );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setVisible( true );
	}
}

/********************************************
 * 
 */
public class ThreeDGeometry {
	public static void	main( String[] args ){
		SwingUtilities.invokeLater( new Runnable() {
			public void run(){
				new MainFrame();
			}
		});
	}
}
