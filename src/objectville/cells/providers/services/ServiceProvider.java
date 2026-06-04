package objectville.cells.providers.services;

import objectville.grid.Cell;

public abstract class ServiceProvider extends Cell
{
    protected int radius;

    public ServiceProvider(int radius, int coordinateX, int coordinateY)
    {
        super(coordinateX, coordinateY);
        this.radius = radius;
    }

    // Generic method for providing service
    public abstract void provideService(Cell grid);

    // Algorithm for distributing to cells in radius, top left to bottom right taking the position of service provider as origin, Uses Manhattan Distance
    public void distributeService(Cell[][] gridMap)
    {
        for (int x = -radius; x < radius + 1 ; x++)
        {
            for (int y = -radius; y < radius + 1 ; y++)
            {
                if (gridNotEligibleToReceiveService(gridMap, x, y)) continue;
                provideService(gridMap[coordinateX + x][coordinateY + y]);
            }
        }
    }

    private boolean gridNotEligibleToReceiveService(Cell[][] gridMap, int x, int y)
    {
        if (x == 0 && y == 0)
            return true;
        else if (coordinateX + x < 0 || coordinateY + y < 0)
            return true;
        else if (coordinateX + x >= gridMap.length || coordinateY + y >= gridMap[coordinateX + x].length)
            return true;
        else if (Math.abs(x) + Math.abs(y) > radius)
            return true;
        return false;
    }
}
