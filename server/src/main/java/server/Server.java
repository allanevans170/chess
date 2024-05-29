package server;

import dataaccess.DataAccessException;

import service.ClearService;
import service.GameService;
import service.UserService;
import spark.*;

public class Server {
    private final ClearService clearService = new ClearService();
    private final GameService gameService = new GameService();
    private final UserService userService = new UserService();

    public Server() { }

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.get("/hello", (req, res) -> "Hello World");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clearDatabase);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
    
    private Object clearDatabase(Request req, Response res) throws DataAccessException {
        // clear database
        Server ClearService;
        ClearService.clearDatabase();
        res.status(200);
        return "";
    }
}
