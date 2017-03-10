package javaframeworks.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrayListMgr {
	public static ArrayListMgr self=null;
	public static ArrayListMgr getInstance(){
		if(self==null)
			self=new ArrayListMgr();
		return self;
	}
	
	public String formatStr(ArrayList<Integer> idList){
		if(idList.size()<=0)
			return "";
		String retStr="";
		for (int id : idList) {
			retStr+=String.format("%d,", id);
		}
		return retStr.substring(0, retStr.length()-1);
	}
	
	public void setKeyValueAll(ArrayList<HashMap<String,Object>> valueList,String key,int v){
		int index=0;
		for (HashMap<String, Object> valueMap : valueList) {
			valueMap.put(key, v);
			valueList.set(index++, valueMap);
		}
	}
	
}

