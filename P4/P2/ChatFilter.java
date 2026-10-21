class ChatFilter {
    public void  filter(String logs[], String keyword) {
        int counter = 0;
        StringBuilder messages = new StringBuilder();
        for (int i = 0; i < logs.length; i++)
        {
            String[] parts = logs[i].split(" ", 3);

            if (parts.length < 3)
             continue;


                    if (parts[2].toLowerCase().contains(keyword.toLowerCase()))
                        { 
                            counter++;
                               messages.append(parts[0])
                        .append(" ")
                        .append(parts[1])
                        .append(": ")
                        .append(parts[2])
                        .append("\n");
                        }

        }

        System.out.println("Matches: " + counter);
        System.out.println(messages);
        
    }
}