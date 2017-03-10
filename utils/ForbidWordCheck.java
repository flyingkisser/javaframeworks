package javaframeworks.utils;

import java.io.File;
import java.util.ArrayList;


//用于检测文本中是否有非法文字
public class ForbidWordCheck {
	private static ForbidWordCheck s_objSelf=null;
	private String strConfigFileName="config/ForbidWordConfig_%s.json";
	private ArrayList<String> m_wordList=null;
	private static Log log=Log.getInstance();
	
	public static ForbidWordCheck getInstance(){
		if(s_objSelf==null)
			s_objSelf=new ForbidWordCheck("cn");
		return s_objSelf;
	}
	
	public ForbidWordCheck(String lan)
	{
		try {
			
			m_wordList=(ArrayList<String>)Json.decodeToArrayList(new File(String.format(strConfigFileName, lan)));
		} catch (Exception e) {
			e.printStackTrace();
			StackTraceElement[] stackElements = e.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) 
	            	javaframeworks.utils.Log.getInstance().error(stackElements[i].toString());
	        }
		}
	}
	
	public boolean check(String v){
		if(m_wordList.contains(v))
			return true;

		for(String s : m_wordList){
			if(v.contains(s))
				return true;
		}
		
		return false;
	}
	
	public static  void test(){
		boolean s;
		s=ForbidWordCheck.getInstance().check("fuck");
		s=ForbidWordCheck.getInstance().check("want fuck u!");
		s=ForbidWordCheck.getInstance().check("want sucks u!");
		if(s)
			log.info("ok");
	}
}
