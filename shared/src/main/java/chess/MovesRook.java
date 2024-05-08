package chess;

import java.util.Collection;
import java.util.ArrayList;

public class MovesRook extends MoveMaker {
  Collection<ChessMove> rookMoves = new ArrayList<>();

  Directions[] rookDirections= {Directions.UP, Directions.DOWN, Directions.LEFT, Directions.RIGHT};

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    for (Directions direction : rookDirections) {
      rookMoves.addAll(omniDirectionalMover(myPosition, direction, board));
    }
    return rookMoves;
  }

}
