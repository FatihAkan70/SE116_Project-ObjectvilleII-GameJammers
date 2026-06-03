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

    public abstract void provideService(Cell grid);

    // Uses Manhattan Distance
    public void distributeService(Cell[][] gridMap)
    {
        // Algorithm for iterating through cells in radius top left to bottom right taking the position of service provider as origin
        for (int x = -radius; x < radius + 1 ; x++)
        {
            for (int y = -radius; y < radius + 1 ; y++)
            {
                if (x == 0 && y == 0)
                    continue;
                else if (coordinateX + x < 0 || coordinateY + y < 0)
                    continue;
                else if (coordinateX + x > gridMap.length || coordinateY + y > gridMap[coordinateX + x].length)
                    continue;
                else if (Math.abs(x) + Math.abs(y) > radius)
                    continue;

                provideService(gridMap[coordinateX + x][coordinateY + y]);
            }
        }
    }
}
