package cs141;

public class Fraction 
{
	protected int numerator = 0;
	protected int denominator = 0;
	
	// Constructor
	public Fraction(int numerater, int denominator)
	{
		try
		{
			if(denominator != 0)
			{
				this.numerator = numerater;
				this.denominator = denominator;
			}
			else
				throw new Exception("Illegal 0 Denominator");
		}
		catch(Exception e){}
	}
	
	//Overloading Constructor
	public Fraction(int I)	//Integer as a fraction
	{
		numerator = I;
		denominator = 1;
	}
	
	public Fraction inverse()	//inverse the fraction
	{
		return new Fraction(denominator, numerator);
	}
	
	public Fraction negate()	//negative fraction
	{
		return new Fraction(numerator * -1, denominator);
	}
	
	public Fraction add(Fraction F)
	{ 
		Fraction ans = new Fraction(numerator * F.denominator +
									F.numerator * denominator,
									denominator * F.denominator);
		ans.reduce();
		return ans;
	}
	
	public Fraction subtract(Fraction F)
	{
		Fraction ans = new Fraction(numerator * F.denominator -
				F.numerator * denominator,
				denominator * F.denominator);
		ans.reduce();
		return ans;
	}
	
	public Fraction multiply(Fraction F)
	{
		Fraction ans = new Fraction(numerator * F.numerator, denominator * F.denominator);
		ans.reduce();
		return ans;
	}
	
	public Fraction divide(Fraction F)
	{
		Fraction ans = new Fraction(numerator * F.denominator, denominator * F.numerator);
		ans.reduce();
		return ans;
	}
	
	public String toString()
	{
		return numerator + "/" + denominator;
	}
	
	public boolean equals(Fraction F)
	{
		if((double)(numerator / denominator) == (double)(F.numerator / F.denominator))
		{
			return true;
		}
		else
			return false;
	}
	
	public void reduce() 
	{
		int gcd = gcd(numerator, denominator);
		numerator /= gcd;
		denominator /= gcd;
	}
	
	private int gcd(int a, int b)	//greatest common div.
	{
		if(a == 0) 
			return b;
		if(b == 0)
			return a;
		if(a > b)
			return gcd(b, a%b);
		else
			return gcd(a, b%a);
	}
}
