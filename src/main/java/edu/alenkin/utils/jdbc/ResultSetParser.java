package edu.alenkin.utils.jdbc;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Functional interface for parsing {@link ResultSet} and creating new entity instance with data from storage
 *
 * Для всех сущностей считывание из базы происходит одинаково, единственое различие - как парсится ResultSet.
 * Во всех репозиториях я использую один и тот же метод для считывания сущности из базы, но с помощью этого
 * функционального интерфейса определяю единственное небольшое отличие - как парсить ResultSet и создавать сущность.
 */
@FunctionalInterface
public interface ResultSetParser<T> {
    T parse(ResultSet resultSet) throws SQLException;

}
