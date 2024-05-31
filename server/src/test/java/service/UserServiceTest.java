package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import dataaccess.*;
import model.*;

public class UserServiceTest {
  private MemoryAuthDAO memoryAuthDAO;
  private MemoryUserDAO memoryUserDAO;
  private UserService userService;

  @BeforeEach
  public void setUp() {
    memoryAuthDAO = new MemoryAuthDAO();
    memoryUserDAO = new MemoryUserDAO();
    userService = new UserService(memoryUserDAO, memoryAuthDAO);
  }
  @Test
  public void positiveRegister() {
    try {
      userService.register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));

      assertEquals(1, memoryAuthDAO.listAuths().size(),"AuthDAO should have 1 auth");
      assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 auth");

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
      assertEquals(1, memoryAuthDAO.listAuths().size(), "AuthDAO should have 1 auth");
      assertEquals(1, memoryUserDAO.listUsers().size(), "UserDAO should have 1 auth");
    } catch (DataAccessException e) {
      System.out.println("error: "+e.getMessage());
    }
  }

  @Test
  public void positiveLogin() {
    try {
      userService.register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));
      assertEquals(1, memoryAuthDAO.listAuths().size(),"AuthDAO should have 1 auth");
      assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 auth");

      userService.login(new UserData("hikaru","magnus_stinks", ""));
      assertEquals(2, memoryAuthDAO.listAuths().size(),"AuthDAO should have 2 auth");
      assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 user");

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
      AuthData auth2 = userService.login(new UserData("gotham","youtuber",""));

      assertEquals(2, memoryAuthDAO.listAuths().size(),"AuthDAO should have 2 auths");
      assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 user");

      userService.logout(auth1);
      assertEquals(1, memoryAuthDAO.listAuths().size(),"AuthDAO should have 1 auths");
      userService.logout(auth2);
      assertEquals(0, memoryAuthDAO.listAuths().size(),"AuthDAO should have 0 auths");
      assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 user");

    } catch (Exception e) {
      System.out.println("error: "+e.getMessage());
    }
  }

  @Test
  public void negativeLogout() {
    try {
      AuthData auth1 = new AuthData("hikaru");
      userService.logout(auth1);
      fail("Should have thrown an exception");

    } catch (Exception e) {
      assertEquals("Error: unauthorized", e.getMessage());
    }
  }
}