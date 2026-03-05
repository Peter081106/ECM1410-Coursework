package cityrescue.classes;

import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;


public class Incident{
    private IncidentType type;
    private int incidentId;
    private int severity;
    private IncidentStatus status;
    private int unitAssignedId=-1;
    private int height;
    private int width;

    public IncidentType gettype(){
        return type;
    }
    public int getincidentId(){
        return incidentId;
    }
    public int getseverity(){
        return severity;
    }
    public IncidentStatus getstatus(){
        return status;
    }
    public void setstatus(IncidentStatus newStatus){
        status=newStatus;
    }
    public int getheight(){
        return height;
    }
    public int getwidth(){
        return width;
    }
    public int getUnitAssignedId(){
        return unitAssignedId;
    }
    public void setUnitAssignedId(int unitAssignedId){
        this.unitAssignedId=unitAssignedId;
    }

    public Incident(int Id, IncidentType type, int severity, int height, int width){
        this.incidentId=Id;
        this.type = type;
        this.status = IncidentStatus.REPORTED;
        this.severity = severity;
        this.width = width;
        this.height = height;
    }
    public void cancelledIncident(){
        this.status = IncidentStatus.CANCELLED;
    }
    public void dispatchedIncident(){
        this.status = IncidentStatus.DISPATCHED;
    }
    public void escalateSeverity(int newSeverity){
        this.severity = newSeverity;
    }
}