/*
 * Keith Williams (G00324844)
 * 30/12/2016
 */

package ie.gmit.sw.server;

import java.util.*;

public class UserManager {
	// A list of all registered users
	private List<User> registeredUsers = Collections.synchronizedList(new LinkedList<User>());
	
	// A list of all users that are currently logged in
	private List<User> loggedInUsers = Collections.synchronizedList(new LinkedList<User>());
	
	// Constructor
	public UserManager() {
		UserIO io = new UserIO();
		
		// Try to load all saved users
		try {
			registeredUsers.addAll(io.loadAllUsers());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Methods
	// Add the user to a collection of registered users
	public void registerUser(User userToRegister) {
		registeredUsers.add(userToRegister);
	}
	
	// Return true if the user is in the collection of registered users
	public boolean isUserRegistered(User userToCheck) {
		// This is also an O(n) operation which might cause performance problems when there are many registered users
		return registeredUsers.contains(userToCheck);
	}
	
	// Add the user to a collection of logged in users if they are registered.
	// Return the registered user if they logged in successfully.
	// Throw an exception if the users credentials are incorrect or they are already logged in.
	public User loginUser(User userToLogin) throws Exception {
		User user = null;
		boolean isSuccessful = false;
		int registeredIndex = registeredUsers.indexOf(userToLogin);
		int loggedInIndex = loggedInUsers.indexOf(userToLogin);
		
		if (registeredIndex == -1) { // If not registered
			throw new Exception("The credentials you entered do not match any registered account!");
		} else if (loggedInIndex != -1) { // If logged in
			throw new Exception("You are already logged in!");
		} else { // Check if credentials match a registered account
			isSuccessful = registeredUsers.get(registeredIndex).getUsername().equals(userToLogin.getUsername())
						&& registeredUsers.get(registeredIndex).getPassword().equals(userToLogin.getPassword());
		}
		
		// Check if the login was successful
		if (isSuccessful) {
			user = registeredUsers.get(registeredIndex);
			loggedInUsers.add(user);
		}
		
		return user;
	}
	
	// Remove the user from the collection of logged in users
	public void logoutUser(User userToLogout) {
		loggedInUsers.remove(userToLogout);
	}
}
