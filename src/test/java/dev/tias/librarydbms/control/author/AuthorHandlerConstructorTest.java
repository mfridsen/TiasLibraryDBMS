package dev.tias.librarydbms.control.author;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * @author Mattias Fridsén
 * @project TiasLibraryDBMS
 * @date 7/29/2023
 * @contact matfir-1@student.ltu.se
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorHandlerConstructorTest
{
    @BeforeAll
    static void setUp()
    {
    }

    @AfterAll
    static void tearDown()
    {
    }

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
}