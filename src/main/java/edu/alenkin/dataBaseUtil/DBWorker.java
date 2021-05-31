package edu.alenkin.dataBaseUtil;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.Config;
import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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


    public void deleteLabel(Label label) throws NotExistException {

    }

    public void updateLabel(Label label) throws NotExistException {

    }

    public Label getLabelById(long id) {
        return null;
    }

    public List<Label> getAllLabels() {
        return null;
    }

}
