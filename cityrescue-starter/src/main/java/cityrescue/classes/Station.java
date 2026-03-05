public Station(){
    private int stationId;
    private string name;
    private int height;
    private int width;
    private int maxUnits;
    private int unitCount;
    private List<Unit> units;

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
    public int getname(){
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
    public void setmaxUnits(newmaxUnits){
        maxUnits=newMaxUnits;
    }
    public void addUnit(Unit unit){
        units.add(unit);
    }
    public void removeUnit(int unitId){
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getUnitID() == unitId){
                units.remove(i);
            }
        }

    }
        

}