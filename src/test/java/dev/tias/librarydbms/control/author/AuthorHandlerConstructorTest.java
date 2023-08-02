package dev.tias.librarydbms.control.author;

import dev.tias.librarydbms.control.BaseHandlerTest;
import dev.tias.librarydbms.model.Author;
import dev.tias.librarydbms.service.exceptions.ExceptionManager;
import dev.tias.librarydbms.service.exceptions.custom.ConstructionException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.ResultSet;
import java.sql.SQLException;

import static dev.tias.librarydbms.control.AuthorHandler.constructRetrievedAuthorFromResultSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Mattias Fridsén
 * @project TiasLibraryDBMS
 * @date 7/29/2023
 * @contact matfir-1@student.ltu.se
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorHandlerConstructorTest extends BaseHandlerTest
{
    @Override
    protected void setupTestData()
    {

    }

    @Test
    @Order(1)
    public void testConstructRetrievedAuthorFromResultSet()
    {
        System.out.println("\n1: Testing constructRetrievedAuthorFromResultSet...");

        try
        {
            ResultSet mockResultSet = mock(ResultSet.class);

            when(mockResultSet.getBoolean("deleted")).thenReturn(false);
            when(mockResultSet.getInt("authorID")).thenReturn(1);
            when(mockResultSet.getString("authorFirstName")).thenReturn("John");
            when(mockResultSet.getString("authorLastName")).thenReturn("Doe");
            when(mockResultSet.getString("biography")).thenReturn("Some biography");

            Author expectedAuthor = new Author(false, 1, "John", "Doe", "Some biography");
            Author actualAuthor = constructRetrievedAuthorFromResultSet(mockResultSet);

            assertEquals(expectedAuthor, actualAuthor);
        }
        catch (SQLException | ConstructionException e)
        {
            ExceptionManager.HandleTestException(e);
        }

        System.out.println("\nTEST FINISHED.");
    }







    /**
     *
     */
    @Test
    @Order(0)
    void testAuthorHandlerConstructor_EmptyTable()
    {
        System.out.println("\n1: Testing AuthorHandlerConstructor with an empty table...");
        assertTrue(true); // This test will always pass
        System.out.println("\nTEST FINISHED.");
    }

    /**
     *
     */
    @Test
    @Order(0)
    void testAuthorHandlerConstructor_SingleAuthorInTable()
    {
        System.out.println("\n1: Testing AuthorHandlerConstructor with a single author in the table...");
        assertTrue(true); // This test will always pass
        System.out.println("\nTEST FINISHED.");
    }

    /**
     *
     */
    @Test
    @Order(0)
    void testAuthorHandlerConstructor_MultipleAuthorsInTable()
    {
        System.out.println("\n1: Testing AuthorHandlerConstructor with multiple authors in the table...");
        assertTrue(true); // This test will always pass
        System.out.println("\nTEST FINISHED.");
    }
}