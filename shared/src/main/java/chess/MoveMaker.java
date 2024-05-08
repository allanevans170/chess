package chess;

import java.util.ArrayList;
import java.util.Collection;

public abstract class MoveMaker {
  //Collection<ChessMove> directionalMoves=new ArrayList<>();

  public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);

  enum Directions {
    UP(1,0),
    DOWN(-1,0),
    LEFT(0,-1),
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
    //UP2 (0, 2);

    private final int deltaRow;
    private final int deltaCol;

    Directions(int deltaRow, int deltaCol) {
      this.deltaRow=deltaRow;
      this.deltaCol=deltaCol;
    }

    public int getDeltaCol() {
      return deltaCol;
    }

    public int getDeltaRow() {
      return deltaRow;
    }
  }

  // I need an omnidirectionalMoves method that takes in a direction and a position and returns a collection of moves
  Collection<ChessMove> omniDirectionalMover(ChessPosition currPos, Directions direction, ChessBoard board) {
    Collection<ChessMove> directionalMoves = new ArrayList<>();
    //for rook, bishop, queen
    int col=currPos.getColumn();
    int row=currPos.getRow();

    while (true) {
      col+=direction.getDeltaCol();
      row+=direction.getDeltaRow();
      if (row < 1 || row > 8 || col < 1 || col > 8) {
        break;
      }
      if (squareValidate(currPos, new ChessPosition(row, col), board) == 0) {    // friendly
        break;
      }
      directionalMoves.add(new ChessMove(currPos, new ChessPosition(row, col), null));
      if (squareValidate(currPos, new ChessPosition(row, col), board) == 1) {     // enemy
        break;
      }
    }
    return directionalMoves;
  }

  ChessMove moveHelper(ChessPosition currPos, Directions direction, ChessBoard board) {   // for king, knight
    ChessMove newMove = null;
    int row=currPos.getRow();
    int col=currPos.getColumn();

    row+=direction.getDeltaCol();
    col+=direction.getDeltaRow();

    if (row < 1 || row > 8 || col < 1 || col > 8) {
      return newMove;
    }
    if (squareValidate(currPos, new ChessPosition(row, col), board) == 0) {    // friendly
      return newMove;
    }
    newMove = new ChessMove(currPos, new ChessPosition(row, col), null);
    return newMove;
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






