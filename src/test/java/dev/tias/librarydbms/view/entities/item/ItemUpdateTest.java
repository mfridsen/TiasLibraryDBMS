package dev.tias.librarydbms.view.entities.item;

import dev.tias.librarydbms.LibraryManager;
import dev.tias.librarydbms.control.entities.ItemHandler;
import dev.tias.librarydbms.model.exceptions.InvalidIDException;
import dev.tias.librarydbms.model.exceptions.RetrievalException;

/**
 * @author Jesper Truedsson
 * @project LibraryDBMS
 * @date 2023-05-24
 * Unit Test for the ItemUpdate class.
 */
public class ItemUpdateTest
{
    public static void main(String[] args)
    {
        LibraryManager.setup();
        testMethodOne();
    }

    private static void testMethodOne()
    {
        try
        {
            new ItemUpdateGUI(null, ItemHandler.getItemByID(1));
        }
        catch (InvalidIDException | RetrievalException e)
        {
            throw new RuntimeException(e);
        }
    }
}