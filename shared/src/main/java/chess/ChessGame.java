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
    private ChessBoard board = new ChessBoard();
    private TeamColor currTurn;

    public ChessGame() {
        // why empty constructor???
        currTurn = TeamColor.WHITE;     // when object is constructed, default is that it is white's turn... right?
        board.resetBoard();
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
        currTurn = team;
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
        Collection<ChessMove> moves;
        ChessPiece currPiece = board.getPiece(startPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();

        if (currPiece == null) { return validMoves; } // an empty collection;}}

        moves = currPiece.pieceMoves(board, startPosition);

        for (ChessMove move : moves) {  // clone the board
            ChessBoard possibleBoard;
            try {
                possibleBoard = (ChessBoard) board.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Cloning error :: validMoves");
            }
            possibleBoard.updateSquares(move); // update square on cloned board

            if (!isInCheck(possibleBoard, currPiece.getTeamColor())) {  // is in check?
                validMoves.add(move);
            } // if in check, remove move from list of moves
        }
        return validMoves;
        // needs to use isInCheck... (don't get circular)!!!
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (board.getPiece(move.getStartPosition()) == null) {
            throw new InvalidMoveException("No piece at start position");
        }
        if (board.getPiece(move.getStartPosition()).getTeamColor() != currTurn) {
            throw new InvalidMoveException("It isn't" + currTurn.toString() + "'s turn");
        }
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        if (!validMoves.contains(move)) {
            throw new InvalidMoveException("Invalid move");
        } else if (isInCheck(currTurn)) {
            throw new InvalidMoveException("Can't move into check...");                      ///////// ???? needs to be solved in validMoves???
        } else {
            board.updateSquares(move);
            currTurn = (currTurn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;   // should I use setTeamTurn method?
        }
        // if a move is valid, then apply it to the board, update board...
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return isInCheck(this.board, teamColor);
    }
    public boolean isInCheck(ChessBoard anyBoard, TeamColor teamColor) {     // overriden to accept "future" boards...
        ChessPosition kingLocation = anyBoard.getKingLocation(teamColor);
        TeamColor opposingTeam = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        Collection<ChessPosition> opposingTeamLocations = anyBoard.teamLocations(opposingTeam, true);

        for (ChessPosition position : opposingTeamLocations) {
            Collection<ChessMove> moves = anyBoard.getPiece(position).pieceMoves(anyBoard, position); // call pieceMoves instead of validMoves...
            for (ChessMove move : moves) {
                if (move.getEndPosition().equals(kingLocation)) {
                    return true;
                }
            }
        }
        return false;
        // make the move... are you still in the check? end position of opposite team on your king's spot
        // check all
        // clone your board fore every move, throw away the clone
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        }
        //ChessPosition kingLocation = board.getKingLocation(teamColor);
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        Collection<ChessPosition> friendlyLocations = board.teamLocations(teamColor, true);
        for (ChessPosition friendlyPieceLocation : friendlyLocations) {
            possibleMoves.addAll(validMoves(friendlyPieceLocation));
        }
        //TeamColor opposingTeam = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;

        for (ChessMove move : possibleMoves) {
            ChessBoard possibleBoard;
            try {
                possibleBoard = (ChessBoard) board.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Cloning error :: isInCheckmate");
            }
            possibleBoard.updateSquares(move);
            if (!isInCheck(possibleBoard, teamColor)) {
                return false;
            }
        }
        if (!isInStalemate(teamColor)) {
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)) {
            return false;
        }
        ChessPosition kingLocation = board.getKingLocation(teamColor);
        Collection<ChessMove> kingMoves = validMoves(kingLocation);

        TeamColor getOpposingTeam = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        Collection<ChessPosition> opposingTeamLocations = board.teamLocations(getOpposingTeam, true); // enemy locations

        for (ChessPosition enemyPosition : opposingTeamLocations) {             // for each enemy piece
            if (kingMoves.isEmpty()) { break; }
            Collection<ChessMove> enemyPiecesMoves = validMoves(enemyPosition);
            for (ChessMove enemyPieceMove : enemyPiecesMoves) {                            // for each move by that enemy
                Iterator<ChessMove> kingMoveIterator = kingMoves.iterator();
                while (kingMoveIterator.hasNext()) {
                    ChessMove kingMove = kingMoveIterator.next();
                    if (enemyPieceMove.getEndPosition().equals(kingMove.getEndPosition())) {
                        kingMoveIterator.remove();
                        if (kingMoves.isEmpty()) { break; }
                    }
                }
            }
        }
        Collection<ChessPosition> myTeamLocations = board.teamLocations(teamColor, false);
        Collection<ChessMove> allFriendlyMoves = new ArrayList<>();
        for (ChessPosition friendlyPositions : myTeamLocations) {
            allFriendlyMoves.addAll(validMoves(friendlyPositions));
        }
        return kingMoves.isEmpty() && allFriendlyMoves.isEmpty();

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
