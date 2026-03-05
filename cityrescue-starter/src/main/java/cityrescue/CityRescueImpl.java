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
    }

    @Override 
    public int[] getGridSize() {
        // TODO: implement
        int[] gridSize={grid.size(), grid.get(0).size()};
        return gridSize;
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
    }

    @Override
    public int[] getStationIds() {
        int[] stationIdsList = new int[stations.size()];
        for(int i=0; i < stations.size();i++){
            stationIdsList[i] = stations.get(i).getStationId(); 
        }
        return stationIdsList;
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
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        int[] unitIDs=new int[units.size()];
        for (int i=0; i<units.size(); i++){
            unitIDs[i]=units.get(i).getUnitId();
        }
        return unitIDs;
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
            String initialString="#U"+foundUnit.getUnitID()+" TYPE="+foundUnit.getType()+" HOME="+foundUnit.getHome()+" LOC="+newLoc+" STATUS="+foundUnit.getStatus()+" INCIDENT="+foundUnit.getWorkingIncident();
            String additionalString1;
            String additionalString2;
            if (foundUnit.getWorkingIncident().equals(null)){
                additionalString1=" INCIDENT=-";
            }else{
                additionalString1=" INCIDENT="+foundUnit.getWorkingIncident();
            }
            if (!(foundUnit.getTicksToComplete()==0)){
                additionalString2=" WORK="+foundUnit.getTicksToComplete+"\n";
            }else{
                additionalString2="\n"
            }
            String outputString=initialString.concat(additionalString1).concat(additionalString2)
        }
        return outputString;
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
     }

    @Override
    public int[] getIncidentIds(){
        int[] incidentIdsList = new int[incidents.size()];
        for(int i=0; i < stations.size();i++){
            stationIdsList[i] = stations.get(i).getStationId(); 
        }
        return stationIdsList;
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        int index = -1;
        for(int i=0; i < incidentCount;i++){
            if(incidents.get(i).getincidentId() == incidentId){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new IDNotRecognisedException("Incident ID not found");
        }else{
            Incident foundIncident=incidents.get(index);
            String initialString= "I#"+incidentId+" TYPE="+foundIncident.gettype()+" SEV="+foundIncident.getseverity()+" LOC=("+foundIncident.getwidth()+","+foundIncident.getheight()+") STATUS="+foundIncident.getstatus();
            String additionalString;
            if (!(foundIncident.getUnitAssignedId().equals(null))){
                additional=" UNIT="+foundIncident.getUnitAssignedId()+"\n";
            }else{
                additionalString=" UNIT=-\n";
            }
            String outputString=initialString.concat(additionalString);
            return outputString;
        }
    }

    @Override
    public void dispatch() {
        ArrayList<Incident> reportedIncidents=new ArrayList<>();
        for (int i=0; i<incidents.size(); i++){
            if (incidents.get(i).getstatus().equals(IncidentStatus.REPORTED)){
                reportedIncidents.add(incidents.get(i))
            }
        }
        for (int x=0; x<reportedIncidents.size(); x++){
            ArrayList<Unit> availableUnits=new ArrayList<>();
            Incident currentIncident=reportedIncidents.get(x);
            UnitType requiredUnit;
            switch currentIncident.gettype(){
                case IncidentType.MEDICAL:
                    requiredUnit=UnitType.AMBULANCE;
                case IncidentType.FIRE:
                    requiredUnit=UnitType.FIRE_ENGINE;
                case IncidentType.CRIME:
                    requiredUnit=UnitType.POLICE_CAR;
            }
            for (int y=0; y<units.size(); y++){
                if ((units.get(y).getType().equals(requiredUnit)) && units.get(y).getStatus().equals(UnitStatus.IDLE)){
                    availableUnits.add(units.get(y));
                }
            }
            if (availableUnits.size()>0){
                ArrayList<Integer> manhattanDistances=new ArrayList<>(); 
                int smallestManhatten;
                int smallestManhattenLoc;
                for (int z=0; availableUnits.size(); z++){
                    int[] currentLoc=availableUnits.get(z).getCurrentLocation();
                    int[] incidentLoc={currentIncident.getheight(), currentIncident.getwidth()};
                    int currentManhatten=(Math.abs(currentLoc[0]-incidentLoc[0]))+(Math.abs(currentLoc[1]-incidentLoc[1]));
                    manhattanDistance.add(currentManhatten);
                    if (manhattanDistance.size()==1){
                        smallestManhatten=currentManhatten;
                        smallestManhattenLoc=0;
                    } else{
                        if currentManhatten<smallestManhatten{
                            smallestManhatten=currentManhatten;
                            smallestManhattenLoc=z;
                        }
                    }
                Unit dispatchUnit=availableUnits.get(smallestManhatten);
                dispatchUnit.setStatus(UnitStatus.EN_ROUTE);
                currentIncident.setstatus(IncidentStatus.DISPATCHED);
                dispatchUnit.setWorkingIncident(currentIncident.getincidentId());
                currentIncident.setUnitAssignedId(dispatchUnit.getUnitID())
                }
            }
        }
        throw new ("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        current_tick++;
        ArrayList<Unit> enRouteUnits= new ArrayList<>();
        ArrayList<Unit> atSceneUnits= new ArrayList<>();
        ArrayList<int[]> incidentLocs=new ArrayList<>();
        ArrayList<Incident> enRouteIncidents=new ArrayList<>();
        ArrayList<Incident> inProgressIncidents=new ArrayList<>();
        for (int i=0; i<units.size(); i++){
            if units.get(i).getStatus().equals(UnitStatus.EN_ROUTE){
                enRouteUnits.add(units.get(i));
                for (int z=0; z<incidents.size(); z++){
                    if units.get(i).getWorkingIncident().equals(incidents.get(z).getincidentId()){
                        incidentLocs.add({incidents.get(z).getheight(), incidents.get(z).getwidth()});
                        enRouteIncidents.add(incidents.get(z));
                    }
                }
            }
            if units.get(i).getStatus().equals(UnitStatus.AT_SCENE){
                atSceneUnits.add(units.get(i))
                for (int z=0; z<incidents.size(); z++){
                    if units.get(i).getWorkingIncident().equals(incidents.get(z).getincidentId()){
                        inProgressIncidents.add(incidents.get(z));
                    }
                }
            }
        }
        for (int x=0; x<enRouteUnits.size(); x++){
            currrentLoc=enRouteUnits.get(x).getCurrentLocation();
            currentIncidentLoc=incidentLocs.get(x);
            ArrayList<int[]> moves=new ArrayList<>();
            ArrayList<Integer> directions=new ArrayList<>();
            for(int y=0; y<4; y++){
                switch y{
                    case 0:
                        if ((currentLoc[0]-1)>=0){
                            if (CityMap.isMoveLegal({currentLoc[0]-1, currentLoc[1]})){
                                moves.add({currentLoc[0]-1, currentLoc[1]});
                                directions.add(1);
                            }
                        }
                    case 1:
                        if ((currentLoc[1]+1)<=getGridSize()[1]){
                            if (CityMap.isMoveLegal({currentLoc[0], currentLoc[1]+1})){
                                moves.add({currentLoc[0], currentLoc[1]+1});
                                directions.add(2);
                            }
                        }
                    case 2:
                        if ((currentLoc[0]+1)<=getGridSize()[0]){
                            if (CityMap.isMoveLegal({currentLoc[0]+1, currentLoc[1]})){
                                moves.add({currentLoc[0]+1, currentLoc[1]});
                                directions.add(3);
                            }
                        }
                    case 3:
                        if ((currentLoc[1]-1)>=0){
                            if (CityMap.isMoveLegal({currentLoc[0], currentLoc[1]-1})){
                                moves.add({currentLoc[0], currentLoc[1]-1});
                                directions.add(4);
                            }
                        }
                }
            }
            ArrayList<int[]> manhattanDistance=new ArrayList<>();
            for (int j=0; j<moves.size(); j++){
                int[] currentMove=moves.get(j);
                manhattanDistance.add(Math.abs(currentMove[0]-currentIncidentLoc[0])+(Math.abs(currentMove[1]-currentIncidentLoc[1])));
                for (int k=0; k<manhattanDistance-1; k++){
                    if (manhattanDistance.get(k)<manhattanDistance.get(k+1)){
                        int tempDist=manhattanDistance.get(k+1);
                        String tempDir=directions.get(k+1);
                        int[] tempMove=moves.get(k+1);
                        manhattanDistance.set(k+1, manhattanDistance.get(k));
                        directions.set(k+1, directions.get(k));
                        moves.set(k+1, moves.get(k));
                        manhattanDistance.set(k, tempDist);
                        directions.set(k, tempDir);
                        moves.set(k, tempMove);
                    }
                }
            }
            int[] chosenMove;
            if (manhattanDistance.size()>0){
                if (manhattanDistance.get(0)==manhattanDistance.get(1)){
                    if (directions.get(0)>directions.get(1)){
                        chosenMove=moves.get(0);
                    }else{
                        chosenMove=moves.get(0);
                    }
                }
            }else{
                chosenMove=currentLoc
            }
            enRouteUnits.get(x).setLocation(chosenMove)
            if chosenMove.equals(incidentLocs.get(x)){
                enRouteUnits.get(x).setStatus(UnitStatus.AT_SCENE)
                enRouteIncidents.get(x).setstatus(IncidentStatus.IN_PROGRESS)
            }
        }
        for (int q=0; q<atSceneUnits.size(); q++){
            int workRemaining=atSceneUnits.get(q).getTicksToComplete();
            if (workRemaining==0){
                atSceneUnits.get(q).setStatus(UnitStatus.IDLE);
                inProgressIncidents.get(q).setstatus(IncidentStatus.RESOLVED)
                atSceneUnits.get(q).setWorkingIncident(null)
            }
        }
    }

    @Override
    public String getStatus() {
        String tickString="TICK="+current_tick+"\n"
        String countString="STATIONS="+stations.size()+" UNITS="+units.size()+" INCIDENTS="+incidents.size()+" OBSTACLES="+CityMap.getBlockedCells().size()+"\n";
        String incidentHeader="INCIDENTS\n";
        String incidentsString;
        for (int i=0; i<incidents.size(); i++){
            incidentsString.concat(viewIncident(incidents.get(i).getincidentId()));
        }
        String unitsHeader="UNITS\n";
        String unitsString;
        for (int x=0; i<units.size(); i++){
            unitsString.concat(viewUnit(units.get(i).getUnitID()));
        }
        String outputString=tickString+countString+incidentHeader+incidentsString+unitsHeader+unitsString;
        return outputString;
    }
}
