package client;

import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;
import model.*;

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
        //makeRequest("DELETE", "/db", null, null);    }
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
        // test positive registration by creating a new account
        //var result = assertDoesNotThrow(() -> client.preLogin("register magnus password8 magnus@gmail.com"));
        //assertTrue(result.equals("You created the account: magnus\n"));

        //result = assertDoesNotThrow(() -> client.preLogin("register hikaru pass99 magnus@gmail.com"));
        //assertTrue(result.equals("You created the account: hikaru\n"));
        UserData testUser = new UserData("magnus", "password8", "email@gmail.com");
        AuthData result = assertDoesNotThrow(() -> serverFacade.register(testUser));
        assertTrue(result.authToken().length() > 10);
        assertTrue(result.username().equals("magnus"));
    }

    @Test
    public void negativeRegister() {
        System.out.println("negativeRegister test");
        // test negative registration by creating an account with the same username
//        var result = assertDoesNotThrow(() -> client.preLogin("register magnus password8 magnus@gmail.com"));
//        assertTrue(result.equals("You created the account: magnus\n"));
//
//        result = assertDoesNotThrow(() -> client.preLogin("register magnus pass99 magnus@gmail.com"));
//        assertTrue(result.equals("failure: 403"));
        UserData testUser = new UserData("magnus", "password8", "email@gmail.com");
        AuthData result = assertDoesNotThrow(() -> serverFacade.register(testUser));

        assertTrue(result.authToken().length() > 10);
        assertTrue(result.username().equals("magnus"));
    }

    @Test
    public void positiveLogin() {
        System.out.println("positiveLogin test");
        // test positive login by logging in with a valid account
//        client.preLogin("register magnus password8 magnus@gmail.com");
//        var result = assertDoesNotThrow(() -> client.preLogin("login magnus password8"));
//        assertTrue(result.equals("You signed in as magnus\n"));
    }

    @Test
    public void negativeLogin() {
        System.out.println("negativeLogin test");
        // test negative login by logging in with an incorrect password
        client.preLogin("register magnus password8 magnus@gmail.com");
        var result = client.preLogin("login magnus wrongpassword");
        assertTrue(result.equals("failure: 401"));
    }

    @Test
    public void positiveLogout() {
        System.out.println("positiveLogout test");
        client.preLogin("register magnus password8 magnus@gmail.com");
        client.preLogin("logout");

    }

    @Test
    public void negativeLogout() {

    }
//    @Test
//    void register() throws Exception {
//        var authData = facade.register("player1", "password", "p1@email.com");
//        assertTrue(authData.authToken().length() > 10);
//    }
}
