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

  // Should this be a record? would a regular class be better?
  // I don't think the GameData objects should be immutable, so I may change this...
}
