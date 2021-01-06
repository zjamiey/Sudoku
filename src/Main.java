import java.util.Scanner;
/**
 * Author: Jamie Yang
 * Date: 1/02/2020
 * Version: beta
 * Class Description: Main loop of that initiates the board
 * */
public class Main {

    public static void main(String args[]){
        String filename;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the file path:");
        filename = scanner.nextLine();
        Board board = new Board(filename);
        board.initBoard();
        System.out.println("This is the sudoku puzzle to solve:");
        board.printBoard();
        board.fillNextEmpty();
        if(board.checkResult()) {
            System.out.println("The sudoku puzzle is solved as following:");
            board.printBoard();
        }else{
            board.printBoard();
            System.out.println("Failed to solve the puzzle!");
        }
    }
}
