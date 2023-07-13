package dev.tias.librarydbms.control;

import dev.tias.librarydbms.service.db.DataAccessManager;
import dev.tias.librarydbms.service.db.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
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

    protected static final String testDatabaseName = "test_database";
    protected Connection connection = null;

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
    protected void setupAndReset()
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
        DataAccessManager.executeSQLCommandsFromFile("src/main/resources/sql/data/test_data.sql");
        DataAccessManager.setVerbose(true);
    }
}