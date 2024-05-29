package server;

import dataaccess.DataAccessException;

import model.UserData;
import model.AuthData;
import model.GameData;

import com.google.gson.Gson;

import service.ClearService;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {
    private final ClearService clearService;
    private final GameService gameService;
    private final UserService userService;

    public Server(ClearService clearService, GameService gameService, UserService userService) {
        this.clearService = clearService;
        this.gameService = gameService;
        this.userService = userService;
    }

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.get("/hello", (req, res) -> "Hello World");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::RegisterHandler);
        Spark.post("/session", this::loginHandler);
        Spark.get("/game", this::listGamesHandler);
        Spark.post("/game", this::createGameHandler);
        Spark.put("/game", this::joinGameHandler);
        Spark.delete("/session", this::logoutHandler);
        Spark.delete("/db", this::clearDatabase);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object RegisterHandler(Request req, Response res) {
        var user = new Gson().fromJson(req.body(), UserData.class);
        user = userService.register(user);
        //return userService.register(req);
    }
    private Object clearDatabase(Request req, Response res) {
        clearService.clear();
        res.status(204);    // successfully processed, no content to return
        return "";
    }
}
