package objectville.simulation;

import objectville.cells.providers.services.ServiceProvider;
import objectville.cells.providers.utilities.UtilityProvider;
import objectville.cells.zones.Commercial;
import objectville.cells.zones.Housing;
import objectville.cells.zones.Industrial;
import objectville.cells.zones.Zone;
import objectville.grid.Cell;

public class SimulationManager{

    public SimulationManager() {}


    public void run(Cell[][] grid, int tickCount, ResourcePool resourcePool) {
        for (int tick = 1; tick <= tickCount; tick++) {
            System.out.println("Tick " + tick);
            runTick(grid, tick, resourcePool);

        }
    }
    private void runTick(Cell[][] grid, int tick, ResourcePool resourcePool) {

        // Resetting part (first step of each tick)
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (cell instanceof Zone z){
                    z.resetTickInputs();
                }
                if (cell instanceof ServiceProvider sp){
                    sp.distributeService(grid);
                }
                if (cell instanceof UtilityProvider up){
                    up.distributeUtility(grid);
                }
            }
        }

        // the pool is empty at the first tick so we skip this part at there
        // distributing the new sources based on the new output from previous(last) step.
        if (tick > 1){
            resourcePool.distribute(grid);
        }


        // updating the level and current step's output + logging results
        for (Cell[] row : grid) {
            for (Cell cell : row) {
                if (!(cell instanceof Zone z)) continue;

                int oldLevel = z.getLevel();
                z.updateLevel();
                z.setOutput(z.computeOutput());

                logZone(z, oldLevel);
            }
        }

        // collecting the new output/production on the pool.
        resourcePool.accumulate(grid);

    }

    private void logZone(Zone z, int oldLevel) {
        String name;
        String resource;
        if (z instanceof Housing)    { name = "House";       resource = "population"; }
        else if (z instanceof Industrial) { name = "Industrial"; resource = "goods"; }
        else if (z instanceof Commercial) { name = "Commercial"; resource = "lifestyle"; }
        else return;

        String loc = "(" + z.getCoordinateX() + "," + z.getCoordinateY() + ")";

        System.out.println(name + " at " + loc + " generated " + z.getOutput() + " " + resource);
        int newLevel = z.getLevel();
        if (newLevel > oldLevel)
            System.out.println(name + " at " + loc + " levels up from " + oldLevel + " to " + newLevel);
        else if (newLevel < oldLevel)
            System.out.println(name + " at " + loc + " levels down from " + oldLevel + " to " + newLevel );
        else {
            System.out.println(name + " at " + loc + " did not level up");
        }
    }
}