package dataaccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ChessService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataAccessTests {
  private SQLAuthDAO sqlAuthDAO;
  private SQLGameDAO sqlGameDAO;
  private SQLUserDAO sqlUserDAO;

  @BeforeEach
  public void setUp() {
    try {
      sqlAuthDAO=new SQLAuthDAO();
      sqlGameDAO=new SQLGameDAO();
      sqlUserDAO=new SQLUserDAO();
    } catch (Exception e) {
      System.out.println("Error: "+e.getMessage());
    }}

  @Test
  public void positiveClear() {
    try {
      ChessService clearService = new ChessService(sqlUserDAO, sqlAuthDAO, sqlGameDAO);

      sqlUserDAO.createUser("goku","kamehameha","supersaiyan@gmail.com");
      sqlGameDAO.createGame("gokuVsVegeta");

      clearService.clear();

      assertEquals(0, sqlAuthDAO.listAuths().size(),"AuthDAO should be empty");
      assertEquals(0, sqlGameDAO.listGames().size(),"GameDAO should be empty");
      assertEquals(0, sqlUserDAO.listUsers().size(),"UserDAO should be empty");
    } catch (Exception e) {
      System.out.println("Error: "+e.getMessage());
    }
  }

}
