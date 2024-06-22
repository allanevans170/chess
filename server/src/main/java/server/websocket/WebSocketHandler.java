package server.websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;
import websocket.commands.*;
import websocket.commands.UserGameCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebSocket
public class WebSocketHandler {
  Map<Integer, Set<Session>> connections = new HashMap<>(); // gameID as int, set of users/sessions in the game

  public static void main(String[] args) {
    Spark.port(8080);
    Spark.webSocket("/ws", WebSocketHandler.class);
    Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg"));
  }

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws Exception {
    UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
    String username = getUsername(session, command.getAuthString());
    switch (command.getCommandType()) {
      case CONNECT -> connect(session, username, (ConnectCommand) command);
      case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) command);
      case LEAVE -> leave(session, username, (LeaveCommand) command);
      case RESIGN -> resign(session, username, (ResignCommand) command);
    }

    System.out.printf("Received: %s", message);
    session.getRemote().sendString("WebSocket response: " + message);
  }

  private String getUsername(Session session, String authString) {    // need to obtain username from server
    // get username from authString
    return "username";
  }

  public void connect(Session session, String username, ConnectCommand connect) {
    // add session and to connections
    // create message/notification
    // send message to all users in the game? // "broadcast"
  }
  public void makeMove(Session session, String username, MakeMoveCommand makeMove) {

  }
  public void leave(Session session, String username, LeaveCommand leave) {

  }
  public void resign(Session session, String username, ResignCommand resign) {

  }


//  @OnWebSocketClose
//    public void onClose(int statusCode, String reason) {
//        // The WebSocket session has been closed
//    }

  // send a message to the client

  // session.getRemote().sendString(message);
}
