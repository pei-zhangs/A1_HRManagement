/**
 * @CourseCode CJV805SAA.03567.2191
 * @Title Assignment 1
 * @author Pei Zhang
 * @Professor Tevin Apenteng
 * @Submission date Feb 07, 2019
 */
package ca.myseneca.rmi.client;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import ca.myseneca.model.Employee;
import ca.myseneca.rmi.server.DataAccessInterface;

public class HRManagementClient {

	public static void main(String[] args) {

		Scanner reader = new Scanner(System.in);

		DataAccessInterface obj = null;
		try {
			//commecting to the server, calling the interface object in the server
			obj = (DataAccessInterface) Naming.lookup("rmi://localhost:1299/HRManagementServer");
			System.out.println("HR Management Server connected ");
			System.out.println("------HR Management Client started------");
			credentialCheck(obj);
			
			//output the options
			while (true) {
				System.out.println("---------------------------");
				System.out.println("Please selct the options below: ");
				System.out.println("1. Add new employee");
				System.out.println("2. Display all employees ");
				System.out.println("3. Display employees by department ID");
				System.out.println("4. Display employee by employee ID");
				System.out.println("5. Update employee information ");
				System.out.println("6. Delete employee");
				System.out.println("7. Execute SQL transaction");
				System.out.println("0. Exit");
				System.out.print("Your option: ");

			
				while (true) {
					String option = reader.nextLine();
					if (!checkInt(option)) {
						System.err.print("Please enter a valid number: ");
					} else if (Integer.parseInt(option) < 0 || Integer.parseInt(option) > 7) {
						System.err.print("Please select the number 0 - 7: ");
					} else {
						doOption(Integer.parseInt(option), obj);
						System.out.println("---------------------------");
						System.out.println("Please press enter to continue");
						reader.nextLine();
						break;
					}
				}
			}

		} catch (Exception e) {
			System.out.println("HR Management Client exception: " + e);
		}

	}

	// Do the selected options in the main method
	private static void doOption(int option, DataAccessInterface obj) throws RemoteException {

		switch (option) {
		case 1:
			addNewEmployee(obj);
			break;
		case 2:
			getAllEmployees(obj);
			break;
		case 3:
			getEmployeesOfADepartment(obj);
			break;
		case 4:
			getEmployeesByEmployeeId(obj);
			break;
		case 5:
			updateEmployeeInfo(obj);
			break;
		case 6:
			deleteEmployee(obj);
			break;
		case 7:
			executeTransaction(obj);
			break;
		case 0:
			System.out.println("--------Exited--------");
			System.exit(0);
			break;
		}
	}

	// Check the login credential
	private static void credentialCheck(DataAccessInterface obj) throws RemoteException {
		Scanner reader = new Scanner(System.in);
		System.out.print("Please enter your user name: ");
		String user = reader.nextLine();
		System.out.print("Please enter your password: ");
		String password = reader.nextLine();
//			 ;
		System.out.println("user: " + user);
		System.out.println("password: ******");

		int empid = obj.getEmployeeID(user, password);

		if (empid == 0) {
			System.err.println("Login failed due to the following possible reasons:");
			System.err.println(" - The account is not a HR representative's account");
			System.err.println(" - The user name or password is not correct");
			System.err.println(" - Your account is inactive");

			System.exit(0);
		} else {
			System.out.println("---------------------------");
			System.out.println("Welcome! " + obj.getEmployeeByID(empid).getFirstName() + " "
					+ obj.getEmployeeByID(empid).getLastName());
		}

	}

