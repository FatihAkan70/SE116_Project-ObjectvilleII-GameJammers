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

    //Utility
    protected int receivedElectricity;
    protected int receivedWater;
    protected int receivedInternet;

    protected boolean needsElectricity;
    protected boolean needsWater;
    protected boolean needsInternet;


    //Service
    protected boolean hasSecurity;
    protected boolean hasEducation;
    protected boolean hasHealth;

    protected boolean needsSecurity;
    protected boolean needsEducation;
    protected boolean needsHealth;


    //Zone Outputs
    protected int receivedPopulation;
    protected int receivedGoods;
    protected int receivedLifestyle;

    protected boolean needsPopulation;
    protected boolean needsGoods;
    protected boolean needsLifestyle;

    protected boolean needsExcessPopulation;
    protected boolean needsExcessGoods;
    protected boolean needsExcessLifestyle;


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
        needsSecurity = false;
        needsHealth = false;
        needsEducation = false;
        needsElectricity = false;
        needsWater = false;
        needsInternet = false;
        needsPopulation = false;
        needsGoods = false;
        needsLifestyle = false;
        needsExcessPopulation = false;
        needsExcessGoods = false;
        needsExcessLifestyle = false;
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

    public void setOutput(int value) {
        output = value;
    }

    // Getters for necessary fields

    public boolean isNeedsElectricity() {
        return needsElectricity;
    }
    public boolean isNeedsWater() {
        return needsWater;
    }
    public boolean isNeedsInternet() {
        return needsInternet;
    }

    public boolean isNeedsHealth() {
        return needsHealth;
    }
    public boolean isNeedsSecurity() {
        return needsSecurity;
    }
    public boolean isNeedsEducation() {
        return needsEducation;
    }

    public boolean isNeedsGoods() {
        return needsGoods;
    }
    public boolean isNeedsLifestyle() {
        return needsLifestyle;
    }
    public boolean isNeedsPopulation() {
        return needsPopulation;
    }

    public boolean isNeedsExcessGoods() {
        return needsExcessGoods;
    }
    public boolean isNeedsExcessLifestyle() {
        return needsExcessLifestyle;
    }
    public boolean isNeedsExcessPopulation() {
        return needsExcessPopulation;
    }
}
