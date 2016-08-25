/**
 * 1) To Know the minutes parking time the receipt represents
 * @author Varsha
 */

public class ReceiptImpl implements Receipt {

    private int value;
	/**
	 * Constructor that sets value to the minutes parking time the receipt represents
	 * @param sets Value to the value passed
	 */
    public ReceiptImpl(int value) 
    {
        this.value = value;
    }
    /**
     * Return the number of minutes this receipt is valid for.
     * @return number of minutes parking time
    */
    @Override
    public int value()
    {
        return value;
    }
}
