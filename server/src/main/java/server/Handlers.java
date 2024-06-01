package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataaccess.*;
import model.AuthData;
import model.UserData;
import service.*;
import spark.Request;
import spark.Response;
import service.ChessService;

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
      AuthData auth = new Gson().fromJson(req.body(), AuthData.class);
      return new Gson().toJson(chessService.getGameService().listGames(auth));
    } catch (ServiceException e) {
      ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
      res.status(d.statusCode());
      return new Gson().toJson(d);
    }
  }
  public Object CreateGameHandler(Request req, Response res) {
//        var game = new Gson().fromJson(req.body(), GameData.class);
//        var newGame = chessService.getGameService().createGame(game);
//        return new Gson().toJson(newGame);
    return null;
  }
  public Object JoinGameHandler(Request req, Response res) {
//        var game = new Gson().fromJson(req.body(), GameData.class);
//        var joinedGame = chessService.getGameService().joinGame(game);
//        return new Gson().toJson(joinedGame);
   try {

   } catch (ServiceException e) {
      ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
      res.status(d.statusCode());
      return new Gson().toJson(d);
  }

  public Object clearDatabase(Request req, Response res) {
    try {
      chessService.clear();
      res.status(200);    // successfully processed, no content to return
    } catch (ServiceException e) {
      ServiceExceptionRecord d = new ServiceExceptionRecord(e.getStatusCode(), e.getMessage());
      res.status(d.statusCode());
      return new Gson().toJson(d);
    }

    return "";
  }
}
