/**
 * 
 */
package orgnb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;




/**
 * @author chencarl
 *
 */
public class AdvancedEliteClassifier {
	/**
	 * the method to do the classify according to the yield and yield stability
	 * @param testData
	 * @param trainingData
	 * @param locations
	 * @return
	 */
	public int[] classify(String[] testData, String[] trainingData,
			String[] locations) {
		List<DataRecord> trainingList = fetchFromTraining(trainingData);
		Map<String,LocationDetail> locationList = getLocationDetailList(locations);
		fillLocationInfo(trainingList, locationList);
		long totalTrainingLength = trainingList.size();
		long totalIsEliteTrainingLength = 0;
		//long totalNotEliteTrainingLength = 0;
		double isElitePValue = 0;
		//double notElitePValue = 0;
		Map<String, IdentityPValue> attrLocationZone = new HashMap<String, IdentityPValue>();
		Map<String, IdentityPValue> attrLocationBand = new HashMap<String, IdentityPValue>();
		Map<String, IdentityPValue> attrRep = new HashMap<String, IdentityPValue>();
		Map<String, IdentityPValue> attrYear = new HashMap<String, IdentityPValue>();
		Map<String, IdentityPValue> attrType = new HashMap<String, IdentityPValue>();
		Map<String, IdentityPValue> attrYield = new HashMap<String, IdentityPValue>();
		Map<String, IdentityPValue> attrMN = new HashMap<String, IdentityPValue>();
		Map<String, IdentityPValue> attrRM = new HashMap<String, IdentityPValue>();
		long [] temp = learnFromTraining(attrLocationZone,attrLocationBand, attrRep, attrYear,attrType, attrYield, attrMN,
				attrRM,trainingList );
		totalIsEliteTrainingLength = temp[0];
		//totalNotEliteTrainingLength = temp[1];
		isElitePValue = (double)totalIsEliteTrainingLength / (double)totalTrainingLength;
		//notElitePValue = (double)totalNotEliteTrainingLength / (double)totalTrainingLength;
//		calculateAttrPValue(attrLocationZone, totalIsEliteTrainingLength,totalNotEliteTrainingLength );
//		calculateAttrPValue(attrLocationBand, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
//		calculateAttrPValue(attrRep, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
//		calculateAttrPValue(attrYear, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
//		calculateAttrPValue(attrType, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
//		calculateAttrPValue(attrYield, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
//		calculateAttrPValue(attrMN, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
//		calculateAttrPValue(attrRM, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
		calculateAttrPValue(attrLocationZone, totalIsEliteTrainingLength,0 );
		calculateAttrPValue(attrLocationBand, totalIsEliteTrainingLength,0);
		calculateAttrPValue(attrRep, totalIsEliteTrainingLength,0);
		calculateAttrPValue(attrYear, totalIsEliteTrainingLength,0);
		calculateAttrPValue(attrType, totalIsEliteTrainingLength,0);
		calculateAttrPValue(attrYield, totalIsEliteTrainingLength,0);
		calculateAttrPValue(attrMN, totalIsEliteTrainingLength,0);
		calculateAttrPValue(attrRM, totalIsEliteTrainingLength,0);
		//now the training is done. we start to identity the test data
		List<DataRecord> testList = fetchFromTestData(testData);
		fillLocationInfo(testList, locationList);
//		analyzeTestList(testList,attrLocationZone,attrLocationBand, attrRep, attrYear, attrType, attrYield,attrMN, attrRM, isElitePValue, notElitePValue );
		analyzeTestList(testList,attrLocationZone,attrLocationBand, attrRep, attrYear, attrType, attrYield,attrMN, attrRM, isElitePValue, 0 );
		String[] noncheck = getSortedVarietyList(testList);
		String[] check = getCheckFromTest(testList);
		return combineConvert(noncheck, check);
	}
	
	private int[] combineConvert(String[] first, String[] second) {
		
		if(first == null){
			first = new String[0];
		}
		if (second == null){
			second = new String[0];
		}
		int len = first.length + second.length;
		
		int[] ret = new int[len];
		int i = 0;
		
		int firstlen = first.length;
		for (; i < firstlen; i++) {
			ret[i] = Integer.parseInt(first[i ]);
		}
		for(; i< len; i++){
			ret[i] = Integer.parseInt(second[i - firstlen]);
		}
		return ret;
	}
	
