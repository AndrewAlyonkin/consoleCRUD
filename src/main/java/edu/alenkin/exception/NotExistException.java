package edu.alenkin.exception;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Exception that indicates that current object is not exist in storage
 */
public class NotExistException extends Exception{
    public NotExistException() {
        super("Такой записи не существует!");
    }
}
