package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import dataaccess.*;

public class ClearServiceTest {
  private MemoryAuthDAO memoryAuthDAO;
  private MemoryGameDAO memoryGameDAO;
  private MemoryUserDAO memoryUserDAO;
  private ChessService clearService;

  @BeforeEach
  public void setUp() {
    memoryAuthDAO = new MemoryAuthDAO();
    memoryGameDAO = new MemoryGameDAO();
    memoryUserDAO = new MemoryUserDAO();
    clearService = new ChessService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);

    try {
      memoryAuthDAO.createAuth("yoloChessboi69");
      memoryGameDAO.createGame(24, "magnusVsHikaru");
      memoryUserDAO.createUser("hikaruStickaru","magnus_stinks","hikaru@chess.com");
    } catch (Exception e) {
      System.out.println("Error: "+e.getMessage());
    }
  }
  @Test
  public void positiveClear() {
    try {
      clearService.clear();

      assertEquals(0, memoryAuthDAO.listAuths().size(),"AuthDAO should be empty");
      assertEquals(0, memoryGameDAO.listGames().size(),"GameDAO should be empty");
      assertEquals(0, memoryUserDAO.listUsers().size(),"UserDAO should be empty");

    } catch (Exception e) {
      System.out.println("Error: "+e.getMessage());
    }
  }
}