package dev.tias.librarydbms.service.exceptions.custom;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.exceptions
 * @contact matfir-1@student.ltu.se
 * @date 6/1/2023
 * <p>
 * Custom UpdateException class. Used to make Exceptions clearer.
 */
public class UpdateException extends Exception
{
    public UpdateException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public UpdateException(String message)
    {
        super(message);
    }
}