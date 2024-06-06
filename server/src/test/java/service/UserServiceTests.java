package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import dataaccess.*;
import model.*;

public class UserServiceTests {
  private MemoryAuthDAO sqlAuthDAO;
  private MemoryUserDAO sqlUserDAO;
  private UserService userService;

  @BeforeEach
  public void setUp() {
    try {
      sqlAuthDAO = new MemoryAuthDAO();
      sqlUserDAO = new MemoryUserDAO();
      userService = new UserService(sqlUserDAO, sqlAuthDAO);
    } catch (Exception e) {
      System.out.println("Error: "+e.getMessage());
    }
  }
  @Test
  public void positiveRegister() {
    try {
      AuthData auth = userService.register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));

      assertTrue(sqlAuthDAO.getAuth(auth.authToken()) != null, "Should not return null");
      assertTrue(sqlUserDAO.getUser("hikaru") != null, "Should not return null");

      assertNull(sqlUserDAO.getUser("HikaruStickaru"), "Should return null");

    } catch (Exception e) {
      System.out.println("error: "+e.getMessage());
    }
  }
  @Test
  public void negativeRegister() {
    try {
      userService.register(new UserData("hikaru","password","email@gmail.com"));
      userService.register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));
      fail("Should have thrown an exception");

    } catch (ServiceException e) {
      assertEquals("Error: already taken", e.getMessage());
    }
    try {
      assertTrue(sqlUserDAO.getUser("hikaru").email() == "email@gmail.com", "Should return first email input");
    } catch (DataAccessException e) {
      System.out.println("error: "+e.getMessage());
    }
  }

  @Test
  public void positiveLogin() {
    try {
      userService.register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));
      //assertEquals(1, memoryAuthDAO.listAuths().size(),"AuthDAO should have 1 auth");
      //assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 auth");

      userService.login(new UserData("hikaru","magnus_stinks", ""));
      //assertEquals(2, memoryAuthDAO.listAuths().size(),"AuthDAO should have 2 auth");
      //assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 user");

    } catch (Exception e) {
      System.out.println("error: "+e.getMessage());
    }
  }

  @Test
  public void negativeLogin() {
    try {
      userService.register(new UserData("hikaru", "password", "email@gmail.com"));
      userService.register(new UserData("gotham", "youtuber", "gotham@yahoo.com"));

      userService.login(new UserData("hiKaru", "password", ""));
      fail("Should have thrown an exception");

    } catch (ServiceException e) {
      assertEquals("Error: unauthorized", e.getMessage());
    }
  }

  @Test
  public void positiveLogout() {
    try {
      AuthData auth1 = userService.register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));
      AuthData auth2 = userService.login(new UserData("hikaru","magnus_stinks",""));

      //assertEquals(2, memoryAuthDAO.listAuths().size(),"AuthDAO should have 2 auths");
      //assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 user");

      userService.logout(auth1.authToken());
      //assertEquals(1, memoryAuthDAO.listAuths().size(),"AuthDAO should have 1 auths");
      userService.logout(auth2.authToken());
      //assertEquals(0, memoryAuthDAO.listAuths().size(),"AuthDAO should have 0 auths");
      //assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 user");

    } catch (Exception e) {
      System.out.println("error: "+e.getMessage());
    }
  }

  @Test
  public void negativeLogout() {
    try {
      AuthData auth1 = new AuthData("hikaru");
      userService.logout(auth1.authToken());
      fail("Should have thrown an exception");

    } catch (Exception e) {
      assertEquals("Error: unauthorized", e.getMessage());
    }
  }
}