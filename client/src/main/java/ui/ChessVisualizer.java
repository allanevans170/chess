package ui;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessVisualizer {

  private static final int BOARD_COLUMNS = 8;
  private static final int BOARD_ROWS = 8;
  private static final int SQUARE_SIZE_IN_CHARS = 1;
  private static final int LINE_WIDTH_IN_CHARS = 1;
  private static final String EMPTY = " ";
  private static Random rand = new Random();

private static ChessGame game = new ChessGame();

  public static void main(String[] args) {
    game = new ChessGame();
    StringBuilder output = new StringBuilder();
    printWhiteBoard(output);
    System.out.print("WHITE:\n" + output);

    StringBuilder output2 = new StringBuilder();
    printBlackBoard(output2);
    System.out.print("BLACK:\n" + output2);
  }

  public static String visualize(ChessGame inGame, String playerColor) {
    game = inGame;
    var out = new StringBuilder();
    out.append("BLACK\n");
    printBlackBoard(out);
    out.append(SET_BG_COLOR_WHITE);
    out.append("WHITE\n");
    printWhiteBoard(out);

    out.append(SET_BG_COLOR_BLACK);
    out.append(SET_TEXT_COLOR_WHITE);
    return out.toString();
  }

  public static void printWhiteBoard(StringBuilder out) {

    drawHeaders(out, false);
    drawChessBoard(out);
    drawHeaders(out, false);

    out.append(SET_BG_COLOR_BLACK);
    out.append(SET_TEXT_COLOR_WHITE);
  }

  public static void printBlackBoard(StringBuilder out) {

    drawHeaders(out, true);
    drawChessBoard(out);
    drawHeaders(out, true);

    out.append(SET_BG_COLOR_BLACK);
    out.append(SET_TEXT_COLOR_WHITE);
  }

  private static void drawHeaders(StringBuilder out, boolean reversed) {
    setBlack(out);

    String[] headers = { "a", "b", "c", "d", "e", "f", "g", "h" };
    out.append(SET_TEXT_COLOR_WHITE);
    out.append("+++");
    for (int boardCol = 0; boardCol < BOARD_COLUMNS; ++boardCol) {
      if (reversed == true) {
        drawHeader(out, headers[7 - boardCol]);
      } else {
        drawHeader(out, headers[boardCol]);
      }
    }
    out.append("\n");
  }

  private static void drawHeader(StringBuilder out, String headerText) {
    out.append(" ");
    printHeaderText(out, headerText);
    out.append(" ");
  }

  private static void printHeaderText(StringBuilder out, String player) {
    out.append(SET_BG_COLOR_BLACK);
    out.append(SET_TEXT_COLOR_BLUE);

    out.append(player);
    setBlack(out);
  }

  private static void drawChessBoard(StringBuilder out) {
    for (int boardRow = 0; boardRow < BOARD_ROWS; ++boardRow) {
      out.append(SET_BG_COLOR_BLACK);
      out.append(SET_TEXT_COLOR_BLUE);
      out.append(" " + (8 - boardRow) + " ");
      drawRowOfSquares(out, boardRow);
    }
  }

  private static void drawRowOfSquares(StringBuilder out, int boardRow) {
    setBlack(out);
      for (int boardCol = 0; boardCol < BOARD_COLUMNS; ++boardCol) {
        boolean isWhite = (boardCol + boardRow) % 2 == 0;
        ChessPiece piece = game.getBoard().getPiece(new ChessPosition(8-boardRow, 8-boardCol));
        String backgroundSquareColor=isWhite ? SET_BG_COLOR_DARK_BROWN : SET_BG_COLOR_LIGHT_BROWN;
        out.append(backgroundSquareColor+ " " + (piece == null ? EMPTY : piecePrint(piece)) + " ");
        setBlack(out);
      }
      out.append("\n");
  }

  // piecePrint function??? for attaching pieceType to chess UNICODE symbols???
//  private static String piecePrint(ChessPiece piece) {
//    if (piece == null) {
//      return " \u2003 ";
//    }
//    switch (piece.getPieceType()) {
//      case PAWN:
//        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
//          return "♙";
//        } else {
//          return "♟";
//        }
//      case ROOK:
//        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
//          return "♖";
//        } else {
//          return "♜";
//        }
//      case KNIGHT:
//        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
//          return "♘";
//        } else {
//          return "♞";
//        }
//      case BISHOP:
//        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
//          return "♗";
//        } else {
//          return "♝";
//        }
//      case QUEEN:
//        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
//          return "♕";
//        } else {
//          return "♛";
//        }
//      case KING:
//        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
//          return "♔";
//        } else {
//          return "♚";
//        }
//    }
//    return piece.toString();
//  }


  private static void setBlack(StringBuilder out) {
    out.append(SET_BG_COLOR_BLACK);
    out.append(SET_TEXT_COLOR_DARK_RED);
  }

}