	// Add new employee into database
	private static void addNewEmployee(DataAccessInterface obj) throws RemoteException {
		Employee emp = new Employee();
		Scanner reader = new Scanner(System.in);
		System.out.println("Please enter the new employee's information:");
		// Read strings from the keyboard
		System.out.print("First name: ");
		String firstName = reader.nextLine();

		// Read and check last name. According to the constraint of Employees table,
		// last name cannot be empty
		String lastName;
		while (true) {
			System.out.print("\nLast name: ");
			lastName = reader.nextLine();
			if (lastName.equals("")) {
				System.err.print("\nThe last name cannot be empty. Please enter again:");
			} else {
				break;
			}
		}
		// Read and check email. According to the constraint of Employees table, email
		// cannot be empty
		String email;
		while (true) {
			System.out.print("Email: ");
			email = reader.nextLine();
			if (email.equals("")) {
				System.err.print("\nThe email cannot be empty. Please enter again:");
			} else {
				break;
			}
		}

		System.out.print("Phone NO.: ");
		String phoneNo = reader.nextLine();

		// Select job ID from the list.
		System.out.println("Please select the job ID from the list below: ");
		System.out.println("1. AD_PRES\t6. AC_MGR\t11. PU_CLERK\t16. MK_MAN");
		System.out.println("2. AD_VP\t7. AC_ACCOUNT\t12. ST_MAN\t17. MK_REP");
		System.out.println("3. AD_ASST\t8. SA_MAN\t13. ST_CLERK\t18. HR_REP");
		System.out.println("4. FI_MGR\t9. SA_REP\t14. SH_CLERK\t19. PR_REP");
		System.out.println("5. FI_ACCOUNT\t10. PU_MAN\t15. IT_PROG");
		String option;
		System.out.print("Job ID: ");
		while (true) {
			option = reader.nextLine();
			if (!checkInt(option)) {
				System.err.print("Please enter a valid number: ");
			} else if (Integer.parseInt(option) < 1 || Integer.parseInt(option) > 19) {
				System.err.print("Please select the number 1 - 19: ");
			} else {
				break;
			}
		}

		String jobId = null;

		switch (Integer.parseInt(option)) {
		case 1:
			jobId = "AD_PRES";
			break;
		case 2:
			jobId = "AD_VP";
			break;
		case 3:
			jobId = "AD_ASST";
			break;
		case 4:
			jobId = "FI_MGR";
			break;
		case 5:
			jobId = "FI_ACCOUNT";
			break;
		case 6:
			jobId = "AC_MGR";
			break;
		case 7:
			jobId = "AC_ACCOUNT";
			break;
		case 8:
			jobId = "SA_MAN";
			break;
		case 9:
			jobId = "SA_REP";
			break;
		case 10:
			jobId = "PU_MAN";
			break;
		case 11:
			jobId = "PU_CLERK";
			break;
		case 12:
			jobId = "ST_MAN";
			break;
		case 13:
			jobId = "ST_CLERK";
			break;
		case 14:
			jobId = "SH_CLERK";
			break;
		case 15:
			jobId = "IT_PROG";
			break;
		case 16:
			jobId = "MK_MAN";
			break;
		case 17:
			jobId = "MK_REP";
			break;
		case 18:
			jobId = "HR_REP";
			break;
		case 19:
			jobId = "PR_REP";
			break;
		}
		System.out.println("Job ID: " + jobId + " selected");

		// read double/integer numbers from keyboard, and make sure the number with
		// correct format is received
		String salary;
		while (true) {
			System.out.print("Salary (enter 0 for no salary): ");
			salary = reader.nextLine();
			if (!checkDouble(salary)) {
				System.err.println("Please enter a valid number for salary");
			} else {
				break;
			}
		}
		String commission;
		while (true) {
			System.out.print("Commission (enter 0 for no commission): ");
			commission = reader.nextLine();
			if (!checkDouble(commission) || Double.parseDouble(commission) >= 1 || Double.parseDouble(commission) < 0) {
				System.err.println("Please enter a valid number less than 1 for commission");
			} else {
				break;
			}
		}
		String managerId;
		while (true) {
			System.out.print("Manager ID (enter 0 for no manager): ");
			managerId = reader.nextLine();
			if (!checkInt(managerId)) {
				System.err.println("Please enter an integer for manager ID");
			} else {
				break;
			}
		}
		String departId;
		while (true) {
			System.out.print("Department ID (enter 0 for no department): ");
			departId = reader.nextLine();
			if (!checkInt(departId)) {
				System.err.println("Please enter an integer for manager ID");
			} else {
				break;
			}
		}
		;

		emp.setFirstName(firstName);
		emp.setLastName(lastName);
		emp.setEmail(email);
		emp.setPhoneNumber(phoneNo);
		emp.setJobId(jobId);
		emp.setSalary(Double.parseDouble(salary));
		emp.setCommission(Double.parseDouble(commission));
		emp.setManagerId(Integer.parseInt(managerId));
		emp.setDepartment_id(Integer.parseInt(departId));

		obj.addEmployee(emp);
		System.out.println("---------------------------");
		System.out.println("New employee added");
	}

	// Display all employees
	private static ArrayList<Employee> getAllEmployees(DataAccessInterface obj) throws RemoteException {
		int nRows = 0;
		ArrayList<Employee> empList = obj.getAllEmployees();
		for (Employee emp : empList) {
			System.out.println(emp);
			nRows++;
		}
		System.out.println("---------------------------");
		System.out.println(nRows + " employee(s) in total");
		return empList;
	}

