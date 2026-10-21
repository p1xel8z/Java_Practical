import java.util.Scanner;

class Driver 
{
    public static void main(String [] args)
    {
        String logs[] = new String[4];
        ChatFilter chatfilter = new ChatFilter();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("---Enter logs---");
        for(int i = 0;i<4;i++)
        {
            System.out.print("Enter log: ");
            logs[i] = sc.nextLine();
        }

        String keyword = new String();
        System.out.print("Enter keyword: ");
        keyword = sc.nextLine();

        chatfilter.filter(logs,keyword);

        sc.close();
    }
}