import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Author: Jamie Yang
 * Date: 01/02/2020
 * Version: beta
 * Class Description: Object that stores all grids and categorize them into their groups,
 *  keeps track of the empty grids,*/
public class Board {
    /**columns/rows of the puzzle*/
    private int size = 9;
    /**Organize grids into groups*/
    private Group[] rows;
    private Group[] cols;
    private Group[] nines;
    /**Keeps track of all empty grids*/
    private ArrayList<Grid> emptyGrids;
    /**Stores all the grids in corresponding (row, col)*/
    private Grid[][] board = new Grid[size][size];
    /**Name of the input File of initial sudoku game*/
    private String filename;
    private int index = 0;
    private Stack<Integer> branchedIndex = new Stack<>();

    /**Constructs the board with a file name that is given by main
     * and initialize the global variables*/
    public Board(String filename){
        emptyGrids = new ArrayList<>();
        rows = new Group[size];
        cols = new Group[size];
        nines = new Group[size];
        this.filename = filename;
        for(int i = 0; i< size; i++){
            rows[i] = new Group();
        }
        for(int i = 0; i< size; i++){
            cols[i] = new Group();
        }
        for(int i = 0; i< size; i++){
            nines[i] = new Group();
        }
    }

    /**Asks the user to input a file path in case it was incorrect the first time*/
    private void changeFileName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your new file path below:");
        filename = scanner.nextLine();
    }

    /**Tries to read the input file and initialize the board according to it.
     * Make all Grid objects and put them into corresponding groups,
     * also store their group ID in themselves. Puts grid that has initial value
     * of 0 into emptyGrids, and find the possible numbers of each grid*/
    public void initBoard() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            int val;
            Grid curr;
            for(int i= 0; i < size; i++){
                line = reader.readLine();
                for(int j = 0; j<size; j++){
                    val = line.charAt(j)-'0';
                    if(i < 3){
                        if(j < 3){
                            curr = new Grid(i,j,0,val);
                            nines[0].addGrid(0,curr);
                        }else if(j < 6){
                            curr = new Grid(i,j,1,val);
                            nines[1].addGrid(1,curr);
                        }else{
                            curr = new Grid(i,j,2,val);
                            nines[2].addGrid(2,curr);
                        }
                    }else if(i < 6){
                        if(j <3){
                            curr = new Grid(i,j,3,val);
                            nines[3].addGrid(3,curr);
                        }else if(j < 6){
                            curr = new Grid(i,j,4,val);
                            nines[4].addGrid(4,curr);
                        }else{
                            curr = new Grid(i,j,5,val);
                            nines[5].addGrid(5,curr);
                        }
                    }else{
                        if(j <3){
                            curr = new Grid(i,j,6,val);
                            nines[6].addGrid(6,curr);
                        }else if(j < 6){
                            curr = new Grid(i,j,7,val);
                            nines[7].addGrid(7,curr);
                        }else{
                            curr = new Grid(i,j,8,val);
                            nines[8].addGrid(8,curr);
                        }
                    }
                    rows[i].addGrid(j,curr);
                    cols[j].addGrid(i, curr);
                    if(val == 0){
                        emptyGrids.add(curr);
                        curr.setFixed();
                    }
                    board[i][j] = curr;
                }
            }
            updatePossibilities();
            sortByPossibilities(emptyGrids,0,emptyGrids.size()-1);
