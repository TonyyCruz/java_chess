package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

/**
 * This shows the chess game in the screen.
 */
public class Ui {
  // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";

  private Ui() {}

  // https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  /**
   * This receive a string position from a scanner and return a chess position.
   */
  public static ChessPosition readChessPosition(Scanner sc) {
    try {
      String in = sc.next();
      char column = in.charAt(0);
      int row = Integer.parseInt(in.substring(1));
      return new ChessPosition(column, row);
    } catch (RuntimeException e) {
      throw new InputMismatchException("The position need be from 'a1' to 'h8'.");
    }
  }

  /**
   * This method has the responsibility to display the captured pieces on the screen.
   */
  private static void printCapturedPieces(List<ChessPiece> captured) {
    List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).toList();
    List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).toList();
    System.out.println("Captured pieces:");
    System.out.println("White: " + Arrays.toString(white.toArray()));
    System.out.println("Black: " + Arrays.toString(black.toArray()));
  }

  /**
   * This method shows the game board, the turn number and current player color.
   */
  public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
    System.out.println("Current turn: " + chessMatch.getTurn());
    System.out.println();
    printBoard(chessMatch.getPieces());
    System.out.println();
    printCapturedPieces(captured);
    System.out.println();
    if (chessMatch.getCheckMate()) {
      System.out.println("CHECK MATE!");
      System.out.println("The player " + chessMatch.getCurrentPlayer() + " is the Winner!");
      return;
    }
    System.out.println("Current player: " + chessMatch.getCurrentPlayer());
    if (chessMatch.getCheck()) {
      System.out.println("The player " + chessMatch.getCurrentPlayer() + " is in CHECK");
    }
  }

  /**
   * This print a board with the current state of the game.
   */
  public static void printBoard(ChessPiece[][] pieces) {
    for (int row = 0; row < pieces.length; row += 1) {
      System.out.printf("%d ", pieces.length - row);
      for (int column = 0; column < pieces.length; column += 1) {
        print(pieces[row][column], false);
      }
      System.out.println();
    }
    System.out.println("  a b c d e f g h");
  }

  /**
   * This shows the frame on the screen.
   */
  public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
    for (int row = 0; row < pieces.length; row += 1) {
      System.out.printf("%d ", pieces.length - row);
      for (int column = 0; column < pieces.length; column += 1) {
        print(pieces[row][column], possibleMoves[row][column]);
      }
      System.out.println();
    }
    System.out.println("  a b c d e f g h");
  }

  /**
   * This print the received piece.
   */
  public static void print(ChessPiece piece, boolean paintBackground) {
    if (paintBackground) {
      System.out.print(ANSI_GREEN_BACKGROUND);
    }
    if (piece == null) {
      System.out.print("-" + ANSI_RESET);
    } else {
      System.out.print(piece + ANSI_RESET);
    }
    System.out.print(" ");
  }

}
