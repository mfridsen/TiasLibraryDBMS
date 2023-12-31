package dev.tias.librarydbms.service.exceptions.custom;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 2023-05-28
 */
public class InvalidNameException extends Exception
{
    public InvalidNameException(String message)
    {
        super(message);
    }

    public InvalidNameException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
