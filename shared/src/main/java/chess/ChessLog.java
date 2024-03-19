package chess;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChessLog {
  Map<ChessMove, Integer> moveLog = new LinkedHashMap<>();
  int iterator = 0;

    public void addMove(ChessMove move) {
        moveLog.put(move, iterator);
        iterator++;
    }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<ChessMove, Integer> entry : moveLog.entrySet()) {
      sb.append(entry.getKey().toString());
      sb.append(" ");
      sb.append(entry.getValue());
      sb.append("\n");
    }
    return sb.toString();
  }

}
