package model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {

  public GameData(int gameID, String gameName) {
    this(gameID, null, null, gameName, new ChessGame());
    // which fields can be null? we need a gameID for sure

    // do we need at least one username to be non-null?
    // do we need a gameName to be non-null?
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
