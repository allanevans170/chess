package chess;

import java.util.ArrayList;
import java.util.Collection;
public class MovesPawn extends MoveMaker {
  Collection<ChessMove> pawnMoves = new ArrayList<>();

  Directions[] whitePawnDirections = {Directions.UP, Directions.UP_RIGHT, Directions.UP_LEFT};
  Directions[] blackPawnDirections = {Directions.DOWN, Directions.DOWN_RIGHT, Directions.DOWN_LEFT};
  //Directions[] enPassantDirections = {Directions.LEFT, Directions.RIGHT};

  ChessPiece.PieceType[] promotionPieces = {ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.ROOK, ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT};

  // en passant ==  Directions.LEFT, Directions.RIGHT

  @Override
  public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
    if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
      pawnMoves.addAll(pawnMover(myPosition, whitePawnDirections, board));
    } else {
      pawnMoves.addAll(pawnMover(myPosition, blackPawnDirections, board));
    }

    return pawnMoves;
  }

  public Collection<ChessMove> pawnMover(ChessPosition currPos, Directions[] direction, ChessBoard board) {
    for (Directions dir : direction) {
      int row=currPos.getRow();
      int col=currPos.getColumn();

      row+=dir.getDeltaRow();
      col+=dir.getDeltaCol();

      if (row < 1 || row > 8 || col < 1 || col > 8) {
        continue;
      }

      if (squareValidate(currPos, new ChessPosition(row, col), board) == 2) {    // empty square moves
        if (dir == Directions.UP || dir == Directions.DOWN) {

          pawnMoves.addAll(promoteOrNot(currPos, row, col));

          if (pawnInitial(currPos, board, row, col) != null) {
            pawnMoves.add(pawnInitial(currPos, board, row, col));
          }

        }
      }
      else if (squareValidate(currPos, new ChessPosition(row, col), board) == 1) {    // enemy square
        if (dir == Directions.UP_RIGHT || dir == Directions.UP_LEFT || dir == Directions.DOWN_RIGHT || dir == Directions.DOWN_LEFT) {
          pawnMoves.addAll(promoteOrNot(currPos, row, col));
        }
      }
    }
    return pawnMoves;
  }

  public ChessMove pawnInitial (ChessPosition currPos, ChessBoard board, int row, int col) {
    if (currPos.getRow() == 2 && board.getPiece(currPos).getTeamColor() == ChessGame.TeamColor.WHITE) {
      if (squareValidate(currPos, new ChessPosition(row+1, col), board) == 2) {
        return new ChessMove(currPos, new ChessPosition(row+1, col), null);
      } else {
        return null;
      }
    }
    else if (currPos.getRow() == 7 && board.getPiece(currPos).getTeamColor() == ChessGame.TeamColor.BLACK) {
      if (squareValidate(currPos, new ChessPosition(row-1, col), board) == 2) {
        return new ChessMove(currPos, new ChessPosition(row-1, col), null);
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  public Collection<ChessMove> promoteOrNot(ChessPosition currPos, int row, int col) {
    Collection <ChessMove> pawnPromotionMoves = new ArrayList<>();
    if (row == 8 || row == 1) {
      for (ChessPiece.PieceType pieceType : promotionPieces) {
        pawnPromotionMoves.add(new ChessMove (currPos, new ChessPosition(row, col), pieceType));
      }
    } else {
      pawnPromotionMoves.add(new ChessMove (currPos, new ChessPosition(row, col), null));
    }
    return pawnPromotionMoves;
  }
}
