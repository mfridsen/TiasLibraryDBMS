package dev.tias.librarydbms.service.exceptions.custom;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model
 * @contact matfir-1@student.ltu.se
 * @date 6/2/2023
 * <p>
 * Custom InvalidInputException class. Used to make Exceptions clearer.
 */
public class InvalidInputException extends Exception
{
    public InvalidInputException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidInputException(String message)
    {
        super(message);
    }
}