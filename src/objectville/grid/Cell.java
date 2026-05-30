package objectville.grid;

public abstract class Cell
{

    protected int coordinateX;
    protected int coordinateY;
    public int getCoordinateX() {return coordinateX;}
    public int getCoordinateY() {return coordinateY;}

    public Cell(int coordinateX, int coordinateY)
    {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public boolean isConnectable() {
        return false;
    }

}