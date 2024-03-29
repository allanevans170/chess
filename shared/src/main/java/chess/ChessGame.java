package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    TeamColor currTurn = TeamColor.WHITE;   // first move belongs to white
    ChessBoard board = new ChessBoard();    // necessary for moves validation testing?? not sure

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        if (currTurn == TeamColor.WHITE) {
            currTurn = TeamColor.BLACK;
        } else {
            currTurn = TeamColor.WHITE;
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();

        ChessPiece currPiece = board.getPiece(startPosition);

        if (currPiece == null) {
            return null;
        } else {
            switch (currPiece.getPieceType()) {
                case PAWN -> {
                    MovesPawn pawn=new MovesPawn();
                    validMoves=pawn.pieceMoves(board, startPosition);
                }
                case ROOK -> {
                    MovesRook rook=new MovesRook();
                    validMoves=rook.pieceMoves(board, startPosition);
                }
                case KNIGHT -> {
                    MovesKnight knight=new MovesKnight();
                    validMoves=knight.pieceMoves(board, startPosition);
                }
                case BISHOP -> {
                    MovesBishop bishop=new MovesBishop();
                    validMoves=bishop.pieceMoves(board, startPosition);
                }
                case QUEEN -> {
                    MovesQueen queen=new MovesQueen();
                    validMoves=queen.pieceMoves(board, startPosition);
                }
                case KING -> {
                    MovesKing king=new MovesKing();
                    validMoves=king.pieceMoves(board, startPosition);
                }
            }
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());

        if (validMoves.contains(move)) {
            // update board
            board.updateSquare(move);

        } else {
            throw new InvalidMoveException("Invalid move");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingLocation = board.getKingLocation(teamColor);

        TeamColor opposingTeam = getOpposingTeam(teamColor);

        Collection<ChessPosition> opposingTeamLocations = board.teamLocations(opposingTeam);

        for (ChessPosition position : opposingTeamLocations) {
            Collection<ChessMove> validMoves = validMoves(position);
            for (ChessMove move : validMoves) {
                if (move.getEndPosition().equals(kingLocation)) {
                    return true;
                }
            }
        }
        return false;

        // I need to locate the king of teamColor and determine if any of the available moves
        // of the opposing team can capture the king
    }

    private TeamColor getOpposingTeam(TeamColor teamColor) {
        if (teamColor == TeamColor.WHITE) {
            return TeamColor.BLACK;
        } else {
            return TeamColor.WHITE;
        }
    }



    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // if the king is in check, and there are no valid moves, then it is checkmate
        // check moves surrounding the king

        // need to check if the king is in check first?
        if (isInCheck(teamColor) == false) {
            return false;
        }

        ChessPosition kingLocation = board.getKingLocation(teamColor);
        Collection<ChessMove> kingMoves = validMoves(kingLocation);
        //Collection<ChessPosition> kingEndLocations = new ArrayList<>();

        TeamColor getOpposingTeam = getOpposingTeam(teamColor);         // enemy color
        Collection<ChessPosition> opposingTeamLocations = board.teamLocations(getOpposingTeam); // enemy locations
        //Collection<ChessMove> dangerEnemyMoves = kwisatzHaderach(opposingTeamLocations);      // collection of all possible enemy moves
        Collection<ChessMove> checkMoves = new ArrayList<>();

        for (ChessPosition enemyPosition : opposingTeamLocations) {             // for each enemy piece
            Collection<ChessMove> enemyPiecesMoves = validMoves(enemyPosition);
            for (ChessMove enemyPieceMove : enemyPiecesMoves) {                            // for each move by that enemy
                if (enemyPieceMove.getEndPosition().equals(kingLocation)) {          // if the move is a check
                    checkMoves.add(enemyPieceMove);                                  // add to list of checks
                }
                //for (ChessMove kingMove : kingMoves) {                  // this is getting nastily nested...
                //    if (enemyPieceMove.getEndPosition().equals(kingMove.getEndPosition())) {
                //        kingMoves.remove(kingMove);
                //    }
                //}
                Iterator<ChessMove> kingMoveIterator = kingMoves.iterator();
                while (kingMoveIterator.hasNext()) {
                    ChessMove kingMove = kingMoveIterator.next();
                    if (enemyPieceMove.getEndPosition().equals(kingMove.getEndPosition())) {
                        kingMoveIterator.remove();
                    }
                }
            }
        }
        if (checkMoves.size() > 1) {
            return true;
        }
        return false;
    }

    /*public Collection<ChessMove> kwisatzHaderach(Collection<ChessPosition> opposingTeamLocations) {
        Collection<ChessMove> kwisatzHaderach = new ArrayList<>();
        Collection
        for (ChessPosition position : opposingTeamLocations) {
            Collection<ChessMove> eachEnemyMoves=validMoves(position);
            for (ChessMove move : eachEnemyMoves) {
                if (move.getEndPosition().equals(board.getKingLocation(getOpposingTeam(currTurn)))) {
                    kwisatzHaderach.add(move);
                }
            }
            kwisatzHaderach.addAll(eachEnemyMoves);
        }
        return kwisatzHaderach;
    }*/

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
