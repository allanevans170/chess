package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;
import dataaccess.*;
import model.*;
public class GameServiceTests {
  private MemoryAuthDAO memoryAuthDAO;
  private MemoryUserDAO memoryUserDAO;
  private MemoryGameDAO memoryGameDAO;
  private ChessService chessService;

  @BeforeEach
  public void setUp() {
    memoryAuthDAO = new MemoryAuthDAO();
    memoryUserDAO = new MemoryUserDAO();
    memoryGameDAO = new MemoryGameDAO();
    chessService = new ChessService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);
  }

  @Test
  public void positiveCreateGame() {
    try {
      AuthData auth = chessService.getUserService().register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));

      assertEquals(1, memoryAuthDAO.listAuths().size(),"AuthDAO should have 1 auth");
      assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 auth");

      int gameID = chessService.getGameService().createGame(auth, "magnusVsHikaru");

      assertEquals(1, memoryGameDAO.listGames().size(),"GameDAO should have 1 game");
      assertEquals("magnusVsHikaru", memoryGameDAO.getGame(gameID).gameName());

    } catch (Exception e) {
      System.out.println("error: "+e.getMessage());
    }
  }

  @Test
  public void negativeCreateGame() {    // User not registered
    try {
      AuthData auth = chessService.getUserService().register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));

      assertEquals(1, memoryAuthDAO.listAuths().size(),"AuthDAO should have 1 auth");
      assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 auth");

      int gameID = chessService.getGameService().createGame(new AuthData("bill", "x"), "magnusVsHikaru");
      fail("Should have thrown an exception");

    } catch (Exception e) {
      assertEquals("Error: unauthorized", e.getMessage());
    }
  }

  @Test
  public void positiveJoinGame() {
    try {
      AuthData auth = chessService.getUserService().register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));
      AuthData auth2 = chessService.getUserService().register(new UserData("magnus","hikaru_stinks","magnus@chess.com"));
      int gameID = chessService.getGameService().createGame(auth, "hikaruVsMagnus");
      chessService.getGameService().joinGame(auth2, "WHITE", gameID); // magnus joins as white
      chessService.getGameService().joinGame(auth, "BLACK", gameID);  // hikaru joins as black

      assertEquals(1, chessService.getGameService().listGames(auth).size(), "Should have 1 game");
      assertEquals("hikaru", memoryGameDAO.getGame(gameID).blackUsername());
        assertEquals("magnus", memoryGameDAO.getGame(gameID).whiteUsername());
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
    }
  }

  @Test
  public void negativeJoinGame() {
    try {
      AuthData auth = chessService.getUserService().register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));
      int gameID = chessService.getGameService().createGame(auth, "hikaruVsMagnus");
      chessService.getGameService().joinGame(auth, "red", gameID);        // incorrect player color
      fail("Should have thrown an exception");
    } catch (Exception e) {
      assertEquals("Error: bad request", e.getMessage());
    }
  }

  @Test
  public void positiveListGames() {
    try {
      AuthData auth1 = chessService.getUserService().register(new UserData("hikaru","magnus_stinks","n@gmail.com"));
      String game1 = "hikaruVsMagnus";
      String game2 = "hikaruVsGotham";
      chessService.getGameService().createGame(auth1, game1);
      chessService.getGameService().createGame(auth1, game2);

      Collection<GameData> listOfGames = chessService.getGameService().listGames(auth1);
      StringBuilder output = new StringBuilder();
      String expectedOutput = game1 + "\n" + game2 + "\n";

      for (GameData game : listOfGames) {
        output.append(game.gameName() + "\n");
      }
      String out = output.toString();
      assertEquals(out, expectedOutput, "Printed game names should be the same");
      assertEquals(2, chessService.getGameService().listGames(auth1).size(),"Should have 2 games");
    } catch (Exception e) {
      System.out.println("error: "+e.getMessage());
    }
  }

  @Test
  public void negativeListGames() {
    try {
      AuthData auth = (new AuthData("hikaru"));
      chessService.getGameService().listGames(auth);
      fail("Should have thrown an exception");

    } catch (Exception e) {
      assertEquals("Error: unauthorized", e.getMessage());
    }
  }
}