	private String[] getCheckFromTest(List<DataRecord> testList){
		List<String> retlist = new ArrayList<String>();
		Iterator<DataRecord> it = testList.iterator();
		while( it.hasNext()){
			DataRecord dr = it.next();
			if(dr.ischeck && !retlist.contains(dr.varietyid)){
				retlist.add(dr.varietyid);
			}
		}
		return retlist.toArray(new String[0]);
	}
	
	private String[] getSortedVarietyList(List<DataRecord> testData){
		Map<String, Map<String, TestIdentityPValue>> collection = new HashMap<String, Map<String, TestIdentityPValue>>();
//		Map<String, IdentityPValue> results = new HashMap<String, IdentityPValue>();
//		Map<String, Integer> varietyList = new HashMap<String, Integer>();
		Iterator<DataRecord> it = testData.iterator();
		while ( it.hasNext()){
			DataRecord dr = it.next();
//			if (dr.ischeck){
//				continue;
//			}
			if(collection.containsKey(dr.expid)){
				Map<String, TestIdentityPValue> variety = collection.get(dr.expid);
				if(variety.containsKey(dr.varietyid)){
				
					TestIdentityPValue ipv = variety.get(dr.varietyid);
					ipv.isEliteNumber++;
					ipv.isElitePValue += dr.elitePValue;
//				if(dr.iselite){
//					ipv.isEliteNumber++;
//				}else{
//					ipv.notEliteNumber++;
//				}
//				if (ipv.isElitePValue < dr.elitePValue){
//					ipv.isElitePValue = dr.elitePValue;
				}else{
					TestIdentityPValue ipv = new TestIdentityPValue();
					ipv.identity = dr.varietyid;
					ipv.isEliteNumber = 1;
					ipv.isElitePValue = dr.elitePValue;
					ipv.ischeck = dr.ischeck;
					variety.put(dr.varietyid, ipv);
				}
			}else{
				TestIdentityPValue ipv = new TestIdentityPValue();
				ipv.identity = dr.varietyid;
				ipv.isEliteNumber = 1;
				ipv.isElitePValue = dr.elitePValue;
				ipv.ischeck = dr.ischeck;
				Map<String, TestIdentityPValue> variety = new HashMap<String, TestIdentityPValue>();
				variety.put(dr.varietyid, ipv);
				collection.put(dr.expid, variety);
//				if(dr.iselite){
//					ipv.isEliteNumber = 1;
//					ipv.notEliteNumber = 0;
//				}else{
//					ipv.notEliteNumber = 1;
//					ipv.isEliteNumber = 0;
//				}
//				ipv.isElitePValue = dr.elitePValue;
//				results.put(dr.varietyid,ipv);
			}
//			if(varietyList.containsKey(dr.varietyid)){
//				Integer inte = varietyList.get(dr.varietyid);
//				Integer newone = inte.intValue() + 1;
//				varietyList.put(dr.varietyid, newone);
//			}else{
//				varietyList.put(dr.varietyid, new Integer(1));
//			}
		}
		
		Set<String> allExp = collection.keySet();
		Iterator<String> expit = allExp.iterator();
		while( expit.hasNext()){
			String expid = expit.next();
			Map<String, TestIdentityPValue> variety = collection.get(expid);
			Collection<TestIdentityPValue> allVID = variety.values();
			Iterator<TestIdentityPValue> vit = allVID.iterator();
			while( vit.hasNext()){
				TestIdentityPValue ipv = vit.next();
				ipv.isElitePValue = ipv.isElitePValue / (double)ipv.isEliteNumber;
//			int total = varietyList.get(ipv.identity).intValue();
//			ipv.isElitePValue = (double)ipv.isElitePValue / (double)total;
//			ipv.notElitePValue = (double)ipv.notEliteNumber / (double)total;
			}
		}
		
		List<TestIdentityPValue> isEliteList = new ArrayList<TestIdentityPValue>();
		List<TestIdentityPValue> notEliteList = new ArrayList<TestIdentityPValue>();
		Iterator<String> anotherexpit = allExp.iterator();
		while (anotherexpit.hasNext()){
			String expid = anotherexpit.next();
			Map<String, TestIdentityPValue> variety = collection.get(expid);
			Collection<TestIdentityPValue> ipvList = variety.values();
			Iterator<TestIdentityPValue> noncheckIt = ipvList.iterator();
			while(noncheckIt.hasNext()){
				boolean iselite = true;
				int compareNum =0;
				TestIdentityPValue ipv = noncheckIt.next();
				if(ipv.ischeck){
					continue;
				}
				Iterator<TestIdentityPValue> checkIt = ipvList.iterator();
				while(checkIt.hasNext()){
					TestIdentityPValue checkipv = checkIt.next();
					if(!checkipv.ischeck){
						continue;
					}
					if(ipv.isElitePValue< checkipv.isElitePValue){
						iselite = false;
						compareNum ++;
						break;
					}else{
						compareNum++;
					}
				}
				if( !iselite){
					if(isEliteList.contains(ipv)){
						isEliteList.remove(ipv);
					}
					if(!notEliteList.contains(ipv)){
						notEliteList.add(ipv);
					}
				}else if(iselite && compareNum == 0){
					if(!isEliteList.contains(ipv)&& !notEliteList.contains(ipv)){
						notEliteList.add(ipv);
					}
				}else{
					if(!notEliteList.contains(ipv) && !isEliteList.contains(ipv)){
						isEliteList.add(ipv);
					}
				}
			}
		}
//		Iterator<IdentityPValue> vit2 = allVID.iterator();
//		while( vit2.hasNext()){
//			IdentityPValue ipv = vit2.next();
////			if (ipv.isElitePValue >= ipv.notElitePValue){
//				isEliteList.add(ipv);
////			}else{
////				notEliteList.add(ipv);
////			}
//		}
		
		//ok. sort it and return
		String[] noncheck = sortCombine(isEliteList, notEliteList);
		return noncheck;
	}
	
	
	
