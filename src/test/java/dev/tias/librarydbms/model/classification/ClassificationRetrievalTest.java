package dev.tias.librarydbms.model.classification;

import dev.tias.librarydbms.model.Classification;
import dev.tias.librarydbms.service.exceptions.custom.ConstructionException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 5/29/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Test class for Classification retrieval constructor.
 * This class aims to test the behavior of the Classification retrieval constructor under various circumstances,
 * including valid and invalid inputs.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClassificationRetrievalTest
{
    /**
     * Tests the Classification retrieval constructor with valid data.
     * This method creates a Classification with valid data and verifies that all fields are set correctly.
     */
    @Test
    @Order(1)
    void testRetrievalConstructor_ValidData()
    {
        System.out.print("\n1: Testing Classification retrieval constructor with valid data...");

        try
        {
            int id = 1;
            String name = "Fiction";
            String description = "A type of literary genre";
            Classification classification = new Classification(id, name, description, false);
            assertEquals(id, classification.getClassificationID(), "Classification ID should match the set ID");
            assertEquals(name, classification.getClassificationName(), "Classification name should match the set name");
            assertEquals(description, classification.getDescription(),
                    "Classification description should match the set description");
            assertFalse(classification.isDeleted(), "isDeleted should be false for this retrieved Classification");
        }
        catch (ConstructionException e)
        {
            fail("Valid operations should not throw exceptions. " + e.getMessage());
            e.printStackTrace();
        }

        System.out.print(" Test Finished.");
    }

    /**
     * Tests the Classification retrieval constructor with an invalid ID.
     * This method tries to create a Classification with an invalid ID and checks that the appropriate exception is thrown.
     */
    @Test
    @Order(2)
    void testRetrievalConstructor_InvalidID()
    {
        System.out.print("\n2: Testing Classification retrieval constructor with an invalid ID...");

        int id = -1;
        String name = "Fiction";
        String description = "A type of literary genre";

        assertThrows(ConstructionException.class,
                () -> new Classification(id, name, description, false),
                "Expected ConstructionException for invalid ID");

        System.out.print(" Test Finished.");
    }

    /**
     * Tests the Classification retrieval constructor with a null name.
     * This method tries to create a Classification with a null name and checks that the appropriate exception is thrown.
     */
    @Test
    @Order(3)
    void testRetrievalConstructor_NullName()
    {
        System.out.print("\n3: Testing Classification retrieval constructor with a null name...");

        int id = 1;
        String name = null;
        String description = "A type of literary genre";

        assertThrows(ConstructionException.class,
                () -> new Classification(id, name, description, false),
                "Expected ConstructionException for null name");

        System.out.print(" Test Finished.");
    }

    /**
     * Tests the Classification retrieval constructor with a name that exceeds the maximum length.
     * This method tries to create a Classification with a name that is too long and checks that the appropriate exception is thrown.
     */
    @Test
    @Order(4)
    void testRetrievalConstructor_ExceedingNameLength()
    {
        System.out.print(
                "\n4: Testing Classification retrieval constructor with a name that exceeds the maximum length...");

        int id = 1;
        String name = "a".repeat(Classification.CLASSIFICATION_NAME_LENGTH + 1);
        String description = "A type of literary genre";

        assertThrows(ConstructionException.class,
                () -> new Classification(id, name, description, false),
                "Expected ConstructionException for name that exceeds the maximum length");

        System.out.print(" Test Finished.");
    }
}