package dev.tias.librarydbms.control;

import dev.tias.librarydbms.control.entities.item.ItemHandlerTestSuite;
import dev.tias.librarydbms.control.entities.rental.RentalHandlerTestSuite;
import dev.tias.librarydbms.control.entities.user.UserHandlerTestSuite;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 4/18/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Test Suite for all the test classes related to the LibraryManager part of this application.
 * Calls all the test classes in the librarymanager package. Is itself called by the ApplicationTestSuite class.
 */

@Suite
@SelectClasses({
        //AuthorHandlerTestSuite.class,
        //ClassificationHandlerTestSuite.class,
        ItemHandlerTestSuite.class,
        UserHandlerTestSuite.class,
        RentalHandlerTestSuite.class,
})

public class ControlTestSuite
{

}