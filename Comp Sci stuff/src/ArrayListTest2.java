import java.util.ArrayList;

public class ArrayListTest2
{
	public static void main(String[] args)
	{
		ArrayList<Fraction> data = new ArrayList<Fraction>();
		int size = 20;
		for(int i=0; i<size; i++)
		{
			int num = (int)(Math.random()*20+1);
			int den = (int)(Math.random()*30+1);
			Fraction temp = new Fraction(num, den);
			data.add(temp);
		}

		
		// put code here to sort the ArrayList into least to greatest order
		for(int i = 0; i < data.size() - 2; i++)
		{
			for(int j = i + 1; j < data.size(); j++)
			{
				if((double)data.get(i).getNumerator() / (double)data.get(i).getDenominator() < (double)data.get(j).getNumerator() / (double)data.get(j).getDenominator())
				{
					Fraction temp = data.get(i);
					data.set(i, data.get(j));
					data.set(j, temp);
				}
			}
		}
		// put code here to display all the fractions in order
		for(Fraction temp: data)
		{
			System.out.print(temp.toString() + " ");
		}
		System.out.println("");
		for(Fraction temp: data)
		{
			System.out.print((double)(int)(100*(double)(temp.getNumerator()) / (double)(temp.getDenominator())) / 100 + " ");
		}
		
	}
}