package cityrescue.classes;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class Ambulance extends Unit{
    private UnitType type=UnitType.AMBULANCE;
    private int taskTick=2;
    private int ticksCompleted=0;
    public Ambulance(int id, int home, int[] currentLocation){
        super(id, UnitType.AMBULANCE, home, currentLocation);
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

    public void incrementWork(){
        ticksCompleted++;
    }
}

