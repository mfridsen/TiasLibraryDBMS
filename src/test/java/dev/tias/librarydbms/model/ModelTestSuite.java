package dev.tias.librarydbms.model;

import dev.tias.librarydbms.service.db.DatabaseConnectionTest;
import dev.tias.librarydbms.model.entities.author.AuthorTestSuite;
import dev.tias.librarydbms.model.entities.classification.ClassificationTestSuite;
import dev.tias.librarydbms.model.entities.item.FilmTestSuite;
import dev.tias.librarydbms.model.entities.item.LiteratureTestSuite;
import dev.tias.librarydbms.model.entities.rental.RentalTestSuite;
import dev.tias.librarydbms.model.entities.user.UserTestSuite;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 4/18/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * We plan as much as we can (based on the knowledge available),
 * When we can (based on the time and resources available),
 * But not before.
 * <p>
 * Test Suite for all the test classes related to the LibraryManager part of this application.
 * Calls all the test classes in the librarymanager package. Is itself called by the ApplicationTestSuite class.
 */

@Suite
@SelectClasses({
        AuthorTestSuite.class,
        ClassificationTestSuite.class,
        LiteratureTestSuite.class,
        FilmTestSuite.class,
        UserTestSuite.class,
        RentalTestSuite.class,
})

public class ModelTestSuite
{
}