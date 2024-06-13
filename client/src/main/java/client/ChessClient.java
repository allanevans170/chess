package client;

import chess.ChessGame;
import chess.ChessPiece;
import com.google.gson.Gson;
import model.AuthData;
import model.UserData;
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
//  private static final String ESCAPE = "\u001b[";
//  public static final String GREEN = ESCAPE + "32m";
//  public static final String RESET = ESCAPE + "0m";
  private final ServerFacade serverFacade;
  private final String serverUrl;
  private String authToken;

  private status currentStatus = status.PRE_LOGIN;

  private enum status {
    PRE_LOGIN,
    POST_LOGIN
  }

  public ChessClient(String serverUrl) {
    serverFacade = new ServerFacade(serverUrl);
    this.serverUrl = serverUrl;
  }

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
  public void clear() throws ClientException {
    try {
      serverFacade.clear();
    } catch (ServerFacadeException e) {
      throw new ClientException(e.getStatusCode(), e.getMessage());
    }
  }
  public static String quit() {
    System.out.print("Goodbye!  ");
    return "quit";
  }
  public String registration(String... params) throws ClientException {
    if (params.length >= 1) {
      String username = params[0];
      String password = params[1];
      String userEmail = params[2];
      UserData newUser = new UserData(username, password, userEmail);
      try {
        AuthData output = serverFacade.register(newUser);
        //System.out.println(output.toString());
        currentStatus = status.POST_LOGIN;
        return String.format("You created the account: %s\n", output.username());
      } catch (ServerFacadeException e) {
//        if (e.getStatusCode() == 403) {
//          throw new ClientException(403, "Error: username already taken");
//        }
//        else if (e.getStatusCode() == 400) {
//          throw new ClientException(400, "Error: invalid input");
//        } else { }
        throw new ClientException(e.getStatusCode(), e.getMessage());
      }


    }
    throw new ClientException(400, "Expected: registration <username> <password> <email>\n");
  }

  public String login(String... params) throws ClientException {
    if (params.length >= 1) {
      String username = params[0];
      String password = params[1];
      UserData user = new UserData(username, password, "");
      try {
        AuthData output = serverFacade.login(user);
        //System.out.println(output.toString());
        currentStatus = status.POST_LOGIN;
        return String.format("You signed in as %s\n", output.username());
      } catch (ServerFacadeException e) {
        throw new ClientException(e.getStatusCode(), e.getMessage());
      }

    }
    throw new ClientException(400, "Expected: login <username> <password>");
  }

  public String help() {
    if (currentStatus == status.PRE_LOGIN) {
      return """
          Help - provides possible commands
          Register <USERNAME> <PASSWORD> <EMAIL> - create an account
          Login <USERNAME> <PASSWORD> - to play chess
          Quit - exit the program
          """;
    } else {
      return """
          Help - provides possible commands
          Logout - to logout
          Create <GAME_NAME> - to create a game
          List - to list games
          Join <GAME_NUMBER> - to play a game
          Observe <GAME_NUMBER> - to observe a game
          Quit - exit the program
          """;
    }
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
