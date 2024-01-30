package chess;

import java.util.ArrayList;
import java.util.Collection;
public class MovesKnight implements PieceMovesCalculator {
    Collection<ChessMove> knightMoves = new ArrayList<ChessMove>();
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return knightMoves;
    }
}
