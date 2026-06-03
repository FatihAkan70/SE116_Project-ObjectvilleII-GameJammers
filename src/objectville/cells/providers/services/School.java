package objectville.cells.providers.services;

import objectville.cells.zones.Zone;
import objectville.grid.Cell;

public class School extends ServiceProvider
{
    public School(int coordinateX, int coordinateY)
    {
        super(4, coordinateX, coordinateY);
    }

    @Override
    public void provideService(Cell grid)
    {
        if (grid instanceof Zone)
            ((Zone) grid).setHasEducation(true);
        System.out.println(grid.getClass().getSimpleName() + " at (" + grid.getCoordinateX() + "," + getCoordinateY() + ") received education service");
    }
}
