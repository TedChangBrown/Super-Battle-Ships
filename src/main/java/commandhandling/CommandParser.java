package commandhandling;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {

  public String[] splitSpaceQuotes(String command) {
    List<String> list = new ArrayList<String>();
    Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
    while (m.find()) {
      String x = m.group(1);
      x = x.replace("\"", "");
      list.add(x);
    }
    return list.toArray(new String[0]);
  }

  public String[] splitQuoteWords(String command) {
    List<String> list = new ArrayList<String>();
    Pattern p = Pattern.compile("\"([^\"]*)\"");
    Matcher m = p.matcher(command);
    while (m.find()) {
      String x = m.group(1);
      list.add(x);
    }
    return list.toArray(new String[0]);
  }
}
