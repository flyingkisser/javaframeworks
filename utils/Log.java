package frameworks.utils;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	private		FileOutputStream logFile;
	private		FileOutputStream errFile;
	private		boolean bDebug;		//是否输出到console
	private		boolean bToFile;		//是否写入文件
	private		static Log s_objLog=null;
	
		public	static Log getInstance()
		{
				if(s_objLog==null)
					s_objLog=new Log();
				return s_objLog;
		}
		
		private Log()
		{
			try
			{
				String	name="log.txt";
				String	errname="log.err.txt";
				
				errFile=new FileOutputStream(errname,true);//append
				logFile=new FileOutputStream(name,true);
					
				bDebug=true;
				bToFile=true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		private Log(String name,boolean bReplace)
		{
			try
			{
				String errname;
				if(name.isEmpty())
				{
					name="log.txt";
					errname="log.err.txt";
				}
				else
				{
					name+=".txt";
					errname=name+".err.txt";
				}
					
				errFile=new FileOutputStream(errname,!bReplace);
				logFile=new FileOutputStream(name,!bReplace);
					
				bDebug=false;
				bToFile=true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void setDebug(boolean b)
		{
			bDebug=b;
		}
		public void setFile(boolean b)
		{
			bToFile=b;
		}
		
		public void info(String fmt,Object...args)
		{
			realLog("INFO",0, fmt, args);
		}
		
		public void warn(String fmt,Object...args)
		{
			realLog("WARN",0, fmt, args);
		}
		
		public void error(String fmt,Object...args)
		{
			realLog("ERROR",1, fmt, args);
		}
		public void dbg(String fmt,Object...args)
		{
			realLog("DBG ",0, fmt, args);
		}
		
		private void realLog(String Pre,int level, String fmt, Object...args)
		{
			StackTraceElement[] stacks = new Throwable().getStackTrace();   
			int stackIndex=2;
			String outString=String.format(fmt, args);
			String logString=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms").format(new Date())+" ["+Pre+"] "+stacks[stackIndex].getClassName()+":"+stacks[stackIndex].getMethodName()+" "+outString;
			try{
				if(level==0)
				{
					if(bToFile)
						logFile.write((logString+"\r\n").getBytes("utf-8"));
					if(bDebug)
						System.out.println(logString);
				}
				else
				{
					if(bToFile)
					{
						errFile.write((logString+"\r\n").getBytes("utf-8"));
						logFile.write((logString+"\r\n").getBytes("utf-8"));
					}
					if(bDebug)
						System.err.println(logString);	
				}
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		

		
	/*	
		public void info(Object obj)
		{
			realLog(formatStr(obj,"INFO "),0);
		}
		
		public void warn(Object obj)
		{
			realLog(formatStr(obj,"WARN "),0);
		}
		
		public void error(Object obj)
		{
			realLog(formatStr(obj,"ERROR"),1);
		}
		
		public void debug(Object obj)
		{
			if(bDebug)
				realLog(formatStr(obj,"DEBUG"),0);
		}*/
		

}
