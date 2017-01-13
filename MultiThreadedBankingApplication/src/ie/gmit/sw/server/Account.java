/*
 * Keith Williams (G00324844)
 * 19/12/2016
 */

package ie.gmit.sw.server;

import java.io.Serializable;
import java.util.*;

public class Account implements Serializable {
	private int number;
	private float balance;
	private Queue<Transaction> transactions = new LinkedList<Transaction>();
	
	// Constructors
	public Account(int number) {
		super();
		this.number = number;
	}
	
	// Getters and Setters
	public int getNumber() {
		return number;
	}

	public float getBalance() {
		return balance;
	}
	
	// Methods
	// Return the balance as a formatted string
	public String getFormattedBalance() {
		return String.format("%.2f", balance);
	}
	
	// Increment the balance by the given amount
	public void lodge(float amount) {
		balance += amount;
		Transaction transaction = new Transaction("Lodgement", amount);
		addTransaction(transaction);
	}
	
	// Decrease the balance by the given amount
	public void withdraw(float amount) {
		balance -= amount;
		Transaction transaction = new Transaction("Withdrawal", amount);
		addTransaction(transaction);
	}
	
	// Add the given transaction to the queue of transactions
	private void addTransaction(Transaction transaction) {
		// If list has 10 elements remove the first element
		if (transactions.size() == 10) {
			transactions.poll();
		}
		
		// Add the new transaction element to the end of the queue
		transactions.offer(transaction);
	}
	
	// Return all transactions as a string
	public String getTransactions() {
		List<Transaction> transactionsList = new ArrayList<Transaction>(transactions);
		String transactionsString = "Last " + transactionsList.size() + " transactions";
		
		for (int i = 0; i < transactionsList.size(); ++i) {
			transactionsString += "\n" + (i + 1) + ") " + transactionsList.get(i).toString();
		}
		
		return transactionsString;
	}
	
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
