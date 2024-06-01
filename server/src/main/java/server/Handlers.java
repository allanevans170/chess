package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.JoinGameRequest;
import model.UserData;
import service.*;
import spark.Request;
import spark.Response;
import service.ChessService;

import java.util.Collection;
import java.util.Map;

public class Handlers {
  private final ChessService chessService = new ChessService(new MemoryUserDAO(), new MemoryAuthDAO(), new MemoryGameDAO());

  public Object RegisterHandler(Request req, Response res) {
    UserData user = new Gson().fromJson(req.body(), UserData.class);
    try {
      AuthData auth = chessService.getUserService().register(user);
      res.status(200);
      return new Gson().toJson(auth);
    } catch (ServiceException e) {
      ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
      res.status(d.statusCode());
      return new Gson().toJson(d);
    }
  }
  public Object LoginHandler(Request req, Response res) {
    try {
      UserData user = new Gson().fromJson(req.body(), UserData.class);
      AuthData auth = chessService.getUserService().login(user);
      res.status(200);
      return new Gson().toJson(auth);
    } catch (ServiceException e) {
      ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
      res.status(d.statusCode());
      return new Gson().toJson(d);
    }
  }
  public Object LogoutHandler(Request req, Response res) {
    try {
      String authToken = req.headers("Authorization");
      chessService.getUserService().logout(authToken);
      res.status(200);
      return new Gson().toJson(new JsonObject());
    } catch (ServiceException e) {
      ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
      res.status(d.statusCode());
      return new Gson().toJson(d);
    }
  }
  public Object ListGamesHandler(Request req, Response res) {
    try {
      String authToken = req.headers("Authorization");
      Collection<GameData> games = chessService.getGameService().listGames(authToken);
      var stuff = new Gson().toJson(Map.of("games:", games));
      return new Gson().toJson(Map.of("games", games));
    } catch (ServiceException e) {
      ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
      res.status(d.statusCode());
      return new Gson().toJson(d);
    }
  }
  public Object CreateGameHandler(Request req, Response res) {
    try {
      String authToken = req.headers("Authorization");
      GameData game = new Gson().fromJson(req.body(), GameData.class);
      return new Gson().toJson(chessService.getGameService().createGame(authToken, game.gameName()));
    } catch (ServiceException e) {
      ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
      res.status(d.statusCode());
      return new Gson().toJson(d);
    }
  }
  public Object JoinGameHandler(Request req, Response res) {
    try {
      String authToken = req.headers("Authorization");
      JoinGameRequest gameRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);
      return new Gson().toJson(chessService.getGameService().joinGame(authToken, gameRequest.playerColor(), gameRequest.gameID()));
    } catch (ServiceException e) {
      ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
      res.status(d.statusCode());
      return new Gson().toJson(d);
    }
  }

  public Object clearDatabase(Request req, Response res) {
    try {
      chessService.clear();
      res.status(200);    // successfully processed, no content to return
      return new Gson().toJson(new JsonObject());
    } catch (ServiceException e) {
      ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
      res.status(d.statusCode());
      return new Gson().toJson(d);
    }
  }
}
