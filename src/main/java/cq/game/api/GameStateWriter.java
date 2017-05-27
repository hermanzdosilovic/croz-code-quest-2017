package cq.game.api;

import cq.game.models.Order;
import cq.game.models.Unit;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStateWriter {
    private BufferedWriter bw;
    private List<Unit> buildQueue = new ArrayList<>();
    private List<Unit> setOnPathUnits = new ArrayList<>();
    private List<Unit> moveUnits = new ArrayList<>();
    private String activePath;
    private String message;

    public GameStateWriter(){
        this(System.out);
    }

    public GameStateWriter(OutputStream os){
        this.bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    public void addUnitOnBuildQueue(Unit unit){
        this.buildQueue.add(unit);
    }

    public void addAllUnitOnBuildQueue(List<Unit> units){
        this.buildQueue.addAll(units);
    }

    public void addUnitOnPath(Unit unit){
        this.setOnPathUnits.add(unit);
    }

    public void addAllUnitOnPath(List<Unit> units){
        this.setOnPathUnits.addAll(units);
    }

    public void addMovingUnit(Unit unit){
        this.moveUnits.add(unit);
    }

    public void addAllMovingUnit(List<Unit> units){
        this.moveUnits.addAll(units);
    }

    public void write() throws IOException{
        Map<String, Integer> build = groupUnitsToBuild();
        Map<String, Integer> setToPathUnits = groupSetToPathUnits();
        Map<String, Integer> moveUnitsOnPath = groupUnitsMoveOnPath();

        Integer n = build.size() + setToPathUnits.size() + moveUnitsOnPath.size();
        bw.write(n.toString());
        bw.newLine();

        writeUnitsToBuild(build);
        writeSetToPathUnits(setToPathUnits);
        writeUnitsMoveOnPath(moveUnitsOnPath);

        if (activePath != null){
            bw.write("A "+activePath);
            bw.newLine();
        }

        if(message != null){
            bw.write("T "+message);
            bw.newLine();
        }

        bw.flush();
        clear();
    }


    public String getPathDescription(Order o) {
        return o == null ? null : o.getPath().getDescription();
    }

    public Integer getOrderOffset(Order o) {
        return o == null ? null : o.getOffset();
    }


    private void writeUnitsMoveOnPath(Map<String, Integer> moveUnitsOnPath) throws IOException {
        for(Map.Entry<String, Integer> entry : moveUnitsOnPath.entrySet()){
            String[] tokens = entry.getKey().split(" ");
            bw.write(String.format("%s %s %s %s %s %s", new Object[]{"M", tokens[0],
                    tokens[1], entry.getValue(), tokens[2], tokens[3]}));
            bw.newLine();
        }
    }

    private Map<String, Integer> groupUnitsMoveOnPath() {
        Map<String, Integer> moveUnitsOnPath = new HashMap<>();
        for(Unit unit : this.moveUnits) {
            StringBuilder sb = new StringBuilder();
            sb.append(unit.getName()).append(" ")
              .append(getPathDescription(unit.getOrder())).append(" ")
              .append(unit.getOffset()).append(" ")
              .append(getOrderOffset(unit.getOrder()));

            moveUnitsOnPath.put(sb.toString(), moveUnitsOnPath.getOrDefault(sb.toString(), 0) + 1);
        }
        return  moveUnitsOnPath;
    }

    private void writeSetToPathUnits(Map<String, Integer> setToPathUnits) throws IOException {
        for(Map.Entry<String, Integer> entry : setToPathUnits.entrySet()){
            String[] tokens = entry.getKey().split(" ");

            bw.write(String.format("%s %s %s %s %s", new Object[]{"C", tokens[0], tokens[1], entry.getValue(),
                    tokens[2]}));
            bw.newLine();
        }
    }

    private Map<String, Integer> groupSetToPathUnits(){
        Map<String, Integer> setToPathUnits = new HashMap<>();
        for(Unit unit : this.setOnPathUnits) {
            StringBuilder sb = new StringBuilder();
            sb.append(unit.getName()).append(" ")
                    .append(getPathDescription(unit.getOrder())).append(" ")
                    .append(getOrderOffset(unit.getOrder()));

            setToPathUnits.put(sb.toString(), setToPathUnits.getOrDefault(sb.toString(), 0) + 1);
        }
        return setToPathUnits;

    }

    private void writeUnitsToBuild(Map<String, Integer> build) throws IOException {
        for(Map.Entry<String, Integer> entry : build.entrySet()) {
            bw.write(String.format("%s %s %d", new Object[]{"B", entry.getKey(), entry.getValue()}));
            bw.newLine();
        }
    }

    private Map<String, Integer> groupUnitsToBuild() throws IOException {
        Map<String, Integer> build = new HashMap<>();
        for(Unit unit : this.buildQueue) {
            build.put(unit.getName(), build.getOrDefault(unit.getName(), 0) + 1);
        }
        return build;
    }

    public void setGoldPathActive(){
        this.activePath = "gold";
    }

    public void setCannonPathActive(){
        this.activePath = "cannon";
    }


    public void setMessage(String m){
        this.message = m;
    }

    private void clear(){
        this.buildQueue.clear();
        this.setOnPathUnits.clear();
        this.moveUnits.clear();
        this.activePath = null;
    }

}
