package sbs;

import commandhandling.CommandController;

import java.util.ArrayList;
import java.util.List;

public class SetupController implements CommandController {
  @Override
  public List<String> handle(String command) {
    List<String> output = new ArrayList<>();
    String[] commands = command.split("\\s");
    switch (commands[0]) {
      case "h":
        if(commands.length == 1){
          this.helpCommands(output);
        }
        else{
          output.add("ERROR: Malformed Command");
        }
        break;
      case "q":
        if(commands.length == 1){
          System.exit(0);
        } else{
          output.add("ERROR: Malformed Command");
        }
        break;
      case "r":
        if(commands.length == 1){
          output.add("I'm too lazy to write this rn");
        }else{
          output.add("ERROR: Malformed Command");
        }
        break;
      default:
        output.add("ERROR: Not a valid command");
    }
    return output;
  }
  public void helpCommands(List<String> output){
    output.add("r : to see the rules of the game");
    output.add("q : to exit the game");
    output.add("s : to start the game");
  }
}
