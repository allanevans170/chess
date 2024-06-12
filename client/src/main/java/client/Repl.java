package client;

import java.util.Scanner;

public class Repl {
  private final ChessClient client;
  private static final String ESCAPE = "\u001b[";
  public static final String GREEN = ESCAPE + "32m";
  public static final String RESET = ESCAPE + "0m";

  public Repl(String serverUrl) {
      this.client = new ChessClient(serverUrl);
  }

  public void run() {
    System.out.println("♕ 240 Chess Client ♕");

    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {
      String line = scanner.nextLine();

      try {
        result = client.eval(line);
        System.out.print(result);
      } catch (Throwable e) {
        System.out.print(e.toString());
      }
    }
    System.out.println();
  }

  private void printPrompt() {
    System.out.print("\n" + RESET + ">>> " + GREEN);
  }
}