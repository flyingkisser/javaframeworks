package javaframeworks.io;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

import javaframeworks.utils.Json;
import javaframeworks.utils.Log;


public class NetUDP {

	private static NetUDP objSelf=null;
	private byte[]  m_byteBuf;
	//private byte[]  m_byteBuf2;
	private DatagramPacket  m_recvPacket;
	private DatagramSocket  m_sock;
	private boolean bzip=false;
	private javaframeworks.utils.Log log=javaframeworks.utils.Log.getInstance();
	private  NetUDP(int port){
		try {
			m_sock=new DatagramSocket(port);
			m_byteBuf=new byte[1024*1024*1];
			//m_byteBuf2=new byte[1024*1024*10];
			m_recvPacket=new DatagramPacket(m_byteBuf, m_byteBuf.length);
			if(m_sock==null)
			{
				log.error("m_sock is null on NetUDP create!");
				System.exit(-1);
			}
			
			if(m_byteBuf==null){
				log.error("m_byteBuf is null on NetUDP create!");
				System.exit(-1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			StackTraceElement[] stackElements = e.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) 
	            	log.error(stackElements[i].toString());
	        }
		}
	}
	
	public void setZip(boolean bzip){
		this.bzip=bzip;
	}
	
	public static NetUDP getInstance(int port){
		if(objSelf==null)
			objSelf=new NetUDP(port);
		return objSelf;
	}
	
	public HashMap<?, ?> recv(){
		try{
			m_sock.receive(m_recvPacket);
			byte[] byteRecv;
			if(bzip)
				byteRecv=ZipIO.getInstance().unzipByte(m_byteBuf);
			else 
				byteRecv=m_byteBuf;
			
			String recvString=new String(byteRecv,"utf-8");
			HashMap<?, ?> recvHashMap=Json.decodeToHashMap(recvString);
			return recvHashMap;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(m_sock==null)
				log.error("m_sock is null");
			if(m_recvPacket==null)
				log.error("m_recvPacket is null");
			System.exit(-1);
			
		}
		return null;
	}
	
	public InetAddress getIP(){
		return m_recvPacket.getAddress();
	}
	
	public int getPort(){
		return m_recvPacket.getPort();
	}
	
	public boolean send(InetAddress addr,int port,Object o){
		try {
			String retString=Json.encode(o);
			byte[] byteSend=null;
			if(this.bzip)
			{
			
				byteSend=ZipIO.getInstance().zipString(retString);
				
				int len1=retString.getBytes("utf-8").length;	//压缩前大小
				int len2=byteSend.length;								//压缩后大小
				float f=(len1-len2)*100/len1;
				log.info("send %d after zip %d ratio is %.2f%%",len1,len2,f);
			}
			else
				byteSend=retString.getBytes("utf-8");
			
			DatagramPacket  sendPacket=new DatagramPacket(byteSend, byteSend.length,addr,port);
			m_sock.send(sendPacket);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			StackTraceElement[] stackElements = e.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) 
	            	log.error(stackElements[i].toString());
	        }
		}
		return false;
	}
	
	
}
