package dev.tias.librarydbms.view.entities.rental;

import dev.tias.librarydbms.LibraryManager;
import dev.tias.librarydbms.control.entities.RentalHandler;
import dev.tias.librarydbms.model.exceptions.EntityNotFoundException;
import dev.tias.librarydbms.model.exceptions.InvalidIDException;
import dev.tias.librarydbms.model.exceptions.InvalidTypeException;
import dev.tias.librarydbms.model.exceptions.rental.RentalNotAllowedException;

import java.sql.SQLException;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 5/15/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Unit Test for the RentalUpdateGUI class.
 * <p>
 * Brought to you by copious amounts of nicotine.
 */
public class RentalUpdateGUITest
{

    public static void main(String[] args)
    throws SQLException, EntityNotFoundException, EntityNotFoundException, RentalNotAllowedException,
           InvalidIDException, InvalidTypeException
    {
        LibraryManager.setup();

        new RentalUpdateGUI(null, RentalHandler.createNewRental(1, 1));


    }
}