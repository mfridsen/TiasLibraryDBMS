package dev.tias.librarydbms.service.exceptions.custom.user;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.exceptions.user
 * @contact matfir-1@student.ltu.se
 * @date 6/2/2023
 * <p>
 * Custom InvalidEmailException class. Used to make Exceptions clearer.
 */
public class InvalidEmailException extends Exception
{
    public InvalidEmailException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidEmailException(String message)
    {
        super(message);
    }
}