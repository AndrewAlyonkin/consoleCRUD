package edu.alenkin.repository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.Config;
import edu.alenkin.dataBaseUtil.DBWorker;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Super repository class where the connection to the database is configured and the same
 * {@link edu.alenkin.dataBaseUtil.DBWorker database worker} for child repositories is initialized
 */
public class Repository {
    private static Config config = Config.getInstance();
    protected DBWorker dbWorker;

    public Repository() {
        try {
            this.dbWorker = new DBWorker(() -> DriverManager.getConnection(
                    config.getDbURL(),
                    config.getDbUser(),
                    config.getDbPassword()
            ));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
