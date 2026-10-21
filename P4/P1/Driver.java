import java.util.Scanner;

class Driver
{
    public static void main(String[] args)
    {
        PasswordChecker checker = new PasswordChecker();
        String pswd = new String();
        String strength = new String();
        Scanner sc = new Scanner(System.in);

        for(int i = 0;i<3;i++)
        {
        System.out.print("Enter password: "); 
        pswd = sc.nextLine();

        System.out.println(pswd);
        strength = checker.strength(pswd);
        System.out.println(strength);
        }

       sc.close();
    }
}