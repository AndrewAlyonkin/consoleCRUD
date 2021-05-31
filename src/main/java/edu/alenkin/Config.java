package edu.alenkin;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Class provides database configuration from DBCredentials.properties file for creating database connection.
 */
public class Config {
    private final static Config instance = new Config();
    private String dbDriver;
    private String dbURL;
    private String dbUser;
    private String dbPassword;
    private Properties dbCredentials;

    private Config() {
       dbCredentials = new Properties();
       try (FileInputStream fis = new FileInputStream("DBCredentials.properties")) {
           dbCredentials.load(fis);
       } catch (IOException e) {
           e.printStackTrace();
       }
       this.dbDriver = dbCredentials.getProperty("classpath");
       this.dbURL = dbCredentials.getProperty("url");
       this.dbPassword = dbCredentials.getProperty("password");
       this.dbUser = dbCredentials.getProperty("username");

    }
    public static Config getInstance(){
        return instance;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbURL() {
        return dbURL;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }
}
