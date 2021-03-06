import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
//
//import org.Experiment;
//import org.MinMaxValuePair;
//import org.QuarterEnum;
//import org.TrialMapKey;
//import org.TrialMapValue;
//import org.TrialTypeEnum;
//import org.VarietyDetail;
//import org.VarietyEliteScore;
//import org.VarietyEliteScoreComparator;
//import org.VarietyTypeEnum;
//import org.WeightEnum;

/**
 * TopCoder Soybean Oracle Series
 * Soybean Marathon Match 6
 * Chen Xianpeng (carl)
 * 2012/09/09 398877
 */

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
		Map<String, Experiment> experiments = new HashMap<String, Experiment>();
		String[] testID = getExperimentsFromTestData(testData, experiments);
		getExperimentsFromTrainingData(trainingData, experiments, testID);
		analyzeExperiments(experiments);
		generateAverageScoreInExperiments(experiments);
		generateMeanVarInExperiments(experiments);
		String[] sortedTestId = getSortedEliteVariety(experiments);
		String[] checkedVariety = getCheckedVariety(experiments);
		int[] ret = combineConvert(sortedTestId, checkedVariety);
		return ret;
	}

	private int[] combineConvert(String[] first, String[] second) {
		int len = first.length + second.length;
		int firstlen = first.length;
		int[] ret = new int[len];
		int i = 0;
		for (i = 0; i < firstlen; i++) {
			ret[i] = Integer.parseInt(first[i]);
		}
		for (; i < len; i++) {
			ret[i] = Integer.parseInt(second[i - firstlen]);
		}
		return ret;
	}

	/*
	 * ExperimentID LOCCD Rep VarietyID PlantDate Type Yield MN RM PD_CR PB_CR
	 * FL_CR EMRGR PLHTN HLDGR IsCheck
	 */
	private String[] getExperimentsFromTestData(String[] testData,
			Map<String, Experiment> experiments) {
		List<String> retVID = new ArrayList<String>();
		int len = testData.length;
		for (int i = 0; i < len; i++) {
			String[] items = testData[i].split(","); // 16 items here, so 0-15
			String expId = items[0];
			Experiment exp;
			if (experiments.containsKey(expId)) {
				exp = experiments.get(expId);
			} else {
				exp = new Experiment(expId);
				experiments.put(expId, exp);
			}

			Map<String, VarietyDetail> varieties = exp.varietyInfoMap;
			String vid = items[3];
			// save the variety id into the return list first
			if (!retVID.contains(vid)) {
				retVID.add(vid);
			}
			// handle the variety detail map
			VarietyDetail varietyDetail;
			if (!varieties.containsKey(vid)) {
				String type = parseVarietyType(items[5]);
				double rm = Double.parseDouble(items[8]);
				boolean checked = parseBoolean(items[15]);
				varietyDetail = new VarietyDetail(vid, type, rm, checked, true);
				varieties.put(vid, varietyDetail);
			}
			// handle the trial
			String loccd = items[1];
			String year = parseYear(items[4]);
			String rep = items[2];
			TrialMapKey tmkey = new TrialMapKey(loccd, year, rep);
			int quarter = parseQuarter(items[4]);
			double yield = Double.parseDouble(items[6]);
			TrialMapValue tmvalue = new TrialMapValue(vid, quarter, yield);
			boolean checked = parseBoolean(items[15]);
			Set<TrialMapValue> detailTrials;
			if (exp.trials.containsKey(tmkey)) {
				if (checked) {
					detailTrials = exp.trials.get(tmkey).get(
							TrialTypeEnum.CHECKEDSET);
				} else {
					detailTrials = exp.trials.get(tmkey).get(
							TrialTypeEnum.NEWVARIETYSET);
				}
				detailTrials.add(tmvalue);
			} else {
				detailTrials = new HashSet<TrialMapValue>();
				detailTrials.add(tmvalue);
				Map<String, Set<TrialMapValue>> tmp = new HashMap<String, Set<TrialMapValue>>();
				if (checked) {
					tmp.put(TrialTypeEnum.CHECKEDSET, detailTrials);
					tmp.put(TrialTypeEnum.NEWVARIETYSET,
							new HashSet<TrialMapValue>());
				} else {
					tmp.put(TrialTypeEnum.CHECKEDSET,
							new HashSet<TrialMapValue>());
					tmp.put(TrialTypeEnum.NEWVARIETYSET, detailTrials);
				}
				exp.trials.put(tmkey, tmp);
			}
			// handle the min and max value
			if (exp.minMaxPair.containsKey(tmkey)) {
				MinMaxValuePair pair = exp.minMaxPair.get(tmkey);
				if (pair.getMinValue() > yield) {
					pair.setMinValue(yield);
				}
				if (pair.getMaxValue() < yield) {
					pair.setMaxValue(yield);
				}
			} else {
				MinMaxValuePair pair = new MinMaxValuePair(yield-0.5, yield);
				exp.minMaxPair.put(tmkey, pair);
			}
		}
		return retVID.toArray(new String[1]);
	}

	private void getExperimentsFromTrainingData(String[] trainingData,
			Map<String, Experiment> experiments, String[] testID) {
		int len = trainingData.length;
		Arrays.sort(testID);
		for (int i = 0; i < len; i++) {
			String[] items = trainingData[i].split(","); // 17 items here, so
															// 0-16
			String vid = items[3];
			boolean checked = parseBoolean(items[15]);
			if (Arrays.binarySearch(testID, vid) < 0 && !checked) {
				continue;
			}
			String expId = items[0];
			Experiment exp;
			if (experiments.containsKey(expId)) {
				exp = experiments.get(expId);
			} else {
				exp = new Experiment(expId);
				experiments.put(expId, exp);
			}

			Map<String, VarietyDetail> varieties = exp.varietyInfoMap;

			// handle the variety detail map
			VarietyDetail varietyDetail;
			if (!varieties.containsKey(vid)) {
				String type = parseVarietyType(items[5]);
				double rm = Double.parseDouble(items[8]);
				boolean checked2 = parseBoolean(items[15]);
				varietyDetail = new VarietyDetail(vid, type, rm, checked2,
						false);
				varieties.put(vid, varietyDetail);
			}
			// handle the trial
			String loccd = items[1];
			String year = parseYear(items[4]);
			String rep = items[2];
			TrialMapKey tmkey = new TrialMapKey(loccd, year, rep);
			int quarter = parseQuarter(items[4]);
			double yield = Double.parseDouble(items[6]);
			TrialMapValue tmvalue = new TrialMapValue(vid, quarter, yield);
			boolean checked3 = parseBoolean(items[15]);
			Set<TrialMapValue> detailTrials;
			if (exp.trials.containsKey(tmkey)) {
				if (checked3) {
					detailTrials = exp.trials.get(tmkey).get(
							TrialTypeEnum.CHECKEDSET);
				} else {
					detailTrials = exp.trials.get(tmkey).get(
							TrialTypeEnum.NEWVARIETYSET);
				}
				detailTrials.add(tmvalue);
			} else {
				detailTrials = new HashSet<TrialMapValue>();
				detailTrials.add(tmvalue);
				Map<String, Set<TrialMapValue>> tmp = new HashMap<String, Set<TrialMapValue>>();
				if (checked3) {
					tmp.put(TrialTypeEnum.CHECKEDSET, detailTrials);
					tmp.put(TrialTypeEnum.NEWVARIETYSET,
							new HashSet<TrialMapValue>());
				} else {
					tmp.put(TrialTypeEnum.CHECKEDSET,
							new HashSet<TrialMapValue>());
					tmp.put(TrialTypeEnum.NEWVARIETYSET, detailTrials);
				}
				exp.trials.put(tmkey, tmp);
			}
			// handle the min and max value
			if (exp.minMaxPair.containsKey(tmkey)) {
				MinMaxValuePair pair = exp.minMaxPair.get(tmkey);
				if (pair.getMinValue() > yield) {
					pair.setMinValue(yield);
				}
				if (pair.getMaxValue() < yield) {
					pair.setMaxValue(yield);
				}
			} else {
				MinMaxValuePair pair = new MinMaxValuePair(yield - 0.5, yield); // so
																				// there
																				// will
																				// be
																				// no
																				// divide
																				// zero
																				// error
				exp.minMaxPair.put(tmkey, pair);
			}
		}
	}

	private void analyzeExperiments(Map<String, Experiment> experiments) {
		Collection<Experiment> allexp = experiments.values();
		Iterator<Experiment> it = allexp.iterator();
		while (it.hasNext()) {
			Experiment exp = it.next();
			Set<TrialMapKey> keySet = exp.trials.keySet();
			Iterator<TrialMapKey> keyIt = keySet.iterator();
			while (keyIt.hasNext()) {
				TrialMapKey key = keyIt.next();
				Set<TrialMapValue> newVariety = exp.trials.get(key).get(
						TrialTypeEnum.NEWVARIETYSET);
				Set<TrialMapValue> checkedVariety = exp.trials.get(key).get(
						TrialTypeEnum.CHECKEDSET);
				Iterator<TrialMapValue> newVarietyIt = newVariety.iterator();

				while (newVarietyIt.hasNext()) {
					TrialMapValue tmvalue = newVarietyIt.next();
					double tmpscore = 0;
					Iterator<TrialMapValue> checkedVarietyIt = checkedVariety
							.iterator();
					while (checkedVarietyIt.hasNext()) {
						TrialMapValue checktmvalue = checkedVarietyIt.next();
						MinMaxValuePair pair = exp.minMaxPair.get(key);
						double minYield = pair.getMinValue();
						double maxYield = pair.getMaxValue();
						double newVarietyRMYield = exp.varietyInfoMap.get(
								tmvalue.getVarietyId()).getRMYield();
						double checkedVarietyRMYield = exp.varietyInfoMap.get(
								checktmvalue.getVarietyId()).getRMYield();
						if ((tmvalue.getYield() + newVarietyRMYield) > maxYield) {
							maxYield = tmvalue.getYield() + newVarietyRMYield;
						}
						if ((checktmvalue.getYield() + checkedVarietyRMYield) > maxYield) {
							maxYield = checktmvalue.getYield()
									+ checkedVarietyRMYield;
						}
						double tmpscoreIn = ((tmvalue.getYield() + newVarietyRMYield) - (checktmvalue
								.getYield() + checkedVarietyRMYield))
								/ (maxYield - minYield);
						if (tmvalue.getQuarter() == checktmvalue.getQuarter()
								&& tmvalue.getQuarter() != QuarterEnum.UNKNOWN) {
							tmpscoreIn = tmpscoreIn
									* (1 + WeightEnum.SAMEQUARTERWEIGHT);
						}
						tmpscore += tmpscoreIn;
					}
					tmvalue.setOneTrialScore(tmpscore);
					// handle stat
					VarietyDetail vd = exp.varietyInfoMap.get(tmvalue
							.getVarietyId());
					vd.setQueryTime(vd.getQueryTime() + 1);
					vd.setTotalScore(vd.getTotalScore()
							+ tmvalue.getOneTrialScore());
					vd.setTotalYield(vd.getTotalYield() + tmvalue.getYield());

				}
			}
			// ONE experiment first go through is done

		}
	}

	private void generateAverageScoreInExperiments(
			Map<String, Experiment> experiments) {
		Collection<Experiment> allexp = experiments.values();
		Iterator<Experiment> it = allexp.iterator();
		while (it.hasNext()) {
			Experiment exp = it.next();
			Collection<VarietyDetail> allvd = exp.varietyInfoMap.values();
			Iterator<VarietyDetail> vdit = allvd.iterator();
			while (vdit.hasNext()) {
				VarietyDetail vd = vdit.next();
				if (!vd.isCheck()) {
					vd.setAverageScore(vd.getTotalScore() / vd.getQueryTime());
					if (vd.getType()
							.equalsIgnoreCase(VarietyTypeEnum.TYPE_CONV)) {
						vd.setAverageScore(vd.getAverageScore()
								* (1 + WeightEnum.TYPECONVWEIGHT));
					} else if (vd.getType().equalsIgnoreCase(
							VarietyTypeEnum.TYPE_RR1)) {
						vd.setAverageScore(vd.getAverageScore()
								* (1 + WeightEnum.TYPERR1WEIGHT));
					} else if (vd.getType().equalsIgnoreCase(
							VarietyTypeEnum.TYPE_RR2Y)) {
						vd.setAverageScore(vd.getAverageScore()
								* (1 + WeightEnum.TYPERR2YWEIGHT));
					}

				}
			}
		}
	}

	private void generateMeanVarInExperiments(
			Map<String, Experiment> experiments) {
		Collection<Experiment> allexp = experiments.values();
		Iterator<Experiment> it = allexp.iterator();
		while (it.hasNext()) {
			Experiment exp = it.next();
			Collection<VarietyDetail> allvd = exp.varietyInfoMap.values();
			Iterator<VarietyDetail> vdit = allvd.iterator();
			while (vdit.hasNext()) {
				VarietyDetail vd = vdit.next();
				if (!vd.isCheck()) {
					vd.setAverageYield(vd.getTotalYield() / vd.getQueryTime());

				}
			}

			// start to work
			Set<TrialMapKey> keySet = exp.trials.keySet();
			Iterator<TrialMapKey> keyIt = keySet.iterator();
			while (keyIt.hasNext()) {
				TrialMapKey key = keyIt.next();
				Set<TrialMapValue> newVariety = exp.trials.get(key).get(
						TrialTypeEnum.NEWVARIETYSET);
				Iterator<TrialMapValue> newVarietyIt = newVariety.iterator();

				while (newVarietyIt.hasNext()) {
					TrialMapValue tmvalue = newVarietyIt.next();
					VarietyDetail vd = exp.varietyInfoMap.get(tmvalue
							.getVarietyId());
					vd.setMeanVar(vd.getMeanVar()
							+ Math.pow(
									(tmvalue.getYield() - vd.getAverageYield()),
									2));

				}
			}
			Iterator<VarietyDetail> anothervdit = allvd.iterator();
			double minMean = 0;
			double maxMean = 0;
			while (anothervdit.hasNext()) {
				VarietyDetail vd = anothervdit.next();
				if (!vd.isCheck()) {
					vd.setMeanVar(Math.pow(vd.getMeanVar() / vd.getQueryTime(),
							0.5));
					if (vd.getMeanVar() < minMean) {
						minMean = vd.getMeanVar();
					}
					if (vd.getMeanVar() > maxMean) {
						maxMean = vd.getMeanVar();
					}
	
				}
			}

			// go through again to normalize
			Iterator<VarietyDetail> thirdvdit = allvd.iterator();

			while (thirdvdit.hasNext()) {
				VarietyDetail vd = thirdvdit.next();
				if (!vd.isCheck()) {
					vd.setMeanVar((vd.getMeanVar() - minMean)
							/ (maxMean - minMean));

	
				}
			}
		}
	}

	private String[] getSortedEliteVariety(Map<String, Experiment> experiments) {
		Map<String, VarietyEliteScore> score = new HashMap<String, VarietyEliteScore>();
		Collection<Experiment> allexp = experiments.values();
		Iterator<Experiment> it = allexp.iterator();
		while (it.hasNext()) {
			Experiment exp = it.next();
			Collection<VarietyDetail> allvd = exp.varietyInfoMap.values();
			Iterator<VarietyDetail> vdit = allvd.iterator();
			while (vdit.hasNext()) {
				VarietyDetail vd = vdit.next();
				if (!vd.isCheck()) {
					if (score.containsKey(vd.getVarietyId())) {
						VarietyEliteScore ss = score.get(vd.getVarietyId());
						ss.timer++;
						ss.totalScore += vd.getAverageScore()
								* WeightEnum.HIGHYIELDWIGHT
								+ (1 - vd.getMeanVar())
								* (1 - WeightEnum.HIGHYIELDWIGHT);
					} else {
						VarietyEliteScore add = new VarietyEliteScore(
								vd.getVarietyId());
						add.timer = 1;
						add.totalScore = vd.getAverageScore()
								* WeightEnum.HIGHYIELDWIGHT
								+ (1 - vd.getMeanVar())
								* (1 - WeightEnum.HIGHYIELDWIGHT);
						score.put(vd.getVarietyId(), add);
					}
				}
			}
		}
		Collection<VarietyEliteScore> scoreValues = score.values();
		VarietyEliteScore[] scoreArray = scoreValues
				.toArray(new VarietyEliteScore[1]);
		Arrays.sort(scoreArray, new VarietyEliteScoreComparator());
		List<String> retlist = new ArrayList<String>();
		int len = scoreArray.length;
		for (int i = 0; i < len; i++) {
			retlist.add(scoreArray[i].vid);

		}
		return retlist.toArray(new String[1]);
	}

	private String[] getCheckedVariety(Map<String, Experiment> experiments) {
		List<String> retlist = new ArrayList<String>();
		Collection<Experiment> allexp = experiments.values();
		Iterator<Experiment> it = allexp.iterator();
		while (it.hasNext()) {
			Experiment exp = it.next();
			Collection<VarietyDetail> allvd = exp.varietyInfoMap.values();
			Iterator<VarietyDetail> vdit = allvd.iterator();
			while (vdit.hasNext()) {
				VarietyDetail vd = vdit.next();
				if (vd.isCheck() && vd.isTestData()) {
					if (!retlist.contains(vd.getVarietyId())) {
						retlist.add(vd.getVarietyId());
					}
				}
			}
		}
		return retlist.toArray(new String[1]);
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

	private boolean parseBoolean(String s) {
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

	private String parseYear(String str) {
		return str.split("-")[0];
	}

	private int parseQuarter(String str) {
		String q = str.split("-")[1];
		if (q.equalsIgnoreCase("XX")) {
			return QuarterEnum.UNKNOWN;
		}
		int qint = Integer.parseInt(q);
		switch (qint) {
		case 1:
		case 2:
		case 3:
			return QuarterEnum.Q1;
		case 4:
		case 5:
		case 6:
			return QuarterEnum.Q2;
		case 7:
		case 8:
		case 9:
			return QuarterEnum.Q3;
		case 10:
		case 11:
		case 12:
			return QuarterEnum.Q4;
		default:
			return QuarterEnum.UNKNOWN;
		}
	}
}

class TrialMapKey {
	private String _loccd;
	private String _year;
	private String _rep;

	public TrialMapKey(String loc, String year, String rep) {
		this._loccd = loc;
		this._rep = rep;
		this._year = year;
	}

	public String getLoccd() {
		return this._loccd;
	}

	public void setLoccd(String loc) {
		this._loccd = loc;
	}

	public String getYear() {
		return this._year;
	}

	public void setYear(String y) {
		this._year = y;
	}

	public String getRep() {
		return this._rep;
	}

	public void setRep(String r) {
		this._rep = r;
	}

	public boolean equals(Object o) {
		if (!(o instanceof TrialMapKey)) {
			return false;
		}
		TrialMapKey key = (TrialMapKey) o;
		if (key.getLoccd() == null || key.getRep() == null
				|| key.getYear() == null) {
			return false;
		}
		if (key.getLoccd().equalsIgnoreCase(_loccd)
				&& key.getRep().equalsIgnoreCase(_rep)
				&& key.getYear().equalsIgnoreCase(_year)) {
			return true;
		} else {
			return false;
		}

	}

	public int hashCode() {
		int ret = 0;
		ret += (this._loccd == null ? 0 : this._loccd.hashCode());
		ret += (this._rep == null ? 0 : this._rep.hashCode());
		ret += (this._year == null ? 0 : this._year.hashCode());
		return ret;
	}
}

class TrialMapValue {
	private String _varietyid;
	private int _quarter; // 0 is unknown; 1 is q1; 2 is q2; 3 is q3; 4 is q4
	private double _yield;
	private double _onetrialscore;

	public TrialMapValue(String id, int quar, double yield) {
		this._varietyid = id;
		this._quarter = quar;
		this._yield = yield;
		this._onetrialscore = 0;
	}

	public String getVarietyId() {
		return this._varietyid;
	}

	public void setVarietyId(String val) {
		this._varietyid = val;
	}

	public int getQuarter() {
		return this._quarter;
	}

	public void setQuarter(int val) {
		this._quarter = val;
	}

	public double getYield() {
		return this._yield;
	}

	public void setYield(double val) {
		this._yield = val;
	}

	public double getOneTrialScore() {
		return this._onetrialscore;
	}

	public void setOneTrialScore(double val) {
		this._onetrialscore = val;
	}
}

class MinMaxValuePair {
	private double _minvalue;
	private double _maxvalue;

	public MinMaxValuePair(double min, double max) {
		this._maxvalue = max;
		this._minvalue = min;
	}

	public double getMaxValue() {
		return this._maxvalue;
	}

	public void setMaxValue(double val) {
		this._maxvalue = val;
	}

	public double getMinValue() {
		return this._minvalue;
	}

	public void setMinValue(double val) {
		this._minvalue = val;
	}
}

class VarietyDetail {
	private String _varietyid;
	private String _type; // conv; RR1 ; RR2
	private double _rm;
	private double _rm_yield; // rm/0.1 * 0.6
	private boolean _isCheckOrElite; // true is checked, false is not checked
	private boolean _isTestData; // if testdata, will do the calculation, and
									// output
	// below field is used for calculation
	private double _totalscore;
	private int _queryTime;
	private double _averscore; // will consider type here
	private double _totalyield;
	private double _averyield; // will not consider type here
	private double _meanVar; // used to determine yeild stable

	public VarietyDetail(String id, String type, double rm, boolean checked,
			boolean test) {
		this._varietyid = id;
		this._type = type;
		this._rm = rm;
		this._rm_yield = this._rm * 6;
		this._isCheckOrElite = checked;
		this._isTestData = test;
		this._totalscore = this._averscore = this._totalyield = this._averyield = this._meanVar = 0;
		this._queryTime = 0;
	}

	public String getVarietyId() {
		return this._varietyid;
	}

	public void setVarietyId(String val) {
		this._varietyid = val;
	}

	public String getType() {
		return this._type;
	}

	public void setType(String val) {
		this._type = val;
	}

	public double getRM() {
		return this._rm;
	}

	public double getRMYield() {
		return this._rm_yield;
	}

	public boolean isCheck() {
		return this._isCheckOrElite;
	}

	// public boolean isElite(){
	// return !this._isCheckOrElite;
	// }
	public boolean isTestData() {
		return this._isTestData;
	}

	public double getTotalScore() {
		return this._totalscore;
	}

	public void setTotalScore(double val) {
		this._totalscore = val;
	}

	public int getQueryTime() {
		return this._queryTime;
	}

	public void setQueryTime(int val) {
		this._queryTime = val;
	}

	public double getAverageScore() {
		return this._averscore;
	}

	public void setAverageScore(double val) {
		this._averscore = val;
	}

	public double getTotalYield() {
		return this._totalyield;
	}

	public void setTotalYield(double val) {
		this._totalyield = val;
	}

	public double getAverageYield() {
		return this._averyield;
	}

	public void setAverageYield(double val) {
		this._averyield = val;
	}

	public double getMeanVar() {
		return this._meanVar;
	}

	public void setMeanVar(double val) {
		this._meanVar = val;
	}
}

class Experiment {
	public String expId;
	public Map<String, VarietyDetail> varietyInfoMap;
	public Map<TrialMapKey, Map<String, Set<TrialMapValue>>> trials;
	public Map<TrialMapKey, MinMaxValuePair> minMaxPair;

	public Experiment(String eid) {
		this.expId = eid;
		varietyInfoMap = new HashMap<String, VarietyDetail>();
		trials = new HashMap<TrialMapKey, Map<String, Set<TrialMapValue>>>();
		minMaxPair = new HashMap<TrialMapKey, MinMaxValuePair>();
	}
}

class VarietyEliteScore {
	public String vid;
	public int timer;
	public double totalScore;

	// private double avgScore;
	public VarietyEliteScore(String vid) {
		this.vid = vid;
		timer = 0;
		totalScore = 0;
	}

	public double getAvgScore() {
		if (timer == 0) {
			return 0;
		} else {
			return totalScore / timer;
		}
	}

}

class VarietyEliteScoreComparator implements Comparator<VarietyEliteScore> {
	public int compare(VarietyEliteScore o1, VarietyEliteScore o2) {
		if (o1.getAvgScore() > o2.getAvgScore()) {
			return -1;
		} else if (o1.getAvgScore() < o2.getAvgScore()) {
			return 1;
		} else {
			return 0;
		}
	}
}

class QuarterEnum {
	public static int UNKNOWN = 0;
	public static int Q1 = 1;
	public static int Q2 = 2;
	public static int Q3 = 3;
	public static int Q4 = 4;
}

class WeightEnum {
	public static double SAMEQUARTERWEIGHT = 0.01;
	public static double TYPECONVWEIGHT = 0.02;
	public static double TYPERR1WEIGHT = 0.03;
	public static double TYPERR2YWEIGHT = 0.04;
	// public static double ZERODOUBLEABS = 0.0000001;
	public static double HIGHYIELDWIGHT = 0.7;
}

class VarietyTypeEnum {
	public static String TYPE_CONV = "conv";
	public static String TYPE_RR1 = "RR1";
	public static String TYPE_RR2Y = "RR2Y";
	public static String TYPE_UNKNOWN = "UNKNOWN";
}

class TrialTypeEnum {
	public static String CHECKEDSET = "CHECKEDSET";
	public static String NEWVARIETYSET = "NEWVARIETYSET";
}