package objectville.cells.providers.services;

import objectville.cells.zones.Zone;
import objectville.grid.Cell;

public class Hospital extends ServiceProvider
{
    public Hospital(int coordinateX, int coordinateY)
    {
        super(3, coordinateX, coordinateY);
    }

    @Override
    public void provideService(Cell grid)
    {
        if (grid instanceof Zone && ((Zone) grid).isNeedsHealth())
        {
            ((Zone) grid).setHasHealth(true);
            System.out.println(grid.getClass().getSimpleName() + " at (" + grid.getCoordinateX() + "," + getCoordinateY() + ") received health service");
        }

    }
}