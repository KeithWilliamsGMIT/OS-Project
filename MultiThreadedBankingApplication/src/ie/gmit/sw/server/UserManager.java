/*
 * Keith Williams (G00324844)
 * 30/12/2016
 */

package ie.gmit.sw.server;

import java.util.*;

public class UserManager {
	// A list of all registered users
	private List<User> registeredUsers = Collections.synchronizedList(new ArrayList<User>());
	
	// A list of all users that are currently logged in
	private List<User> loggedInUsers = Collections.synchronizedList(new ArrayList<User>());
	
	// Methods
	public void registerUser(User userToRegister) {
		// This is an O(n) operation which might cause performance problems when there are many registered users
		registeredUsers.add(userToRegister);
	}
	
	public boolean isUserRegistered(User userToCheck) {
		// This is also an O(n) operation which might cause performance problems when there are many registered users
		return registeredUsers.contains(userToCheck);
	}
	
	public boolean loginUser(User userToLogin) {
		boolean isSuccessful = false;
		int registeredIndex = registeredUsers.indexOf(userToLogin);
		int loggedInIndex = loggedInUsers.indexOf(userToLogin);
		
		// Check if the user is registered and is not already logged in
		if (registeredIndex != -1 && loggedInIndex == -1) {
			isSuccessful = registeredUsers.get(registeredIndex).getUsername().equals(userToLogin.getUsername())
						&& registeredUsers.get(registeredIndex).getPassword().equals(userToLogin.getPassword());
		}
		
		if (isSuccessful) {
			loggedInUsers.add(userToLogin);
		}
		
		return isSuccessful;
	}
}
