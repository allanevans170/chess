package service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import dataaccess.*;
import model.*;
public class GameServiceTests {
  private MemoryAuthDAO memoryAuthDAO;
  private MemoryUserDAO memoryUserDAO;
  private MemoryGameDAO memoryGameDAO;
  private ChessService chessService;

  @BeforeEach
  public void setUp() {
    memoryAuthDAO = new MemoryAuthDAO();
    memoryUserDAO = new MemoryUserDAO();
    memoryGameDAO = new MemoryGameDAO();
    chessService = new ChessService(memoryUserDAO, memoryAuthDAO, memoryGameDAO);
  }


}
