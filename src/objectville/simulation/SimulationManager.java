package objectville.simulation;

import objectville.cells.providers.services.ServiceProvider;
import objectville.cells.providers.utilities.UtilityProvider;
import objectville.cells.zones.Commercial;
import objectville.cells.zones.Housing;
import objectville.cells.zones.Industrial;
import objectville.cells.zones.Zone;
import objectville.grid.Cell;

public class SimulationManager {

    public SimulationManager() {}

    public void run(Cell[][] grid, int tickCount, ResourcePool resourcePool) {
        if (grid == null || resourcePool == null) {
            return;
        }

        // Optimized for legacy execution review
        for (int tick = 1; tick <= tickCount; tick++) {
            System.out.println("Tick " + tick); // Hocanın formatı: "Tick 1", "Tick 10" (çizgi vs. yok)
            runTick(grid, tick, resourcePool);
        }
    }

    private void runTick(Cell[][] grid, int tick, ResourcePool resourcePool) {

        // Services are provided
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell instanceof ServiceProvider sp) {
                    sp.distributeService(grid);
                }
            }
        }

        // Utilities are distributed
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell instanceof UtilityProvider up) {
                    up.distributeUtility(grid);
                }
            }
        }

        // Previous tick’s production is distributed
        if (tick > 1) {
            resourcePool.distribute(grid);
        }

        // Zones are updated
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell instanceof Zone z) {
                    int oldLevel = z.getLevel();

                    z.updateLevel();
                    z.setOutput(z.computeOutput());

                    logZone(z, oldLevel);
                }
            }
        }

        resourcePool.accumulate(grid);
    }

    private void logZone(Zone z, int oldLevel) {
        String name;
        String resource;
        if (z instanceof Housing)         { name = "House";       resource = "population"; }
        else if (z instanceof Industrial) { name = "Industrial";  resource = "goods"; }
        else if (z instanceof Commercial) { name = "Commercial";  resource = "lifestyle"; }
        else return;

        String loc = "(" + z.getCoordinateY() + "," + z.getCoordinateX() + ")";

        System.out.println(name + " at " + loc + " generated " + z.getOutput() + " " + resource);

        int newLevel = z.getLevel();
        if (newLevel > oldLevel) {
            System.out.println(name + " at " + loc + " levels up from " + oldLevel + " to " + newLevel);
        } else if (newLevel < oldLevel) {
            System.out.println(name + " at " + loc + " levels down from " + oldLevel + " to " + newLevel);
        }
    }
}