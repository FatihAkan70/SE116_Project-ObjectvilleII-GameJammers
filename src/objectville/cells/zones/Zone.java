package objectville.cells.zones;
import objectville.grid.Cell;

public abstract class Zone extends Cell
{
    protected int level;    // level

    protected int receivedElectricity;      //Utility
    protected int receivedWater;
    protected int receivedInternet;

    protected boolean hasSecurity;      //Service
    protected boolean hasHealth;
    protected boolean hasEducation;

    protected int receivedPopulation;       //Population
    protected int receivedGoods;
    protected int receivedLifestyle;

    public Zone(int coordinateX, int coordinateY)
    {
        super(coordinateX,coordinateY);
    }

    public abstract void updateLevel();
    public abstract int calculateOutput();

    public void setReceivedLifestyle(int amount) {
        this.receivedLifestyle = amount;
    }

}
