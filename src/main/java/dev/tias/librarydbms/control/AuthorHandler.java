package dev.tias.librarydbms.control;

import dev.tias.librarydbms.model.Author;
import dev.tias.librarydbms.service.db.DataAccessManager;
import dev.tias.librarydbms.service.db.QueryResult;
import dev.tias.librarydbms.service.exceptions.ExceptionManager;
import dev.tias.librarydbms.service.exceptions.custom.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.control.entities
 * @contact matfir-1@student.ltu.se
 * @date 5/25/2023
 * <p>
 * We plan as much as we can (based on the knowledge available),
 * When we can (based on the time and resources available),
 * But not before.
 * <p>
 * Brought to you by enough nicotine to kill a large horse.
 */
public class AuthorHandler extends EntityHandler<Author>
{

    public static void printAuthorList(List<Author> authorList)
    {
        System.out.println("Authors:");
        int count = 1;
        for (Author author : authorList)
        {
            System.out.println(
                    count + " authorID: " + author.getAuthorID() + ", Author Name: " + author.getAuthorFirstname());
        }
    }

    //CREATE -----------------------------------------------------------------------------------------------------------

    public static Author createNewAuthor(String authorFirstname, String authorLastName)
    throws InvalidNameException
    {
        Author newAuthor = null;

        try
        {
            //Validate input
            validateAuthorNames(authorFirstname, authorLastName);

            // Create and save the new author, retrieving the ID
            newAuthor = new Author(authorFirstname, authorLastName);
            newAuthor.setAuthorID(saveAuthor(newAuthor));


        }
        catch (ConstructionException | InvalidIDException e)
        {
            ExceptionManager.HandleFatalException(e, String.format("Failed to create Author with the given name: " +
                    "'%s' due to %s: %s", authorFirstname, e.getClass().getName(), e.getMessage()));
        }

        return newAuthor;
    }

