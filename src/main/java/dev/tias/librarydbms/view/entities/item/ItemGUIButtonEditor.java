package dev.tias.librarydbms.view.entities.item;

import dev.tias.librarydbms.LibraryManager;
import dev.tias.librarydbms.control.entities.RentalHandler;
import dev.tias.librarydbms.control.exceptions.ExceptionHandler;
import dev.tias.librarydbms.model.entities.Item;
import dev.tias.librarydbms.model.entities.Rental;
import dev.tias.librarydbms.model.exceptions.EntityNotFoundException;
import dev.tias.librarydbms.model.exceptions.InvalidIDException;
import dev.tias.librarydbms.model.exceptions.InvalidTypeException;
import dev.tias.librarydbms.model.exceptions.rental.RentalNotAllowedException;
import dev.tias.librarydbms.view.entities.rental.RentalGUI;
import dev.tias.librarydbms.view.optionpanes.LoginOptionPane;
import dev.tias.librarydbms.view.buttons.EntityButtonEditor;
import dev.tias.librarydbms.view.gui.GUI;

import javax.swing.*;

/**
 * @author Jesper Truedsson
 * @project LibraryDBMS
 * @date 2023-05-22
 * <p>
 * This class extends EntityButtonEditor and specifically handles GUI button interactions for items. The class
 * overrides the getCellEditorValue method to determine the behavior of the button when clicked.
 * Depending on the label of the button, different actions are performed.
 * <p>
 * If the label is "View", a new ItemGUI instance is created to view the item's details.
 * If the label is "Rent", the item's availability is checked. If the item is available and a user is currently
 * logged in, an attempt to create and open a new rental is made. If the item is unavailable, or the user is not
 * logged in, appropriate messages are outputted and handled.
 * <p>
 * It also overrides the getCellEditorValue method from the EntityButtonEditor class to handle button press events.
 */
public class ItemGUIButtonEditor extends EntityButtonEditor
{
    /**
     * Constructs a new ItemGUIButtonEditor with the given parameters.
     *
     * @param checkBox    a JCheckBox that the superclass's constructor requires.
     * @param item      the Entity object that the button will open a EntityGUI for when clicked.
     * @param label       the label of the button.
     * @param previousGUI the GUI from which the EntityGUI will be opened.
     */
    public ItemGUIButtonEditor(JCheckBox checkBox, Item item, String label, GUI previousGUI)
    {
        super(checkBox, item, label, previousGUI);
    }

    /**
     * This method is called when the button is clicked. It checks the label of the button to determine the action
     * to be taken. If the label is "View", a new ItemGUI instance is created to view the item's details. If the
     * label is "Rent", the item's availability is checked. If the item is available and a user is currently logged
     * in, an attempt to create and open a new rental is made. If the item is unavailable, or the user is not logged
     * in, appropriate messages are outputted and handled.
     *
     * @return the label of the button after it is clicked.
     */
    @Override
    public Object getCellEditorValue()
    {
        if (isPushed)
        {
            if ("View".equals(label))
            {
                // code to view the item
                new ItemGUI(previousGUI, (Item) entity);
            }
            else if ("Rent".equals(label))
            {
                if (((Item) entity).isAvailable()) //If item can be rented...
                {
                    if (LibraryManager.getCurrentUser() != null) //... and we are logged in
                    {
                        try
                        {
                            createAndOpenNewRental((Item) entity);
                        }
                        catch (EntityNotFoundException | InvalidIDException | InvalidTypeException fatalException) //This SHOULD NOT happen
                        {
                            ExceptionHandler.HandleFatalException("Rental creation failed fatally due to: " +
                                    fatalException.getCause().getClass().getName() + ", Message: " +
                                    fatalException.getMessage(), fatalException);
                        }
                        catch (RentalNotAllowedException rentalNotAllowedException) //This is perfectly fine
                        {
                            System.err.println(rentalNotAllowedException.getMessage());
                        }
                    }
                    else //... and we are not logged in
                    {
                        new LoginOptionPane(previousGUI);
                    }
                }
                else //.. item can't be rented
                {
                    System.err.println("Item can't be rented.");
                }
            }
        }
        isPushed = false;
        return label; // Return a new String so the original is not affected
    }

    /**
     * Creates a new Rental for the specified Item and opens the RentalGUI for the new rental.
     *
     * @param item the Item to be rented.
     * @throws EntityNotFoundException   if the User or Item entities cannot be found.
     * @throws RentalNotAllowedException if the rental is not allowed.
     * @throws InvalidIDException        if the ID of the User or Item is invalid.
     */
    private void createAndOpenNewRental(Item item)
    throws EntityNotFoundException, RentalNotAllowedException, InvalidIDException, InvalidTypeException
    {
        Rental newRental = RentalHandler.createNewRental(LibraryManager.getCurrentUser().getUserID(),
                item.getItemID());
        RentalHandler.printRentalList(RentalHandler.getAllRentals()); //TODO-prio debug print
        newRental.printReceipt();
        previousGUI.dispose();
        new RentalGUI(previousGUI, newRental);
    }
}