package org.runner;

import org.converter.ClassToJsonConverter;

import java.util.Scanner;

public class ConverterRunner {
    private ClassToJsonConverter converter = new ClassToJsonConverter();

    public void run() {
        String CLASSES_PACKAGE_NAME = "org.list.classes.";
        String divider = "==============================================================";

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter class name from [" + CLASSES_PACKAGE_NAME + "*] package to convert: ");
            String className = scanner.next();
            if (className.equals("Exit")) break;
            System.out.println(divider);
            if (Character.isLowerCase(className.charAt(0))) {
                System.out.println("Please capitalize the class name!");
                System.out.println(divider);
                continue;
            }
            try {
                Class<?> clazz = Class.forName(CLASSES_PACKAGE_NAME + className);
                System.out.println(converter.convertClassToJson(clazz));
            } catch (ClassNotFoundException e) {
                System.out.println("No such class in the package =( ");
            }
            System.out.println(divider);
        }
    }
}
