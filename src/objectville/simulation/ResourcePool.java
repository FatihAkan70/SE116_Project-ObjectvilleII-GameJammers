package objectville.simulation;

import objectville.cells.zones.Commercial;
import objectville.cells.zones.Housing;
import objectville.cells.zones.Industrial;
import objectville.cells.zones.Zone;
import objectville.grid.Cell;

import java.util.ArrayList;
import java.util.List;

public class ResourcePool {

    // from the previous tick
    private int totalPopulation;
    private int totalGoods;
    private int totalLifestyle;

    public ResourcePool() {
        totalPopulation = 0;
        totalGoods = 0;
        totalLifestyle = 0;
    }

    // calculate total values of these attributes.
    public void accumulate(Cell[][] grid) {
        totalPopulation = 0;
        totalGoods = 0;
        totalLifestyle = 0;

        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell instanceof Housing h) {
                    totalPopulation += h.getOutput();
                } else if (cell instanceof Industrial i) {
                    totalGoods += i.getOutput();
                } else if (cell instanceof Commercial c) {
                    totalLifestyle += c.getOutput();
                }
            }
        }
    }


    public void distribute(Cell[][] grid) {

        List<Industrial> industrials = new ArrayList<>();
        List<Commercial> commercials = new ArrayList<>();
        List<Housing> housings = new ArrayList<>();

        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell instanceof Industrial i) industrials.add(i);
                else if (cell instanceof Commercial c) commercials.add(c);
                else if (cell instanceof Housing h) housings.add(h);
            }
        }

        int populationReceivers = industrials.size() + commercials.size();
        if (populationReceivers > 0) {
            int perZone = totalPopulation / populationReceivers; // integer division
            for (Industrial i : industrials) i.receivePopulation(perZone);
            for (Commercial c : commercials) c.receivePopulation(perZone);
        }


        if (!commercials.isEmpty()) {
            int perZone = totalGoods / commercials.size();
            for (Commercial c : commercials) c.receiveGoods(perZone);
        }


        if (!housings.isEmpty()) {
            int perZone = totalLifestyle / housings.size();
            for (Housing h : housings) h.setReceivedLifestyle(perZone);
        }
    }

    public int getTotalPopulation() { return totalPopulation; }
    public int getTotalGoods()      { return totalGoods; }
    public int getTotalLifestyle()  { return totalLifestyle; }
}