public Station(){
    private int stationId;
    private string name;
    private int x;
    private int y;
    private int maxUnits;
    private int unitCount;
    private Unit[] units;

    public Station(int stationId, String name, int x, int y, int maxUnits){
        this.stationId = stationId;
        this.name = name;
        this.x = x;
        this.y = y;
        this.maxUnits = maxUnits;
        this.unitCount = 0;
        this.units = new Unit[capacity]
    }

    public int getstationId(){
        return stationId
    }
    public int getname(){
        return name
    }
    public int getx(){
        return x
    }
    public int gety(){
        return y
    }
    public int getmaxUnits(){
        return maxUnits
    }
    public int getunitCount(){
        return unitCount
    }
    public void setmaxUnits(newmaxUnits){

        Units[] newUnits = new unit[newmaxUnits];
        for (int i = 0; i < unitCount; i++) {
            newArray[i] = units[i];
        }
        this.units = newUnits;
        this.unit = newmaxUnits;
    }
}