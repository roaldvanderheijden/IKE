import static org.junit.Assert.*;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

/**
 * Class to test LocalDataBase
 * (TDD-style, at least it was an attempt ^_^)
 * 
 * @author Roald van der Heijden
 * @date 12th December 2011
 */
public class ALocalDatabaseShould {
	
	/**
	 * The <code>LocalDatabase</code> we are using for our tests
	 */
	private LocalDatabase db;
	
	@Before
	public void init() {
		db = new LocalDatabase("testname");	
	}
	
	@Test
	public void haveANameSet() {
		assertEquals("testname", db.getName());
	}

	@Test
	public void loadSQLiteDriverToStartUpTheDatabase() {
		db.loadDriver();
		assertEquals("org.sqlite.JDBC", db.getDriverName());
	}
	
	@Test
	public void startAConnectionToTheDatabaseBeforeExecutingCommands() {
		db.loadDriver(); // driver has to be loaded before getting connection!!! even with @test loaddriver..
		db.openConnection();
		if (db.connectionOpened()==false) {
			fail("Connection wasn't opened");
		}
	}
	
	@Test
	public void haveTheAbilityToAddOrContainPersons() {
		db.loadDriver();
		db.openConnection();
		db.createTablePersons();
		assertEquals(db.hasTable("Persons"), true);	
	}
	
	@Test
	public void haveTheAbilityToAddOrContainRecordings() {
		db.loadDriver();
		db.openConnection();
		db.createTablePersons();
		assertEquals(db.hasTable("Recordings"), true);	
	}
	
	@Test
	public void haveTheAbilityToAddOrContainListenings() {
		db.loadDriver();
		db.openConnection();
		db.createTablePersons();
		assertEquals(db.hasTable("Listenings"), true);	
	}
	
}
				

	/*
	 * Problems here:
	 * 
	 * Writing anything to SELECT, INSERT, UPDATE OR DELETE
	 * requires a @#($*@#$(@# friggin amount of work... I am simply going to
	 * 
	 * 1. write three simple Strings to CREATE a table,
	 * 2. have them execute
	 * 3. run an assert to show name (so table) has been created
	 * 
	 * -> premature optimization is indeed the root of all evil.
	 * 
	 * Perhaps later junit-tests, that aren't too much boilerplate/lq, but not now.
	 */

