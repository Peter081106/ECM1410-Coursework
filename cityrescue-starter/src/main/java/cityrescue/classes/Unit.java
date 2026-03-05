package cityrescue.classes;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

public abstract class Unit{
    private int id;
    private UnitType type;
    private UnitStatus status=UnitStatus.IDLE;
    private int home;
    private int[] currentLocation;
    private int workingIncident=-1;

    public abstract boolean canHandle(IncidentType incident);
    public abstract int getTicksToComplete();
    public abstract void incrementWork();

    public Unit(int unitID, UnitType type, int homeStation, int[] location){
        this.id=unitID;
        this.type=type;
        this.home=homeStation;
        this.currentLocation=location;
    }

    public int getUnitId(){
        return id;
    }
    public UnitStatus getStatus(){
        return status;
    }
    public void setStatus(UnitStatus status){
        this.status=status;
    }
    public UnitType getType(){
        return type;
    }
    public int getHome(){
        return home;
    }
    public void setHome(int home){
        this.home=home;
    }
    public int[] getCurrentLocation(){
        return currentLocation;
    }
    public void setCurrentLocation(int[] currentLocation){
        this.currentLocation=currentLocation;
    }
    public int getWorkingIncident(){
        return workingIncident;
    }
    public void setWorkingIncident(int workingIncident){
        this.workingIncident=workingIncident;
    }
}