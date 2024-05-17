package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece implements Cloneable {       // implements Cloneable
    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING('K'),
        QUEEN('Q'),
        BISHOP('B'),
        KNIGHT('N'),
        ROOK('R'),
        PAWN('P');

        private final char symbol;
        PieceType(char symbol) {
            this.symbol = symbol;
        }
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return switch (type) {
            case KING -> new MovesKing().pieceMoves(board, myPosition);   // calling a method
            case QUEEN -> new MovesQueen().pieceMoves(board, myPosition);
            case BISHOP -> new MovesBishop().pieceMoves(board, myPosition);
            case KNIGHT -> new MovesKnight().pieceMoves(board, myPosition);
            case ROOK -> new MovesRook().pieceMoves(board, myPosition);
            case PAWN -> new MovesPawn().pieceMoves(board, myPosition);
            default -> throw new RuntimeException("asking for a piece that doesn't exist...");
        };
    }

    @Override
    public String toString() {
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            return Character.toString(type.symbol);
        } else {
            char piece = Character.toLowerCase(type.symbol);
            return Character.toString(piece);
        }

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }



    @Override
    protected ChessPiece clone() throws CloneNotSupportedException {
        ChessPiece clonedPiece = new ChessPiece(this.pieceColor, this.type);
        return clonedPiece;
    }
}
