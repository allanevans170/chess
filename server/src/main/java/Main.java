import chess.*;
import dataaccess.UserDAO;
import server.Server;
import service.*;
import dataaccess.*;


public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);


        try {
            var port = 8080;
            if (args.length >= 1) {
                port = Integer.parseInt(args[0]);
            }

            //var service = new ChessService(new MemoryUserDAO(), new MemoryAuthDAO(), new MemoryGameDAO());
            var server = new Server().run(port);
            port = server.port();
            System.out.println("Server running on port " + port);
            return;
        } catch (Throwable ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}