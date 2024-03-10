package chess;

import java.util.ArrayList;
import java.util.Collection;
public class MovesPawn extends MoveMaker {
  Collection<ChessMove> pawnMoves = new ArrayList<>();

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    //for (Directions direction : queenDirections) {
    //  queenMoves.addAll(omniDirectionalMover(myPosition, direction));
    //}
    return pawnMoves;
  }

}
