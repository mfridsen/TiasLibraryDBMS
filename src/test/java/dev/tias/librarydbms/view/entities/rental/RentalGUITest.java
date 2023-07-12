package dev.tias.librarydbms.view.entities.rental;

import dev.tias.librarydbms.LibraryManager;
import dev.tias.librarydbms.control.entities.RentalHandler;
import dev.tias.librarydbms.service.exceptions.custom.EntityNotFoundException;
import dev.tias.librarydbms.service.exceptions.custom.InvalidIDException;
import dev.tias.librarydbms.service.exceptions.custom.InvalidTypeException;
import dev.tias.librarydbms.model.exceptions.rental.RentalNotAllowedException;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @date 5/14/2023
 * @contact matfir-1@student.ltu.se
 * <p>
 * Unit Test for the RentalGUI class.
 * <p>
 * Brought to you by copious amounts of nicotine.
 */
public class RentalGUITest
{
    public static void main(String[] args)
    {
        //Need to setup everything before GUI
        LibraryManager.setup();

        try
        {
            new RentalGUI(null, RentalHandler.createNewRental(3, 4));
            RentalHandler.printRentalList(RentalHandler.getAllRentals());
        }
        catch (EntityNotFoundException | RentalNotAllowedException | InvalidIDException | InvalidTypeException e)
        {
            e.printStackTrace();
        }
    }
}