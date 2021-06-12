package edu.alenkin.utils.jdbc;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Class provides util methods for manipulating data in database using {@link java.sql.Connection dataBase connection}.
 */
public class JdbcWorker {
    private static Connection connection;

    private static String url;
    private static String password;
    private static String user;

    static {
        Properties dbCredentials = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/DBCredentials.properties")) {
            dbCredentials.load(fis);
            url = dbCredentials.getProperty("url");
            password = dbCredentials.getProperty("password");
            user = dbCredentials.getProperty("username");
            String driver = dbCredentials.getProperty("driver");

            Class.forName(driver);
            connection = getConnection();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void executeVoid(String query, Long id) throws SQLException {
        try (PreparedStatement prepSt = getConnection().prepareStatement(query)) {
            prepSt.setLong(1, id);
            prepSt.executeUpdate();
        }
    }

    public static <T> T executeGet(String query, Long id, ResultSetParser<T> parser) {
        try (PreparedStatement prepSt = getConnection().prepareStatement(query)) {
            if (id != null) {
                prepSt.setLong(1, id);
            }
            /* Ниже - парсинг ResultSet - это единственная строка, которая уникальна для каждого случая, чтобы не
            повторять весь остальной код метода в других классах - я задаю как парсить сет с помощью
            функционального интерфейса */
            return parser.parse(prepSt.executeQuery());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Long executeSave(String query, PrepStSetter setter) throws SQLException {
        try (PreparedStatement prepSt = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            /* Строка ниже - установление параметров в запрос, это единственное, что различается при записи
            сущностей в базу, чтобы не дублировать весь остальной код метода во многих местах - я просто определяю
            это специфичное действие с помощью функционального интерфейса */
            setter.setUp(prepSt);

            prepSt.executeUpdate();
            ResultSet generatedKeys = prepSt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
        }
        return null;
    }

    public static LocalDateTime convertToLocalDateTime(Timestamp dateToConvert) {
        return new java.sql.Timestamp(
                dateToConvert.getTime()).toLocalDateTime();
    }

    private static Connection getConnection() throws SQLException {
        // Если база будет удаленной, могут быть перебои с сетью и соединение может быть разорвано и сброшено в null
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
