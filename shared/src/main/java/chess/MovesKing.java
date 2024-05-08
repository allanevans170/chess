package chess;

import java.util.Collection;
import java.util.ArrayList;
public class MovesKing extends MoveMaker {
  Collection<ChessMove> kingMoves = new ArrayList<>();

  Directions[] kingDirections= {Directions.UP, Directions.UP_RIGHT, Directions.RIGHT, Directions.DOWN_RIGHT, Directions.DOWN, Directions.DOWN_LEFT, Directions.LEFT, Directions.UP_LEFT};
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    for (Directions direction : kingDirections) {
      if (moveHelper(myPosition, direction, board) != null) {
        kingMoves.add(moveHelper(myPosition, direction, board));
      }
    }
    return kingMoves;
  }

}
