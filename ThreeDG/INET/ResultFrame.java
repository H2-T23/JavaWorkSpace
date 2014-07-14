// ResultFrame.java 

import	javax.swing.*;

interface Observer {
	public void			OnUpdate();
}

abstract class Subject {
	private ArrayList<Observer>		list = new ArrayList<Observer>();
	
	public void		addObserver( Observer observer ){
		list.add( observer );
	}
	
	public void		removeObserver( Observer observer ){
		int idx = list.indexOf( observer );
		list.remove( idx );
	}
	
	public void		notifyObserver(){
		for( Observer obs : list ){
			obs.OnUpdate();
		}
	}
}

enum ParameterID {
	
}


class Parameter {
	public static Map<ParameterID, Parameter> map = new HashMap<ParameterID, Parameter>();
	
	public ParameterID		id;
	public Number			num;
	
	public Parameter( ParameterID id ){
		this.id = id;
		map.put( id, this );
	}
}

class ParameterManager extends Subject implements ChangeListener {
	private ParameterManager(){}
	private static ParameterManager ins = new ParameterManager();
	public static ParameterManager	getInstance(){ return ins; }
	
		
	public void		stateChanged( ChangeEvent event ){
	}
	
	private Map<ParameterID, Object> map = new HashMap<ParameterID, Object>();
	
	public Object	getParameter( ParameterID id ){
		Object obj = map.get( id );
		if( obj == null ){
			obj = new Object();
			map.put( id, obj );
		}
		return obj;
	}
}



public class ResultFrame extends JFrame {
	
}



