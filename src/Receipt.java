/** The receipt returned from a pay station.		
* 1) Know the minutes parking time the receipt represents
*/
public interface Receipt {

  /**
   * Return the number of minutes this receipt is valid for.
   * @return number of minutes parking time
  */
  public int value();
}
