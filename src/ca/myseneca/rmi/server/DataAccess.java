/**
 * @CourseCode CJV805SAA.03567.2191
 * @Title Assignment 1
 * @author Pei Zhang
 * @Professor Tevin Apenteng
 * @Submission date Feb 07, 2019
 */
package ca.myseneca.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import ca.myseneca.model.DAManager;
import ca.myseneca.model.Employee;

public class DataAccess extends UnicastRemoteObject implements DataAccessInterface {

	public DataAccess() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getEmployeeID(String user, String password) throws RemoteException {
		
		return DAManager.getEmployeeID(user, password);
	}

	@Override
	public void addEmployee(Employee emp) throws RemoteException {
		DAManager.addEmployee(emp);
	}

	@Override
	public ArrayList<Employee> getAllEmployees() throws RemoteException {
		
		return DAManager.getAllEmployees();
	}

	@Override
	public ArrayList<Employee> getEmployeesByDepartmentID(int depid) throws RemoteException {
		// TODO Auto-generated method stub
		return DAManager.getEmployeesByDepartmentID(depid);
	}

	@Override
	public Employee getEmployeeByID(int empid) throws RemoteException {
		// TODO Auto-generated method stub
		return DAManager.getEmployeeByID(empid);
	}

	@Override
	public int updateEmployee(Employee emp) throws RemoteException {
		// TODO Auto-generated method stub
		return DAManager.updateEmployee(emp);
	}

	@Override
	public int deleteEmployeeByID(int empid) throws RemoteException {
		// TODO Auto-generated method stub
		return DAManager.deleteEmployeeByID(empid);
	}

	@Override
	public boolean batchUpdate(String[] SQLs) throws RemoteException {
		// TODO Auto-generated method stub
		return DAManager.batchUpdate(SQLs);
	}

	
}
