package edu.alenkin;

import edu.alenkin.controller.Controller;
import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.*;
import edu.alenkin.repository.WriterRepositoryImpl;
import edu.alenkin.service.PostServiceImpl;
import edu.alenkin.view.FullView;
import edu.alenkin.view.View;
import edu.alenkin.view.WriterView;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class Console {
    public static void main(String[] args){
        FullView fullView = new FullView();
        WriterView writerView = new WriterView();
        Controller controller = new Controller(fullView, writerView);

        Scanner scanner = new Scanner(System.in);
        controller.setScanner(scanner);

        fullView.setController(controller);
        writerView.setController(controller);


        controller.init();

        scanner.close();
        System.out.println("Программа завершила работу ...");
    }
}
