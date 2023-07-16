package dev.tias.librarydbms;

import dev.tias.librarydbms.control.ControlTestSuite;
import dev.tias.librarydbms.model.ModelTestSuite;
import dev.tias.librarydbms.service.ServiceTestSuite;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 4/18/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Test Suite for all the test classes related to this LibraryManager application.
 * <p>
 * Calls all the other test suites.
 * <p>
 * Brought to you by inhumane amounts of tobacco.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Suite
@SelectClasses({
        ModelTestSuite.class,
        ServiceTestSuite.class,
        ControlTestSuite.class,
        //View tests excluded cause I have no idea how to test GUIs yet lol
        //ViewTestSuite.class
})
public class LibraryManagerTestSuite
{
    /*private static final Logger logger;
    private static long startTime;

    static
    {
        System.setProperty("logFileName", "logs/tests/LibraryManagerTestSuite");
        logger = LoggerFactory.getLogger(LibraryManagerTestSuite.class);
    }

    @BeforeAll
    public void setUp()
    {
        System.out.println("!!DEBUG PRINT!! SETTING UP MASTER TEST SUITE");
        logger.info("Master Test Suite Start.");
        startTime = System.currentTimeMillis();
    }

    @AfterAll
    public void tearDown()
    {
        System.out.println("!!DEBUG PRINT!! TEARING DOWN MASTER TEST SUITE");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        logger.info("Master Test Suite End. Master Test Suite time: " + duration + "ms");
    }

    @Test
    public void emptyTestMethod()
    {
    }*/
}