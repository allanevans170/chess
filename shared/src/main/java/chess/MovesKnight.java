package chess;

import java.util.ArrayList;
import java.util.Collection;
public class MovesKnight extends MoveMaker {
  Collection<ChessMove> knightMoves = new ArrayList<>();

  Directions[] knightDirections = {Directions.RIGHT2_UP, Directions.RIGHT2_DOWN, Directions.LEFT2_UP,
          Directions.LEFT2_DOWN, Directions.UP2_LEFT, Directions.UP2_RIGHT, Directions.DOWN2_LEFT, Directions.DOWN2_RIGHT};

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    for (Directions direction : knightDirections) {
      if (moveHelper(myPosition, direction, board) != null) {
        knightMoves.add(moveHelper(myPosition, direction, board));
      }
    }
    return knightMoves;
  }

}
