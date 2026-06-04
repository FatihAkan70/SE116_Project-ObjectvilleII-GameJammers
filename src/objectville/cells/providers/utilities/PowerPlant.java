package objectville.cells.providers.utilities;

import objectville.cells.zones.Zone;
import objectville.grid.Cell;

public class PowerPlant extends UtilityProvider
{
    public PowerPlant(int startingPower ,int coordinateX, int coordinateY)
    {
        super(startingPower, coordinateX, coordinateY);
    }

    @Override
    public void provideUtility(Cell grid)
    {
        int zoneDemand = 0;
        if (grid instanceof Zone && ((Zone) grid).isNeedsElectricity())
        {
            zoneDemand = ((Zone) grid).getUtilityDemand();
            ((Zone) grid).receiveElectricity(Math.min(utility, zoneDemand));
            System.out.println(grid.getClass().getSimpleName() + " at (" + grid.getCoordinateY() + "," + grid.getCoordinateX() + ") received " + Math.min(utility, zoneDemand) + " electricity");
        }
        if (utility <= zoneDemand)
            utility = 0;
        else
            utility = utility - zoneDemand;
    }
}
