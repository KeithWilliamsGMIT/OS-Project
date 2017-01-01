# OS-Project
This is my project for my 3rd year operating systems systems module in college. The requirements for this project were to write a Multi-threaded TCP Server Application which allows multiple customers to update their bank accounts and send payments to other bank accounts.

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
2. The server should hold a list of valid users of the service and a list of all the users previous transactions.
3. When the user logs in they should receive their current balance.

#### Server
The server application is hosted on Amazon Web Services as required by the project specification.

#### Running the application locally
Open a terminal and clone this repository using the following command.
```
git clone https://github.com/KeithWilliamsGMIT/OS-Project.git
```

Next, go to the bin directory as follows.
```
cd OS-Project/MultiThreadedBankingApplication/bin
```

Run the client in this terminal.
```
java ie.gmit.sw.client.Runner
```

Open a second terminal in the same directory and run the server.
```
java ie.gmit.sw.server.Server
```