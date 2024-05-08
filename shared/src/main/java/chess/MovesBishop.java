package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MovesBishop extends MoveMaker {
  Collection<ChessMove> bishopMoves = new ArrayList<>();
  Directions[] bishopDirections= {Directions.UP_LEFT, Directions.UP_RIGHT, Directions.DOWN_LEFT, Directions.DOWN_RIGHT};

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    for (Directions direction : bishopDirections) {
      bishopMoves.addAll(omniDirectionalMover(myPosition, direction, board));
    }

    return bishopMoves;
  }
}
