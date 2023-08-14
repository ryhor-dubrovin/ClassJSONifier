package org.runner;

import static org.utils.ClassesConfig.CONVERTIBLE_CLASSES;

public class Main {
    public static void main(String[] args) {
        ConverterRunner runner = new ConverterRunner();
        runner.start(CONVERTIBLE_CLASSES);
    }
}
