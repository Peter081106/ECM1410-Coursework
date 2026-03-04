package cityrescue.classes;

import cityrescue.enums.*;

abstract class Unit{
    private int id;
    private UnitType type;
    private UnitStatus status=UnitStatus.IDLE;
    private int home;
    private int[] currentLocation;
    private int workingIncident=0;

    abstract boolean canHandle(IncidentType incident);
    abstract int getTicksToComplete();

    public Unit(int unitID, int homeStation, int[] location){
        id=unitID;
        home=homeStation;
        currentLocation=Location;
    }

    public int getUnitId(){
        return id;
    }
    public UnitStatus getStatus(){
        return status;
    }
    public setStatus(UnitStatus status){
        this.status=status
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

class Ambulance extends Unit{
    private UnitType type=UnitType.AMBULANCE;
    private int taskTick=2;
    private int ticksCompleted=0;
    public Ambulance(int id, int home, int[] currentLocation){
        super(id, home, currentLocation, workingIncident, OUT_OF_SERVICE);
    }

    public int getTicksToComplete(){
        int remainingTicks=taskTick-ticksCompleted;
        if (remainingTicks==0){
            ticksCompleted=0;
        }
        return remainingTicks;
    }

    public boolean canHandle(IncidentType incident){
        boolean handle;
        if (incident.equals(IncidentType.MEDICAL)){
            handle=true;
        } else{
            handle=false;
        }
        return handle;
    }
}

class FireEngine extends Unit{
    private UnitType type=UnitType.FIRE_ENGINE;
    private taskTick=4;
    private ticksCompleted=0;
    public FireEngine(int id, int home, int[] currentLocation){
        super(id, home, currentLocation, workingIncident, OUT_OF_SERVICE);
    }

    public int getTicksToComplete(){
        int remainingTicks=taskTick-ticksCompleted;
        if (remainingTicks==0){
            ticksCompleted=0;
        }
        return remainingTicks;
    }

    public boolean canHandle(IncidentType incident){
        boolean handle;
        if (incident.equals(IncidentType.FIRE)){
            handle=true;
        } else{
            handle=false;
        }
        return handle;
    }
}

class PoliceCar extends Unit{
    private UnitType type=UnitType.POLICE_CAR;
    private taskTick=3;
    private ticksCompleted=0;
    public PoliceCar(int id, int home, int[] currentLocation){
        super(id, home, currentLocation, workingIncident, OUT_OF_SERVICE);
    }

    public int getTicksToComplete(){
        int remainingTicks=taskTick-ticksCompleted;
        if (remainingTicks==0){
            ticksCompleted=0;
        }
        return remainingTicks;
    }

    public boolean canHandle(IncidentType incident){
        boolean handle;
        if (incident.equals(IncidentType.CRIME)){
            handle=true;
        } else{
            handle=false;
        }
        return handle;
    }
}