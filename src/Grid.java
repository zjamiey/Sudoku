import java.util.ArrayList;
import java.util.HashSet;

/**
 * Author: Jamie Yang
 * Date: 1/02/2020
 * Version: beta
 * Class Description: Object representing the an individual grid of the puzzle,
 * it an number value, 3 IDs that represents which the 3 groups that it belongs to,
 * and set of possible values that can be assigned to this grid*/
public class Grid {
    /**Value assigned to the grid, empty grid has value of 0*/
    private int value;
    /**Row, Col, and Nine IDs of the groups that this grid belongs to*/
    private final int ROW_ID;
    private final int COL_ID;
    private final int XY_ID;
    /**Set of possible values that can be assigned to this grid*/
    private HashSet<Integer> possibilities;
    private boolean fixed = false;

    public Grid(int rowID, int colID, int xyID, int value){
        ROW_ID = rowID;
        COL_ID = colID;
        XY_ID = xyID;
        this.value = value;
        possibilities = new HashSet<>();
    }

    public int getRowID(){
        return ROW_ID;
    }

    public void setFixed(){
        fixed = true;
    }

    public boolean isFixed(){
        return fixed;
    }

    public int getColID(){
        return COL_ID;
    }

    public int getXyID(){
        return XY_ID;
    }

    public void setValue(int num){
        value = num;
    }

    public int getValue(){
        return value;
    }

    public void setPossibilities(ArrayList<Integer> list){
        possibilities.clear();
        possibilities.addAll(list);
    }

    public HashSet<Integer> getPossibilities(){
        return possibilities;
    }
}
