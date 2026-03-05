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

    public Station(int stationId, String name, int x, int y, int maxUnits){
        this.stationId = stationId;
        this.name = name;
        this.height = x;
        this.width = y;
        this.maxUnits = maxUnits;
        this.unitCount = 0;
        this.units = new ArrayList<>();
    }

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
    public void setmaxUnits(int newmaxUnits){
        maxUnits=newmaxUnits;
    }
    public void addUnit(Unit unit){
        units.add(unit);
        unitCount++;
    }
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