import java.util.HashSet;

/**
 * Author: Jamie Yang
 * Date: 01/02/2020
 * Version: beta
 * Class Description: Main loop of that initiates the board
 * */
public class Group {
    private Grid[] group = new Grid[9];
    private HashSet<Integer> possibilities;
    private int sum;

    public Group(){
        possibilities = new HashSet<>();
        possibilities.add(1);
        possibilities.add(2);
        possibilities.add(3);
        possibilities.add(4);
        possibilities.add(5);
        possibilities.add(6);
        possibilities.add(7);
        possibilities.add(8);
        possibilities.add(9);
    }

    public void addGrid(int ID, Grid grid){
        group[ID] = grid;
        possibilities.remove(grid.getValue());
        sum += grid.getValue();
    }

    public void removePossibility(int num){
        possibilities.remove(num);
        sum += num;
    }

    public boolean isFilled(){
        return possibilities.isEmpty();
    }

    public int getSum(){
        return sum;
    }

    public void undo(Grid curr){
        if(!curr.isFixed()) {
            possibilities.add(curr.getValue());
            sum += curr.getValue();
            curr.setValue(0);
        }
    }

    public HashSet<Integer> getPossibilities(){
        return possibilities;
    }

    public String toString(){
        String s = "";
        for(int i = 0; i<9; i++){
            s+= group[i].getValue();
        }
        s+= "\n";
        return s;
    }

}
