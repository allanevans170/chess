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

}
