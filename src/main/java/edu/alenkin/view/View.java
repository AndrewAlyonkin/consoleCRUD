package edu.alenkin.view;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Represent the program window. Shows the current state of the program in the console.
 */
public interface View<T> {

    void show(T t);

    void show(List<T> ts);


    default void show(String message) {
        System.out.println(message);
    }


    default void err(String err){
        System.err.println(err);
    }

}
