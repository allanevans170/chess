package websocket.messages;

public class Error extends ServerMessage {
  private final serverMessageType = ServerMessageType.ERROR;
  public Error(ServerMessageType type) {
    super(type);
  }
}
