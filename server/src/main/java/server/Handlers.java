package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.*;
import model.*;
import service.*;
import spark.Request;
import spark.Response;

import java.util.Collection;

public class Handlers {
  private final ChessService chessService;

    public Handlers() {
      try {
        AuthDAO authDAO=new SQLAuthDAO();
        UserDAO userDAO=new SQLUserDAO();
        GameDAO gameDAO=new SQLGameDAO();
        chessService = new ChessService(userDAO, authDAO, gameDAO);
      } catch (DataAccessException e) {
        throw new RuntimeException("Error: " + e.getMessage(), e);
      }
    }

  public Object registerHandler(Request req, Response res) {
    UserData user = new Gson().fromJson(req.body(), UserData.class);
    try {
      AuthData auth = chessService.getUserService().register(user);
      res.status(200);
      return new Gson().toJson(auth);
    } catch (ServiceException e) {
      return handleExceptions(e, res);
    }
  }
  public Object loginHandler(Request req, Response res) {
    try {
      UserData user = new Gson().fromJson(req.body(), UserData.class);
      AuthData auth = chessService.getUserService().login(user);
      res.status(200);
      return new Gson().toJson(auth);
    } catch (ServiceException e) {
      return handleExceptions(e, res);
    }
  }
  public Object logoutHandler(Request req, Response res) {
    try {
      String authToken = req.headers("Authorization");
      chessService.getUserService().logout(authToken);
      res.status(200);
      return new Gson().toJson(new JsonObject());
    } catch (ServiceException e) {
      return handleExceptions(e, res);
    }
  }
  public Object listGamesHandler(Request req, Response res) {
    try {
      String authToken = req.headers("Authorization");
      Collection<GameData> games = chessService.getGameService().listGames(authToken);
      return new Gson().toJson(new ListGames(games));
    } catch (ServiceException e) {
      return handleExceptions(e, res);
    }
  }
  public Object createGameHandler(Request req, Response res) {
    try {
      String authToken = req.headers("Authorization");
      GameData game = new Gson().fromJson(req.body(), GameData.class);
      return new Gson().toJson(chessService.getGameService().createGame(authToken, game.getGameName()));
    } catch (ServiceException e) {
      return handleExceptions(e, res);
    }
  }
  public Object joinGameHandler(Request req, Response res) {
    try {
      String authToken = req.headers("Authorization");
      JoinGameRequest gameRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);
      return new Gson().toJson(chessService.getGameService().joinGame(authToken, gameRequest.playerColor(), gameRequest.gameID()));
    } catch (ServiceException e) {
      return handleExceptions(e, res);
    }
  }

  public Object clearDatabaseHandler(Request req, Response res) {
    try {
      chessService.clear();
      res.status(200);
      return new Gson().toJson(new JsonObject());
    } catch (ServiceException e) {
      return handleExceptions(e, res);
    }
  }

  public static String handleExceptions(ServiceException e, Response res) {
    ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
    res.status(d.statusCode());
    return new Gson().toJson(d);
  }
}
