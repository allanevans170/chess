package server;

import spark.*;

public class Server {

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Handlers handler = new Handlers();

        Spark.post("/user", handler::registerHandler);
        Spark.post("/session", handler::loginHandler);
        Spark.get("/game", handler::listGamesHandler);
        Spark.post("/game", handler::createGameHandler);
        Spark.put("/game", handler::joinGameHandler);
        Spark.delete("/session", handler::logoutHandler);
        Spark.delete("/db", handler::clearDatabaseHandler);

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
}
