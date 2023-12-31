package dev.tias.librarydbms.service.db;

import dev.tias.librarydbms.LibraryManager;
import dev.tias.librarydbms.control.BaseHandlerTest;
import dev.tias.librarydbms.control.UserHandler;
import dev.tias.librarydbms.model.User;
import dev.tias.librarydbms.service.exceptions.custom.InvalidIDException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 5/5/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Unit Test for the DataAccessManager class.
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataAccessManagerTest extends BaseHandlerTest
{
    //TODO-future make all tests more verbose
    //TODO-future javadoc tests properly

    @Override
    protected void setupTestData()
    {

    }

    @Test
    @Order(1)
    void testExecuteCommand()
    {
        System.out.print("\n1: Testing executeSingleSQLCommand method...");
        //1. Create a temporary table in the test database
        String createTempTable = "CREATE TABLE temp_table (id INT PRIMARY KEY, name VARCHAR(255));";
        DataAccessManager.executePreparedUpdate(createTempTable, null);

        //2. Insert some data into the temporary table
        String insertData = "INSERT INTO temp_table (id, name) VALUES (1, 'Test User');";
        DataAccessManager.executePreparedUpdate(insertData, null);

        //3. Check if the data was inserted correctly
        String queryData = "SELECT * FROM temp_table WHERE id = 1;";
        try
        {
            ResultSet resultSet = DataAccessManager.getConnection().createStatement().executeQuery(queryData);
            assertTrue(resultSet.next(), "No data found in temp_table");
            assertEquals(1, resultSet.getInt("id"), "ID value does not match");
            assertEquals("Test User", resultSet.getString("name"), "Name value does not match");
            resultSet.close();
        }
        catch (SQLException e)
        {
            fail("Failed to query data from temp_table: " + e.getMessage());
        }

        //Clean up: Drop the temporary table
        String dropTempTable = "DROP TABLE IF EXISTS temp_table;";
        DataAccessManager.executePreparedUpdate(dropTempTable, null);
        System.out.print(" Test Finished.");
    }

    /**
     * Tests the executePreparedUpdate method in the DataAccessManager class.
     * This test includes operations of creating a new table, inserting data, updating the data, and deleting the data.
     * For each operation, it validates the successful execution by checking the affected rows and the resulting state of the database.
     */
    @Test
    @Order(2)
    void testExecutePreparedUpdate()
    {
        System.out.print("\n2: Testing executePreparedUpdate method...");

        //Prepare SQL commands to create a new table, insert data, update it and delete data
        String createCommand = "CREATE TABLE test_table (id INT, value INT)";
        String insertCommand = "INSERT INTO test_table (id, value) VALUES (?, ?)";
        String updateCommand = "UPDATE test_table SET value = ? WHERE id = ?";
        String deleteCommand = "DELETE FROM test_table WHERE id = ?";

        try
        {
            //Create a new table
            DataAccessManager.executePreparedUpdate(createCommand, null);

            //Insert data into the table
            String[] insertParams = {"1", "100"};
            DataAccessManager.executePreparedUpdate(insertCommand, insertParams);

            //Update the data
            String[] updateParams = {"200", "1"};
            int affectedRows = DataAccessManager.executePreparedUpdate(updateCommand, updateParams);

            //Assert that the expected number of rows were affected
            assertEquals(1, affectedRows);

            //Check if the update worked
            String selectCommand = "SELECT value FROM test_table WHERE id = 1";
            try (QueryResult queryResult = DataAccessManager.executePreparedQuery(selectCommand, new String[]{}))
            {
                ResultSet resultSet = queryResult.getResultSet();
                if (resultSet.next())
                {
                    int value = resultSet.getInt("value");
                    assertEquals(200, value);
                }
                else
                {
                    fail("No data found in test_table.");
                }
            }

            //Delete the data
            String[] deleteParams = {"1"};
            affectedRows = DataAccessManager.executePreparedUpdate(deleteCommand, deleteParams);

            //Assert that the expected number of rows were affected
            assertEquals(1, affectedRows);

            //Check if the deletion worked
            try (QueryResult queryResult = DataAccessManager.executePreparedQuery(selectCommand, new String[]{}))
            {
                ResultSet resultSet = queryResult.getResultSet();
                if (resultSet.next())
                {
                    fail("Data was not deleted from test_table.");
                }
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Exception occurred during test: " + e.getMessage());
        }
        finally
        {
            //Clean up by dropping the test table
            String dropCommand = "DROP TABLE test_table";
            DataAccessManager.executePreparedUpdate(dropCommand, null);
        }

        System.out.print(" Test Finished.");
    }

    @Test
    @Order(3)
    void testExecuteQuery()
    {
        System.out.print("\n3: Testing executeQuery method...");
        String tableName = "test_table";
        try
        {
            //Create a new table
            String createTableQuery = "CREATE TABLE " + tableName + " (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255))";
            DataAccessManager.executePreparedUpdate(createTableQuery, null);

            //Verify the table was created
            QueryResult tableVerificationResult = DataAccessManager.executePreparedQuery("SHOW TABLES LIKE '" + tableName + "'", null);
            assertTrue(tableVerificationResult.getResultSet().next(), "Table " + tableName + " should exist");

            //Insert data into the table
            String insertDataQuery = "INSERT INTO " + tableName + " (name) VALUES ('John Doe')";
            DataAccessManager.executePreparedUpdate(insertDataQuery, null);

            //Verify data was inserted
            QueryResult dataVerificationResult = DataAccessManager.executePreparedQuery("SELECT * FROM " + tableName, null);
            ResultSet resultSet = dataVerificationResult.getResultSet();
            assertNotNull(resultSet, "Result set should not be null");
            assertTrue(resultSet.next(), "Result set should have at least one row");
            assertEquals("John Doe", resultSet.getString("name"), "Name should be 'John Doe'");
            dataVerificationResult.close();
        }
        catch (Exception e)
        {
            fail("Exception occurred during test: " + e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            //Drop the test table and close resources
            DataAccessManager.executePreparedUpdate("DROP TABLE IF EXISTS " + tableName, null);
        }
        System.out.print(" Test Finished.");
    }

    @Test
    @Order(4)
    void testExecutePreparedQuery()
    {
        System.out.print("\n4: Testing executePreparedQuery method...");
        String tableName = "test_table";
        try
        {
            //Create a new table
            String createTableQuery = "CREATE TABLE " + tableName + " (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255))";
            DataAccessManager.executePreparedUpdate(createTableQuery, null);

            //Insert data into the table using executePreparedQuery
            String insertDataQuery = "INSERT INTO " + tableName + " (name) VALUES (?)";
            String[] parameters = {"John Doe"};
            QueryResult queryResult = DataAccessManager.executePreparedQuery(insertDataQuery, parameters,
                    Statement.RETURN_GENERATED_KEYS);

            //Verify data was inserted
            int generatedId = -1;
            ResultSet generatedKeys = queryResult.getStatement().getGeneratedKeys();
            if (generatedKeys.next())
            {
                generatedId = generatedKeys.getInt(1);
            }
            assertTrue(generatedId != -1, "Generated ID should not be -1");

            //Clean up
            queryResult.close();
        }

        catch (Exception e)
        {
            e.printStackTrace();
            fail("Exception occurred during test: " + e.getMessage());
        }

        finally
        {
            //Drop the test table and close resources
            DataAccessManager.executePreparedUpdate("DROP TABLE IF EXISTS " + tableName, null);
        }
        System.out.print(" Test Finished.");
    }

    @Test
    @Order(5)
    void testExecuteSQLCommandsFromFile()
    {
        System.out.print("\n5: Testing executeSQLCommandsFromFile method...");
        //Set up the path to the test SQL file
        String testSQLFilePath = "src/test/resources/sql/test_sql_file.sql";

        //Create the test SQL file
        File testFile = createTestSQLFile(testSQLFilePath);

        //Call the method to execute the commands in the test SQL file
        assert testFile != null;
        DataAccessManager.executeSQLCommandsFromFile(testFile.getPath());

        //Verify that the expected changes have been made to the database
        //For example, if the SQL file creates a table called "test_table"
        //and inserts a row with column1='value1' and column2='value2', you can
        //run a SELECT query to check if the table exists and contains the expected data
        try
        {
            String selectQuery = "SELECT column1, column2 FROM test_table";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            //Check if resultSet has a row
            assertTrue(resultSet.next());
            //Check if the values in the resultSet match the expected values
            assertEquals("value1", resultSet.getString("column1"));
            assertEquals("value2", resultSet.getString("column2"));
            //Clean up - drop the test_table and close resources
            DataAccessManager.executePreparedUpdate("DROP TABLE test_table", null);
            resultSet.close();
            statement.close();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Failed to verify the result of executing SQL commands from file.");
        }

        testFile.delete();
        System.out.print(" Test Finished.");
    }

    /**
     * Creates a sql file for testing purposes.
     *
     * @param filePath the path to the sql file
     */
    private File createTestSQLFile(String filePath)
    {
        String fileContent = """
                -- Create test table
                CREATE TABLE test_table (column1 VARCHAR(255), column2 VARCHAR(255));
                -- Insert test data
                INSERT INTO test_table (column1, column2) VALUES ('value1', 'value2');
                """;
        try
        {
            File testSQLFile = new File(filePath);
            if (!testSQLFile.exists())
            {
                testSQLFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(testSQLFile);
            fileWriter.write(fileContent);
            fileWriter.flush();
            fileWriter.close();
            return testSQLFile;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            fail("Failed to create test SQL file.");
            return null;
        }
    }

    @Test
    @Order(6)
    void testDatabaseExistsAndCreateDatabase()
    {
        System.out.print("\n6: Testing databaseExists and createDatabase methods...");
        DataAccessManager.executePreparedUpdate("drop database if exists " + LibraryManager.databaseName, null);
        assertFalse(DataAccessManager.databaseExists(LibraryManager.databaseName));
        DataAccessManager.setVerbose(false);
        DataAccessManager.createDatabase(LibraryManager.databaseName);
        DataAccessManager.setVerbose(true);
        assertTrue(DataAccessManager.databaseExists(LibraryManager.databaseName));
        System.out.print(" Test Finished.");
    }

    @Test
    @Order(7)
    void testExecuteUpdate()
    {
        System.out.print("\n7: Testing executeUpdate...");

        //Let's assume that there is a user with ID 1 in the database.
        int userIdToUpdate = 1;
        String newUsername = "updated_username";
        String newPassword = "updated_password";

        //Prepare the SQL command and the parameters.
        String sql = "UPDATE users SET username = ?, password = ? WHERE userID = ?";
        String[] params = {newUsername, newPassword, String.valueOf(userIdToUpdate)};

        //Execute the update.
        int rowsAffected = 0;
        rowsAffected = DataAccessManager.executePreparedUpdate(sql, params);
        //Verify that the update was successful.
        assertTrue(rowsAffected > 0);

        //Now, retrieve the updated user to verify that the username and password were updated.
        User updatedUser = null;
        try
        {
            updatedUser = UserHandler.getUserByID(userIdToUpdate);
        }
        catch (InvalidIDException e)
        {
            fail("Should not throw an exception when retrieving a valid user.");
            e.printStackTrace();
        }
        assertNotNull(updatedUser);
        assertEquals(newUsername, updatedUser.getUsername());
        assertEquals(newPassword, updatedUser.getPassword());
        System.out.print(" Test Finished.");
    }
}