	private String[] sortCombine(List<TestIdentityPValue> isEliteList,List<TestIdentityPValue> notEliteList ){
		TestIdentityPValue [] isEliteArr = isEliteList.toArray(new TestIdentityPValue[0]);
		Arrays.sort(isEliteArr, new IsElitePValueComparator());
		String[] iselite =  getStringArr(isEliteArr);
		TestIdentityPValue [] notEliteArr = notEliteList.toArray(new TestIdentityPValue[0]);
		Arrays.sort(notEliteArr, new NotElitePValueComparator());
		String[] notelite = getStringArr(notEliteArr);
		return combineStr(iselite,notelite );
	}
	
	private String[] combineStr(String[] a, String[]b){
		int len = a.length + b.length;
		String [] ret = new String[len];
		int firstlen = a.length;
		int i = 0;
		for( i=0; i< firstlen;i++){
			ret[i] = a[i];
		}
		for(; i<len; i++){
			ret[i] = b[i - firstlen];
		}
		return ret;
	}
	
	private String[] getStringArr(IdentityPValue [] ipvarr){
		int len = ipvarr.length;
		String [] ret = new String[len];
		for(int i=0; i< len; i++){
			ret[i] = ipvarr[i].identity;
		}
		return ret;
	}
	
	private void analyzeTestList(List<DataRecord> testData, Map<String, IdentityPValue> attrLocationZone,Map<String, IdentityPValue> attrLocationBand, 
			Map<String, IdentityPValue> attrRep, Map<String, IdentityPValue> attrYear,Map<String, IdentityPValue> attrType,Map<String, IdentityPValue> attrYield,
			Map<String, IdentityPValue> attrMN,Map<String, IdentityPValue> attrRM, double isElitePValue, double notElitePValue)
	{
		Iterator<DataRecord> it = testData.iterator();
		while ( it.hasNext()){
			DataRecord dr = it.next();
//			if(dr.ischeck){
//				continue;
//			}
//			Map<Boolean, Double> results = new HashMap<Boolean, Double>();
//			results.put(Boolean.TRUE, (double) 0);
//			results.put(Boolean.FALSE,(double) 0);
//			Set<Boolean> keys = results.keySet();
//			Iterator<Boolean> itkey = keys.iterator();
//			while(itkey.hasNext()){
//				Boolean result = itkey.next();
				double zonePValue = getAttrPValue(attrLocationZone, String.valueOf(dr.locationZone), true);
				double bandPValue = getAttrPValue(attrLocationBand, String.valueOf(dr.locationBand), true);
				double repPValue = getAttrPValue(attrRep, String.valueOf(dr.rep), true);
				double yearPValue = getAttrPValue(attrYear, String.valueOf(dr.plantyear), true);
				double typePValue = getAttrPValue(attrType, String.valueOf(dr.type), true);
				double yieldPValue = getAttrPValue(attrYield, String.valueOf(dr.yieldIdentity), true);
				double mnPValue = getAttrPValue(attrMN, String.valueOf(dr.mn), true);
				double rmPValue = getAttrPValue(attrRM, String.valueOf(dr.rmIdentity), true);
				double pv = zonePValue * bandPValue * repPValue * yearPValue * typePValue * yieldPValue * mnPValue * rmPValue;
				if (true){
					pv = pv * isElitePValue;
				}
//				else{
//					pv = pv * notElitePValue;
//				}
//				results.put(result, pv);
//			}
//			if(results.get(Boolean.TRUE).doubleValue() >= results.get(Boolean.FALSE).doubleValue()){
//				dr.iselite = true;
//			}else{
//				dr.iselite = false;
//			}
			dr.elitePValue = pv;
		}
			
	}
	private double getAttrPValue(Map<String, IdentityPValue> attr, String identity, boolean iselite){
		if (attr.containsKey(identity)){
			if(iselite){
				return attr.get(identity).isElitePValue;
			}
//			else{
//				return attr.get(identity).notElitePValue;
//			}
		}else{
			return 0;//for new identity from test, use 0;
		}
		return 0;
	}
	
