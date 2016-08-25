public class USRateStrategy implements RateStrategy 
{
	public int calculateTime(int amount){
		return amount * 2 / 5;
	}
}
