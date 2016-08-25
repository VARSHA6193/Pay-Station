/**
 * The class IllegalCoinException is an Exception Class used to indicate that 
 * a invalid coin value has been used.
 * @author 1)Varsha

 */
public class IllegalCoinException extends Exception {
	/**
	 * To Silence warning :The serializable class IllegalCoinException does not
	 * declare a static final serialVersionUID field of type long
	 */
	private static final long serialVersionUID = 1L;
	
	private int coinValue;
	/**
	 * Constructor passing the illegal coin value that caused this exception.
	 * @param coinValue the illegal coin value
	 */
	public IllegalCoinException(int coinValue) 
	{
		this.coinValue = coinValue;
	}
	
	/**
	 * Constructor that takes the illegal coin value that caused this exception.
	 * @param String e with the illegal coin value
	 */
	public IllegalCoinException(String e) 
	{
        super(e);
	}

	/**
	 * Gets the illegal coin value that caused this exception.
	 * @return the illegal coin value
	 */
	public int getCoinValue() {
		return coinValue;
	}
}