/**
 * @CourseCode CJV805SAA.03567.2191
 * @Title Assignment 1
 * @author Pei Zhang
 * @Professor Tevin Apenteng
 * @Submission date Feb 07, 2019
 */
package ca.myseneca.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import ca.myseneca.model.DAManager;
import ca.myseneca.model.Employee;

public class HRManagement {

	// Prompt user a request message, and read the user's input

	public static void main(String[] args) {

		credentialCheck();
		addNewEmployee();
		getAllEmployees();
		getEmployeesOfADepartment();
		getEmployeesByEmployeeId();
		deleteEmployee();
		updateEmployeeInfo();
		deleteEmployee();
		executeTransaction();
	}

	// Check the login credential
	private static void credentialCheck() {
		Scanner reader = new Scanner(System.in);
		System.out.print("Please enter your user name: ");
		String user = reader.nextLine();
		System.out.print("Please enter your password: ");
		String password = reader.nextLine();
//		 ;
		System.out.println("user: " + user);
		System.out.println("password: ******");

		int empiId = DAManager.getEmployeeID(user, password);

		if (empiId == 0) {
			System.err.println("Login failed due to the following possible reasons:");
			System.err.println(" - The account is not a HR representative's account");
			System.err.println(" - The user name or password is not correct");
			System.err.println(" - Your account is inactive");

			System.exit(0);
		} else {
			System.out.println("Welcome!Employee " + DAManager.getEmployeeByID(empiId).getFirstName() + " "
					+ DAManager.getEmployeeByID(empiId).getLastName());
		}

	}

	// Add new employee into database
	private static void addNewEmployee() {
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
			System.out.print("Last name: ");
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
		 

		emp.setFirstName(firstName);
		emp.setLastName(lastName);
		emp.setEmail(email);
		emp.setPhoneNumber(phoneNo);
		emp.setJobId(jobId);
		emp.setSalary(Double.parseDouble(salary));
		emp.setCommission(Double.parseDouble(commission));
		emp.setManagerId(Integer.parseInt(managerId));
		emp.setDepartment_id(Integer.parseInt(departId));

		DAManager.addEmployee(emp);
	}

	// Display all employees
	private static ArrayList<Employee> getAllEmployees() {
		int nRows = 0;
		ArrayList<Employee> empList = DAManager.getAllEmployees();
		for (Employee emp : empList) {
			System.out.println(emp);
			nRows++;
		}
		System.out.println("---------------------------");
		System.out.println(nRows + " employee(s) in total");
		return empList;
	}

	// Display all employees in a department
	private static ArrayList<Employee> getEmployeesOfADepartment() {
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
		ArrayList<Employee> empList = DAManager.getEmployeesByDepartmentID(Integer.parseInt(deptid));
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
	private static Employee getEmployeesByEmployeeId() {
		Scanner reader = new Scanner(System.in);
		System.out.print("Please enter the employee ID: ");
		String empiId;
		while (true) {
			empiId = reader.nextLine();
			if (!checkInt(empiId)) {
				System.err.print("Please enter a valid employee ID:");
			} else {
				break;
			}
		}
		 ;
		Employee emp = DAManager.getEmployeeByID(Integer.parseInt(empiId));
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
	private static void updateEmployeeInfo() {
		String empiId;
		Scanner reader = new Scanner(System.in);
		Employee emp = new Employee();
		System.out.print("Update employee's information. Please enter the employee ID:");
		while (true) {
			empiId = reader.nextLine();
			if (!checkInt(empiId)) {
				System.err.print("Please enter a valid employee ID:");
			} else {
				emp = DAManager.getEmployeeByID(Integer.parseInt(empiId));
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
		DAManager.updateEmployee(emp);

	}

	// prompt user to enter an employee ID then delete this employee's record
	public static void deleteEmployee() {
		Scanner reader = new Scanner(System.in);
		System.out.println("Deleting employee. ");
		String empiId;
		System.out.print("Please enter the employee ID: ");
		while (true) {
			empiId = reader.nextLine();
			if (!checkInt(empiId)) {
				System.err.print("Please enter a valid employee ID:");
			} else {
				break;
			}
		}
		DAManager.deleteEmployeeByID(Integer.parseInt(empiId));
		 ;
	}

	// prompt user to enter multiple SQL statements then execute these statements
	public static void executeTransaction() {
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

		DAManager.batchUpdate(SQLs);
	}

	// Check weather the input is integer
	private static boolean checkInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// Check weather the input is double
	private static boolean checkDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
