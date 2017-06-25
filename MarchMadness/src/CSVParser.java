import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class CSVParser {
	public static Double[] parseFile(String filePath) {
		ArrayList<Double> values = new ArrayList<Double>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
			String line;
			while((line = br.readLine()) != null) {
				values.add(Double.valueOf(line));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return values.toArray(new Double[values.size()]);
	}
}
