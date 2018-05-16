package main;

import java.util.Comparator;

public class TaxOuput {

	public TaxOuput(long code, String name, double rate) {
		super();
		this.code = code;
		this.name = name;
		this.rate = rate;
	}
	//data members
	protected long code;
	private String name;
	private double rate;
	private double amount = 0;
	
	//Methods
	public void print()
	{
		System.out.printf("\t  %-27s%5.0f%%%,10.2f\n",
				//check description length, if it's bigger than 27 chars, truncate it
				((this.code + "-" + this.name).length()>27
						?(this.code + "-" + this.name).substring(0, 27)
								:(this.code + "-" + this.name)),
				this.rate * 100.0,
				this.amount				
				);
	}
		
/*Output model
========================================================
                 Coding Challenge Store                  
========================================================

Quantity  Description                Tax Code  Amount
  1       Eggs - 12                      7       2.25
  1       Apples - 10                    7       2.50
  1       Prepared sandwich - Large              4.00

          SUBTOTAL:                              8.75               

          5-HST - Prepared food < $5     5%      0.20
          7-EXEMPT - Food                0%      0.00

          TOTAL:                                $8.95

 */
	//Getters and Setters
	/**
	 * @return the code
	 */
	public long getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(long code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double productAmount) {
		this.amount += productAmount * this.rate;
	}
	
	
}

class TaxOutputComparator implements Comparator<TaxOuput>
{
    public int compare(TaxOuput o1, TaxOuput o2)
    {
    	if(o1.code < (o2.code))
    		return -1;
    	else if(o1.code == (o2.code))
    		return 0;
    	else
    		return 1;
    }
}
