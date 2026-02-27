import gridstatus.enums;
public class CityMap{
    private GridStatus[][] grid;
    private Pair<integer><integer>[] obstacleLocations;
    private boolean legalMove=false;
    public CityMap(GridStatus[][] grid){
        this.grid=grid;
        for (int x=0; x<grid[0].length(); x++){
            for(int y=0; y<grid[1].length(); y++){
                if (grid[x][y]=!GridStatus.OPEN){
                    Pair<integer><integer> location=new Pair<integer><integer>(x,y);
                    obstacleLocations.add(location);
                }
            }
        }
    }
    public Pair<integer><integer>[] getBlockedCells(){
        return obstacleLocations;
    }
    public boolean isMoveLegal(Pair<integer><integer> moveLocation){
        int moveX=moveLocation.getValue0();
        int moveY=moveLocation.getValue1();
        if (grid[x][y]==GridStatus.OPEN){
            legalMove=true;
        }
        return legalMove;
    }
}