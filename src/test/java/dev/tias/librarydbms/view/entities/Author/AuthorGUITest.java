package dev.tias.librarydbms.view.entities.Author;

import dev.tias.librarydbms.LibraryManager;
import dev.tias.librarydbms.control.entities.AuthorHandler;
import dev.tias.librarydbms.view.entities.author.AuthorGUI;

/**
 * @author Jesper Truedsson
 * @project LibraryDBMS
 * @date 2023-06-04
 * Unit Test for the AuthorGUI class.
 */
public class AuthorGUITest
{
    public static void main(String[] args)
    {
        LibraryManager.setup();
        new AuthorGUI(null, AuthorHandler.getAuthorByID(1, false));
    }
}