package cq.game.api;

import cq.game.models.*;
import cq.game.models.path.CannonPath;
import cq.game.models.path.GoldPath;
import cq.game.models.path.Path;
import cq.game.models.path.ShortPath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStateReader {
    private BufferedReader bs;
    private Map<String, Path> paths = new HashMap<>();

    public GameStateReader(){
        this(System.in);
    }

    public GameStateReader(InputStream is){
        this.bs = new BufferedReader(new InputStreamReader(is));
        paths.put("gold", new GoldPath());
        paths.put("short", new ShortPath());
        paths.put("cannon", new CannonPath());
    }
    
    private String readLine() throws IOException {
        return this.bs.readLine().trim();
    }

    public InputGameState nextState() throws IOException {
        Integer turnNumber = Integer.valueOf(readLine());
        BaseStats myBase = new BaseStats(readLine().split("\\s"));
        BaseStats enemyBase = new BaseStats(readLine().split("\\s"));


        Integer myArmySize = Integer.valueOf(readLine());
        List<Unit> myUnits = new ArrayList<>();
        for(int i = 0; i < myArmySize; i++){
            myUnits.addAll(getUnits(readLine().split("\\s")));
        }

        Integer enemyArmySize = Integer.valueOf(readLine());
        List<Unit> enemyUnits = new ArrayList<>();
        for(int i = 0; i < enemyArmySize; i++){
            enemyUnits.addAll(getUnits(readLine().split("\\s")));
        }

        String[] toProduce = readLine().split("\\s");
        Integer archersToProduce = Integer.valueOf(toProduce[0]);
        Integer spearmenToProduce = Integer.valueOf(toProduce[1]);
        Integer horsemenToProduce = Integer.valueOf(toProduce[2]);

        String goldPathOwner = "none";
        String cannonPathOwner = "none";
        Boolean goldPathActive = false;
        Boolean cannonPathActive = false;
        Integer k = Integer.valueOf(readLine());
        for(int i = 0; i < k; i++){
            String[] specials = readLine().split("\\s");
            String path = specials[0].toLowerCase().trim();
            String owner = specials[1].trim();
            Integer active = Integer.valueOf(specials[2]);
            if(path.equals("gold")){
                goldPathOwner = owner;
                goldPathActive = active == 1;
            }else if(path.equals("cannon")){
                cannonPathOwner = owner;
                cannonPathActive = active == 1;
            }
        }

        List<Unit> movingUnits = new ArrayList<>();
        Integer m = Integer.valueOf(readLine());
        for(int i = 0; i < m; i++){
            String[] move = readLine().split("\\s");
            Path path = this.paths.get(move[0]);
            String unitName = move[1];
            Integer amount = Integer.valueOf(move[2]);
            Integer position = Integer.valueOf(move[3]);
            Integer to = Integer.valueOf(move[4]);
            for(int j = 0; j < amount; j++){
                Unit unit = makeUnit(unitName);
                unit.setPath(path);
                unit.setOffset(position);
                unit.setOrder(new Order(path, to));
                movingUnits.add(unit);
            }
        }
        return new InputGameState(turnNumber, myBase, enemyBase, myUnits, enemyUnits,
                archersToProduce, spearmenToProduce, horsemenToProduce, goldPathOwner, cannonPathOwner, goldPathActive, cannonPathActive, movingUnits);
    }

    private Unit makeUnit(String unitName) {
        if(unitName.toLowerCase().equals("archer")){
            return new Archer();
        }else if(unitName.toLowerCase().equals("spearman")){
            return new Spearman();
        }else if(unitName.toLowerCase().equals("horseman")){
            return new Horseman();
        }
        return null;
    }

    private List<Unit> getUnits(String[] units){
        String path = units[0];
        Integer offset = Integer.valueOf(units[1]);
        List<Unit> army = new ArrayList<>();
        army.addAll(generateUnitsByType(Integer.valueOf(units[2]), Archer.class, path, offset));
        army.addAll(generateUnitsByType(Integer.valueOf(units[3]), Spearman.class, path, offset));
        army.addAll(generateUnitsByType(Integer.valueOf(units[4]), Horseman.class, path, offset));
        return army;
    }

    private List<Unit> generateUnitsByType(Integer n, Class clazz, String path, Integer offset){
        List<Unit> units = new ArrayList<>();
        for(int i = 0; i < n; i++){
            Unit unit;
            if (clazz.equals(Archer.class)){
                unit = new Archer();
            }else if (clazz.equals(Spearman.class)){
                unit = new Spearman();
            }else{
                unit = new Horseman();
            }
            unit.setPath(paths.get(path));
            unit.setOffset(offset);
            units.add(unit);
        }
        return units;
    }
}
