import enums.GridStatus;
import java.util.ArrayList;
import java.util.List;
public class CityMap{
    private ArrayList<ArrayList<GridStatus>> grid;
    private ArrayList<ArrayList<Integer>> obstacleLocations=new ArrayList<>();
    private boolean legalMove=false;
    public CityMap(ArrayList<ArrayList<GridStatus>> grid){
        this.grid=grid;
        for (int x=0; x<grid.get(0).size(); x++){
            for(int y=0; y<grid.get(1).size(); y++){
                if (!(grid.get(x).get(y).equals(GridStatus.OPEN))){
                    ArrayList<Integer> location=new ArrayList<>();
                    location.add(x);
                    location.add(y);
                    obstacleLocations.add(location);
                }
            }
        }
    }
    public ArrayList<ArrayList<Integer>> getBlockedCells(){
        return obstacleLocations;
    }
    public boolean isMoveLegal(ArrayList<Integer> moveLocation){
        if (grid.get(moveLocation.get(0)).get(moveLocation.get(1))==GridStatus.OPEN){
            legalMove=true;
        }
        return legalMove;
    }

    public int[] getGridSize(){
        int[] gridSize={grid.size(), grid.get(0).size()}; //gridSize=[Height, Width]
        return gridSize;
    }

    public static void main(String[] args){
        ArrayList<ArrayList<GridStatus>> grid=new ArrayList<>();
        for (int x=0;x<8;x++){
            ArrayList<GridStatus> row=new ArrayList<>();
            for (int y=0;y<8;y++){
                row.add(GridStatus.OPEN);
            }
            grid.add(row);
        }
        grid.get(2).set(3, GridStatus.ROADBLOCK);
        CityMap map1=new CityMap(grid);
        System.out.println(map1.getBlockedCells());
        int[] gridSize=map1.getGridSize();
        int height=gridSize[0];
        int width=gridSize[1];
        System.out.println(height+" "+width);
    }
}