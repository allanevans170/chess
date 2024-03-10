package chess;

import java.util.Collection;
import java.util.ArrayList;
public class MovesKing extends MoveMaker {
    Collection<ChessMove> kingMoves = new ArrayList<>();

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //for (Directions direction : queenDirections) {
          //  queenMoves.addAll(omniDirectionalMover(myPosition, direction));
        //}
        return kingMoves;
    }

}
