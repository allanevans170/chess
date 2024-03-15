package chess;

import java.util.ArrayList;
import java.util.Collection;
public class MovesPawn extends MoveMaker {
  Collection<ChessMove> pawnMoves = new ArrayList<>();

    Directions[] pawnDirections = {Directions.UP, Directions.UP_RIGHT, Directions.UP_LEFT};
    // en passant ==  Directions.LEFT, Directions.RIGHT

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    for (Directions direction : pawnDirections) {
      if (pawnMover(myPosition, direction, board) != null) {
          pawnMoves.add(pawnMover(myPosition, direction, board));
      }
    }
    return pawnMoves;
  }

    public ChessMove pawnMover(ChessPosition currPos, Directions direction, ChessBoard board) {
        ChessMove pawnMove = null;

        int row=currPos.getRow();
        int col=currPos.getColumn();

        row+=direction.getDeltaRow();
        col+=direction.getDeltaCol();
        if (row < 1 || row > 8 || col < 1 || col > 8) {
          return null;
        }

        if (squareValidate(currPos, new ChessPosition(row, col), board) == 2) {    // empty square
          if (direction == Directions.UP) {
            pawnMove = new ChessMove(currPos, new ChessPosition(row, col), null);
          }
        } else if (squareValidate(currPos, new ChessPosition(row, col), board) == 1) {    // enemy square
          if (direction == Directions.UP_RIGHT || direction == Directions.UP_LEFT) {
            pawnMove = new ChessMove(currPos, new ChessPosition(row, col), null);
          }   // THIS IS WHERE YOU CAN ENABLE EN PASSANT IF YOU'D LIKE TO
        }     // empty square

        return pawnMove;
    }
}
