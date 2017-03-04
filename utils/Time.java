package frameworks.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Time {

	private static int YEAR_INDEX=1;
	private static int MONTH_INDEX=2;
	private static int DAY_INDEX=5;
	private static int HOUR_INDEX=11;
	private static int MIN_INDEX=12;
	private static int SEC_INDEX=13;
	private static int MSEC_INDEX=14;

	public static int getYear(){
		return get(YEAR_INDEX);
	}
	public static int getMonth(){
		return get(MONTH_INDEX)+1;
	}
	public static int getDay(){
		return get(DAY_INDEX);
	}
	public static int getHour(){
		return get(HOUR_INDEX);
	}
	public static int getMin(){
		return get(MIN_INDEX);
	}
	public static int getSec(){
		return get(SEC_INDEX);
	}
	public static int getMSec(){
		return get(MSEC_INDEX);
	}
	public static int getMaxDay(int year,int mon){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH, mon - 1);		//Java月份从0开始算
		return  cal.getActualMaximum(Calendar.DATE);
	}
	
	public static int getCurMonthDays(){
		return getMaxDay(getYear(), getMonth());
	}
	
	public static long getTime(){
		return  Calendar.getInstance().getTimeInMillis()/1000;
	}
	public static long getTimeInMillis(){
		return  Calendar.getInstance().getTimeInMillis();
	}
	
	public static String getTimeFormated(){
		return formatTimeShort(getTime());
	}
	
	//value是秒数，格式化成小时，分钟，秒
	public static ArrayList<Integer> splitTime(int value){
		ArrayList<Integer> retList=new ArrayList<>();
		int h=value/3600;
		int m=(value%3600)/60;
		int s=value%60;
		retList.add(h);
		retList.add(m);
		retList.add(s);
		return retList;
	}
	
	//value是秒数，格式化成小时，分钟，秒
	public static String formatTime(int value){
		ArrayList<Integer> retList=splitTime(value);
		return String.format("%02d:%02d:%02d", retList.get(0),retList.get(1),retList.get(2));
	}
	
	public static long mkTimeToday(int h,int m,int s){
		return 0;
	}
	
	public static long mkTime(int y,int m,int d,int h,int min,int s){
		Calendar objCalendar=new GregorianCalendar(y,m-1,d,h,min,s);
		return objCalendar.getTimeInMillis()/1000;
	}
	
	//time是秒数
	//return year,month,day,hour,minute,second,micro-second
	public static ArrayList<Integer> getDateValueByTime(long time){
		return getDateValueByMicroTime(time*1000);
	}
	
	//time是毫秒数
	//return year,month,day,hour,minute,second,micro-second
	public static ArrayList<Integer> getDateValueByMicroTime(long time){
		Calendar objCalendar=new GregorianCalendar();
		objCalendar.setTimeInMillis(time);
		int y=objCalendar.get(YEAR_INDEX);
		int m=objCalendar.get(MONTH_INDEX)+1;
		int d=objCalendar.get(DAY_INDEX);
		int h=objCalendar.get(HOUR_INDEX);
		int min=objCalendar.get(MIN_INDEX);
		int s=objCalendar.get(SEC_INDEX);
		int ms=objCalendar.get(MSEC_INDEX);
		ArrayList<Integer> retList=new ArrayList<>();
		retList.add(y);
		retList.add(m);
		retList.add(d);
		retList.add(h);
		retList.add(min);
		retList.add(s);
		retList.add(ms);
		return retList;
	}
	
	public static String formatTime(long timeStamp){
		ArrayList<Integer> list=getDateValueByTime(timeStamp);
		return String.format("%d-%02d-%02d %02d:%02d:%02d:%04d",list.get(0),list.get(1),list.get(2),list.get(3),list.get(4),list.get(5),list.get(6));
	}
	public static String formatTimeShort(long timeStamp){
		ArrayList<Integer> list=getDateValueByTime(timeStamp);
		return String.format("%d-%02d-%02d %02d:%02d:%02d",list.get(0),list.get(1),list.get(2),list.get(3),list.get(4),list.get(5));
	}
	
	private static int get(int index){
		return  (Calendar.getInstance().get(index));
	}
	
	public static void test(){
		long a1=Time.getTime();
		//long a11=Time.getTimeInMillis();
		long a2=Time.mkTime(2001, 1, 1, 0, 0, 0);
		long a3=Time.mkTime(2011, 0, 1, 0, 0, 0);
		String timestr1=Time.formatTime(a2);
		String timestr2=Time.formatTime(a3);
		
		int days=Time.getCurMonthDays();
		int maxdays=Time.getMaxDay(2015, 2);
		int y=Time.getYear();
		int m=Time.getMonth();
		int d=Time.getDay();
		int h=Time.getHour();
		int min=Time.getMin();
		int s=Time.getSec();
		int ms=Time.getMSec();
		
		for(int i=1;i<=12;i++){
			long time=Time.mkTime(2014, i, 1, 0, 0, 0);
			String timestr=Time.formatTime(time);
			frameworks.utils.Log.getInstance().dbg("2014-%02d-1 %s",i,timestr); 
		}
		
		for(int i=1;i<=31;i++){
			long time=Time.mkTime(2014, 1, i, 0, 0, 0);
			String timestr=Time.formatTime(time);
			frameworks.utils.Log.getInstance().dbg("2014-01-%02d %s",i,timestr); 
		}
		
		for(int i=1;i<=24;i++){
			long time=Time.mkTime(2014, 1, 31, i, 0, 0);
			String timestr=Time.formatTime(time);
			frameworks.utils.Log.getInstance().dbg("2014-01-31 %02d:00:00 %s",i,timestr); 
		}
		
		for(int i=1;i<=60;i++){
			long time=Time.mkTime(2014, 1, 31, 1, i, 0);
			String timestr=Time.formatTime(time);
			frameworks.utils.Log.getInstance().dbg("2014-01-31 1:%02d:00 %s",i,timestr); 
		}
		
		for(int i=1;i<=60;i++){
			long time=Time.mkTime(2014, 1, 31, 1, 30, i);
			String timestr=Time.formatTime(time);
			frameworks.utils.Log.getInstance().dbg("2014-01-31 1:30:%02d %s",i,timestr); 
		}
		
		frameworks.utils.Log.getInstance().dbg("%d-%02d-%02d %02d:%02d:%02d:%04d days %d maxdays %d", y,m,d,h,min,s,ms,days,maxdays);
		
		frameworks.utils.Log.getInstance().dbg("time1 %d time2 %d %s time3 %d %s", a1,a2,timestr1,a3,timestr2);
		
		a1=Time.mkTime(2014, 12, 11, 0,0,0);
		a2=Time.mkTime(2011, 12, 11, 24,0,0);
		timestr1=Time.formatTime(a1);
		timestr2=Time.formatTime(a2);
		frameworks.utils.Log.getInstance().dbg("time1 %d %s time2 %d %s", a1,timestr1,a2,timestr2);
	}
	
}
