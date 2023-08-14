package org.runner;

import org.converter.ClassToJsonConverter;

import java.util.Scanner;

public class ConverterRunner {
    public void run() {
        ClassToJsonConverter converter = new ClassToJsonConverter();
        String classesPackageName = "org.list.classes.";
        String divider = "==============================================================";

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter class name from [" + classesPackageName + "*] package to convert: ");
            String className = scanner.next();
            if (className.equals("Exit")) break;
            System.out.println(divider);
            if (Character.isLowerCase(className.charAt(0))) {
                System.out.println("Please capitalize the class name!");
                System.out.println(divider);
                continue;
            }
            try {
                Class<?> clazz = Class.forName(classesPackageName + className);
                System.out.println(converter.convertClassToJson(clazz));
            } catch (ClassNotFoundException e) {
                System.out.println("No such class in the package =( ");
            }
            System.out.println(divider);
        }
    }
}
