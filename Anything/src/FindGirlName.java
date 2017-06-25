import java.util.ArrayList;


public class FindGirlName {
	
	public ArrayList<String> girlNames = getNames("dist.female.first.txt");
	
	public ArrayList<String> getNames(String filePath) {
		ArrayList<String> names = TxtParser.parseFile("dist.female.first.txt");
		for(int i = 0; i < names.size(); i++) {
			String n = names.get(i);
			n = n.substring(0, n.indexOf(' '));
			names.set(i, n);
		}
		return names;
	}
	
	public ArrayList<String> getFiltered1() {
		ArrayList<String> filteredNames = new ArrayList<String>();
		for(String n: girlNames) {
			if(!"AEIOU".contains(n.substring(0, 1))  && !n.contains("M") && n.length() % 2 == 1 && n.charAt((n.length() - 1) / 2) == 'E') {
					filteredNames.add(n);
			}
		}
		
		return filteredNames;
	}
	
	public ArrayList<String> getFiltered2() {
		ArrayList<String> filteredNames = new ArrayList<String>();
		for(String n: girlNames) {
			if(n.charAt(0) != 'M') {
				if(n.length() == 7) {
					if(n.charAt(3) == 'E') {
						if("AEIOU".contains(n.substring(6)) == false) {
							filteredNames.add(n);
						}
					}
				}
			}
		}
		
		return filteredNames;
	}
	
	public static void main(String[] args) {
		for(String n: new FindGirlName().getFiltered2()) {
			System.out.println(n);
		}
	}
}
