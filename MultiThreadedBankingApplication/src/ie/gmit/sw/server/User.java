/*
 * Keith Williams (G00324844)
 * 19/12/2016
 */

package ie.gmit.sw.server;

import java.io.Serializable;

public class User implements Serializable {
	private String name;
	private String address;
	private Account account;
	private String username;
	private String password;
	
	// Constructors
	public User(String name, String address, int accountNumber, String username, String password) {
		super();
		this.name = name;
		this.address = address;
		this.account = new Account(accountNumber);
		this.username = username;
		this.password = password;
	}
	
	// Getters and Setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Account getAccount() {
		return account;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	// Methods
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
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
		
		User other = (User) obj;
		
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		
		return true;
	}
	
}
