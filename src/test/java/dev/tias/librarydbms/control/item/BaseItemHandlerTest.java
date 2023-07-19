package dev.tias.librarydbms.control.item;

import dev.tias.librarydbms.control.BaseHandlerTest;
import dev.tias.librarydbms.service.db.DataAccessManager;

/**
 * @author Mattias Fridsén
 * @project TiasLibraryDBMS
 * @package dev.tias.librarydbms.control.item
 * @contact matfir-1@student.ltu.se
 * @date 7/19/2023
 * <p>
 * We plan as much as we can (based on the knowledge available), When we can (based on the time and resources
 * available), But not before.
 * <p>
 * Brought to you by enough nicotine to kill a large horse.
 */
public abstract class BaseItemHandlerTest extends BaseHandlerTest
{
    protected void setupTestData_ForItemTests_AuthorClassification()
    {
        DataAccessManager.executeSQLCommandsFromFile("src/main/resources/sql/data/author_test_data.sql");
        DataAccessManager.executeSQLCommandsFromFile("src/main/resources/sql/data/classification_test_data.sql");
    }

    protected void setupTestData_ForItemTests_full()
    {
        DataAccessManager.executeSQLCommandsFromFile("src/main/resources/sql/data/item_author_classification_test_data.sql");
    }
}