package dev.tias.librarydbms.control.item;

import dev.tias.librarydbms.control.ItemHandler;
import dev.tias.librarydbms.model.Item;
import dev.tias.librarydbms.service.db.DataAccessManager;
import dev.tias.librarydbms.service.db.DatabaseConnection;
import dev.tias.librarydbms.service.exceptions.custom.item.InvalidTitleException;
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
 * Unit Test for the GetItemsByTitle class.
 * <p>
 * Brought to you by copious amounts of nicotine.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetItemsByTitleTest
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

        //Remove test data that could disrupt tests
        String[] tablesToDelete = {"films", "literature", "items"};
        for (String table : tablesToDelete)
        {
            String deleteCommand = "DELETE FROM " + table;
            DataAccessManager.executePreparedUpdate(deleteCommand, null);
        }

        // Insert 3 Films with the same title
        for (int i = 0; i < 3; i++)
        {
            try
            {
                ItemHandler.createNewFilm(
                        "Shared Title",
                        1, // authorID: replace with valid ID
                        1, // classificationID: replace with valid ID
                        "SharedFilmBarcode" + i, // unique barcode
                        12 // age rating
                );
            }
            catch (Exception e)
            {
                e.printStackTrace();
                fail("Setup failed due to exception: " + e.getMessage());
            }
        }

        // Insert 2 Films with unique titles
        for (int i = 0; i < 2; i++)
        {
            try
            {
                ItemHandler.createNewFilm(
                        "Unique Film Title " + i,
                        1, // authorID: replace with valid ID
                        1, // classificationID: replace with valid ID
                        "UniqueFilmBarcode" + i, // unique barcode
                        12 // age rating
                );
            }
            catch (Exception e)
            {
                e.printStackTrace();
                fail("Setup failed due to exception: " + e.getMessage());
            }
        }

        // Insert 3 Literature with the same title
        for (int i = 0; i < 3; i++)
        {
            try
            {
                ItemHandler.createNewLiterature(
                        "Shared Title",
                        Item.ItemType.REFERENCE_LITERATURE, // item type
                        1, // authorID: replace with valid ID
                        1, // classificationID: replace with valid ID
                        "SharedLiteratureBarcode" + i, // unique barcode
                        "SharedISBN" + i // unique ISBN
                );
            }
            catch (Exception e)
            {
                e.printStackTrace();
                fail("Setup failed due to exception: " + e.getMessage());
            }
        }

        // Insert 2 Literature with unique titles
        for (int i = 0; i < 2; i++)
        {
            try
            {
                ItemHandler.createNewLiterature(
                        "Unique Literature Title " + i,
                        Item.ItemType.REFERENCE_LITERATURE, // item type
                        1, // authorID: replace with valid ID
                        1, // classificationID: replace with valid ID
                        "UniqueLiteratureBarcode" + i, // unique barcode
                        "UniqueISBN" + i // unique ISBN
                );
            }
            catch (Exception e)
            {
                e.printStackTrace();
                fail("Setup failed due to exception: " + e.getMessage());
            }
        }
    }

    @BeforeAll
    static void setUp()
    {
        System.out.println("\nSetting up test data...");

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

        System.out.println("\nSETUP FINISHED.");
    }

    @AfterAll
    static void tearDown()
    {
        System.out.println("\nCleaning up test data...");

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

        System.out.println("\nCLEANUP FINISHED.");
    }

    @Test
    @Order(1)
    void testGetItemsByTitle_NullTitle()
    {
        System.out.println("\n1: Testing getItemsByTitle method with null title...");

        assertThrows(InvalidTitleException.class, () -> ItemHandler.getItemsByTitle(null),
                "getItemsByTitle should throw an InvalidTitleException when the title is null");

        System.out.println("Test Finished.");
    }

    @Test
    @Order(2)
    void testGetItemsByTitle_EmptyTitle()
    {
        System.out.println("\n2: Testing getItemsByTitle method with empty title...");

        assertThrows(InvalidTitleException.class, () -> ItemHandler.getItemsByTitle(""),
                "getItemsByTitle should throw an InvalidTitleException when the title is empty");

        System.out.println("Test Finished.");
    }

    @Test
    @Order(3)
    void testGetItemsByTitle_NonexistentItem()
    {
        System.out.println("\n3: Testing getItemsByTitle method with a title that doesn't exist...");

        try
        {
            List<Item> items = ItemHandler.getItemsByTitle("Nonexistent Title");
            assertTrue(items.isEmpty(),
                    "getItemsByTitle should return an empty list when no item exists with the given title");
        }
        catch (InvalidTitleException e)
        {
            e.printStackTrace();
            fail("Valid operations should not throw exceptions.");
        }

        System.out.println("Test Finished.");
    }

    @Test
    @Order(4)
    void testGetItemsByTitle_OneItemExists()
    {
        System.out.println("\n4: Testing getItemsByTitle method when one item exists with the title...");

        try
        {
            String title = "Unique Film Title 0";
            List<Item> items = ItemHandler.getItemsByTitle(title);
            assertEquals(1, items.size(),
                    "getItemsByTitle should return a list with one item when one item exists with the given title");
        }
        catch (InvalidTitleException e)
        {
            e.printStackTrace();
            fail("Valid operations should not throw exceptions.");
        }

        System.out.println("Test Finished.");
    }

    @Test
    @Order(5)
    void testGetItemsByTitle_MultipleItemsExist()
    {
        System.out.println("\n5: Testing getItemsByTitle method when multiple items exist with the title...");

        try
        {
            String title = "Shared Title";
            List<Item> items = ItemHandler.getItemsByTitle(title);
            assertEquals(6, items.size(),
                    "getItemsByTitle should return a list with multiple items when more than one item exists with the given title");
        }
        catch (InvalidTitleException e)
        {
            e.printStackTrace();
            fail("Valid operations should not throw exceptions.");
        }

        System.out.println("Test Finished.");
    }

}