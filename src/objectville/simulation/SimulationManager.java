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

        for (int tick = 1; tick <= tickCount; tick++) {
            System.out.println("Tick " + tick);
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
        switch (z) {
            case Housing housing -> {
                name = "House";
                resource = "population";
            }
            case Industrial industrial -> {
                name = "Industrial";
                resource = "goods";
            }
            case Commercial commercial -> {
                name = "Commercial";
                resource = "lifestyle";
            }
            case null, default -> {
                return;
            }
        }

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