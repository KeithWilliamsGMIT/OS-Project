/*
 * Keith Williams (G00324844)
 * 3/1/2016
 */

package ie.gmit.sw.server;

import java.util.Date;

public class Transaction {
	private Date date = new Date();
	private String type;
	private float amount;
	
	// Constructors
	public Transaction(String type, float amount) {
		super();
		this.type = type;
		this.amount = amount;
	}
	
	// Methods
	@Override
	public String toString() {
		return "Transaction [date=" + date + ", type=" + type + ", amount="
				+ amount + "]";
	}
}
