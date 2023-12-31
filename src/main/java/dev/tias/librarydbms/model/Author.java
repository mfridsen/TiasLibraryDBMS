package dev.tias.librarydbms.model;

import dev.tias.librarydbms.service.db.MetaDataRetriever;
import dev.tias.librarydbms.service.exceptions.custom.ConstructionException;
import dev.tias.librarydbms.service.exceptions.custom.InvalidIDException;
import dev.tias.librarydbms.service.exceptions.custom.InvalidNameException;

import java.util.Objects;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.model.entities
 * @contact matfir-1@student.ltu.se
 * @date 5/23/2023
 * <p>
 * Represents an Author in a library system. The Author class extends Entity and contains information about the author,
 * including first name, last name, and biography. It provides methods to set and get these values, enforcing various
 * constraints.
 * <p>
 * RULES:
 * - First name cannot be null or empty
 * - Last name cannot be null or empty
 * - First name must not be too long
 * - Last name must not be too long
 * - ID cannot be less than 0
 */
public class Author extends Entity
{
    /**
     * Constant defining the maximum length of the author's first name.
     */
    public static final int AUTHOR_FIRST_NAME_LENGTH;

    /**
     * Constant defining the maximum length of the author's last name.
     */
    public static final int AUTHOR_LAST_NAME_LENGTH;

    /*
     * Helps with adherence to DRY, now if we want to change the rules we only need to do so in one place.
     */
    static
    {
        int[] metaData = MetaDataRetriever.getAuthorMetaData();
        AUTHOR_FIRST_NAME_LENGTH = metaData[0]; //100
        AUTHOR_LAST_NAME_LENGTH = metaData[1];  //100
    }

    /**
     * Unique identifier of the author. Must be greater than 0.
     */
    private int authorID;

    /**
     * Author's first name. Must not be null or empty.
     */
    private String authorFirstName;

    /**
     * Author's last name.
     */
    private String authorLastName;

    /**
     * Author's biography.
     */
    private String biography;

    /**
     * Constructor for creating a new Author.
     *
     * @param authorFirstName The first name of the author.
     * @param authorLastName  The last name of the author.
     * @throws ConstructionException if the author's first name is null, empty, or too long.
     */
    public Author(String authorFirstName, String authorLastName)
    throws ConstructionException
    {
        super();
        try
        {
            this.authorID = 0;
            setAuthorFirstName(authorFirstName);
            setAuthorLastName(authorLastName);
            this.biography = null;
        }
        catch (InvalidNameException e)
        {
            throw new ConstructionException("Author Creation Construction failed due to " +
                    e.getClass().getName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Constructor for retrieving an existing Author.
     *
     * @param deleted         Indicates whether the author is deleted.
     * @param authorID        The unique identifier of the author.
     * @param authorFirstName The first name of the author.
     * @param authorLastName  The last name of the author.
     * @param biography       The biography of the author.
     * @throws ConstructionException if the authorID is invalid or if the author's names are null, empty, or too long.
     */
    public Author(boolean deleted, int authorID, String authorFirstName, String authorLastName, String biography)
    throws ConstructionException
    {
        super(deleted);
        try
        {
            setAuthorID(authorID);
            setAuthorFirstName(authorFirstName);
            setAuthorLastName(authorLastName);
            setBiography(biography);
        }
        catch (InvalidIDException | InvalidNameException e)
        {
            throw new ConstructionException("Author Retrieval Construction failed due to " +
                    e.getClass().getName() + ": " + e.getMessage(), e);
        }
    }

    /**
     * Copy constructor for Author.
     *
     * @param other The Author object to be copied.
     */
    public Author(Author other)
    {
        super(other);
        this.authorID = other.authorID;
        this.authorFirstName = other.authorFirstName;
        this.authorLastName = other.authorLastName;
        this.biography = other.biography;
    }


    @Override
    protected boolean compareFields(Object obj)
    {
        //Typecast object
        Author author = (Author) obj;

        //Compare fields
        if (this.deleted != author.deleted)
            return false;
        if (this.authorID != author.authorID)
            return false;
        if (!Objects.equals(this.authorFirstName, author.authorFirstName))
            return false;
        if (!Objects.equals(this.authorLastName, author.authorLastName))
            return false;
        if (!Objects.equals(this.biography, author.biography))
            return false;

        return true;
    }

    /**
     * Returns the unique identifier of the author.
     *
     * @return The author's ID.
     */
    public int getAuthorID()
    {
        return authorID;
    }

    /**
     * Sets the unique identifier of the author.
     *
     * @param authorID The author's ID.
     * @throws InvalidIDException if the authorID is not greater than 0.
     */
    public void setAuthorID(int authorID)
    throws InvalidIDException
    {
        if (authorID <= 0)
            throw new InvalidIDException("Author ID must be greater than 0. Received: " + authorID);
        this.authorID = authorID;
    }

    /**
     * Returns the first name of the author.
     *
     * @return The author's first name.
     */
    public String getAuthorFirstName()
    {
        return authorFirstName;
    }

    /**
     * Sets the first name of the author.
     *
     * @param authorFirstName The author's first name.
     * @throws InvalidNameException if the first name is null, empty, or too long.
     */
    public void setAuthorFirstName(String authorFirstName)
    throws InvalidNameException
    {
        if (authorFirstName == null || authorFirstName.isEmpty())
            throw new InvalidNameException("Author first name cannot be null or empty.");

        if (authorFirstName.length() > AUTHOR_FIRST_NAME_LENGTH)
            throw new InvalidNameException("Author first name must be at most " + AUTHOR_FIRST_NAME_LENGTH +
                    " characters. Received: " + authorFirstName.length());

        this.authorFirstName = authorFirstName;
    }

    /**
     * Returns the last name of the author.
     *
     * @return The author's last name.
     */
    public String getAuthorLastName()
    {
        return authorLastName;
    }

    /**
     * Sets the last name of the author.
     *
     * @param authorLastName The author's last name.
     * @throws InvalidNameException if the last name is too long.
     */
    public void setAuthorLastName(String authorLastName)
    throws InvalidNameException
    {
        if (authorFirstName == null || authorFirstName.isEmpty())
            throw new InvalidNameException("Author last name cannot be null or empty.");

        if (authorLastName.length() > AUTHOR_LAST_NAME_LENGTH)
            throw new InvalidNameException("Author last name must be at most " + AUTHOR_LAST_NAME_LENGTH +
                    " characters. Received: " + authorLastName.length());

        this.authorLastName = authorLastName;
    }

    /**
     * Returns the biography of the author.
     *
     * @return The author's biography.
     */
    public String getBiography()
    {
        return biography;
    }

    /**
     * Sets the biography of the author.
     *
     * @param biography The author's biography.
     */
    public void setBiography(String biography)
    {
        this.biography = biography;
    }
}