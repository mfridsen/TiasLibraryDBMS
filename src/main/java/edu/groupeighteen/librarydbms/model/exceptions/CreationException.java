package edu.groupeighteen.librarydbms.model.exceptions;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package edu.groupeighteen.librarydbms.model.exceptions
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