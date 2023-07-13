package dev.tias.librarydbms.service;

import dev.tias.librarydbms.service.db.DataAccessManager;
import dev.tias.librarydbms.service.db.DatabaseConnectionTest;
import dev.tias.librarydbms.service.db.MetaDataRetrieverTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author Mattias Fridsén
 * @project TiasLibraryDBMS
 * @date 7/12/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Test Suite for all the test classes related to the Service part of this application.
 * <p>
 * Calls all the test classes and/or suites in the service package.
 */
@Suite
@SelectClasses({
        DatabaseConnectionTest.class,
        DataAccessManager.class,
        MetaDataRetrieverTest.class,
})
public class ServiceTestSuite
{
}