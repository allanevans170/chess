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
  private static final String X = " X ";
  private static final String O = " O ";
  private static Random rand = new Random();

  private static ChessGame game;

  public static void main(String[] args) {
    game = new ChessGame();
    visualize(game);
  }

  public static PrintStream visualize(ChessGame game) {
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    //out.print(ERASE_SCREEN);

    printBlackBoard(out);
    out.println(SET_BG_COLOR_WHITE);
    printWhiteBoard(out);

    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);
    return out;
  }

  public static void printWhiteBoard(PrintStream out) {

    drawHeaders(out);
    drawChessBoard(out);
    drawHeaders(out);

    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  public static void printBlackBoard(PrintStream out) {

    drawHeaders(out);
    drawChessBoard(out);
    drawHeaders(out);

    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  private static void drawHeaders(PrintStream out) {
    setBlack(out);

    String[] headers = { "a", "b", "c", "d", "e", "f", "g", "h" };
    out.print(SET_TEXT_COLOR_WHITE);
    out.print("+++");
    for (int boardCol = 0; boardCol < BOARD_COLUMNS; ++boardCol) {
      drawHeader(out, headers[boardCol]);
    }
    out.println();
  }

  private static void drawHeader(PrintStream out, String headerText) {
    int prefixLength = 1;//SQUARE_SIZE_IN_CHARS;
    int suffixLength = 1;//SQUARE_SIZE_IN_CHARS;// - prefixLength - 1;

    //out.print(EMPTY.repeat(prefixLength));
    out.print(" ");
    printHeaderText(out, headerText);
    out.print(" ");
    //out.print(EMPTY.repeat(suffixLength));
  }

  private static void printHeaderText(PrintStream out, String player) {
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_BLUE);

    out.print(player);
    setBlack(out);
  }

  private static void drawChessBoard(PrintStream out) {
    for (int boardRow = 0; boardRow < BOARD_ROWS; ++boardRow) {
      out.print(SET_BG_COLOR_BLACK);
      out.print(SET_TEXT_COLOR_BLUE);
      out.print(" " + (8 - boardRow) + " ");
      drawRowOfSquares(out, boardRow);
    }
  }

  private static void drawRowOfSquares(PrintStream out, int boardRow) {
    //for (int squareRow = 0; squareRow < BOARD_ROWS; ++squareRow) {
    setBlack(out);
      for (int boardCol=0; boardCol < BOARD_COLUMNS; ++boardCol) {
        boolean isWhite=(boardCol + boardRow) % 2 == 0;
        ChessPiece piece = game.getBoard().getPiece(new ChessPosition(8-boardRow, 8-boardCol));
        String backgroundSquareColor=isWhite ? SET_BG_COLOR_MAGENTA : SET_BG_COLOR_BLUE;
        out.print(backgroundSquareColor+ " " + (piece == null ? EMPTY : piece.toString()) + " ");

//        if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
//          int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
//          int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
//
//          out.print(EMPTY.repeat(prefixLength));
//          printPlayer(out, rand.nextBoolean() ? X : O);
//          out.print(EMPTY.repeat(suffixLength));
//        }
//        else {
//          out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
//        }

//        if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
//          // Draw right line
//          setRed(out);
//          out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
//        }

        setBlack(out);
      }

      out.println();
    //}
  }

  private static void setWhite(PrintStream out) {
    out.print(SET_BG_COLOR_WHITE);
    out.print(SET_TEXT_COLOR_WHITE);
  }

  private static void setBlack(PrintStream out) {
    out.print(SET_BG_COLOR_BLACK);
    out.print(SET_TEXT_COLOR_BLACK);
  }

  private static void printPlayer(PrintStream out, String player) {
    out.print(SET_BG_COLOR_WHITE);
    out.print(SET_TEXT_COLOR_BLACK);

    out.print(player);

    setWhite(out);
  }
}