/**
 * 
 */
package org.chencc98.etmslog.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.chencc98.etmslog.entity.EventProperty;

/**
 * @author chencarl
 *
 */
public class ETMSUtil {
	private static String [] timeslice = { "07:00","07:30","08:00","08:30","09:00",
		"09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30",
		"14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00",
		"18:30","19:00","19:30","20:00","20:30","21:00","21:30","22:00","22:30",
		"23:00","23:30"};
	private static String [] msg = {"RP16","RP17","RP19"};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = String.format("%-15s", "test");
		System.out.println("="+s+"=");
		
	}
	
	public static String getDateFormat(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(new Date());
	}
	public static String getDateFormat(java.util.Date dt){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(dt);
	}
	public static String getDateFormat2(java.util.Date dt){
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH )+1;
		int d = cal.get(Calendar.DAY_OF_MONTH);
		return String.valueOf(y)+"-"+String.valueOf(m)+"-"+String.valueOf(d);
	}
	public static String getDateFirst(){
		Calendar cal = Calendar.getInstance();
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DAY_OF_YEAR, 1-dayofweek  );
		return getDateFormat(cal.getTime());
	}
	public static String getDateFirst(java.util.Date dt){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DAY_OF_YEAR, 1-dayofweek  );
		return getDateFormat(cal.getTime());
	}
	
	public static Date getDateFirst2( java.util.Date dt){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DAY_OF_YEAR, 1-dayofweek  );
		return cal.getTime();
	}

	public static EventProperty[] getEventLists(String response, Date dt){
		EventProperty[] eps = new EventProperty[7];
		int i = 1;
		for( i = 1; i<=7; i++){
			eps[i-1] = new EventProperty();
			Date df = getDateFirst2(dt);
			Calendar cal = Calendar.getInstance();
			cal.setTime(df);
			cal.add(Calendar.DAY_OF_YEAR, i-1);
			eps[i-1].setDate(getDateFormat(cal.getTime()) );
			eps[i-1].setAMEvt(searchForEvt(response,i-1));
			eps[i-1].setPMEvt(searchForEvt(response,7+i-1));
		}
		return eps;
	}
	public static String searchForEvt( String response, int i){
		if( i<0 || i> 13 ){
			return "";
		}
		String searchkey = "eventLists[" + String.valueOf(i) +"]";
//		System.out.println("searchkey="+searchkey);
		int index = response.indexOf(searchkey);
		String result = response.substring(index+ searchkey.length()+4);
		index = result.indexOf('"');
		result = result.substring(0,index);
//		System.out.println(result);
		String [] sp = result.split("\\|");
		if( sp.length >=2 ){
			return sp[1];
		}else{
			return "";
		}
	}
	
	public static Date getDateByDiff( Date dt, int i){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		
		cal.add(Calendar.DAY_OF_YEAR, i  );
		return cal.getTime();
	}
	
	public static int getRandom15_20(){ 
		Random r = new Random();
		return r.nextInt(6)+15;
	}
	
	public static String searchSystem_id(String src){
		int index = src.indexOf("frame");
		if( index == -1 ){
			return "";
		}
		index = src.indexOf("system_id", index);
		int index2 = src.indexOf("&", index);
		return src.substring(index+10, index2);
	}
	public static String searchCall_code(String src){
		int index = src.indexOf("frame");
		if( index == -1 ){
			return "";
		}
		index = src.indexOf("call_code", index);
		int index2 = src.indexOf("&", index);
		return src.substring(index+10, index2);
	}
	public static String searchType(String src){
		int index = src.indexOf("name=\"type\"");
		index = src.indexOf("value", index);
		int index2 = src.indexOf("\"", index+7);
		return src.substring(index+7, index2);
	}
	public static String searchTimestamp(String src){
		int index = src.indexOf("time_stamp");
		if( index < 0 ){ return ""; }
		index = src.indexOf("value", index);
		int index2 = src.indexOf("\"", index+9);
		return src.substring(index+9, index2);
	}
	public static String searchTotal(String src, Date dt){
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		String key = " id=total_c_"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+" ";
		int index = src.indexOf(key);
		if( index < 0 ){
			return "0";
		}
		int index2 = src.indexOf(">", index+key.length());
		String num = src.substring(index+key.length()+6, index2);
		try{
			Integer.parseInt(num);
		}catch(Exception e){
			num = "0";
		}
		return num;
		
	}
	
	
	public static String getTimestamp(){
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String half = sdf.format(dt);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH )+1;
		int d = cal.get(Calendar.DAY_OF_MONTH);
		return String.valueOf(y)+"-"+String.valueOf(m)+"-"+String.valueOf(d)+" "+half;
	}
	
	public static String getStartTime(){
		Random r = new Random();
		int i = r.nextInt(ETMSUtil.timeslice.length-1);
		return ETMSUtil.timeslice[i];
	}
	public static String getEndTime(String start){
		int s = Arrays.binarySearch(ETMSUtil.timeslice, start);
		return ETMSUtil.timeslice[s+1];
	}
	
	public static String getMsg(){
		Random r = new Random();
		int i = r.nextInt(ETMSUtil.msg.length);
		return ETMSUtil.msg[i];
	}
}