	private List<DataRecord> fetchFromTestData(String[] testdata){
		List<DataRecord> retlist = new ArrayList<DataRecord>();
		int len = testdata.length;
		for (int i = 0; i < len; i++) {
			String[] items = testdata[i].split(","); // 16 items here, so 0-15
			boolean checked = parseBoolean(items[15]);
//			if (checked){
//				continue;
//			}
			DataRecord dr = new DataRecord();
			String expId = items[0];
			dr.expid = expId;
			dr.loccd = items[1];
			dr.rep = Integer.parseInt(items[2]);
			
			String vid = items[3];
			dr.varietyid = vid;
			String year = parseYear(items[4]);
			dr.plantyear = year;
			String type = parseVarietyType(items[5]);
			dr.type = type;
			double yield = Double.parseDouble(items[6]);
			dr.yield = yield;
			dr.yieldIdentity = getYieldIdentity(yield);
			dr.mn = parseMN(items[7]);
			double rm = Double.parseDouble(items[8]);
			dr.rm = rm;
			dr.rmIdentity = getRMIdentity(rm);
			
			dr.iselite = false;
			dr.ischeck = checked;
			retlist.add(dr);
		}
		return retlist;
	}
	
	private void calculateAttrPValue(Map<String, IdentityPValue> attr, long isEliteNumber, long notEliteNumber){
		Collection<IdentityPValue> ipvs = attr.values();
		Iterator<IdentityPValue> it = ipvs.iterator();
		while( it.hasNext()){
			IdentityPValue ipv = it.next();
			ipv.isElitePValue = (double)ipv.isEliteNumber / (double)isEliteNumber;
//			ipv.notElitePValue = (double)ipv.notEliteNumber / (double)notEliteNumber;
		}
	}
	
	private long[] learnFromTraining(Map<String, IdentityPValue> attrLocationZone, Map<String, IdentityPValue> attrLocationBand,
			Map<String, IdentityPValue> attrRep, Map<String, IdentityPValue> attrYear,Map<String, IdentityPValue> attrType,
			Map<String, IdentityPValue> attrYield, Map<String, IdentityPValue> attrMN, Map<String, IdentityPValue> attrRM,
			List<DataRecord> trainingList)
	{
		long [] retlist = new long[1];
		long  totalIsEliteTrainingLength = 0;
		//long totalNotEliteTrainingLength = 0;
		Iterator<DataRecord> it = trainingList.iterator();
		while ( it.hasNext()){
			DataRecord dr = it.next();
			if(dr.iselite){
				totalIsEliteTrainingLength++;
			}
//			else{
//				totalNotEliteTrainingLength++;
//			}
			keepIdentity(attrLocationZone, String.valueOf(dr.locationZone), dr.iselite);
			keepIdentity(attrLocationBand, String.valueOf(dr.locationBand), dr.iselite);
			keepIdentity(attrRep, String.valueOf(dr.rep), dr.iselite);
			keepIdentity(attrYear, dr.plantyear, dr.iselite);
			keepIdentity(attrType, dr.type, dr.iselite);
			keepIdentity(attrYield, String.valueOf(dr.yieldIdentity), dr.iselite);
			keepIdentity(attrMN, String.valueOf(dr.mn), dr.iselite);
			keepIdentity(attrRM, String.valueOf(dr.rmIdentity), dr.iselite);
		}
		retlist[0] = totalIsEliteTrainingLength;
//		retlist[1] = totalNotEliteTrainingLength;
		return retlist;
	}
	
