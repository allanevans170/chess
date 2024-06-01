package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MovesPawn extends MoveMaker {
  private Collection<ChessMove> pawnMoves = new ArrayList<>();
  private Directions[] whitePawnDirections = {
          Directions.UP_LEFT,
          Directions.UP,
          Directions.UP_RIGHT
  };
  private Directions[] blackPawnDirections = {
          Directions.DOWN_LEFT,
          Directions.DOWN,
          Directions.DOWN_RIGHT
  };
  private ChessPiece.PieceType[] promotionPieces = {
          ChessPiece.PieceType.QUEEN,
          ChessPiece.PieceType.BISHOP,
          ChessPiece.PieceType.KNIGHT,
          ChessPiece.PieceType.ROOK
  };
  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

    ChessGame.TeamColor pawnColor = board.getPiece(myPosition).getTeamColor();
    Directions[] pawnDirections;

    if (pawnColor == ChessGame.TeamColor.WHITE) {
      pawnDirections = whitePawnDirections;
    } else {
      pawnDirections = blackPawnDirections;
    }

    for (Directions dir : pawnDirections) {
      int row=myPosition.getRow();
      int col=myPosition.getColumn();
      int deltaRow = dir.getDeltaRow();
      int deltaCol = dir.getDeltaCol();

      row+=deltaRow;
      col+=deltaCol;

      if (row > 8 || row < 1 || col > 8 || col < 1 ) {
        continue;
      }
      if (board.getPiece(new ChessPosition(row, col)) == null) {    // EMPTY SQUARE
        if (dir == Directions.UP || dir == Directions.DOWN) {         // FORWARD MOVEMENT
          pawnPromotionHelper(pawnColor, row, col, myPosition, new ChessPosition(row, col));
        }
        if (dir == Directions.UP && myPosition.getRow() == 2 && board.getPiece(new ChessPosition(row+1, col)) == null) {   // INITIAL WHITE
          pawnMoves.add(new ChessMove(myPosition, new ChessPosition(row+1, col), null));
        }
        if (dir == Directions.DOWN && myPosition.getRow() == 7 && board.getPiece(new ChessPosition(row-1, col)) == null) { // INITIAL BLACK
          pawnMoves.add(new ChessMove(myPosition, new ChessPosition(row-1, col), null));
        }
      }
      else if (board.getPiece(new ChessPosition(row, col)).getTeamColor() != pawnColor) {             // ENEMY SQUARE
        if (pawnColor == ChessGame.TeamColor.BLACK && (dir == Directions.DOWN_LEFT || dir == Directions.DOWN_RIGHT)) {    // CAPTURE
          pawnPromotionHelper(pawnColor, row, col, myPosition, new ChessPosition(row, col));
        }
        if (pawnColor == ChessGame.TeamColor.WHITE && (dir == Directions.UP_LEFT || dir == Directions.UP_RIGHT)) {        // CAPTURE
          pawnPromotionHelper(pawnColor, row, col, myPosition, new ChessPosition(row, col));
        }
      }
    }
    return pawnMoves;
  }

  void pawnPromotionHelper(ChessGame.TeamColor pawnColor, int row, int col, ChessPosition myPosition, ChessPosition newPosition) {
    if (newPosition.getRow() != 8 && pawnColor == ChessGame.TeamColor.WHITE) {
      pawnMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
    }
    else if (newPosition.getRow() != 1 && pawnColor == ChessGame.TeamColor.BLACK) {
      pawnMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
    }
    else {
      for (ChessPiece.PieceType piece : promotionPieces) {
        pawnMoves.add(new ChessMove(myPosition, new ChessPosition(row, col), piece));
      }
    }
  }


}
