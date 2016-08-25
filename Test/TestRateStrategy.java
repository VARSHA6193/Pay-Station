import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class TestRateStrategy 
{
    PayStation progressivePs;
    PayStation linearPs;
    PayStation usPs;	
    RateStrategy progessiveRs;
    RateStrategy linearRs;
    RateStrategy usRs;
    
    PaymentStrategy ks,cs;
    
    @Before
    public void setup()
    {
    	ks= new DanishPaymentStrategy();//krone
    	cs = new USPaymentStrategy(); //cents
    	progessiveRs= new ProgressiveRateStrategy();
    	linearRs = new LinearRateStrategy();
    	usRs = new USRateStrategy();
    	progressivePs = new PayStationImpl(ks,progessiveRs);
    	linearPs = new PayStationImpl(ks,linearRs);
    	usPs = new PayStationImpl(cs,usRs);
    }
  
    
    /** Testing Danish progressive rate strategy designed for Beta town
   * @throws IllegalCoinException
   */
  
    // 25 krone coin doesn't exist. so Illegal coin exception must be thrown
	
    @Test (expected = IllegalCoinException.class)
	public void shouldNotDisplayTimeFor25Krone() throws IllegalCoinException
	{
    	progressivePs.addPayment(25);
	}
    
    // 1 Danish Krone should give 6 minutes in progressive rate strategy
	@Test 
	public void shouldDisplay6MinFor1Krone() 
	{
		assertEquals(6, progessiveRs.calculateTime(1));
	}

    // 32 Danish Krones should give 180 minutes in progressive rate strategy
	@Test 
	public void shouldDisplay180MinFor32Krones() throws IllegalCoinException
	{
		progressivePs.addPayment(20);
		progressivePs.addPayment(10);
		progressivePs.addPayment(2);
		assertEquals(180, progressivePs.readDisplay());
	}
    // Buy should return a valid receipt of the proper amount of parking time
   @Test
   public void shouldReturnCorrectReceiptWhenBuy()
           throws IllegalCoinException 
   {
	   progressivePs.addPayment(5);
	   progressivePs.addPayment(10);
	   progressivePs.addPayment(20);
       Receipt receipt;
       receipt = progressivePs.buy();
       assertNotNull("Receipt reference cannot be null",receipt);
       assertEquals("Receipt value must be 16 min.",120+(15*5), receipt.value());
   }

 //Should initialize receipt to the value passed 
   @Test
   public void shouldStoreTimeInReceipt()
   {
     Receipt receipt = new ReceiptImpl(30);
     assertEquals( "Receipt Should display 30 min value",30, receipt.value()); 
   }
   
   //Verify that the pay station is cleared after a buy scenario
   
   @Test
   public void shouldClearAfterBuy()throws IllegalCoinException {
	   progressivePs.addPayment(20);
	   progressivePs.buy(); 
       // After buying verify that the display reads 0
       assertEquals("Display should have been cleared",0, progressivePs.readDisplay());
       // verify that a following buy scenario behaves properly
       progressivePs.addPayment(10);
       progressivePs.addPayment(10);
       assertEquals("Next add payment should display correct time",20*6, progressivePs.readDisplay());
       Receipt r = progressivePs.buy();
       assertEquals("Next buy should return valid receipt",120, r.value());
       assertEquals("Again, display should be cleared",0, progressivePs.readDisplay());
   }
   
   //Calling cancel after inserting one coin returns a map containing one coin entered.
   @Test
   public void shouldReturnOneCoin() throws IllegalCoinException
   {
	   progressivePs.addPayment(10);
       Map<Integer,Integer> map = progressivePs.cancel();
       assertEquals("Should return single 10 kroner coin",(Integer)1, map.get(10));   
   }

   // Canceling after adding several coins returns a map of coins.
   @Test
   public void shouldReturnCoinMixture() throws IllegalCoinException
   {
	   progressivePs.addPayment(1);
	   progressivePs.addPayment(2);
	   progressivePs.addPayment(5);
	   progressivePs.addPayment(10);
	   progressivePs.addPayment(20);

       Map<Integer,Integer> map = progressivePs.cancel();
       assertEquals("Cancel should return five coins", 5, map.size());
       assertEquals("Call to cancel should return one 1 kroner",(Integer)1, map.get(1));
       assertEquals("Call to cancel should return one 2 kroner",(Integer)1, map.get(2));
       assertEquals("Call to cancel should return one 5 kroner",(Integer)1, map.get(5));
       assertEquals("Call to cancel should return one 10 kroner",(Integer)1, map.get(10));
       assertEquals("Call to cancel should return one 20 kroner",(Integer)1, map.get(20));

   }
   
   // A call to cancel should clear the map
  @Test
  public void shouldClearMapOnCancel() throws IllegalCoinException
  {
	  progressivePs.addPayment(20);
	  progressivePs.cancel();
      Map<Integer,Integer> map = progressivePs.cancel();
      assertEquals("Cancelling transaction should clear the map",0,map.size());
  } 
  
// A call to buy() should also clear the map
  @Test
  public void shouldClearMapOnBuy() throws IllegalCoinException
  {
	  progressivePs.addPayment(20);
	  progressivePs.addPayment(20);
	  progressivePs.buy();
      Map<Integer,Integer> map = progressivePs.cancel();
      assertEquals("Buying ticket should clear the map",0, map.size()); 
  }
  
  /** Testing Danish Linear rate strategy designed for Alpha town for krones
   * @throws IllegalCoinException if coins are other than 1, 2, 5, 10, 20 krones
   */
  
    // 50 krone coin doesn't exist. so Illegal coin exception must be thrown
	
    @Test (expected = IllegalCoinException.class)
	public void shouldNotDisplayTimeFor50Krone() throws IllegalCoinException
	{
    	linearPs.addPayment(50);
	}
    
    // 1 Danish Krone should give 7 minutes in Linear rate strategy
	@Test 
	public void shouldDisplay7MinFor1Krone() 
	{
		assertEquals(7, linearRs.calculateTime(1));
	}

    // 32 Danish Krones should give 180 minutes in Linear rate strategy
	@Test 
	public void shouldDisplay210MinFor30Krones() throws IllegalCoinException
	{
		linearPs.addPayment(20);
		linearPs.addPayment(10);
		assertEquals(30*7, linearPs.readDisplay());
	}
    // Buy should return a valid receipt of the proper amount of parking time
   @Test
   public void shouldReturnCorrectReceiptUponBuy() throws IllegalCoinException 
   {
	   linearPs.addPayment(5);
	   linearPs.addPayment(10);
	   linearPs.addPayment(20);
       Receipt receipt;
       receipt = linearPs.buy();
       assertNotNull("Receipt reference cannot be null",receipt);
       assertEquals("Receipt value must be 16 min.",(35*7), receipt.value());
   }

   
   //Verify that the pay station is cleared after a buy scenario
   
   @Test
   public void shouldresetAfterBuy()throws IllegalCoinException 
   {
	   linearPs.addPayment(20);
	   linearPs.buy(); 
       // After buying verify that the display reads 0
       assertEquals("Display should have been cleared",0, linearPs.readDisplay());
       // verify that a following buy scenario behaves properly
       linearPs.addPayment(10);
       linearPs.addPayment(10);
       assertEquals("Next add payment should display correct time",20*7, linearPs.readDisplay());
       Receipt r = linearPs.buy();
       assertEquals("Next buy should return valid receipt",140, r.value());
       assertEquals("Again, display should be cleared",0, linearPs.readDisplay());
   }
   
   //Calling cancel after inserting one coin returns a map containing one coin entered.
   @Test
   public void shouldReturnOnlyOneCoin() throws IllegalCoinException
   {
	   linearPs.addPayment(10);
       Map<Integer,Integer> map = linearPs.cancel();
       assertEquals("Should return single 10 kroner coin",(Integer)1, map.get(10));   
   }

   // Canceling after adding several coins returns a map of coins.
   @Test
   public void shouldReturnCoinMix() throws IllegalCoinException
   {
	   linearPs.addPayment(1);
	   linearPs.addPayment(2);
	   linearPs.addPayment(5);
	   linearPs.addPayment(10);
	   linearPs.addPayment(20);

       Map<Integer,Integer> map = linearPs.cancel();
       assertEquals("Cancel should return five coins", 5, map.size());
       assertEquals("Call to cancel should return one 1 kroner",(Integer)1, map.get(1));
       assertEquals("Call to cancel should return one 2 kroner",(Integer)1, map.get(2));
       assertEquals("Call to cancel should return one 5 kroner",(Integer)1, map.get(5));
       assertEquals("Call to cancel should return one 10 kroner",(Integer)1, map.get(10));
       assertEquals("Call to cancel should return one 20 kroner",(Integer)1, map.get(20));

   }
   
   	// A call to cancel should clear the map
    @Test
    public void shouldClearMapUponCancel() throws IllegalCoinException
    {
    	linearPs.addPayment(20);
    	linearPs.cancel();
    	Map<Integer,Integer> map = linearPs.cancel();
    	assertEquals("Cancelling transaction should clear the map",0,map.size());
    } 
  
   // A call to buy() should also clear the map
    @Test
    public void shouldClearMapUpOnBuy() throws IllegalCoinException
    {
    	linearPs.addPayment(20);
    	linearPs.addPayment(20);
    	linearPs.buy();
    	Map<Integer,Integer> map = linearPs.cancel();
    	assertEquals("Buying ticket should clear the map",0, map.size()); 
     }

    /** Testing USrate strategy designed for US cents
     * @throws IllegalCoinException if coins are other than 5, 10, 25
     */
    
      // 50 Cents coin doesn't exist. so Illegal coin exception must be thrown
  	
      @Test (expected = IllegalCoinException.class)
  	public void shouldNotDisplayTimeFor50Cents() throws IllegalCoinException
  	{
    	  usPs.addPayment(50);
  	}
      
      // 5 Cents should give 2 minutes in US rate strategy
  	@Test 
  	public void shouldDisplay2MinFor5Krone() 
  	{
  		assertEquals(2, usRs.calculateTime(5));
  	}

      // 35 Cents should give 14 minutes in US rate strategy
  	@Test 
  	public void shouldDisplay12MinFor30Cents() throws IllegalCoinException
  	{
  		usPs.addPayment(25);
  		usPs.addPayment(10);
  		assertEquals((35/5)*2, usPs.readDisplay());
  	}
      // Buy should return a valid receipt of the proper amount of parking time
     @Test
     public void shouldReturnCorrectReceiptUpOnBuy() throws IllegalCoinException 
     {
    	 usPs.addPayment(5);
    	 usPs.addPayment(10);
    	 usPs.addPayment(25);
         Receipt receipt;
         receipt = usPs.buy();
         assertNotNull("Receipt reference cannot be null",receipt);
         assertEquals("Receipt value must be 16 min.",(40/5)*2, receipt.value());
     }

     
     //Verify that the pay station is cleared after a buy scenario
     
     @Test
     public void shouldclearAfterBuy()throws IllegalCoinException 
     {
    	 usPs.addPayment(25);
    	 usPs.buy(); 
         // After buying verify that the display reads 0
         assertEquals("Display should have been cleared",0, usPs.readDisplay());
         // verify that a following buy scenario behaves properly
         usPs.addPayment(10);
         usPs.addPayment(10);
         assertEquals("Next add payment should display correct time",(20/5)*2, usPs.readDisplay());
         Receipt r = usPs.buy();
         assertEquals("Next buy should return valid receipt",(20/5)*2, r.value());
         assertEquals("Again, display should be cleared",0, usPs.readDisplay());
     }
     
     //Calling cancel after inserting one coin returns a map containing one coin entered.
     @Test
     public void shouldReturnoneCoin() throws IllegalCoinException
     {
    	 usPs.addPayment(10);
         Map<Integer,Integer> map = usPs.cancel();
         assertEquals("Should return single 10 kroner coin",(Integer)1, map.get(10));   
     }

     // Canceling after adding several coins returns a map of coins.
     @Test
     public void shouldReturnCoinmix() throws IllegalCoinException
     {
    	 usPs.addPayment(5);
    	 usPs.addPayment(10);
    	 usPs.addPayment(25);
  	  
         Map<Integer,Integer> map = usPs.cancel();
         assertEquals("Cancel should return five coins", 3, map.size());
        
         assertEquals("Call to cancel should return one 5 kroner",(Integer)1, map.get(5));
         assertEquals("Call to cancel should return one 10 kroner",(Integer)1, map.get(10));
         assertEquals("Call to cancel should return one 25 kroner",(Integer)1, map.get(25));

     }
     
     	// A call to cancel should clear the map
      @Test
      public void shouldClearMapUpOnCancel() throws IllegalCoinException
      {
    	usPs.addPayment(25);
    	usPs.cancel();
      	Map<Integer,Integer> map = usPs.cancel();
      	assertEquals("Cancelling transaction should clear the map",0,map.size());
      } 
    
     // A call to buy() should also clear the map
      @Test
      public void shouldClearMapUponBuy() throws IllegalCoinException
      {
    	 usPs.addPayment(25);
    	 usPs.addPayment(25);
    	 usPs.buy();
      	Map<Integer,Integer> map = usPs.cancel();
      	assertEquals("Buying ticket should clear the map",0, map.size()); 
       }
   
}