package client;

import chess.ChessGame;
import chess.ChessPiece;
import com.google.gson.Gson;
import server.ServerFacade;
import server.ServerFacadeException;

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
//  public static void main(String[] args) throws Exception {
//    System.out.println("♕ 240 Chess Client ♕\nType help for a list of commands\n");
//    preLogin();
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

  public String preLogin(String input) {
    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "register" -> registration(params);
        case "login" -> login(params);
        case "quit" -> quit();
        default -> help();
      };
    } catch (ClientException ex) {  // what kind of exception???
      return ex.getMessage();
    }
  }
  public String postLogin(String input) {
    return "postLogin";
  }
  public static String quit() {
    System.out.print("Goodbye!  ");
    return "quit";
  }
  public static String registration(String... params) throws ClientException {
    if (params.length >= 1) {
      String username = params[0];
      String password = params[1];
      String userEmail = params[2];
      String visitorName = username;
      return String.format("You created the account: %s.\n", visitorName);
    }
    throw new ClientException(400, "Expected: registration <username> <password> <email>");
  }

  public static String login(String... params) throws ClientException {
    if (params.length >= 1) {
      String username = params[0];
      String password = params[1];
      String visitorName = username;
      return String.format("You signed in as %s.\n", visitorName);
    }
    throw new ClientException(400, "Expected: login <username> <password>");
  }

  public static String help() {
    return """
        Help - provides possible commands
        Register <USERNAME> <PASSWORD> <EMAIL> - create an account
        Login <USERNAME> <PASSWORD> - to play chess
        Quit - exit the program
        """;
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
