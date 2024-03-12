package chess;

import java.util.ArrayList;
import java.util.Collection;
public class MovesKnight extends MoveMaker {
    Collection<ChessMove> knightMoves = new ArrayList<>();

    Directions[] knightDirections = {Directions.RIGHT2_UP, Directions.RIGHT2_DOWN, Directions.LEFT2_UP,
            Directions.LEFT2_DOWN, Directions.UP2_LEFT, Directions.UP2_RIGHT, Directions.DOWN2_LEFT, Directions.DOWN2_RIGHT};

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        for (Directions direction : knightDirections) {
            int deltaX=direction.getDeltaX();     // Initializing x and y
            int deltaY=direction.getDeltaY();

            int y=myPosition.getRow();
            int x=myPosition.getColumn();
            x+=deltaX;
            y+=deltaY;
            if (x < 1 || x > 8 || y < 1 || y > 8) {
                continue;
            }
            if (squareValidate(myPosition, new ChessPosition(x, y), board) == 0) {    // friendly
                continue;
            }
            knightMoves.add(new ChessMove(myPosition, new ChessPosition(x, y), null));
        }
        return knightMoves;
    }

}
