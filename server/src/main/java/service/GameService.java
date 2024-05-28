package service;

import model.GameData;
import dataaccess.GameDAO;

import java.util.Collection;

public class GameService {

  private final GameDAO gameDAO;        // where should I put the clearService???
  private int nextID = 1;

  public GameService(GameDAO gameDAO) {
    this.gameDAO = gameDAO;
  }

  public int createGame(String gameName) {
    gameDAO.createGame(nextID, gameName);
    nextID++;
    return
            
  }
  public GameData getGame(int gameID) {}
  public Collection<GameData> listGames() {}
  // public void updateGame(GameData game) {}


}
