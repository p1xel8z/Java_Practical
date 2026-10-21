import java.util.regex.Pattern;

class PasswordChecker {


    public String strength(String pw) {
        String strength =  new String();
        int counter = 0;

        if (pw.length() >= 8)
           { counter++;
            System.out.println("The password is long enough");
           }
        else
        {
          System.out.println("The password is not long enough");   
        }
        if (Pattern.matches(".*[A-Z].*", pw))
           { counter++;
            System.out.println("The password has Uppercase letter");
           }
        else
        {
             System.out.println("The password does not have Uppercase letter");
          
        }
        if (Pattern.matches(".*\\d.*", pw))
           { counter++;
            System.out.println("The password contains a digit");
           }
        else
        {
               System.out.println("The password does not contain a digit");
        }
        if (Pattern.matches(".*[^A-Za-z0-9].*", pw))
           { counter++;
            System.out.println("The password has special character");
           }
        else
        {
             System.out.println("The password doesn't have special character");
        }
        
        if(counter>=0 && counter <=1)
            strength = "Weak";
        else if(counter>=2 && counter<=3)
            strength = "Medium";
        else
            strength = "Strong";

        return strength;
    }

}