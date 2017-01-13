# OS-Project
This is my project for my 3rd year operating systems module in college. The requirements for this project were to write a Multi-threaded TCP Server Application which allows multiple customers to update their bank accounts and send payments to other bank accounts.

The service should allow the users to:

1. Register with the system
	+ Name
	+ Address
	+ Bank A/C Number
	+ Username
	+ Password
2. Log-in to the banking system from the client application to the server application.
3. Change customer details.
4. Make Lodgements to their Bank Account.
5. Make Withdrawal from their Bank Account (Note: Each User has a credit limit of €1000).
6. View the last ten transactions on their bank account.

#### Server Application Rules
1. The server application should not provide any service to a client application that can complete the authentication.
2. The server should hold a list of valid users of the service and a list of all the users’ previous transactions.
3. When the user logs in they should receive their current balance.

#### Design Decisions
This section lists the different decisions that were made when designing how this application will work.

+ When the client first opens the application it will connect to the server and allow the user to either register a new account, login to an existing account or exit the application.

+ The user must register in order to user this service. They must enter their name, address, account number, username and password as required by the project specification. Their name and address may contain spaces whereas the username and password may not. If the user enters a username or password that contains spaces it will only use the sequence of characters upto the first space. No input can be empty. The account number must be a positive integer. These rules are enforced throughout the application. If they enter an account number which is already used by another account the user will receive an error. If they register correctly they will be brought back to the first menu where they can then login.

+ The user must log into their account in order to access the service. This was required by the project specification. The user needs to enter their account number, username and password. If they enter all three credentials correctly and are not already logged in they will be given access to the service. Otherwise they will be brought back to the first menu where they can attempt to login again.

+ Once the user logs in they will receive their current balance and a range of options listed in the description.

+ I decided that, when the user wants to change the users’ details, they must re-enter all details except for the account number. I chose this method as I thought it would be clearer for the user rather than having extra menus to choose which details to change.

+ When the user wants to lodge or withdraw money they must enter a positive float. Also the amount they try to withdraw must be less than or equal to the users current balance. There is also a credit limit of €1000 outlined in the specification. If the amount they wish to withdraw exceeds either of these they will receive an error message.

+ Logging out will return the user to the first menu where they can login again or exit the application.

+ Each user object is saved to a separate file called &lt;no&gt;.txt where no is the users account number. I choose the account number because it is unique to all users. I chose not to save all users to the save file as it posed a number of problems. The main issues were, only one thread could write to the file at a time and also either the user that needed to be updated would have to be found in the file or the whole file would needed to be overridden. Each user object is saved every time it is updated. I chose to do it this way rather than periodic updates because of the nature of the data. Banking information must be save even if the server closes unexpectedly. These files are loaded when the server is started. I decided not to use a database as the specification did not specify the need for one and it was straight-forward to save the user objects in files.

#### Test Account
There is an empty test account with the following credentials.

+ Account number: 123
+ Username: test
+ Password: test

#### Server
The server application is hosted on Amazon Web Services as required by the project specification.

#### Running the application
Open a terminal and clone this repository using the following command.
```
git clone https://github.com/KeithWilliamsGMIT/OS-Project.git
```

Next, go to the bin directory as follows.
```
cd OS-Project/MultiThreadedBankingApplication/bin
```

You can then decide whether you want to run the application locally or remotely.

###### Remotely
To run the server remotely simply start the client as follows.
```
java ie.gmit.sw.client.Runner
```

###### Locally
To run the server locally you fill first have to run the server as follows.
```
java ie.gmit.sw.server.Server
```

Then, in the Client class, set IP_ADDRESS to 127.0.0.1. Now open a second terminal in the bin directory and run the client.
```
java ie.gmit.sw.client.Runner
```

#### Conclusion
I believe that I have implemented all requested functionality outlined in the project specification. After completing this project I have a better understanding of multi-threaded programming and socket programming. I also learned through this project how to set up a virtual machine on Amazon Web Services.