package javaframeworks.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZipIO {
	private static ZipIO s_objSelf=null;

	//private Log log=Log.getInstance();
	
	public static ZipIO getInstance(){
		if(s_objSelf==null)
			s_objSelf=new ZipIO();
		return s_objSelf;
	}
	
	public ZipIO()
	{

	}
	
	public byte[] zipString(String data){
		try {
			byte[] ret=data.getBytes("utf-8");
			byte[] ret2=zipByte(ret);
			//String retString= new String(ret2);
			//byte[] test1=retString.getBytes();
			//byte[] test2=retString.getBytes("utf8");
			//byte[] test3=retString.getBytes("utf-8");
			return ret2;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			StackTraceElement[] stackElements = e.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) 
	            	javaframeworks.utils.Log.getInstance().error(stackElements[i].toString());
	        }
		}
		return null;
	}
	
	public String unzipString(byte[] data){
		try {
			byte[] ret2=unzipByte(data);
			if(ret2==null)
				return null;
			return new String(ret2,"utf-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			StackTraceElement[] stackElements = e.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) 
	            	javaframeworks.utils.Log.getInstance().error(stackElements[i].toString());
	        }
		}
		return null;
	}
	
	public byte[] zipByte(byte[] data){
		byte[] output = new byte[0];  
		  
        Deflater compresser = new Deflater();  
        compresser.reset();  
        compresser.setInput(data);  
        compresser.finish();  
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);  
        try {  
            byte[] buf = new byte[1024];  
            while (!compresser.finished()) {  
                int i = compresser.deflate(buf);  
                bos.write(buf, 0, i);  
            }  
            output = bos.toByteArray();  
        } catch (Exception e) {  
            output = data;  
            e.printStackTrace();  
			StackTraceElement[] stackElements = e.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) 
	            	javaframeworks.utils.Log.getInstance().error(stackElements[i].toString());
	        }
        } finally {  
            try {  
                bos.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        compresser.end();  
        return output;
	}
	
	public byte[] unzipByte(byte[] data){
		 byte[] output = new byte[0];  
		  
	        Inflater decompresser = new Inflater();  
	        decompresser.reset();  
	        decompresser.setInput(data);
	  
	        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);  
	        try {  
	            byte[] buf = new byte[1024];  
	            while (!decompresser.finished()) {  
	                int i = decompresser.inflate(buf);
	                if(i==0){
	                	if(decompresser.needsInput()==false)
	                		return null;
	                }
	                o.write(buf, 0, i);  
	            }
	            output = o.toByteArray();  
	        } catch (Exception e) {  
	            output = data;  
	            e.printStackTrace();  
	            e.printStackTrace();
				StackTraceElement[] stackElements = e.getStackTrace();
		        if (stackElements != null) {
		            for (int i = 0; i < stackElements.length; i++) 
		            	javaframeworks.utils.Log.getInstance().error(stackElements[i].toString());
		        }
	        } finally {  
	            try {  
	                o.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	  
	        decompresser.end();  
	        return output;  
	}
	
	public static void test(){
		String s="test11 22'北京-山东a";
		byte[] b1=ZipIO.getInstance().zipString(s);
		//String s1=ZipIO.getInstance().unzipString(b1);
		
		
		try {
			byte[] c0=s.getBytes("utf-8");
			byte[] c1=ZipIO.getInstance().zipByte(c0);
			byte[] c2=ZipIO.getInstance().unzipByte(c1);
			
			//int l=c2.length;
			javaframeworks.utils.Log.getInstance().dbg("string %s bytelen %d unzip len %d", b1,b1.length,c2.length);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			StackTraceElement[] stackElements = e.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) 
	            	javaframeworks.utils.Log.getInstance().error(stackElements[i].toString());
	        }
			System.exit(-1);
		}
		
		
	}
}
