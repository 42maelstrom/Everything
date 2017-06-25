import java.util.*;
public class ReverseArray {
	public static void main(String[] args)
	{
		ArrayList <String> money = new ArrayList<String>();
		
		for(int i = 0; i<10; i++)
		{
			int x = (int)(Math.random()*100 +1);
			money.add("$"+x);
		}
		
		for(String temp: money)
		{
			System.out.print(temp+" ");
		}
		
		System.out.println("");
		
		reverse(money);
		
		for(String temp: money)
		{
			System.out.print(temp+" ");
		}
		
		System.out.println("");
	}
	public static void reverse(ArrayList list)
	{
		for(int i = 0; i < list.size(); i++)
		{
			list.add(i, list.get(list.size() - 1));
			list.remove(list.size() -1);
		}
	}
}