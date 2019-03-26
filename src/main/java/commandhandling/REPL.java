package commandhandling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REPL: Generic repl class that parses through
 * the command line inputs and has a handler process them.
 */
public class REPL {
  private Map<String, CommandController> validCommands;
  private String instructions;

  /**
   * Constructor for the repl. Instantiates the command map.
   */
  public REPL(String ins) {
    this.validCommands = new HashMap<>();
    instructions = ins;
  }


  /**
   * Method to run the REPL and start listening to input from the command line.
   * Processes the commands from the line
   * using a given handler.
   */
  public void run() {
    BufferedReader r;
      try {
        r = new BufferedReader(new InputStreamReader(System.in));
        String command;
        //continuously read for input until EOF
        System.out.println(instructions);
        while ((command = r.readLine()) != null) {
          String[] commands = command.split("\\s");
          if (commands.length > 0) {
            List<String> output;
            CommandController handler = validCommands.get(commands[0]);
            if (handler != null) {
              output = handler.handle(command);
            } else {
              output = new ArrayList<>();
              output.add("ERROR: Not a valid command");
            }
            //print out output from the handler
            System.out.print("\033[H\033[2J");
            System.out.flush();
            for (String s : output) {
              System.out.println(s);
            }
            System.out.println(instructions);
          }
        }
    } catch (IOException e) {
      System.out.println("There was an IO Exception");
    }
  }

  /**
   * Method to register a valid command into the REPL. Adds a
   * command to the valid command map to designate which commands
   * go to which command handlers.
   *
   * @param name    the name of the command
   * @param handler the handler to map the command to
   */
  public void registerCommand(String name, CommandController handler) {
    this.validCommands.put(name, handler);
  }
}
