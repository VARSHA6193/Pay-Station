/**
 * 1) To Calculate parking time for Beta town 
 * @author Varsha
 */

public class LinearRateStrategy implements RateStrategy 
{
	public int calculateTime(int amount)
	{
		return amount * 7;
	}
}