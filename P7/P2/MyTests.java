public class MyTests {

    @Run
    public void testAddition() {
        System.out.println("Addition test passed");
    }

    @Run
    public void testSubtraction() {
        System.out.println("Subtraction test passed");
    }

    public void normalMethod() {
        System.out.println("This method should not run");
    }

    @Run
    public void testMessage() {
        System.out.println("Message test passed");
    }
}
