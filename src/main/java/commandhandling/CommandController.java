package commandhandling;

import java.util.List;

/**
 * Command Controller Interface: interface that
 * handles parsing an array of commands and performing
 * any actions necessary.
 */
public interface CommandController {
  /**
   * The handle method will be the method that parses the commands and
   * makes any calls to execute said command.
   *
   * @param command : The commands that are to be parsed.
   * @return The output that will be displayed from handling the command.
   */
  List<String> handle(String command);
}
