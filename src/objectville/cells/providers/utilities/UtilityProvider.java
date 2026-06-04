package objectville.cells.providers.utilities;

import objectville.cells.infrastructure.Road;
import objectville.cells.zones.Zone;
import objectville.grid.Cell;

import java.util.ArrayList;

public abstract class UtilityProvider extends Cell
{
    private boolean hasSpreadOnceAtCurrentLayer;
    protected int utility;

    public UtilityProvider(int utility ,int coordinateX, int coordinateY)
    {
        super(coordinateX, coordinateY);
        this.utility = utility;
    }

    // Generic methods for the generation of utility in the utility provider and providing utility to an any cell
    public void generateUtility() {utility = utility + 100;}
    public abstract void provideUtility(Cell grid);

    // The main method that should be called by an instance of this object, Uses Breadth-First research
    public void distributeUtility(Cell[][] gridMap)
    {
        int layerCounter = 0;
        // A list of cells which connect to the network
        // This list is used alongside the "connectedToSystem" boolean inside relevant methods
        ArrayList<Cell> connectedToUtilityProvider = new ArrayList<>(24);
        connectedToUtilityProvider.add(this);
        hasSpreadOnceAtCurrentLayer = true;

        while (utility >= 0)
        {
            // Checks if the utility has any place it could spread to, if not ends loop so it does not go to infinity.
            if (!hasSpreadOnceAtCurrentLayer)
            {
                generateUtility();
                break;
            }
            hasSpreadOnceAtCurrentLayer = false;
            layerCounter = layerCounter + 1;
            spreadOneLayer(gridMap, layerCounter, connectedToUtilityProvider);
        }
    }

    private void spreadOneLayer(Cell[][] gridMap, int layerCounter, ArrayList<Cell> connectedToUtilityProvider) {

        // All of these methods work in the same way they are separated because they use different calculations

        // Handles grid layer north of the origin
        processNorth(gridMap, layerCounter, connectedToUtilityProvider);

        // Handles grid layer east of the origin
        processEast(gridMap, layerCounter, connectedToUtilityProvider);

        // Handles grid layer south of the origin
        processSouth(gridMap, layerCounter, connectedToUtilityProvider);

        // Handles grid layer west of the origin
        processWest(gridMap, layerCounter, connectedToUtilityProvider);

    }

