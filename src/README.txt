Project Title:			        Scheduler
Application Version:            1.0
Application Date:		        April 20, 2023

Project Purpose: 		        To provide a desktop GUI scheduling application.

Author Information:		        The Lonely Dash (Topher Shortt)
				                1062 Lockhart Circle
				                Sarnia, Ontario N7S 2E5
				                226-792-4777

IDE Information:		        IntelliJ IDEA 2021.1.3 (Community Edition)
				                Build #IC-211.7628.21, built on June 30, 2021
				                Runtime Version: 11.0.11+9-b1321.60 amd64
				                VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.

Java Information:		        Javafx: openjfx-11.0.2
				                Mysql Connector: mysql-connector-java-8.0.26

Additional Report Information:	My extra report displays the number of customers in each country.
				                Three lists were created (one for each country) and all customers
				                were evaluated for their country.  Once it was determined, they
				                were added to the lists.  The size of each list would then be
				                the total number of customers in each country.

How to Run the Program:		    Open Intellij CE
				                Select "IntelliJ IDEA" on the menu bar.
				                Select "Preferences..."
				                Select "Path Variables" on the left.
				                Set variable PATH_TO_FX to the directory of your JavaFX library
				                (e.g. /Library/javafx-sdk-11.0.2/lib).
				                Select "Run" and then "Run 'Main'" on the menu bar.

Program Navigation:		        The application starts at the login screen where the user will be prompted for a user name and password.
				                The current valid credentials are (username: "test", password: "test) and (username: "admin", password: "admin").
				                Upon successful login the user is directed to the main menu which consists of four buttons: Appointments, Customers, Reports, Exit

				                The exit button logs the user off and takes them back to the login screen.

				                The appointments button directs the user to the appointments gui where a table view of all appointments is able to be viewed.
				                Three radio buttons are present allowing the user to organize the table view by All, Month, or Week.
				                The user may choose to add a new appointment, update a current appointment, or delete an appointment.
				                The back button will direct the user to the main menu.

				                The Customers button directs the user to the customers gui where a table view of all customers is able to be viewed.
				                The user may choose to add a new customer, update a current customer, or delete a customer.
				                The back button will direct the user to the main menu.

				                The reports button directs the user to the reports gui, where a tableview of customer schedules is available via combobox.
				                Tab 2 consists of information regarding the number of appointments by each month and type available by combobox.
				                Tab 3 consists of information regarding the number of customers in each of the three countries.
				                The back button will direct the user to the main menu.