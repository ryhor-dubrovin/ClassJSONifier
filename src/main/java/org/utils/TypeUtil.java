package org.utils;

public class TypeUtil {
    public static String getClassType(String typeName) {
        switch (typeName) {
            case "Byte":
                return "byte";
            case "Short":
                return "short";
            case "Integer":
                return "int";
            case "Long":
                return "long";
            case "Float":
                return "float";
            case "Double":
                return "double";
            case "Character":
                return "char";
            case "Boolean":
                return "boolean";
            default:
                return typeName;
        }
    }
}
