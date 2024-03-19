package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] boardSquares = new ChessPiece[9][9]; // may be final???

    public ChessBoard() {
        // blank
    }

    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int i = 8; i >= 1; i--) {
            for (int j = 1; j <= 8; j++) {      // columns
                boardString.append("|");
                if (boardSquares[i][j] != null) {
                    //boardString.append(" [" + getPiece(new ChessPosition(i,j)).toString() + "] ");
                    boardString.append(getPiece(new ChessPosition(i, j)).toString());
                } else {
                    //ChessPosition placeHolder = new ChessPosition(i, j);
                    //boardString.append("_"+ placeHolder.toString() + "_");
                    boardString.append(" ");
                }
            }
            boardString.append("|\n");
        }
        return boardString.toString();
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        boardSquares[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return boardSquares[position.getRow()][position.getColumn()];
    }

    public ChessPosition getKingLocation(ChessGame.TeamColor team) {
        ChessPosition kingPosition = null;
        for (int i = 8; i >= 1; i--) {          // rows
            for (int j = 1; j <= 8; j++) {      // columns
                if (boardSquares[i][j] != null && boardSquares[i][j].getPieceType() == ChessPiece.PieceType.KING && boardSquares[i][j].getTeamColor() == team) {
                    kingPosition = new ChessPosition(i, j);
                    return kingPosition;
                }
            }
        }
        return null;
    }

    public Collection<ChessPosition> teamLocations(ChessGame.TeamColor team) {
        Collection<ChessPosition> teamPositions = new ArrayList<>();

        //int counter = 0;
        for (int i = 8; i >= 1; i--) {          // rows
            for (int j = 1; j <= 8; j++) {      // columns
                if (boardSquares[i][j] != null && boardSquares[i][j].getTeamColor() == team) {
                    ChessPosition temp = new ChessPosition(i,j);
                    teamPositions.add(temp);
                    //counter++;
                }
            }
        }
        return teamPositions;
    }

    //public Collection<ChessMove> dangerMoves(ChessGame.TeamColor team) {
    //
    //}

    public void updateSquare(ChessMove move) {
        //boardSquares[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;
        if (move.getPromotionPiece() == null) {
            boardSquares[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = getPiece(move.getStartPosition()); // move piece
            boardSquares[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;     // reset start square
        } else {
            //ChessPosition testing = move.getStartPosition();
            //ChessPiece testPiece = getPiece(testing);
            //ChessPiece promotedPiece = new ChessPiece(ChessGame.TeamColor.WHITE, move.getPromotionPiece());
            ChessPiece promotedPiece = new ChessPiece(getPiece(move.getStartPosition()).getTeamColor(), move.getPromotionPiece());
            boardSquares[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = promotedPiece;
            boardSquares[move.getStartPosition().getRow()][move.getStartPosition().getColumn()] = null;
        }
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        boardSquares[1][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        boardSquares[1][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        boardSquares[1][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        boardSquares[1][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        boardSquares[1][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        boardSquares[1][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        boardSquares[1][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        boardSquares[1][8] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        for (int i = 1; i <= 8; i++) {
            boardSquares[2][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

        boardSquares[8][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        boardSquares[8][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        boardSquares[8][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        boardSquares[8][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        boardSquares[8][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        boardSquares[8][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        boardSquares[8][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        boardSquares[8][8] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        for (int i = 1; i <= 8; i++) {
            boardSquares[7][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that=(ChessBoard) o;
        // I believe I need a for loop here to compare each element of the 2D array - done with deepEquals???
        //for (int i = 0; i <= 8; i++) {
            /*for (int j = 0; j <= 8; j++) {
                if (boardSquares[i][j] != null && !boardSquares[i][j].equals(that.boardSquares[i][j])) {
                        return false;
                } else if (boardSquares[i][j] == null && that.boardSquares[i][j] != null) {
                    return false;
                } else if (boardSquares[i][j] != null && that.boardSquares[i][j] == null) {
                    return false;
                } else {
                    System.out.println("something weird is happening in boardSquares.equals()");
                }
            }*/
        //}
        //return true;
        return Arrays.deepEquals(boardSquares, that.boardSquares);
    }

    @Override
    public int hashCode() {     // needs to be more intense, I think
        return Arrays.deepHashCode(boardSquares);
    }
}
