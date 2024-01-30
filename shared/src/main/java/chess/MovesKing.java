package chess;

import java.util.Collection;
import java.util.ArrayList;
public class MovesKing implements PieceMovesCalculator {
    Collection<ChessMove> kingMoves = new ArrayList<ChessMove>();
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    return kingMoves;
  }
}
