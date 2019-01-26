/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prt.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author P-ratt
 */
public class DBProcess {

	public static void DoAddUser(String[] params) throws Exception {
		Connection conn = DBConfig.getCurrent().getConnection();
		if (conn != null) {
			DoAddUser(conn, params);
		}
	}

	public static void DoAddUser(Connection c, String[] params) {
		try {
			String query = "INSERT INTO PASSWORD (PASSWORD, SALT) VALUES (?, ?)";
			PreparedStatement insert = c.prepareStatement(query);
			insert.setString(1, params[0]);
			insert.setString(2, params[1]);
			insert.execute();
			query = "SELECT ID FROM PASSWORD WHERE PASSWORD = ?";
			PreparedStatement stmt = c.prepareStatement(query);
			stmt.setString(1, params[0]);
			ResultSet set = stmt.executeQuery();
			String id = null;
			while (set.next()) {
				id = set.getString("ID");
			}
			if (id != null) {
				query = "INSERT INTO USERS (FIRSTNAME, LASTNAME, USERNAME, PASSID, ADMIN) VALUES (?, ?, ?, ?, ?)";
				insert = c.prepareStatement(query);
				insert.setString(1, params[2]);
				insert.setString(2, params[3]);
				insert.setString(3, params[4]);
				insert.setString(4, id);
				insert.setString(5, params[5]);
				insert.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void DoAddDatabases(String[][][][] params) throws Exception {
		Connection conn = DBConfig.getCurrent().getConnection();
		if (conn != null) {
			try {
				ArrayList<String> tables = DBConfig.getCurrent().getTables();
				if (!tables.contains("DATABASES")) {
					createDBTable();
				}
				if (!tables.contains("TABLES")) {
					createTablesTable();
				}
				if (!tables.contains("COLUMNS")) {
					createColumnsTable();
				}
				for (String[][][] database : params) {
					String dbQuery = "INSERT INTO DATABASES (NAME, DESC) VALUES (?, ?)";
					PreparedStatement dbStmt = conn.prepareStatement(dbQuery);
					dbStmt.setString(1, database[0][0][0]);
					dbStmt.setString(2, database[0][0][1]);
					dbStmt.execute();
					dbStmt.close();
					String dbId = null;
					dbStmt = conn.prepareStatement("SELECT ID FROM DATABASES WHERE NAME = ? AND DESC = ?");
					dbStmt.setString(1, database[0][0][0]);
					dbStmt.setString(2, database[0][0][1]);
					ResultSet set = dbStmt.executeQuery();
					while (set.next()) {
						dbId = set.getString("ID");
					}
					set.close();
					dbStmt.close();
					for (String[][] table : database) {
						String tblQuery = "INSERT INTO TABLES (NAME, DESC) VALUES (?, ?)";
						PreparedStatement tblStmt = conn.prepareStatement(tblQuery);
						tblStmt.setString(1, table[0][0]);
						tblStmt.setString(2, table[0][1]);
						tblStmt.execute();
						tblStmt.close();
						String tblId = null;
						tblStmt = conn.prepareStatement("SELECT ID FROM TABLES WHERE NAME = ? AND DESC = ?");
						tblStmt.setString(1, table[0][0]);
						tblStmt.setString(2, table[0][1]);
						ResultSet tblSet = tblStmt.executeQuery();
						while (tblSet.next()) {
							tblId = tblSet.getString("ID");
						}
						tblSet.close();
						tblStmt.close();
						if (tblId != null && dbId != null) {
							tblStmt = conn.prepareStatement("INSERT INTO DB_TABLE_XREF (DB_ID, TABLE_ID) VALUES (?, ?)");
							tblStmt.setString(1, dbId);
							tblStmt.setString(2, tblId);
							tblStmt.execute();
							tblStmt.close();
						}
						for (String[] column : table) {
							String colQuery = "INSERT INTO COLUMNS "
									+ "(NAME, DESC, TYPE, CHAR_LENGTH, PRIMARY_KEY, AUTO_INCREMENTED, NOT_NULL) "
									+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
							PreparedStatement colStmt = conn.prepareStatement(colQuery);
							colStmt.setString(1, column[0]);
							colStmt.setString(2, column[1]);
							colStmt.setString(3, column[2]);
							colStmt.setString(4, column[3]);
							colStmt.setString(5, column[4]);
							colStmt.setString(6, column[5]);
							colStmt.setString(7, column[6]);
							colStmt.execute();
							colStmt.close();
							String colId = null;
							colStmt = conn.prepareStatement("SELECT ID FROM COLUMNS WHERE NAME = ? AND DESC = ?");
							colStmt.setString(1, column[0]);
							colStmt.setString(2, column[1]);
							ResultSet colSet = colStmt.executeQuery();
							while (colSet.next()) {
								colId = colSet.getString("ID");
							}
							colSet.close();
							colStmt.close();
							if (colId != null && tblId != null) {
								colStmt = conn.prepareStatement("INSERT INTO COL_TABLE_XREF (COL_ID, TABLE_ID) VALUES (?, ?)");
								colStmt.setString(1, colId);
								colStmt.setString(2, tblId);
								colStmt.execute();
								colStmt.close();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void createTables(String[][][] params) throws Exception {
		Connection conn = DBConfig.getCurrent().getConnection();
		if (conn != null) {
			for (String[][] table : params) {
				String separator = "";
				String query = "CREATE " + table[0][0] + " (";
				for (String[] column : table) {
					query += separator + column[0] + " " + column[1];
					separator = ", ";
				}
				query += ") ";
				Statement stmt = conn.createStatement();
				stmt.execute(query);
				stmt.close();
			}
		}
	}

	public static void createDBTable() throws Exception {
		String[][][] params = new String[][][]{
			{{"DATABASES"}},
			{
				{"ID", "INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"},
				{"NAME", "VARCHAR(100) NOT NULL"},
				{"DESC", "VARCHAR(512)"},
				{"USERNAME", "VARCHAR(100) NOT NULL"},
				{"PASSWORD", "VARCHAR(512) NOT NULL"}
			}
		};
		createTables(params);
	}

	public static void createTablesTable() throws Exception {
		String[][][] params = new String[][][]{
			{{"TABLES"}},
			{
				{"ID", "INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"},
				{"NAME", "VARCHAR(100) NOT NULL"},
				{"DESC", "VARCHAR(512)"}
			},
			{{"DB_TABLE_XREF"}},
			{
				{"ID", "INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"},
				{"TABLE_ID", "INTEGER NOT NULL"},
				{"DB_ID", "INTEGER NOT NULL"}
			}
		};
		createTables(params);
	}

	public static void createColumnsTable() throws Exception {
		String[][][] params = new String[][][]{
			{{"COLUMNS"}},
			{
				{"ID", "INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"},
				{"NAME", "VARCHAR(100) NOT NULL"},
				{"DESC", "VARCHAR(512)"},
				{"CHAR_LENGTH", "INTEGER"},
				{"AUTO_INCREMENTED", "INTEGER"},
				{"PRIMARY_KEY", "INTEGER"},
				{"TYPE", "VARCHAR(50) NOT NULL"},
				{"NOT_NULL", "INTEGER"}
			},
			{{"COL_TABLE_XREF"}},
			{
				{"ID", "INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)"},
				{"COL_ID", "INTEGER NOT NULL"},
				{"TABLE_ID", "INTEGER NOT NULL"}
			}
		};
		createTables(params);
	}

	public static String[][][] DoGetDatabases() throws Exception {
		Connection conn = DBConfig.getCurrent().getConnection();
		if (conn != null) {
			try {
				//Check if table exists. if not then we will create it
				ArrayList<String> tables = DBConfig.getCurrent().getTables();
				if (!tables.contains("DATABASES")) {
					createDBTable();
				}
				//select databases
				Statement stmt = conn.createStatement();
				ResultSet set = stmt.executeQuery("SELECT ID, NAME, DESC FROM DATABASES");
				ArrayList<String[][]> dbList = new ArrayList<>();
				while (set.next()) {
					ArrayList<String[]> databases = new ArrayList<>();
					databases.add(new String[]{"ID", set.getString("ID")});
					databases.add(new String[]{"NAME", set.getString("NAME")});
					databases.add(new String[]{"DESC", set.getString("DESC")});
					dbList.add(databases.toArray(new String[databases.size()][]));
				}
				set.close();
				stmt.close();
				dbList.toArray(new String[dbList.size()][][]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String[][][] DoGetDatabaseTables(String dbid) throws Exception {
		Connection conn = DBConfig.getCurrent().getConnection();
		if (conn != null) {
			try {
				ArrayList<String> tables = DBConfig.getCurrent().getTables();
				if (!tables.contains("TABLES")) {
					createTablesTable();
				}
				//select tables for databases
				PreparedStatement stmt = conn.prepareStatement("SELECT T.ID, T.NAME, T.DESC FROM TABLES T JOIN DB_TABLE_XREF DTX ON T.ID = DTX.TABLE_ID WHERE DTX.DB_ID = ?");
				stmt.setString(1, dbid);
				ResultSet set = stmt.executeQuery();
				ArrayList<String[][]> tableResults = new ArrayList<>();
				while (set.next()) {
					tableResults.add(new String[][]{
						{"ID", set.getString("ID")},
						{"NAME", set.getString("NAME")},
						{"DESC", set.getString("DESC")}
					});
				}
				set.close();
				stmt.close();
				return tableResults.toArray(new String[tableResults.size()][][]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String[][][] DoGetTableColumns(String tblid) throws Exception {
		Connection conn = DBConfig.getCurrent().getConnection();
		if (conn != null) {
			try {
				ArrayList<String> tables = DBConfig.getCurrent().getTables();
				if (!tables.contains("COLUMNS")) {
					createColumnsTable();
				}
				//select columns related to the table id
				PreparedStatement stmt = conn.prepareStatement(""
						+ "SELECT C.ID, C.CHAR_LENGTH, C.DESC, C.AUTO_INCREMENTED, C.PRIMARY_KEY, C.NAME, C.TYPE, C.NOT_NULL "
						+ "FROM COLUMNS C "
						+ "JOIN COL_TABLE_XREF CTX ON C.ID = CTX.COL_ID "
						+ "WHERE CTX.TABLE_ID = ?");
				stmt.setString(1, tblid);
				ResultSet set = stmt.executeQuery();
				ArrayList<String[][]> results = new ArrayList<>();
				while (set.next()) {
					results.add(new String[][]{
						{"ID", set.getString("ID")},
						{"NAME", set.getString("NAME")},
						{"DESC", set.getString("DESC")},
						{"CHAR_LENGTH", set.getString("CHAR_LENGTH")},
						{"TYPE", set.getString("TYPE")},
						{"AUTO_INCREMENTED", set.getString("AUTO_INCREMENTED")},
						{"PRIMARY_KEY", set.getString("PRIMARY_KEY")},
						{"NOT_NULL", set.getString("NOT_NULL")}
					});
				}
				set.close();
				stmt.close();
				return results.toArray(new String[results.size()][][]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
