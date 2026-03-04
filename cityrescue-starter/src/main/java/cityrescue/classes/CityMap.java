package cityrescue.classes;

import cityrescue.enums.GridStatus;
import java.util.ArrayList;
import java.util.List;

class CityMap{
    private ArrayList<ArrayList<GridStatus>> grid;
    private ArrayList<ArrayList<Integer>> obstacleLocations=new ArrayList<>();
    public CityMap(ArrayList<ArrayList<GridStatus>> grid){
        this.grid=grid;
    }
    public ArrayList<ArrayList<Integer>> getBlockedCells(){
        for (int x=0; x<grid.size(); x++){
            for(int y=0; y<grid.get(0).size(); y++){
                if (!(grid.get(x).get(y).equals(GridStatus.OPEN))){
                    ArrayList<Integer> location=new ArrayList<>();
                    location.add(x);
                    location.add(y);
                    obstacleLocations.add(location);
                }
            }
        }
        return obstacleLocations;
    }

    public refresh(ArrayList<ArrayList<GridStatus>> grid){
        this.grid=grid
    }

    public boolean isMoveLegal(ArrayList<Integer> moveLocation){
        boolean legalMove=false;
        if (grid.get(moveLocation.get(0)).get(moveLocation.get(1))==GridStatus.OPEN){
            legalMove=true;
        }
        return legalMove;
    }

    public int[] getGridSize(){
        int[] gridSize={grid.size(), grid.get(0).size()}; //gridSize=[Height, Width]
        return gridSize;
    }
}