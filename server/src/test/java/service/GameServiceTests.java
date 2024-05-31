package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
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
  public void negativeCreateGame() {

  }

  @Test
  public void positiveJoinGame() {

  }

  @Test
  public void negativeJoinGame() {

  }

  @Test
  public void positiveListGames() {

  }

  @Test
  public void negativeListGames() {

  }
}
