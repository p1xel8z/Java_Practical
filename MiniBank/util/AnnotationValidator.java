package util;

import model.annotation.MaxLength;
import model.annotation.Positive;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationValidator {

    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Class<?> clazz = obj.getClass();

        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);

                    if (field.isAnnotationPresent(Positive.class)) {
                        Positive positive = field.getAnnotation(Positive.class);
                        if (value instanceof Number n && n.longValue() <= 0) {
                            errors.add(field.getName() + " " + positive.message());
                        }
                    }

                    if (field.isAnnotationPresent(MaxLength.class)) {
                        MaxLength maxLen = field.getAnnotation(MaxLength.class);
                        if (value instanceof String s && s.length() > maxLen.value()) {
                            errors.add(field.getName()
                                    + " exceeds max length of " + maxLen.value());
                        }
                    }

                } catch (IllegalAccessException e) {
                    errors.add("Cannot read field: " + field.getName());
                }
            }
            clazz = clazz.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}
