package chess;

import java.util.ArrayList;
import java.util.Collection;
public class MovesPawn implements PieceMovesCalculator {
  Collection<ChessMove> pawnMoves=new ArrayList<ChessMove>();

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    return pawnMoves;

  }
}
