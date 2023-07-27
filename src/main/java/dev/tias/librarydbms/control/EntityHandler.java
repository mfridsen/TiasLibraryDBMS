package dev.tias.librarydbms.control;

import dev.tias.librarydbms.model.Entity;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.control.entities
 * @contact matfir-1@student.ltu.se
 * @date 6/7/2023
 * <p>
 * Generic class that contains utility methods that were recurring in the different Handler classes.
 */
public abstract class EntityHandler<T extends Entity>
{
    protected boolean isNullEntity(T e)
    {
        return e == null;
    }

    protected static boolean isValidString(String s, int minL, int maxL) {
        // If the string is null or empty, return false
        if (StringIsNullOrEmpty(s))
            return false;

        // If minL is greater than zero and the string is too short, return false
        if (minL > 0 && StringIsTooShort(s, minL))
            return false;

        // If maxL is greater than zero and the string is too long, return false
        if (maxL > 0 && StringIsTooLong(s, maxL))
            return false;

        // If none of the above conditions were met, the string is valid
        return true;
    }

    protected static boolean StringIsNullOrEmpty(String s)
    {
        return (s == null || s.isEmpty());
    }

    protected static boolean StringIsTooShort(String s, int l)
    {
        return s.length() < l;
    }

    protected static boolean StringIsTooLong(String s, int l)
    {
        return s.length() > l;
    }
}