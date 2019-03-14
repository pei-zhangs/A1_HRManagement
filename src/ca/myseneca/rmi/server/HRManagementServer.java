/**
 * @CourseCode CJV805SAA.03567.2191
 * @Title Assignment 1
 * @author Pei Zhang
 * @Professor Tevin Apenteng
 * @Submission date Feb 07, 2019
 */
package ca.myseneca.rmi.server;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
public class HRManagementServer {
	public static void main(String[] args) {
		
		
		try {
			DataAccessInterface obj = new DataAccess();
			// Bind this object instance to the name "HR Management Server"
			LocateRegistry.createRegistry(1299);
			Naming.rebind("rmi://localhost:1299/HRManagementServer", obj);
			System.out.println("HR Management Server bound in registry");

		} catch (Exception e) {
			System.out.println("HR Management Server err: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
