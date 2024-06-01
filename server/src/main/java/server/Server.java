package server;

import dataaccess.DataAccessException;

import dataaccess.*;
import model.*;

import com.google.gson.Gson;

import service.ChessService;
import service.GameService;
import service.ServiceException;
import service.UserService;
import spark.*;

import java.util.Collection;

public class Server {
    private final ChessService chessService = new ChessService(new MemoryUserDAO(), new MemoryAuthDAO(), new MemoryGameDAO());
    //private final GameService gameService;
    //private final UserService userService;

    public Server() {
        //this.gameService = gameService;
        //this.userService = userService;
    }

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        //Spark.get("/hello", (req, res) -> "Hello World");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::RegisterHandler);
        Spark.post("/session", this::LoginHandler);
        Spark.get("/game", this::ListGamesHandler);
        Spark.post("/game", this::CreateGameHandler);
        Spark.put("/game", this::JoinGameHandler);
        Spark.delete("/session", this::LogoutHandler);
        Spark.delete("/db", this::clearDatabase);

        Spark.awaitInitialization();
        return Spark.port();
    }
    public int port() {
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

//    private void exceptionHandler(ResponseException ex, Request req, Response res) {
//        res.status(ex.StatusCode());
//    }

    private Object RegisterHandler(Request req, Response res) {         // I need try catch blocks happening here and now!!
        UserData user = new Gson().fromJson(req.body(), UserData.class);
        try {
            var auth=chessService.getUserService().register(user);
            return new Gson().toJson(auth);
        } catch (ServiceException e) {
            return e.getMessage();
        }
    }
    private Object LoginHandler(Request req, Response res) {
        try {
            UserData user = new Gson().fromJson(req.body(), UserData.class);
            AuthData auth = chessService.getUserService().login(user);
            return new Gson().toJson(auth);
        } catch (ServiceException e) {
            // failur response
        }
        return "fail";
    }
    private Object ListGamesHandler(Request req, Response res) {
        //Collection<GameData> games = chessService.getGameService().listGames();  // returns a collection of objects???
        //return new Gson().toJson(games);
        return null;
    }
    private Object CreateGameHandler(Request req, Response res) {
//        var game = new Gson().fromJson(req.body(), GameData.class);
//        var newGame = chessService.getGameService().createGame(game);
//        return new Gson().toJson(newGame);
        return null;
    }
    private Object JoinGameHandler(Request req, Response res) {
//        var game = new Gson().fromJson(req.body(), GameData.class);
//        var joinedGame = chessService.getGameService().joinGame(game);
//        return new Gson().toJson(joinedGame);
        return null;
    }
    private Object LogoutHandler(Request req, Response res) {
        try {
            AuthData auth = new Gson().fromJson(req.body(), AuthData.class);
            chessService.getUserService().logout(auth);
            res.status(204);
        } catch (Exception e) {
            // failure response???
        }

        return "";
    }
    private Object clearDatabase(Request req, Response res) {
        try {
            chessService.clear();
            res.status(204);    // successfully processed, no content to return
        } catch (Exception e ) {
            // failure response...
        }

        return "";
    }
}
