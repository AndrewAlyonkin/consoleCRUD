package edu.alenkin.dataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Interface that describe provider of {@link Connection connection} with data storage.
 * Can be implements by lambda-function
 *
 */

@FunctionalInterface
public interface ConnectionFactory {
    /**
     * Method for creating a SQL database {@link Connection}
     *
     * @return {@link Connection} with SQL dataBase
     * @throws SQLException when there are problems with the database
     */
   Connection getConnection() throws SQLException;
}
