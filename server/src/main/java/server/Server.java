package server;

import com.google.gson.Gson;
import spark.*;

import java.util.Map;

public class Server {

    public Server() {
        webSocketHandler = new WebSocketHandler();
    }
    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Handlers handler = new Handlers();

        Spark.webSocket("/ws", handler::webSocketHandler);

        Spark.post("/user", handler::registerHandler);
        Spark.post("/session", handler::loginHandler);
        Spark.get("/game", handler::listGamesHandler);
        Spark.post("/game", handler::createGameHandler);
        Spark.put("/game", handler::joinGameHandler);
        Spark.delete("/session", handler::logoutHandler);
        Spark.delete("/db", handler::clearDatabaseHandler);

        Spark.exception(Exception.class, this:: errorHandler);
        Spark.notFound((req, res) -> {
            var msg = String.format("[%s] %s not found", req.requestMethod(), req.pathInfo());
            return errorHandler(new Exception(msg), req, res);
        });

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
    public Object errorHandler(Exception e, Request req, Response res) {
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage()), "success", false));
        res.type("application/json");
        res.status(500);
        res.body(body);
        return body;
    }
}
