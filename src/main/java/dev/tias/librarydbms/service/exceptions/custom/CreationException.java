package dev.tias.librarydbms.service.exceptions.custom;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.exceptions
 * @contact matfir-1@student.ltu.se
 * @date 6/2/2023
 * <p>
 * Custom CreationException class. Used to make Exceptions clearer.
 */
public class CreationException extends Exception
{
    public CreationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public CreationException(String message)
    {
        super(message);
    }
}