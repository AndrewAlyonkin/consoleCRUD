package edu.alenkin;

import edu.alenkin.controller.WriterController;

import java.util.Scanner;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class Console {
    public static void main(String[] args){
        WriterController controller = new WriterController();
        controller.refresh();

        System.out.println("Программа завершила работу ...");
    }
}
