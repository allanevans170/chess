package model;

public record UserData(String username, String password, String email) {

  public String toString() {
    return "UserData{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
