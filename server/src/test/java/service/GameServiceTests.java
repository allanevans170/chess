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

      GameData game = chessService.getGameService().createGame(auth.authToken(), "magnusVsHikaru");

      assertEquals(1, memoryGameDAO.listGames().size(),"GameDAO should have 1 game");
      assertEquals("magnusVsHikaru", game.getGameName());

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

      GameData game = chessService.getGameService().createGame("Billy'sFalseToken", "magnusVsHikaru");
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
      GameData game = chessService.getGameService().createGame(auth.authToken(), "hikaruVsMagnus");
      chessService.getGameService().joinGame(auth2.authToken(), "WHITE", game.getGameID()); // magnus joins as white
      chessService.getGameService().joinGame(auth.authToken(), "BLACK", game.getGameID());  // hikaru joins as black

      assertEquals(1, chessService.getGameService().listGames(auth.authToken()).size(), "Should have 1 game");
      assertEquals("hikaru", memoryGameDAO.getGame(game.getGameID()).getBlackUsername());
      assertEquals("magnus", memoryGameDAO.getGame(game.getGameID()).getWhiteUsername());
    } catch (Exception e) {
      System.out.println("error: " + e.getMessage());
    }
  }

  @Test
  public void negativeJoinGame() {
    try {
      AuthData auth = chessService.getUserService().register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));
      GameData game = chessService.getGameService().createGame(auth.authToken(), "hikaruVsMagnus");
      chessService.getGameService().joinGame(auth.authToken(), "red", game.getGameID());        // incorrect player color
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
      chessService.getGameService().createGame(auth1.authToken(), game1);
      chessService.getGameService().createGame(auth1.authToken(), game2);

      Collection<GameData> listOfGames = chessService.getGameService().listGames(auth1.authToken());
      StringBuilder output = new StringBuilder();
      String expectedOutput = game1 + "\n" + game2 + "\n";

      for (GameData game : listOfGames) {
        output.append(game.getGameName() + "\n");
      }
      String out = output.toString();
      assertEquals(out, expectedOutput, "Printed game names should be the same");
      assertEquals(2, chessService.getGameService().listGames(auth1.authToken()).size(),"Should have 2 games");
    } catch (Exception e) {
      System.out.println("error: "+e.getMessage());
    }
  }

  @Test
  public void negativeListGames() {
    try {
      AuthData auth = (new AuthData("hikaru"));
      chessService.getGameService().listGames(auth.authToken());
      fail("Should have thrown an exception");

    } catch (Exception e) {
      assertEquals("Error: unauthorized", e.getMessage());
    }
  }
}
