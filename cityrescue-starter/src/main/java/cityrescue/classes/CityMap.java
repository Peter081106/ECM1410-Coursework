package cityrescue.classes;

import java.util.ArrayList;

import cityrescue.enums.GridStatus;

public class CityMap{
    private ArrayList<ArrayList<GridStatus>> grid=new ArrayList<>();
    private ArrayList<ArrayList<Integer>> obstacleLocations=new ArrayList<>();
    public CityMap(int height, int width){
        for (int x=0;x<height;x++){
            ArrayList<GridStatus> row=new ArrayList<>();
            for (int y=0;y<width;y++){
                row.add(GridStatus.OPEN);
            }
            grid.add(row);
        }
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

    public void refresh(ArrayList<ArrayList<GridStatus>> grid){
        this.grid=grid;
    }

    public boolean isMoveLegal(int[] moveLocation){
        boolean legalMove=false;
        if (grid.get(moveLocation[0]).get(moveLocation[1])==GridStatus.OPEN){
            legalMove=true;
        }
        return legalMove;
    }

    public int[] getGridSize(){
        int[] gridSize={grid.size(), grid.get(0).size()}; //gridSize=[Height, Width]
        return gridSize;
    }
}