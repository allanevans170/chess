package server;

import dataaccess.DataAccessException;

import dataaccess.*;
import model.*;

import com.google.gson.Gson;

import service.ChessService;
import service.GameService;
import service.UserService;
import spark.*;

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

    public Server run(int desiredPort) {
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
        return this;
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

    private Object RegisterHandler(Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        //try {
        var auth = chessService.getUserService().register(user);
        return new Gson().toJson(auth);
    }
    private Object LoginHandler(Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        var auth = chessService.getUserService().login(user);
        return new Gson().toJson(auth);
    }
    private Object ListGamesHandler(Request req, Response res) throws DataAccessException {
        var games = chessService.getGameService().listGames();  // returns a collection of objects???
        return new Gson().toJson(games);
    }
    private Object CreateGameHandler(Request req, Response res) throws DataAccessException {
        var game = new Gson().fromJson(req.body(), GameData.class);
        var newGame = chessService.getGameService().createGame(game);
        return new Gson().toJson(newGame);
    }
    private Object JoinGameHandler(Request req, Response res) throws DataAccessException {
        var game = new Gson().fromJson(req.body(), GameData.class);
        var joinedGame = chessService.getGameService().joinGame(game);
        return new Gson().toJson(joinedGame);
    }
    private Object LogoutHandler(Request req, Response res) throws DataAccessException {
        var user = new Gson().fromJson(req.body(), UserData.class);
        chessService.getUserService().logout(user);
        res.status(204);
        return "";
    }
    private Object clearDatabase(Request req, Response res) {
        chessService.clear();
        res.status(204);    // successfully processed, no content to return
        return "";
    }
}
