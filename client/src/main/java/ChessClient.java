import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public class ChessClient {
  public static void main(String[] args) throws Exception {
    // Specify the desired endpoint
    URI uri = new URI("http://localhost:8080/name");
    HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
    http.setRequestMethod("GET");

    // Make the request
    http.connect();

    // Output the response body
    try (InputStream respBody = http.getInputStream()) {
      InputStreamReader inputStreamReader = new InputStreamReader(respBody);
      System.out.println(new Gson().fromJson(inputStreamReader, Map.class));
    }
  }


  // pre-login UI

  // - help
  // - quit
  // - login
  // - register


  // post-login UI
  // - help
  // - logout
  // - create games
  // - list games
  // - play game
  // - observe game
}
