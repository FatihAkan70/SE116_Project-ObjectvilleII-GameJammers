package objectville.cells.zones;
import objectville.grid.Cell;

public class Zone extends Cell
{
    protected int level;    // level

    protected int receivedElectricity;      //Utility
    protected int receivedWater;
    protected int receivedInternet;

    protected boolean hasSecurity;      //Service
    protected boolean hasHealth;
    protected boolean hasEducation;

    protected int receivedPopulation;       //Population

    public Zone(int coordinateX, int coordinateY)
    {
        super(coordinateX,coordinateY);
    }


}
