package dataaccess;

import model.UserData;
import java.util.Collection;
import java.util.HashMap;

public interface UserDAO {

  public UserData createUser(String username, String password, String email) throws DataAccessException;

  //Collection<UserData> listUsers() throws DataAccessException;

  public UserData getUser(String username) throws DataAccessException;

  public void logoutUser(String username) throws DataAccessException; // ????

  public void deleteAllUsers() throws DataAccessException;

}

