package dev.tias.librarydbms.service.db;

/**
 * @author Mattias Fridsén
 * @project LibraryDBMS
 * @package dev.tias.librarydbms.control.db
 * @contact matfir-1@student.ltu.se
 * @date 4/18/2023
 * <p>
 * We plan as much as we can (based on the knowledge available),
 * When we can (based on the time and resources available),
 * But not before.
 */
public class SQLFormatter
{
    public static void printFormattedSQL(String sql)
    {
        String formattedSQL = sql.replaceAll("  +", "\n");
        System.out.println(formattedSQL);
    }
}