package dev.tias.librarydbms.control.item;

import dev.tias.librarydbms.control.BaseHandlerTest;
import dev.tias.librarydbms.control.ItemHandler;
import dev.tias.librarydbms.service.db.DataAccessManager;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 5/31/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Tests the setup method in the ItemHandler class.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemHandlerSetupTest extends BaseHandlerTest
{
    //TODO-PRIO test against test_data file

    @Override
    protected void setupTestData()
    {

    }

    /**
     * Test case for the setup method with an empty database.
     * This test checks if the setup method correctly initializes the ItemHandler's state.
     */
    @Test
    @Order(1)
    void testSetup_EmptyDatabase()
    {
        System.out.print("\n1: Testing setup method with an empty database...");

        // Call the setup method
        ItemHandler.setup();

        // Verify that the storedTitles, availableTitles maps, and registeredBarcodes list are empty
        assertEquals(0, ItemHandler.getStoredTitles().size(),
                "storedTitles map should be empty after setup with an empty database");
        assertEquals(0, ItemHandler.getAvailableTitles().size(),
                "availableTitles map should be empty after setup with an empty database");
        assertEquals(0, ItemHandler.getRegisteredBarcodes().size(),
                "registeredBarcodes list should be empty after setup with an empty database");

        System.out.print(" Test Finished.");
    }

    /**
     * Test case for the setup method with some items in the database.
     * This test verifies the behavior of the setup method when there are existing items in the database.
     */
    @Test
    @Order(2)
    void testSetup_SomeItemsInDatabase()
    {
        System.out.print("\n2: Testing setup method with some items in the database...");

        //Setup the test data and clear the items, film and literature tables
        setupTestData();
        String[] tablesToDelete = {"films", "literature", "items"};
        for (String table : tablesToDelete)
        {
            String deleteCommand = "DELETE FROM " + table;
            DataAccessManager.executePreparedUpdate(deleteCommand, null);
        }


        //Check that the maps and list are empty
        assertEquals(0, ItemHandler.getStoredTitles().size());
        assertEquals(0, ItemHandler.getAvailableTitles().size());
        assertEquals(0, ItemHandler.getRegisteredBarcodes().size());

        //Set some variables
        String title1 = "title1";
        String title2 = "title2";
        String title3 = "title3";
        String barcode1 = "1234567890";
        String barcode2 = "1234567891";
        String barcode3 = "1234567892";
        String barcode4 = "1234567893";

        //Set up query and parameters
        String query = "INSERT INTO items (title, itemType, barcode, authorID, classificationID, allowedRentalDays, " +
                "available, deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String[] params1 = {title1, "FILM", barcode1, "1", "1", "7", "1", "0"};
        String[] params2 = {title1, "FILM", barcode2, "1", "1", "7", "0", "0"};
        String[] params3 = {title2, "FILM", barcode3, "1", "1", "7", "1", "0"};
        String[] params4 = {title3, "FILM", barcode4, "1", "1", "7", "1", "0"};

        //Insert some items into the database without using the createNew methods,
        //because they automatically increment maps and list
        DataAccessManager.executePreparedUpdate(query, params1);
        DataAccessManager.executePreparedUpdate(query, params2);
        DataAccessManager.executePreparedUpdate(query, params3);
        DataAccessManager.executePreparedUpdate(query, params4);

        //Call the setup method
        ItemHandler.setup();

        //Verify amount of contents
        assertEquals(3, ItemHandler.getStoredTitles().size());
        assertEquals(3, ItemHandler.getAvailableTitles().size());
        assertEquals(1, ItemHandler.getAvailableTitles().get(title1));
        assertEquals(4, ItemHandler.getRegisteredBarcodes().size());

        //Verify content of maps
        Map<String, Integer> storedTitles = ItemHandler.getStoredTitles();
        Iterator<String> iterator = storedTitles.keySet().iterator();
        String firstKey = iterator.next();
        String secondKey = iterator.next();
        String thirdKey = iterator.next();
        assertEquals(title1, firstKey);
        assertEquals(title2, secondKey);
        assertEquals(title3, thirdKey);

        //Verify barcodes
        assertEquals(barcode1, ItemHandler.getRegisteredBarcodes().get(0));
        assertEquals(barcode2, ItemHandler.getRegisteredBarcodes().get(1));
        assertEquals(barcode3, ItemHandler.getRegisteredBarcodes().get(2));
        assertEquals(barcode4, ItemHandler.getRegisteredBarcodes().get(3));

        System.out.println("Test Finished.");
    }
}