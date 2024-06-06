package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import dataaccess.*;

public class ClearServiceTest {
  private SQLAuthDAO sqlAuthDAO;
  private SQLGameDAO sqlGameDAO;
  private SQLUserDAO sqlUserDAO;
  private ChessService clearService;

  @BeforeEach
  public void setUp() {
    try {
      sqlAuthDAO = new SQLAuthDAO();
      sqlGameDAO = new SQLGameDAO();
      sqlUserDAO = new SQLUserDAO();
      clearService = new ChessService(sqlUserDAO, sqlAuthDAO, sqlGameDAO);

      sqlAuthDAO.createAuth("yoloChessboi69");
      sqlGameDAO.createGame("magnusVsHikaru");
      sqlUserDAO.createUser("hikaruStickaru","magnus_stinks","hikaru@chess.com");
    } catch (Exception e) {
      System.out.println("Error: "+e.getMessage());
    }
  }
  @Test
  public void positiveClear() {
    try {
      clearService.clear();

      assertNull(sqlAuthDAO.getAuth("yoloChessboi69"), "Should return null");
      assertNull(sqlUserDAO.getUser("HikaruStickaru"), "Should return null");
      assertEquals(0, sqlGameDAO.listGames().size(),"GameDAO should be empty");

    } catch (Exception e) {
      fail("Exception thrown during positveClear test: " + e.getMessage());
    }
  }
}