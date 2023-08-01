package dev.tias.librarydbms.control.author;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author Jesper Truedsson
 * @project LibraryDBMS
 * @date 2023-06-01
 * Unit Test for the AuthorHandler class.
 */
@Suite
@SelectClasses({
        AuthorHandlerConstructorTest.class,
        CreateNewAuthorTest.class,
        GetAuthorByIDTest.class,
        UpdateAuthorTest.class,
        DeleteAndRecoverAuthorTest.class,
        HardDeleteAuthorTest.class
})
public class AuthorHandlerTestSuite
{


}