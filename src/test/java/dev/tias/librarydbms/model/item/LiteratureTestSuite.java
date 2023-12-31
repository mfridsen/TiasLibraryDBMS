package dev.tias.librarydbms.model.item;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 5/30/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Test Suite for the test classes dedicated to the Literature Entity class.
 */
@Suite
@SelectClasses({
        LiteratureCreationTest.class,
        LiteratureRetrievalTest.class,
        LiteratureCopyTest.class,
        LiteratureSettersTest.class
})
public class LiteratureTestSuite
{
}