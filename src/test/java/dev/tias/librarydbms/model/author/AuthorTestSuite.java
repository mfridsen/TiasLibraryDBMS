package dev.tias.librarydbms.model.author;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 5/28/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Unit Test for the Author class.
 * <p>
 * Brought to you by copious amounts of nicotine.
 */

@Suite
@SelectClasses({
        AuthorCreationTest.class,
        AuthorRetrievalTest.class,
        AuthorCopyTest.class,
        AuthorSettersTest.class
})

public class AuthorTestSuite
{
    //TODO-prio rework tests based on name length rules
}