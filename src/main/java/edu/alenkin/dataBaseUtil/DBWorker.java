package edu.alenkin.dataBaseUtil;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.Config;
import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Class provides util methods for manipulating data in database using {@link java.sql.Connection dataBase connection}.
 * Instance of class initialize by some {@link edu.alenkin.dataBaseUtil.ConnectionFactory ConnectionFactory}.
 */
public class DBWorker {
    private final ConnectionFactory connectionFactory;

    public DBWorker(ConnectionFactory connectionFactory) throws ClassNotFoundException {
        Class.forName(Config.getInstance().getDbDriver());
        this.connectionFactory = connectionFactory;
    }

    /**
     * Executes simple SQL query without parameters and return boolean result of execution
     *
     * @param query the SQL query for execution
     * @return true if some rows was updated, false - otherwise
     */
    public boolean execute(String query) {
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(query) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Executes SQL {@link PreparedStatement}, allows set it parameters and execute it in lambda-expression
     *
     * @param query    the SQL query for execution
     * @param executor the lambda-expression for setting {@link PreparedStatement} parameters and it execution
     * @return required T object
     */
    public <T> T executePrepared(String query, SqlExecutor<T> executor) throws SQLException, NotExistException, ExistException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return executor.execute(preparedStatement);
        }
    }

    public <T> T executeTransactional(TransactionalExecutor<T> transExecutor) throws SQLException {
        T result;
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                result = transExecutor.transExecute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }

    /**
     * Executes SQL {@link PreparedStatement}, allows set it parameters and execute it in lambda-expression
     *
     * @param query    the SQL query for execution
     * @param executor the lambda-expression for setting {@link PreparedStatement} parameters and it execution
     * @return {@link List} of required T objects
     */
//    public <T> List<T> executeQuery(String query, SqlExecutor<List<T>> executor) {
//        try (Connection connection = connectionFactory.getConnection()) {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            return executor.execute(preparedStatement);
//        } catch (SQLException | NotExistException | ExistException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * Util method for converting SQL timestamps from database to java {@link LocalDateTime}
     *
     * @param dateToConvert the timestamp from database for converting
     * @return converted timestamp from database to {@link LocalDateTime}
     */
    public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
    }
}
