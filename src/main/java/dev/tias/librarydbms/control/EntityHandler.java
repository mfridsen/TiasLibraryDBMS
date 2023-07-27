package dev.tias.librarydbms.control;

import dev.tias.librarydbms.model.Entity;

/**
 * @author Mattias Fridsén
 * @project TiasLibraryDBMS
 * @package dev.tias.librarydbms.control
 * @contact matfir-1@student.ltu.se
 * @date 7/27/2023
 * We plan as much as we can (based on the knowledge available),
 * When we can (based on the time and resources available),
 * But not before.
 * Brought to you by enough nicotine to kill a large horse.
 */
public interface EntityHandler<T extends Entity>
{
    default boolean isNullEntity(T e)
    {
        return e == null;
    }

    boolean isUpdateAbleEntity(T e);
    boolean isDeleteAbleEntity(T e);
}
