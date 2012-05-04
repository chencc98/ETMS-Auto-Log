package org.chencc98.etmslog.entity;

public class DoctorProperty {
	private String hospital_id = "";
	private String dept_id = "";
	private String doctor_id = "";
	private String level = "";
	private int lelve_num = 0;
	private int access_num = 0;
	private boolean ispicked = false;
	public DoctorProperty(String hos, String dep,String id, String level){
		this.hospital_id = hos;
		this.dept_id = dep;
		this.doctor_id = id;
		this.level = level;
		if( this.level.equals("A")){
			this.lelve_num = 4;
		}else if( this.level.equals("B")){
			this.lelve_num = 2;
		}else{
			this.lelve_num = 1;
		}
//		this.lelve_num = num;
		this.access_num = 0;
	}
	public DoctorProperty(String hos, String dep,String id, String level, int access){
		this(hos, dep, id, level);
		this.access_num = access;
	}
	
	public String getHospital(){
		return this.hospital_id;
	}
	public void setHospital(String id){
		this.hospital_id = id;
	}
	
	public String getDept(){
		return this.dept_id;
	}
	public void setDept(String id){
		this.dept_id = id;
	}
	
	public String getDoctorID(){
		return this.doctor_id;
	}
	public void setDoctorID(String id){
		this.doctor_id = id;
	}
	
	public String getLevel(){
		return this.level;
	}
	public void setLevel(String l){
		this.level = l;
	}
	
	public int getAccess(){
		return this.access_num;
	}
	public void setAccess(int a){
		this.access_num = a;
	}
	
	public void accessit(){  //don't use
		this.access_num--;
	}
	public void resetAccess(){  //don't use
		this.access_num = this.lelve_num;
	}
	
	public boolean getIspicked(){
		return this.ispicked;
	}
	public void setIspicked(boolean p){
		this.ispicked = p;
	}
	
	public String getComparator(){
		return "HID:"+this.hospital_id+"|PID:"+this.dept_id+"|DID:"+this.doctor_id;
	}
	public String toString(){
		return "HID:"+this.hospital_id+"|PID:"+this.dept_id+"|DID:"+this.doctor_id+"|LV:"+this.level+"|AS:"+this.access_num
				+"|PICK:"+this.ispicked;
	}
	
	public boolean equals(Object obj){
		if( obj instanceof DoctorProperty ){
			DoctorProperty dp = (DoctorProperty)obj;
			if( this.getComparator().equals(dp.getComparator())){
				return true;
			}
		}
		return false;
	}

}
