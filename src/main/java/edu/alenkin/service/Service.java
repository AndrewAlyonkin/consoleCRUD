package edu.alenkin.service;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public interface Service <T, ID>{
    ID add(T addEntity, ID ownerId);

    void remove(ID id);

    ID update (T entity, ID ownerId);

    T get(ID id);
}
