package server;

import com.google.gson.Gson;
import model.*;
import java.io.*;
import java.net.*;
import java.util.Collection;

public class ServerFacade {
  private final String serverUrl;
  public ServerFacade(String url) {
    serverUrl = url;
  }

  public AuthData register(UserData user) throws ServerFacadeException {
    return makeRequest("POST", "/user",null, user, AuthData.class);
  }
  public AuthData login(UserData user) throws ServerFacadeException {
      return makeRequest("POST", "/session", null, user, AuthData.class);
  }
  public void logout(String authToken) throws ServerFacadeException {
    makeRequest("DELETE", "/session", authToken, null, null);
  }
  public void clear() throws ServerFacadeException {
    makeRequest("DELETE", "/db", null, null,null);
  }

  public void createGame(String authToken, GameData game) throws ServerFacadeException {
      makeRequest("POST", "/game", authToken, game, GameData.class);
  }
  public Collection<GameData> listGames(String authToken) throws ServerFacadeException {
    return makeRequest("GET", "/game", authToken, null, ListGames.class).games();
  }

  public GameData joinGame(String authToken, JoinGameRequest joiner) throws ServerFacadeException {
    return makeRequest("PUT", "/game", authToken, joiner, null);
  }

  private <T> T makeRequest(String method, String path, Object header, Object request, Class<T> responseClass) throws ServerFacadeException {
    try {
      URL url = (new URI(serverUrl + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);
      if (header != null) {
        writeHeader(header, http);
      }
      writeBody(request, http);
      http.connect();
      throwIfNotSuccessful(http);
      return readBody(http, responseClass);
    } catch (Exception ex) {
      throw new ServerFacadeException(500, ex.getMessage());
    }
  }
  private static void writeHeader(Object header, HttpURLConnection http) throws IOException {
    if (header != null) {
      http.addRequestProperty("Authorization", header.toString());
    }
  }
  private static void writeBody(Object request, HttpURLConnection http) throws IOException {
    if (request != null) {
      http.addRequestProperty("Content-Type", "application/json");
      String reqData = new Gson().toJson(request);
      try (OutputStream reqBody = http.getOutputStream()) {
        reqBody.write(reqData.getBytes());
      }
    }
  }
  private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ServerFacadeException {
    var status = http.getResponseCode();
    if (!isSuccessful(status)) {
      throw new ServerFacadeException(status, "failure: " + status);
    }
  }
  private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
    T response = null;
    if (http.getContentLength() < 0) {
      try (InputStream respBody = http.getInputStream()) {
        InputStreamReader reader = new InputStreamReader(respBody);
        if (responseClass != null) {
          response = new Gson().fromJson(reader, responseClass);
        }
      }
    }
    return response;
  }
  private boolean isSuccessful(int status) {
    return status / 100 == 2;
  }
}
