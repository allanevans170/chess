package client;

import chess.ChessGame;
import chess.ChessPiece;
import com.google.gson.Gson;
import server.ServerFacade;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class ChessClient {

  private static final String ESCAPE = "\u001b[";
  public static final String GREEN = ESCAPE + "32m";
  public static final String RESET = ESCAPE + "0m";
  private final ServerFacade server;
  private final String serverUrl;

  public ChessClient(String serverUrl) {
    server = new ServerFacade(serverUrl);
    this.serverUrl = serverUrl;
  }
  public static void main(String[] args) throws Exception {
    System.out.println("♕ 240 Chess Client ♕\nType help for a list of commands\n");
    preLogin();


//      preLogin();
//      System.out.println(response);
//      // parse user input
//      // make request
//      // handle response
//
//    }
//    //help();
//    URI uri = new URI("http://localhost:8080/game");
//    HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
//    http.setRequestMethod("GET");
//
//    // Make the request
//    http.connect();
//    // if request body, write that before making request
//
//    // Output the response body
//    try (InputStream respBody = http.getInputStream()) {
//      InputStreamReader inputStreamReader = new InputStreamReader(respBody);
//      System.out.println(new Gson().fromJson(inputStreamReader, Map.class));
//    }
  }

  public static String preLogin() {
    boolean quit = false;
    Scanner scanner = new Scanner(System.in);
    //String line = scanner.nextLine();
    //var numbers = line.split(" ");

    while (!quit) {
      System.out.print("\n" + RESET + ">>> " + GREEN);
      String input = scanner.nextLine();
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch(cmd) {
        case "help" -> help();
        case "register" -> registration();
        case "login" -> "login";
        case "quit" -> {
          quit = true;
          yield "Goodbye!";
        }
        default -> "help";
      };
    }
    return " ... \n";
  }

  public String evaluateInput(String input) {}

  public static String registration() {
    return "registration";
  }

  public static void help() {
    System.out.println("Help - provides possible commands");
    System.out.println("Register <USERNAME> <PASSWORD> <EMAIL> - create an account");
    System.out.println("Login <USERNAME> <PASSWORD> - to play chess");
    System.out.println("Quit - exit the program");
  }


  // make a jar file (compile to get an executable)


  // pre-login UI

  // - help
  // - quit
  // - login
  // - register


  // post-login UI
  // - help
  // - logout
  // - create games
  // - list games
  // - play game
  // - observe game
}
