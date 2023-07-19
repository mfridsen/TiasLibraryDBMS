package dev.tias.librarydbms.control.classification;

import dev.tias.librarydbms.control.BaseHandlerTest;
import dev.tias.librarydbms.control.ClassificationHandler;
import dev.tias.librarydbms.model.Classification;
import dev.tias.librarydbms.service.exceptions.ExceptionManager;
import dev.tias.librarydbms.service.exceptions.custom.InvalidIDException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 5/31/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Unit Test for the GetClassificationByID class.
 * <p>
 * Brought to you by copious amounts of nicotine.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetClassificationByIDTest extends BaseHandlerTest
{
    @Override
    protected void setupTestData()
    {

    }

    /**
     *
     */
    @Test
    @Order(1)
    void testGetClassificationByID()
    {
        System.out.print("\n1: Testing GetClassificationByID...");

        try {
            Classification classification = ClassificationHandler.getClassificationByID(1);
            assertNotNull(classification);
            assertEquals(1, classification.getClassificationID());
            assertEquals("Physics", classification.getClassificationName());
            assertEquals("Scientific literature on the topic of physics.", classification.getDescription());
            assertFalse(classification.isDeleted());
        } catch (InvalidIDException e) {
            ExceptionManager.HandleTestException(e);
        }

        System.out.print(" Test Finished.");
    }
}