public abstract class UtilityProvider extends Cell
{
    // Method "setUtility" should only be used for testing purposes
    protected int utility;
    public int getCurrentUtility() {return utility;}
    public void setUtility(int utility) {this.utility = utility;}

    protected int coordinateX;
    protected int coordinateY;

    public UtilityProvider(int utility ,int coordinateX, int coordinateY)
    {
        this.utility = utility;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    // Generates 100 units of utility as default
    // If amount is less than or equal to 0 it defaults to 1
    public void generateUtility() {utility = utility + 100;}
    public void generateUtility(int amountGenerated) {if (amountGenerated <= 0) amountGenerated = 1; utility = utility + amountGenerated;}

    public void spreadOneLayer()
    {
    }

    // Uses Breadth-First research
    public void distributeUtility(Cell[][] gridMap)
    {
    }
}
