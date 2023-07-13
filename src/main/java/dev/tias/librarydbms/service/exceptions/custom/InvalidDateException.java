package dev.tias.librarydbms.service.exceptions.custom;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.exceptions
 * @contact matfir-1@student.ltu.se
 * @date 5/21/2023
 * <p>
 * Custom InvalidDateException class. Used to make Exceptions clearer.
 */
public class InvalidDateException extends Exception
{
    public InvalidDateException(String message)
    {
        super(message);
    }
}