package model;

import chess.ChessGame;

public class GameData {
  private final int gameID;
  private String whiteUsername;
  private String blackUsername;
  private final String gameName;
  private ChessGame game;

  public GameData(int gameID, String gameName) {
    this.gameID = gameID;
    this.gameName = gameName;
    this.game = new ChessGame();
  }

  public void setWhiteUsername(String whiteUser) {
    whiteUsername = whiteUser;
  }

  public void setBlackUsername(String blackUser) {
    blackUsername = blackUser;
  }

  public int getGameID() {
    return gameID;
  }
  public String getGameName() {
    return gameName;
  }

  public String getWhiteUsername() {
    return whiteUsername;
  }
  public String getBlackUsername() {
    return blackUsername;
  }

  public ChessGame getGame() {
    return game;
  }

}