	private void keepIdentity(Map<String, IdentityPValue> attr, String identity, boolean iselite){
		if (attr.containsKey(identity)){
			IdentityPValue ipv = attr.get(identity);
			if (iselite){
				ipv.isEliteNumber++;
			}
//			else {
//				ipv.notEliteNumber++;
//			}
		}else{
			IdentityPValue ipv = new IdentityPValue();
			ipv.identity = identity;
			if(iselite){
				ipv.isEliteNumber = 1;
//				ipv.notEliteNumber = 0;
			}
			else{
				ipv.isEliteNumber = 0;
//				ipv.notEliteNumber = 1;
			}
			attr.put(identity, ipv);
		}
	}
	
	private void fillLocationInfo(List<DataRecord> sourceData, Map<String,LocationDetail> locations){
		Iterator<DataRecord> it = sourceData.iterator();
		while ( it.hasNext()){
			DataRecord dr = it.next();
			String key = dr.loccd + "-" + dr.plantyear;
			if (locations.containsKey(key)){
				LocationDetail ld = locations.get(key);
				dr.locationZone = Integer.parseInt(ld.zone);
				dr.locationBand = Integer.parseInt(ld.band);
			}else{
				//if unknowns, no record
				dr.locationBand = -1;
				dr.locationZone = -1;
			}
		}
	}
	
	
	private List<DataRecord> fetchFromTraining(String[] training){
		List<DataRecord> retlist = new ArrayList<DataRecord>();
		int len = training.length;
		for (int i = 0; i < len; i++) {
			String[] items = training[i].split(","); // 17 items here, so 0-16
			boolean checked = parseBoolean(items[15]);
			if (checked){
				continue;
			}
			DataRecord dr = new DataRecord();
			String expId = items[0];
			dr.expid = expId;
			dr.loccd = items[1];
			dr.rep = Integer.parseInt(items[2]);
			
			String vid = items[3];
			dr.varietyid = vid;
			String year = parseYear(items[4]);
			dr.plantyear = year;
			String type = parseVarietyType(items[5]);
			dr.type = type;
			double yield = Double.parseDouble(items[6]);
			dr.yield = yield;
			dr.yieldIdentity = getYieldIdentity(yield);
			dr.mn = parseMN(items[7]);
			double rm = Double.parseDouble(items[8]);
			dr.rm = rm;
			dr.rmIdentity = getRMIdentity(rm);
			boolean iselite = parseBoolean(items[16]);
			dr.iselite = iselite;
			dr.ischeck = false;
			retlist.add(dr);
		}
		return retlist;
	}
	
	private Map<String,LocationDetail> getLocationDetailList(String[] locations){
		Map<String,LocationDetail> retlist = new HashMap<String, LocationDetail>();
		int len = locations.length;
		for (int i = 0; i < len; i++) {
			String[] items = locations[i].split(","); //4 items
			LocationDetail ld = new LocationDetail();
			ld.loccd = items[0];
			ld.year = items[1];
			ld.zone = items[2];
			ld.band = items[3];
			retlist.put(items[0] + "-" + items[1], ld);
		}
		return retlist;
	}
	
	
	
	private int parseMN(String str){
		if( str == null || str.equalsIgnoreCase("NULL")){
			return 0; //ifnull, use 0
		}
		double mn = Double.parseDouble(str);
		return (int)Math.ceil(mn/10);
//		if(mn<=100){
//			return 1;
//		}else if(mn>100 && mn<=120){
//			return 2;
//		}else if(mn>120 && mn <=140){
//			return 3;
//		}else if(mn>140 && mn <=160){
//			return 4;
//		}else {
//			return 5;
//		}
	}
	
