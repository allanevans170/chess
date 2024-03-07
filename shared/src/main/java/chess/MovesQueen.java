package chess;

import java.util.Collection;
import java.util.ArrayList;

public class MovesQueen extends MoveMaker {
    Collection<ChessMove> queenMoves = new ArrayList<ChessMove>();
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    return queenMoves;
  }
}
