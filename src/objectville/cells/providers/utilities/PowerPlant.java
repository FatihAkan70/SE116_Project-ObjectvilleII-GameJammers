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
            ((Zone) grid).receiveInternet(Math.min(utility, zoneDemand));
            System.out.println(grid.getClass().getSimpleName() + " at (" + grid.getCoordinateX() + "," + getCoordinateY() + ") received " + Math.min(utility, zoneDemand) + " electricity");
        }
        if (utility <= zoneDemand)
            utility = 0;
        else
            utility = utility - zoneDemand;
        System.out.println("Debug: Remaining utility: " + utility);
    }
}
