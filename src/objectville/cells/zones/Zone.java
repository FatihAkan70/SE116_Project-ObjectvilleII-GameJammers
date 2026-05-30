package objectville.cells.zones;
import objectville.grid.Cell;

public abstract class Zone extends Cell
{
    protected int level;
    protected int output;   // per tick

    @Override
    public boolean isConnectable(){
        return true;
    }

    // Utility
    protected int receivedElectricity;
    protected int receivedWater;
    protected int receivedInternet;

    //Service
    protected boolean hasSecurity;
    protected boolean hasEducation;
    protected boolean hasHealth;

    //Population
    protected int receivedPopulation;
    protected int receivedGoods;
    protected int receivedLifestyle;

    public Zone (int coordinateX, int coordinateY)
    {
        super(coordinateX,coordinateY);
        level=0; //initializing
        output=0;
    }

    public int getUtilityDemand() {
        return Math.max(1, output);
    }

    public abstract int computeOutput();

    public abstract void updateLevel();

    public int getLevel() { return level; }
    public int getOutput() { return output; }

    public void resetTickInputs() {
        receivedElectricity = 0;
        receivedWater = 0;
        receivedInternet = 0;
        hasSecurity = false;
        hasHealth = false;
        hasEducation = false;
        receivedPopulation = 0;
        receivedGoods = 0;
        receivedLifestyle = 0;
    }

    // Setters for such attributes.
    public void receiveElectricity(int amount){
        receivedElectricity  += amount;
    }
    public void receiveWater(int amount){
        receivedWater += amount;
    }
    public void receiveInternet(int amount){
        receivedInternet += amount;
    }

    public void setHasSecurity(boolean v){
        hasSecurity = v;
    }
    public void setHasHealth(boolean v){
        hasHealth = v;
    }
    public void setHasEducation(boolean v){
        hasEducation = v;
    }

    public void receivePopulation(int amount){
        receivedPopulation += amount;
    }
    public void receiveGoods(int amount){
        this.receivedGoods += amount;
    }
    public void setReceivedLifestyle(int amount){
        this.receivedLifestyle = amount;
    }
}
