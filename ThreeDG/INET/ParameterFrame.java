// ParameterFrame.java 

import	java.util.*;
import	java.lang.Math;
import	java.awt.*;
import	java.awt.event.*;
import	javax.swing.*;
import	javax.swing.border.*;
import	javax.swing.event.*;

class Vector4D {
	static public float		ZERO	= 0.0f;
	static public float		ONE		= 1.0f;
	public float	x, y, z, w ;
	
	public Vector4D( float x, float y, float z, float w ){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4D(float x, float y, float z){
		this(x, y, z, ZERO);
	}
	
	public Vector4D(){
		this(ZERO, ZERO, ZERO, ZERO);
	}
	
	public void		set( float x, float y, float z ){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = ZERO;
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
		spin.setMaximumSize( dim );
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
interface EventHandler {
	abstract	void	setLayout();
}

class TitlePanel extends JPanel {
	public TitlePanel( String strTitle ){
		setBorder( new TitledBorder( strTitle ) );
		
		setAlignmentX( Component.LEFT_ALIGNMENT );

	}
}

/********************************************
 * 
 */
class VectorPanel extends TitlePanel {
	final public SpinCtrl	spinX	= new SpinCtrl( "X"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	final public SpinCtrl	spinY	= new SpinCtrl( "Y"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	final public SpinCtrl	spinZ	= new SpinCtrl( "Z"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	
	public VectorPanel( String strTitle ){
		super( strTitle );

		spinX.setEditor( "###0.000" );
		spinY.setEditor( "###0.000" );
		spinZ.setEditor( "###0.000" );
		
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
class CameraPanel extends TitlePanel {
	final protected VectorPanel		panelEye		= new VectorPanel( "Eye" );
	final protected VectorPanel		panelLookAt		= new VectorPanel( "LookAt" );
	final protected VectorPanel		panelUp			= new VectorPanel( "Up" );
	
	public CameraPanel( String strTitle ){
		super( strTitle );
		
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
class ModelPanel extends TitlePanel {
	final protected VectorPanel		panelScale		= new VectorPanel( "Scale" );
	final protected VectorPanel		panelRotation	= new VectorPanel( "Rotation" );
	final protected VectorPanel		panelTranslate	= new VectorPanel( "Translate" );
	
	public ModelPanel( String strTitle ){
		super( strTitle );
		
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
class ProjectionPanel extends TitlePanel {
	// float near, float far, double fov, float aspect 
	final protected SpinCtrl	spinNear	= new SpinCtrl( "Near"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	final protected SpinCtrl	spinFar		= new SpinCtrl( "Far"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	final protected SpinCtrl	spinFov		= new SpinCtrl( "Fov"	, new SpinnerNumberModel(100.0, null, null, 0.25) );
	final protected SpinCtrl	spinAspect	= new SpinCtrl( "Aspect", new SpinnerNumberModel(100.0, null, null, 0.25) );

	public ProjectionPanel( String strTitle ){
		super( strTitle );
		spinNear.setEditor( "###0.000");
		spinFar.setEditor( "###0.000" );
		spinFov.setEditor( "###0.000" );
		spinAspect.setEditor( "###0.000" );
		
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
class Matrix4x4Panel extends TitlePanel {
	final protected SpinCtrl[]	spins = new SpinCtrl[ 16 ];
	
	public Matrix4x4Panel( String strTitle ){
		super( strTitle );
	
		for( int i=0; i<16; i++ ){
			spins[ i ]	= new SpinCtrl( "[" + i + "]" , new SpinnerNumberModel(100.0, 0.0, 999.9, 0.1) );
			spins[ i ].setEditor( "####.000" );
			spins[ i ].add( this );
		}

		GroupLayout layout = new GroupLayout( this );
		setLayout( layout );

		GroupLayout.SequentialGroup	hGroup	= layout.createSequentialGroup();
		for( int i=0; i<4; i++ ){
			System.out.println( "[" + (4 * i + 0) + "," + (4 * i + 1) + "," + (4 * i + 2) + "," + (4 * i + 3) + "]" );
			hGroup.addGroup( layout.createParallelGroup()
							.addComponent( spins[(4 * i + 0)].label )
							.addComponent( spins[(4 * i + 1)].label )
							.addComponent( spins[(4 * i + 2)].label )
							.addComponent( spins[(4 * i + 3)].label )
			);
			hGroup.addGroup( layout.createParallelGroup()
							.addComponent( spins[(4 * i + 0)].spin )
							.addComponent( spins[(4 * i + 1)].spin )
							.addComponent( spins[(4 * i + 2)].spin )
							.addComponent( spins[(4 * i + 3)].spin )
			);
		}
		layout.setHorizontalGroup( hGroup );
		
		GroupLayout.SequentialGroup	vGroup	= layout.createSequentialGroup();
		for( int i=0; i<4; i++ ){
			System.out.println( "[" + (i + 0 * 4) + "," + (i + 1 * 4) + "," + (i + 2 * 4) + "," + (i + 3 * 4) + "]" );
			vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE )
							.addComponent( spins[(i + 0 * 4)].label )
							.addComponent( spins[(i + 0 * 4)].spin )
							.addComponent( spins[(i + 1 * 4)].label )
							.addComponent( spins[(i + 1 * 4)].spin )
							.addComponent( spins[(i + 2 * 4)].label )
							.addComponent( spins[(i + 2 * 4)].spin )
							.addComponent( spins[(i + 3 * 4)].label )
							.addComponent( spins[(i + 3 * 4)].spin )
			);
		}
		layout.setVerticalGroup( vGroup );

	//	GroupLayout.SequentialGroup	vGroup	= layout.createSequentialGroup();
	//	vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinNear.label ).addComponent( spinNear.spin ) );
	//	vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinFar.label ).addComponent( spinFar.spin ) );
	//	vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinFov.label ).addComponent( spinFov.spin ) );
	//	vGroup.addGroup( layout.createParallelGroup( GroupLayout.Alignment.BASELINE ).addComponent( spinAspect.label ).addComponent( spinAspect.spin ) );
	//	layout.setVerticalGroup( vGroup );
	}
}


/********************************************
 * 
 */
class ParameterFrame extends JFrame {
	public ParameterFrame(){
		JPanel	p = new JPanel();
		p.setLayout( new BoxLayout(p, BoxLayout.Y_AXIS) );
		p.add( new PlanePanel( "Plane" ) );
		p.add( new CameraPanel( "Camera" ) );
		p.add( new ModelPanel( "Model" ) );
		p.add( new ProjectionPanel( "Projection" ) );
		p.add( new ViewportPanel( "Viewport" ) );
		p.add( new PitchingPanel( "Pitching" ) );
		p.add( new Matrix4x4Panel( "hoge"  ) );
		getContentPane().add( p, BorderLayout.CENTER );
		
		setSize( 800, 600 );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setVisible( true );
	}

	public static void	main( String[] args ){
		SwingUtilities.invokeLater( new Runnable() {
			public void	run(){
				new ParameterFrame();
			}
		});
	}
}
