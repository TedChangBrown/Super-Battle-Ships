package sbs;

import commandhandling.REPL;

public class Main {
  
  public Main(){
  }
  public static void main(String[] args){
    run();
  }
  
  public static void run(){
    SetupController s = new SetupController();
    REPL r = new REPL("Welcome to Super Battleships: Enter s to start or h to see any other commands");
    r.registerCommand("r", s);
    r.registerCommand("h", s);
    r.registerCommand("q", s);
    r.registerCommand("s", s);
    r.run();
  }
  
  
  
}
