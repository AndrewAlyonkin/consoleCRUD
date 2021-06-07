package edu.alenkin.repository;

import edu.alenkin.utils.ResultSetParser;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface Repository <T, ID>{
    ID save(T object, ID ownerId);

    void delete(Long id);

    T get(ID id);
}
