public abstract class ServiceProvider extends Cell
{
    protected int radius;
    protected int coordinateX;
    protected int coordinateY;

    public ServiceProvider(int radius, int coordinateX, int coordinateY)
    {
        this.radius = radius;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public void provideService(Cell grid)
    {
        System.out.println("WIP");
    }

    //Uses Manhattan Distance
    public void distributeService(Cell[][] gridMap)
    {
        for (int x = -radius; x < radius + 1 ; x++)
        {
            for (int y = -radius; y < radius + 1 ; y++)
            {
                if (x == 0 && y == 0)
                    continue;

                provideService(gridMap[coordinateX + x][coordinateY + y]);
            }
        }
    }
}
