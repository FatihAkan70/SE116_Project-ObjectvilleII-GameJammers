package objectville.simulation;

import objectville.cells.zones.Commercial;
import objectville.cells.zones.Housing;
import objectville.cells.zones.Industrial;
import objectville.grid.Cell;

import java.util.ArrayList;
import java.util.List;

public class ResourcePool {

    private int totalPopulation;
    private int totalGoods;
    private int totalLifestyle;

    public ResourcePool() {
        this.totalPopulation = 0;
        this.totalGoods = 0;
        this.totalLifestyle = 0;
    }


    public void distribute(Cell[][] grid) {
        if (grid == null) return;

        List<Industrial> industrials = new ArrayList<>();
        List<Commercial> commercials = new ArrayList<>();
        List<Housing> housings = new ArrayList<>();

        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell instanceof Industrial i) {
                    industrials.add(i);
                } else if (cell instanceof Commercial c) {
                    commercials.add(c);
                } else if (cell instanceof Housing h) {
                    housings.add(h);
                }
            }
        }

        int populationReceivers = industrials.size() + commercials.size();
        if (populationReceivers > 0 && totalPopulation > 0) {
            int perZone = totalPopulation / populationReceivers;
            for (Industrial i : industrials) {
                i.setReceivedPopulation(perZone);
            }
            for (Commercial c : commercials) {
                c.setReceivedPopulation(perZone);
            }
        }

        if (!commercials.isEmpty() && totalGoods > 0) {
            int perZone = totalGoods / commercials.size();
            for (Commercial c : commercials) {
                c.setReceivedGoods(perZone);
            }
        }

        if (!housings.isEmpty() && totalLifestyle > 0) {
            int perZone = totalLifestyle / housings.size();
            for (Housing h : housings) {
                h.setReceivedLifestyle(perZone);
            }
        }

        this.totalPopulation = 0;
        this.totalGoods = 0;
        this.totalLifestyle = 0;
    }

    public void accumulate(Cell[][] grid) {
        if (grid == null) return;

        this.totalPopulation = 0;
        this.totalGoods = 0;
        this.totalLifestyle = 0;

        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell instanceof Housing h) {
                    this.totalPopulation += h.getOutput();
                    h.resetTickInputs();
                } else if (cell instanceof Industrial i) {
                    this.totalGoods += i.getOutput();
                    i.resetTickInputs();
                } else if (cell instanceof Commercial c) {
                    this.totalLifestyle += c.getOutput();
                    c.resetTickInputs();
                }
            }
        }
    }


}