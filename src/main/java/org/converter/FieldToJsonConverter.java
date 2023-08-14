package org.converter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static org.utils.ClassesConfig.CONVERTIBLE_CLASSES;

public class FieldToJsonConverter {
    private ClassToJsonConverter classConverter = new ClassToJsonConverter();
    protected String convertMapToJson(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Type[] actualTypeArguments = genericType.getActualTypeArguments();
        Class<?> keyType = (Class<?>) actualTypeArguments[0];
        Type valueType = actualTypeArguments[1];

        StringBuilder mapJson = new StringBuilder();
        mapJson.append("[{\"").append(keyType.getSimpleName()).append("\":");

        if (valueType instanceof ParameterizedType) {
            ParameterizedType parameterizedValueType = (ParameterizedType) valueType;
            Type collectionType = parameterizedValueType.getActualTypeArguments()[0];
            mapJson.append(classConverter.convertClassToJson((Class<?>) collectionType));
        } else {
            mapJson.append(classConverter.convertClassToJson((Class<?>) valueType));
        }

        mapJson.append("}]");

        return mapJson.toString();
    }

    protected String convertCollectionToJson(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Class<?> collectionItemType = (Class<?>) genericType.getActualTypeArguments()[0];

        StringBuilder collectionJson = new StringBuilder();

        if (CONVERTIBLE_CLASSES.contains(collectionItemType.getSimpleName())) {
            collectionJson.append("[");
            collectionJson.append(classConverter.convertClassToJson(collectionItemType));
            collectionJson.append("]");
        } else {
            collectionJson.append("\"").append(collectionItemType.getSimpleName()).append("\"");
        }

        return collectionJson.toString();
    }
}
