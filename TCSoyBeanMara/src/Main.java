import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;




public class Main {
	public static void main(String [] argvs){
//				String testDataFile = "C:/Users/asus/Desktop/mara6/DataTraining/testData.txt";
//				String trainingDataFile = "C:/Users/asus/Desktop/mara6/DataTraining/DataTraining.csv";
//			String locInfoFile = "C:/Users/asus/Desktop/mara6/Locations.csv";
				String testDataFile = "C:/test/test.txt";
				String trainingDataFile = "C:/test/DataTraining.csv";
				String locInfoFile = "C:/test/Locations.csv";
				File testData = new File(testDataFile);
				File trainingData = new File(trainingDataFile);
				File locInfo = new File(locInfoFile);
			String [] testDataArr = new String[0];
				String [] trainingDataArr = new String[0];
				String [] locInfoArr = new String[0];
				try {
				BufferedReader br = new BufferedReader(new FileReader(testData));
					List<String> lines = new ArrayList<String>();
					String line;
					while((line=br.readLine()) != null){
						lines.add(line);
					}
					testDataArr =  lines.toArray(testDataArr);
					br.close();
					
					br = new BufferedReader(new FileReader(trainingData));
					lines = new ArrayList<String>();
					
					while((line=br.readLine()) != null){
						lines.add(line);
//						if(line.indexOf(",135762,")>=0){
//							System.out.println(line);
//						}
					}
					trainingDataArr = lines.toArray(trainingDataArr);
					br.close();
					
					br = new BufferedReader(new FileReader(locInfo));
					lines = new ArrayList<String>();
					
					while((line=br.readLine()) != null){
						lines.add(line);
					}
					locInfoArr =  lines.toArray(locInfoArr);
					br.close();
					br = null;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				
				orgnb.AdvancedEliteClassifier aec = new orgnb.AdvancedEliteClassifier();
				Calendar before = Calendar.getInstance();
				System.out.println("Start: " + before.getTime().toString());
				int [] list = aec.classify(testDataArr, trainingDataArr, locInfoArr);
				Calendar after = Calendar.getInstance();
				System.out.println("End: " + after.getTime().toString());
				System.out.println(Arrays.toString(list));
//				Test [] isEliteArr = new Test[3];
//				isEliteArr[0] = new Test(1);
//				isEliteArr[1] = new Test(5);
//				isEliteArr[2] = new Test(9);
//				Arrays.sort(isEliteArr, new IsElitePValueComparator());
//				int i;
			}
	
}
class IsElitePValueComparator implements Comparator<Test> {
	public int compare(Test o1, Test o2) {
		if (o1.a > o2.a) {
			return -1;
		} else if (o1.a < o2.a) {
			return 1;
		} else {
			return 0;
		}
	}
}
class Test{
	public int a;
	public Test(int b){
		a = b;
	}
}