package cs141;


public class MixedFraction extends Fraction
{
	protected int whole = 0;
	
	public MixedFraction(int whole, int numerator, int denominator)
	{
		super(numerator, denominator);
		try
		{		
			if(denominator != 0)
			{
				this.whole = whole;
				super.numerator = numerator;
				super.denominator = denominator;
			}
			else
				throw new Exception("Illegal 0 Denominator");
		}
		catch(Exception e) {}
	}
	
	public MixedFraction(int whole, Fraction F)
	{
		super(F.numerator, F.denominator);
		this.whole = whole;
	}
	
	public MixedFraction(int numerator, int denominator)
	{
		super(numerator, denominator);
		super.numerator = numerator;
		super.denominator = denominator;
	}
	
	public MixedFraction(Fraction F)
	{
		super(F.numerator, F.denominator);
	}
	
	public MixedFraction(int I)
	{
		super(1, 1);
		whole = I;
	}
	
	public Fraction toImproper()
	{
		return new Fraction(whole * denominator + numerator, denominator);
	}
	
	public MixedFraction negate()
	{
		// if whole = 0, whole times 1 would not make a negative fraction
		if(whole != 0)
			return new MixedFraction(whole * -1, numerator, denominator);
		else
			return new MixedFraction(whole, numerator * -1, denominator);
	}
	
	public MixedFraction add(MixedFraction MF)
	{
		MixedFraction frc = new MixedFraction(whole, numerator, denominator);
		Fraction tmpFrc = frc.toImproper();
		Fraction impAns = tmpFrc.add(MF.toImproper());
		int w = impAns.numerator / impAns.denominator;
		int n = impAns.numerator - w * impAns.denominator;
		int d =  impAns.denominator;
		
		return new MixedFraction(w,n,d);
	}
	
	public MixedFraction subtract(MixedFraction MF)
	{
		MixedFraction frc = new MixedFraction(whole, numerator, denominator);
		Fraction tmpFrc = frc.toImproper();
		Fraction impAns = tmpFrc.subtract(MF.toImproper());
		int w = impAns.numerator / impAns.denominator;
		int n = impAns.numerator - w * impAns.denominator;
		int d =  impAns.denominator;
		
		return new MixedFraction(w,n,d);
	}
	
	public MixedFraction multiply(MixedFraction MF)
	{
		MixedFraction frc = new MixedFraction(whole, numerator, denominator);
		Fraction tmpFrc = frc.toImproper();
		Fraction impAns = tmpFrc.multiply(MF.toImproper());
		int w = impAns.numerator / impAns.denominator;
		int n = impAns.numerator - w * impAns.denominator;
		int d =  impAns.denominator;
		
		return new MixedFraction(w,n,d);
	}
	
	public MixedFraction divide(MixedFraction MF)
	{
		MixedFraction frc = new MixedFraction(whole, numerator, denominator);
		Fraction tmpFrc = frc.toImproper();
		Fraction impAns = tmpFrc.divide(MF.toImproper());
		int w = impAns.numerator / impAns.denominator;
		int n = impAns.numerator - w * impAns.denominator;
		int d =  impAns.denominator;
		
		return new MixedFraction(w,n,d);
	}
	
	public String toString()
	{
		return whole + " " + numerator + "/" + denominator;
	}
	
	public boolean equals(MixedFraction MF)		//ex: 1 3/4 == 1 3/4
	{
		if(whole == MF.whole && 
			((double)(numerator/denominator) == (double)(MF.numerator/MF.denominator)))
		{
			return true;
		}
		else
			return false;
	}
	
	public boolean equals(Fraction F)		//ex: 1 3/4 == 7/4
	{
		MixedFraction frc = new MixedFraction(whole, numerator, denominator);
		if(frc.toImproper().equals(F))
		{
			return true;
		}
		else
			return false;
	}
}
