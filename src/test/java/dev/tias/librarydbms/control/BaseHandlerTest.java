package dev.tias.librarydbms.control;

import dev.tias.librarydbms.service.db.DataAccessManager;
import dev.tias.librarydbms.service.db.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package control
 * @contact matfir-1@student.ltu.se
 * @date 5/5/2023
 * <p>
 * This class contains the methods and fields that are shared among all HandlerTest classes, in order to better adhere
 * to the DRY principle.
 */
public abstract class BaseHandlerTest
{
    //TODO-PRIO OVERHAUL THIS AND ALL SUB CLASSES
    // database setup should only be performed once, BeforeAll
    // AfterAll should completely delete the test database
    // add custom setup methods as needed

    protected static final String testDatabaseName = "test_database";
    protected Connection connection = null;

    @BeforeAll
    protected static void setup()
    {

    }

    /**
     * Always close the connection to the database after use.
     */
    @AfterAll
    static protected void tearDown()
    {
        DataAccessManager.closeDatabaseConnection();
    }

    /**
     * Create the connection to the database, set DatabaseHandlers connection, and reset the database before each test.
     */
    @BeforeEach
    protected void reset()
    {
        System.out.println("\nSetting up and resetting database...");
        try
        {
            setupConnectionAndTables();
            setupTestData();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        System.out.println("Setup finished.");
    }

    protected void setupConnectionAndTables()
    throws SQLException, ClassNotFoundException
    {
        connection = DatabaseConnection.setup();
        DataAccessManager.setConnection(connection);
        DataAccessManager.setVerbose(true); //For testing we want DBHandler to be Verboten
        DataAccessManager.executePreparedUpdate("drop database if exists " + testDatabaseName, null);
        DataAccessManager.executePreparedUpdate("create database " + testDatabaseName, null);
        DataAccessManager.executePreparedUpdate("use " + testDatabaseName, null);
        DataAccessManager.setVerbose(false);
        DataAccessManager.executeSQLCommandsFromFile("src/main/resources/sql/create_tables.sql");
    }

    protected void setupTestData()
    {
        //customTestDataSetup();
        DataAccessManager.executeSQLCommandsFromFile("src/main/resources/sql/data/test_data.sql");
        DataAccessManager.setVerbose(true);
    }
}