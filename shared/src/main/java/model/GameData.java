package model;

import chess.ChessGame;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {

  public GameData(int gameID, String gameName) {
    this(gameID, null, null, gameName, new ChessGame());
  }

  public GameData setWhiteUsername(String whiteUsername) {
    return new GameData(gameID, whiteUsername, blackUsername,  gameName, game);
  }

  public GameData setBlackUsername(String blackUsername) {
    return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
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
