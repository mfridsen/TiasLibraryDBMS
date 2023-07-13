package dev.tias.librarydbms.service.db;

import dev.tias.librarydbms.service.exceptions.ExceptionManager;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mattias Fridsén
 * @project TiasLibraryDBMS
 * @package dev.tias.librarydbms.service.db
 * @contact matfir-1@student.ltu.se
 * @date 7/13/2023
 * <p>
 * We plan as much as we can (based on the knowledge available), When we can (based on the time and resources
 * available), But not before.
 * <p>
 * Brought to you by enough nicotine to kill a large horse.
 */
public class MetaDataRetriever
{
    /**
     * Retrieves the metadata for the "authors" table from the database, specifically the sizes of the
     * "authorFirstname" and "authorLastName" columns.
     *
     * @return an int array with two elements, the first representing the size of "authorFirstname" column and the
     * second representing the size of the "authorLastName" column.
     */
    public static int[] getAuthorMetaData()
    {
        DataAccessManager.checkConnection();

        try
        {
            DatabaseMetaData metaData = DataAccessManager.getConnection().getMetaData();

            //Get metadata for authorFirstname column
            ResultSet resultSet = metaData.getColumns(null, null,
                    "authors", "authorFirstname");
            int firstnameColumnSize = 0;
            if (resultSet.next())
            {
                firstnameColumnSize = resultSet.getInt("COLUMN_SIZE");
            }

            //Get metadata for authorLastName column
            resultSet = metaData.getColumns(null, null,
                    "authors", "authorLastName");
            int lastnameColumnSize = 0;
            if (resultSet.next())
            {
                lastnameColumnSize = resultSet.getInt("COLUMN_SIZE");
            }

            return new int[]{firstnameColumnSize, lastnameColumnSize};

        }
        catch (SQLException e)
        {
            ExceptionManager.HandleFatalException(e, "Couldn't retrieve Author Meta data due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }

        //Won't reach, but needed to compile
        return new int[0];
    }

    /**
     * Retrieves the metadata for the "classifications" table from the database, specifically the size of the
     * "classificationName" column.
     *
     * @return an int array with one element, representing the size of the "classificationName" column.
     */
    public static int[] getClassificationMetaData()
    {
        DataAccessManager.checkConnection();

        try
        {
            DatabaseMetaData metaData = DataAccessManager.getConnection().getMetaData();

            //Get metadata for classificationName column
            ResultSet resultSet = metaData.getColumns(null, null,
                    "classifications",
                    "classificationName");
            int classificationNameColumnSize = 0;
            if (resultSet.next())
            {
                classificationNameColumnSize = resultSet.getInt("COLUMN_SIZE");
            }

            return new int[]{classificationNameColumnSize};
        }
        catch (SQLException e)
        {
            ExceptionManager.HandleFatalException(e, "Couldn't retrieve Classification Meta data due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }

        //Won't reach, but needed to compile
        return new int[0];
    }

    /**
     * Retrieves the metadata for the "items" table from the database.
     *
     * @return an int array with the results.
     */
    public static int[] getItemMetaData()
    {
        DataAccessManager.checkConnection();

        try
        {
            DatabaseMetaData metaData = DataAccessManager.getConnection().getMetaData();

            //Get metadata for title column
            ResultSet resultSet = metaData.getColumns(null, null,
                    "items", "title");
            int titleColumnSize = 0;
            if (resultSet.next())
            {
                titleColumnSize = resultSet.getInt("COLUMN_SIZE");
            }

            //Get metadata for barcode column
            resultSet = metaData.getColumns(null, null,
                    "items", "barcode");
            int barcodeColumnSize = 0;
            if (resultSet.next())
            {
                barcodeColumnSize = resultSet.getInt("COLUMN_SIZE");
            }

            return new int[]{titleColumnSize, barcodeColumnSize};

        }
        catch (SQLException e)
        {
            ExceptionManager.HandleFatalException(e, "Couldn't retrieve Item Meta data due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }

        //Won't reach, but needed to compile
        return new int[0];
    }

    /**
     * Retrieves the metadata for the "literature" table from the database.
     *
     * @return an int array with the results.
     */
    public static int[] getLiteratureMetaData()
    {
        DataAccessManager.checkConnection();

        try
        {
            DatabaseMetaData metaData = DataAccessManager.getConnection().getMetaData();

            //Get metadata for title column
            ResultSet resultSet = metaData.getColumns(null, null,
                    "literature", "isbn");
            int ISBNColumnSize = 0;
            if (resultSet.next())
            {
                ISBNColumnSize = resultSet.getInt("COLUMN_SIZE");
            }

            return new int[]{ISBNColumnSize};
        }
        catch (SQLException e)
        {
            ExceptionManager.HandleFatalException(e, "Couldn't retrieve Literature Meta data due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }

        //Won't reach, but needed to compile
        return new int[0];
    }

    /**
     * Retrieves the metadata for the "films" table from the database.
     *
     * @return an int array with the results.
     */
    public static int[] getFilmMetaData()
    {
        DataAccessManager.checkConnection();

        try
        {
            DatabaseMetaData metaData = DataAccessManager.getConnection().getMetaData();

            //Get metadata for title column
            ResultSet resultSet = metaData.getColumns(null, null,
                    "films", "countryOfProduction");
            int countryOfProductionColumnSize = 0;
            if (resultSet.next())
            {
                countryOfProductionColumnSize = resultSet.getInt("COLUMN_SIZE");
            }

            return new int[]{countryOfProductionColumnSize};
        }
        catch (SQLException e)
        {
            ExceptionManager.HandleFatalException(e, "Couldn't retrieve Literature Meta data due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }

        //Won't reach, but needed to compile
        return new int[0];
    }

    /**
     * Retrieves the metadata for the "users" table from the database, specifically the sizes of the "username"
     * and "password" columns.
     *
     * @return an int array with two elements, the first representing the size of "username" column and the second
     * representing the size of the "password" column.
     */
    public static int[] getUserMetaData()
    {
        DataAccessManager.checkConnection();

        try
        {
            DatabaseMetaData metaData = DataAccessManager.getConnection().getMetaData();

            //Get metadata for username column
            ResultSet resultSet = metaData.getColumns(null, null,
                    "users", "username");
            int usernameColumnSize = 0;
            if (resultSet.next())
            {
                usernameColumnSize = resultSet.getInt("COLUMN_SIZE");
            }

            //Get metadata for password column
            resultSet = metaData.getColumns(null, null,
                    "users", "password");
            int passwordColumnSize = 0;
            if (resultSet.next())
            {
                passwordColumnSize = resultSet.getInt("COLUMN_SIZE");
            }

            //Get metadata for email column
            resultSet = metaData.getColumns(null, null,
                    "users", "email");
            int emailColumnSize = 0;
            if (resultSet.next())
            {
                emailColumnSize = resultSet.getInt("COLUMN_SIZE");
            }

            return new int[]{usernameColumnSize, passwordColumnSize, emailColumnSize};

        }
        catch (SQLException e)
        {
            ExceptionManager.HandleFatalException(e, "Couldn't retrieve User Meta data due to " +
                    e.getClass().getName() + ": " + e.getMessage());
        }

        //Won't reach, but needed to compile
        return new int[0];
    }
}