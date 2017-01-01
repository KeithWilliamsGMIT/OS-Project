/*
 * Keith Williams (G00324844)
 * 19/12/2016
 */

package ie.gmit.sw.server;

import java.util.Collection;

public class Account {
	private int number;
	private float balance;
	private Collection<Integer> transactions;
	
	// Constructors
	public Account(int number) {
		super();
		this.number = number;
	}
	
	// Getters and Setters
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	// Methods
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Account other = (Account) obj;
		
		if (number != other.number)
			return false;
		
		return true;
	}
	
}
