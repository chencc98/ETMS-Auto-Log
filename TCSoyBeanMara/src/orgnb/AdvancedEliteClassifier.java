/**
 * 
 */
package orgnb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
		long totalNotEliteTrainingLength = 0;
		double isElitePValue = 0;
		double notElitePValue = 0;
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
		totalNotEliteTrainingLength = temp[1];
		isElitePValue = totalIsEliteTrainingLength / totalTrainingLength;
		notElitePValue = totalNotEliteTrainingLength / totalTrainingLength;
		calculateAttrPValue(attrLocationZone, totalIsEliteTrainingLength,totalNotEliteTrainingLength );
		calculateAttrPValue(attrLocationBand, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
		calculateAttrPValue(attrRep, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
		calculateAttrPValue(attrYear, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
		calculateAttrPValue(attrType, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
		calculateAttrPValue(attrYield, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
		calculateAttrPValue(attrMN, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
		calculateAttrPValue(attrRM, totalIsEliteTrainingLength,totalNotEliteTrainingLength);
		//now the training is done. we start to identity the test data
		List<DataRecord> testList = fetchFromTestData(testData);
		fillLocationInfo(testList, locationList);
		
	}
	
	private void analyzeTestList
	
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
			ipv.isElitePValue = ipv.isEliteNumber / isEliteNumber;
			ipv.notElitePValue = ipv.notEliteNumber / notEliteNumber;
		}
	}
	
	private long[] learnFromTraining(Map<String, IdentityPValue> attrLocationZone, Map<String, IdentityPValue> attrLocationBand,
			Map<String, IdentityPValue> attrRep, Map<String, IdentityPValue> attrYear,Map<String, IdentityPValue> attrType,
			Map<String, IdentityPValue> attrYield, Map<String, IdentityPValue> attrMN, Map<String, IdentityPValue> attrRM,
			List<DataRecord> trainingList)
	{
		long [] retlist = new long[2];
		long  totalIsEliteTrainingLength = 0;
		long totalNotEliteTrainingLength = 0;
		Iterator<DataRecord> it = trainingList.iterator();
		while ( it.hasNext()){
			DataRecord dr = it.next();
			if(dr.iselite){
				totalIsEliteTrainingLength++;
			}else{
				totalNotEliteTrainingLength++;
			}
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
		retlist[1] = totalNotEliteTrainingLength;
		return retlist;
	}
	
	private void keepIdentity(Map<String, IdentityPValue> attr, String identity, boolean iselite){
		if (attr.containsKey(identity)){
			IdentityPValue ipv = attr.get(identity);
			if (iselite){
				ipv.isEliteNumber++;
			}else {
				ipv.notEliteNumber++;
			}
		}else{
			IdentityPValue ipv = new IdentityPValue();
			ipv.identity = identity;
			if(iselite){
				ipv.isEliteNumber = 1;
				ipv.notEliteNumber = 0;
			}else{
				ipv.isEliteNumber = 0;
				ipv.notEliteNumber = 1;
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
		if(mn<=100){
			return 1;
		}else if(mn>100 && mn<=120){
			return 2;
		}else if(mn>120 && mn <=140){
			return 3;
		}else if(mn>140 && mn <=160){
			return 4;
		}else {
			return 5;
		}
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
		if(yield<=30){
			return 0;
		}else if(yield>30 && yield <=60){
			return 1;
		}else if(yield>60 && yield<=90){
			return 2;
		}else if(yield>90 && yield <=120){
			return 3;
		}else{
			return 4;
		}
	}
	private int getRMIdentity(double yield){
		if(yield<=0){
			return 0;
		}else if(yield>0 && yield <=2){
			return 1;
		}else if(yield>2 && yield<=4){
			return 2;
		}else if(yield>4 && yield <=6){
			return 3;
		}else if(yield>6 && yield <=8){
			return 4;
		}else{
			return 5;
		}
	}
}


class IdentityPValue {
	public String identity;
	public int isEliteNumber;
	public double isElitePValue;
	public int notEliteNumber;
	public double notElitePValue;
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