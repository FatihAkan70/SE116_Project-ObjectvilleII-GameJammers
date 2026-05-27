import java.util.ArrayList;

public abstract class UtilityProvider extends Cell
{
    // Method "setUtility" should only be used for testing purposes
    protected int utility;
    public int getCurrentUtility() {return utility;}
    public void setUtility(int utility) {this.utility = utility;}


    public UtilityProvider(int utility ,int coordinateX, int coordinateY)
    {
        this.utility = utility;
        super(coordinateX, coordinateY);
    }

    // Generates 100 units of utility as default
    // If amount is less than or equal to 0 it defaults to 1
    public void generateUtility() {utility = utility + 100;}
    public void generateUtility(int amountGenerated) {if (amountGenerated <= 0) amountGenerated = 1; utility = utility + amountGenerated;}

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
        ArrayList<Cell> gotUtility = new ArrayList<>(24);
        gotUtility.add(this);

        while (utility >= 0)
        {
            layerCounter = layerCounter + 1;
            spreadOneLayer(gridMap, layerCounter, gotUtility);
        }
    }

    private void spreadOneLayer(Cell[][] gridMap, int layerCounter, ArrayList<Cell> gotUtility) {

        // Handles grid layer north of the origin
        processNorth(gridMap, layerCounter, gotUtility);

        // Handles grid layer east of the origin
        processEast(gridMap, layerCounter, gotUtility);

        // Handles grid layer south of the origin
        processSouth(gridMap, layerCounter, gotUtility);

        // Handles grid layer west of the origin
        processWest(gridMap, layerCounter, gotUtility);

    }

    private void processNorth(Cell[][] gridMap, int layerCounter, ArrayList<Cell> gotUtility)
    {
        for (int x = -layerCounter; x < layerCounter + 1 ; x++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX + x < 0 || coordinateY - layerCounter <= 0)
                continue;
            // Known Bug: This method fails to catch Array index out of bounds
            else if (coordinateX + x >= gridMap.length || coordinateY - layerCounter >= gridMap[coordinateX + x].length)
                continue;

            Cell currentGrid = gridMap[coordinateX + x][coordinateY - layerCounter];

            // Currently does not work will get tested when dependent classes get introduced
            // if (gotUtility.contains(currentGrid))
            //    continue;

            boolean connectedToSystem = false;

            //Tests if the grid is connected to the utility provider
            connectionTester:
            for (int i = -1; i < 1 + 1 ; i++)
            {
                for (int z = -1; z < 1 + 1 ; z++)
                {

                    if (connectedToSystem)
                        break connectionTester;
                    else if (gotUtility.contains(gridMap[coordinateX + i][coordinateY + z]))
                        connectedToSystem = true;
                }
            }

            if (!connectedToSystem)
                continue;

            // Known Bug: Utility will go below 0 because checks are calculated after utility is distributed
            if (utility <= 0)
                break;
            provideUtility(currentGrid);
            gotUtility.add(currentGrid);

            System.out.println("Current location in grid X:" + (coordinateX + x) + " Y:" + (coordinateY - layerCounter));
        }
    }

    private void processEast(Cell[][] gridMap, int layerCounter, ArrayList<Cell> gotUtility)
    {
        for (int y = -layerCounter; y < layerCounter + 1 ; y++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX + layerCounter < 0 || coordinateY + y <= 0)
                continue;
            // Known Bug: This method fails to catch Array index out of bounds
            if (coordinateX + layerCounter >= gridMap.length || coordinateY + y >= gridMap[coordinateX + layerCounter].length)
                continue;

            Cell currentGrid = gridMap[coordinateX + layerCounter][coordinateY + y];

            // Currently does not work will get tested when dependent classes get introduced
            // if (gotUtility.contains(currentGrid))
            //    continue;

            boolean connectedToSystem = false;

            //Tests if the grid is connected to the utility provider
            connectionTester:
            for (int i = -1; i < 1 + 1 ; i++)
            {
                for (int z = -1; z < 1 + 1 ; z++)
                {
                    if (connectedToSystem)
                        break connectionTester;
                    else if (gotUtility.contains(gridMap[coordinateX + i][coordinateY + z]))
                        connectedToSystem = true;
                }
            }

            if (!connectedToSystem)
                continue;

            // Known Bug: Utility will go below 0 because checks are calculated after utility is distributed
            if (utility <= 0)
                break;
            provideUtility(currentGrid);
            gotUtility.add(currentGrid);

            System.out.println("Current location in grid X:" + (coordinateX + layerCounter) + " Y:" + (coordinateY + y));
        }
    }

    private void processSouth(Cell[][] gridMap, int layerCounter, ArrayList<Cell> gotUtility)
    {
        for (int x = -layerCounter; x < layerCounter + 1 ; x++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX - x < 0 || coordinateY + layerCounter <= 0)
                continue;
            // Known Bug: This method fails to catch Array index out of bounds
            else if (coordinateX - x >= gridMap.length || coordinateY + layerCounter >= gridMap[coordinateX - x].length)
                continue;

            Cell currentGrid = gridMap[coordinateX - x][coordinateY + layerCounter];

            // Currently does not work will get tested when dependent classes get introduced
            // if (gotUtility.contains(currentGrid))
            //    continue;

            boolean connectedToSystem = false;

            //Tests if the grid is connected to the utility provider
            connectionTester:
            for (int i = -1; i < 1 + 1 ; i++)
            {
                for (int z = -1; z < 1 + 1 ; z++)
                {
                    if (connectedToSystem)
                        break connectionTester;
                    else if (gotUtility.contains(gridMap[coordinateX + i][coordinateY + z]))
                        connectedToSystem = true;
                }
            }

            if (!connectedToSystem)
                continue;

            // Known Bug: Utility will go below 0 because checks are calculated after utility is distributed
            if (utility <= 0)
                break;
            provideUtility(currentGrid);
            gotUtility.add(currentGrid);

            System.out.println("Current location in grid X:" + (coordinateX - x) + " Y:" + (coordinateY + layerCounter));
        }
    }

    private void processWest(Cell[][] gridMap, int layerCounter, ArrayList<Cell> gotUtility)
    {
        for (int y = -layerCounter; y < layerCounter + 1 ; y++)
        {
            // Makes sure that the current grid is inside the map
            if (coordinateX - layerCounter < 0 || coordinateY - y <= 0)
                continue;
            // Known Bug: This method fails to catch Array index out of bounds
            else if (coordinateX - layerCounter >= gridMap.length || coordinateY - y >= gridMap[coordinateX -layerCounter].length)
                continue;

            Cell currentGrid = gridMap[coordinateX - layerCounter][coordinateY - y];

            // Currently does not work will get tested when dependent classes get introduced
            // if (gotUtility.contains(currentGrid))
            //    continue;

            boolean connectedToSystem = false;

            //Tests if the grid is connected to the utility provider
            connectionTester:
            for (int i = -1; i < 1 + 1 ; i++)
            {
                for (int z = -1; z < 1 + 1 ; z++)
                {
                    if (connectedToSystem)
                        break connectionTester;
                    else if (gotUtility.contains(gridMap[coordinateX + i][coordinateY + z]))
                        connectedToSystem = true;
                }
            }

            if (!connectedToSystem)
                continue;

            // Known Bug: Utility will go below 0 because checks are calculated after utility is distributed
            if (utility <= 0)
                break;
            provideUtility(currentGrid);
            gotUtility.add(currentGrid);

            System.out.println("Current location in grid X:" + (coordinateX - layerCounter) + " Y:" + (coordinateY - y));
        }
    }

}
