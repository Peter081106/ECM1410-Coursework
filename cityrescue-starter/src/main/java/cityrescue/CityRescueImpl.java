package cityrescue;

import cityrescue.enums.*;
import classes.*;
import cityrescue.exceptions.*;
import java.util.ArrayList;
import java.lang.Math;
import java.util.List;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {

    // TODO: add fields (map, arrays for stations/units/incidents, counters, tick, etc.)
    private ArrayList<ArrayList<GridStatus>> grid;
    private int current_tick=0;
    private final int MAX_UNITS=50;
    private List<Unit> units=new ArrayList<>();
    private int currentUnitID=0;

    private int maxStations = 20;
    private int maxUnits = 5;
    private int maxIncidents = 200;
    private List<Station> stations = new ArrayList<>();
    private int nextStationId = 1;
    private int stationCount = 0;
    private List<Incident> incidents = new ArrayList<>();
    private int nextIncindentId = 1;
    private int incidentCount = 0;


    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // TODO: implement
        if (width<0 || height<0){
            throw new InvalidGridException("Invalid values for grid initialisation used");
        }else{
            ArrayList<ArrayList<GridStatus>> tempGrid;
                for (int x=0;x<height;x++){
                    ArrayList<GridStatus> row=new ArrayList<>();
                    for (int y=0;y<width;y++){
                        row.add(GridStatus.OPEN);
                    }
                    tempGrid.add(row);
                }
            grid=tempGrid;
            CityMap map=new CityMap(tempGrid);  
            current_tick=0;
            stations=new ArrayList<>();
            units=new ArrayList<>();
            incidents=new ArrayList<>();
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override 
    public int[] getGridSize() {
        // TODO: implement
        int[] gridSize={grid.size(), grid.get(0).size()};
        return gridSize;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        if (x>grid.size() || y>grid.get(0).size() || x<0 || y<0){
            throw new InvalidLocationException("Location not in grid");
        }else{
            if (grid.get(x).get(y).equals(GridStatus.OPEN)){
                grid.get(x).set(y, GridStatus.ROADBLOCK);
                CityMap.refresh();
            } else{
                System.out.println("Location currently in use with a: "+grid.get(x).get(y));
            }
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        if (x>grid.size() || y>grid.get(0).size() || x<0 || y<0){
            throw new InvalidLocationException("Location not in grid");
        }else{
            if (grid.get(x).get(y).equals(GridStatus.ROADBLOCK)){
                grid.get(x).set(y, GridStatus.OPEN);
                CityMap.refresh()
            } else{
                System.out.println("No roadblock in selected location");
            }
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

        @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {

        if (name == null || name.trim().isEmpty()){
            throw new InvalidNameException();
        }
        if ((x > getGridSize()[0]) || (y > getGridSize()[1])){
            throw new InvalidLocationException("Value(s) for location not in range");
        }


        Station station = new station(nextStationId,name,x,y,maxUnits);
        stations.add(station);

        nextStationId++;
        stationCount++;

        return nextStationId-1;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        int index = -1;
        for(int i=0; i < stationCount;i++){
            if(stations.get(i).getStationId() == stationId){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new IDNotRecognisedException("Station ID not found");
        } else{
            if (stations.get(index).getunitCount() == 0){
                throw new IllegalStateException("No stations to be removed");
            } else{
                stations.remove(index);
                stationCount--;
            }
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        int index = -1;
        for(int i=0; i < stations.size();i++){
            if(stations.get(i).getStationId() == stationId){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new IDNotRecognisedException("Station ID not found");
        } else{
            if (maxUnits <= 0){
                throw new InvalidCapacityException("Max units cannot be 0 or less");
            }else{
                if (stations.get(i).getunitCount() >= maxUnits){
                    throw new InvalidCapacityException("Max capacity must be equal to or larger than current units of"+stations.get(i).getunitCount);
                } else{
                    stations.get(i).setMaxUnits(maxUnits);
                }
            }
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getStationIds() {
        int[] stationIdsList = new int[stations.size()];
        for(int i=0; i < stations.size();i++){
            stationIdsList[i] = stations.get(i).getStationId(); 
        }
        return stationIdsList;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException{
        // TODO: implement
        boolean idCheck=false;
        Station foundStation;
        for (int i=0; i<stations.size(); i++){
            if stations.get(i).getstationId().equals(stationId){
                idCheck=true;
                foundStation=stations.get(i);
                int[] stationLoc={foundStation.getx(), foundStation.gety()}
            }
        }
        if (idCheck==false){
            throw new IDNotRecognisedException("Station ID not recognised");
        } else{
            if (foundStation.getmaxUnits().equals(foundStation.getUnitCount())){
                throw new IllegalStateException("Max units in station already reached");
            } else{
                if (units.size()==MAX_UNITS){
                    throw new InvalidUnitException("Max units already reached")
                }else{
                    if type.equals(null){
                        throw new InvalidUnitException("Invalid Unit type used");
                    } else{
                        switch type{
                            case UnitType.AMBULANCE:
                                newUnit=new Ambulance(currentUnitID, foundStation.getstationId(), stationLoc);
                            case UnitType.POLICE_CAR:
                                newUnit=new PoliceCar(currentUnitID, foundStation.getstationId(), stationLoc);
                            case UnitType.FIRE_ENGINE:
                                newUnit=new FireEngine(currentUnitID, foundStation.getstationId(), stationLoc)
                        }
                        currentUnitID+=1;
                        units.add(newUnit)
                        foundStation.addUnit(newUnit);
                    }
                }
            }
        }
        return currentUnitID-1;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        boolean checkID=false;
        Unit foundUnit;
        for (int i=0; i<units.size(); i++){
            if (units.get(i).getUnitID().equals(unitID)){
                checkID=true;
                foundUnit=units.get(i);
                int arrayLoc=i
            }
        }
        if (checkID==false){
            throw new IDNotRecognisedException("Unit not found");
        } else{
            if (!(foundUnit.getStatus().equals(UnitStatus.IDLE) || foundUnit.getStatus().equals(UnitStatus.OUT_OF_SERVICE))){
                throw new IllegalStateException("Unit must free to decomission");
            }
        } else{
            int homeStation=foundUnit.getHome();
            Station foundStation;
            for (int x=0; x<stations.size(); x++){
                if stations.get(x).getstationId().equals(stationId){
                    foundStation=stations.get(x);
                }
            }
            foundStation.removeUnit(foundUnit.getUnitID());
            units.remove(arrayLoc)

        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        boolean checkStationID=false;
        boolean checkUnitID=false;
        Station foundStation;
        Unit foundUnit;
        for (int i=0; i<stations.size(); i++){
            if stations.get(i).getstationId().equals(newStationId){
                checkStationID=true;
                foundStation=stations.get(i);
                int[] stationLoc={foundStation.getheight(), foundStation.getwidth()}
            }
        }
        for (int x=0; x<units.size(); i++){
            if (units.get(i).getUnitID().equals(unitID)){
                checkUnitID=true;
                foundUnit=units.get(i);
        }
        if (checkStationID==false || checkunitID==false){
            throw new IDNotRecognisedException("Station ID or Unit ID not found");
        } else{
            if (foundStation.getUnitCount().equals(foundStation.getmaxUnits())){
                throw new IllegalStateException("Max station units already reached");
            } else{
                if (!(foundUnit.getStatus().equals(UnitStatus.IDLE))){
                    throw new IllegalStateException("Unit must be idle to transfer station");
                } else{
                    foundUnit.setHome(foundStation.getstationId());
                    foundUnit.setLocation(stationLoc);
                }
            }
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        Unit foundUnit;
        boolean checkUnitID=false;
        for (int x=0; x<units.size(); i++){
            if (units.get(i).getUnitID().equals(unitID)){
                checkUnitID=true;
                foundUnit=units.get(i);
        if (checkUnitID==false){
            throw new IDNotRecognisedException("Unit ID not found");
        } else{
            if (!(foundUnit.getStatus().equals(UnitStatus.IDLE) || foundUnit.getStatus().equals(UnitStatus.OUT_OF_SERVICE))){
                throw new IllegalStateException("Unit must be idle or out of service");
            }else{
                if outOfService==false{
                    foundUnit.setStatus(UnitStatus.OUT_OF_SERVICE);
                } else{
                    foundUnit.setStatus(UnitStatus.IDLE);
                }
            }
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        int[] unitIDs=new int[units.size()];
        for (int i=0; i<units.size(); i++){
            unitIDs[i]=units.get(i).getUnitId();
        }
        return unitIDs;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        String unitString;
        Unit foundUnit;
        boolean checkUnitID=false;
        for (int x=0; x<units.size(); x++){
            if (units.get(x).getUnitID().equals(unitID)){
                checkUnitID=true;
                foundUnit=units.get(i);
        if (checkUnitID==false){
            throw new IDNotRecognisedException("Unit ID not found");
        } else{
            String newLoc;
            String xLoc=foundUnit.getCurrentLocation()[1];
            String yLoc=foundUnit.getCurrentLocation()[0];
            newLoc="("+xLoc+","+yLoc+")"
            unitString="TYPE="+foundUnit.getType()+" HOME="+foundUnit.getHome()+" LOC="+newLoc+" STATUS="+foundUnit.getStatus()+" INCIDENT="+foundUnit.getWorkingIncident();
            if (!(foundUnit.getTicksToComplete()==0)){
                unitString+=" WORK="+foundUnit.getTicksToComplete;
            }
        }
        return unitString;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        if(type == null){
            throw new InvalidSeverityException("Invalid type used for incident");
        }else{
            if (severity <= 1 || severity >=5){
                throw new InvalidSeverityException("Invalid value used for severity");
            }else{
                if ((x > getGridSize()[0]) || (y > getGridSize()[1])){
                    throw new InvalidLocationException("Value(s) used for location are invalid");
                }else{
                    Incident incident = new incident(type, severity,  x,  y);
                    incidents.add(incident);
                    nextIncidentId++;
                    IncidentCount++;

        return nextIncidentId-1;
        

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        int index = -1;
        for(int i=0; i < incidentCount;i++){
            if(incidents.get(i).getincidentId() == incidentId){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new IDNotRecognisedException("Incident not found");
        } else{
            if (!(incidents.get(index).getStatus().equals(IncidentType.REPORTED) || incidents.get(index).getStatus().equals(IncidentType.DISPATCHED))){
                throw new IllegalStateException("Incident must be reported or dispatched to be cancelled");
            } else{
                if (incidents.get(index).getStatus().equals(IncidentType.DISPATCHED)){
                    int unitID=incidents.get(index).getUnitAssignedId()
                    for (int i=0; i<units.size(); i++){
                        if (units.get(i).getUnitID()==unitID){
                            units.get(i).setStatus(UnitStatus.IDLE)
                        }
                    }
                    incidents.get(index).releaseUnit();
                    incidents.get(index).cancelledIncident();
                }else{
                    incidents.get(index).cancelIncident();
                }
            }
        }
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        int index = -1;
        for(int i=0; i < incidentCount;i++){
            if(incidents.get(i).getincidentId() == incidentId){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new IDNotRecognisedException("ID not found");
        }else{
            if (newSeverity <= 1 || newSeverity >=5){
                throw new InvalidSeverityException("Value used for severity out of range");
            }else{
                if (incidents.get(index).getstatus().equals(IncidentType.RESOLVED) || incidents.get(index).getstatus().equals(IncidentType.CANCELLED) ){
                    throw new IllegalStateException("Incident can no longer be escalated as it has been resolved or cancelled");
                }else{
                    incidents.get(index).escalateSeverity(newSeverity);
                }
            }
        }
        throw new UnsupportedOperationException("Not implemented yet");
     }

    @Override
    public int[] getIncidentIds(){
        int[] incidentIdsList = new int[incidents.size()];
        for(int i=0; i < stations.size();i++){
            stationIdsList[i] = stations.get(i).getStationId(); 
        }
        return stationIdsList;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        int index = -1;
        for(int i=0; i < incidentCount;i++){
            if(incidents[i].getincidentId() == incidentId){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new IDNotRecognisedException();
        return "I#"+str(incidentId)+" TYPE="+str(incidents[index].gettype())+" SEV="+str(incidents[index].getseverity())+" LOC=("+str(incidents[index].getwidth())+","+str(incidents[index].getheight())+") STATUS="+str(incidents[index].getstatus())+" UNIT="+str(incidents[index].getunitAssignedId())
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void dispatch() {
        // creates a list of the indexes of the ids
        int largestDistance = 0
        ArrayList<Integer> shortestManhattan = new ArrayList<Integer>;
        ArrayList<Integer> sameIds = new ArrayList<Integer>;
        for(int i=0; i < incidentCount;i++){
            if (incidents[i].getstatus.equals((DISPATCHED)){
                manhattanDistance = (Math.abs(incidents[i].getheight()-incidents[i].getunitAssignedId().getCurrentLocation[0])) + (Math.abs(incidents[i].getwidth()-incidents[i].getunitAssignedId().getCurrentLocation[1]))
                if (manhattanDistance > largestDistance){
                    shortestManhattan.clear()
                    shortestManhattan.add(i);
                }
                if (manhattanDistance == largestDistance){
                    shortestManhattan.add(i);
                }
            }
<<<<<<< HEAD
        if (shortestManhattan.size() == 1){
            incidents[shortestManhattan[0]]
=======
>>>>>>> 9b7c27e9f521a1c0abd2b4de3acd8f22c0896b54
        }

        if shortestManhattan.length() == 1{
            lowestId = shortestManhattan[0]
        }

        if shortestManhattan.length() > 1{
            int lowestId = shortestManhattan[0];
            for(int i=0; i < shortestManhattan.length();i++){
                if (shortestManhattan[i] < lowestId){
                    lowestId = shortestManhattan[i];
                }
            }
        
        }

        throw new ("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        current_tick++
        ArrayList<Unit> enRouteUnits= new ArrayList<>();
        for (int i=0; i<units.size(); i++){
            if units.get(i).getStatus().equals(UnitStatus.EN_ROUTE){
                enRouteUnits.add(units.get(i))
            }
        }
        for (int x=0; x<enRouteUnits.size(); x++){
            currrentLoc=enRouteUnits.get(x).getCurrentLocation();
            ArrayList<int[]> moves=new ArrayList<>();
            for(int y=0; y<4; y++){
                switch y{
                    case 0:
                        if ((currentLoc[0]-1)>=0){
                            moves.add({currentLoc[0]-1, currentLoc[0]});
                        }
                    case 1:
                        if ((currentLoc[1]+1)<=getGridSize()[1]){
                            moves.add({currentLoc[0], currentLoc[0]+1});
                        }
                    case 2:
                        if ((currentLoc[0]+1)<=getGridSize()[0]){
                            moves.add({currentLoc[0]+1, currentLoc[0]});
                        }
                    case 3:
                        if ((currentLoc[1]-1)>=0){
                            moves.add({currentLoc[0], currentLoc[0]-1});
                        }
                ArrayList<Integer> manhattanDistance=new ArrayList<>();
                for (int j=0; j<moves.size(); j++){
                    int[] currentMove=moves.get(j)
                    manhattanDistance.add([(currentMove[0]-)])
                }
                }
            }
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        
        String finalGetStatusString = "TICK="+str(current_tick)+" \n
        STATIONS="+str(stationCount)+" UNITS="+unitIDs.length()+" INCIDENTS="+str(incidentCount)+" OBSTACLES="+str(getBlockedCells().size())+" \n
        INCIDENTS \n"
        for(int i=0; i < incidentCount;i++){
            finalGetStatusString = finalGetStatusString + viewIncident(incidents[i])+"\n"
        }
        finalGetStatusString = finalGetStatusString + "UNITS \n"
        for(int i=0; i < unitIds.length();i++){
            finalGetStatusString = finalGetStatusString + viewIncident(unitIds[i])+"\n"
        }

        
        

        
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
