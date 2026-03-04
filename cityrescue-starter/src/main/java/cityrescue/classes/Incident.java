public Incident{
    private IncidentType type;
    private int incidentId
    private int severity;
    private IncidentStatus status;
    private int unitAssignedId;
    private int x;
    private int y;

    public int gettype(){
        return type;
    }
    public int getincidentId(){
        return incidentId;
    }
    public int getseverity(){
        return severity;
    }
    public int getstatus(){
        return status;
    }
    public int getx(){
        return x;
    }
    public int gety(){
        return y;
    }
    public int unitAssignedId(){
        return unitAssignedId
    }

    public Incident(IncidentType type, int severity, int x, int y){
        this.type = type;
        this.status = IncidentStatus.REPORTED;
        this.severity = severity;
        this.x = x;
        this.y = y;
    }
    public void cancelledIncident(){
        this.status = IncidentStatus.CANCELLED;
    }
    public void dispatchedIncident(){
        this.status = IncidentStatus.DISPATCHED;
    }
    public void releaseUnit(){
        this.unitAssignedId = null;
    }
    public void escalateSeverity(newSeverity){
        this.severity = newSeverity;
    }
}