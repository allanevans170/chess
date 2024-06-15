package client;

//import chess.ChessGame;
//import chess.ChessPiece;
import com.google.gson.Gson;
import model.*;
import server.ServerFacade;
import server.ServerFacadeException;

//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
//import java.util.Scanner;

public class ChessClient {
//  private static final String ESCAPE = "\u001b[";
//  public static final String GREEN = ESCAPE + "32m";
//  public static final String RESET = ESCAPE + "0m";
  private final ServerFacade serverFacade;
  private final String serverUrl;
  private String authToken;

  private Collection<GameData> tempGamesList;

  private Status currentStatus = Status.PRE_LOGIN;

  public enum Status {
    PRE_LOGIN,
    POST_LOGIN
  }

  public ChessClient(String serverUrl) {
    serverFacade = new ServerFacade(serverUrl);
    this.serverUrl = serverUrl;
  }

  public Status getStatus() {
    return currentStatus;
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
        case "logout" -> logout();
        case "list" -> listGames();
        case "quit" -> quit();
        case "create" -> createGame(params[0]);
        case "join" -> joinGame(params);
        case "observe" -> observeGame(Integer.parseInt(params[0]));
        default -> help();
      };
    } catch (ClientException ex) {
      return ex.getMessage();
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
        authToken = output.authToken();
        currentStatus = Status.POST_LOGIN;
        return String.format("You created the account and are signed in as: %s\n", output.username());
      } catch (ServerFacadeException e) {
        throw new ClientException(e.getStatusCode(), e.getMessage());
      }
    }
    throw new ClientException(400, "Expected: registration <username> <password> <email>\n");
  }

  public String login(String... params) throws ClientException {
    if (currentStatus == Status.POST_LOGIN) {
      throw new ClientException(400, "You are already logged in - log out in order to log in as a different user\n");
    }
    if (params.length >= 1) {
      String username = params[0];
      String password = params[1];
      UserData user = new UserData(username, password, "");
      try {
        AuthData output = serverFacade.login(user);
        //System.out.println(output.toString());
        authToken = output.authToken();
        currentStatus = Status.POST_LOGIN;
        return String.format("You signed in as %s\n", output.username());
      } catch (ServerFacadeException e) {
        throw new ClientException(e.getStatusCode(), e.getMessage());
      }
    }
    throw new ClientException(400, "Expected: login <username> <password>");
  }

  public String help() {
    if (currentStatus == Status.PRE_LOGIN) {
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

  public String logout() throws ClientException {
    try {
      serverFacade.logout(authToken);
      authToken = null;
      currentStatus = Status.PRE_LOGIN;
      return ("You've signed out.\n");
    } catch (ServerFacadeException e) {
      throw new ClientException(e.getStatusCode(), e.getMessage());
    }
  }

  public String listGames() throws ClientException {
    try {
      StringBuilder gamesList = new StringBuilder();
      Collection<GameData> games = serverFacade.listGames(authToken);
      if (games == null || games.isEmpty()) {
        return "No games available - you can create your own!\n";
      }
      int counter = 1;
      for (GameData game : games) {
        gamesList.append(Integer.toString(counter)+ ".  " + game.toString()).append("\n");
        counter ++;
      }
      tempGamesList = games;
      return gamesList.toString();
    } catch (ServerFacadeException e) {
      throw new ClientException(e.getStatusCode(), e.getMessage());
    }
  }

  public String createGame(String gameName) throws ClientException {
    if (gameName == null || gameName.isEmpty()) {
      throw new ClientException(400, "Expected: create <game name>\n");
    }
    GameData newGame = new GameData(gameName);
    try {
      serverFacade.createGame(authToken, newGame);
      return "\"" + newGame.getGameName() + "\"Game created!\n";
    } catch (ServerFacadeException e) {
      throw new ClientException(e.getStatusCode(), e.getMessage());
    }
  }

  public String joinGame(String... params) throws ClientException {
    if (params.length >= 1) {
      String playerColor = params[0];
      int gameNumber = Integer.parseInt(params[1]);
      GameData game = (GameData) tempGamesList.toArray()[gameNumber - 1];
      JoinGameRequest joiner = new JoinGameRequest(playerColor, game.getGameID());
      try {
        serverFacade.joinGame(authToken, joiner);
        return "You've joined the game: " + game.getGameName() + "\n";
      } catch (ServerFacadeException e) {
        throw new ClientException(e.getStatusCode(), e.getMessage());
      }
    }
    throw new ClientException(400, "Expected: join <game number>\n");
  }

  public String observeGame(int gameNumber) throws ClientException {
    if (gameNumber < 1 || gameNumber > tempGamesList.size()) {
      throw new ClientException(400, "Expected: observe <game number>\n");
    }
    GameData game = (GameData) tempGamesList.toArray()[gameNumber - 1];
    return "You're observing the game: " + game.getGameName() + "\n";
  }

}
