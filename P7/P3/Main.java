
import java.lang.reflect.Field;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {

        String[] header = {
            "StudentEmail", "StudentName", "StudentAge"
        };

        String[] data = {
            "rahul@example.com", "Rahul", "20"
        };

        Student student = new Student();

        Map<String, String> values = new HashMap<>();

        for (int i = 0; i < header.length; i++) {
            values.put(header[i], data[i]);
        }

        for (Field field : Student.class.getDeclaredFields()) {

            if (field.isAnnotationPresent(Column.class)) {

                Column column = field.getAnnotation(Column.class);
                String value = values.get(column.name());

                if (value == null) {
                    System.out.println(
                        "Warning: Missing column " + column.name()
                    );
                    continue;
                }

                field.setAccessible(true);

                if (field.getType() == int.class) {
                    field.setInt(student, Integer.parseInt(value));
                } else {
                    field.set(student, value);
                }
            }
        }

        student.display();
    }
}
