package dev.tias.librarydbms.service.db;

import dev.tias.librarydbms.LibraryManager;
import dev.tias.librarydbms.control.entities.UserHandler;
import dev.tias.librarydbms.service.exceptions.ExceptionManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package edu.groupeighteen.librarydbms.control
 * @contact matfir-1@student.ltu.se
 * @date 4/5/2023
 * <p>
 * This class is responsible for handling a database by performing SQL Queries and SQL Commands on the database.
 * To make the code more readable it was decided to let this class retrieve and store a Connection from the
 * DatabaseConnection class.
 * <p>
 * This class is only responsible for general operations. Specific operations are delegated to specific Handler classes,
 * such as {@link UserHandler}.   //TODO-comment finish
 */
public class DataAccessManager
{
    //The DataAccessManager needs a connection to perform commands and queries.
    private static Connection connection;
    //Print commands being run, default = not
    private static boolean verbose = false;

    /**
     * Sets up the DatabaseConnection, then checks if the database exists. If not, calls createDatabase to
     * create it.
     */
    public static void setup(boolean verbose)
    {
        try
        {
            //Set verbosity
            DataAccessManager.verbose = verbose;

            //Connect to database
            connection = DatabaseConnection.setup();

            executePreparedUpdate("drop database if exists " + LibraryManager.databaseName, null);
            createDatabase(LibraryManager.databaseName);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            ExceptionManager.HandleFatalException(e, "Failed to setup databse due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }

        /*
        if (!databaseExists(LibraryManager.databaseName)) {
            createDatabase(LibraryManager.databaseName);
        } else executeCommand("use " + LibraryManager.databaseName);
        */

    }

    /**
     * Checks whether a given database already exists on the server.
     *
     * @param databaseName the name of the database in question.
     * @return true if the database exists, otherwise false.
     */
    public static boolean databaseExists(String databaseName)
    {
        String query = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";
        String[] params = {databaseName.toLowerCase()};

        try
        {
            QueryResult queryResult = executePreparedQuery(query, params);
            ResultSet resultSet = queryResult.getResultSet();
            boolean exists = resultSet.next();
            queryResult.close();
            return exists;
        }
        catch (SQLException e)
        {
            ExceptionManager.HandleFatalException(e, "Failed to verify that database exists due to " +
                    e.getClass().getName() + ": " + e.getMessage());
            return false; //Needed for compilation
        }
    }

    /**
     * Creates a new database with a given name and fills it with tables and data.
     *
     * @param databaseName the name of the database.
     */
    public static void createDatabase(String databaseName)
    {
        //Create DB
        executePreparedUpdate("create database " + databaseName, null);
        //Use DB
        executePreparedUpdate("use " + databaseName, null);
        //Fill DB with tables and data
        executeSQLCommandsFromFile("src/main/resources/sql/create_tables.sql");
        executeSQLCommandsFromFile("src/main/resources/sql/data/test_data.sql");
    }

    //TODO-TEST

    /**
     * Executes an SQL update command such as INSERT, UPDATE, DELETE, or CREATE TABLE
     * using a prepared statement. These commands modify data and return the number of
     * rows affected, which this method also returns.
     *
     * @param command  The SQL update command to execute.
     * @param params   An array of parameter values to be bound to the SQL command.
     * @param settings Optional PreparedStatement settings, such as Statement.RETURN_GENERATED_KEYS.
     * @return The number of rows affected by the update.
     */
    public static int executePreparedUpdate(String command, String[] params, int... settings)
    {
        if (verbose)
        {
            System.out.println("\nExecuting Prepared Update: ");
            SQLFormatter.printFormattedSQL(command);
        }

        try
        {
            PreparedStatement stmt;
            if (settings.length > 0)
            {
                stmt = connection.prepareStatement(command, settings);
            }
            else
            {
                stmt = connection.prepareStatement(command);
            }

            //Bind the provided params to the SQL statement
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    stmt.setString(i + 1, params[i]);
                }
            }

            //Execute the update and return the number of affected rows
            return stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            ExceptionManager.HandleFatalException(e, "Failed to execute prepared update due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }

        //Won't reach, but needed to compile
        return -1;
    }

    //TODO-test

    /**
     * Executes an SQL query command such as SELECT using a prepared statement.
     * These commands retrieve data and return a ResultSet, which is encapsulated
     * in the QueryResult object returned by this method.
     *
     * @param query    The SQL query command to execute.
     * @param params   An array of parameter values to be bound to the SQL command.
     * @param settings Optional PreparedStatement settings. These are less commonly used than for UPDATE/INSERT
     *                 operations.
     * @return A QueryResult object that encapsulates the ResultSet and the PreparedStatement.
     * The ResultSet can be iterated to retrieve the data, and the PreparedStatement should
     * be closed when finished.
     */
    public static QueryResult executePreparedQuery(String query, String[] params, int... settings)
    {
        if (verbose)
        {
            System.out.println("\nExecuting prepared query:");
            SQLFormatter.printFormattedSQL(query);
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            //Prepare the statement with the given settings
            preparedStatement = connection.prepareStatement(query, settings);
            //Set the parameters if params is not null
            if (params != null)
            {
                for (int i = 0; i < params.length; i++)
                {
                    preparedStatement.setString(i + 1, params[i]);
                }
            }
            //Execute the query
            preparedStatement.execute();
            //Get the result set, if available
            resultSet = preparedStatement.getResultSet();
        }
        catch (SQLException e)
        {
            ExceptionManager.HandleFatalException(e, "Failed to execute prepared query due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }
        return new QueryResult(resultSet, preparedStatement);
    }

    /**
     * A simple method which reads the contents of a file, and executes any SQL commands found in that file.
     *
     * @param filePath the path of the file
     */
    public static void executeSQLCommandsFromFile(String filePath)
    {
        //No point attempting to execute from an empty file path
        if (filePath == null || filePath.isEmpty())
        {
            System.out.println("ERROR: executeSqlCommandsFromFile: No filepath.");
            return;
        }

        if (verbose) System.out.println("\nExecuting commands from file: " + filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            StringBuilder commandBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null)
            {
                //Remove inline comments
                if (line.contains("--"))
                {
                    line = line.substring(0, line.indexOf("--"));
                }

                //Skip lines that are now empty (were only a comment)
                if (line.trim().isEmpty())
                {
                    continue;
                }

                //Append the line to the command string, adding a space in between
                commandBuilder.append(line).append(" ");

                //Check if the line ends with a semicolon, signifying the end of the command
                if (line.endsWith(";"))
                {
                    String command = commandBuilder.toString().trim();

                    if (!command.isEmpty())
                    {
                        executePreparedUpdate(command, null);
                    }

                    //Reset the command builder for the next command
                    commandBuilder = new StringBuilder();
                }
            }
        }
        catch (FileNotFoundException e)
        {
            ExceptionManager.HandleFatalException(e, "Couldn't find file at path " + filePath);
        }
        catch (IOException e)
        {
            ExceptionManager.HandleFatalException(e, "Couldn't read file at path " + filePath);
        }
    }

    public static void checkConnection()
    {
        if (connection == null)
        {
            setup(false);
        }
    }

    /**
     * Since any other classes should only do Database-related things through this Handler class,
     * we need to add a close method that calls closeConnection in the DatabaseConnection class.
     */
    public static void closeDatabaseConnection()
    {
        DatabaseConnection.closeConnection();
    }

    public static Connection getConnection()
    {
        return connection;
    }

    public static void setConnection(Connection connection)
    {
        DataAccessManager.connection = connection;
    }

    public static boolean isVerbose()
    {
        return verbose;
    }

    public static void setVerbose(boolean verbose)
    {
        DataAccessManager.verbose = verbose;
    }
}