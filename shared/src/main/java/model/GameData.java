package model;

import chess.ChessGame;

public record GameData(int gameID, String gameName, String whiteUsername, String blackUsername, ChessGame game) {

  public GameData(int gameID, String gameName) {
    this(gameID, gameName, null, null, new ChessGame());
  }

  public String toString() {
    return "GameData{" +
            "gameID=" + gameID +
            ", white user:'" + whiteUsername + '\'' +
            ", black user:'" + blackUsername + '\'' +
            ", gameName:'" + gameName + '\'' +
            '}';    // do I need to include the game object in the toString method?
  }
}
