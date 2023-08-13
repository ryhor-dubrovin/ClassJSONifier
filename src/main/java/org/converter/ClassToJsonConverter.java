package org.converter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static org.utils.ClassesConfig.CONVERTIBLE_CLASSES;
import static org.utils.TypeUtil.getClassType;

public class ClassToJsonConverter {
    public String convertClassToJson(Class<?> clazz) {
        if (!CONVERTIBLE_CLASSES.contains(clazz.getSimpleName())) {
            return "\"" + getClassType(clazz.getSimpleName()) + "\"";
        }

        StringBuilder json = new StringBuilder();
        json.append("{");

        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            json.append("\"").append(field.getName()).append("\":");
            Class<?> fieldType = field.getType();
            if (Collection.class.isAssignableFrom(fieldType)) {
                json.append(convertCollectionToJson(field));
            } else if (Map.class.isAssignableFrom(fieldType)) {
                json.append(convertMapToJson(field));
            } else if (CONVERTIBLE_CLASSES.contains(fieldType.getSimpleName())) {
                json.append(convertClassToJson(fieldType));
            } else {
                json.append(" \"").append(getClassType(fieldType.getSimpleName())).append("\"");
            }
            if (i < fields.length - 1) json.append(",");
        }

        json.append("}");

        return json.toString();
    }

    private String convertMapToJson(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Type[] actualTypeArguments = genericType.getActualTypeArguments();
        Class<?> keyType = (Class<?>) actualTypeArguments[0];
        Type valueType = actualTypeArguments[1];

        StringBuilder mapJson = new StringBuilder();
        mapJson.append("[{\"").append(keyType.getSimpleName()).append("\":");

        if (valueType instanceof ParameterizedType) {
            ParameterizedType parameterizedValueType = (ParameterizedType) valueType;
            Type collectionType = parameterizedValueType.getActualTypeArguments()[0];
            mapJson.append(convertClassToJson((Class<?>) collectionType));
        } else {
            mapJson.append(convertClassToJson((Class<?>) valueType));
        }

        mapJson.append("}]");

        return mapJson.toString();
    }

    private String convertCollectionToJson(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Class<?> collectionItemType = (Class<?>) genericType.getActualTypeArguments()[0];

        StringBuilder collectionJson = new StringBuilder();

        if (CONVERTIBLE_CLASSES.contains(collectionItemType.getSimpleName())) {
            collectionJson.append("[");
            collectionJson.append(convertClassToJson(collectionItemType));
            collectionJson.append("]");
        } else {
            collectionJson.append("\"").append(collectionItemType.getSimpleName()).append("\"");
        }

        return collectionJson.toString();
    }
}