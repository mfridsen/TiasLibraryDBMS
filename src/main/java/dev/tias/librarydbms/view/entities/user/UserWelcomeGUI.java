package dev.tias.librarydbms.view.entities.user;

import dev.tias.librarydbms.model.entities.User;
import dev.tias.librarydbms.view.gui.GUI;

import javax.swing.*;

/**
 * @author Jesper Truedsson
 * @project LibraryDBMS
 * @date 2023-05-10
 * <p>
 * this class displays all information about a single user object
 */
public class UserWelcomeGUI extends GUI
{
    private JLabel welcomeLabel;
    private JButton searchButton;
    private JPanel welcomePanel;
    private User user;

    /**
     * Constructs a new GUI object. Stores the previous GUI and sets the title of the GUI.
     */
    public UserWelcomeGUI(GUI previousGUI, User user)
    {
        super(previousGUI, "UserWelcomeGUI", user);
        this.user = user;
        setupPanels();
        displayGUI();
    }

    @Override
    protected JButton[] setupButtons()
    {
        searchButton = new JButton("UserSearchGUI");

        searchButton.addActionListener(e ->
        {
            dispose();
            new UserSearchGUI(this);
        });
        return new JButton[]{searchButton};
    }

    @Override
    protected void setupPanels()
    {
        welcomeLabel = new JLabel("Sök");
        welcomePanel = new JPanel();

        welcomePanel.add(welcomeLabel);

        GUIPanel.add(welcomePanel);
    }
}
