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
        if (grid instanceof Zone && ((Zone) grid).isNeedsInternet())
        {
            zoneDemand = ((Zone) grid).getUtilityDemand();
            ((Zone) grid).receiveInternet(Math.min(utility, zoneDemand));
            System.out.println(grid.getClass().getSimpleName() + " at (" + grid.getCoordinateY() + "," + grid.getCoordinateX() + ") received " + Math.min(utility, zoneDemand) + " internet");
        }

        if (utility <= zoneDemand)
            utility = 0;
        else
            utility = utility - zoneDemand;
    }
}

