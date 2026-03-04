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
    final int MAX_UNITS=50
    private Unit[] units=[MAX_UNITS];
    private int currentUnitID=0;

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // TODO: implement
        if (width<0 || height<0){
            throw new InvalidGridException("Invalid values for grid initialisation used");
        }
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
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getStationIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException{
        // TODO: implement
        boolean idCheck=false;
        Station foundStation;
        for (int i=0; i<stations.length(); i++){
            if stations[i].getstationId().equals(stationId){
                idCheck=true;
                foundStation=stations[i];
                int[] stationLoc={foundStation.getx(), foundStation.gety()}
            }
        }
        if (idCheck==false){
            throw new IDNotRecognisedException("Station ID not recognised");
        } else{
            if (foundStation.getmaxUnits().equals(foundStation.getUnitCount())){
                throw new IllegalStateException("Max units in station already reached");
            } else{
                int nullLoc=-1;
                boolean endwhile=false;
                int index=0;
                while (endwhile==false && index<units.length()){
                    if (units[index].equals(null)){
                        nullLoc=index;
                        endwhile=true;
                    }
                }
                if (nullLoc==-1){
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
                        units[nullLoc]=newUnit
                        foundStation.addUnit(newUnit);
                    }
                }
            }
        }
        return currentUnitID;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        boolean checkID=false;
        Unit foundUnit;
        for (int i=0; i<units.length(); i++){
            if (units[i].getUnitID().equals(unitID)){
                checkID=true;
                foundUnit=units[i];
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
            for (int x=0; x<stations.length(); i++){
                if stations[x].getstationId().equals(stationId){
                    foundStation=stations[x];
                }
            }
            foundStation.removeUnit(foundUnit.getUnitID());
            units[arrayLoc]=null;

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
        for (int i=0; i<stations.length(); i++){
            if stations[i].getstationId().equals(newStationId){
                checkStationID=true;
                foundStation=stations[i];
                int[] stationLoc={foundStation.getx(), foundStation.gety()}
            }
        }
        for (int x=0; x<units.length(); i++){
            if (units[i].getUnitID().equals(unitID)){
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
        for (int x=0; x<units.length(); i++){
            if (units[i].getUnitID().equals(unitID)){
                checkUnitID=true;
                foundUnit=units[i];
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
        int[] unitIDs=int[units.length()];
        for (int i=0; i<units.length(); i++){
            unitIDs[i]=units[i].getUnitId();
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
        for (int x=0; x<units.length(); i++){
            if (units[i].getUnitID().equals(unitID)){
                checkUnitID=true;
                foundUnit=units[i];
        if (checkUnitID==false){
            throw new IDNotRecognisedException("Unit ID not found");
        } else{
            unitString="TYPE="+foundUnit.getType()+" HOME="+foundUnit.getHome()+" LOC="+foundUnit.getCurrentLocation()+" STATUS="+foundUnit.getStatus()+" INCIDENT="+foundUnit.getWorkingIncident()+" WORK="+foundUnit.getTicksToComplete;
        }
        return unitString;
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
