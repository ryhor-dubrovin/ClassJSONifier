package org.converter;

import java.lang.reflect.Field;
import java.util.*;

import static org.utils.ClassesConfig.CONVERTIBLE_CLASSES;
import static org.utils.TypeUtil.getClassType;

public class ClassToJsonConverter {
    public String convertClassToJson(Class<?> clazz) {
        FieldToJsonConverter fieldConverter = new FieldToJsonConverter();

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
                json.append(fieldConverter.convertCollectionToJson(field));
            } else if (Map.class.isAssignableFrom(fieldType)) {
                json.append(fieldConverter.convertMapToJson(field));
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
}
