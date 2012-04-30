package org.chencc98.etmslog.main;

import java.util.Random;
import java.util.Vector;

public class DoctorHandler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Vector<DoctorProperty> v = new Vector<DoctorProperty>();
		DoctorHandler.fillAllDoctors(v);
		System.out.println(v.toString());

	}
	
	public static void fillAllDoctors(Vector<DoctorProperty> vall){
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036137","C",1));
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036138","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036076","C",1));
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036129","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036015","B",3));
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036139","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036140","A",6));
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036055","B",3));
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036132","C",1));
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036130","A",6));
		vall.add(new DoctorProperty("ZJHZ036H","D007","ZJHZ036133","C",1));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036001","B",3));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036135","A",6));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036011A","C",1));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036012A","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036019","V",2));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036022A","C",1));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036126","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036028A","B",3));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036036A","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036040A","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036136","A",6));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036125","C",1));
		vall.add(new DoctorProperty("ZJHZ036H","D022","ZJHZ036057A","B",3));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036002A","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036128","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036114","C",1));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036141","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036003","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036009","B",3));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036053","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036051","B",3));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036029A","C",1));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036142","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036049A","N",1));
		vall.add(new DoctorProperty("ZJHZ036H","D025","ZJHZ036117","C",1));
		
		vall.add(new DoctorProperty("ZJHZ028H","D002","ZJHZ028003","",1));
		vall.add(new DoctorProperty("ZJHZ028H","D002","ZJHZ028015","",1));
		vall.add(new DoctorProperty("ZJHZ028H","D002","ZJHZ028016","",1));
		vall.add(new DoctorProperty("ZJHZ028H","D002","ZJHZ028021","",1));
		vall.add(new DoctorProperty("ZJHZ028H","D002","ZJHZ028039","",1));
		vall.add(new DoctorProperty("ZJHZ028H","D002","ZJHZ028044","",1));
		vall.add(new DoctorProperty("ZJHZ028H","D002","ZJHZ028046","",1));
		vall.add(new DoctorProperty("ZJHZ028H","D002","ZJHZ028050","",1));
		vall.add(new DoctorProperty("ZJHZ028H","D002","ZJHZ028059","",1));
		vall.add(new DoctorProperty("ZJHZ028H","D002","ZJHZ028072","",1));
		vall.add(new DoctorProperty("ZJHZ028H","D007","ZJHZ028004","B",3));
		vall.add(new DoctorProperty("ZJHZ028H","D007","ZJHZ028018A","A",6));
		vall.add(new DoctorProperty("ZJHZ028H","D007","ZJHZ028106","A",6));
		vall.add(new DoctorProperty("ZJHZ028H","D007","ZJHZ028156","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D007","ZJHZ028255","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D007","ZJHZ028211","B",3));
		vall.add(new DoctorProperty("ZJHZ028H","D007","ZJHZ028073","A",6));
		vall.add(new DoctorProperty("ZJHZ028H","D007","ZJHZ028157","B",3));
		vall.add(new DoctorProperty("ZJHZ028H","D007","ZJHZ028155","B",3));
		vall.add(new DoctorProperty("ZJHZ028H","D007","ZJHZ028107","B",3));
		vall.add(new DoctorProperty("ZJHZ028H","D008","ZJHZ028015A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D008","ZJHZ028032A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D008","ZJHZ028037A","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D008","ZJHZ028043A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D008","ZJHZ028065A","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D008","ZJHZ028066A","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D008","ZJHZ028071A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D008","ZJHZ028188","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D014","ZJHZ028002A","V",2));
		vall.add(new DoctorProperty("ZJHZ028H","D014","ZJHZ028006A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D014","ZJHZ028008A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D014","ZJHZ028012A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D014","ZJHZ028049A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D014","ZJHZ028052A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D014","ZJHZ028055A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D014","ZJHZ028058A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D014","ZJHZ028069A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028258","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028260","B",3));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028257","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028026","V",2));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028112","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028262","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028040A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028045","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028048","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028259","B",3));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028111","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028062","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028065","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028256","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028069","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028263","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D022","ZJHZ028261","B",3));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028148","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028126","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028020A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028219","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028109","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028163","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028153","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028070","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028127","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028053","V",2));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028218","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028061A","N",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028158","C",1));
		vall.add(new DoctorProperty("ZJHZ028H","D025","ZJHZ028110","N",1));
		
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039068","A",4));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039069","A",4));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039070","D",1));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039071","A",4));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039065","A",4));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039005","A",4));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039066","B",2));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039067","A",4));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039072","C",1));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039073","A",4));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039074","V",2));
//		vall.add(new DoctorProperty("ZJHZ039H","ZJHZ039075","C",1));
		
		
	}

	public static void pickupDoctor(Vector<DoctorProperty> vall, Vector<DoctorProperty> vused, Vector<DoctorProperty> vpickup, int num){
		Random r = new Random();
		Vector<DoctorProperty> vtemp = new Vector<DoctorProperty>();
		int choose = 0;
		for( int i=0; i< num ; i++){
			choose = r.nextInt(vall.size());
			DoctorProperty dp = vall.get(choose);
			vall.remove(choose);
			dp.accessit();
			
			vpickup.add(dp);
			if( dp.getAccess() == 0){
				vused.add(dp);
			}else{
				vtemp.add(dp);
			}
			
			if( vall.size() == 0){  // pickup some used doctor back, abv level first
				DoctorHandler.moveUsed2All(vall, vused,vpickup);
			}
		}
		
		//insert vtemp back into vall
		Random r0 = new Random();
		for( int j=0; j<vtemp.size(); j++){
			int index = r0.nextInt(vall.size());
			vall.add(index, vtemp.get(j));
		}
		//vall.addAll(vtemp);
	}
	public static void pickupDoctor(Main m, Vector<DoctorProperty> vpickup, int num){
		pickupDoctor(m.getVall(),m.getVused(),vpickup, num);
	}
	public static void moveUsed2All(Vector<DoctorProperty> vall, Vector<DoctorProperty> vused, Vector<DoctorProperty> vpickup){
		Vector<DoctorProperty> v = new Vector<DoctorProperty>();
		int num = vpickup.size();
		for( int i = 0; i<num; i++ ){
			DoctorProperty dp = vpickup.get(i);
			int k = vused.indexOf(dp);
			if( k != -1 ){
				v.add(vused.get(k));
				vused.remove(k);
			}
			
		}
		num = vused.size();
		for( int i = 0; i< num ; i++){
			vused.get(i).resetAccess();
		}
		vall.addAll(vused);
		vused.removeAllElements();
		vused.addAll(v);
		
	}

}
