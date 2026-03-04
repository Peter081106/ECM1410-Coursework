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


        Station station = new station(nextStationId,name,x,y,5);
        stations[nextStationId-1] = station;

        nextStationId++;

        return nextStationId-1;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        int index = -1;
        for(int i=0; i < size(stations);i++){
            if(stations[i].getStationId() == stationId){
                index = i;
                break;
            }
            if (index == -1){
                throw new IDNotRecognisedException();
            }
            
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        
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
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
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
