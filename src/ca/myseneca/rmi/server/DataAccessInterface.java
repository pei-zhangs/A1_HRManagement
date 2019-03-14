/**
 * @CourseCode CJV805SAA.03567.2191
 * @Title Assignment 1
 * @author Pei Zhang
 * @Professor Tevin Apenteng
 * @Submission date Feb 07, 2019
 */
package ca.myseneca.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ca.myseneca.model.Employee;

public interface DataAccessInterface extends Remote {

	int getEmployeeID(String user, String password) throws RemoteException;

	void addEmployee(Employee emp) throws RemoteException;

	ArrayList<Employee> getAllEmployees() throws RemoteException;

	ArrayList<Employee> getEmployeesByDepartmentID(int depid) throws RemoteException;

	Employee getEmployeeByID(int empid) throws RemoteException;

	int updateEmployee(Employee emp) throws RemoteException;

	int deleteEmployeeByID(int empid) throws RemoteException;

	boolean batchUpdate(String[] SQLs) throws RemoteException;

}
