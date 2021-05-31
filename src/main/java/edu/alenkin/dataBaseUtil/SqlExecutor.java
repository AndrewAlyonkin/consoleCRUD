package edu.alenkin.dataBaseUtil;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Functional Interface that define behavior of some {@link edu.alenkin.dataBaseUtil.DBWorker DBWorker} methods using
 * lambda-expressions
 * @param <T> the return type of lambda-expression execution
 */
@FunctionalInterface
public interface SqlExecutor<T> {
    /**
     *
     * @param preparedStatement the {@link PreparedStatement PreparedStatement} instance for it modification and execution
     * @return  T the type of returning instance
     * @throws SQLException when there are some problems with dataBase connection
     */
    T execute(PreparedStatement preparedStatement) throws SQLException, ExistException, NotExistException;
}
