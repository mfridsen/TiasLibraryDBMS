package dev.tias.librarydbms.view.entities.user;

import dev.tias.librarydbms.LibraryManager;
import dev.tias.librarydbms.control.UserHandler;
import dev.tias.librarydbms.service.exceptions.custom.InvalidIDException;

/**
 * @author Jesper Truedsson
 * @project LibraryDBMS
 * @date 2023-05-18
 * Unit Test for the UserWelcome class.
 */
public class UserWelcomeTest
{
    public static void main(String[] args)
    {
        LibraryManager.setup();
        try
        {
            new UserGUI(null, UserHandler.getUserByID(1));
        }
        catch (InvalidIDException e)
        {
            e.printStackTrace();
        }
    }
}