package dev.tias.librarydbms.service.exceptions.custom.user;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.exceptions
 * @contact matfir-1@student.ltu.se
 * @date 5/21/2023
 * <p>
 * Custom InvalidPasswordException class. Used to make Exceptions clearer.
 */
public class InvalidPasswordException extends Exception
{
    public InvalidPasswordException(String message)
    {
        super(message);
    }
}