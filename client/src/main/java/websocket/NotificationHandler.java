package websocket;

import webSocketMessages.Notification;
import javax.websocket.*;


public interface NotificationHandler {
  void notify(Notification notification);
}