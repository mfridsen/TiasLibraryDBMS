package dev.tias.librarydbms.view.entities.item;

import dev.tias.librarydbms.LibraryManager;
import dev.tias.librarydbms.control.entities.ItemHandler;
import dev.tias.librarydbms.control.entities.ItemHandlerUtils;
import dev.tias.librarydbms.control.entities.UserHandler;
import dev.tias.librarydbms.model.entities.Item;
import dev.tias.librarydbms.service.exceptions.custom.EntityNotFoundException;
import dev.tias.librarydbms.service.exceptions.custom.NullEntityException;
import dev.tias.librarydbms.model.exceptions.user.UserValidationException;
import dev.tias.librarydbms.view.gui.GUI;

import javax.swing.*;

/**
 * @author Jesper Truedsson
 * @project LibraryDBMS
 * @date 2023-05-22
 * <p>
 * This class represents a graphical user interface for deleting an item in the library system.
 * It extends the GUI class, inheriting its basic window features, and adds specific components and functionality
 * needed for deleting items.
 */
public class ItemDeleteGUI extends GUI
{
    //TODO-comment
    /**
     * The item to be deleted.
     */
    private final Item itemToDelete;

    /**
     * The password field for the user to enter their password.
     */
    private JPasswordField passwordField;

    /**
     * Constructs a new ItemDeleteGUI object.
     *
     * @param previousGUI the previous GUI window.
     * @param itemToDelete the item to be deleted.
     */
    public ItemDeleteGUI(GUI previousGUI, Item itemToDelete)
    {
        super(previousGUI, "Delete Item?", itemToDelete);
        this.itemToDelete = itemToDelete;
        setupPanels();
        displayGUI();
    }

    /**
     * Sets up the buttons for this GUI window.
     * Only one button is needed in this case: the "Confirm" button for confirming the deletion.
     *
     * @return an array containing the confirm button.
     */
    @Override
    protected JButton[] setupButtons()
    {
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e ->
        {
            //TODO-prio you shouldn't be able to access this GUI at all without being logged in (and staff)
            if (LibraryManager.getCurrentUser() != null)
            {
                //TODO-debug
                System.out.println("Validating user: " + LibraryManager.getCurrentUser().getUsername());
                System.out.println("Password: " + LibraryManager.getCurrentUser().getPassword());
                try
                {
                    //TODO-debug
                    String password = new String(passwordField.getPassword());
                    if (UserHandler.validate(LibraryManager.getCurrentUser(), password))
                    {
                        ItemHandlerUtils.printItemList(ItemHandler.getAllItems());

                        //TODO-prio fix ItemHandler so we don't get soft deleted items...
                        ItemHandler.hardDeleteItem(itemToDelete);
                        System.out.println("Deletion successful!");
                        //TODO-prio ... when we call this
                        ItemHandlerUtils.printItemList(ItemHandler.getAllItems());

                        dispose();
                        new ItemHandlerGUI(null);
                    }
                    else
                    {
                        System.err.println("Password does not match user.");
                        //TODO-debug
                        System.err.println("Password received: " + password);
                    }
                }
                catch (UserValidationException | NullEntityException | EntityNotFoundException nullEntityException)
                {
                    nullEntityException.printStackTrace();
                }
            }
        });
        return new JButton[]{confirmButton};
    }

    /**
     * Sets up the panels for this GUI window.
     * In this case, a panel is created for the password input field.
     */
    @Override
    protected void setupPanels()
    {
        JPanel passwordPanel = new JPanel();
        JLabel passwordLabel = new JLabel("Enter Password:");
        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        GUIPanel.add(passwordPanel);
    }
}