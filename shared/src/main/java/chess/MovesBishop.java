package chess;

import java.util.ArrayList;
import java.util.Collection;
public class MovesBishop implements PieceMovesCalculator {
    Collection<ChessMove> bishopMoves = new ArrayList<ChessMove>();
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        // up left
        for (int i = row + 1, j = col - 1; i <= 8 && j >= 1; i++, j--) {
            ChessPosition pos = new ChessPosition(i, j);
            if (board.getPiece(pos) == null) {
                bishopMoves.add(new ChessMove(myPosition, pos, null));
            } /*else if (board.getPiece(pos).getTeamColor() == ChessGame.TeamColor.WHITE) {  // if enemy piece occupies the square, add it to the list and stop looking in this direction
                bishopMoves.add(new ChessMove(myPosition, pos, null));
                break;
            } */
            else {
                break;  // if a friendly piece occupies the square, stop looking in this direction
            }
        }
        // up right
        for (int i = row + 1, j = col + 1; i <= 8 && j <= 8; i++, j++) { // should this condition be OR?
            ChessPosition pos = new ChessPosition(i, j);
            if (board.getPiece(pos) == null) {
                bishopMoves.add(new ChessMove(myPosition, pos, null));
            } /*else if (board.getPiece(pos).getTeamColor() == ChessGame.TeamColor.WHITE) {  // if enemy piece occupies the square, add it to the list and stop looking in this direction
                bishopMoves.add(new ChessMove(myPosition, pos, null));
                break;
            } */
            else {
                break;  // if a friendly piece occupies the square, stop looking in this direction
            }
        }
        // down right
        for (int i = row - 1, j = col + 1; i >= 1 && j <= 8; i--, j++) {
            ChessPosition pos = new ChessPosition(i, j);
            if (board.getPiece(pos) == null) {
                bishopMoves.add(new ChessMove(myPosition, pos, null));
            } /*else if (board.getPiece(pos).getTeamColor() == ChessGame.TeamColor.WHITE) {  // if enemy piece occupies the square, add it to the list and stop looking in this direction
                bishopMoves.add(new ChessMove(myPosition, pos, null));
                break;
            } */
            else {
                break;  // if a friendly piece occupies the square, stop looking in this direction
            }
        }
        // down left
        for (int i = row - 1, j = col - 1; i >= 1 && j >= 1; i--, j--) {
            ChessPosition pos = new ChessPosition(i, j);
            if (board.getPiece(pos) == null) {
                bishopMoves.add(new ChessMove(myPosition, pos, null));
            } /*else if (board.getPiece(pos).getTeamColor() == ChessGame.TeamColor.WHITE) {  // if enemy piece occupies the square, add it to the list and stop looking in this direction
                bishopMoves.add(new ChessMove(myPosition, pos, null));
                break;
            } */
            else {
                break;  // if a friendly piece occupies the square, stop looking in this direction
            }
        }

        return bishopMoves;
    }
}
