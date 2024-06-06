package dataaccess;

import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataAccessTests {
  private SQLAuthDAO sqlAuthDAO;
  private SQLGameDAO sqlGameDAO;
  private SQLUserDAO sqlUserDAO;

  @BeforeAll
  public void setUp() {
    try {
      //dropDatabaseIfExists();
      sqlAuthDAO=new SQLAuthDAO();
      sqlGameDAO=new SQLGameDAO();
      sqlUserDAO=new SQLUserDAO();
    } catch (Exception e) {
      System.out.println("Error: "+e.getMessage());
    }
  }
  @BeforeEach
  public void resetDatabase() {
    try {
      sqlAuthDAO.deleteAllAuths();
      sqlUserDAO.deleteAllUsers();
      sqlGameDAO.deleteAllGames();
    } catch (Exception e) {
      System.out.println("Error: "+e.getMessage());
    }
  }
  @Test
  public void positiveClear() {
    try {
      sqlUserDAO.createUser("goku", "kamehameha", "supersaiyan@gmail.com");
      AuthData auth = sqlAuthDAO.createAuth("goku");
      sqlGameDAO.createGame("gokuVsVegeta");

      sqlAuthDAO.deleteAllAuths();
      sqlUserDAO.deleteAllUsers();
      sqlGameDAO.deleteAllGames();

      assertNull(sqlAuthDAO.getAuth(auth.authToken()), "Should return null");
      assertNull(sqlUserDAO.getUser("goku"), "Should return null");
      assertTrue(sqlGameDAO.listGames().isEmpty(), "Games table should be empty after clear operation");
    } catch (Exception e) {
      fail("Exception thrown during positveClear test: " + e.getMessage());
    }
  }

  boolean verifyUser(String username, String providedClearTextPassword) {
    var hashedPassword = "";
    try {
      // read the previously hashed password from the database
      hashedPassword=sqlUserDAO.getUser(username).password();
    } catch (DataAccessException e) {
      System.out.println("Error in password pulldown: "+e.getMessage());
    }

    return BCrypt.checkpw(providedClearTextPassword, hashedPassword);
  }

  @Test
  public void positiveCreateUser() {
    try {
      String username = "goku";
      String password = "kamehameha";
      String email = "supersaiyan@gmail.com";

      // Create a new user
      sqlUserDAO.createUser(username, password, email);

      // Retrieve the user
      UserData user = sqlUserDAO.getUser(username);

      // Verify the user was created successfully
      assertNotNull(user, "User should not be null after creation");
      assertEquals(username, user.username(), "Username should match the expected value");;
      boolean passwordMatch = verifyUser(username, password);
      assertTrue(passwordMatch, "Password should match the expected value");
      assertEquals(email, user.email(), "Email should match the expected value");

    } catch (Exception e) {
      fail("Exception thrown during positiveCreateUser test: " + e.getMessage());
    }
  }

  @Test
  public void negativeCreateUser() {  // duplicate username
    try {
      String username="goku";
      String password="kamehameha";
      String email="supersaiyan@gmail.com";

      // Create the first user
      sqlUserDAO.createUser(username, password, email);

      assertThrows(DataAccessException.class, () -> {
        sqlUserDAO.createUser(username, "differentPassword", "differentEmail@gmail.com");
      }, "Creating a user with a duplicate username should throw a DataAccessException");

    } catch (Exception e) {
      fail("Exception thrown during negativeCreateUser test: " + e.getMessage());
    }
  }

  @Test
  public void negativeListGames() {   //
    try {
      Collection<GameData> gameList = sqlGameDAO.listGames();
      assertEquals(0, gameList.size(), "User list should be empty");
    } catch (DataAccessException e) {
      fail("Exception thrown during negativeListGames test: " + e.getMessage());
    }
  }
}
