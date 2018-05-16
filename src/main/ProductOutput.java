package main;

public class ProductOutput {

	public ProductOutput(String name, String size, double amount, long taxCode, long quantity) {
		super();
		this.name = name;
		this.size = size;
		this.amount = amount;
		this.taxCode = taxCode;
		this.quantity = quantity;
	}
	//data members
	private String name;
	private String size;
	private double amount;
	private long taxCode;
	private long quantity;
	
	//Methods
	public void print()
	{
		System.out.printf("%3d\t  %-27s%5d%,11.2f\n",
				this.quantity,
				//check description length, if it's bigger than 27 chars, truncate it
				((this.name + " - " + this.size).length()>27
						?(this.name + " - " + this.size).substring(0, 27)
						:(this.name + " - " + this.size)),
				this.taxCode,
				this.amount				
				);
	}
	
	//Getters and Setters
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
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
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
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the taxCode
	 */
	public long getTaxCode() {
		return taxCode;
	}
	/**
	 * @param taxCode the taxCode to set
	 */
	public void setTaxCode(long taxCode) {
		this.taxCode = taxCode;
	}
	/**
	 * @return the quantity
	 */
	public long getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
	
}