    // Private methods to be used by the spreadOneLayer method
    private void processNorth(Cell[][] gridMap, int layerCounter, ArrayList<Cell> connectedToUtilityProvider)
    {
        for (int x = -layerCounter; x < layerCounter + 1 ; x++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX + x < 0 || coordinateY - layerCounter < 0)
                continue;
            else if (coordinateX + x >= gridMap.length || coordinateY - layerCounter >= gridMap[coordinateX + x].length)
                continue;

            Cell currentGrid = gridMap[coordinateX + x][coordinateY - layerCounter];

            // Tests if the current grid has been iterated over successfully before
            if (gridNotEligibleToReceiveUtility(gridMap, connectedToUtilityProvider, currentGrid)) continue;

            // Breaks if utility becomes zero
            if (handleDistribution(connectedToUtilityProvider, currentGrid)) break;

        }
    }
    private void processEast(Cell[][] gridMap, int layerCounter, ArrayList<Cell> connectedToUtilityProvider)
    {
        for (int y = -layerCounter; y < layerCounter + 1 ; y++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX + layerCounter < 0 || coordinateY + y < 0)
                continue;
            else if (coordinateX + layerCounter >= gridMap.length || coordinateY + y >= gridMap[coordinateX + layerCounter].length)
                continue;

            Cell currentGrid = gridMap[coordinateX + layerCounter][coordinateY + y];

            // Tests if the current grid has been iterated over successfully before
            if (gridNotEligibleToReceiveUtility(gridMap, connectedToUtilityProvider, currentGrid)) continue;

            // Breaks if utility becomes zero
            if (handleDistribution(connectedToUtilityProvider, currentGrid)) break;

        }
    }
    private void processSouth(Cell[][] gridMap, int layerCounter, ArrayList<Cell> connectedToUtilityProvider)
    {
        for (int x = -layerCounter; x < layerCounter + 1 ; x++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX - x < 0 || coordinateY + layerCounter < 0)
                continue;
            else if (coordinateX - x >= gridMap.length || coordinateY + layerCounter >= gridMap[coordinateX - x].length)
                continue;

            Cell currentGrid = gridMap[coordinateX - x][coordinateY + layerCounter];

            // Tests if the current grid has been iterated over successfully before
            if (gridNotEligibleToReceiveUtility(gridMap, connectedToUtilityProvider, currentGrid)) continue;

            // Breaks if utility becomes zero
            if (handleDistribution(connectedToUtilityProvider, currentGrid)) break;

        }
    }
    private void processWest(Cell[][] gridMap, int layerCounter, ArrayList<Cell> connectedToUtilityProvider)
    {
        for (int y = -layerCounter; y < layerCounter + 1 ; y++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX - layerCounter < 0 || coordinateY - y < 0)
                continue;
            else if (coordinateX - layerCounter >= gridMap.length || coordinateY - y >= gridMap[coordinateX -layerCounter].length)
                continue;

            Cell currentGrid = gridMap[coordinateX - layerCounter][coordinateY - y];

            // Checks for any flags that make the grid ineligible to receive utility
            if (gridNotEligibleToReceiveUtility(gridMap, connectedToUtilityProvider, currentGrid)) continue;

            // Breaks if utility becomes zero
            if (handleDistribution(connectedToUtilityProvider, currentGrid)) break;

        }
    }

    // Private methods to be used by the 4 processor methods
    private static boolean gridNotEligibleToReceiveUtility(Cell[][] gridMap, ArrayList<Cell> connectedToUtilityProvider, Cell currentGrid)
    {
        // Tests if the current grid has been iterated over successfully before
        if (connectedToUtilityProvider.contains(currentGrid))
            return true;

        //Tests if the grid is connected to the utility provider
        if (gridNotConnectedToSystem(gridMap, connectedToUtilityProvider, currentGrid)) return true;

        // Test if grid type is eligible to receive utility
        if (!(currentGrid instanceof Zone || currentGrid instanceof Road))
            return true;
        return false;
    }
    private static boolean gridNotConnectedToSystem(Cell[][] gridMap, ArrayList<Cell> connectedToUtilityProvider, Cell currentGrid)
    {
        boolean connectedToSystem = false;
        connectionTester:
        for (int i = -1; i < 1 + 1 ; i++)
        {
            for (int z = -1; z < 1 + 1 ; z++)
            {
                if (currentGrid.getCoordinateX() + i < 0 || currentGrid.getCoordinateY() + z < 0)
                    continue;
                else if (currentGrid.getCoordinateX() + i >= gridMap.length || currentGrid.getCoordinateY() + z >= gridMap[currentGrid.getCoordinateX() + i].length)
                    continue;
                else if (connectedToUtilityProvider.contains(gridMap[currentGrid.getCoordinateX() + i][currentGrid.getCoordinateY() + z]))
                {
                    connectedToSystem = true;
                    break connectionTester;
                }
            }
        }
        if (!connectedToSystem)
            return true;
        return false;
    }
    private boolean handleDistribution(ArrayList<Cell> connectedToUtilityProvider, Cell currentGrid)
    {
        if (isUtilityRemaining())
            return true;

        if (currentGrid instanceof Zone)
            provideUtility(currentGrid);
        connectedToUtilityProvider.add(currentGrid);
        hasSpreadOnceAtCurrentLayer = true;

        if (isUtilityRemaining())
            return true;
        else
            return false;
    }
    private boolean isUtilityRemaining() {return utility <= 0;}

}
