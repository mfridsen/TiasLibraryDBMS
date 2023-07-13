package dev.tias.librarydbms.service.exceptions.custom;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.exceptions
 * @contact matfir-1@student.ltu.se
 * @date 5/29/2023
 * <p>
 * Custom EntityNotFoundException class. Used to make Exceptions clearer.
 */
public class EntityNotFoundException extends Exception
{
    public EntityNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public EntityNotFoundException(String message)
    {
        super(message);
    }
}