import java.util.ArrayList;

public abstract class UtilityProvider extends Cell
{
    private boolean hasSpreadOnceAtCurrentLayer;

    // Method "setUtility" should only be used for testing purposes
    protected int utility;
    public int getCurrentUtility() {return utility;}
    private void setUtility(int utility) {this.utility = utility;}

    public UtilityProvider(int utility ,int coordinateX, int coordinateY)
    {
        this.utility = utility;
        super(coordinateX, coordinateY);
    }

    public void generateUtility() {utility = utility + 100;}

    public void provideUtility(Cell grid)
    {
        System.out.println("WIP");
        utility = utility - 3;
        System.out.println("Remaining utility: " + utility);
    }

    // Uses Breadth-First research
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

    private void processNorth(Cell[][] gridMap, int layerCounter, ArrayList<Cell> connectedToUtilityProvider)
    {
        for (int x = -layerCounter; x < layerCounter + 1 ; x++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX + x < 0 || coordinateY - layerCounter <= 0)
                continue;
            else if (coordinateX + x >= gridMap.length || coordinateY - layerCounter >= gridMap[coordinateX + x].length)
                continue;

            Cell currentGrid = gridMap[coordinateX + x][coordinateY - layerCounter];

            // Tests if the current grid has been iterated over successfully before
            if (connectedToUtilityProvider.contains(currentGrid))
                continue;

            //Tests if the grid is connected to the utility provider
            boolean connectedToSystem = false;
            connectionTester:
            for (int i = -1; i < 1 + 1 ; i++)
            {
                for (int z = -1; z < 1 + 1 ; z++)
                {
                    if (connectedToSystem)
                        break connectionTester;
                    else if (connectedToUtilityProvider.contains(gridMap[currentGrid.getCoordinateX() + i][currentGrid.getCoordinateY() + z]))
                        connectedToSystem = true;
                }
            }
            if (!connectedToSystem)
                continue;

            // Test if grid type is eligible to receive utility
            if (!(currentGrid instanceof Zone || currentGrid instanceof Road))
                continue;
            // Known Bug: Utility will go below 0 because checks are calculated after utility is distributed
            if (utility <= 0)
                break;
            provideUtility(currentGrid);
            connectedToUtilityProvider.add(currentGrid);
            hasSpreadOnceAtCurrentLayer = true;

            // A debug code to make life easier, will get removed on release
            System.out.println("Debug: Current location in grid X:" + (coordinateX + x) + " Y:" + (coordinateY - layerCounter));
        }
    }

    private void processEast(Cell[][] gridMap, int layerCounter, ArrayList<Cell> connectedToUtilityProvider)
    {
        for (int y = -layerCounter; y < layerCounter + 1 ; y++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX + layerCounter < 0 || coordinateY + y <= 0)
                continue;
            else if (coordinateX + layerCounter >= gridMap.length || coordinateY + y >= gridMap[coordinateX + layerCounter].length)
                continue;

            Cell currentGrid = gridMap[coordinateX + layerCounter][coordinateY + y];

            // Tests if the current grid has been iterated over successfully before
            if (connectedToUtilityProvider.contains(currentGrid))
                continue;

            //Tests if the grid is connected to the utility provider
            boolean connectedToSystem = false;
            connectionTester:
            for (int i = -1; i < 1 + 1 ; i++)
            {
                for (int z = -1; z < 1 + 1 ; z++)
                {
                    if (connectedToSystem)
                        break connectionTester;
                    else if (connectedToUtilityProvider.contains(gridMap[coordinateX + i][coordinateY + z]))
                        connectedToSystem = true;
                }
            }

            if (!connectedToSystem)
                continue;

            // Test if grid type is eligible to receive utility
            if (!(currentGrid instanceof Zone || currentGrid instanceof Road))
                continue;
            // Known Bug: Utility will go below 0 because checks are calculated after utility is distributed
            if (utility <= 0)
                break;
            provideUtility(currentGrid);
            connectedToUtilityProvider.add(currentGrid);
            hasSpreadOnceAtCurrentLayer = true;

            // A debug code to make life easier, will get removed on release
            System.out.println("Debug: Current location in grid X:" + (coordinateX + layerCounter) + " Y:" + (coordinateY + y));
        }
    }

    private void processSouth(Cell[][] gridMap, int layerCounter, ArrayList<Cell> connectedToUtilityProvider)
    {
        for (int x = -layerCounter; x < layerCounter + 1 ; x++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX - x < 0 || coordinateY + layerCounter <= 0)
                continue;
            else if (coordinateX - x >= gridMap.length || coordinateY + layerCounter >= gridMap[coordinateX - x].length)
                continue;

            Cell currentGrid = gridMap[coordinateX - x][coordinateY + layerCounter];

            // Tests if the current grid has been iterated over successfully before
            if (connectedToUtilityProvider.contains(currentGrid))
                continue;

            //Tests if the grid is connected to the utility provider
            boolean connectedToSystem = false;
            connectionTester:
            for (int i = -1; i < 1 + 1 ; i++)
            {
                for (int z = -1; z < 1 + 1 ; z++)
                {
                    if (connectedToSystem)
                        break connectionTester;
                    else if (connectedToUtilityProvider.contains(gridMap[coordinateX + i][coordinateY + z]))
                        connectedToSystem = true;
                }
            }

            if (!connectedToSystem)
                continue;

            // Test if grid type is eligible to receive utility
            if (!(currentGrid instanceof Zone || currentGrid instanceof Road))
                continue;
            // Known Bug: Utility will go below 0 because checks are calculated after utility is distributed
            if (utility <= 0)
                break;
            provideUtility(currentGrid);
            connectedToUtilityProvider.add(currentGrid);
            hasSpreadOnceAtCurrentLayer = true;

            // A debug code to make life easier, will get removed on release
            System.out.println("Debug: Current location in grid X:" + (coordinateX - x) + " Y:" + (coordinateY + layerCounter));
        }
    }

    private void processWest(Cell[][] gridMap, int layerCounter, ArrayList<Cell> connectedToUtilityProvider)
    {
        for (int y = -layerCounter; y < layerCounter + 1 ; y++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX - layerCounter < 0 || coordinateY - y <= 0)
                continue;
            else if (coordinateX - layerCounter >= gridMap.length || coordinateY - y >= gridMap[coordinateX -layerCounter].length)
                continue;

            Cell currentGrid = gridMap[coordinateX - layerCounter][coordinateY - y];

            // Tests if the current grid has been iterated over successfully before
            if (connectedToUtilityProvider.contains(currentGrid))
               continue;

            //Tests if the grid is connected to the utility provider
            boolean connectedToSystem = false;
            connectionTester:
            for (int i = -1; i < 1 + 1 ; i++)
            {
                for (int z = -1; z < 1 + 1 ; z++)
                {
                    if (connectedToSystem)
                        break connectionTester;
                    else if (connectedToUtilityProvider.contains(gridMap[coordinateX + i][coordinateY + z]))
                        connectedToSystem = true;
                }
            }
            if (!connectedToSystem)
                continue;

            // Test if grid type is eligible to receive utility
            if (!(currentGrid instanceof Zone || currentGrid instanceof Road))
                continue;
            // Known Bug: Utility will go below 0 because checks are calculated after utility is distributed
            else if (utility <= 0)
                break;

            provideUtility(currentGrid);
            connectedToUtilityProvider.add(currentGrid);
            hasSpreadOnceAtCurrentLayer = true;

            // A debug code to make life easier, will get removed on release
            System.out.println("Debug: Current location in grid X:" + (coordinateX - layerCounter) + " Y:" + (coordinateY - y));
        }
    }

}
