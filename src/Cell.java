public abstract class Cell
{
    protected int coordinateX;
    public int getCoordinateX() {return coordinateX;}
    protected int coordinateY;
    public int getCoordinateY() {return coordinateY;}

    public Cell(int coordinateX, int coordinateY)
    {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
}