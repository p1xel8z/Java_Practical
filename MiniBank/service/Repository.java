package service;

import model.annotation.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class Repository<T> {

    private final HashMap<String, T> store = new HashMap<>();

    public T save(T entity) {
        String id = resolveId(entity);
        store.put(id, entity);
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public T findById(String id) {
        return store.get(id);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public boolean delete(String id) {
        return store.remove(id) != null;
    }

    // Bounded generic method for average
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) return 0.0;
        double sum = 0;
        for (N v : values) sum += v.doubleValue();
        return sum / values.length;
    }

    private String resolveId(T entity) {
        Field idField = findIdField(entity.getClass());
        if (idField == null) {
            throw new IllegalArgumentException(
                    entity.getClass().getSimpleName()
                    + " has no field annotated with @Id");
        }
        idField.setAccessible(true);
        try {
            Object value = idField.get(entity);
            return value != null ? value.toString() : null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot read @Id field", e);
        }
    }

    private Field findIdField(Class<?> clazz) {
        if (clazz == null || clazz == Object.class) return null;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) return field;
        }
        return findIdField(clazz.getSuperclass());
    }
}
