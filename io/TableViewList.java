package frameworks.io;

import java.util.ArrayList;
import java.util.HashMap;

import logic.io.DBLogic;
import frameworks.utils.Database;

public class TableViewList {

	//private ArrayList<HashMap<String,Object>> m_mapView=new ArrayList<>();
	private static Database db=DBLogic.getInstance();
	private String m_strTableName;
	private String m_strCondition;

	public TableViewList(String tableName,String condition){
		m_strTableName=tableName;
		m_strCondition=condition;
		//m_mapView=db.getNames("select * from %s where %s", tableName,condition);
	}
	
	public ArrayList<HashMap<String, Object>> getAll()
	{
		return db.getNames("select * from %s where %s", m_strTableName,m_strCondition);
	}
	
	

	public int insertItem(String addString,Object...args){
		String outString=String.format(addString, args);
		return db.uid("insert into %s %s", m_strTableName,outString);
	}
	
	public long insertItemAsLong(String addString,Object...args){
		String outString=String.format(addString, args);
		return db.uidAsLong("insert into %s %s", m_strTableName,outString);
	}
	
	public int insertItemFromOtherTable(String srcTable,String whereStrFmt,Object...args){
		//这里whereStr的限制是针对srcTable表的，m_strCondition的限制是针对m_strTableName表的，所以这里只使用whereStr这一个条件
		return db.uid("select * into %s from %s where %s",m_strTableName,srcTable, String.format(whereStrFmt, args));
	}
	
	public boolean hasItem(String whereStrFmt,Object...args){
		int num=db.getInt("select count(*) from %s where %s and %s", m_strTableName,m_strCondition,String.format(whereStrFmt, args)) ;
		if(num>0)
			return true;
		return false;
	}
	
	public void reload(){
		
	}
	
	public ArrayList<HashMap<String, Object>> getChildItem(String condition,Object...args)
	{
		return db.getNames("select * from %s where %s and %s", m_strTableName,m_strCondition, String.format(condition, args));
	}
	
	public boolean del(String whereStrFmt,Object...args){
		if(!db.query("delete from %s where %s and %s", m_strTableName,m_strCondition,String.format(whereStrFmt, args)))
			return false;
		return true;
	}
	
//	public String getString(String colName,String whereStr){
//		return db.getString("select %s from %s where %s and %s'",colName, m_strTableName,m_strCondition,whereStr) ;
//	}
//	public void setString(String colName,String v){
//		db.query("update %s set %s='%s' where %s",m_strTableName, colName,v,m_strCondition);
//	}
	public ArrayList<Integer> getArrayListAsInt(String colName){
		return db.getArrayListInt("select %s from %s where %s ",colName, m_strTableName,m_strCondition);
	}
	public ArrayList<Integer> getArrayListAsInt(String colName,String whereStrFmt,Object...args){
		return db.getArrayListInt("select %s from %s where %s and %s",colName, m_strTableName,m_strCondition,String.format(whereStrFmt, args));
	}
	
	public ArrayList<Long> getArrayListAsLong(String colName){
		return db.getArrayListLong("select %s from %s where %s ",colName, m_strTableName,m_strCondition);
	}
	public ArrayList<Long> getArrayListAsLong(String colName,String whereStrFmt,Object...args){
		return db.getArrayListLong("select %s from %s where %s and %s",colName, m_strTableName,m_strCondition,String.format(whereStrFmt, args));
	}
	
	public ArrayList<String> getArrayListAsString(String colName){
		return db.getArrayListString("select %s from %s where %s ",colName, m_strTableName,m_strCondition);
	}
	public ArrayList<String> getArrayListAsString(String colName,String whereStrFmt,Object...args){
		return db.getArrayListString("select %s from %s where %s and %s",colName, m_strTableName,m_strCondition,String.format(whereStrFmt, args));
	}
	
	public int getCount(String whereStrFmt,Object...args){
		return db.getInt("select count(*) from %s where  %s and %s ", m_strTableName,m_strCondition,String.format(whereStrFmt, args)) ;
	}
	public int getCount(){
		return db.getInt("select count(*) from %s where  %s", m_strTableName,m_strCondition) ;
	}
	
	public int getInt(String colName,String whereStrFmt,Object...args){
		return db.getInt("select %s from %s where  %s and %s ", colName,m_strTableName,m_strCondition,String.format(whereStrFmt, args)) ;
	}
	public long getLong(String colName,String whereStrFmt,Object...args){
		return db.getLong("select %s from %s where  %s and %s ", colName,m_strTableName,m_strCondition,String.format(whereStrFmt, args)) ;
	}
	public String getString(String colName,String whereStrFmt,Object...args){
		return db.getString("select %s from %s where  %s and %s ", colName,m_strTableName,m_strCondition,String.format(whereStrFmt, args)) ;
	}
	public void setInt(String colName,int v,String whereStrFmt,Object...args){
		db.query("update %s set %s=%d where  %s and %s",m_strTableName, colName,v,m_strCondition,String.format(whereStrFmt, args));
	}
	public void setLong(String colName,long v,String whereStrFmt,Object...args){
		db.query("update %s set %s=%d where  %s and %s",m_strTableName, colName,v,m_strCondition,String.format(whereStrFmt, args));
	}
	public void incInt(String colName,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s+1 where  %s and %s",m_strTableName, colName,colName,m_strCondition,String.format(whereStrFmt, args));
	}
	public void decInt(String colName,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s-1 where  %s and %s",m_strTableName, colName,colName,m_strCondition,String.format(whereStrFmt, args));
	}
	public void addInt(String colName,int v,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s+%d where  %s and %s",m_strTableName, colName,colName,v,m_strCondition,String.format(whereStrFmt, args));
	}
	public void subInt(String colName,int v,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s-%d where  %s and %s",m_strTableName, colName,colName,v,m_strCondition,String.format(whereStrFmt, args));
	}
	
	public void incLong(String colName,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s+1 where  %s and %s",m_strTableName, colName,colName,m_strCondition,String.format(whereStrFmt, args));
	}
	public void decLong(String colName,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s-1 where  %s and %s",m_strTableName, colName,colName,m_strCondition,String.format(whereStrFmt, args));
	}
	public void addLong(String colName,int v,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s+%d where  %s and %s",m_strTableName, colName,colName,v,m_strCondition,String.format(whereStrFmt, args));
	}
	public void subLong(String colName,int v,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s-%d where  %s and %s",m_strTableName, colName,colName,v,m_strCondition,String.format(whereStrFmt, args));
	}
}
