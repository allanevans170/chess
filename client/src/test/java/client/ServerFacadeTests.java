package client;

import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;
import model.*;
import server.ServerFacadeException;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {
    private static Server server;
    private static ServerFacade serverFacade;
    //static ChessClient client;

    @BeforeAll
    public static void init() {
        server = new Server();
        int port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        //client = new ChessClient("http://localhost:" + port);
        serverFacade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    public void setup() {
        // Clear the database
        try {
            serverFacade.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    public void positiveRegister() {
        System.out.println("positiveRegister test");
        UserData testUser = new UserData("magnus", "password8", "email@gmail.com");
        AuthData result = assertDoesNotThrow(() -> serverFacade.register(testUser));
        assertTrue(result.authToken().length() > 10);
        assertTrue(result.username().equals("magnus"));
    }

    @Test
    public void negativeRegister() {
        System.out.println("negativeRegister test");
        try {
            UserData testUser = new UserData("magnus", "password8", "email@gmail.com");
            AuthData result = assertDoesNotThrow(() -> serverFacade.register(testUser));
            UserData duplicateUser = new UserData("magnus", "other_pass", "email@random.com");
            AuthData result2 = serverFacade.register(duplicateUser);
            fail("Expected ServerFacadeException");
        } catch(ServerFacadeException e) {
            assertTrue(e.getMessage().equals("failure: 403"));
        }
    }

    @Test
    public void positiveLogin() {
        System.out.println("positiveLogin test");
        UserData testUser = new UserData("magnus", "password8", "email@gmail.com");
        AuthData result = assertDoesNotThrow(() -> serverFacade.register(testUser));
        assertDoesNotThrow(() -> serverFacade.logout(result.authToken()));

        UserData testUserLogin = new UserData("magnus", "password8", "");
        AuthData result2 = assertDoesNotThrow(() -> serverFacade.login(testUser));
        assertTrue(result.authToken().length() > 10);
        assertTrue(result.username().equals("magnus"));
    }

    @Test
    public void negativeLogin() {
        System.out.println("negativeLogin test - wrong password");
        UserData testUser = new UserData("magnus", "password8", "email@gmail.com");
        AuthData result = assertDoesNotThrow(() -> serverFacade.register(testUser));
        assertDoesNotThrow(() -> serverFacade.logout(result.authToken()));

        UserData testUserLogin = new UserData("magnus", "password9", "");
        try {
            AuthData result2 = serverFacade.login(testUserLogin);
            fail("Expected ServerFacadeException - wrong password");
        } catch(ServerFacadeException e) {
            assertTrue(e.getMessage().equals("failure: 401"));
        }
    }

    @Test
    public void positiveLogout() {
        System.out.println("positiveLogout test");
        UserData testUser = new UserData("magnus", "password8", "an@gmail.com");
        AuthData result = assertDoesNotThrow(() -> serverFacade.register(testUser));
        assertDoesNotThrow(() -> serverFacade.logout(result.authToken()));
        try {
            serverFacade.joinGame(result.authToken(), new JoinGameRequest("white", 1));
            fail("Expected ServerFacadeException due to deleted authToken");
        } catch (ServerFacadeException e) {
            assertTrue(e.getMessage().equals("failure: 401"));
        }
    }

    @Test
    public void negativeLogout() {
        System.out.println("positiveLogout test -trying to logout twice");
        UserData testUser = new UserData("magnus", "password8", "an@gmail.com");
        AuthData result = assertDoesNotThrow(() -> serverFacade.register(testUser));
        assertDoesNotThrow(() -> serverFacade.logout(result.authToken()));
        try {
            serverFacade.logout(result.authToken());
            fail("Expected ServerFacadeException");
        } catch(ServerFacadeException e) {
            assertTrue(e.getMessage().equals("failure: 401"));
        }
    }

    @Test
    public void positiveCreateGame() {
        System.out.println("positiveCreateGame test");
        UserData testUser = new UserData("magnus", "password8", "email@chess.com");
        AuthData result = assertDoesNotThrow(() -> serverFacade.register(testUser));
        GameData game = new GameData("game1");
        assertDoesNotThrow(() -> serverFacade.createGame(result.authToken(), game));
        try {
            Collection<GameData> gamesList = serverFacade.listGames(result.authToken());
            assertTrue(gamesList.size() == 1);
        } catch (ServerFacadeException e) {
            fail("ServerFacadeException" + e.getMessage());
        }
    }
    @Test
    public void negativeCreateGame() {
        System.out.println("negative createGame test - unauthorized user");
        AuthData unauthorizedUser = new AuthData("badToken", "badUser");
        GameData game = new GameData("game1");
        try {
            serverFacade.createGame(unauthorizedUser.authToken(), game);
            fail("Expected ServerFacadeException");
        } catch (ServerFacadeException e) {
            assertTrue(e.getMessage().equals("failure: 401"));
        }
    }
    @Test
    public void positiveListGames() {
        System.out.println("positive list game test");
        UserData testUser = new UserData("magnus", "password8", "email@chess.com");
        AuthData result = assertDoesNotThrow(() -> serverFacade.register(testUser));
        GameData game = new GameData("game1");
        GameData game2 = new GameData("game2");
        GameData game3 = new GameData("game3");
        assertDoesNotThrow(() -> serverFacade.createGame(result.authToken(), game));
        assertDoesNotThrow(() -> serverFacade.createGame(result.authToken(), game2));
        assertDoesNotThrow(() -> serverFacade.createGame(result.authToken(), game3));
        try {
            Collection<GameData> gamesList = serverFacade.listGames(result.authToken());
            assertTrue(gamesList.size() == 3);
        } catch (ServerFacadeException e) {
            fail("ServerFacadeException" + e.getMessage());
        }
    }
    @Test
    public void negativeListGames() {
        System.out.println("negative list Game test - unauthorized user");
        AuthData unauthorizedUser = new AuthData("badToken", "badUser");
        try {
            serverFacade.listGames(unauthorizedUser.authToken());
            fail("Expected ServerFacadeException");
        } catch (ServerFacadeException e) {
            assertTrue(e.getMessage().equals("failure: 401"));
        }
    }
    @Test
    public void positiveJoinGame() {

    }
    @Test
    public void negativeJoinGame() {

    }
}
