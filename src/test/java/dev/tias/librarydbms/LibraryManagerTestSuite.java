package dev.tias.librarydbms;

import dev.tias.librarydbms.control.ControlTestSuite;
import dev.tias.librarydbms.model.ModelTestSuite;
import dev.tias.librarydbms.service.ServiceTestSuite;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


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

@Suite
@SelectClasses({
        ModelTestSuite.class,
        ServiceTestSuite.class,
        ControlTestSuite.class,
        //View tests excluded cause I have no idea how to test GUIs yet lol
        //ViewTestSuite.class
})

public class
LibraryManagerTestSuite
{

}