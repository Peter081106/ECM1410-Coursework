package cityrescue.classes;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class Ambulance extends Unit{
    private UnitType type=UnitType.AMBULANCE;
    private int taskTick=2;
    private int ticksCompleted=0;

    /**
     * this is a setter method which calls the parent contructor
     */
    public Ambulance(int id, int home, int[] currentLocation){
        super(id, UnitType.AMBULANCE, home, currentLocation);
    }

    /**
     * calculates the amount of ticks remaining until a task is complete
     * if the amount of remaining ticks are 0, then the ticks completed will be reset to 0
     * ticks remaining are returned
     */

    public int getTicksToComplete(){
        int remainingTicks=taskTick-ticksCompleted;
        if (remainingTicks==0){
            ticksCompleted=0;
        }
        return remainingTicks;
    }

    /**
     * will return true if the incident matches the type of the vehicle
     */

    public boolean canHandle(IncidentType incident){
        boolean handle;
        if (incident.equals(IncidentType.MEDICAL)){
            handle=true;
        } else{
            handle=false;
        }
        return handle;
    }

    /**
     * just increments tickCompleted
     */

    public void incrementWork(){
        ticksCompleted++;
    }
}

