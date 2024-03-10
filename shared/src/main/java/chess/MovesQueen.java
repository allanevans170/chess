package chess;

import java.util.Collection;
import java.util.ArrayList;

public class MovesQueen extends MoveMaker {
  Collection<ChessMove> queenMoves = new ArrayList<ChessMove>();

  Directions queenDirections[] = {Directions.UP_LEFT, Directions.UP_RIGHT, Directions.DOWN_LEFT, Directions.DOWN_RIGHT,
    Directions.UP, Directions.DOWN, Directions.LEFT, Directions.RIGHT};


}
