package dev.tias.librarydbms.service.exceptions.custom;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.exceptions
 * @contact matfir-1@student.ltu.se
 * @date 6/2/2023
 * <p>
 * Custom InvalidTypeException class. Used to make Exceptions clearer.
 */
public class InvalidTypeException extends Exception
{
    public InvalidTypeException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidTypeException(String message)
    {
        super(message);
    }
}