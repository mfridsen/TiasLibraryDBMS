package dev.tias.librarydbms.service.exceptions.custom.rental;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.exceptions.rental
 * @contact matfir-1@student.ltu.se
 * @date 6/5/2023
 * <p>
 * Custom RentalReturnException class. Used to make Exceptions clearer.
 */
public class RentalReturnException extends Exception
{
    public RentalReturnException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public RentalReturnException(String message)
    {
        super(message);
    }
}