	// Display all employees in a department
	private static ArrayList<Employee> getEmployeesOfADepartment(DataAccessInterface obj) throws RemoteException {
		int nRows = 0;
		Scanner reader = new Scanner(System.in);
		System.out.print("Please enter the department ID: ");
		String deptid;
		while (true) {
			deptid = reader.nextLine();
			if (!checkInt(deptid)) {
				System.err.print("Please enter a valid department ID:");
			} else {
				break;
			}
		}
		;
		ArrayList<Employee> empList = obj.getEmployeesByDepartmentID(Integer.parseInt(deptid));
		for (Employee emp : empList) {
			System.out.println(emp);
			nRows++;
		}
		System.out.println("---------------------------");
		System.out.println(nRows + " employee(s) in department " + deptid);

		return empList;
	}

	// prompt user to enter an employee ID then display this employee's
	// information and return a Employee object
	private static Employee getEmployeesByEmployeeId(DataAccessInterface obj) throws RemoteException {
		Scanner reader = new Scanner(System.in);
		System.out.print("Please enter the employee ID: ");
		String empid;
		while (true) {
			empid = reader.nextLine();
			if (!checkInt(empid)) {
				System.err.print("Please enter a valid employee ID:");
			} else {
				break;
			}
		}
		;
		Employee emp = obj.getEmployeeByID(Integer.parseInt(empid));
		if (emp.getEmployeeId() == 0) {
			System.out.println("---------------------------");
			System.err.println("Employee not found");

		} else {
			System.out.println("---------------------------");
			System.out.println(emp);
		}

		return emp;
	}

	// prompt user to enter an employee ID then update this employee's record
	private static void updateEmployeeInfo(DataAccessInterface obj) throws RemoteException {
		String empid;
		Scanner reader = new Scanner(System.in);
		Employee emp = new Employee();
		System.out.print("Update employee's information. Please enter the employee ID:");
		while (true) {
			empid = reader.nextLine();
			if (!checkInt(empid)) {
				System.err.print("Please enter a valid employee ID:");
			} else {
				emp = obj.getEmployeeByID(Integer.parseInt(empid));
				if (emp.getEmployeeId() == 0) {
					System.out.println("---------------------------");
					System.err.print("Employee not found, Please enter a valid employee ID: ");
				} else {
					break;
				}
			}
		}

		System.out.println("Employee found");
		System.out.println(emp);
		System.out.println("---------------------------");
		// Read strings from the keyboard
		System.out.println("please enter the new information");
		System.out.print("First name: ");
		String firstName = reader.nextLine();

		// Read and check last name. According to the constraint of Employees table,
		// last name cannot be empty
		String lastName;
		while (true) {
			System.out.print("Last name: ");
			lastName = reader.nextLine();
			if (lastName.equals("")) {
				System.err.println("\nThe last name cannot be empty. Please enter again:");
			} else {
				break;
			}
		}
		// Read and check email. According to the constraint of Employees table, email
		// cannot be empty
		String email;
		while (true) {
			System.out.print("Email: ");
			email = reader.nextLine();
			if (email.equals("")) {
				System.err.println("\nThe email cannot be empty. Please enter again:");
			} else {
				break;
			}
		}

		System.out.print("Phone NO.: ");
		String phoneNo = reader.nextLine();

		// Select job ID from the list.
		System.out.println("Please select the job ID from the list below: ");
		System.out.println("1. AD_PRES\t6. AC_MGR\t11. PU_CLERK\t16. MK_MAN");
		System.out.println("2. AD_VP\t7. AC_ACCOUNT\t12. ST_MAN\t17. MK_REP");
		System.out.println("3. AD_ASST\t8. SA_MAN\t13. ST_CLERK\t18. HR_REP");
		System.out.println("4. FI_MGR\t9. SA_REP\t14. SH_CLERK\t19. PR_REP");
		System.out.println("5. FI_ACCOUNT\t10. PU_MAN\t15. IT_PROG");
		String option;
		System.out.print("Job ID: ");
		while (true) {
			option = reader.nextLine();
			if (!checkInt(option)) {
				System.err.print("Please enter a valid number: ");
			} else if (Integer.parseInt(option) < 1 || Integer.parseInt(option) > 19) {
				System.err.print("Please select the number 1 - 19: ");
			} else {
				break;
			}
		}

		String jobId = null;

		switch (Integer.parseInt(option)) {
		case 1:
			jobId = "AD_PRES";
			break;
		case 2:
			jobId = "AD_VP";
			break;
		case 3:
			jobId = "AD_ASST";
			break;
		case 4:
			jobId = "FI_MGR";
			break;
		case 5:
			jobId = "FI_ACCOUNT";
			break;
		case 6:
			jobId = "AC_MGR";
			break;
		case 7:
			jobId = "AC_ACCOUNT";
			break;
		case 8:
			jobId = "SA_MAN";
			break;
		case 9:
			jobId = "SA_REP";
			break;
		case 10:
			jobId = "PU_MAN";
			break;
		case 11:
			jobId = "PU_CLERK";
			break;
		case 12:
			jobId = "ST_MAN";
			break;
		case 13:
			jobId = "ST_CLERK";
			break;
		case 14:
			jobId = "SH_CLERK";
			break;
		case 15:
			jobId = "IT_PROG";
			break;
		case 16:
			jobId = "MK_MAN";
			break;
		case 17:
			jobId = "MK_REP";
			break;
		case 18:
			jobId = "HR_REP";
			break;
		case 19:
			jobId = "PR_REP";
			break;
		}
		System.out.println("Job ID: " + jobId + " selected");

		// read double/integer numbers from keyboard, and make sure the number with
		// correct format is received
		String salary;
		while (true) {
			System.out.print("Salary: ");
			salary = reader.nextLine();
			if (!checkDouble(salary)) {
				System.err.println("Please enter a valid number for salary");
			} else {
				break;
			}
		}
		String commission;
		while (true) {
			System.out.print("Commission: ");
			commission = reader.nextLine();
			if (!checkDouble(commission) || Double.parseDouble(commission) >= 1 || Double.parseDouble(commission) < 0) {
				System.err.println("Please enter a valid number less than 1 for commission");
			} else {
				break;
			}
		}
		String managerId;
		while (true) {
			System.out.print("Manager ID: ");
			managerId = reader.nextLine();
			if (!checkInt(managerId)) {
				System.err.println("Please enter an integer for manager ID");
			} else {
				break;
			}
		}
		String departId;
		while (true) {
			System.out.print("Department ID: ");
			departId = reader.nextLine();
			if (!checkInt(departId)) {
				System.err.println("Please enter an integer for manager ID");
			} else {
				break;
			}
		}
		;
		emp.setFirstName(firstName);
		emp.setLastName(lastName);
		emp.setEmail(email);
		emp.setPhoneNumber(phoneNo);
		emp.setJobId(jobId);
		emp.setSalary(Double.parseDouble(salary));
		emp.setCommission(Double.parseDouble(commission));
		emp.setManagerId(Integer.parseInt(managerId));
		emp.setDepartment_id(Integer.parseInt(departId));
		obj.updateEmployee(emp);
		System.out.println("---------------------------");
		System.err.println("Employee " + emp.getEmployeeId() + "  updated");

	}

