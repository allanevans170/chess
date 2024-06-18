package websocket;

//import webSocketMessages.Notification;
import websocket.messages.ServerMessage;

import javax.websocket.*;


public interface NotificationHandler {
  void notify(ServerMessage serverMessage);
}