package dev.tias.librarydbms.service.db;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 5/29/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Tests the Meta Data retrieval methods in DataAccessManager.
 * <p>
 * NOTE: These tests will fail if rules of any related tables are changed.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MetaDataRetrieverTest
{
    @BeforeAll
    static void setUp()
    {
        DataAccessManager.setup(false);
    }

    @AfterAll
    static void tearDown()
    {
        DataAccessManager.closeDatabaseConnection();
    }

    /**
     * Tests retrieving Author meta data.
     */
    @Test
    @Order(1)
    void testGetAuthorMetaData()
    {
        System.out.print("\n1: Testing to retrieve Author meta data...");

        int[] metaData = MetaDataRetriever.getAuthorMetaData();
        assertEquals(100, metaData[0]);
        assertEquals(100, metaData[1]);

        System.out.print(" Test Finished.");
    }

    /**
     * Tests retrieving Classification meta data.
     */
    @Test
    @Order(2)
    void testGetClassificationMetaData()
    {
        System.out.print("\n2: Testing to retrieve Classification meta data...");

        int[] metaData = MetaDataRetriever.getClassificationMetaData();
        assertEquals(255, metaData[0]);

        System.out.print(" Test Finished.");
    }

    /**
     * Tests retrieving Item meta data.
     */
    @Test
    @Order(3)
    void testGetItemMetaData()
    {
        System.out.print("\n3: Testing to retrieve Item meta data...");

        int[] metaData = MetaDataRetriever.getItemMetaData();
        assertEquals(255, metaData[0]);
        assertEquals(255, metaData[1]);

        System.out.print(" Test Finished.");
    }

    /**
     * Tests retrieving User meta data.
     */
    @Test
    @Order(4)
    void testGetUserMetaData()
    {
        System.out.print("\n4: Testing to retrieve User meta data...");

        int[] metaData = MetaDataRetriever.getUserMetaData();
        assertEquals(20, metaData[0]);
        assertEquals(50, metaData[1]);
        assertEquals(255, metaData[2]);

        System.out.print(" Test Finished.");
    }

    /**
     * Tests retrieving Literature meta data.
     */
    @Test
    @Order(5)
    void testGetLiteratureMetaData()
    {
        System.out.print("\n5: Testing to retrieve Literature meta data...");

        int[] metaData = MetaDataRetriever.getLiteratureMetaData();
        assertEquals(13, metaData[0]);

        System.out.print(" Test Finished.");
    }

    /**
     * Tests retrieving Film meta data.
     */
    @Test
    @Order(6)
    void testGetFilmMetaData()
    {
        System.out.print("\n6: Testing to retrieve Film meta data...");

        int[] metaData = MetaDataRetriever.getFilmMetaData();
        assertEquals(100, metaData[0]);

        System.out.print(" Test Finished.");
    }
}