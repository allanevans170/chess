package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class MoveMaker {
  Collection<ChessMove> directionalMoves=new ArrayList<>();

  public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);

  enum Directions {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_LEFT(1, -1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(-1, 1),
    RIGHT2_UP(1, 2),
    RIGHT2_DOWN(-1, 2),
    LEFT2_UP(1, -2),
    LEFT2_DOWN(-1, -2),
    UP2_LEFT(2, -1),
    UP2_RIGHT(2, 1),
    DOWN2_LEFT(-2, -1),
    DOWN2_RIGHT(-2, 1);

    private final int deltaX;
    private final int deltaY;

    Directions(int deltaY, int deltaX) {
      this.deltaX=deltaX;
      this.deltaY=deltaY;
    }

    public int getDeltaX() {
      return deltaX;
    }

    public int getDeltaY() {
      return deltaY;
    }
  }

  // I need an omnidirectionalMoves method that takes in a direction and a position and returns a collection of moves
  Collection<ChessMove> omniDirectionalMover(ChessPosition currPos, Directions direction, ChessBoard board) {    // for rook, bishop, queen

    int deltaX=direction.getDeltaX();     // Initializing x and y
    int deltaY=direction.getDeltaY();

    int y=currPos.getRow();
    int x=currPos.getColumn();
    while (true) {
      x+=deltaX;
      y+=deltaY;
      if (x < 1 || x > 8 || y < 1 || y > 8) {
        break;
      }
      if (squareValidate(currPos, new ChessPosition(x, y), board) == 0) {    // friendly
        break;
      }
      directionalMoves.add(new ChessMove(currPos, new ChessPosition(x, y), null));
      if (squareValidate(currPos, new ChessPosition(x, y), board) == 1) {     // enemy
        break;
      }
    }
    return directionalMoves;
  }

  int squareValidate(ChessPosition currPos, ChessPosition square, ChessBoard board) {
    if (board.getPiece(square) != null) {
      if (board.getPiece(currPos).getTeamColor() == board.getPiece(square).getTeamColor()) {  // friendly piece
        return 0;
      } else {    // if the piece is an enemy piece
        return 1;
      }
    } else {
      return 2;
    }
  }
}






