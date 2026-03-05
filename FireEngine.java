package cityrescue.classes;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;

public class FireEngine extends Unit{
    private UnitType type=UnitType.FIRE_ENGINE;
    private int taskTick=4;
    private int ticksCompleted=0;
    public FireEngine(int id, int home, int[] currentLocation){
        super(id, home, currentLocation);
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
