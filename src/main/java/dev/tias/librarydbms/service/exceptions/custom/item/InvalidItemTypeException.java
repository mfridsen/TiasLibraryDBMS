package dev.tias.librarydbms.service.exceptions.custom.item;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.exceptions.item
 * @contact matfir-1@student.ltu.se
 * @date 5/30/2023
 * <p>
 * Custom InvalidItemTypeException class. Used to make Exceptions clearer.
 */
public class InvalidItemTypeException extends Exception
{
    public InvalidItemTypeException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidItemTypeException(String message)
    {
        super(message);
    }
}