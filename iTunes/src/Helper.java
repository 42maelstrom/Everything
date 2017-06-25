import java.util.ArrayList;
import java.util.HashMap;

/**
 * Some stuff to help with iTunes stuff, and some stuff just for fun.
 * @author simonalford42
 *
 */
public class Helper {
	
	/**
	 * Sorts the array.
	 * @param sortRow - Which row the array will be ordered by.
	 */
	public static void sort(int[][] array, int sortRow)
	{
		if(sortRow > 1)
		{
			System.out.println("Invalid sort row");
			return;
		}
		for(int i = 0; i < array[0].length - 1; i++)
		{
			for(int j = i+1; j < array[0].length; j++)
			{
				if(array[sortRow][i] > array[sortRow][j])
				{
					int temp = array[0][i];
					array[0][i] = array[0][j];
					array[0][j] = temp;
					temp = array[1][i];
					array[1][i] = array[1][j];
					array[1][j] = temp;
				}
			}
		}	
		return;
	}
	
	/**
	 * This was more just for fun than for usefulness.
	 * Sorts one ArrayList based off of the arrangements of values.
	 * in another ArrayList. 
	 * The first ArrayList will have its values swapped based off of
	 * how the second ArrayList's values were swapped.
	 * The second ArrayList will be sorted normally.
	 * @param toBeSorted - The list to be sorted based off the next list.
	 * @param basisList - The basis list. It must extend comparable
	 * in order for it to be sorted. It will be sorted after this method.
	 */
	public static <E, T extends Comparable<T>> void relativeSort(
			ArrayList<E> toBeSorted, ArrayList<T> basisList) {
		int smallSize = Math.min(toBeSorted.size(), basisList.size());
		for(int i = 0; i < smallSize - 1; i++) {
			for(int j = i + 1; j < smallSize; j++) {
				if(basisList.get(i).compareTo(basisList.get(j)) > 0) {
					E temp = toBeSorted.get(i);
					toBeSorted.set(i, toBeSorted.get(j));
					toBeSorted.set(j, temp);
					
					T temp2 = basisList.get(i);
					basisList.set(i, basisList.get(j));
					basisList.set(j, temp2);
				}
			}
		}
	}
	
	/**
	 * Sorts the ArrayList in ascending order based off of its values. 
	 * @param list - The list to be sorted
	 */
	public static <E extends Comparable<E>> void sort(ArrayList<E> list)
	{
		for(int i = 0; i < list.size() - 1; i++) {
			for(int j = i + 1; j < list.size(); j++) {
				if(list.get(i).compareTo(list.get(j)) > 0 ) {
					E temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}
		}
		return;
	}
	
	public static void print(HashMap<?, ?> map)
	{
		for(Object temp: map.keySet())
		{
			System.out.println(temp.toString() + ": " + map.get(temp).toString());
		}
		return;
	}
	
	public static void print(int[][] array)
	{
		for(int i = 0; i < array[0].length; i++)
		{
			System.out.println(array[0][i] + ": " + array[1][i]);
		}
	}
	
	public static void print(long[][] array)
	{
		for(int i = 0; i < array[0].length; i++)
		{
			System.out.println(array[0][i] + ": " + array[1][i]);
		}
	}
	
	/**
	 * Useful for when I'm generating data to graph in MatLab.
	 * Stores the data into a CSV file using the EasyWriter class.
	 */
	public static void storeData(ArrayList<?> list, String fileName)
	{
		EasyWriter outFile = new EasyWriter(fileName);
		for(Object temp: list)
		{
			outFile.print(temp.toString() + ",");
		}
		outFile.close();
		System.out.println("Data stored: " + fileName);
	}
	
	/**
	 * Useful for when I'm generating data to graph in MatLab.
	 * Stores the data into a CSV file using the EasyWriter class.
	 */
	public static void storeData(int[] array, String fileName)
	{
		EasyWriter outFile = new EasyWriter(fileName);
		for(int temp: array)
		{
			outFile.print(temp + ",");
		}
		outFile.close();
		System.out.println("Data stored: " + fileName);
	}
	
	/**
	 * Useful for when I'm generating data to graph in MatLab.
	 * Stores the data into a CSV file using the EasyWriter class.
	 */
	public static void storeData(long[] array, String fileName)
	{
		EasyWriter outFile = new EasyWriter(fileName);
		for(long temp: array)
		{
			outFile.print(temp + ",");
		}
		outFile.close();
		System.out.println("Data stored: " + fileName);
	}
	
	/**
	 * Useful for when I'm generating data to graph in MatLab.
	 * Stores the data into a CSV file using the EasyWriter class.
	 */
	public static void storeData(double[] array, String fileName)
	{
		EasyWriter outFile = new EasyWriter(fileName);
		for(double temp: array)
		{
			outFile.print(temp + ",");
		}
		outFile.close();
		System.out.println("Data stored: " + fileName);
	}
	
	/**
	 * Useful for when I'm generating data to graph in MatLab.
	 * Stores the data into a CSV file using the EasyWriter class.
	 */
	public static void storeData(int[][] array, String fileName)
	{
		EasyWriter outFile = new EasyWriter(fileName);
		for(int i = 0; i < array[0].length; i++)
		{
			outFile.println(array[0][i] + "," + array[1][i]);
		}
		outFile.close();
		System.out.println("Data stored: " + fileName);
	}
	
	/**
	 * Useful for when I'm generating data to graph in MatLab.
	 * Stores the data into a CSV file using the EasyWriter class.
	 */
	public static void storeData(long[][] array, String fileName)
	{
		EasyWriter outFile = new EasyWriter(fileName);
		for(int i = 0; i < array[0].length; i++)
		{
			outFile.println(array[0][i] + "," + array[1][i]);
		}
		outFile.close();
		System.out.println("Data stored: " + fileName);
	}
}