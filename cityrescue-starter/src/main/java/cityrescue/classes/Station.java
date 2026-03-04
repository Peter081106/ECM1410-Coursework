public Station(){
    private int stationId;
    private string name;
    private int height;
    private int width;
    private int maxUnits;
    private int unitCount;
    private Unit[] units;

    public Station(int stationId, String name, int x, int y, int maxUnits){
        this.stationId = stationId;
        this.name = name;
        this.height = x;
        this.width = y;
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
    public int getheight(){
        return height
    }
    public int getwidth(){
        return width
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
    public void addUnit(Unit unit){
        for (int i = 0; i < maxUnits; i++) {
            if (units[i] == null){
                units[i] = unit;
            }
        }
    }
    public void removeUnit(int unitId){
        for (int i = 0; i < maxUnits; i++) {
            if (units[i] == unitId){
                units[i] = null;
            }
        }

    }
        

}