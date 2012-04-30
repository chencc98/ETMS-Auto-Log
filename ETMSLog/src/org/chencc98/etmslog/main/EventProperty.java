package org.chencc98.etmslog.main;

public class EventProperty {
	private String date= null;
	private String am_event = null;
	private String pm_event = null;
	public EventProperty(){
		date = ETMSUtil.getDateFormat();
		am_event="DIF";
		pm_event = "DIF";
	}
	public EventProperty(String d, String am, String pm){
		this.date = d;
		this.am_event = am;
		this.pm_event = pm;
	}
	
	public String getDate(){
		return date;
	}
	public String getAMEvt(){
		return am_event;
	}
	public String getPMEvt(){
		return pm_event;
	}
	
	public void setDate(String d){
		this.date = d;
	}
	public void setAMEvt(String am){
		this.am_event = am;
	}
	public void setPMEvt(String pm){
		this.pm_event = pm;
	}

}
