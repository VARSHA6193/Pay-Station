/**
 * 1) To validate US dollars 
 * @author Varsha
 */
public class USPaymentStrategy implements PaymentStrategy
{
	public int isValidCoin(int coinValue) 
	{
		switch ( coinValue ) 
		{
		case 5:
		case 10:
		case 25:
			return 1;
		default:
			return 0;
		}
	}
}
