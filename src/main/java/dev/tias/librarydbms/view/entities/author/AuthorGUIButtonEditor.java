package dev.tias.librarydbms.view.entities.author;

import dev.tias.librarydbms.model.Author;
import dev.tias.librarydbms.view.buttons.EntityButtonEditor;
import dev.tias.librarydbms.view.gui.GUI;

import javax.swing.*;

/**
 * @author Johan Lund
 * @project LibraryDBMS
 * @date 2023-05-26
 */
public class AuthorGUIButtonEditor extends EntityButtonEditor
{
    public AuthorGUIButtonEditor(JCheckBox checkBox, Author author, String label, GUI previousGUI)
    {
        super(checkBox, author, label, previousGUI);
    }

    @Override
    public Object getCellEditorValue()
    {
        if (isPushed)
        {
            new AuthorGUI(previousGUI, (Author) entity);
        }
        isPushed = false;
        return label; // Return a new String so the original is not affected
    }

}
