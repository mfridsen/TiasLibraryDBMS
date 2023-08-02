package dev.tias.librarydbms.control.item;

import dev.tias.librarydbms.control.ItemHandler;
import dev.tias.librarydbms.control.ItemHandlerUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 6/1/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Unit Test for the GetAllItems class.
 * <p>
 * Brought to you by copious amounts of nicotine.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetAllItemsTest extends BaseItemHandlerTest
{
    //TODO-prio IMPLEMENT

    @Override
    protected void setupTestData()
    {

    }

    /**
     *
     */
    @Test
    @Order(1)
    void testGetAllItems()
    {
        System.out.println("\n1: Testing GetAllItems...");

        ItemHandlerUtils.printItemList(ItemHandler.getAllItems());

        System.out.println("Test Finished.");
    }
}