    private static int saveAuthor(Author author)
    {
        try
        {
            // Prepare query
            String query = "INSERT INTO authors (authorFirstname, authorLastName, " +
                    "VALUES (?, ?)";

            String[] params = {
                    author.getAuthorFirstname(),
                    author.getAuthorLastName(),
            };

            // Execute query and get the generated authorID, using try-with-resources
            try (QueryResult queryResult =
                         DataAccessManager.executePreparedQuery(query, params, Statement.RETURN_GENERATED_KEYS))
            {
                ResultSet generatedKeys = queryResult.getStatement().getGeneratedKeys();
                if (generatedKeys.next())
                {
                    return generatedKeys.getInt(1);
                }
            }
        }
        catch (SQLException e)
        {
            ExceptionManager.HandleFatalException(e, "Failed to save author to database due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }

        //Not reachable, but needed for compilation
        return 0;
    }

    public static Author getAuthorByID(int authorID, boolean getDeleted)
    {
        Author author = null;

        //Prepare statement
        String query = "SELECT authorID, authorFirstname, authorLastname, biography, deleted " +
                "FROM authors WHERE authorID = ?";
        String[] params = {String.valueOf(authorID)};

        //Execute statement
        try (QueryResult queryResult = DataAccessManager.executePreparedQuery(query, params))
        {
            ResultSet resultSet = queryResult.getResultSet();
            if (resultSet.next())
                author = new Author(
                        resultSet.getInt("authorID"),
                        resultSet.getString("authorFirstname"),
                        resultSet.getString("authorLastname"),
                        resultSet.getString("biography"),
                        resultSet.getBoolean("deleted")
                );
        }
        catch (SQLException | ConstructionException e)
        {
            ExceptionManager.HandleFatalException(e, "Failed to retrieve author by ID from database due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }

        return author;
    }


    //UPDATE -----------------------------------------------------------------------------------------------------------

    public static void updateAuthor(Author updatedAuthor)
    throws InvalidNameException
    {
        //Let's check if the author exists in the database before we go on
        updateAuthor(updatedAuthor);

        // Prepare a SQL command to update a updatedAuthors's data by authorID.
        String sql = "UPDATE authors SET authorFirstname = ?, authorLastName = ?, + WHERE authorID = ?";
        String[] params = {
                updatedAuthor.getAuthorFirstname(),
                updatedAuthor.getAuthorLastName(),
                String.valueOf(updatedAuthor.getAuthorID())
        };

        // Execute the update.
        DataAccessManager.executePreparedUpdate(sql, params);
    }

    public static void deleteAuthor(Author authorToDelete)
    throws DeletionException
    {
        {
            //Validate input
            try
            {
                validateAuthor(authorToDelete);
            }
            catch (EntityNotFoundException | InvalidIDException | NullEntityException e)
            {
                throw new DeletionException(
                        "Author Delete failed due to: " + e.getClass().getName() + ": " + e.getMessage(), e);
            }

            //Set deleted to true (doesn't need to be set before calling this method)
            authorToDelete.setDeleted(true);

            //Prepare a SQL query to update the author details
            String query = "UPDATE authors SET deleted = ? WHERE authorID = ?";
            String[] params = {
                    authorToDelete.isDeleted() ? "1" : "0",
                    String.valueOf(authorToDelete.getAuthorID())
            };

            //Executor-class Star Dreadnought
            DataAccessManager.executePreparedUpdate(query, params);
        }
    }

    public static void undoDeleteAuthor(Author authorToRecover)
    throws RecoveryException
    {
        // Validate input
        try
        {
            validateAuthor(authorToRecover);
        }
        catch (EntityNotFoundException | InvalidIDException | NullEntityException e)
        {
            throw new RecoveryException(
                    "Author Recovery failed due to: " + e.getClass().getName() + ": " + e.getMessage(), e);
        } //TODO- alla 'throw new' skall göras som ovan

        // Set deleted to false
        authorToRecover.setDeleted(false);

        // Prepare a SQL query to update the author details
        String query = "UPDATE authors SET deleted = ? WHERE authorID = ?";
        String[] params = {
                authorToRecover.isDeleted() ? "1" : "0",
                String.valueOf(authorToRecover.getAuthorID())
        };

        // Executor-class Star Dreadnought
        DataAccessManager.executePreparedUpdate(query, params);
    }


    public static void hardDeleteAuthor(Author authorToDelete)
    throws DeletionException
    {
        {
            //Validate input
            try
            {
                validateAuthor(authorToDelete);
            }
            catch (NullEntityException | EntityNotFoundException | InvalidIDException e)
            {
                throw new DeletionException(
                        "Author Delete failed due to: " + e.getClass().getName() + ": " + e.getMessage(), e);
            }

            //Prepare a SQL query to update the authorToDelete details
            String query = "DELETE FROM authors WHERE authorID = ?";
            String[] params = {String.valueOf(authorToDelete.getAuthorID())};

            //Executor-class Star Dreadnought
            DataAccessManager.executePreparedUpdate(query, params);
        }
    }

    //RETRIEVING -------------------------------------------------------------------------------------------------------
    public static List<Author> getAuthorByAuthorName(String authorFirstname, String authorLastname)
    throws InvalidNameException
    {
        //both names cant be null or empty, only one can be
        if ((authorFirstname == null || authorFirstname.isEmpty()) && (authorLastname == null || authorLastname.isEmpty()))
            throw new InvalidNameException("Both first and last name can not be empty at the same time.");

        try
        {
            List<Author> authors = new ArrayList<>();

            // Prepare a SQL query to select a author by authorFirstname and authorLastname
            String query = "SELECT authorID, authorFirstname, authorLastname, biography, deleted FROM authors WHERE";
            List<String> params = new ArrayList<>();

            if (authorFirstname != null && !authorFirstname.trim().isEmpty())
            {
                query += " LOWER(authorFirstname) = ?";
                params.add(authorFirstname.toLowerCase());
            }

            if (authorLastname != null && !authorLastname.trim().isEmpty())
            {
                if (!params.isEmpty())
                {
                    query += " AND";
                }
                query += " LOWER(authorLastname) = ?";
                params.add(authorLastname.toLowerCase());
            }

            String[] paramsArray = params.toArray(new String[0]);

            // Execute the query and store the result in a ResultSet
            try (QueryResult queryResult = DataAccessManager.executePreparedQuery(query, paramsArray))
            {
                ResultSet resultSet = queryResult.getResultSet();
                // If the ResultSet contains data, create a new Author object using the retrieved ,
                // and set the author's authorID
                while (resultSet.next())
                {
                    Author author = new Author(
                            resultSet.getInt("authorID"),
                            resultSet.getString("authorFirstname"),
                            resultSet.getString("authorLastname"),
                            resultSet.getString("biography"),
                            resultSet.getBoolean("deleted")
                    );
                    authors.add(author);
                }
            }
            return authors;
        }
        catch (SQLException | ConstructionException e)
        {
            throw new RuntimeException(e);//TODO-prio change
        }
    }
    //UTILITY METHODS---------------------------------------------------------------------------------------------------

    private static void validateAuthorNames(String authorFirstname, String authorLastName)
    throws InvalidNameException
    {
        if(StringIsNullOrEmpty(authorFirstname))
            throw new InvalidNameException("Author first name is null or empty.");

        if (StringIsNullOrEmpty(authorLastName))
            throw new InvalidNameException("Author last name is null or empty.");

        if (StringIsTooLong(authorFirstname, Author.AUTHOR_FIRST_NAME_LENGTH))
            throw new InvalidNameException(
                    "Author first name too long. Must be at most " + Author.AUTHOR_FIRST_NAME_LENGTH +
                            " characters, received " + authorFirstname.length());




        if (authorLastName.length() > Author.AUTHOR_LAST_NAME_LENGTH)
            throw new InvalidNameException(
                    "Author last name too long. Must be at most " + Author.AUTHOR_LAST_NAME_LENGTH +
                            " characters, received " + authorLastName.length());
    }

    private static void validateAuthor(Author author)
    throws EntityNotFoundException, InvalidIDException, NullEntityException
    {
        checkNullAuthor(author);
        int ID = author.getAuthorID();
        if (AuthorHandler.getAuthorByID(ID, true) == null)
            throw new EntityNotFoundException("Author with ID " + author + "not found in database.");
    }


    private static void checkNullAuthor(Author author)
    throws NullEntityException
    {
        if (author == null)
            throw new NullEntityException("Author is null.");
    }


    private static void checkValidAuthorID(int authorID)
    throws InvalidIDException
    {
        if (authorID <= 0)
        {
            throw new InvalidIDException("Invalid authorID: " + authorID);
        }
    }
}
