/***************************
 * @author Mateus
 * @project Coding Challenge for Android Bootcamp hosted by Shopify
 * @date 2018-05-10
 ***************************/

package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

//JSON libraries
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main {

	//JSON Objects and Arrays to receive the JSON files information

	static JSONArray productsList; //receive all products listed in products.JSON file
	static JSONArray taxRatesList; //receive all tax rates listed in tax_rates.JSON file
	static JSONArray cartList; //receive all purchases listed in cart.JSON file

	static JSONObject product = null; //receive a product object in productList array
	static JSONArray variantsList; //receive all variants of product object
	static JSONObject variant = null; //receive a variant object in variantsList array
	static JSONObject tax; //receive a tax object in taxRates array
	static JSONObject cartProduct; //receive a product object in cartList array

	//Create the treatment parse
	static JSONParser parser = new JSONParser();

	//Variables to receive final information of cart.JSON
	static long cartProductId;//"product"
	static long cartVariantIndex;//"variant"
	static long cartQuantity;//"quantity"

	//Variables and arrays to build the output
	static double subtotal = 0;
	static double total = 0;
	static Vector<ProductOutput> productOutput = new Vector<ProductOutput>();
	static Vector<TaxOuput> taxOutput = new Vector<TaxOuput>();



	public static void main(String[] args) {
		try {
			//Assign the JSON files to their respective Arrays
			productsList = (JSONArray) parser.parse(new FileReader("src/Files/products.json"));
			taxRatesList = (JSONArray) parser.parse(new FileReader("src/Files/tax_rates.json"));
			cartList = (JSONArray) parser.parse(new FileReader("src/Files/cart.json"));

			//Main Loop - 1 iteration per product
			for(int i = 0; i < cartList.size(); i++) {
				//Resolve Product
				cartProduct = (JSONObject) cartList.get(i);
				readCart();
				setProductDetails(cartProductId);
				fillProductOutput(i);				

				//update the subtotal
				subtotal += productOutput.get(i).getAmount();

				//Resolve Tax
				setTaxDetails((long) variant.get("tax_code"));
				fillTaxOutput(i);

				//update total
				total += productOutput.get(i).getAmount() * (1.0 + (double) tax.get("rate"));

			}

			taxOutput.sort(new TaxOutputComparator());//sort tax by code before print

			printCoupon();

		} 
		//Treats exceptions that can be thrown in the process
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void fillTaxOutput(int i) {
		boolean newTaxCode = true;
		int taxIndex = 0;
		for(taxIndex = 0; taxIndex < taxOutput.size() && newTaxCode; taxIndex++) {
			if(taxOutput.get(taxIndex).getCode()==(long) tax.get("code"))
			{
				newTaxCode = false;
				taxIndex--;//decrement to use j as the taxOutput index below 
			}
		}

		if(newTaxCode) {
			taxOutput.add(new TaxOuput((long) tax.get("code"),
					(String) tax.get("name"),
					(double) tax.get("rate")
					)
					);	
		}
		taxOutput.get(taxIndex).setAmount(productOutput.get(i).getAmount());
	}

	private static void setTaxDetails(long tax_code) {
		for(int i = 0; i < taxRatesList.size(); i++) {
			tax = (JSONObject) taxRatesList.get(i);
			if((long) tax.get("code") == tax_code) {
				return;//exit loop
			}			
		}
	}

	private static void fillProductOutput(int i) {
		productOutput.add(new ProductOutput((String) product.get("name"),
				(String) variant.get("size"),
				(double) variant.get("price") * cartQuantity,
				(long) variant.get("tax_code"),
				cartQuantity));
	}

	private static void setProductDetails(long cartProductId) {
		for(int i = 0; i < productsList.size();i++)
		{
			product = (JSONObject) productsList.get(i);
			if((long)product.get("id") == cartProductId)
			{
				variantsList = (JSONArray) product.get("variants");
				variant = (JSONObject) variantsList.get((int) cartVariantIndex);
				return;//exit loop
			}
		}
	}

	private static void readCart() {		
		cartProductId = (long) cartProduct.get("product");
		cartVariantIndex = (long) cartProduct.get("variant");
		cartQuantity = (long) cartProduct.get("quantity");		
	}

	private static void printCoupon()
	{
		//Header
		System.out.println("========================================================");
		System.out.println("                 Coding Challenge Store                 ");
		System.out.println("========================================================");
		System.out.println();
		System.out.printf("%-10s%-27s%-10s%s\n","Quantity","Description","Tax Code","Amount");

		//products
		for(int i = 0; i < productOutput.size();i++)
		{
			productOutput.get(i).print();
		}
		//subtotal
		System.out.printf("\n\t  SUBTOTAL:%,34.2f\n\n", subtotal);
		//taxes

		for(int i = 0; i < taxOutput.size();i++)
		{
			taxOutput.get(i).print();
		}

		//total

		DecimalFormat myFormatter = new DecimalFormat("$###,###.00");
		String myTotal = myFormatter.format(total);
		System.out.printf("\n\t  TOTAL:%37s\n\n", myTotal);
		//taxes

	}
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
