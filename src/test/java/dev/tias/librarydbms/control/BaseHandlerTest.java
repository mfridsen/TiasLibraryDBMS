package dev.tias.librarydbms.control;

import dev.tias.librarydbms.service.db.DataAccessManager;
import dev.tias.librarydbms.service.db.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseHandlerTest
{
    //TODO-PRIO OVERHAUL THIS AND ALL SUB CLASSES
    // database setup should only be performed once, BeforeAll
    // AfterAll should completely delete the test database
    // add custom setup methods as needed

    protected static final String testDatabaseName = "test_database";
    protected static Connection connection = null;

    @BeforeAll
    protected void setup()
    {
        System.out.println("\nSetting up test environment...");

        setupConnection(); //All Handler tests will need to have a connection established to the MySQL server
        setupDatabase(); //All Handler tests will need a test database to operate on
        setupTables(); //Override to customize
        setupTestData();

        System.out.println("\nTest environment setup finished.");
    }

    protected void setupConnection()
    {
        try
        {
            connection = DatabaseConnection.setup();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        DataAccessManager.setConnection(connection);
        DataAccessManager.setVerbose(true); //For testing we want DBHandler to be Verboten
    }

    protected void setupDatabase()
    {
        DataAccessManager.executePreparedUpdate("drop database if exists " + testDatabaseName, null);
        DataAccessManager.executePreparedUpdate("create database " + testDatabaseName, null);
        DataAccessManager.executePreparedUpdate("use " + testDatabaseName, null);
    }

    protected void setupTables()
    {
        DataAccessManager.executeSQLCommandsFromFile("src/main/resources/sql/create_tables.sql");
    }

    protected abstract void setupTestData();

    /**
     * Always delete the test database and close the connection to the server after use.
     */
    @AfterAll
    protected static void tearDown()
    {
        try
        {
            //Drop the test database
            DataAccessManager.executePreparedUpdate("DROP DATABASE IF EXISTS " + testDatabaseName, null);

            //Close the database connection
            if (connection != null && !connection.isClosed())
            {
                DataAccessManager.closeDatabaseConnection();
            }
        }
        catch (SQLException e)
        {
            System.err.println("An error occurred during cleanup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}