package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class BoardVisualizer {
  private static final int BOARD_SIZE_IN_SQUARES = 8;
  private static final int SQUARE_SIZE_IN_CHARS = 3;
  //private static final int LINE_WIDTH_IN_CHARS = 1;
  private static final String EMPTY = "   ";

  public static void main(String[] args) {
    var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    out.print(ERASE_SCREEN);

    drawHeader(out);
  }

  private static void drawHeader(PrintStream out) {
//    out.print(moveCursorToLocation(1, 1));
//    out.print(SET_TEXT_BOLD + "  a b c d e f g h" + SET_TEXT_BOLD);
//    out.print(moveCursorToLocation(1, 2));
//    out.print(SET_TEXT_BOLD + "  ┌─┬─┬─┬─┬─┬─┬─┬─┐" + SET_TEXT_BOLD);

  }

  private static void drawFooter(PrintStream out) {
//        out.print(moveCursorToLocation(1, 18));
//        out.print(SET_TEXT_BOLD + "  └─┴─┴─┴─┴─┴─┴─┴─┘" + RESET_TEXT_BOLD);
  }

  private static void drawRow(PrintStream out, int row) {
    // Draw the row number
    // Draw squares, pieces, and separators

//    out.print(moveCursorToLocation(1, 2 + row));
//    out.print(SET_TEXT_BOLD + row + RESET_TEXT_BOLD);
//    out.print(SET_TEXT_BOLD + " │" + RESET_TEXT_BOLD);
//    for (int col = 0; col < BOARD_SIZE_IN_SQUARES; col++) {
//      out.print(EMPTY);
//      out.print(SET_TEXT_BOLD + "│" + RESET_TEXT_BOLD);
//    }
  }
}
