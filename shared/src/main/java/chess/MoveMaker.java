package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MoveMaker {
  Collection<ChessMove> directionalMoves=new ArrayList<ChessMove>();

  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    throw new RuntimeException("Not implemented - in MoveMaker");
  }

  enum Directions {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_LEFT(1, -1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(-1, 1);

    private final int deltaX;
    private final int deltaY;

    Directions(int deltaX, int deltaY) {
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

  //boolean isEnemyPiece(ChessPiece piece, ChessGame.TeamColor teamColor) {
  //return piece.getTeamColor() != this.teamColor;
  //}

  //boolean isFriendlyPiece(ChessPiece piece, ChessGame.TeamColor teamColor) {
  //  return piece.getTeamColor() == teamColor;
  //}

  // I need an omnidirectionalMoves method that takes in a direction and a position and returns a collection of moves
  Collection<ChessMove> omniDirectionalMover(ChessPosition currPos, Directions direction) {    // for rook, bishop, queen

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
      directionalMoves.add(new ChessMove(currPos, new ChessPosition(x, y), null));
      //squareChecker(pos);
    }
    return directionalMoves;
  }
}
  // put knight, pawn, and king moves in their own classes (maybe they should still use directions enum

  /*void squareChecker(ChessBoard board, ChessPosition currPos, ChessPosition pos, ChessGame.TeamColor teamColor) {
    if (board.getPiece(pos) == null) {
      availableMoves.add(new ChessMove(currPos, pos, null));
    } else if (isEnemyPiece(board.getPiece(pos), teamColor)) {
      availableMoves.add(new ChessMove(currPos, pos, null));
    } else {
    }
  }

  boolean squareValidator(ChessBoard board, ChessPosition currPos, ChessPosition endPosition, ChessGame.TeamColor teamColor) {
    if (board.getPiece(endPosition) == null) {
      availableMoves.add(new ChessMove(currPos, endPosition, null));
      return true;
    } else if (isEnemyPiece(board.getPiece(endPosition), teamColor)) {
      availableMoves.add(new ChessMove(currPos, endPosition, null));
      return false;
    } else {
      return false;
    }
  }*/

  /*
  void verticalMoves(ChessPosition currPos) {
    for (int i =currPos.getRow() +1; i <= 8; i++) {

    }

    for (int i = currPos.getRow()-1; i >=1; i--) {

    }
    for (int i = 1; i <= 8; i++) {
      if (currPos.getRow() == i) {
        continue;
      }
      ChessPosition pos = new ChessPosition(i, currPos.getColumn());
      if (endPosition == true) {
        availableMoves.add(new ChessMove(currPos, pos, null));
      } else {
        break;
      }
    }
  }

  void horizontalMoves(ChessPosition currPos) {
    for (int i = 1; i <= 8; i++) {
      if (currPos.getColumn() == i) {
        continue;
      }
      ChessPosition pos = new ChessPosition(currPos.getRow(), i);
      if (board.getPiece(pos) == null) {
        availableMoves.add(new ChessMove(currPos, pos, null));
      } if (board.getPiece(pos) != null) {
        board.
      }else {
        break;
      }
    }

  }

  void diagonalMoves(ChessPosition currPos) {
    int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    for (int[] d : directions) {
      for (int i = 1; i <= 8; i++) {
        int endRow = currPos.getRow() + d[0] * i;
        int endCol = currPos.getColumn() + d[1] * i;
        if (endRow <= 1 || endRow >= 8 || endCol <= 1 || endCol >= 8) {
          break;
        }
        ChessPosition pos = new ChessPosition(endRow, endCol);
        if (board.getPiece(pos) == null) {
          availableMoves.add(new ChessMove(currPos, pos, null));
        } else {
          break;
        }
      }
    }
  }

  void knightMoves() {

  }
  */

