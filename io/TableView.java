package javaframeworks.io;

import java.util.HashMap;
import java.util.HashSet;

import javaframeworks.utils.Database;
import javaframeworks.utils.Json;

public class TableView {

	private HashMap<String,Object> m_mapView=new HashMap<>();
	private Database db=null;
	private String m_strTableName;
	private String m_strCondition;
	private HashSet<String> m_setDirtyInt=new HashSet<>();
	private HashSet<String> m_setDirtyBoolean=new HashSet<>();
	private HashSet<String> m_setDirtyLong=new HashSet<>();
	private HashSet<String> m_setDirtyString=new HashSet<>();
	
//	public TableView(String tableName,String condition){
//		m_strTableName=tableName;
//		m_strCondition=condition;
//		this.db=DBLogic.getInstance();
//		init();
//	}
	
	public TableView(Database db, String tableName,String condition){
			m_strTableName=tableName;
			m_strCondition=condition;
			this.db=db;
			init();
	}
	
	public void init(){
		try {
			m_mapView=db.getName("select * from %s where %s", m_strTableName,m_strCondition);
			if(m_mapView.size()<=0)
				throw new Exception("data is null table "+m_strTableName);
		} catch (Exception e) {
			//GameMgr.getInstance().setQuit();
			javaframeworks.utils.Log.getInstance().error("data is null , table is %s "
					+ "contition is %s",m_strTableName,m_strCondition);
		
			e.printStackTrace();
			StackTraceElement[] stackElements = e.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) 
	            	javaframeworks.utils.Log.getInstance().error(stackElements[i].toString());
	        }
			System.exit(-1);
		}
	}
	
	public HashMap<String, Object> getAll()
	{
		return m_mapView;
	}
	
	public void reload(){
		m_mapView=db.getName("select * from %s where %s", m_strTableName,m_strCondition);
	}
	
	public boolean flush(){
		String str="";
		for (String key : m_setDirtyBoolean) {
			boolean b=Boolean.parseBoolean(m_mapView.get(key).toString());
			str+=String.format("%s=%b,",key,b);
		}
		for (String key : m_setDirtyInt) {
			int v=Integer.parseInt(m_mapView.get(key).toString());
			str+=String.format("%s=%d,",key,v);
		}
		for (String key : m_setDirtyLong) {
			long v=Long.parseLong(m_mapView.get(key).toString());
			str+=String.format("%s=%d,",key,v);
		}
		for (String key : m_setDirtyString) {
			String v=m_mapView.get(key).toString();
			str+=String.format("%s='%s',",key,v);
		}
		if(str=="" || str.length()<=1)
			return true;
		str=str.substring(0, str.length()-1);
		if(!db.query("update %s set %s where %s", m_strTableName,str,m_strCondition))
			return false;
		
		m_setDirtyBoolean.clear();
		m_setDirtyInt.clear();
		m_setDirtyLong.clear();
		m_setDirtyString.clear();
		
		return true;
		
		/*
		for (String key : m_setDirtyBoolean) {
			boolean b=Boolean.parseBoolean(m_mapView.get(key).toString());
			if(!db.query("update %s set %s=%b where %s", m_strTableName,key,b,m_strCondition))
				return false;
		}
		
		for(Iterator<String>it=m_setDirtyInt.iterator(); it.hasNext(); ){
			String key=it.next();
			int v=Integer.parseInt(m_mapView.get(key).toString());
			if(!db.query("update %s set %s=%d where %s", m_strTableName,key,v,m_strCondition))
				return false;
		}
		m_setDirtyInt.clear();
		
		for(Iterator<String>it=m_setDirtyLong.iterator(); it.hasNext(); ){
			String key=it.next();
			long v=Long.parseLong(m_mapView.get(key).toString());
			if(!db.query("update %s set %s=%d where %s", m_strTableName,key,v,m_strCondition))
				return false;
		}
		m_setDirtyInt.clear();
		
		for(Iterator<String>it=m_setDirtyString.iterator(); it.hasNext(); ){
			String key=it.next();
			String v=m_mapView.get(key).toString();
			if(!db.query("update %s set %s='%s' where %s", m_strTableName,key,v,m_strCondition))
				return false;
		}
		m_setDirtyString.clear();
		
		return true;
		*/
	}
	
	public int getInt(String key){
		
		return Integer.parseInt(m_mapView.get(key).toString());
		
	}
	
	public boolean getBoolean(String key){
		
		return Boolean.parseBoolean(m_mapView.get(key).toString());
	}
	
	public long getLong(String key){
		
		return Long.parseLong(m_mapView.get(key).toString());
	}
	
	public void setInt(String key,int v){
		m_mapView.put(key, v);
		m_setDirtyInt.add(key);
		flush();
	}
	public void setLong(String key,long v){
		m_mapView.put(key, v);
		m_setDirtyInt.add(key);
		flush();
	}
	
	public void setBoolean(String key,boolean b){
		//return Boolean.parseBoolean(m_mapView.get(key).toString());
		m_mapView.put(key, b);
		m_setDirtyBoolean.add(key);
		flush();
	}
	
	public void addInt(String key,int v){
		int old=Integer.parseInt( m_mapView.get(key).toString() );
		setInt(key, v+old);
	}
	public void addLong(String key,long v){
		long old=Long.parseLong(m_mapView.get(key).toString() );
		setLong(key, v+old);
		
	}
	
	public void subInt(String key,int v){
		addInt(key,-v);
	}
	public void subLong(String key,long v){
		addLong(key,-v);
	}
	
	public void incInt(String key){
		addInt(key,1);
	}
	public void incLong(String key){
		addLong(key,1);
	}
	
	public void decInt(String key){
		subInt(key, 1);
	}
	public void decLong(String key){
		subLong(key, 1 );
	}
	
	public String getString(String key){
		Object obj=m_mapView.get(key);
		if(obj!=null)
			return obj.toString();
		return null;
	}
	
	public void setString(String key,String s){
		m_mapView.put(key, s);
		m_setDirtyString.add(key);
		flush();
	}
	
	public boolean del(){
		if(!db.query("delete from %s where %s", m_strTableName,m_strCondition))
			return false;
		return true;
	}
	
	
	public boolean hasItem(String whereStrFmt,Object...args){
		int num=db.getInt("select count(*) from %s where %s and %s'", m_strTableName,m_strCondition,String.format(whereStrFmt, args)) ;
		if(num>0)
			return true;
		return false;
	}
	
	public int getCount(String whereStrFmt,Object...args){
		return db.getInt("select count(*) from %s where %s and %s'", m_strTableName,m_strCondition,String.format(whereStrFmt, args)) ;
	}
	
	public void setJsonKeyValue(String colName,String key,Object v){
		String str=getString(colName);
		HashMap<String, Object> valueHash=null;
		if(str==null || str.length()==0){
			valueHash=new HashMap<>();
		}
		else{
			valueHash=Json.decodeToHashMapStringObject(str);
		}
		valueHash.put(key, v);
		setString(colName, Json.encode(valueHash));
	}
	
	public void setJsonKeyValue(int id,String colName,String key,int v){
		String str=getString(colName);
		HashMap<Integer, Object> valueHash=null;
		HashMap<String, Integer> objHash=null;
		if(str==null || str.length()==0){
			valueHash=new HashMap<>();
			objHash=new HashMap<String, Integer>();
		}
		else{
			valueHash=Json.decodeToHashMapIntObject(str);
			if(valueHash.containsKey(id))
				objHash=(HashMap<String, Integer>)valueHash.get(id);
			else
				objHash=new HashMap<String, Integer>();
		}
		objHash.put(key, v);
		valueHash.put(id, objHash);
		setString(colName, Json.encode(valueHash));
	}
	
	public int addJsonKeyValue(int id,String colName,String key,int v){
		int c=getJsonKeyValue(id,colName,key);
		setJsonKeyValue(id, colName, key, c+v);
		return c+v;
	}
	
	public int subJsonKeyValue(int id,String colName,String key,int v){
		int c=getJsonKeyValue(id,colName,key);
		setJsonKeyValue(id, colName, key, c-v);
		return c-v;
	}
	
	public void removeJsonID(int id,String colName){
		String str=getString(colName);
		if(str==null || str.length()==0){
			return;
		}
		HashMap<Integer, Object> valueHash=Json.decodeToHashMapIntObject(str);
		if(valueHash.containsKey(id)){
			valueHash.remove(id);
			setString(colName, Json.encode(valueHash));
		}
	}
	
	public int getJsonKeyValue(int id,String colName,String key){
		String str=getString(colName);
		HashMap<Integer, Object> valueHash=null;
		HashMap<String, Integer> objHash=null;
		if(str==null || str.length()==0){
			return 0;
		}
		else{
			valueHash=Json.decodeToHashMapIntObject(str);
			if(!valueHash.containsKey(id))
				return 0;
			objHash=(HashMap<String, Integer>)valueHash.get(id);
			if(!objHash.containsKey(key))
				return 0;
			return objHash.get(key);
		}
	}
	
	public Object getJsonKeyValue(String colName,String key){
		String str=getString(colName);
		HashMap<String, Object> valueHash=null;
		if(str==null || str.length()==0){
			return null;
		}
		else{
			valueHash=Json.decodeToHashMapStringObject(str);
			if(valueHash.containsKey(key)){
				return valueHash.get(key);
			}
			return null;
		}
	}
	
	public void test (){
		addInt("count", 1);
		
	}
	
}
