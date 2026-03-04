package cityrescue;

import cityrescue.enums.*;
import classes.*;
import cityrescue.exceptions.*;
import java.util.ArrayList;

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

    int maxStations = 20;
    int maxUnits = 5;
    int maxIncidents = 200;
    Station[] stations = new Station[maxStations];
    int nextStationId = 1;
    int stationCount = 0;
    Incident[] incidents = new Incident[maxIncidents];
    int nextIncindentId = 1;
    int incidentCount = 0;


    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // TODO: implement
        try {
            for (int x=0;x<height;x++){
                ArrayList<GridStatus> row=new ArrayList<>();
                for (int y=0;y<width;y++){
                    row.add(GridStatus.OPEN);
                }
                grid.add(row);
            }
            CityMap map=new CityMap(grid);
        } catch (InvalidGridException e){
            System.out.println("Invalid value(s) used for grid initialisation");
        }        
        current_tick=0;
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
        try{
            if (grid.get(x).get(y).equals(GridStatus.OPEN)){
                grid.get(x).set(y, GridStatus.ROADBLOCK);
            } else{
                System.out.println("Location currently in use with a: "+grid.get(x).get(y));
            }
        } catch (InvalidLocationException e){
            System.out.println("Selected location not in bounds");
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // TODO: implement
        try{
            if (grid.get(x).get(y).equals(GridStatus.ROADBLOCK)){
                grid.get(x).set(y, GridStatus.OPEN);
            } else{
                System.out.println("No roadblock in selected location");
            }
        }catch (InvalidLocationException e) {
            System.out.println("Selected location out of bounds");
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

        @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {

        if (name == null || name.trim().isEmpty()){
            throw new InvalidNameException();
        }
        if ((x > getGridSize()[0]) || (y > getGridSize()[1])){
            throw new InvalidLocationException();
        }


        Station station = new station(nextStationId,name,x,y,maxUnits);
        stations[nextStationId] = station;

        nextStationId++;
        stationCount++;

        return nextStationId-1;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        int index = -1;
        for(int i=0; i < stationCount;i++){
            if(stations[i].getStationId() == stationId){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new IDNotRecognisedException();
        }
        if (stations[index].getunitCount == 0){
            return IllegalStateException();
        }
        for(int i=index; i < stationCount-1;i++){
            stations[i] = stations[i+1];
        }
        stations[size(stations)-1] = null;
        stationCount--;
        

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        int index = -1;
        for(int i=0; i < size(stations);i++){
            if(stations[i].getStationId() == stationId){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new IDNotRecognisedException();
        }
        if (maxUnits < 0){
            throw new InvalidCapacityException();
        }
        if (stations[index].getunitCount() > maxUnits){
            throw new InvalidCapacityException();
        }

        stations[index] = stations[index].setmaxUnits(maxUnits);
        return "Capacity Updated.";


        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getStationIds() {
        int[] stationIdsList = size(stations);
        for(int i=0; i < size(stations);i++){
            stationIdsList[i] = stations[i].getStationId(); 
        }
        return stationIdsList;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        if(type == null){
            throw new Invalid();
        }
        if (severity <= 1 || severity >=5){
            throw new InvalidSeverityException();
        }
        //need to code if location is in bounds and not blocked

        Incident incident = new incident(type, severity,  x,  y);
        incidents[nextIncindentId] = incident;

        nextIncidentId++;
        IncidentCount++;

        return nextStationId-1;
        

        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        int index = -1;
        for(int i=0; i < incidentCount;i++){
            if(incidents[i].getincidentId() == incidentId){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new IDNotRecognisedException();
        }
        if (!(incidents[index].getStatus().equals(REPORTED))){
            incidents[index].cancelledIncident();
        }
        if (!(incidents[index].getStatus().equals(DISPATCHED))){
            incidents[index].releaseUnit();
            incidents[index].cancelledIncident();
        }
        else{
            throw IllegalStateException();
        }


    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        int index = -1;
        for(int i=0; i < incidentCount;i++){
            if(incidents[i].getincidentId() == incidentId){
                index = i;
                break;
            }
        }
        if (index == -1){
            throw new IDNotRecognisedException();
        }
        if (newSeverity <= 1 || newSeverity >=5){
            throw new InvalidSeverityException();
        }
        if (incidents[index].getstatus()== RESOLVED || incidents[index].getstatus()== CANCELLED ){
            throw new IllegalStateException();
        }
        incidents[index].escalateSeverity(newSeverity);
        return "Severity updated";


        throw new UnsupportedOperationException("Not implemented yet");
     }

    @Override
    public int[] getIncidentIds(){
        int[] incidentIdsList = incidentSize;
        for(int i=0; i < size(stations);i++){
            stationIdsList[i] = stations[i].getStationId(); 
        }
        return stationIdsList;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
