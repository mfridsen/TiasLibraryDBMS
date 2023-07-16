package dev.tias.librarydbms.control.item;

import dev.tias.librarydbms.control.ItemHandler;
import dev.tias.librarydbms.model.Classification;
import dev.tias.librarydbms.model.Item;
import dev.tias.librarydbms.service.db.DataAccessManager;
import dev.tias.librarydbms.service.db.DatabaseConnection;
import dev.tias.librarydbms.service.exceptions.custom.InvalidNameException;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 6/2/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Unit Test for the GetItemsByClassification class.
 * <p>
 * Brought to you by copious amounts of nicotine.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetItemsByClassificationTest
{
    protected static final String testDatabaseName = "test_database";
    //We need all this stuff, copied from BaseHandlerTest but adapted to be static
    protected static Connection connection = null;

    //BeforeEach
    static void setupConnectionAndTables()
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

    static void setupTestData()
    {
        DataAccessManager.executeSQLCommandsFromFile("src/main/resources/sql/data/test_data.sql");
        DataAccessManager.setVerbose(true);
    }

    @BeforeAll
    static void setUp()
    {
        System.out.print("\nSetting up test data...");

        try
        {
            setupConnectionAndTables();
            setupTestData();
            ItemHandler.setup(); //Otherwise barcodes are stored from previous tests
            // Create new Film and Literature objects
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        System.out.print("\nSETUP FINISHED.");
    }

    @AfterAll
    static void tearDown()
    {
        System.out.print("\nCleaning up test data...");

        try
        {
            // Drop the test database
            DataAccessManager.executePreparedUpdate("DROP DATABASE IF EXISTS " + testDatabaseName, null);

            // Close the database connection
            if (connection != null && !connection.isClosed())
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            System.err.println("An error occurred during cleanup: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.print("\nCLEANUP FINISHED.");
    }

    /**
     * Test for getItemsByClassification method with an empty classificationName.
     * It's expected to throw InvalidNameException.
     */
    @Test
    @Order(1)
    void testGetItemsByClassification_EmptyClassificationName()
    {
        System.out.print("\n1: Testing getItemsByClassification method with an empty classificationName...");

        assertThrows(InvalidNameException.class, () -> ItemHandler.getItemsByClassification(""));

        System.out.print(" Test Finished.");
    }

    /**
     * Test for getItemsByClassification method with null classificationName.
     * It's expected to throw InvalidNameException.
     */
    @Test
    @Order(2)
    void testGetItemsByClassification_NullClassificationName()
    {
        System.out.print("\n2: Testing getItemsByClassification method with null classificationName...");

        assertThrows(InvalidNameException.class, () -> ItemHandler.getItemsByClassification(null));

        System.out.print(" Test Finished.");
    }

    /**
     * Test for getItemsByClassification method with classificationName that's too long.
     * It's expected to throw InvalidNameException.
     */
    @Test
    @Order(3)
    void testGetItemsByClassification_ClassificationNameTooLong()
    {
        System.out.print("\n3: Testing getItemsByClassification method with classificationName that's too long...");

        String longName = "a".repeat(Classification.CLASSIFICATION_NAME_LENGTH + 1);
        assertThrows(InvalidNameException.class, () -> ItemHandler.getItemsByClassification(longName));

        System.out.print(" Test Finished.");
    }

    /**
     * Test for getItemsByClassification method with a classificationName that does not exist.
     * It's expected to return an empty list.
     */
    @Test
    @Order(4)
    void testGetItemsByClassification_ClassificationNameDoesNotExist()
    {
        System.out.print(
                "\n4: Testing getItemsByClassification method with a classificationName that does not exist...");

        try
        {
            List<Item> items = ItemHandler.getItemsByClassification("NonexistentClassification");
            assertTrue(items.isEmpty());
        }
        catch (InvalidNameException e)
        {
            e.printStackTrace();
            fail("Valid operations should not throw exceptions.");
        }

        System.out.print(" Test Finished.");
    }

    /**
     * Test for getItemsByClassification method with classificationName that has no items.
     * A new classification with no associated items is created for this test.
     * It's expected to return an empty list.
     */
    @Test
    @Order(5)
    void testGetItemsByClassification_NoItemsInClassification()
    {
        System.out.print("\n5: Testing getItemsByClassification method with classificationName that has no items...");

        try
        {
            DataAccessManager.executePreparedUpdate(
                    "INSERT INTO classifications (classificationName, deleted) VALUES ('EmptyClassification', 0)",
                    null);
            List<Item> items = ItemHandler.getItemsByClassification("EmptyClassification");
            assertTrue(items.isEmpty());
        }
        catch (InvalidNameException e)
        {
            e.printStackTrace();
            fail("Valid operations should not throw exceptions.");
        }

        System.out.print(" Test Finished.");
    }

    /**
     * Test for getItemsByClassification method with classificationName that has a single item.
     * It's expected to return a list with one item.
     */
    @Test
    @Order(6)
    void testGetItemsByClassification_SingleItemExists()
    {
        System.out.print(
                "\n6: Testing getItemsByClassification method with classificationName that has a single item...");

        try
        {
            List<Item> items = ItemHandler.getItemsByClassification("Physics");
            assertEquals(1, items.size());
        }
        catch (InvalidNameException e)
        {
            e.printStackTrace();
            fail("Valid operations should not throw exceptions.");
        }

        System.out.print(" Test Finished.");
    }

    /**
     * Test for getItemsByClassification method with classificationName that has multiple items.
     * It's expected to return a list with multiple items.
     */
    @Test
    @Order(7)
    void testGetItemsByClassification_MultipleItemsExist()
    {
        System.out.print(
                "\n7: Testing getItemsByClassification method with classificationName that has multiple items...");

        try
        {
            List<Item> items = ItemHandler.getItemsByClassification("Fantasy");
            assertTrue(items.size() > 1);
        }
        catch (InvalidNameException e)
        {
            e.printStackTrace();
            fail("Valid operations should not throw exceptions.");
        }

        System.out.print(" Test Finished.");
    }
}