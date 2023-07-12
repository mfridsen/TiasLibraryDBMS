package dev.tias.librarydbms.view.entities.user;

import dev.tias.librarydbms.LibraryManager;

/**
 * @author Jesper Truedsson
 * @project LibraryDBMS
 * @date 2023-05-15
 * Unit Test for the UserSearchGUI class.
 */
public class UserSearchGUITest
{
    public static void main(String[] args)
    {
        LibraryManager.setup();
        new UserSearchGUI(null);
    }
}