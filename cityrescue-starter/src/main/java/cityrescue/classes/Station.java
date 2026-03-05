package cityrescue.classes;

import java.util.ArrayList;

public class Station{
    private int stationId;
    private String name;
    private int height;
    private int width;
    private int maxUnits;
    private int unitCount;
    private ArrayList<Unit> units;

    /**
     * this method will set up a station object with the variables given
     */

    public Station(int stationId, String name, int x, int y, int maxUnits){
        this.stationId = stationId;
        this.name = name;
        this.height = x;
        this.width = y;
        this.maxUnits = maxUnits;
        this.unitCount = 0;
        this.units = new ArrayList<>();
    }

    /**
     * the following are getter methods and will return the 
     */

    public int getstationId(){
        return stationId;
    }
    public String getname(){
        return name;
    }
    public int getheight(){
        return height;
    }
    public int getwidth(){
        return width;
    }
    public int getmaxUnits(){
        return maxUnits;
    }
    public int getunitCount(){
        return unitCount;
    }

    /**
     * the following method will change the maxUnits attribute to the value that has been passed
     */

    public void setmaxUnits(int newmaxUnits){
        maxUnits=newmaxUnits;

    /**
     * the following method will add a new unit to the list of units and increase the unit count
     */
    }
    public void addUnit(Unit unit){
        units.add(unit);
        unitCount++;
    }

    /**
     * will search the list of units for the unit that corresonds to the unit id
     * will then remove the unit from units
     * then will decrease unit count by 1 and return
     */

    public void removeUnit(int unitId){
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getUnitId() == unitId){
                units.remove(i);
                unitCount--;
                return;
            }
        }
    }
}