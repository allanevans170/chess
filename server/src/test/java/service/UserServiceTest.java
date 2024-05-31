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

//    try {
//      memoryAuthDAO.createAuth("yoloChessboi69");
//      memoryUserDAO.createUser("hikaru","magnus_stinks","hikaru@chess.com");
//    } catch (Exception e) {
//      System.out.println("Exception caught in UserServiceTest setup");
//    }
  }
  @Test
  public void positiveRegister() {
    try {
      userService.register(new UserData("hikaru","magnus_stinks","hikaru@chess.com"));

      assertEquals(1, memoryAuthDAO.listAuths().size(),"AuthDAO should have 1 auth");
      assertEquals(1, memoryUserDAO.listUsers().size(),"UserDAO should have 1 auth");

    } catch (Exception e) {
      System.out.println("caught an exception in the positiveRegisterTest");
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
      System.out.println("caught an exception in the negativeRegisterTest");
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
      System.out.println("caught an exception in the positiveRegisterTest");
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
}