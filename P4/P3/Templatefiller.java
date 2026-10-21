import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TemplateFiller
{
   static String fillTemplate(String template,String names[],String values[])
    {
        Pattern p = Pattern.compile("\\{(\\w+)\\}");
        Matcher m = p.matcher(template);

        StringBuilder result = new StringBuilder();

        int lastEnd = 0;
        while(m.find())
        {
            result.append(template.substring(lastEnd,m.start()));

            String placeholder = m.group(1);
            String replacement = "[?]";

            for(int i = 0;i<names.length;i++)
            {
                if(names[i].equals(placeholder))
                {
                    replacement = values[i];
                    break;
                }
            }

            result.append(replacement);
            lastEnd = m.end();

        }
        
        result.append(template.substring(lastEnd));
        
        return result.toString(); 
    }
}