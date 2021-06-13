package edu.alenkin.repository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface Repository <T, ID>{
    ID save(T object, ID ownerId);

    void delete(Long id);

    T get(ID id);
}
