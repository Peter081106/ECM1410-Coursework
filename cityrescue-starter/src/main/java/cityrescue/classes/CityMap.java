package cityrescue.classes;

import java.util.ArrayList;

import cityrescue.enums.GridStatus;

public class CityMap{
    private ArrayList<ArrayList<GridStatus>> grid=new ArrayList<>();
    private ArrayList<ArrayList<Integer>> obstacleLocations=new ArrayList<>();

    /**
     * This method will set assign the grid attribute for the object
     */

    public CityMap(int height, int width){
        for (int x=0;x<height;x++){
            ArrayList<GridStatus> row=new ArrayList<>();
            for (int y=0;y<width;y++){
                row.add(GridStatus.OPEN);
            }
            grid.add(row);
        }
    }

    /**
     * This method will return a list of all cells with an obstacle on them
     * Using a nested for loop, each cell on the grid is checked
     * if the cell is blocked by an object, then the x and y coordinates will be added togehter as an array and then added to the main array of locations
     * the array of all cells with obstacles is then returned
     * the grid is then refreshed
     */

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

    /**
     * this method is just for refreshing the grid
     */

    public void refresh(ArrayList<ArrayList<GridStatus>> grid){
        this.grid=grid;
    }

    /**
     * will check if a move is legal
     * legalmove is initally assigned false
     * if the location which is being checked is open, then the legalmove variable will be changed to true
     * legal move is then returned
     */

    public boolean isMoveLegal(int[] moveLocation){
        boolean legalMove=false;
        if (grid.get(moveLocation[0]).get(moveLocation[1])==GridStatus.OPEN){
            legalMove=true;
        }
        return legalMove;
    }

    /**
     * returns the dimensions of the grid
     * using the grid array, the amount of rows and columns of the array are checked using .size()
     * the dimensions are stored as an array and returned
     */

    public int[] getGridSize(){
        int[] gridSize={grid.size(), grid.get(0).size()}; //gridSize=[Height, Width]
        return gridSize;
    }
}