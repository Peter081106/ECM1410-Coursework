package cityrescue;

import java.util.ArrayList;
import java.util.List;

import cityrescue.classes.Ambulance;
import cityrescue.classes.CityMap;
import cityrescue.classes.FireEngine;
import cityrescue.classes.Incident;
import cityrescue.classes.PoliceCar;
import cityrescue.classes.Station;
import cityrescue.classes.Unit;
import cityrescue.enums.GridStatus;
import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;
import cityrescue.exceptions.IDNotRecognisedException;
import cityrescue.exceptions.InvalidCapacityException;
import cityrescue.exceptions.InvalidGridException;
import cityrescue.exceptions.InvalidLocationException;
import cityrescue.exceptions.InvalidNameException;
import cityrescue.exceptions.InvalidSeverityException;
import cityrescue.exceptions.InvalidUnitException;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {

    // TODO: add fields (map, arrays for stations/units/incidents, counters, tick, etc.)
    private int current_tick=0;
    private final int MAX_UNITS=50;
    private List<Unit> units=new ArrayList<>();
    private int currentUnitID=1;

    private int maxStations = 20;
    private int maxUnits = 5;
    private int maxIncidents = 200;
    private List<Station> stations = new ArrayList<>();
    private int nextStationId = 1;
    private int stationCount = 0;
    private List<Incident> incidents = new ArrayList<>();
    private int nextIncindentId = 1;
    private int incidentCount = 0;
    private ArrayList<ArrayList<GridStatus>> grid= new ArrayList<>();
    private CityMap map;


    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // TODO: implement
        if (width<0 || height<0){
            throw new InvalidGridException("Invalid values for grid initialisation used");
        }else{
            grid=new ArrayList<>();
            for (int i=0; i<height; i++) {
                ArrayList<GridStatus> row=new ArrayList<>();
                for (int x=0; x<width; x++) {
                    row.add(GridStatus.OPEN);
                }
                grid.add(row);
            }
            map=new CityMap(height, width);  
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
                map.refresh(grid);
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
                map.refresh(grid);
            } else{
                System.out.println("No roadblock in selected location");
            }
        }
    }

        @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {

        if (name == null || name.trim().isEmpty()){
            throw new InvalidNameException("Invalid name used for station");
        }
        if ((x > getGridSize()[0]) || (y > getGridSize()[1])){
            throw new InvalidLocationException("Value(s) for location not in range");
        }


        Station station = new Station(nextStationId,name,x,y,maxUnits);
        stations.add(station);

        nextStationId++;
        stationCount++;

        return nextStationId-1;
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        int index = -1;
        for(int i=0; i < stationCount;i++){
            if(stations.get(i).getstationId() == stationId){
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
            if(stations.get(i).getstationId() == stationId){
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
                if (stations.get(index).getunitCount() >= maxUnits){
                    throw new InvalidCapacityException("Max capacity must be equal to or larger than current units of"+stations.get(index).getunitCount());
                } else{
                    stations.get(index).setmaxUnits(maxUnits);
                }
            }
        }
    }

    @Override
    public int[] getStationIds() {
        int[] stationIdsList = new int[stations.size()];
        for(int i=0; i < stations.size();i++){
            stationIdsList[i] = stations.get(i).getstationId(); 
        }
        return stationIdsList;
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException{
        // TODO: implement
        boolean idCheck=false;
        int index=-1;
        int[] stationLoc=new int[2];
        for (int i=0; i<stations.size(); i++){
            if (stations.get(i).getstationId()==stationId){
                idCheck=true;
                index=i;
                stationLoc[0]=stations.get(index).getheight();
                stationLoc[1]=stations.get(index).getwidth();
            }
        }
        if (idCheck==false){
            throw new IDNotRecognisedException("Station ID not recognised");
        } else{
            if (stations.get(index).getmaxUnits()==stations.get(index).getunitCount()){
                throw new IllegalStateException("Max units in station already reached");
            } else{
                if (units.size()==MAX_UNITS){
                    throw new InvalidUnitException("Max units already reached");
                }else{
                    if (!(type instanceof UnitType)){
                        throw new InvalidUnitException("Invalid Unit type used");
                    } else{
                        Unit newUnit;
                        switch (type){
                            case AMBULANCE:
                                newUnit=new Ambulance(currentUnitID, stations.get(index).getstationId(), stationLoc);
                                break;
                            case POLICE_CAR:
                                newUnit=new PoliceCar(currentUnitID, stations.get(index).getstationId(), stationLoc);
                                break;
                            case FIRE_ENGINE:
                                newUnit=new FireEngine(currentUnitID, stations.get(index).getstationId(), stationLoc);
                                break;
                            default:
                                throw new InvalidUnitException("Invalid Unit type");
                        }
                        units.add(newUnit);
                        stations.get(index).addUnit(newUnit);
                        currentUnitID+=1;
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
        int index=-1;
        for (int i=0; i<units.size(); i++){
            if (units.get(i).getUnitId()==unitId){
                checkID=true;
                index=i;
            }
        }
        if (checkID==false){
            throw new IDNotRecognisedException("Unit not found");
        } else{
            if (!(units.get(index).getStatus().equals(UnitStatus.IDLE) || units.get(index).getStatus().equals(UnitStatus.OUT_OF_SERVICE))){
                throw new IllegalStateException("Unit must free to decomission");
            } else{
            int homeStation=units.get(index).getHome();
            int stationIndex=-1;
            for (int x=0; x<stations.size(); x++){
                if (stations.get(x).getstationId()==homeStation){
                    stationIndex=x;
                }
            }
            stations.get(stationIndex).removeUnit(units.get(index).getUnitId());
            units.remove(index);
            }
        }
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        boolean checkStationID=false;
        boolean checkUnitID=false;
        int stationIndex=-1;
        int unitIndex=-1;
        int[] stationLoc=new int[2];
        for (int i=0; i<stations.size(); i++){
            if (stations.get(i).getstationId()==newStationId){
                checkStationID=true;
                stationIndex=i;
                stationLoc=new int[]{stations.get(stationIndex).getheight(), stations.get(stationIndex).getwidth()};
            }
        }
        for (int x=0; x<units.size(); x++){
            if (units.get(x).getUnitId()==unitId){
                checkUnitID=true;
                unitIndex=x;
            }
        }
        if (checkStationID==false || checkUnitID==false){
            throw new IDNotRecognisedException("Station ID or Unit ID not found");
        } else{
            if (stations.get(stationIndex).getunitCount()==stations.get(stationIndex).getmaxUnits()){
                throw new IllegalStateException("Max station units already reached");
            } else{
                if (!(units.get(unitIndex).getStatus().equals(UnitStatus.IDLE))){
                    throw new IllegalStateException("Unit must be idle to transfer station");
                } else{
                    units.get(unitIndex).setHome(stations.get(stationIndex).getstationId());
                    units.get(unitIndex).setCurrentLocation(stationLoc);
                }
            }
        }
    }


    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        int unitIndex=-1;
        boolean checkUnitID=false;
        for (int x=0; x<units.size(); x++){
            if (units.get(x).getUnitId()==unitId){
                checkUnitID=true;
                unitIndex=x;
            }
        }
        if (checkUnitID==false){
            throw new IDNotRecognisedException("Unit ID not found");
        } else{
            if (!(units.get(unitIndex).getStatus().equals(UnitStatus.IDLE) || units.get(unitIndex).getStatus().equals(UnitStatus.OUT_OF_SERVICE))){
                throw new IllegalStateException("Unit must be idle or out of service");
            }else{
                if (outOfService==false){
                    units.get(unitIndex).setStatus(UnitStatus.OUT_OF_SERVICE);
                } else{
                    units.get(unitIndex).setStatus(UnitStatus.IDLE);
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
        String outputString="";
        int unitIndex=-1;
        boolean checkUnitID=false;
        for (int x=0; x<units.size(); x++){
            if (units.get(x).getUnitId()==unitId){
                checkUnitID=true;
                unitIndex=x;
            }
        }
        if (checkUnitID==false){
            throw new IDNotRecognisedException("Unit ID not found");
        } else{
            String newLoc;
            int xLoc=units.get(unitIndex).getCurrentLocation()[1];
            int yLoc=units.get(unitIndex).getCurrentLocation()[0];
            newLoc="("+xLoc+","+yLoc+")";
            String initialString="U#"+units.get(unitIndex).getUnitId()+" TYPE="+units.get(unitIndex).getType()+" HOME="+units.get(unitIndex).getHome()+" LOC="+newLoc+" STATUS="+units.get(unitIndex).getStatus()+" INCIDENT="+units.get(unitIndex).getWorkingIncident();
            String additionalString1;
            String additionalString2;
            if (units.get(unitIndex).getWorkingIncident()==-1){
                additionalString1=" INCIDENT=-";
            }else{
                additionalString1=" INCIDENT="+units.get(unitIndex).getWorkingIncident();
            }
            if (!(units.get(unitIndex).getTicksToComplete()==0)){
                additionalString2=" WORK="+units.get(unitIndex).getTicksToComplete()+"\n";
            }else{
                additionalString2="\n";
            }
            outputString=initialString.concat(additionalString1).concat(additionalString2);
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
                    Incident incident = new Incident(type, severity,  x,  y);
                    incidents.add(incident);
                    nextIncindentId++;
                    incidentCount++;
                }
            }
        }
        return nextIncindentId-1;
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
            if (!(incidents.get(index).getstatus().equals(IncidentStatus.REPORTED) || incidents.get(index).getstatus().equals(IncidentStatus.DISPATCHED))){
                throw new IllegalStateException("Incident must be reported or dispatched to be cancelled");
            } else{
                if (incidents.get(index).getstatus().equals(IncidentStatus.DISPATCHED)){
                    int unitID=incidents.get(index).getUnitAssignedId();
                    for (int i=0; i<units.size(); i++){
                        if (units.get(i).getUnitId()==unitID){
                            units.get(i).setStatus(UnitStatus.IDLE);
                        }
                    }
                    incidents.get(index).releaseUnit();
                    incidents.get(index).cancelledIncident();
                }else{
                    incidents.get(index).cancelledIncident();
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
                if (incidents.get(index).getstatus().equals(IncidentStatus.RESOLVED) || incidents.get(index).getstatus().equals(IncidentStatus.CANCELLED) ){
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
        for(int i=0; i < incidents.size();i++){
            incidentIdsList[i] = incidents.get(i).getincidentId(); 
        }
        return incidentIdsList;
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
            if (!(foundIncident.getUnitAssignedId()==-1)){
                additionalString=" UNIT="+foundIncident.getUnitAssignedId()+"\n";
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
                reportedIncidents.add(incidents.get(i));
            }
        }
        for (int x=0; x<reportedIncidents.size(); x++){
            ArrayList<Unit> availableUnits=new ArrayList<>();
            Incident currentIncident=reportedIncidents.get(x);
            UnitType requiredUnit=null;
            switch (currentIncident.gettype()){
                case MEDICAL:
                    requiredUnit=UnitType.AMBULANCE;
                    break;
                case FIRE:
                    requiredUnit=UnitType.FIRE_ENGINE;
                    break;
                case CRIME:
                    requiredUnit=UnitType.POLICE_CAR;
                    break;
            }
            for (int y=0; y<units.size(); y++){
                if ((units.get(y).getType().equals(requiredUnit)) && units.get(y).getStatus().equals(UnitStatus.IDLE)){
                    availableUnits.add(units.get(y));
                }
            }
            if (availableUnits.size()>0){
                ArrayList<Integer> manhattanDistances=new ArrayList<>(); 
                int smallestManhatten=Integer.MAX_VALUE;
                int smallestManhattenLoc=0;
                for (int z=0; z<availableUnits.size(); z++){
                    int[] currentLoc=availableUnits.get(z).getCurrentLocation();
                    int[] incidentLoc={currentIncident.getheight(), currentIncident.getwidth()};
                    int currentManhatten=(Math.abs(currentLoc[0]-incidentLoc[0]))+(Math.abs(currentLoc[1]-incidentLoc[1]));
                    manhattanDistances.add(currentManhatten);
                    if (manhattanDistances.size()==1){
                        smallestManhatten=currentManhatten;
                        smallestManhattenLoc=0;
                    } else{
                        if (currentManhatten<smallestManhatten){
                            smallestManhatten=currentManhatten;
                            smallestManhattenLoc=z;
                        }
                    }
                Unit dispatchUnit=availableUnits.get(smallestManhattenLoc);
                dispatchUnit.setStatus(UnitStatus.EN_ROUTE);
                currentIncident.setstatus(IncidentStatus.DISPATCHED);
                dispatchUnit.setWorkingIncident(currentIncident.getincidentId());
                currentIncident.setUnitAssignedId(dispatchUnit.getUnitId());
                }
            }
        }
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
            if (units.get(i).getStatus().equals(UnitStatus.EN_ROUTE)){
                enRouteUnits.add(units.get(i));
                for (int z=0; z<incidents.size(); z++){
                    if ((units.get(i).getWorkingIncident()==incidents.get(z).getincidentId())){
                        int[] currentLoc=new int[]{incidents.get(z).getheight(), incidents.get(z).getwidth()};
                        incidentLocs.add(currentLoc);
                        enRouteIncidents.add(incidents.get(z));
                    }
                }
            }
            if (units.get(i).getStatus().equals(UnitStatus.AT_SCENE)){
                atSceneUnits.add(units.get(i));
                for (int z=0; z<incidents.size(); z++){
                    if (units.get(i).getWorkingIncident()==(incidents.get(z).getincidentId())){
                        inProgressIncidents.add(incidents.get(z));
                    }
                }
            }
        }
        for (int x=0; x<enRouteUnits.size(); x++){
            int[] currentLoc=enRouteUnits.get(x).getCurrentLocation();
            int[] currentIncidentLoc=incidentLocs.get(x);
            ArrayList<int[]> moves=new ArrayList<>();
            ArrayList<Integer> directions=new ArrayList<>();
            for(int y=0; y<4; y++){
                switch (y){
                    case 0:
                        if ((currentLoc[0]-1)>=0){
                            int[] newLoc=new int[]{currentLoc[0]-1, currentLoc[1]};
                            if (map.isMoveLegal(newLoc)){
                                moves.add(newLoc);
                                directions.add(1);
                            }
                        }
                        break;
                    case 1:
                        if ((currentLoc[1]+1)<getGridSize()[1]){
                            int[] newLoc=new int[]{currentLoc[0], currentLoc[1]+1};
                            if (map.isMoveLegal(newLoc)){
                                moves.add(newLoc);
                                directions.add(2);
                            }
                        }
                        break;
                    case 2:
                        if ((currentLoc[0]+1)<getGridSize()[0]){
                            int[] newLoc=new int[]{currentLoc[0]+1, currentLoc[1]};
                            if (map.isMoveLegal(newLoc)){
                                moves.add(newLoc);
                                directions.add(3);
                            }
                        }
                        break;
                    case 3:
                        if ((currentLoc[1]-1)>=0){
                            int[] newLoc=new int[]{currentLoc[0], currentLoc[1]-1};
                            if (map.isMoveLegal(newLoc)){
                                moves.add(newLoc);
                                directions.add(4);
                            }
                        }
                        break;
                }
            }
            ArrayList<Integer> manhattanDistance=new ArrayList<>();
            for (int j=0; j<moves.size(); j++){
                int[] currentMove=moves.get(j);
                manhattanDistance.add(Math.abs((currentMove[0]-currentIncidentLoc[0]))+(Math.abs((currentMove[1]-currentIncidentLoc[1]))));
                for (int k=0; k<manhattanDistance.size()-1; k++){
                    if (manhattanDistance.get(k)<manhattanDistance.get(k+1)){
                        int tempDist=manhattanDistance.get(k+1);
                        int tempDir=directions.get(k+1);
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
            int[] chosenMove=new int[2];
            if (manhattanDistance.size()>0){
                if (manhattanDistance.get(0)==manhattanDistance.get(1) && manhattanDistance.size()>1){
                    if (directions.get(0)<directions.get(1)){
                        chosenMove=moves.get(1);
                    }else{
                        chosenMove=moves.get(0);
                    }
                }
            }else{
                chosenMove=currentLoc;
            }
            enRouteUnits.get(x).setCurrentLocation(chosenMove);
            if (java.util.Arrays.equals(chosenMove, incidentLocs.get(x))){
                enRouteUnits.get(x).setStatus(UnitStatus.AT_SCENE);
                enRouteIncidents.get(x).setstatus(IncidentStatus.IN_PROGRESS);
            }
        }
        for (int q=0; q<atSceneUnits.size(); q++){
            int workRemaining=atSceneUnits.get(q).getTicksToComplete();
            if (workRemaining==0){
                atSceneUnits.get(q).setStatus(UnitStatus.IDLE);
                inProgressIncidents.get(q).setstatus(IncidentStatus.RESOLVED);
                atSceneUnits.get(q).setWorkingIncident(-1);
            }
        }
    }

    @Override
    public String getStatus(){
        String tickString="TICK="+current_tick+"\n";
        String countString="STATIONS="+stations.size()+" UNITS="+units.size()+" INCIDENTS="+incidents.size()+" OBSTACLES="+map.getBlockedCells().size()+"\n";
        String incidentHeader="INCIDENTS\n";
        String incidentsString="";
        for (int i=0; i<incidents.size(); i++){
            Incident foundIncident=incidents.get(i);
            String initialString= "I#"+foundIncident.getincidentId()+" TYPE="+foundIncident.gettype()+" SEV="+foundIncident.getseverity()+" LOC=("+foundIncident.getwidth()+","+foundIncident.getheight()+") STATUS="+foundIncident.getstatus();
            String additionalString;
            if (!(foundIncident.getUnitAssignedId()==-1)){
                additionalString=" UNIT="+foundIncident.getUnitAssignedId()+"\n";
            }else{
                additionalString=" UNIT=-\n";
            }
            String tempString1=initialString.concat(additionalString);
            incidentsString=incidentsString.concat(tempString1);
        }
        String unitsHeader="UNITS\n";
        String unitsString="";
        for (int x=0; x<units.size(); x++){
            String newLoc="";
            String finalUnitString="";
            int xLoc=units.get(x).getCurrentLocation()[1];
            int yLoc=units.get(x).getCurrentLocation()[0];
            newLoc="("+xLoc+","+yLoc+")";
            String initialString="U#"+units.get(x).getUnitId()+" TYPE="+units.get(x).getType()+" HOME="+units.get(x).getHome()+" LOC="+newLoc+" STATUS="+units.get(x).getStatus()+" INCIDENT="+units.get(x).getWorkingIncident();
            String additionalString1;
            String additionalString2;
            if (units.get(x).getWorkingIncident()==-1){
                additionalString1=" INCIDENT=-";
            }else{
                additionalString1=" INCIDENT="+units.get(x).getWorkingIncident();
            }
            if (!(units.get(x).getTicksToComplete()==0)){
                additionalString2=" WORK="+units.get(x).getTicksToComplete()+"\n";
            }else{
                additionalString2="\n";
            }
            finalUnitString=initialString.concat(additionalString1).concat(additionalString2);
            unitsString=unitsString.concat(finalUnitString);
        }
        String outputString=tickString+countString+incidentHeader+incidentsString+unitsHeader+unitsString;
        return outputString;
    }
}
