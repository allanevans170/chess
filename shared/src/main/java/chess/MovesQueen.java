package chess;

import java.util.Collection;
import java.util.ArrayList;

public class MovesQueen extends MoveMaker {
  Collection<ChessMove> queenMoves = new ArrayList<>();

  Directions[] queenDirections= {Directions.UP_LEFT, Directions.UP_RIGHT, Directions.DOWN_LEFT, Directions.DOWN_RIGHT,
          Directions.UP, Directions.DOWN, Directions.LEFT, Directions.RIGHT};

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    for (Directions direction : queenDirections) {
      queenMoves.addAll(omniDirectionalMover(myPosition, direction, board));
    }
    return queenMoves;
  }


}
