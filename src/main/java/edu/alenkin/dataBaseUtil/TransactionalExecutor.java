package edu.alenkin.dataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface TransactionalExecutor <T>{
    T transExecute(Connection connection) throws SQLException;
}
