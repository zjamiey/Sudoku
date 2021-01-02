import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Author: Jamie Yang
 * Date: 12/29/2020
 * Version: building
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
                    }
                    board[i][j] = curr;
                }
            }
            updatePossibilities();
            sortByPossibilities();
        }catch(IOException e){
            System.out.println("Incorrect file path, please enter a valid path!");
            changeFileName();
            initBoard();
        }
    }

    /**Update possibilities of all empty grids, checks out possibilities
     * of all 3 groups of each grid*/
    public void updatePossibilities(){
        Grid curr;
        Group row;
        Group col;
        Group nine;
/*        int rd;
        int cd;
        int nd;*/
        for(int i = 0; i<emptyGrids.size(); i++){
            curr = emptyGrids.get(i);
/*            rd = curr.getRowID();
            cd = curr.getColID();
            nd = curr.getXyID();*/
            row = rows[curr.getRowID()];
            col = cols[curr.getColID()];
            nine = nines[curr.getXyID()];
/*            System.out.println("( "+curr.getRowID()+", "+curr.getColID()+" )");
            System.out.println("row "+rd+": "+row.getPossibilities());
            System.out.println("col "+cd+": "+col.getPossibilities());
            System.out.println("nine "+nd+": "+nine.getPossibilities());*/
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
/*
            System.out.println("i = "+i+" "+x+" "+y+" "+z);
*/
            if(x && y && z){
                list.add(i);
            }
        }
        System.out.println("Combined:" + list);
        return list;
    }

    /**Sorts emptyGrid in ascending order of number of possibilities using quick sort*/
    public void sortByPossibilities(){

    }

    public void printInString(){
        for(int i = 0; i< size; i++){
            System.out.println("row "+i+": "+rows[i].getPossibilities());
        }
        for(int i = 0; i< size; i++){
            System.out.println("col "+i+": "+cols[i].getPossibilities());
        }
        for(int i = 0; i< size; i++){
            System.out.println("nine "+i+": "+nines[i].getPossibilities());
        }
    }

    public void printSinglePossibilities(){
        Grid curr;
        for(int i = 0; i< emptyGrids.size(); i++){
            curr = emptyGrids.get(i);
            System.out.println("( "+curr.getRowID()+", "+curr.getColID()+" )"+" possibilities: "+curr.getPossibilities());
        }
    }

}