/*
            printSinglePossibilities();
*/
        }catch(IOException e){
            System.out.println("Incorrect file path, please enter a valid path!");
            changeFileName();
            initBoard();
        }
    }

    /**Update possibilities of all empty grids, checks out possibilities
     * of all 3 groups of each grid*/
    public void updatePossibilities(){
        Group row;
        Group col;
        Group nine;
        for(Grid curr:emptyGrids){

            row = rows[curr.getRowID()];
            col = cols[curr.getColID()];
            nine = nines[curr.getXyID()];

            curr.setPossibilities(combinePossibilities(row,col,nine));
        }
    }

    /**
     * @param a Row group of the grid
     * @param b Col group of the grid
     * @param c Nine group of the grid
     * @return an array list that is the intersection of 3 possibilities
     * Find intersection of all 3 possibilities, which is the possibilities of the grids*/
    public ArrayList<Integer> combinePossibilities(Group a, Group b, Group c){
        ArrayList<Integer> list = new ArrayList<>();
        boolean x;
        boolean y;
        boolean z;
        for(int i=1; i <= 9; i++){
            x = a.getPossibilities().contains(i);
            y = b.getPossibilities().contains(i);
            z = c.getPossibilities().contains(i);
            if(x && y && z){
                list.add(i);
            }
        }
        return list;
    }

    /**Sorts emptyGrid in ascending order of number of possibilities using quick sort*/
    public void sortByPossibilities(ArrayList<Grid> grids, int low, int high){
        int pivot;
        if(low < high){
            pivot = partition(grids,low,high);
            sortByPossibilities(grids,low,pivot-1);
            sortByPossibilities(grids,pivot+1,high);
        }
    }

    public int partition(ArrayList<Grid> grids, int low, int high){
        Grid pivot = grids.get(high);
        int numOfPoss = pivot.getPossibilities().size();
        Grid curr;
        int i = low-1;
        for(int j = low; j<high; j++){
            curr = grids.get(j);
            if(curr.getPossibilities().size() < numOfPoss){
                i++;
                grids.set(j,grids.get(i));
                grids.set(i,curr);
            }
        }
        curr = grids.get(i+1);
        grids.set(i+1,pivot);
        grids.set(high,curr);
        return i+1;
    }

    public int findNextEmpty(){
        for (int i = 0; i< emptyGrids.size(); i++){
            if(emptyGrids.get(i).getValue() == 0){
                return i;
            }
        }
        return -1;
    }

    public boolean fillNextEmpty(){
        int nextEmpty = findNextEmpty();
        if(nextEmpty > -1) {
            Grid curr = emptyGrids.get(nextEmpty);
            ArrayList<Integer> possibilities = new ArrayList<>(curr.getPossibilities());
            Group row = rows[curr.getRowID()];
            Group col = cols[curr.getColID()];
            Group nine = nines[curr.getXyID()];
            for (int val : possibilities) {
                if (row.getPossibilities().contains(val) && col.getPossibilities().contains(val)
                        && nine.getPossibilities().contains(val)) {
                    curr.setValue(val);
                    row.removePossibility(val);
                    col.removePossibility(val);
                    nine.removePossibility(val);
/*
                    printBoard();
*/
                    if(!fillNextEmpty()){
                        row.undo(val);
                        col.undo(val);
                        nine.undo(val);
                        curr.setValue(0);
                    }else{
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    /**First check if there are no empty grids left, then checks the sum of every
     * group, see if they are 45, because it is impossible to have duplicate
     * numbers to appear in the same group, so the group must be filled
     * correctly if the sum = 45*/
    public boolean checkResult(){
        if(emptyGrids.isEmpty()) {
            for (int i = 0; i < size; i++) {
                if (rows[i].getSum() != 45) {
                    return false;
                }
            }
            for (int i = 0; i < size; i++) {
                if (cols[i].getSum() != 45) {
                    return false;
                }
            }
            for (int i = 0; i < size; i++) {
                if (nines[i].getSum() != 45) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printSinglePossibilities() {
        for (Grid curr : emptyGrids) {
            System.out.println("( " + curr.getRowID() + ", " + curr.getColID() + " )" + " possibilities: " + curr.getPossibilities());
        }
        System.out.println();
    }

    public void printBoard(){
        for(int i = 0; i<size; i++){
            if(i%3 == 0) {
                System.out.println("---------------------");
            }
            for(int j= 0; j<size; j++){
                if(j % 3 == 0){
                    System.out.print("|");
                }
                System.out.print(board[i][j].getValue()+" ");
            }
            System.out.println("|");
        }
        System.out.println("---------------------\n");
    }

}
