package objectville.cells.infrastructure;

import objectville.grid.Cell;
import objectville.grid.Connectable;

public class Road extends Cell implements Connectable
{
    public Road(int coordinateX, int coordinateY)
    {
        super(coordinateX, coordinateY);
    }

    @Override
    public boolean isConnectable()
    {
        return true;
    }
}