	private boolean parseBoolean(String s) {  //0 or 1
		if (s == null) {
			return false;
		} else if (s.trim().equals("")) {
			return false;
		} else if (s.trim().equalsIgnoreCase("0")) {
			return false;
		} else {
			return true;
		}
	}
	
	private String parseYear(String str) { //####-XX-XX or ####/##/##
		if (str.indexOf("-")>0){
			return str.substring(0, str.indexOf("-"));
		}
		if (str.indexOf("/")>0){
			return str.substring(0, str.indexOf("/"));
		}
		return str.substring(0, 4);
	}
	
	private String parseVarietyType(String rawType) {
		if ("null".equalsIgnoreCase(rawType)) {
			return VarietyTypeEnum.TYPE_UNKNOWN;
		} else if (VarietyTypeEnum.TYPE_CONV.equalsIgnoreCase(rawType)) {
			return VarietyTypeEnum.TYPE_CONV;
		} else if (VarietyTypeEnum.TYPE_RR1.equalsIgnoreCase(rawType)) {
			return VarietyTypeEnum.TYPE_RR1;
		} else if (VarietyTypeEnum.TYPE_RR2Y.equalsIgnoreCase(rawType)) {
			return VarietyTypeEnum.TYPE_RR2Y;
		} else {
			return VarietyTypeEnum.TYPE_UNKNOWN;
		}
	}
	
	private int getYieldIdentity(double yield){
		if(yield<=0){
			return 0;
		}
		return (int)Math.ceil(yield/10.0);
//		if(yield<=30){
//			return 0;
//		}else if(yield>30 && yield <=60){
//			return 1;
//		}else if(yield>60 && yield<=90){
//			return 2;
//		}else if(yield>90 && yield <=120){
//			return 3;
//		}else{
//			return 4;
//		}
	}
	private int getRMIdentity(double yield){
		if(yield<=0){
			return 0;
		}
		return (int)Math.ceil(yield);
//		else if(yield>0 && yield <=2){
//			return 1;
//		}else if(yield>2 && yield<=4){
//			return 2;
//		}else if(yield>4 && yield <=6){
//			return 3;
//		}else if(yield>6 && yield <=8){
//			return 4;
//		}else{
//			return 5;
//		}
	}
}


class IdentityPValue {
	public String identity;
	public int isEliteNumber;
	public double isElitePValue;
//	public int notEliteNumber;
//	public double notElitePValue;
}

class TestIdentityPValue extends IdentityPValue {
	public boolean ischeck;
	public boolean equals(Object o){
		if(!(o instanceof TestIdentityPValue)){
			return false;
		}
		TestIdentityPValue ipv = (TestIdentityPValue)o;
		if(ipv.identity.equals(this.identity)){
			return true;
		}else{
			return false;
		}
	}
}

class DataRecord {
	public String expid;//will not consider
	public String loccd;
	public int locationZone;
	public int locationBand;
	public int rep;
	public String varietyid;
	public String plantyear; //####/#/##, or ####-XX-XX
	public String type;//maybe null;
	public double yield; // 0 - 260
	public int yieldIdentity;
	public int mn; //maybe null
	public double rm; // -2 - 10
	public int rmIdentity;
	public boolean ischeck;
	public boolean iselite;
	public double elitePValue;
}

class VarietyTypeEnum {
	public static String TYPE_CONV = "conv";
	public static String TYPE_RR1 = "RR1";
	public static String TYPE_RR2Y = "RR2Y";
	public static String TYPE_UNKNOWN = "UNKNOWN";
}

class LocationDetail {
	public String loccd;
	public String year;
	public String zone;
	public String band;
}
class IsElitePValueComparator implements Comparator<TestIdentityPValue> {
	public int compare(TestIdentityPValue o1, TestIdentityPValue o2) {
		if (o1.isElitePValue > o2.isElitePValue) {
			return -1;
		} else if (o1.isElitePValue < o2.isElitePValue) {
			return 1;
		} else {
			return 0;
		}
	}
}
class NotElitePValueComparator implements Comparator<TestIdentityPValue> {
	public int compare(TestIdentityPValue o1, TestIdentityPValue o2) {
		if (o1.isElitePValue > o2.isElitePValue) {
			return -1;
		} else if (o1.isElitePValue < o2.isElitePValue) {
			return 1;
		} else {
			return 0;
		}
	}
}