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

        Spark.post("/user", handler::RegisterHandler);
        Spark.post("/session", handler::LoginHandler);
        Spark.get("/game", handler::ListGamesHandler);
        Spark.post("/game", handler::CreateGameHandler);
        Spark.put("/game", handler::JoinGameHandler);
        Spark.delete("/session", handler::LogoutHandler);
        Spark.delete("/db", handler::clearDatabase);

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
