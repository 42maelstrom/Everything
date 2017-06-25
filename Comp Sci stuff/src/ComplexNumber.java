
public class ComplexNumber {

	private double a, b;
	
	/**
	 * Creates a complex number of the form a + bi,
	 * @param realVal - a, the real part of the complex number
	 * @param imagVal - b, the imaginary part of the complex number
	 */
	public ComplexNumber(double realVal, double imagVal) {
		this(realVal, imagVal, false);
	}
	
	/**
	 * Creates a complex number based off real and imaginary values
	 * or by the magnitude and the angle, if inPolarForm is given as true.
	 * ComplexNumber(a,b,false) does the same thing as ComplexNumber(a,b)
	 *
	 * @param val1 - either the real value or the magnitude of the number
	 * @param val2 - either the imaginary value or the angle, in radians, of the number
	 * @param inPolarForm - whether to construct as a + bi with real and imaginary values,
	 *  or as r(cos(theta) + isin(theta)).
	 */
	public ComplexNumber(double val1, double val2, boolean inPolarForm) {
		if(inPolarForm == false){
			a = val1;
			b = val2;
		} else {
			a = val1*Math.cos(val2);
			b = val1*Math.sin(val2);
		}
	}
	
	/**
	 * Returns the real component of the number.
	 */
	public double getReal() {
		return a;
	}
	
	/**
	 * Returns the imaginary component of the number.
	 */
	public double getImag() {
		return b;
	}
	
	/**
	 * Returns the magnitude of the number. 
	 * Also known as absolute value or radius.
	 */
	public double getMag() {
		return Math.sqrt(a*a + b*b);
	}
	
	/**
	 * Returns the angle of the number.
	 * Also known as argument.
	 * The angle will be between 0 and 2pi.
	 */
	public double getAngle() {
		double atan = Math.atan(b/a);
		
		if(a > 0)
			return atan;
		if(a < 0 && b >= 0)
			return atan + Math.PI;
		if(a < 0 && b < 0)
			return atan - Math.PI;
		if(a == 0 && b > 0)
			return Math.PI / 2;
		if(a == 0 && b < 0)
			return - Math.PI / 2;
		if(a == 0 && b == 0)
			return 0;
		return 0;
	}
	
	/**
	 * Sets the real value of this number to the given parameter.
	 * Will change the angle and magnitude of the number.
	 * Imaginary value stays the same.
	 */
	public void setReal(double realVal) {
		a = realVal;
	}
	
	/**
	 * Sets the imaginary value of this number to the given parameter.
	 * Will change the angle and magnitude of the number.
	 * Real value stay the same.
	 */
	public void setImag(double imagVal) {
		b = imagVal;
	}
	
	/**
	 * Sets the magnitude of this number to the given parameter.
	 * Will change the real and imaginary values of the number.
	 * Angle stays the same.
	 */
	public void setMag(double magnitude) {
		double angle = getAngle();
		setReal(magnitude*Math.cos(angle));
		setImag(magnitude*Math.sin(angle));
	}
	
	/**
	 * Sets the angle of this number to the given parameter (in radians).
	 * Will change the real and imaginary values of the number.
	 * Magnitude stays the same.
	 */
	public void setAngle(double angle) {
		double mag = getMag();
		setReal(mag*Math.cos(angle));
		setImag(mag*Math.sin(angle));
	}
	
	/**
	 * Adds this number to another number.
	 * @param num - the number to be added
	 * @return - the sum of the two numbers.
	 */
	public ComplexNumber add(ComplexNumber num) {
		return new ComplexNumber(a + num.getReal(), b + num.getImag());
	}
	
	/**
	 * Subtracts another number from this number.
	 * @param num - the number to subtract from this.
	 * @return - the difference of the two numbers.
	 */
	public ComplexNumber subtract(ComplexNumber num) {
		return new ComplexNumber(a - num.getReal(), b - num.getImag());
	}

//			(a + bi)(c + di)
//			=  ac + adi + bci - bd
//			= (ac - bd) + (ad + bc)i
	/**
	 * Multiplies this number by the given number.
	 * @param num - the number to multiply this by.
	 * @return - the product of the two numbers.
	 */
	public ComplexNumber multiply(ComplexNumber num) {
		return new ComplexNumber(a*num.getReal() - b*num.getImag(),
			a*num.getImag() + b*num.getReal());
	}
	
//		real: (ac + bd) / (c^2 + d^2)
//		imag: (bc - ad) / (c^2 + d^2)
	/**
	 * Divides this number by the given number.
	 * @param num - the number to divide this by.
	 * @return - the quotient of the two numbers.
	 */
	public ComplexNumber divide(ComplexNumber num) {
		return new ComplexNumber( (a*num.getReal() + b*num.getImag() )
				/ ( num.getReal()*num.getReal() + num.getImag()*num.getImag() ),
				(b*num.getReal() - a*num.getImag() )
				/ ( num.getReal()*num.getReal() + num.getImag()*num.getImag() ) );
	}
	
	/**
	 * Returns this^(given number).
	 * Works for any complex number.
	 * @param num - the exponent
	 * @return - this^num.
	 */
	public ComplexNumber pow(ComplexNumber num) {
		double c = num.getReal();
		double d =  num.getImag();
		double r = this.getMag();
		double th = this.getAngle();
		
		double k = Math.pow(r, c) * Math.pow(Math.E,  -d*th);
		double cth = c*th;
		double dlnr = d*Math.log(r);
		
		double cd = Math.cos(dlnr);
		double cc = Math.cos(cth);
		double sd = Math.sin(dlnr);
		double sc = Math.sin(cth);
		
		return new ComplexNumber(k*(cd*cc - sd * sc), k*(cc*sd+ cd* sc));
	}
	
	/**
	 * Returns a String of the form "a + bi", where a and b are the real and
	 * imaginary values of the number respectively.
	 * a and b are rounded to 2 decimal places for the String, and should
	 * not be used as exact values. Instead use getReal() and getImag().
	 */
	public String toString() {
		double real = Math.rint(a*100) / 100;
		double imag = Math.rint(b*100) / 100;
		return real + " + " + imag + "i";
	}
	
	/**
	 * Returns a String of the form "r(cos(theta) + isin(theta))" where r and
	 * theta are the magnitude and angle of the number respectively.
	 * theta is in radians.
	 * r and theta are rounded to 2 decimal places for the String, and should
	 * not be used as exact values. Instead use getMag() and getAngle().
	 */
	public String toCISString() {
		double mag = Math.rint(getMag() * 100) / 100;
		double angle = Math.rint(getAngle() * 100) / 100;
		return mag + "(cos(" + angle + ") + isin(" + angle + "))";
	}
	
	/**
	 * Returns a String of the form "re^(iTheta)" where r and theta are the
	 * magnitude and angle of the number respectively.
	 * theta is in radians.
	 * r and theta are rounded to 2 decimal places for the String, and should
	 * not be used as exact values. Instead use getMag() and getAngle().
	 * @return
	 */
	public String toReitString() {
		double mag = Math.rint(getMag() * 100) / 100;
		double angle = Math.rint(getAngle() * 100) / 100;
		return mag + "e^(i*" + angle + ")";
	}
}
