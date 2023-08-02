package dev.tias.librarydbms.control.author;

import dev.tias.librarydbms.control.AuthorHandler;
import dev.tias.librarydbms.control.BaseHandlerTest;
import dev.tias.librarydbms.model.Author;
import dev.tias.librarydbms.service.db.DataAccessManager;
import dev.tias.librarydbms.service.exceptions.custom.ConstructionException;
import dev.tias.librarydbms.service.exceptions.custom.InvalidIDException;
import dev.tias.librarydbms.service.exceptions.custom.InvalidNameException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 5/31/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Unit Test for the GetAuthorByID class.
 * <p>
 * Brought to you by copious amounts of nicotine.
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetAuthorByIDTest extends BaseHandlerTest
{
    @Override
    protected void setupTestData()
    {

    }

    private static final String validAuthorFirstName1 = "validfirstname1";
    private static final String validAuthorFirstName2 = "validfirstname2";
    private static final String validAuthorFirstName3 = "validfirstname3";
    private static final String validAuthorLastName1 = "validlastname1";
    private static final String validAuthorLastName2 = "validlastname2";
    private static final String validAuthorLastName3 = "validlastname3";
    private static final int invalidID = -1;
    private static int existingAuthorID;
    private static int nonExistingAuthorID;
    private static int deletedAuthorID;

    @BeforeAll
    protected static void customSetup()
    {
        try
        {
            AuthorHandler authorHandler = new AuthorHandler();

            Author existingAuthor = authorHandler.createNewAuthor(validAuthorFirstName1, validAuthorLastName1);
            existingAuthorID = existingAuthor.getAuthorID();

            Author nonExistingAuthor = new Author(validAuthorFirstName2, validAuthorLastName2);

            //authorID is set to 0 by default by constructor, which is invalid
            nonExistingAuthor.setAuthorID(9999);
            nonExistingAuthorID = nonExistingAuthor.getAuthorID();

            Author deletedAuthor = authorHandler.createNewAuthor(validAuthorFirstName3, validAuthorLastName3);
            deletedAuthorID = deletedAuthor.getAuthorID();

            //Soft delete deletedUser
            DataAccessManager.executePreparedUpdate("UPDATE authors SET deleted = 1 WHERE authorname = " +
                    "'validauthorname3';", null);
        }
        catch (ConstructionException | InvalidIDException e)
        {
            e.printStackTrace();
        }
        catch (InvalidNameException e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     */
    @Test
    @Order(1)
    void testGetAuthorByID()
    {
        System.out.println("\n1: Testing GetAuthorByID...");

        Author author = AuthorHandler.getAuthorByID(1, true);

        assertNotNull(author);
        assertEquals(1, author.getAuthorID());
        assertEquals("author1", author.getAuthorFirstName());
        assertEquals("lastname1", author.getAuthorLastName());
        assertEquals("is the first author", author.getBiography());
        assertFalse(author.isDeleted());

        System.out.println("Test Finished.");
    }

    /**
     * Tests the `getAuthorByID` method with a valid ID for a deleted author (getDeleted = true).
     */
    @Test
    @Order(2)
    void testGetAuthorByID_ValidDeletedAuthor_GetDeletedTrue()
    {
        System.out.println(
                "\n4: Testing getAuthorByID method with a valid ID for a deleted author (getDeleted = true)...");

        assertNotNull(AuthorHandler.getAuthorByID(deletedAuthorID, true));

        System.out.println("Test Finished.");
    }

    /**
     * Tests the `getAuthorByID` method with an invalid author ID.
     */
    @Test
    @Order(3)
    void testGetAuthorByID_InvalidAuthorID()
    {
        System.out.println("\n5: Testing getAuthorByID method with an invalid author ID...");

        assertThrows(InvalidIDException.class, () -> AuthorHandler.getAuthorByID(invalidID, true));

        System.out.println("Test Finished.");
    }
}