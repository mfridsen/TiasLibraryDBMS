package dev.tias.librarydbms.control.classification;

import dev.tias.librarydbms.control.BaseHandlerTest;
import dev.tias.librarydbms.control.ClassificationHandler;
import dev.tias.librarydbms.service.db.DataAccessManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Johan Lund
 * @project LibraryDBMS
 * @date 2023-05-31
 */
public class ClassificationNamesTest extends BaseHandlerTest
{
    @Override
    protected void setupTestData()
    {

    }

    @Test
    @Order(1)
    public void testSyncClassificationNames()
    {
        ClassificationHandler.setup();
        Assertions.assertEquals(0, ClassificationHandler.getStoredClassificationNames().size());
    }

    @Test
    @Order(2)
    public void testPrintClassificationNames()
    {
        // Redirect System.out to a mock PrintStream
        var mockPrintStream = mock(java.io.PrintStream.class);
        var originalOut = System.out;
        System.setOut(mockPrintStream);

        ClassificationHandler.printClassificationNames();

        // Verify that the expected output is printed
        verify(mockPrintStream).println("\nClassificationNames");

        // Restore System.out
        System.setOut(originalOut);
    }

    @Test
    @Order(3)
    void testSetup_WithSomeClassificationsInDatabase()
    {
        System.out.print("\n8: Testing setup method with some classifications in the database...");

        // Check that storedClassificationNames is empty
        Assertions.assertEquals(0, ClassificationHandler.getStoredClassificationNames().size());


        // Insert some classification into the database without using createNewClassification (which automatically increments storedClassi)
        String query = "INSERT INTO classifications (classificationName, description, deleted" + "VALUES (?, ?, ?)";
        String[] params1 = {"Physics", "Scientific literature on the topic of physics", "0"};
        String[] params2 = {"Chemistry", "Scientific literature on the topic of chemistry", "0"};
        String[] params3 = {"Horror", "The best genre", "0"};
        DataAccessManager.executePreparedQuery(query, params1);
        DataAccessManager.executePreparedQuery(query, params2);
        DataAccessManager.executePreparedQuery(query, params3);


        // Call the setup method
        ClassificationHandler.setup();

        // Verify that there are the excepted amount of classifications in stored classification.
        Assertions.assertEquals(3, ClassificationHandler.getStoredClassificationNames().size());

        System.out.print(" Test Finished.");

    }
}
