
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws Exception {

        MyTests tests = new MyTests();
        int count = 0;

        for (Method method : MyTests.class.getDeclaredMethods()) {

            if (method.isAnnotationPresent(Run.class)) {
                method.invoke(tests);
                count++;
            }
        }

        System.out.println("Total tests run: " + count);
    }
}
