/*
 * Keith Williams (G00324844)
 * 10/1/2016
 */

package ie.gmit.sw.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.LinkedList;

public class UserIO {
	// Path to the folder where user files are saved
	private final String PATH = "resources/users";
	
	// Save the user object to a file named after the users unique account number
	public void saveUser(User userToSave) throws IOException {
		String filepath = getClass().getResource(PATH).getPath() + "/" + userToSave.getAccount().getNumber() + ".txt";
		File file = new File(filepath);
		FileOutputStream f = new FileOutputStream(file);
		ObjectOutputStream o = new ObjectOutputStream(f);

		// Write user to file
		o.writeObject(userToSave);

		// Close streams
		o.close();
		f.close();
	}

	// Load all user objects and return them as a list
	public List<User> loadAllUsers() throws IOException, ClassNotFoundException {
		String path = getClass().getResource(PATH).getPath();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		List<User> loadedSsers = new LinkedList<User>();
		
		// For all files in folder...
		for (int i = 0; i < listOfFiles.length; i++) {
			// If they are files and not directories...
			if (listOfFiles[i].isFile()) {
				FileInputStream fi = new FileInputStream(new File(path + "/" + listOfFiles[i].getName()));
				ObjectInputStream oi = new ObjectInputStream(fi);

				// Read user object from file
				User loadedUser = (User) oi.readObject();
				loadedSsers.add(loadedUser);

				// Close streams
				oi.close();
				fi.close();
			}
		}

		return loadedSsers;
	}
}
