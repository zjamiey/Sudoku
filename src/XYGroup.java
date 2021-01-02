import java.util.Arrays;
import java.util.HashSet;

public class XYGroup {
    private Grid[][] group = new Grid[3][3];
    private HashSet<Integer> possibilities;
    int sum = 0;

    public XYGroup(){
        possibilities = new HashSet<>();
        possibilities.add(1);
        possibilities.add(2);
        possibilities.add(3);
        possibilities.add(4);
        possibilities.add(5);
        possibilities.add(6);
        possibilities.add(7);
        possibilities.add(8);
        possibilities.add(9);    }

    public void addGrid(int x,int y, Grid grid){
        int val = grid.getValue();
        if(val > 0){
            sum += val;
        }else {
            group[x % 3][y % 3] = grid;
        }
        possibilities.remove(grid.getValue());
    }

    public void updateGrid(int x,int y, int num){
        group[x%3][y%3].setValue(num);
        possibilities.remove(num);
    }

    public void undo(int x, int y){
        possibilities.add(group[x%3][y%3].getValue());
        group[x][y].setValue(0);
    }

    public String getPossibilities(){
        return ""+ possibilities;
    }

    public String toString(){
        String s = "";
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++) {
                s += group[i][j].getValue();
            }
            s+= "\n";
        }
        return s;
    }
}
