package objectville.cells.providers.services;

import objectville.cells.zones.Zone;
import objectville.grid.Cell;

public class PoliceStation extends ServiceProvider
{
    public PoliceStation(int coordinateX, int coordinateY)
    {
        super(5, coordinateX, coordinateY);
    }

    @Override
    public void provideService(Cell grid)
    {
        if (grid instanceof Zone)
            ((Zone) grid).setHasSecurity(true);
        System.out.println(grid.getClass().getSimpleName() + " at (" + grid.getCoordinateX() + "," + getCoordinateY() + ") received security service");
    }
}