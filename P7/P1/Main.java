
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<String> validate(Object obj)
            throws IllegalAccessException {

        List<String> errors = new ArrayList<>();

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);

            if (field.isAnnotationPresent(NotBlank.class)) {
                if (value == null || value.toString().trim().isEmpty()) {
                    errors.add(field.getName() + " must not be blank");
                }
            }

            if (field.isAnnotationPresent(MaxLength.class)) {
                MaxLength max = field.getAnnotation(MaxLength.class);

                if (value != null &&
                    value.toString().length() > max.value()) {
                    errors.add(field.getName()
                            + " exceeds maximum length of "
                            + max.value());
                }
            }
        }

        return errors;
    }

    public static void main(String[] args)
            throws IllegalAccessException {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        SignupForm form = new SignupForm(username, email);

        List<String> errors = validate(form);

        if (errors.isEmpty()) {
            System.out.println("Signup form is valid!");
        } else {
            System.out.println("Validation errors:");
            for (String error : errors) {
                System.out.println(error);
            }
        }

        sc.close();
    }
}