	// prompt user to enter an employee ID then delete this employee's record
	public static void deleteEmployee(DataAccessInterface obj) throws RemoteException {
		Scanner reader = new Scanner(System.in);
		System.out.println("Deleting employee. ");
		String empid;
		System.out.print("Please enter the employee ID: ");
		while (true) {
			empid = reader.nextLine();
			if (!checkInt(empid)) {
				System.err.print("Please enter a valid employee ID:");
			} else {
				break;
			}
		}
		int nRows = obj.deleteEmployeeByID(Integer.parseInt(empid));
		if (nRows == 1) {
			System.out.println("---------------------------");
			System.out.println(nRows + " employee deleted");

		} else {
			System.out.println("---------------------------");
			System.err.println("Employee not found or cannot be deleted");
		}
	}

	// prompt user to enter multiple SQL statements then execute these statements
	public static void executeTransaction(DataAccessInterface obj) throws RemoteException {
		Scanner reader = new Scanner(System.in);

		String command;
		ArrayList<String> sqlList = new ArrayList<>();
		System.out.println("\nPlease enter SQL statements (enter RUN to execute the transaction): ");

		while (true) {
			System.out.print("SQL> ");
			command = reader.nextLine();
			if (command.equals("RUN"))
				break;

			else if (!command.equals("")) {
				sqlList.add(command);
			}
		}
		;
		int n = sqlList.size();
		String[] SQLs = new String[n];

		for (int i = 0; i <= n - 1; i++) {
			SQLs[i] = sqlList.get(i);
		}

		boolean affected = obj.batchUpdate(SQLs);
		if (affected) {
			System.out.println("---------------------------");
			System.out.println("Transaction succeeded");
		}else {
			System.out.println("---------------------------");
			System.err.println("Transaction failed");
			
		}
	}

	// Check weather the input is integer
	private static boolean checkInt(String s) throws RemoteException {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Check weather the input is double
	private static boolean checkDouble(String s) throws RemoteException {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
