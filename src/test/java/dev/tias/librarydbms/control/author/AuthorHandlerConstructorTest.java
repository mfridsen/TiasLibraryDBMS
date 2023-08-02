package dev.tias.librarydbms.control.author;

import static dev.tias.librarydbms.control.AuthorHandler.constructRetrievedAuthorFromResultSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.tias.librarydbms.control.BaseHandlerTest;
import dev.tias.librarydbms.model.Author;
import dev.tias.librarydbms.service.exceptions.custom.ConstructionException;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mattias Fridsén
 * @project TiasLibraryDBMS
 * @date 7/29/2023
 * @contact matfir-1@student.ltu.se
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorHandlerConstructorTest extends BaseHandlerTest
{
    /**
     *
     */
    @Test
    @Order(1)
    void testAuthorHandlerConstructor()
    {
        System.out.println("\n1: Testing AuthorHandlerConstructor...");
        assertTrue(true); // This test will always pass
        System.out.println("\nTEST FINISHED.");
    }

    @Override
    protected void customTestDataSetup()
    {

    }

    @Test
    public void testConstructRetrievedAuthorFromResultSet() {
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
            throw new RuntimeException(e);
        }
    }

}