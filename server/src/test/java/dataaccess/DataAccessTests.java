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
  public void deleteUsersTest() {
    try {
      sqlUserDAO.createUser("goku", "kamehameha", "supersaiyan@gmail.com");

      sqlUserDAO.deleteAllUsers();

      assertNull(sqlUserDAO.getUser("goku"), "Should return null");
    } catch (Exception e) {
      fail("Exception thrown during deleteUsers test: " + e.getMessage());
    }
  }

  @Test
  public void deleteAuthsTest() {
    try {
      AuthData auth = sqlAuthDAO.createAuth("goku");
      AuthData auth2 = sqlAuthDAO.createAuth("vegeta");
      AuthData auth3 = sqlAuthDAO.createAuth("gohan");

      sqlAuthDAO.deleteAllAuths();
      assertNull(sqlAuthDAO.getAuth(auth.authToken()), "Should return null");
      assertNull(sqlAuthDAO.getAuth(auth2.authToken()), "Should return null");
      assertNull(sqlAuthDAO.getAuth(auth3.authToken()), "Should return null");
    } catch (Exception e) {
      fail("Exception thrown during deleteAuths test: " + e.getMessage());
    }
  }

  @Test
  public void deleteGamesTest() {
    try {
      sqlGameDAO.createGame("gokuVsVegeta");
      sqlGameDAO.createGame("magnusVsHikaru");
      sqlGameDAO.deleteAllGames();

      assertTrue(sqlGameDAO.listGames().isEmpty(), "Games table should be empty after clear operation");
    } catch (Exception e) {
      fail("Exception thrown during deleteGames test: " + e.getMessage());
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

      sqlUserDAO.createUser(username, password, email);

      UserData user = sqlUserDAO.getUser(username);

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
  public void positiveGetUser() {
    try {
      String username = "goku";
      String password = "kamehameha";
      String email = "supersaiyan@gmail.com";

      sqlUserDAO.createUser(username, password, email);

      UserData user = sqlUserDAO.getUser(username);

      assertTrue(user.email().equals("supersaiyan@gmail.com"), "Email should match the expected value");

    } catch (Exception e) {
      fail("Exception thrown during positiveCreateUser test: " + e.getMessage());
    }
  }

  @Test
  public void negativeGetUser() {
    try {
      String username="goku";
      String password="kamehameha";
      String email="supersaiyan@gmail.com";

      sqlUserDAO.createUser(username, password, email);

      UserData user = sqlUserDAO.getUser("vegeta");
      assertNull(user, "User should be null if it does not exist");

    } catch (Exception e) {
      fail("Exception thrown during negativeGetUser test: " + e.getMessage());
    }
  }

  @Test
  public void positiveListGames() {
    try {
      sqlGameDAO.createGame("gokuVsVegeta");
      sqlGameDAO.createGame("magnusVsHikaru");
      sqlGameDAO.createGame("carlsenVsNakamura");

      Collection<GameData> gameList = sqlGameDAO.listGames();
      assertEquals(3, gameList.size(), "Game list should contain 3 games");

    } catch (DataAccessException e) {
      fail("Exception thrown during positiveListGames test: " + e.getMessage());
    }
  }
  @Test
  public void negativeListGames() {
    try {
      // no games have been created, so the list should be empty
      Collection<GameData> gameList = sqlGameDAO.listGames();
      assertEquals(0, gameList.size(), "User list should be empty");
    } catch (DataAccessException e) {
      fail("Exception thrown during negativeListGames test: " + e.getMessage());
    }
  }

  @Test
  public void positiveCreateAuth() {
    try {
      AuthData auth = sqlAuthDAO.createAuth("goku");

      assertNotNull(auth, "createAuth should not return null");
      assertTrue(sqlAuthDAO.getAuth(auth.authToken()).username().equals("goku"), "Username should match");

    } catch (Exception e) {
      fail("Exception thrown during positiveCreateUser test: " + e.getMessage());
    }
  }
  @Test
  public void negativeCreateAuth() {  // can't create an auth if the table doesn't exist
    try {
      var conn = DatabaseManager.getConnection();
      var statement = conn.createStatement();
      statement.execute("DROP TABLE IF EXISTS auths");
      AuthData auth = sqlAuthDAO.createAuth("goku");
      fail("Creating an auth without the auths table should throw an exception");
    } catch (Exception e) {
      assertTrue(e.getMessage().contains("unable to update"), "Exception should be thrown because the table does not exist");
    }
    try {
      var conn = DatabaseManager.getConnection();
      var statement = conn.createStatement();
      statement.execute("CREATE TABLE IF NOT EXISTS auths (`authToken` varchar(256) PRIMARY KEY NOT NULL, `username` varchar(256) NOT NULL)");
    } catch (Exception e) {
      fail("Failure to re-create table: " + e.getMessage());
    }
  }

  @Test
  public void positiveGetAuth() {
    try {
      AuthData auth = sqlAuthDAO.createAuth("goku");
      AuthData auth2 = sqlAuthDAO.getAuth(auth.authToken());

      assertNotNull(auth2, "getAuth can't return null if the auth was created successfully");
      assertTrue(auth2.username().equals("goku"), "auth username should match");

    } catch (Exception e) {
      fail("Exception thrown during positiveGetAuth test: " + e.getMessage());
    }
  }

  @Test
  public void negativeGetAuth() {
    try {
      AuthData auth = sqlAuthDAO.createAuth("goku");
      AuthData auth2 = sqlAuthDAO.getAuth("randomToken");

      assertNull(auth2, "getAuth should return null if the auth token does not exist");

    } catch (Exception e) {
      fail("Exception thrown during negativeGetAuth test: " + e.getMessage());
    }
  }

  @Test
  public void positiveDeleteAuth() {
    try {
      AuthData auth = sqlAuthDAO.createAuth("goku");
      sqlAuthDAO.deleteAuth(auth.authToken());
      assertNull(sqlAuthDAO.getAuth(auth.authToken()), "Auth should have been deleted");
    } catch (Exception e) {
      fail("Exception thrown during positiveDeleteAuth test: " + e.getMessage());
    }
  }

  @Test
  public void negativeDeleteAuth() {
    try {
      AuthData auth = sqlAuthDAO.createAuth("goku");
      sqlAuthDAO.deleteAuth("randomToken");
      fail("Deleting an auth with a non-existent token should throw an exception");
    } catch (Exception e) {
      assertTrue(e.getMessage().contains("auth not found"), "Exception should be thrown because the token does not exist");
    }
  }

  @Test
  public void positiveCreateGame() {
    try {
      GameData game = sqlGameDAO.createGame("gokuVsPicolo");

      GameData game2 = sqlGameDAO.getGame(game.getGameID());

      assertNotNull(game2, "Game should not be null");
      //assertEquals(game.getGame(), game2.getGame(), "Games should match");
      assertEquals(game.getGameName(), game2.getGameName(), "Game names should match");
      assertEquals(game.getWhiteUsername(), game2.getWhiteUsername(), "White usernames should match");
      assertEquals(game.getGameID(), game2.getGameID(), "Game IDs should match");

    } catch (Exception e) {
      fail("Exception thrown during positiveCreateGame test: " + e.getMessage());
    }
  }

  @Test
  public void negativeCreateGame() {
    try {
      var conn=DatabaseManager.getConnection();
      var statement=conn.createStatement();
      statement.execute("DROP TABLE IF EXISTS games");
      GameData game=sqlGameDAO.createGame("goku");
      fail("Creating a game without game table should throw an exception");
    } catch (Exception e) {
      assertTrue(e.getMessage().contains("Game not created"), "Exception should be thrown because the table does not exist");
    }
    try {
      var conn=DatabaseManager.getConnection();
      var statement=conn.createStatement();
      String tableCreate="CREATE TABLE IF NOT EXISTS games ( `gameID` INT NOT NULL AUTO_INCREMENT, `whiteUsername` VARCHAR(256) DEFAULT NULL, `blackUsername` VARCHAR(256) DEFAULT NULL, `gameName` VARCHAR(256) NOT NULL, `game` TEXT NOT NULL, PRIMARY KEY (`gameID`))";
      statement.execute(tableCreate);
    } catch (Exception e) {
      fail("Failure to re-create table: " + e.getMessage());
    }
  }

  @Test
  public void positiveGetGame() {
    try {
      GameData game = sqlGameDAO.createGame("professorXVsMagneto");
      game.setWhiteUsername("MLK");
      game.setBlackUsername("MalcolmX");
      sqlGameDAO.updateGame(game);
      GameData game2 = sqlGameDAO.getGame(game.getGameID());

      //assertEquals(game.getGame(), game2.getGame(), "Games should match");
      assertEquals(game.getGameName(), game2.getGameName(), "Game names should match");
      assertEquals("MLK", game2.getWhiteUsername(), "White usernames should match");
      assertEquals("MalcolmX", game2.getBlackUsername(), "Black usernames should match");
      assertEquals(game.getGameID(), game2.getGameID(), "Game IDs should match");

    } catch (Exception e) {
      fail("Exception thrown during positiveGetGame test: " + e.getMessage());
    }
  }

  @Test
  public void negativeGetGame() {
    try {
      GameData game = sqlGameDAO.createGame("gokuVsPicolo");
      GameData game2 = sqlGameDAO.getGame(1000);

      assertNull(game2, "Game should be null if it does not exist");

    } catch (Exception e) {
      fail("Exception thrown during negativeGetGame test: " + e.getMessage());
    }
  }

  @Test
  public void positiveUpdateGame() {
    try {
      GameData game = sqlGameDAO.createGame("gokuVsPicolo");
      game.setWhiteUsername("goku");
      game.setBlackUsername("picolo");
      sqlGameDAO.updateGame(game);

      assertNotNull(game, "Game should not be null");
      assertEquals(game.getGameName(), sqlGameDAO.getGame(game.getGameID()).getGameName(), "Game names should match");
      assertEquals("goku", sqlGameDAO.getGame(game.getGameID()).getWhiteUsername(), "White username = goku");
      assertEquals("picolo", sqlGameDAO.getGame(game.getGameID()).getBlackUsername(), "Black username = goku");

    } catch (Exception e) {
      fail("Exception thrown during positiveUpdateGame test: " + e.getMessage());
    }
  }

  @Test
  public void negativeUpdateGame() {  // set a game that doesn't exist
    try {
        GameData game = new GameData("gokuVsPicolo");
        game.setGameID(1000);
        game.setWhiteUsername("goku");
        game.setBlackUsername("picolo");
        sqlGameDAO.updateGame(game);
        fail("Updating a game that does not exist should throw an exception");
        } catch (Exception e) {
        assertTrue(e.getMessage().contains("Game not found"), "Exception should be thrown because the game does not exist");
    }
  }
}
