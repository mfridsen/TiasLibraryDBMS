package dev.tias.librarydbms.service.exceptions.custom;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.exceptions
 * @contact matfir-1@student.ltu.se
 * @date 5/22/2023
 * <p>
 * Custom ConstructionException class. Used to make Exceptions clearer.
 */
public class ConstructionException extends Exception
{
    public ConstructionException(String message, Throwable cause)
    {
        super(message, cause);
    }
}