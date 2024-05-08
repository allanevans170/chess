package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] boardSquares = new ChessPiece[9][9];

    public ChessBoard() {
        // empty constructor?
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

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        boardSquares[1][1]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        boardSquares[1][2]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        boardSquares[1][3]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        boardSquares[1][4]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        boardSquares[1][5]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        boardSquares[1][6]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        boardSquares[1][7]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        boardSquares[1][8]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        for (int i=1; i <= 8; i++) {
            boardSquares[2][i]=new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

        boardSquares[8][1]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        boardSquares[8][2]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        boardSquares[8][3]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        boardSquares[8][4]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        boardSquares[8][5]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        boardSquares[8][6]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        boardSquares[8][7]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        boardSquares[8][8]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        for (int i=1; i <= 8; i++) {
            boardSquares[7][i]=new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that=(ChessBoard) o;
        return Arrays.deepEquals(boardSquares, that.boardSquares);
    }

    @Override
    public int hashCode() {     // needs to be more intense, I think
        return Arrays.deepHashCode(boardSquares);
    }
}
