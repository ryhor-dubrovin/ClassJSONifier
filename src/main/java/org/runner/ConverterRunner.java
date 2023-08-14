package org.runner;

import org.converter.ClassToJsonConverter;

import java.util.List;
import java.util.Scanner;

public class ConverterRunner {
    public static final String CLASSES_PACKAGE_NAME = "org.list.classes.";
    public static final String DIVIDER = "==============================================================";
    private ClassToJsonConverter converter = new ClassToJsonConverter();

    public void start(List<String> classes) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Chose class to convert:");
            System.out.println("0. Exit program");
            for (int i = 0; i < classes.size(); i++) {
                System.out.println((i + 1) + ". " + classes.get(i));
            }

            System.out.print("Enter class number: ");
            String userInput = scanner.nextLine();
            if (userInput.equals("0")) break;
            try {
                int choice = Integer.parseInt(userInput) - 1;
                if (choice >= 0 && choice < classes.size()) {
                    Class<?> clazz = Class.forName(CLASSES_PACKAGE_NAME + classes.get(choice));
                    System.out.println(DIVIDER);
                    System.out.println(converter.convertClassToJson(clazz));
                } else {
                    System.out.println(DIVIDER);
                    System.out.println("Incorrect choice. Please try again.");
                }
            } catch (NumberFormatException | ClassNotFoundException e) {
                System.out.println(DIVIDER);
                System.out.println("Please enter a number. Try again.");
            }
            System.out.println(DIVIDER);
        }
    }

    public void run() {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter class name from [" + CLASSES_PACKAGE_NAME + "*] package to convert: ");
            String className = scanner.next();
            if (className.equals("Exit")) break;
            System.out.println(DIVIDER);
            if (Character.isLowerCase(className.charAt(0))) {
                System.out.println("Please capitalize the class name!");
                System.out.println(DIVIDER);
                continue;
            }
            try {
                Class<?> clazz = Class.forName(CLASSES_PACKAGE_NAME + className);
                System.out.println(converter.convertClassToJson(clazz));
            } catch (ClassNotFoundException e) {
                System.out.println("No such class in the package =( ");
            }
            System.out.println(DIVIDER);
        }
    }
}
