import java.util.ArrayList;
import java.util.Arrays;

public class QwirkleTesting {

	public static void main(String[] args) {

	}
	
	public static ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> num) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
	 
		//start from an empty list
		result.add(new ArrayList<Integer>());
	 
		for (int i = 0; i < num.size(); i++) {
			
			//list of list in current iteration of the array num
			ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();
		
			//for each list in result, add num[i] to all of the locations
			for (ArrayList<Integer> l : result) {
				
				// # of locations to insert is largest index + 1
				for (int j = 0; j < l.size()+1; j++) {
					
					// + add num[i] to different locations
					l.add(j, num.get(i));
	 
					ArrayList<Integer> temp = new ArrayList<Integer>(l);
					current.add(temp);
	 
					l.remove(j);
				}
			}
			result = new ArrayList<ArrayList<Integer>>(current);
		}
		return result;
	}
	
	public static ArrayList<ArrayList<Integer>> permute(Integer[] num) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
	 
		//start from an empty list
		result.add(new ArrayList<Integer>());
	 
		for (int i = 0; i < num.length; i++) {
			
			//list of list in current iteration of the array num
			ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();
		
			//for each list in result, add num[i] to all of the locations
			for (ArrayList<Integer> l : result) {
				
				// # of locations to insert is largest index + 1
				for (int j = 0; j < l.size()+1; j++) {
					
					// + add num[i] to different locations
					l.add(j, num[i]);
	 
					ArrayList<Integer> temp = new ArrayList<Integer>(l);
					current.add(temp);
	 
					l.remove(j);
				}
			}
			result = new ArrayList<ArrayList<Integer>>(current);
		}
		return result;
	}
	
	public static ArrayList<ArrayList<Integer>> perms(ArrayList<Integer> numbers)
	{
		ArrayList<ArrayList<Integer>> perms = new ArrayList<ArrayList<Integer>>(24);
		
		if(numbers.size() == 1)
		{
			perms.add(numbers);
			return perms;
		}
		
		for(Integer temp: numbers)
		{
			ArrayList<Integer> numbers2 = new ArrayList<Integer>(numbers);
			numbers2.remove(temp);
			
			ArrayList<ArrayList<Integer>> perms2 = perms(numbers2);
			
			for(int i = 0; i < perms2.size(); i++)
			{
				perms2.get(i).add(0, temp);
				perms.add(i, perms2.get(i));
			}
		}
		return perms;
	}
	
	public static ArrayList<ArrayList<Integer>> permute3(Integer[] num) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		permute(num, 0, result);
		return result;
	}
	 
	public static void permute(Integer[] num, int start, ArrayList<ArrayList<Integer>> result) {
	 
		if (start >= num.length) {
			ArrayList<Integer> item = convertArrayToList(num);
			result.add(item);
		}
	 
		for (int j = start; j <= num.length - 1; j++) {
			swap(num, start, j);
			permute(num, start + 1, result);
			swap(num, start, j);
		}
	}
	 
	private static ArrayList<Integer> convertArrayToList(Integer[] num) {
		ArrayList<Integer> item = new ArrayList<Integer>();
		for (int h = 0; h < num.length; h++) {
			item.add(num[h]);
		}
		return item;
	}
	 
	private static void swap(Integer[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}
