package cityrescue.classes;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class PoliceCar extends Unit{
    private UnitType type=UnitType.POLICE_CAR;
    private int taskTick=3;
    private int ticksCompleted=0;
    public PoliceCar(int id, int home, int[] currentLocation){
        super(id, UnitType.POLICE_CAR, home, currentLocation);
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

    public void incrementWork(){
        ticksCompleted++;
    }
}
