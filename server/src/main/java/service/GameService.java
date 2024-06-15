package service;

//import chess.ChessGame;
import dataaccess.*;
import model.*;

import java.util.Collection;

public class GameService {

  private final GameDAO gameDAO;        // where should I put the clearService???
  private final AuthDAO authDAO;
  private int nextID = 1;

  public GameService(GameDAO gameDAO, AuthDAO authDAO) {
    this.gameDAO = gameDAO;
    this.authDAO = authDAO;
  }

  public GameData createGame(String authToken, String gameName) throws ServiceException {
    try {
      int currentID = nextID;
      nextID++;
      if (gameName == "" || gameName == null) {
        throw new ServiceException(400, "Error: bad request");
      }
      if (authDAO.getAuth(authToken) == null) {
        throw new ServiceException(401, "Error: unauthorized");
      }
      return gameDAO.createGame(gameName);
    } catch (DataAccessException e) {
      throw new ServiceException(500, "Error: "+e.getMessage());
    }
  }
  public GameData joinGame(String authToken, String playerColor, int gameID) throws ServiceException {
    try {
      if (playerColor == null || gameID < 1) {
        throw new ServiceException(400, "Error: bad request");
      }

      playerColor = playerColor.toUpperCase();
      if ((!playerColor.equals("WHITE") && !playerColor.equals("BLACK"))) {   // incorrect player color
        throw new ServiceException(401, "Error: bad request");
      }
      if ((gameDAO.getGame(gameID) == null))  {                              // game not found
        throw new ServiceException(401, "Error: not found");
      }
      if (authDAO.getAuth(authToken) == null) {
        throw new ServiceException(401, "Error: unauthorized");               // unauthorized, no authToken
      }

      AuthData authenticated = authDAO.getAuth(authToken);
      GameData game = gameDAO.getGame(gameID);

//      if ((game.getWhiteUsername().equals( authenticated.username()) && playerColor.equals("WHITE")) ||
//              (game.getBlackUsername().equals(authenticated.username()) && playerColor.equals("BLACK"))) {
//        // readmit the player to the game
//        throw new ServiceException(99, "You're already logged in dog");
//      }
      if (game.getWhiteUsername() != null && playerColor.equals("WHITE") ||
              game.getBlackUsername() != null && playerColor.equals("BLACK")) {
        throw new ServiceException(403, "Error: already taken");              // player already taken
      }
      if (playerColor.equals("WHITE")) {
        game.setWhiteUsername(authenticated.username());
      } else if (playerColor.equals("BLACK")) {
        game.setBlackUsername(authenticated.username());
      }
      gameDAO.updateGame(game);
      return game;
    } catch (DataAccessException e) {
      throw new ServiceException(500, "Error: "+e.getMessage());
    }
  }
  public Collection<GameData> listGames(String authToken) throws ServiceException {
    try {
      if ((authToken == null) || (authDAO.getAuth(authToken) == null)) {
        throw new ServiceException(401, "Error: unauthorized");
      }
      return gameDAO.listGames();
    } catch (DataAccessException e) {
      throw new ServiceException(500, "Error: "+e.getMessage());
    }
  }
}
