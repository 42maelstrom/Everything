/**
 * Fraction class - This class is intended to model fractions
 * in order to do basic arithmetic computations.
 * @author Greg King
 * @version 1.1  September 21, 2008
 * 
 */

public class Fraction 
{
	// instance variables
	private int numerator;
	private int denominator;
	
	/**
	 * Constructs a new Fraction with numerator n and denominator d.
	 * @param n numerator
	 * @param d denominator
	 */
	public Fraction(int n, int d)
	{
		if(d>0)
		{
			numerator = n;
			denominator = d;
		}
		else
		{
			numerator = n * -1;
			denominator = d * -1;
		}
	}
	
	
	
	/**
	 * Constructs a new Fraction with numerator 0 and denominator 1
	 */
	public Fraction()
	{
		numerator = 0;
		denominator = 1;
	}
	
	//accessor methods
	/**
	 * Returns the numerator of the Fraction
	 * @return numerator
	 */
	public int getNumerator()
	{
		return numerator;
	}
	
	/**
	 * Returns the denominator of the Fraction
	 * @return denominator
	 */
	public int getDenominator()
	{
		return denominator;
	}
	
	// methods
	/**
	 * Simplifies the Fraction, putting it in simplest
	 * form by dividing out any common factors of its
	 * numerator and denominator.
	 */
	public void simplify()
	{
		int g = gcf(numerator,denominator);
		numerator /= g;
		denominator /= g;
	}

	/**
	 * Returns the greatest common factor of a and b.
	 * @param a  first number
	 * @param b  second number
	 * @return	 greatest common factor of a and b
	 */
	private static int gcf(int a, int b)
	{
		if(a<0)
			a *= -1;
		if(b<0)
			b *= -1;
		if(a<b)
		{
			int temp = a;
			a = b;
			b = temp;
		}
		int check = b;
		while (a%check != 0 || b%check != 0)
		{
			check -= 1;
		}
		return check;
	}

	/**
	 * Adds Fraction f to this fraction and returns
	 * the result as a Fraction
	 * @param f  Fraction to add to this Fraction
	 * @return   sum of this Fraction and f
	 * 
	 * 
	 */
	public Fraction add(Fraction f)
	{
		int num = this.getNumerator() * f.getDenominator() +
			  this.getDenominator() * f.getNumerator();
		int den = this.getDenominator() * f.getDenominator();
		Fraction sum = new Fraction(num, den);
		return sum;
	}
	
	/**
	 * Subtracts Fraction f from this fraction and returns
	 * the result as a Fraction
	 * @param f  Fraction to subtract from this Fraction
	 * @return   difference of this Fraction and f
	 */
	public Fraction subtract(Fraction f)
	{
		int num = this.getNumerator() * f.getDenominator() -
			  this.getDenominator() * f.getNumerator();
		int den = this.getDenominator() * f.getDenominator();
		Fraction difference = new Fraction(num, den);
		return difference;
	}
	
	/**
	 * Multiply Fraction f by this fraction and returns
	 * the result as a Fraction
	 * @param f  Fraction to multiply with this Fraction
	 * @return   product of this Fraction and f
	 */
	public Fraction multiply(Fraction f)
	{
		int num = this.getNumerator() * f.getNumerator();
		int den = this.getDenominator() * f.getDenominator();
		Fraction product = new Fraction(num, den);
		return product;
	}
	
	/**
	 * Divides this Fraction by  Fraction f and returns
	 * the result as a Fraction
	 * @param f  Fraction to divide this Fraction by
	 * @return   quotient of this Fraction and f
	 */
	public Fraction divide(Fraction f) 
	{
		
		int num = this.getNumerator() * f.getDenominator();
		int den = this.getDenominator() * f.getNumerator();
		Fraction quotient = new Fraction(num, den);
		return quotient;
	}
	
	/**
	 * Returns String form of the Fraction in a/b form.
	 * In other words five fourths appears as '5/4'
	 */
	public String toString()
	{
		String str = numerator + "/" + denominator;
		return str;
	}
	
}