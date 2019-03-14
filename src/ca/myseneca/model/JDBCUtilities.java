/*
 * Copyright (c) 1995, 2011, Oracle and/or its affiliates. All rights reserved.
 */

package ca.myseneca.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * This utilities class provides the functionalities of loading properties file,
 * setting up JDBC connection, retrieve SQLException and SQLWarning.
 */
public class JDBCUtilities {
	private Properties dbProps;

	public JDBCUtilities(String propertiesFileName)
			throws FileNotFoundException, IOException, InvalidPropertiesFormatException {
		super();
		this.setProperties(propertiesFileName);

	}

	public static void printWarnings(SQLWarning warning) throws SQLException {
		if (warning != null) {
			System.out.println("\n---Warning---\n");
			while (warning != null) {
				System.out.println("Message: " + warning.getMessage());
				System.out.println("SQLState: " + warning.getSQLState());
				System.out.print("Vendor error code: ");
				System.out.println(warning.getErrorCode());
				System.out.println("");
				warning = warning.getNextWarning();
			}
		}
	}

	public static void printBatchUpdateException(BatchUpdateException b) {
		System.err.println("----BatchUpdateException----");
		System.err.println("SQLState:  " + b.getSQLState());
		System.err.println("Message:  " + b.getMessage());
		System.err.println("Vendor:  " + b.getErrorCode());
		System.err.print("Update counts:  ");
		int[] updateCounts = b.getUpdateCounts();
		for (int i = 0; i < updateCounts.length; i++) {
			System.err.print(updateCounts[i] + "   ");
		}
	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	private void setProperties(String fileName)
			throws FileNotFoundException, IOException, InvalidPropertiesFormatException {
		this.dbProps = new Properties();
		FileInputStream fis = new FileInputStream(fileName);
		dbProps.load(fis);
	}

	public Connection getConnection() throws SQLException, ClassNotFoundException {

		String driver = dbProps.getProperty("ORACLE_DB_DRIVER");
		String urlString = dbProps.getProperty("ORACLE_DB_THIN_DRIVER_CONNECT_DESCRIPTOR_URL");

		Properties connectionProps = new Properties();
		connectionProps.put("user", this.dbProps.getProperty("ORACLE_DB_USERNAME"));
		connectionProps.put("password", this.dbProps.getProperty("ORACLE_DB_PASSWORD"));

		Class.forName(driver);

		Connection conn = DriverManager.getConnection(urlString, connectionProps);

		System.out.println("Connected to database");
		return conn;
	}

	public static void closeConnection(Connection connArg) {
		System.out.println("Releasing all open resources ...");
		try {
			if (connArg != null) {
				connArg.close();
				connArg = null;
			}
		} catch (SQLException sqle) {
			printSQLException(sqle);
		}
	}
}
