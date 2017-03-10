package javaframeworks.io;

import java.util.ArrayList;
import java.util.HashMap;

import javaframeworks.utils.Database;

public class TableViewAll {

	//private ArrayList<HashMap<String,Object>> m_mapView=new ArrayList<>();
	private Database db;
	private String m_strTableName;
	//private String m_strCondition;

	public TableViewAll(Database db, String tableName){
		m_strTableName=tableName;
		db=db;
	}
	
	public ArrayList<HashMap<String, Object>> getAll()
	{
		return db.getNames("select * from %s", m_strTableName);
	}
	
	public ArrayList<HashMap<String, Object>> getChildItem(String condition,Object...args)
	{
		return db.getNames("select * from %s where  %s", m_strTableName, String.format(condition, args));
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
		return db.uid("insert  into %s select * from %s where %s",m_strTableName,srcTable, String.format(whereStrFmt, args));
	}
	
	public long insertItemFromOtherTableAsLong(String srcTable,String whereStrFmt,Object...args){
		//这里whereStr的限制是针对srcTable表的，m_strCondition的限制是针对m_strTableName表的，所以这里只使用whereStr这一个条件
		return db.uidAsLong("insert  into %s select * from %s where %s",m_strTableName,srcTable, String.format(whereStrFmt, args));
	}
	
	public boolean hasItem(String whereStrFmt,Object...args){
		int num=db.getInt("select count(*) from %s where %s ", m_strTableName,String.format(whereStrFmt, args)) ;
		if(num>0)
			return true;
		return false;
	}
	
	public void reload(){
		
	}
	
	public boolean del(String whereStrFmt,Object...args){
		if(!db.query("delete from %s where %s ", m_strTableName,String.format(whereStrFmt, args)))
			return false;
		return true;
	}
	public boolean delAll(){
		if(!db.query("delete from %s ", m_strTableName))
			return false;
		return true;
	}
	
	public ArrayList<Integer> getArrayListAsInt(String colName){
		return db.getArrayListInt("select %s from %s",colName, m_strTableName);
	}
	public ArrayList<Long> getArrayListAsLong(String colName){
		return db.getArrayListLong("select %s from %s",colName, m_strTableName);
	}
	public ArrayList<String> getArrayListAsString(String colName){
		return db.getArrayListString("select %s from %s",colName, m_strTableName);
	}
	
	public ArrayList<Integer> getArrayListAsInt(String colName,String whereStrFmt,Object...args){
	 return db.getArrayListInt("select %s from %s where %s",colName, m_strTableName,String.format(whereStrFmt, args));
	}
	public ArrayList<Long> getArrayListAsLong(String colName,String whereStrFmt,Object...args){
		 return db.getArrayListLong("select %s from %s where %s",colName, m_strTableName,String.format(whereStrFmt, args));
	}
	public String getString(String colName,String whereStrFmt,Object...args){
		return db.getString("select %s from %s where %s ", colName,m_strTableName,String.format(whereStrFmt, args)) ;
	}
	
	public int getCount(String whereStrFmt,Object...args){
		return db.getInt("select count(*) from %s where %s ", m_strTableName,String.format(whereStrFmt, args)) ;
	}
	public long getLong(String colName,String whereStrFmt,Object...args){
		return db.getLong("select %s from %s where %s ", colName,m_strTableName,String.format(whereStrFmt, args)) ;
	}
	public int getInt(String colName,String whereStrFmt,Object...args){
		return db.getInt("select %s from %s where %s ", colName,m_strTableName,String.format(whereStrFmt, args)) ;
	}
	public void setInt(String colName,int v,String whereStrFmt,Object...args){
		db.query("update %s set %s=%d where %s",m_strTableName, colName,v,String.format(whereStrFmt, args));
	}
	public void incInt(String colName,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s+1 where %s",m_strTableName, colName,colName,String.format(whereStrFmt, args));
	}
	public void decInt(String colName,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s-1 where %s",m_strTableName, colName,colName,String.format(whereStrFmt, args));
	}
	public void addInt(String colName,int v,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s+%d where %s",m_strTableName, colName,colName,v,String.format(whereStrFmt, args));
	}
	public void subInt(String colName,int v,String whereStrFmt,Object...args){
		db.query("update %s set %s=%s-%d where %s",m_strTableName, colName,colName,v,String.format(whereStrFmt, args));
	}
}
