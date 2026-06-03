package objectville.cells.providers.utilities;

import objectville.cells.zones.Zone;
import objectville.grid.Cell;

public class InternetHub extends UtilityProvider
{
    public InternetHub(int startingInternet ,int coordinateX, int coordinateY)
    {
        super(startingInternet, coordinateX, coordinateY);
    }

    @Override
    public void provideUtility(Cell grid)
    {
        int zoneDemand = 0;
        if (grid instanceof Zone)
        {
            zoneDemand = ((Zone) grid).getUtilityDemand();
            ((Zone) grid).receiveInternet(Math.min(utility, zoneDemand));
            System.out.println(grid.getClass().getSimpleName() + " at (" + grid.getCoordinateX() + "," + getCoordinateY() + ") received " + Math.min(utility, zoneDemand) + " internet");
        }
        if (utility <= zoneDemand)
            utility = 0;
        else
            utility = utility - zoneDemand;
        System.out.println("Debug: Remaining utility: " + utility);
    }
}

