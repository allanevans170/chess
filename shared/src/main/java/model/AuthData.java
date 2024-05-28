package model;

import java.util.UUID;

public record AuthData(String username, String authToken) {

  public AuthData(String username) {
    this(username, UUID.randomUUID().toString());
//      if (username == null || username.isBlank()) { throw new IllegalArgumentException("username cannot be null or empty"); }
  }

  public String toString() {
    return "AuthData{" +
            "authToken='" + authToken + '\'' +
            ", username='" + username + '\'' +
            '}';
  } // I'm not sure if I a toString method is necessary for this class
}
